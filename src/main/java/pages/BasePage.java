package pages;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.io.File;
import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    public BasePage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }
    protected WebElement waitForElement(By by){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
    protected void click(By by){
        waitForElement(by).click();
    }
    protected void type(By by, String text){
        WebElement el = waitForElement(by);
        el.clear();
        el.sendKeys(text);
    }
    protected String getText(By by){
        return waitForElement(by).getText();
    }
//    public void takeScreenshot(String path){
//        TakesScreenshot ts = (TakesScreenshot) driver;
//        File src = ts.getScreenshotAs(OutputType.FILE);
//        src.renameTo(new File(path));
//    }
}
