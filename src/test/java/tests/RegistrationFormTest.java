package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CategoryPage;
import pages.HomePage;
import pages.ProductPage;
import pages.ShippingFormPage;

import java.time.LocalDateTime;

public class RegistrationFormTest extends BaseTest {

    // --------------------------------------------------------
    // פונקציית עזר: ניווט לטופס המשלוח
    // --------------------------------------------------------
    private ShippingFormPage navigateToForm() {
        HomePage home = new HomePage(driver);
        CategoryPage category = new CategoryPage(driver);

        home.openCategoryByHref("/tele");

        category.openFirstProduct();

        ProductPage product = new ProductPage(driver);
        product.waitForProductPageReady();
        product.buyNow();

        return new ShippingFormPage(driver);
    }

    // --------------------------------------------------------
    // בדיקה: שם פרטי חובה
    // --------------------------------------------------------
    @Test
    public void testRequiredFieldFirstName() {
        ShippingFormPage form = navigateToForm();
        form.fillValidBaseData();
        form.fillFirstName("");
        form.submitForm();

        Assert.assertTrue(form.hasErrorForField("firstname"), "שדה שם פרטי לא הציג שגיאת חובה");

        String screen1 = takeScreenshot("test3_registeration", "step1");
    }

    // --------------------------------------------------------
    // בדיקה: אימייל לא תקין
    // --------------------------------------------------------
    @Test
    public void testInvalidEmail() {
        ShippingFormPage form = navigateToForm();
        form.fillValidBaseData();
        form.fillEmail("wrong_email");
        form.submitForm();

        Assert.assertTrue(form.hasErrorForField("email"), "לא הופיעה שגיאה על אימייל לא תקין");

        String screen2 = takeScreenshot("test3_registeration", "step2");
    }

    // --------------------------------------------------------
    // בדיקה: טלפון — ספרות בלבד
    // --------------------------------------------------------
    @Test
    public void testTelephoneDigitsOnly() {
        ShippingFormPage form = navigateToForm();
        form.fillPhone("abc123!!");

        String value = driver.findElement(By.cssSelector("input#telephone")).getAttribute("value");
        Assert.assertTrue(value.matches("\\d+"), "שדה טלפון לא מסנן תווים שאינם ספרות");

        String screen3 = takeScreenshot("test3_registeration", "step3");
    }

    // --------------------------------------------------------
    // בדיקה: כפתור שליחה חסום כשהשדות ריקים
    // --------------------------------------------------------
    @Test
    public void testSubmitDisabledWhenRequiredEmpty() {
        ShippingFormPage form = navigateToForm();

        Assert.assertFalse(form.isSubmitEnabled(), "כפתור שליחה לא חסום כשהשדות ריקים");

        String screen4 = takeScreenshot("test3_registeration", "step4");
    }

}
