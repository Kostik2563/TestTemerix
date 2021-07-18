package ua.com.kulibin.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import ua.com.kulibin.WebDriverFeatures;
import ua.com.kulibin.testSettings.OtherSetting;
import ua.com.kulibin.testSettings.ThirdTestSetting;

public class ThirdTest extends WebDriverFeatures implements ThirdTestSetting, OtherSetting {

    /**
     * Перейти в раздел "Электроинструменты" / "Шуруповерты"
     * Вывести "Наименование" всех товаров у которых есть иконка с американским флагом на первых 3х страницах
     */
    @Test
    public void test() {
        goToTheScrewdrivers();

        openPageAndCheckFlag();
    }

    /**
     * метод открывает страницу и проходится по товарам при этом проверяет флаг товара,
     * в случаи если имя флага указанное в константе совпадает из флагом товара - выводиться имя товара
     */
    private void openPageAndCheckFlag() {
        String firstPageWithScrewdrivers = driver.getCurrentUrl();
        for (int i = 0; i < NUMBER_PAGE_FOR_TESTING_FLAG; i++) {
            if(i != 0)
                driver.get(firstPageWithScrewdrivers + "?PAGEN_1=" + (i + 1));
            System.out.println(ANSI_RED + "Names of goods with the flag of " +
                    NAME_THE_FLAG + " on " + (i + 1) +" page:" + ANSI_RESET);
            goForProducts();
        }
    }

    /**
     * метод поочерёдно идет по товарах и проверяет флаг
     */
    private void goForProducts() {
        int sequenceNumberProduct = 1;
        String xpathProduct = "/html/body/div[3]/div[1]/main/div[3]/div[2]/ul/li[" + sequenceNumberProduct + "]";
        while (isElementPresent(By.xpath(xpathProduct))) {
            String xpathFlag = "//img[contains(@src, '" + NAME_THE_FLAG + "')]";

            checkFlagTheProduct(xpathProduct, xpathFlag);

            sequenceNumberProduct++;
            xpathProduct = "/html/body/div[3]/div[1]/main/div[3]/div[2]/ul/li[" + sequenceNumberProduct + "]";
        }
    }

    /**
     * метод находит флаг и проверяет тот ли это флаг который мы ищем
     * @param xpathProduct идентификатор продукта
     * @param xpathFlag идентификатор флага который мы ещем
     */
    private void checkFlagTheProduct(String xpathProduct, String xpathFlag) {
        if(isElementPresent(By.xpath(xpathProduct + xpathFlag))) {
            getTheProductName(xpathProduct);
        }
    }

    /**
     * выводим на экран информацию о продукте, в частности его наименирование
     * @param xpathProduct идентификатор продукта
     */
    private void getTheProductName(String xpathProduct) {
        String nameClassWithNameProduct = "s_title";
        System.out.println("-\t" + driver.findElement(By.xpath(xpathProduct)).
                findElement(By.className(nameClassWithNameProduct)).getText());
    }

    /**
     * метод, используя меню, открывает страницу из шуроповертами
     */
    private void goToTheScrewdrivers() {
        String xpathButtonScrewdrivers =
                "/html/body/div[3]/div/div[1]/div/div/div[2]/div/div/ul/li[3]/div/div[1]/ul/li[24]/a";

        WebElement xpathButtonPowerTools =
                driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div/div/div[2]/div/div/ul/li[3]"));

        action.moveToElement(xpathButtonPowerTools).
                moveToElement(driver.findElement(By.xpath(xpathButtonScrewdrivers))).click().build().perform();
    }

}
