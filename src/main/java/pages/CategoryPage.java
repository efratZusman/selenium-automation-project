package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CategoryPage extends BasePage  {

    public CategoryPage(WebDriver d){ super(d); }

    public void openFirstProduct() {
        By firstProduct = By.cssSelector(".item.product.product-item:first-of-type a");
        click(firstProduct);
    }

}