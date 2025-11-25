package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DynamicContentPage extends BasePage {

    public DynamicContentPage(WebDriver driver) {
        super(driver);
    }

    private final By eilatButton = By.cssSelector(
            "button.header-eilat-2Rv.button-root_normalPriority-F4F.button-root-17M"
    );

    private final By eilatTitle = By.cssSelector("h1#category-title");

    public void clickEilatButton() {
        click(eilatButton);
    }

    public void waitForEilatTitle() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(eilatTitle));
    }

    public String getEilatTitleText() {
        return driver.findElement(eilatTitle).getText().trim();
    }
}
