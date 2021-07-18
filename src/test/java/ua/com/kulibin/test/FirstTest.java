package ua.com.kulibin.test;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import ua.com.kulibin.WebDriverFeatures;
import ua.com.kulibin.testSettings.FirstTestSetting;
import ua.com.kulibin.testSettings.OtherSetting;

public class FirstTest extends WebDriverFeatures implements OtherSetting, FirstTestSetting {

    /**
     * в разделе "Электроинструменты" / "Дрели"
     * на трех товарах с акционным тикетом  проверить есть ли в карточке товара акционная цена
     * Для этого, из раздела "Электроинструменты"  перейти в раздел "Дрели"  используя меню.
     * Рандомно, на страничке выбрать товар, провалиться в карточку товара и проверить наличие акционной и старой цены
     * Так для 3-х товаров (выбор количества проверяемых товаров сделать гибким)
     */
    @Test
    public void test() {
        goToTheDrillsPage();
        openTestPage();

        failInProductCardAndCheckPrices();
    }

    /**
     * метод открывает номер страницы который указан в константе настроек текущего теста
     */
    private void openTestPage() {
        if(FirstTestSetting.TEST_PAGE != 1)
            driver.get(driver.getCurrentUrl() + "?PAGEN_1=" + FirstTestSetting.TEST_PAGE);
    }


    /**
     * метод заданое количество раз выбирает товар, падает ему в карту и проверяет наличие акционной и старой цены
     */
    private void failInProductCardAndCheckPrices() {
        for (int i = 0; i < NUMBER_PRODUCTS_FOR_FIRST_TEST; i++) {
            choseRandomProduct();
            System.out.println(driver.getCurrentUrl());

            checkOldPrice();
            checkCurrentPrice();
            returnToThePreviousPage();
            System.out.println();
        }
    }

    /**
     * метод находит лабель из ценами и выводит на экран поточную цену,
     * если таковой нет - выводит соответствующее уведомления
     */
    public void checkCurrentPrice() {
        String xpathLabelWithPrices = "/html/body/div[3]/div/main/div[2]/div/div[2]/div/div[2]/div[3]";

        WebElement labelWithPrices = getLabelWithPrices(xpathLabelWithPrices);
        try {
            String currentPrice = labelWithPrices.findElement(By.className("price")).getText();
            System.out.println("Current price = " + currentPrice);
            if(currentPrice.length() == 0)
                System.out.println("\t is absent because the product is not available");
        } catch (NoSuchElementException e) {
            System.out.println(ANSI_RED + "The price is not specified." + ANSI_RESET);
        }
    }

    /**
     * метод находит лабель из ценами и возвращает этот элемент, в случаи если его нет - выводит на экран сообщение
     * @param xpathLabelWithPrices идентификатор лабеля из ценой
     * @return веб элемент из табличкою с ценами
     */
    private WebElement getLabelWithPrices(String xpathLabelWithPrices) {
        WebElement labelWithPrices = null;
        try {
            labelWithPrices = driver.findElement(By.xpath(xpathLabelWithPrices));
        } catch (NoSuchElementException e) {
            System.out.println(ANSI_RED + "label with the price is absent." + ANSI_RESET);
            System.exit(-1);
        }
        return labelWithPrices;
    }

    /**
     * метод пробует найти цену без скидки и если она есть выводит на экран,
     * а если нет, то выводит соответствующее уведомления
     */
    private void checkOldPrice() {
        String xpathOldPrice = "/html/body/div[3]/div/main/div[2]/div/div[2]/div/div[2]/div[3]/span[1]";
        String oldPrice = driver.findElement(By.xpath(xpathOldPrice)).getText();

        if (oldPrice.length() == 0)
            System.out.println(ANSI_RED + "The old price is not specified," +
                    " possibly goods without discount." + ANSI_RESET);
        else
            System.out.println("Old price = " + oldPrice);
    }

    /**
     * метод рандомно находит товар и нажимает на него,
     * если товара из сгенерированным номером не существует - выводит соответствующее уведомления
     */
    private void choseRandomProduct() {
        int sequenceNumberProduct = generateSequenceNumberProduct();

        String cssProduct = "body > div.page > div.wrapper > main > div.row > div.col-xs-9 > ul > li:nth-child(" +
                sequenceNumberProduct + ")";

        if(!isElementPresent(By.cssSelector(cssProduct))) {
            System.out.println("Could not find an item with such a randomly generated sequence number, "
                    + sequenceNumberProduct +
                    " check the number of items on the page and the number specified in the constant.");
            System.exit(-2);
        }

        WebElement product = driver.findElement(By.cssSelector(cssProduct));
        scrollToProduct(product);
        try {
            product.click();
        } catch (NoSuchElementException e) {
            System.out.println("Failed to go to the product card");
        }
    }

    /**
     * метод генерирует рандомно число, это число будет порядковым номером товара на странице;
     * в случае если рандомно число равно 0 - генерируется новый номер
     * @return порядковый номер товара
     */
    private int generateSequenceNumberProduct() {
        int sequenceNumberProduct = random.nextInt(PRODUCTS_ON_PAGE);

        while (sequenceNumberProduct == 0)
            sequenceNumberProduct = random.nextInt(PRODUCTS_ON_PAGE);

        return sequenceNumberProduct;
    }

    /**
     * метод, используя меню, открывает страницу из дрелями
     */
    private void goToTheDrillsPage() {
        try {
            String xpathButtonDrills = "/html/body/div[3]/div/div[1]/div/div/div[2]/div/div/ul/li[3]/div/div[1]/ul/li[3]/a";

            WebElement xpathButtonPowerTools =
                    driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div/div/div[2]/div/div/ul/li[3]"));

            action.moveToElement(xpathButtonPowerTools).
                    moveToElement(driver.findElement(By.xpath(xpathButtonDrills))).click().build().perform();
        } catch (NoSuchElementException e) {
            System.out.println(ANSI_RED + "Could not go to the drill page, there may not be such a page" + ANSI_RESET);
            System.exit(-1);
        }
    }

}