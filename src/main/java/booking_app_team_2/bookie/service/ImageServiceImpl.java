package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.domain.Image;
import booking_app_team_2.bookie.repository.ImageRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

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
        return Optional.empty();
    }

    @Override
    public Image save(Image image) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }

    @Override
    public byte[] getImageBytes(Long imageId) throws IOException {
        Optional<Image> image=imageRepository.findById(imageId);
        System.out.println("IME JE"+image.get().getName());
        if(image.isPresent()){
            InputStream in = getClass().getResourceAsStream(image.get().getPath());
            byte[] imageBytes = IOUtils.toByteArray(in);
            return imageBytes;
        }
        return null;
    }
    @Override
    public void postAccommodationImage(byte[] image,Long accommodationId) throws IOException {
        Optional<Accommodation>optionalAccommodation=accommodationService.findOne(accommodationId);
        Accommodation accommodation = new Accommodation();
        if(optionalAccommodation.isPresent()){
            accommodation=optionalAccommodation.get();
        }
        String imageName="slika"+accommodation.getId().toString()+Integer.toBinaryString(accommodation.getImages().size()+1);
        Path filePath = Paths.get("/images", imageName);
        Files.write(filePath,image);
    }
}
