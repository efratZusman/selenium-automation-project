package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class ProductPage extends BasePage {
    public ProductPage(WebDriver d){ super(d); }
    private By addToCartBtn = By.cssSelector(".action.primary.tocart.black");
    private By qtyInput = By.cssSelector("select[name='qty']"); // בוחר את הסלקט עצמו
    private By productName = By.cssSelector(".page-title-wrapper.product");
    private By productPrice = By.cssSelector("[id*='product-price']");

    public String getName(){ return getText(productName); }
    public String getPrice(){ return getText(productPrice); }

    public void setQuantity(int q){
      Select select = new Select(driver.findElement(qtyInput));
      select.selectByVisibleText(String.valueOf(q));
    }

    public void addToCart(){ click(addToCartBtn); }
}
