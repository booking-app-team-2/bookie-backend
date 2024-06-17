package booking_app_team_2.bookie.selenium.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CancelReservationDialog {
    private final WebDriver driver;

    @FindBy(id = "yes-button")
    private WebElement yesButton;

    @FindBy(id = "no-button")
    private WebElement noButton;

    public CancelReservationDialog(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded() {
        return
                new WebDriverWait(driver, Duration.ofSeconds(20))
                        .until(ExpectedConditions.elementToBeClickable(yesButton))
                        .isDisplayed();
    }

    public void confirmReservationCancellation() {
        yesButton.click();
    }

    public void cancelReservationCancellation() {
        noButton.click();
    }

    public boolean isUnloaded() {
        return new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.invisibilityOf(yesButton));
    }
}
