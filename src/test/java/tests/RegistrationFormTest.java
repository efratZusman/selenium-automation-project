package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CategoryPage;
import pages.HomePage;
import pages.ProductPage;
import pages.ShippingFormPage;

public class RegistrationFormTest extends BaseTest {

    ShippingFormPage form;
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
    // בדיקה: אימייל לא תקין
    // --------------------------------------------------------
    @Test
    public void testInvalidEmail() {
        form = navigateToForm();
        form.fillValidBaseData();
        form.fillEmail("wrong_email");
        form.submitForm();

        Assert.assertTrue(form.hasErrorForField("email"), "לא הופיעה שגיאה על אימייל לא תקין");

        takeScreenshot("test3_registeration", "step2");
    }

    // --------------------------------------------------------
    // בדיקה: טלפון — ספרות בלבד
    // --------------------------------------------------------
    @Test
    public void testTelephoneDigitsOnly() {
        form = navigateToForm();
        form.fillPhone("abc123!!");

        form.submitForm();
        Assert.assertTrue(form.hasErrorForField("telephone"),"לא הופיעה שגיאה על טלפון לא תקין");

//        String value = form.getPhoneValue();
//        Assert.assertTrue(value.matches("\\d+"), "שדה טלפון לא מסנן תווים שאינם ספרות");

        takeScreenshot("test3_registeration", "step3");
    }

    // --------------------------------------------------------
    // בדיקה: כפתור שליחה חסום כשהשדות ריקים
    // --------------------------------------------------------
    @Test
    public void testSubmitDisabledWhenRequiredEmpty() {
        form = navigateToForm();

        Assert.assertFalse(form.isSubmitEnabled(), "כפתור שליחה לא חסום כשהשדות ריקים");

        takeScreenshot("test3_registeration", "step4");
    }

    // --------------------------------------------------------
    // בדיקה: שם פרטי חובה
    // --------------------------------------------------------
    @Test
    public void testRequiredFieldFirstName() {
        form = navigateToForm();
        form.fillValidBaseData();
        form.fillFirstName("");
        form.submitForm();

        Assert.assertTrue(form.hasErrorForField("firstname"), "שדה שם פרטי לא הציג שגיאת חובה");

        takeScreenshot("test3_registeration", "step1");
    }

}
