package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    public HomePage(WebDriver d){ super(d); }

    By cartLink = By.cssSelector(".cartTrigger-triggerContainer-2Jd.hidden.items-center.h-lg.lg_grid");

    public void openCategoryByHref(String hrefFragment){
        By catLink = By.cssSelector("a[href*='" + hrefFragment + "']");
        click(catLink);
    }

    public void goToCart() {
        click(cartLink);
    }

}
