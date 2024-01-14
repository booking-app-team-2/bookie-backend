package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.domain.AccommodationType;
import booking_app_team_2.bookie.domain.Image;
import booking_app_team_2.bookie.repository.ImageRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ImageServiceImpl implements ImageService{
    private ImageRepository imageRepository;
    private AccommodationService accommodationService;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository, AccommodationService accommodationService){this.imageRepository=imageRepository; this.accommodationService=accommodationService;}

    @Override
    public List<Image> findAll() {
        return null;
    }

    @Override
    public Page<Image> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Image> findOne(Long id) {
        return imageRepository.findById(id);
    }

    @Override
    public Image save(Image image) {
        return this.imageRepository.save(image);
    }

    @Override
    public void remove(Long id) {
         this.imageRepository.deleteById(id);
    }

    @Override
    public byte[] getImageBytes(Long imageId) throws IOException {
        Optional<Image> image=imageRepository.findById(imageId);
        if(image.isPresent()){
            if(image.get().isDeleted()){
                return null;
            }
            Path imagePath=Path.of(image.get().getPath(),image.get().getName()+"."+image.get().getType());
            return Files.readAllBytes(imagePath);
        }
        return null;
    }
    @Override
    public String postAccommodationImage(MultipartFile image, Long accommodationId) throws IOException {
        Optional<Accommodation> optionalAccommodation=accommodationService.findOne(accommodationId);
        if(optionalAccommodation.isEmpty()){
            return null;
        }
        Accommodation accommodation=optionalAccommodation.get();
        String fileName="img_"+System.currentTimeMillis();
        Path uploadPath=Path.of("src/main/resources/images");
        Path filePath=uploadPath.resolve(fileName+".jpg");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        Image newImage=new Image();
        newImage.setName(fileName);
        newImage.setType("jpg");
        newImage.setPath("src/main/resources/images");
        newImage.setDeleted(false);
        accommodation.getImages().add(newImage);
        this.save(newImage);
        accommodationService.save(accommodation);
        return fileName;
    }

    @Override
    public String deleteImage(Long imageId) throws IOException {
        Optional<Image> optionalImage=this.findOne(imageId);
        if(optionalImage.isEmpty()){
            return null;
        }
        Image image=optionalImage.get();
        accommodationService.removeImage(image);
        this.remove(imageId);
        Path imagePath=Path.of(image.getPath(),image.getName()+"."+image.getType());
        if (Files.exists(imagePath)) {
            Files.delete(imagePath);
            return "Success";
        } else {
            return "Failed";
        }
    }
}
