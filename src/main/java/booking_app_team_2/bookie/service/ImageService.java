package booking_app_team_2.bookie.service;


import booking_app_team_2.bookie.domain.Image;

import java.io.IOException;

public interface ImageService extends GenericService<Image> {
    public byte[] getImageBytes(Long imageId) throws IOException;
    public void postAccommodationImage(byte[] image,Long accommodationId) throws IOException;
}
