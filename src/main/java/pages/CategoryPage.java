package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CategoryPage extends BasePage  {

    public CategoryPage(WebDriver d){ super(d); }

    private By firstProduct = By.cssSelector(".item-root-2AI:nth-of-type(1) a.item-name-1cZ");

    public void openFirstProduct() {
        click(firstProduct);
    }

}