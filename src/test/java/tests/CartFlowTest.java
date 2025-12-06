package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CategoryPage;
import pages.HomePage;
import pages.ProductPage;
import utils.ExcelWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartFlowTest extends BaseTest {

    @Test
    public void addThreeItemsAndVerifyCart() throws Exception {
        HomePage home = new HomePage(driver);
        CategoryPage category = new CategoryPage(driver);
        CartPage cart = new CartPage(driver);

        // =====================
        // פריט 1: קטגוריה A
        home.openCategoryByHref("/televisions");
        category.openFirstProduct();
        ProductPage product1 = new ProductPage(driver);
        product1.waitForProductPageReady();
        product1.setQuantity(1);
        product1.addToCart();
        home.waitForCartLoaded();

        String screen1 = takeScreenshot("test1_cart_flow", "step1");
//
//        String screen1 = "screens/test1_/step1_" + dtf.format(LocalDateTime.now()) + ".png";
//        takeScreenshot(screen1);
        String name1 = product1.getName();
        double price1 = product1.getPrice();

        // =====================
        // פריט 2: קטגוריה B
        home.openCategoryByHref("/household-electronics");
        category.openFirstProduct();
        ProductPage product2 = new ProductPage(driver);
        product2.waitForProductPageReady();
        product2.setQuantity(2);
        product2.addToCart();
        home.waitForCartLoaded();

        String screen2 = takeScreenshot("test1_cart_flow", "step2");
//
//        String screen2 = "screens/test1_/step2_" + dtf.format(LocalDateTime.now()) + ".png";
//        takeScreenshot(screen2);
        String name2 = product2.getName();
        double price2 = product2.getPrice();

        // =====================
        // פריט 3: קטגוריה C
        home.openCategoryByHref("/cookware-bakeware");
        category.openFirstProduct();
        ProductPage product3 = new ProductPage(driver);
        product3.waitForProductPageReady();
        product3.setQuantity(1);
        product3.addToCart();
        home.waitForCartLoaded();

        String screen3 = takeScreenshot("test1_cart_flow", "step3");
//
//        String screen3 = "screens/test1_/step3_" + dtf.format(LocalDateTime.now()) + ".png";
//        takeScreenshot(screen3);
        String name3 = product3.getName();
        double price3 = product3.getPrice();

        // =====================
        // פתיחת העגלה ובדיקת פריטים
        home.goToCart();
        home.waitForCartLoaded();
//        Thread.sleep(1500);

        double row1Price = price1 * 1;
        double row2Price = price2 * 2;
        double row3Price = price3 * 1;

        double subtotal = cart.getSubtotal();
        int amount = cart.getCartQuantity();
        double calculatedSubtotal = row1Price + row2Price + row3Price;

        Assert.assertTrue(subtotal > 0, "Subtotal לא גדול מאפס");
        Assert.assertEquals(amount, 3, "הכמות בעגלה לא תואמת");
        Assert.assertEquals(subtotal, calculatedSubtotal, 0.01, "Subtotal לא תואם לחישוב");

// =====================
// בניית רשימת מוצרים ל-Excel
        List<Map<String, String>> results = new ArrayList<>();

        Map<String, String> rowMap1 = new HashMap<>();
        rowMap1.put("ProductName", name1);
        rowMap1.put("UnitPrice", String.valueOf(price1));
        rowMap1.put("Quantity", "1");
        rowMap1.put("RowPrice", String.valueOf(row1Price));
        rowMap1.put("Status", "PASS");
        rowMap1.put("Screenshot", screen1);
        results.add(rowMap1);

        Map<String, String> rowMap2 = new HashMap<>();
        rowMap2.put("ProductName", name2);
        rowMap2.put("UnitPrice", String.valueOf(price2));
        rowMap2.put("Quantity", "2");
        rowMap2.put("RowPrice", String.valueOf(row2Price));
        rowMap2.put("Status", "PASS");
        rowMap2.put("Screenshot", screen2);
        results.add(rowMap2);

        Map<String, String> rowMap3 = new HashMap<>();
        rowMap3.put("ProductName", name3);
        rowMap3.put("UnitPrice", String.valueOf(price3));
        rowMap3.put("Quantity", "1");
        rowMap3.put("RowPrice", String.valueOf(row3Price));
        rowMap3.put("Status", "PASS");
        rowMap3.put("Screenshot", screen3);
        results.add(rowMap3);

// =====================
// חישוב סכומים
        double expectedTotal = row1Price + row2Price + row3Price;
        double actualTotal = cart.getSubtotal();

        String finalStatus =
                Math.abs(expectedTotal - actualTotal) < 0.01 ? "PASS" : "FAIL";

        String finalScreenshot =
                takeScreenshot("test1_cart_flow", "final_total");

        ExcelWriter.writeCartResults(
                "output/cart_results.xlsx",
                results,
                expectedTotal,
                actualTotal,
                finalStatus,
                finalScreenshot
        );

    }

}
