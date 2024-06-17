package booking_app_team_2.bookie.selenium.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;

    @FindBy(id = "bookie-logo")
    private WebElement bookieLogo;

    @FindBy(id = "username-input")
    private WebElement usernameInput;

    @FindBy(id = "password-input")
    private WebElement passwordInput;

    @FindBy(css = "#login-form > button")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isLoaded() {
        return
                new WebDriverWait(driver, Duration.ofSeconds(20))
                        .until(ExpectedConditions.visibilityOf(bookieLogo))
                        .isDisplayed();
    }

    public void inputFormFields(String username, String password) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
    }

    public void logIn() {
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
    }
}
