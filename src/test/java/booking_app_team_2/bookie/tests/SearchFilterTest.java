package booking_app_team_2.bookie.tests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import pages.HomePage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource( locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class SearchFilterTest extends TestBase{
    @Test
    public void blankSearch(){
        HomePage homePage=new HomePage(driver);
        homePage.clickSearchButton();
        List<String> accommodationNames=homePage.getAccommodationNames();
        List<String> correctNames=new ArrayList<>();
        correctNames.add("Accommodation1");
        correctNames.add("Accommodation2");
        correctNames.add("Accommodation3");
        correctNames.add("Accommodation4");
        correctNames.add("Accommodation5");
        assertEquals(accommodationNames,correctNames);
    }

    @Test
    public void searchWithGuestNumber(){
        HomePage homePage=new HomePage(driver);
        homePage.waitUntilLoad();
        homePage.guestTestInput();
        homePage.clickSearchButton();
        List<String> accommodationNames=homePage.getAccommodationNames();
        List<String> correctNames=new ArrayList<>();
        correctNames.add("Accommodation1");
        assertEquals(accommodationNames,correctNames);
    }

    @Test
    public void searchWithLocation(){
        //LOCATION API IS VERY VOLATILE
        HomePage homePage=new HomePage(driver);
        homePage.waitUntilLoad();
        homePage.locationTestInput();
        homePage.clickSearchButton();
        List<String> accommodationNames=homePage.getAccommodationNames();
        List<String> correctNames=new ArrayList<>();
        correctNames.add("Accommodation1");
        correctNames.add("Accommodation2");
        correctNames.add("Accommodation3");
        assertEquals(accommodationNames,correctNames);
    }

    @Test
    public void searchWithDate(){
        HomePage homePage=new HomePage(driver);
        homePage.waitUntilLoad();
        homePage.dateTestInput();
        homePage.clickSearchButton();
        List<String> accommodationNames=homePage.getAccommodationNames();
        List<String> correctNames=new ArrayList<>();
        correctNames.add("Accommodation1");
        correctNames.add("Accommodation2");
        assertEquals(accommodationNames,correctNames);
    }

    @Test
    public void searchWithGuestAndLocation(){
        HomePage homePage=new HomePage(driver);
        homePage.waitUntilLoad();
        homePage.guestTestInput();
        homePage.locationTestInput();
        homePage.clickSearchButton();
        List<String> accommodationNames=homePage.getAccommodationNames();
        List<String> correctNames=new ArrayList<>();
        correctNames.add("Accommodation1");
        assertEquals(accommodationNames,correctNames);
    }

    @Test
    public void searchWithDateAndGuest(){
        HomePage homePage=new HomePage(driver);
        homePage.waitUntilLoad();
        homePage.guestTestInput();
        homePage.dateTestInput();
        homePage.clickSearchButton();
        List<String> accommodationNames=homePage.getAccommodationNames();
        List<String> correctNames=new ArrayList<>();
        correctNames.add("Accommodation1");
        assertEquals(accommodationNames,correctNames);
    }

    @Test
    public void searchWithDateAndLocation(){
        //LOCATION API IS VERY VOLATILE
        HomePage homePage=new HomePage(driver);
        homePage.waitUntilLoad();
        homePage.locationTestInput();
        homePage.clickSearchButton();
        homePage.dateTestInput();
        List<String> accommodationNames=homePage.getAccommodationNames();
        List<String> correctNames=new ArrayList<>();
        correctNames.add("Accommodation1");
        correctNames.add("Accommodation2");
        assertEquals(accommodationNames,correctNames);
    }

    @Test
    public void searchWithDateAndLocationAndGuest(){
        HomePage homePage=new HomePage(driver);
        homePage.waitUntilLoad();
        homePage.locationTestInput();
        homePage.guestTestInput();
        homePage.clickSearchButton();
        homePage.dateTestInput();
        List<String> accommodationNames=homePage.getAccommodationNames();
        List<String> correctNames=new ArrayList<>();
        correctNames.add("Accommodation1");
        assertEquals(accommodationNames,correctNames);
    }

    @Test
    public void searchWithBadInput(){
        HomePage homePage=new HomePage(driver);
        homePage.waitUntilLoad();
        homePage.badLocationTestInput();
        homePage.clickSearchButton();
        List<String> accommodationNames=homePage.getAccommodationNames();
        List<String> correctNames=new ArrayList<>();
        assertEquals(accommodationNames,correctNames);
    }

    @Test
    public void studioFilter(){
        HomePage homePage=new HomePage(driver);
        homePage.clickSearchButton();
        homePage.clickFilterButton();
        homePage.studioFilter();
        List<String> accommodationNames=homePage.getAccommodationNames();
        List<String> correctNames=new ArrayList<>();
        correctNames.add("Accommodation2");
        assertEquals(accommodationNames,correctNames);
    }

    @Test
    public void studioAndRoomFilter(){
        HomePage homePage=new HomePage(driver);
        homePage.clickSearchButton();
        homePage.clickFilterButton();
        homePage.roomPlusStudioFilter();
        List<String> accommodationNames=homePage.getAccommodationNames();
        List<String> correctNames=new ArrayList<>();
        correctNames.add("Accommodation1");
        correctNames.add("Accommodation2");
        correctNames.add("Accommodation3");
        assertEquals(accommodationNames,correctNames);
    }

    @Test
    public void studioAndRoomAndSliderFilter(){
        HomePage homePage=new HomePage(driver);
        homePage.clickSearchButton();
        homePage.clickFilterButton();
        homePage.roomPlusStudioStartFilter();
        List<String> accommodationNames=homePage.getAccommodationNames();
        List<String> correctNames=new ArrayList<>();
        correctNames.add("Accommodation2");
        assertEquals(accommodationNames,correctNames);
    }

    @Test
    public void studioAndAllAmenitiesFilter(){
        HomePage homePage=new HomePage(driver);
        homePage.clickSearchButton();
        homePage.clickFilterButton();
        homePage.studioPlusAmenities();
        List<String> accommodationNames=homePage.getAccommodationNames();
        List<String> correctNames=new ArrayList<>();
        correctNames.add("Accommodation2");
        assertEquals(accommodationNames,correctNames);
    }
}
