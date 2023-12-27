package booking_app_team_2.bookie.service;

import booking_app_team_2.bookie.domain.Accommodation;
import booking_app_team_2.bookie.domain.AvailabilityPeriod;
import booking_app_team_2.bookie.domain.Owner;
import booking_app_team_2.bookie.dto.AccommodationDTO;
import booking_app_team_2.bookie.repository.AccommodationRepository;
import booking_app_team_2.bookie.repository.ReservationRepository;
import com.ibm.icu.text.Transliterator;
import lombok.Setter;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

@Setter
@Service
public class AccommodationServiceImpl implements AccommodationService {
    private AccommodationRepository accommodationRepository;

    @Autowired
    public AccommodationServiceImpl(AccommodationRepository accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
    }

    @Override
    public List<Accommodation> findSearched(String location,int numberOfGuests,long startDate,long endDate){
        List<Accommodation> accommodations = accommodationRepository.findAll();
        List<Accommodation> newAccommodations= new ArrayList<>(Collections.emptyList());
        for(Accommodation accommodation:accommodations){
            if(numberOfGuests>=accommodation.getMinimumGuests() && numberOfGuests<=accommodation.getMaximumGuests()){
                //TODO:Add for location
                for(AvailabilityPeriod availabilityPeriod:accommodation.getAvailabilityPeriods()){
                    if((startDate>=availabilityPeriod.getPeriod().getStartDate() && endDate<=availabilityPeriod.getPeriod().getEndDate())||(startDate==0 && endDate==0)){
                        if(Objects.equals(location, getCityName(accommodation.getLocation().getLatitude(), accommodation.getLocation().getLongitude()))||location==""){
                            newAccommodations.add(accommodation);
                            break;
                        }
                    }
                }
            }
        }
        return newAccommodations;
    }
    @Override
    public List<AccommodationDTO> getAll(){
        List<Accommodation> accommodations = accommodationRepository.findAll();
        return accommodations.stream()
                .map(accommodation -> new AccommodationDTO(accommodation.getId(),accommodation.getName(),accommodation.getDescription(),accommodation.getMinimumGuests(),accommodation.getMaximumGuests(),accommodation.getLocation(),accommodation.getAmenities(),accommodation.getAvailabilityPeriods(),accommodation.getReservationCancellationDeadline(),accommodation.getType()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Accommodation> findAll() {
        return accommodationRepository.findAll();
    }

    @Override
    public Page<Accommodation> findAll(Pageable pageable) {
        return accommodationRepository.findAll(pageable);
    }

    @Override
    public Optional<Accommodation> findOne(Long id) {
        return accommodationRepository.findById(id);
    }

    @Override
    public Accommodation save(Accommodation accommodation) {
        return accommodationRepository.save(accommodation);
    }

    @Override
    public void remove(Long id) {
        accommodationRepository.deleteById(id);
    }
    public static String getCityName(double latitude, double longitude) {
        try {
            String url = "https://nominatim.openstreetmap.org/reverse?format=json&lat=" + latitude + "&lon=" + longitude;

            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                // Parse and extract the city name from the JSON response
                String cityName=extractCityName(response.body());
                return transliterateToLatin(cityName);
            } else {
                System.out.println("Error: " + response.statusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String extractCityName(String jsonResponse) throws JSONException {
        JSONObject json = new JSONObject(jsonResponse);

        // Check if the "address" field is present in the JSON
        if (json.has("address")) {
            // Get the "city" field from the "address" JSON object
            JSONObject addressObject = json.getJSONObject("address");
            if (addressObject.has("city")) {
                return addressObject.getString("city");
            }
        }

        // If the expected structure is not found, return the full display name
        return json.getString("display_name");
    }
    private static String transliterateToLatin(String text) {
        Transliterator latinTransliterator = Transliterator.getInstance("Any-Latin; NFD; [:Nonspacing Mark:] Remove; NFC");
        return latinTransliterator.transliterate(text);
    }
}
