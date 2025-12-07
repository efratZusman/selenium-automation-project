package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DynamicContentPage;
import java.time.LocalDateTime;

public class DynamicContentChangeTest extends BaseTest {

    @Test
    public void testEilatButtonNavigation() {
        DynamicContentPage page = new DynamicContentPage(driver);

        takeScreenshot("test2_dynamic_change", "step1");

        page.clickEilatButton();

        page.waitForEilatTitle();

        Assert.assertEquals(
                page.getEilatTitleText(),
                "אילת",
                "העמוד לא הציג את כותרת אילת לאחר לחיצה על הכפתור"
        );

        takeScreenshot("test2_dynamic_change", "step2");
    }

}
