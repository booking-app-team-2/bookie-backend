package booking_app_team_2.bookie.controller;

import booking_app_team_2.bookie.service.ImageService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Setter
@RestController
@RequestMapping("/api/v1/images")
@CrossOrigin
public class ImageController {
    private ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService){
        this.imageService=imageService;
    }

    @DeleteMapping(value="/{imageId}")
    public ResponseEntity<String> deleteImage(@PathVariable Long imageId) throws IOException {
        String result= imageService.deleteImage(imageId);
        if(result == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }

    @GetMapping(value = "/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getAccommodationImages(@PathVariable Long imageId) throws IOException {
        byte[] image=imageService.getImageBytes(imageId);
        if(image==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(image,HttpStatus.OK);
    }
    @PostMapping(value = "/{accommodationId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('Owner')")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile file,
                                              @PathVariable Long accommodationId) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload");
        }
        try {
            String imageName=imageService.postAccommodationImage(file,accommodationId);
            return new ResponseEntity<>(imageName,HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
