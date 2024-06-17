package booking_app_team_2.bookie.selenium.test;

import booking_app_team_2.bookie.selenium.page.HomePage;
import booking_app_team_2.bookie.selenium.page.LoginPage;
import booking_app_team_2.bookie.selenium.page.MyReservationsPage;
import booking_app_team_2.bookie.selenium.testbase.TestBase;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class CancelReservationTest extends TestBase {
    @BeforeEach
    public void logIn() {
        HomePage homePage = new HomePage(driver);
        assertTrue(homePage.isLoaded());
        homePage.openLoginPage();

        LoginPage loginPage = new LoginPage(driver);
        assertTrue(loginPage.isLoaded());
        loginPage.inputFormFields("darko@gmail.com", "darko123");
        loginPage.logIn();

        assertTrue(homePage.isLoaded());
        homePage.openMyReservationsPage();

        MyReservationsPage myReservationsPage = new MyReservationsPage(driver);
        assertTrue(myReservationsPage.isLoaded());
    }

    @Test
    public void testCancelReservationWhenUnsuccessful() {

    }

    @AfterEach
    public void logOut() {
        MyReservationsPage myReservationsPage = new MyReservationsPage(driver);
        assertTrue(myReservationsPage.isLoaded());
        myReservationsPage.logOut();
    }
}
