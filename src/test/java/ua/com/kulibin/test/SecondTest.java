package ua.com.kulibin.test;

import org.testng.annotations.*;
import ua.com.kulibin.WebDriverSettings;

/**
 * Перейти в раздел "Электроинструменты" / "Перфораторы"
 * Проверить, что у всех товаров этого раздела есть цена на первых двух страницах.
 */
public class SecondTest extends WebDriverSettings {

    @BeforeMethod (description = "opens the perforators section")
    public void openSection() {
        perforatorsPage.openSection();
    }

    @AfterMethod (description = "check the availability of prices on all products")
    public void chekPricesOnPage() {
        perforatorsPage.checkPricesOnPage();
    }

    @Test(description = "this test check the price on all products on the first page")
    public void testPriceOnFirstPage() {
        perforatorsPage.openPage(1);
    }

    @Test(description = "this test check the price on all products on the second page")
    public void testPriceOnSecondPage() {
        perforatorsPage.openPage(2);
    }

}