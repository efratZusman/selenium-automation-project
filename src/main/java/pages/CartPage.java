package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.List;
import org.openqa.selenium.WebElement;

public class CartPage extends BasePage {
    public CartPage(WebDriver d){ super(d); }
    private By cartRows = By.cssSelector(".cart-item, .cart-row");
    private By subtotal = By.cssSelector(".cart-subtotal, .subtotal");

    public int getNumberOfRows(){
        List<WebElement> rows = driver.findElements(cartRows);
        return rows.size();
    }

    public double getSubtotal(){
        String s = getText(subtotal).replaceAll("[^0-9.,]","").replace(",",".");
        return Double.parseDouble(s);
    }

    // לקרוא את פרטי כל שורה: שם/qty/price
    public List<WebElement> getRows(){ return driver.findElements(cartRows); }
}
