package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    protected void closeFlashyPopupIfPresent() {
        try {
            WebElement popup = driver.findElement(
                    By.cssSelector("flashy-popup dialog[open]")
            );

            if (popup.isDisplayed()) {
                WebElement closeBtn = popup.findElement(
                        By.cssSelector(".close-on-click")
                );

                closeBtn.click();

                wait.until(ExpectedConditions.invisibilityOf(popup));
            }
        } catch (NoSuchElementException | TimeoutException ignore) {
        }
    }

    protected void click(By by) {
        closeFlashyPopupIfPresent();

        WebElement element = waitAndScroll(by);

        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    protected String getText(By by){
        return waitAndScroll(by).getText();
    }

    protected void type(By by, String text) {
        closeFlashyPopupIfPresent();

        WebElement el = waitAndScroll(by);

        try {
            el.clear();
            el.sendKeys(text);
        } catch (Exception e) {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].value = arguments[1];", el, text);
        }
    }

    protected WebElement waitAndScroll(By by){
        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(by));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center', behavior:'instant'});",
                el
        );

        return wait.until(ExpectedConditions.visibilityOf(el));
    }

//    protected void waitForOverlayToDisappear() {
//        try {
//            wait.until(ExpectedConditions.invisibilityOfElementLocated(
//                    By.cssSelector(".mask-root_active-17w")
//            ));
//        } catch (TimeoutException ignore) {}
//    }
}
