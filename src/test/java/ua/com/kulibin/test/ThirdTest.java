package ua.com.kulibin.test;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ua.com.kulibin.WebDriverSettings;
import ua.com.kulibin.testSettings.ThirdTestSetting;

/**
 * Перейти в раздел "Электроинструменты" / "Шуруповерты"
 * Вывести "Наименование" всех товаров у которых есть иконка с американским флагом на первых 3х страницах
 */
public class ThirdTest extends WebDriverSettings implements ThirdTestSetting {

    @BeforeMethod
    public void openSection() {
        screwdriversPage.openSection();
    }

    @AfterMethod
    public void checkFlagProducts() {
        screwdriversPage.checkFlagProduct();
    }

    @Test(description = "the presence of the required flag on 1 page is tested")
    public void testFlagOnFirstPage() {
        screwdriversPage.openPage(1);
    }
    @Test(description = "the presence of the required flag on 2 page is tested")
    public void testFlagOnSecondPage() {
        screwdriversPage.openPage(2);
    }
    @Test(description = "the presence of the required flag on 3 page is tested")
    public void testFlagOnThirdPage() {
        screwdriversPage.openPage(3);
    }

}
