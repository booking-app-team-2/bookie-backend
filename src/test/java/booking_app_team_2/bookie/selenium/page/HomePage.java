package booking_app_team_2.bookie.selenium.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    private final WebDriver driver;
    private static final String URL = "http://localhost:4200/";

    @FindBy(id = "search-button")
    private WebElement searchButton;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(id = "my-reservations-button")
    private WebElement myReservationsButton;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get(URL);
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded() {
        return
                new WebDriverWait(driver, Duration.ofSeconds(20))
                        .until(ExpectedConditions.visibilityOf(searchButton))
                        .isDisplayed();
    }

    public void openLoginPage() {
        loginButton.click();
    }

    public void openMyReservationsPage() {
        myReservationsButton.click();
    }
}
