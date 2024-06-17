package booking_app_team_2.bookie.selenium.page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class MyReservationsPage {
    private final WebDriver driver;

    @FindBy(id = "filter-holder")
    private WebElement filterHolder;

    @FindBy(xpath = "//app-reservation")
    private List<WebElement> reservations;

    @FindBy(id = "logout-button")
    private WebElement logoutButton;

    private WebElement lastCancelledReservation;

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

    public Optional<WebElement> getFirstCancellableReservation() {
        return
                reservations
                        .stream()
                        .filter(
                                reservation -> {
                                    LocalDate reservationCancellationDeadline;
                                    try {
                                        reservationCancellationDeadline =
                                                LocalDate.from(
                                                        LocalDate
                                                                .parse(
                                                                        reservation
                                                                                .findElement(By.id("period-value"))
                                                                                .getText()
                                                                                .split("-")[0]
                                                                                .trim(),
                                                                        DateTimeFormatter.ofPattern("dd/MM/yyyy")
                                                                )
                                                                .minusDays(
                                                                        Long.parseLong(reservation
                                                                                .findElement(
                                                                                        By.id("cancellation-value")
                                                                                )
                                                                                .getText()
                                                                                .trim())
                                                                )
                                                                .atStartOfDay(ZoneId.systemDefault())
                                                );
                                    } catch (NumberFormatException e) {
                                        throw
                                                new RuntimeException(
                                                        "Something went terribly wrong if the test got here..."
                                                );
                                    }
                                    System.out.println(reservationCancellationDeadline);

                                    return reservation
                                            .findElement(By.id("status-value"))
                                            .getText()
                                            .trim()
                                            .equals("Accepted") && LocalDate
                                            .now()
                                            .isBefore(reservationCancellationDeadline);
                                })
                        .findFirst();
    }

    public boolean hasCancellableReservations() {
        return getFirstCancellableReservation().isPresent();
    }

    public void cancelCancellableReservation() {
        Optional<WebElement> reservation = getFirstCancellableReservation();
        if (reservation.isEmpty())
            throw new RuntimeException("Something went terribly wrong if the test got here...");

        lastCancelledReservation = reservation.get();
        WebElement cancelButton = lastCancelledReservation.findElement(By.id("cancel-button"));

        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoView();", cancelButton);
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(cancelButton));
        cancelButton.click();
    }

    public Optional<WebElement> getFirstUncancellableReservation() {
        return
                reservations
                        .stream()
                        .filter(
                                reservation -> {
                                    LocalDate reservationCancellationDeadline;
                                    try {
                                        reservationCancellationDeadline =
                                                LocalDate.from(
                                                        LocalDate
                                                                .parse(
                                                                        reservation
                                                                                .findElement(By.id("period-value"))
                                                                                .getText()
                                                                                .split("-")[0]
                                                                                .trim(),
                                                                        DateTimeFormatter.ofPattern("dd/MM/yyyy")
                                                                )
                                                                .minusDays(
                                                                        Long.parseLong(reservation
                                                                                .findElement(
                                                                                        By.id("cancellation-value")
                                                                                )
                                                                                .getText()
                                                                                .trim())
                                                                )
                                                                .atStartOfDay(ZoneId.systemDefault())
                                                );
                                    } catch (NumberFormatException e) {
                                        throw
                                                new RuntimeException(
                                                        "Something went terribly wrong if the test got here..."
                                                );
                                    }

                                    return reservation
                                            .findElement(By.id("status-value"))
                                            .getText()
                                            .trim()
                                            .equals("Accepted") && (LocalDate
                                            .now()
                                            .isEqual(reservationCancellationDeadline) || LocalDate
                                            .now().isAfter(reservationCancellationDeadline));
                                })
                        .findFirst();
    }

    public boolean hasUncancellableReservations() {
        return getFirstUncancellableReservation().isPresent();
    }

    public void cancelUncancellableReservation() {
        Optional<WebElement> reservation = getFirstUncancellableReservation();
        if (reservation.isEmpty())
            throw new RuntimeException("Something went terribly wrong if the test got here...");

        lastCancelledReservation = reservation.get();
        WebElement cancelButton = lastCancelledReservation.findElement(By.id("cancel-button"));

        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoView();", cancelButton);
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(cancelButton));
        cancelButton.click();
    }

    public boolean hasReservationBeenCancelledCorrectly(boolean shouldReservationHaveBeenCancelled) {
        try {
            return
                    lastCancelledReservation
                            .findElement(By.id("status-value"))
                            .getText()
                            .trim()
                            .equals(shouldReservationHaveBeenCancelled ? "Cancelled" : "Accepted");
        } catch (StaleElementReferenceException e) {
            return shouldReservationHaveBeenCancelled;
        }
    }

    public void logOut() {
        logoutButton.click();
    }
}
