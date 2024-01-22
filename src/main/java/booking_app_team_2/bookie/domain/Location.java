package booking_app_team_2.bookie.domain;

import com.ibm.icu.text.Transliterator;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class Location {
    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    private static String extractCityName(String jsonResponse) throws JSONException {
        JSONObject json = new JSONObject(jsonResponse);
        if (json.has("address")) {
            JSONObject addressObject = json.getJSONObject("address");
            if (addressObject.has("city")) {
                return addressObject.getString("city");
            }
        }
        return json.getString("display_name");
    }

    private static String transliterateToLatin(String text) {
        Transliterator latinTransliterator = Transliterator.getInstance("Any-Latin; NFD; [:Nonspacing Mark:] Remove; NFC");
        return latinTransliterator.transliterate(text);
    }

    public boolean isInProximity(String city) {
        return Objects.equals(city, getCityName());
    }

    public String getCityName() {
        try {
            String url = "https://nominatim.openstreetmap.org/reverse?format=json&lat=" + this.latitude + "&lon=" + this.longitude;
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String cityName = extractCityName(response.body());
                return transliterateToLatin(cityName);
            } else {
                System.out.println("Error: " + response.statusCode());
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}
