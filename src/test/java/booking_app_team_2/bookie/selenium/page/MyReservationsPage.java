package booking_app_team_2.bookie.selenium.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MyReservationsPage {
    private final WebDriver driver;

    @FindBy(id = "filter-holder")
    private WebElement filterHolder;

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    public MyReservationsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded() {
        return
                new WebDriverWait(driver, Duration.ofSeconds(20))
                        .until(ExpectedConditions.visibilityOf(filterHolder))
                        .isDisplayed();
    }

    public void logOut() {
        logoutButton.click();
    }
}
