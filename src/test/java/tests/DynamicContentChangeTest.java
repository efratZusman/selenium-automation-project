package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DynamicContentPage;

public class DynamicContentChangeTest extends BaseTest {

    @Test
    public void testEilatButtonNavigation() {
        DynamicContentPage page = new DynamicContentPage(driver);

        page.clickEilatButton();

        page.waitForEilatTitle();  // ← עכשיו ההמתנה פנימית ב־Page

        Assert.assertEquals(
                page.getEilatTitleText(),
                "אילת",
                "העמוד לא הציג את כותרת אילת לאחר לחיצה על הכפתור"
        );
    }

}
