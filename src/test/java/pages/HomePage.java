package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.cglib.core.Local;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.List;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    private static String PAGE_URL="http://localhost:4200";

    @FindBy(className = "locationInput")
    private WebElement locationInput;

    @FindBy(className = "guestInput")
    private WebElement guestInput;

    @FindBy(css = "[matStartDate]")
    private WebElement startDateInput;

    @FindBy(css = "[matEndDate]")
    private WebElement endDateInput;

    @FindBy(className = "searchBtn")
    private WebElement searchButton;

    @FindBy(className = "filterBtn")
    private WebElement filterButton;

    @FindBy(className = "wifi")
    private WebElement wifiCheckbox;

    @FindBy(className = "parking")
    private WebElement parkingCheckbox;

    @FindBy(className = "ac")
    private WebElement acCheckbox;

    @FindBy(className = "kitchen")
    private WebElement kitchenCheckbox;

    @FindBy(className = "apartment")
    private WebElement apartmentCheckbox;

    @FindBy(className = "room")
    private WebElement roomCheckbox;

    @FindBy(className = "studio")
    private WebElement studioCheckbox;

    @FindBy(className = "startSlider")
    private WebElement startThumbInput;

    @FindBy(className = "calendarIcon")
    private WebElement calendarIcon;


    @FindBy(className = "cdk-overlay-container")
    private WebElement overlayCalendar;


    @FindBy(css = ".mat-calendar-body-cell[aria-label='24 January 2024']")
    private WebElement startDate;

    @FindBy(css = ".mat-calendar-body-cell[aria-label='25 January 2024']")
    private WebElement endDate;

    @FindBy(css = "div.statement-holder.button-holder button[color='primary'][mat-button]")
    private WebElement applyButton;

    public HomePage(WebDriver driver){
        this.driver=driver;
        driver.get(PAGE_URL);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void clickSearchButton() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    public void clickFilterButton(){
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.filterBtn")));
                filterButton.click();
    }
    public void studioFilter(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.statement-holder.button-holder button[color='primary'][mat-button]")));
        studioCheckbox.click();
        applyButton.click();
    }

    public void roomPlusStudioFilter(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.statement-holder.button-holder button[color='primary'][mat-button]")));
        studioCheckbox.click();
        roomCheckbox.click();
        applyButton.click();
    }

    public void roomPlusStudioStartFilter(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.statement-holder.button-holder button[color='primary'][mat-button]")));
        studioCheckbox.click();
        roomCheckbox.click();
        Actions actions = new Actions(driver);
        actions.clickAndHold(startThumbInput).moveByOffset(22, 0).release().perform();
        applyButton.click();
    }

    public void studioPlusAmenities(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.statement-holder.button-holder button[color='primary'][mat-button]")));
        studioCheckbox.click();
        wifiCheckbox.click();
        parkingCheckbox.click();
        acCheckbox.click();
        kitchenCheckbox.click();
        applyButton.click();
    }

    public void guestTestInput(){
        guestInput.sendKeys("3");
    }

    public void locationTestInput(){
        locationInput.sendKeys("Novi Sad");
    }

    public void badLocationTestInput(){
        locationInput.sendKeys("Notvi Sad");
    }

    public void dateTestInput(){
        calendarIcon.click();
        wait.until(ExpectedConditions.elementToBeClickable(startDate));
        startDate.click();
        endDate.click();
        overlayCalendar.click();
        wait.until(ExpectedConditions.invisibilityOf(overlayCalendar));

    }

    public void waitUntilLoad(){
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
    }

    public List<String> getAccommodationNames(){
        List<String> accommodationNames=new ArrayList<>();
        try {
            List<WebElement> accommodationNameElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("accommodationName")));
            for (WebElement accommodationNameElement : accommodationNameElements) {
                accommodationNames.add(accommodationNameElement.getText());
            }
        } catch (TimeoutException e) {
            System.out.println("No elements with class 'accommodationName' found.");
        }
        return accommodationNames;
    }
}
