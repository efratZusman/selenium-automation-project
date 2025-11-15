package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    public HomePage(WebDriver d){ super(d); }

    By cartLink = By.cssSelector("a.action.showcart");


    public void openCategoryByName(String name){
        By catLink = By.cssSelector("a[title='" + name + "']");
        click(catLink);
    }

    public void goToCart() {
        click(cartLink);
    }

}
