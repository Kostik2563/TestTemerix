package ua.com.kulibin.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ua.com.kulibin.WebDriverSettings;
import ua.com.kulibin.testSettings.FourthTestSetting;

/**
 * В разделе "Электроинструменты" / "Болгарки"
 * Для 10 рандомных товаров с пометкой "Акция"
 *                                      провести расчет акционной цены относительно старой используя процент скидки.
 * В assert упавшего теста вывести наименование товара его ожидаемую и фактическую цену.
 */
public class FourthTest extends WebDriverSettings implements FourthTestSetting {

    @BeforeMethod(description = "opens the grinder section")
    public void openSection() {
        grindersPage.openSection();
    }

    @Test (description = "the correspondence of the promotional price relative to the old one is tested using the discount percentage",
            invocationCount = QUANTITY_PRODUCTS_FOR_TESTING)
    public void testCompliancePromotionalPrice() {
        grindersPage.openPageWithPromotionalProducts(grindersPage.getNumberOfPages());
        grindersPage.openPromotionalProduct();
        grindersPage.calculatePromotionalPrice();
    }

}
