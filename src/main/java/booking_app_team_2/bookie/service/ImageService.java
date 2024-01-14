package booking_app_team_2.bookie.service;


import booking_app_team_2.bookie.domain.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService extends GenericService<Image> {
    public byte[] getImageBytes(Long imageId) throws IOException;
    public String postAccommodationImage(MultipartFile image, Long accommodationId) throws IOException;
    public String deleteImage(Long imageId) throws IOException;
}
