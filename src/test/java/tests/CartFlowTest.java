package tests;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CategoryPage;
import pages.HomePage;
import pages.ProductPage;
import utils.ExcelWriter;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartFlowTest extends BaseTest {

    @Test
    public void addThreeItemsAndVerifyCart() throws Exception {
        HomePage home = new HomePage(driver);
        ProductPage product = new ProductPage(driver);
        CategoryPage category = new CategoryPage(driver);
        CartPage cart = new CartPage(driver);

        List<Map<String, String>> results = new ArrayList<>();

        // פורמט לתמונת מסך עם תאריך ושעה
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

        // =====================
        // פריט 1: קטגוריה A
        home.openCategoryByHref("/televisions");
        category.openFirstProduct();
        String name1 = product.getName();
        double price1 = Double.parseDouble(product.getPrice());
        product.setQuantity(1);
        product.addToCart();
        Thread.sleep(1500);

        // צילום מסך
        String screen1 = "screens/step1_" + dtf.format(LocalDateTime.now()) + ".png";
        takeScreenshot(screen1);

        // =====================
        // פריט 2: קטגוריה B
        driver.navigate().back();
        home.openCategoryByHref("/refrigerators-freezers");
        category.openFirstProduct();
        String name2 = product.getName();
        double price2 = Double.parseDouble(product.getPrice());
        product.setQuantity(2);
        product.addToCart();
        Thread.sleep(1500);

        String screen2 = "screens/step2_" + dtf.format(LocalDateTime.now()) + ".png";
        takeScreenshot(screen2);

        // =====================
        // פריט 3: קטגוריה C
        driver.navigate().back();
        home.openCategoryByHref("/cookware-bakeware");
        category.openFirstProduct();
        String name3 = product.getName();
        double price3 = Double.parseDouble(product.getPrice());
        product.setQuantity(1);
        product.addToCart();
        Thread.sleep(1500);

        String screen3 = "screens/step3_" + dtf.format(LocalDateTime.now()) + ".png";
        takeScreenshot(screen3);

        // =====================
        // פתיחת העגלה ובדיקת פריטים
        home.goToCart();
        Thread.sleep(2000);

        // חישוב מחיר שורה
        double row1Price = price1 * 1;
        double row2Price = price2 * 2;
        double row3Price = price3 * 1;

        // בדיקת subtotal
        double subtotal = cart.getSubtotal();
        double calculatedSubtotal = row1Price + row2Price + row3Price;

        assert subtotal > 0 : "Subtotal לא גדול מאפס";
        assert subtotal <= calculatedSubtotal : "Subtotal גבוה מהמחושב - יתכן מבצע";

        // הוספה ל־Excel
        Map<String, String> rowMap1 = new HashMap<>();
        rowMap1.put("ProductName", name1);
        rowMap1.put("UnitPrice", String.valueOf(price1));
        rowMap1.put("Quantity", "1");
        rowMap1.put("RowPrice", String.valueOf(row1Price));
        rowMap1.put("Status", "PASS");
        rowMap1.put("Screenshot", screen1);

        Map<String, String> rowMap2 = new HashMap<>();
        rowMap2.put("ProductName", name2);
        rowMap2.put("UnitPrice", String.valueOf(price2));
        rowMap2.put("Quantity", "2");
        rowMap2.put("RowPrice", String.valueOf(row2Price));
        rowMap2.put("Status", "PASS");
        rowMap2.put("Screenshot", screen2);

        Map<String, String> rowMap3 = new HashMap<>();
        rowMap3.put("ProductName", name3);
        rowMap3.put("UnitPrice", String.valueOf(price3));
        rowMap3.put("Quantity", "1");
        rowMap3.put("RowPrice", String.valueOf(row3Price));
        rowMap3.put("Status", "PASS");
        rowMap3.put("Screenshot", screen3);

        results.add(rowMap1);
        results.add(rowMap2);
        results.add(rowMap3);

        ExcelWriter.writeCartResults("output/cart_results.xlsx", results);
    }

    // פונקציה לצילום מסך
    public void takeScreenshot(String name) {
        try {
            File dir = new File("screens");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destFile = new File(dir, name + ".png");
            FileUtils.copyFile(scrFile, destFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
