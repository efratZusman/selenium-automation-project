package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class ProductPage extends BasePage {
    public ProductPage(WebDriver d){ super(d); }
    private By addToCartButton = By.cssSelector("button.productFullDetail-addToCartButton-1Zo");
    private By plusButton = By.cssSelector("quantityStepper-button_increment-1ih.quantityStepper-button-BAm.bg-white.border.border-solid.border-button.h-[2rem].inline-flex.items-center.justify-center.px-4xs.w-[2rem].disabled_cursor-not-allowed"); // בוחר את הסלקט עצמו
    private By productName = By.cssSelector(".productFullDetail-productName-iar.font-semibold.text-colorDefault.text-lg.lg_text-2xl.text-primary.font-bold");
    private By productPrice = By.cssSelector(
            ".productPrice-productPrice-2X8.block.mt-8.mb-2.text-colorDefault.text-primary.text-xl.lg_text-2xl > div > span:nth-of-type(2)"
    );

    public String getName(){ return getText(productName); }
    public String getPrice(){ return getText(productPrice); }

    public void setQuantity(int q) {
        for (int i = 1; i < q; i++) {
            driver.findElement(plusButton).click();
        }
    }

    public void addToCart() {
        click(addToCartButton);
    }

}
