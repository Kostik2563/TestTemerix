package ua.com.kulibin.test;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import ua.com.kulibin.WebDriverFeatures;
import ua.com.kulibin.testSettings.OtherSetting;
import ua.com.kulibin.testSettings.SecondTestSetting;

public class SecondTest extends WebDriverFeatures implements OtherSetting, SecondTestSetting {

    /**
     * Перейти в раздел "Электроинструменты" / "Перфораторы"
     * Проверить, что у всех товаров этого раздела есть цена на первых двух страницах.
     */
    @Test
    public void test() {
        goToThePerforators();

        openPageAndCheckPrices();
    }

    /**
     * метод открывает соответствующую страницу и выводит на экран все ли товары имеют цену
     */
    private void openPageAndCheckPrices() {
        String firstPageWithPerforators = driver.getCurrentUrl();
        for (int i = 0; i < NUMBER_TEST_PAGES; i++) {
            if(i != 0)
                driver.get(firstPageWithPerforators + "?PAGEN_1=" + (i + 1));

            getInfoAboutAvailabilityPricesOnPage(i);
        }
    }

    /**
     * метод выводит на экран информацию о наличие цен на проверяемой странице
     * @param numberPage номер проверяемой страницы
     */
    private void getInfoAboutAvailabilityPricesOnPage(int numberPage) {
        if(allPricesPresent())
            System.out.println("On the page " + (numberPage + 1) + " all products have a price");
        else
            System.out.println(ANSI_RED + "On the page " + (numberPage + 1) +
                    " not on all products have a price " + ANSI_RESET);
    }

    /**
     * метод, используя меню, открывает страницу из перфораторами
     */
    private void goToThePerforators() {
        String xpathButtonPerforators =
                "/html/body/div[3]/div/div[1]/div/div/div[2]/div/div/ul/li[3]/div/div[1]/ul/li[12]/a";

        WebElement xpathButtonPowerTools =
                driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div/div/div[2]/div/div/ul/li[3]"));

        action.moveToElement(xpathButtonPowerTools).
                moveToElement(driver.findElement(By.xpath(xpathButtonPerforators))).click().build().perform();
    }

    /**
     * метод проверяет все ли товары имею актуальную цену
     * @return true - если все товары раздела перфораторы имеют цену; false - в противном случаи
     */
    public boolean allPricesPresent() {
        boolean allProductsOnPageHavePrice = true;
        int sequenceNumberProduct = 1;
        String xpathProduct = "/html/body/div[3]/div[1]/main/div[3]/div[2]/ul/li[" + sequenceNumberProduct + "]";

        while (isElementPresent(By.xpath(xpathProduct))) {
            String xpathLabelWithPrice = xpathProduct + "/div/div[3]/div[1]/div[1]/span";

            if(!productHavePrice(xpathProduct, xpathLabelWithPrice))
                allProductsOnPageHavePrice = false;

            sequenceNumberProduct++;
            xpathProduct = "/html/body/div[3]/div[1]/main/div[3]/div[2]/ul/li[" + sequenceNumberProduct + "]";
        }
        return allProductsOnPageHavePrice;
    }

    /**
     * метод сначала пробует найти акционную цену, если её нет, то ищет цену без акции, если ни того ни того нет,
     * то выводит соответственное уведомления из ссылкой на товар
     * @param xpathProduct идентификатор товара
     * @param xpathLabelWithPrice идентификатор к надпису из ценой
     * @return true - если товар имеет цену; false - в противном случаи
     */
    private boolean productHavePrice(String xpathProduct, String xpathLabelWithPrice) {
        boolean productHavePrice = true;
        try {
            driver.findElement(By.xpath(xpathLabelWithPrice + "[2]")).getText();
        }
        catch (NoSuchElementException e) {
            try {
                driver.findElement(By.xpath(xpathLabelWithPrice)).getText();
            } catch (NoSuchElementException exception) {
                if(RECEIVE_INFORMATION_ABOUT_PRODUCT_WHERE_NO_PRICE)
                    getProductInformation(xpathProduct);
                productHavePrice = false;
            }
        }
        return productHavePrice;
    }

    /**
     * метод нажимает на товар из переданым идентификаторм,
     * выводит на экран ссылку на этот товар и возвращается к предыдущей странице
     * @param xpathProduct идентификатор товара
     */
    private void getProductInformation(String xpathProduct) {
        driver.findElement(By.xpath(xpathProduct)).click();
        System.out.println("Link to a product in which there is no price " + driver.getCurrentUrl());
        returnToThePreviousPage();
    }

}