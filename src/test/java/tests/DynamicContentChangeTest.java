package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DynamicContentPage;
import java.time.LocalDateTime;

public class DynamicContentChangeTest extends BaseTest {

    @Test
    public void testEilatButtonNavigation() {
        DynamicContentPage page = new DynamicContentPage(driver);

        String screen1 = takeScreenshot("test2_dynamic_change", "step1");
//        String screen1 = "screens/test2_/step1_" + dtf.format(LocalDateTime.now()) + ".png";
//        takeScreenshot(screen1);

        page.clickEilatButton();

        page.waitForEilatTitle();

        Assert.assertEquals(
                page.getEilatTitleText(),
                "אילת",
                "העמוד לא הציג את כותרת אילת לאחר לחיצה על הכפתור"
        );

        String screen2 = takeScreenshot("test2_dynamic_change", "step2");
//        String screen2 = "screens/test2_/step2_" + dtf.format(LocalDateTime.now()) + ".png";
//        takeScreenshot(screen2);
    }

}
