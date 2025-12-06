package pages;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class ProductPage extends BasePage {
    public ProductPage(WebDriver d){ super(d); }
    private By addToCartButton = By.cssSelector("button.productFullDetail-addToCartButton-1Zo");
    private By quantityInput = By.cssSelector("section.productFullDetail-quantity-lZ3 .quantityStepper-input-1RC"    );
    private By buyNowButton = By.cssSelector("button.productFullDetail-purchaseButton-1AC");
    private By productName = By.cssSelector(".productFullDetail-productName-iar.font-semibold.text-colorDefault.text-lg.lg_text-2xl.text-primary.font-bold");
    private By productPriceDiv = By.cssSelector(".productPrice-productPrice-2X8 > div:not(del)");

    public double getPrice() {
        String raw = getText(productPriceDiv);
        String cleaned = raw.replaceAll("[^0-9,]", "")
                .replace(",", "");
        return Double.parseDouble(cleaned);
    }

    public String getName(){ return getText(productName); }

    public void setQuantity(int q) {
        type(quantityInput, String.valueOf(q) + Keys.ENTER);
    }

    public void addToCart() {
        click(addToCartButton);
    }

    public void buyNow() {
        click(buyNowButton);
    }

    public void waitForProductPageReady() {
        WebElement name = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".productFullDetail-productName-iar")
                )
        );

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                name
        );
    }
}
