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
//        try {
//            WebElement popup = driver.findElement(
//                    By.cssSelector("flashy-popup dialog[open]")
//            );
//
//            if (popup.isDisplayed()) {
//                WebElement closeBtn = popup.findElement(
//                        By.cssSelector(".close-on-click")
//                );
//
//                closeBtn.click();
//
//                wait.until(ExpectedConditions.invisibilityOf(popup));
//            }
//        } catch (NoSuchElementException | TimeoutException ignore) {
//        }
    }

    protected void removeWhatsAppShareIfExists() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript(
                "document.querySelectorAll(" +
                        "'.react-share__ShareButton, " +
                        "button[aria-label*=\\\"וואצפ\\\"], " +
                        "button[aria-label*=\\\"whatsapp\\\" i]'" +
                        ").forEach(function(el){ el.remove(); });"
        );
    }

    protected void disableNewTabs() {
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelectorAll('a[target=_blank]').forEach(a => a.removeAttribute('target'));"
        );
    }

    protected void click(By by) {
        closeFlashyPopupIfPresent();
        removeWhatsAppShareIfExists();

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
            el.sendKeys(Keys.chord(Keys.CONTROL, "a"));
            el.sendKeys(Keys.DELETE);

            el.sendKeys(text);

        } catch (Exception e) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value='';", el);
            js.executeScript("arguments[0].value=arguments[1];", el, text);
        }
    }

    protected WebElement waitAndScroll(By by){
        disableNewTabs();
        removeWhatsAppShareIfExists();

        WebElement el = wait.until(ExpectedConditions.presenceOfElementLocated(by));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center', behavior:'instant'});",
                el
        );

        return wait.until(ExpectedConditions.visibilityOf(el));
    }
}
