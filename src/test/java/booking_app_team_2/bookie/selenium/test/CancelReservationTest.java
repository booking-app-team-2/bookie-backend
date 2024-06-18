package booking_app_team_2.bookie.selenium.test;

import booking_app_team_2.bookie.selenium.page.CancelReservationDialog;
import booking_app_team_2.bookie.selenium.page.HomePage;
import booking_app_team_2.bookie.selenium.page.LoginPage;
import booking_app_team_2.bookie.selenium.page.MyReservationsPage;
import booking_app_team_2.bookie.selenium.testbase.TestBase;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
    }

    @Test
    @Order(1)
    public void testCancelReservationCancellation() {
        MyReservationsPage myReservationsPage = new MyReservationsPage(driver);
        assertTrue(myReservationsPage.isLoaded());
        assertTrue(myReservationsPage.hasCancellableReservations());
        myReservationsPage.cancelCancellableReservation();

        CancelReservationDialog cancelReservationDialog = new CancelReservationDialog(driver);
        assertTrue(cancelReservationDialog.isLoaded());
        cancelReservationDialog.cancelReservationCancellation();
        assertTrue(cancelReservationDialog.isUnloaded());

        assertTrue(myReservationsPage.hasReservationBeenCancelledCorrectly(false));
    }

    @Test
    @Order(2)
    public void testCancelReservation() {
        MyReservationsPage myReservationsPage = new MyReservationsPage(driver);
        assertTrue(myReservationsPage.isLoaded());
        assertTrue(myReservationsPage.hasCancellableReservations());
        myReservationsPage.cancelCancellableReservation();

        CancelReservationDialog cancelReservationDialog = new CancelReservationDialog(driver);
        assertTrue(cancelReservationDialog.isLoaded());
        cancelReservationDialog.confirmReservationCancellation();
        assertTrue(cancelReservationDialog.isUnloaded());

        assertTrue(myReservationsPage.isLoaded());
        assertTrue(myReservationsPage.hasReservationBeenCancelledCorrectly(true));
    }

    @Test
    @Order(3)
    public void testCancelReservationWhenUnsuccessful() {
        MyReservationsPage myReservationsPage = new MyReservationsPage(driver);
        assertTrue(myReservationsPage.isLoaded());
        assertTrue(myReservationsPage.hasUncancellableReservations());
        myReservationsPage.cancelUncancellableReservation();

        CancelReservationDialog cancelReservationDialog = new CancelReservationDialog(driver);
        assertTrue(cancelReservationDialog.isLoaded());
        cancelReservationDialog.confirmReservationCancellation();
        assertTrue(cancelReservationDialog.isUnloaded());

        assertTrue(myReservationsPage.isLoaded());
        assertTrue(myReservationsPage.hasReservationBeenCancelledCorrectly(false));
    }

    @AfterEach
    public void logOut() {
        MyReservationsPage myReservationsPage = new MyReservationsPage(driver);
        assertTrue(myReservationsPage.isLoaded());
        myReservationsPage.logOut();
    }
}
