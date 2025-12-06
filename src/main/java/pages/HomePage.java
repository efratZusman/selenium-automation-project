package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    public HomePage(WebDriver d){ super(d); }

    By cartLink = By.cssSelector(".cartTrigger-triggerContainer-2Jd.hidden.items-center.h-lg.lg_grid");
    By cartTable = By.cssSelector("aside.miniCart-root_open-1sp");

    public void openCategoryByHref(String hrefFragment){
        By catLink = By.cssSelector("a[href*='" + hrefFragment + "']");
        click(catLink);
    }

    public void goToCart() {
        click(cartLink);
    }

    public void waitForCartLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartTable));
    }
}
