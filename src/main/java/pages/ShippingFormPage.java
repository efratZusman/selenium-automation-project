package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ShippingFormPage extends BasePage {

    public ShippingFormPage(WebDriver driver) {
        super(driver);
    }

    private final By firstName = By.cssSelector("input#firstname");
    private final By lastName = By.cssSelector("input#lastname");
    private final By email = By.cssSelector("input#email");
    private final By phone = By.cssSelector("input#telephone");
    private final By street = By.cssSelector("input#street");
    private final By city = By.cssSelector("div[class*='guestForm-city'] input");
    private final By houseNumber = By.cssSelector("input#house_number");
    private final By floorNumber = By.cssSelector("input#floor_number");
    private final By apartmentNumber = By.cssSelector("input#apartment_number");
    private final By submitButton = By.cssSelector("button[type='submit']");

    public void fillFirstName(String txt) { type(firstName, txt); }
    public void fillLastName(String txt) { type(lastName, txt); }
    public void fillEmail(String txt) { type(email, txt); }
    public void fillPhone(String txt) { type(phone, txt); }
    public void fillStreet(String txt) { type(street, txt); }
    public void fillHouseNumber(String txt) { type(houseNumber, txt); }
    public void fillFloor(String txt) { type(floorNumber, txt); }
    public void fillApartment(String txt) { type(apartmentNumber, txt); }

    // עיר – כי זה AutoComplete
    public void fillCity(String txt) {
        type(city, txt + Keys.ENTER);
    }

    public void submitForm() {
        driver.findElement(submitButton).click();
    }

    public boolean isSubmitEnabled() {
        return driver.findElement(submitButton).isEnabled();
    }

    public void fillValidBaseData() {
        fillFirstName("John");
        fillLastName("Doe");
        fillEmail("john@example.com");
        fillPhone("0501234567");
        fillStreet("ירמיהו");
        fillCity("ירושלים");
        fillFloor("5");
        fillHouseNumber("12");
        fillApartment("17");
    }

    public String getPhoneValue() {
        return driver.findElement(phone).getAttribute("value");
    }

    // ========================
    // Error validation
    // Error format: element + sibling with class "error"
    // ========================

    public boolean hasErrorForField(String fieldName) {
        try {
            By selector = By.cssSelector(
                    "[class^='guestForm-" + fieldName + "'] p[class^='message-root_error']"
            );

            List<WebElement> errors = driver.findElements(selector);

            for (WebElement error : errors) {
                String text = error.getText().trim();
                System.out.println("ERROR for " + fieldName + ": '" + text + "'");

                if (!text.isEmpty()) {
                    return true;
                }
            }

            return false;

        } catch (Exception e) {
            System.out.println("ERROR elements for " + fieldName + " NOT FOUND");
            return false;
        }
    }



}
