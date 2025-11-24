package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.util.List;
import org.openqa.selenium.WebElement;

public class CartPage extends BasePage {
    public CartPage(WebDriver d){ super(d); }

    private By subtotal = By.cssSelector(".priceSummary-totalPrice-2Y9");
    private By cartQuantity = By.cssSelector("h2.miniCart-quantity-N3D");

    public int getCartQuantity() {
        String text = driver.findElement(cartQuantity).getText();
        // חיפוש המספר בתוך הסוגריים
        java.util.regex.Matcher m = java.util.regex.Pattern.compile("\\((\\d+)\\)").matcher(text);
        if (m.find()) {
            return Integer.parseInt(m.group(1));
        } else {
            return 0;
        }
    }

    public double getSubtotal(){
        String s = getText(subtotal)
                .replaceAll("[^0-9.,]", "")
                .replace(",", ".");
        return Double.parseDouble(s);
    }

}

