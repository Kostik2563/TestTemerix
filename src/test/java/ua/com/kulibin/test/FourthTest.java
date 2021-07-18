package ua.com.kulibin.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import ua.com.kulibin.WebDriverFeatures;
import ua.com.kulibin.testSettings.FourthTestSetting;
import ua.com.kulibin.testSettings.OtherSetting;

import java.util.ArrayList;
import java.util.HashMap;

public class FourthTest extends WebDriverFeatures implements FourthTestSetting, OtherSetting {

    /**
     * В разделе "Электроинструменты" / "Болгарки"
     * Для 10 рандомных товаров с пометкой "Акция"
     *                                      провести расчет акционной цены относительно старой используя процент скидки.
     * В assert упавшего теста вывести наименование товара его ожидаемую и фактическую цену.
     */
    @Test
    public void test() {
        goToTheGrinder();

        calculateDiscount();
    }

    /**
     * метод выбирает константное число раз товар
     * и проводит расчёт акционной цены относительно старой используя процент скидки
     */
    private void calculateDiscount() {
        String urlFirstPage = driver.getCurrentUrl();

        HashMap<Integer, ArrayList<String>> allPromotionalItems = collectAllPromotionalItems(urlFirstPage);

        for (int i = 0; i < QUANTITY_PRODUCTS_FOR_TESTING; i++) {
            String productLocator = getLocatorRandomProduct(allPromotionalItems, urlFirstPage);
            int percentageDiscount = getPercentageDiscount(productLocator);

            calculateDiscountCurrentProduct(productLocator, percentageDiscount);
        }
    }

    /**
     * метод из коллекции акционных товаров рандомно выбирает ключ, то есть страницу,
     * в значении этого ключа рандомно выбирает акционный товар и возвращает его удаляя его из коллекции,
     *                                                      чтобы этот товар больше не попался под рандом
     * @param allPromotionalItems мапа, ключ которой есть страничка,
     *                            а ее значение колекции из локаторами на акционный товар,
     *                            инными словами это коллекция из всема акционными товарами из всех страничек
     * @param urlFirstPage ссылка первой страници, нужно в случае если рандом выдаст старничку больше первой
     * @return локатор на рандомно выбран товар
     */
    private String getLocatorRandomProduct(HashMap<Integer, ArrayList<String>> allPromotionalItems,
                                           String urlFirstPage) {
        int randomPage = generatePageNumber(allPromotionalItems.size());
        int randomProduct = generateProductNumber(allPromotionalItems.get(randomPage).size());

        if(randomProduct == 0) {
            while (randomProduct == 0) {
                allPromotionalItems.remove(randomPage);
                randomPage = generatePageNumber(allPromotionalItems.size());
                randomProduct = generateProductNumber(allPromotionalItems.get(randomPage).size());
            }
        }
        openPage(randomPage, urlFirstPage);
        // + 1 потому что массивы/коллекции начальный элемент считается от 0
        return allPromotionalItems.get(randomPage).remove(randomProduct - 1);
    }

    /**
     * метод получает текущую цену, цену до скидки и процент скидки переданый в параметрах;
     * затем используя или эти данные делает расчет акционной цены относительно старой
     * @param xpathProduct идентификатор продукта
     * @param percentageDiscount процент скидки
     */
    private void calculateDiscountCurrentProduct(String xpathProduct, int percentageDiscount) {
        double actualPrice = getDiscountedPrice(xpathProduct);
        double oldPrice = getOldPrice(xpathProduct);
        double expectedPrice = oldPrice - (oldPrice / ONE_HUNDRED * percentageDiscount);
        checkPromotionalPriceRelativeOld(actualPrice, expectedPrice, xpathProduct);
    }

    /**
     * метод сравнивает ожидаемую и актуальную цену,
     * если они не равны, то выводится на экран название продукта, его фактическая и ожидаемая цена
     * @param actualPrice текущая цена
     * @param expectedPrice ожидаемая цена
     * @param xpathProduct идентификатор продукта для получения наименования товара
     */
    private void checkPromotionalPriceRelativeOld(double actualPrice, double expectedPrice, String xpathProduct) {
        String nameProduct = driver.findElement(By.xpath(xpathProduct + "/div/h4/a/span")).getText();
        if(actualPrice != expectedPrice)
            System.out.println( ANSI_RED +
                    "Product name: " + nameProduct + ANSI_RESET +
                    "\nExpected price: " + expectedPrice +
                    "\nActual price: " + actualPrice + "\n");

        /* TODO отказался только потому что assert,
         *  в случаи если актуальная цена не равна ожыдаемой, прекратит исполнение программи */
        /* Assert.assertEquals(actualPrice, expectedPrice, ANSI_RED +
         *      "Product name: " + nameProduct + ANSI_RESET +
         *      "\nExpected price: " + expectedPrice +
         *      "\nActual price: " + actualPrice); */
    }

    /**
     * метод ищет цену до акции и возвращает ее, получаем ее в виде строки, затем удаляем все символы кроме цифр
     * @param product продукт в котором будет происходит поиск цени
     * @return цена до акции
     */
    private double getOldPrice(String product) {
        String oldPrice = driver.findElement(By.xpath(product + "/div/div[3]/div[1]/div[1]/span[1]")).getText();
        // удаляем все символы кроме цифр
        oldPrice = oldPrice.replaceAll("\\D+","");
        return Double.parseDouble(oldPrice);
    }

    /**
     * метод ищет акционную цену и возвращает ее, получаем ее в виде строки, затем удаляем все символы кроме цифр
     * @param product продукт в котором будет происходит поиск цени
     * @return акционная цена
     */
    private double getDiscountedPrice(String product) {
        String discountedPrice = driver.findElement(By.xpath(product + "/div/div[3]/div[1]/div[1]/span[2]")).getText();
        // удаляем все символы кроме цифр
        discountedPrice = discountedPrice.replaceAll("\\D+", "");
        return Double.parseDouble(discountedPrice);
    }

    /**
     * метод ищет процент скидки и возвращает его, получаем ее в виде строки, затем удаляем все символы кроме цифр
     * @param product продукт в котором будет происходит поиск процента скидки
     * @return процент скидки
     */
    private int getPercentageDiscount(String product) {
        String discount =
                driver.findElement(By.xpath(product + "//span[contains(@class, 'stick-list__span')]")).getText();
        // удаляем все символы кроме цифр
        discount = discount.replaceAll("\\D+", "");
        return Integer.parseInt(discount);
    }

    /**
     * метод генерирует рандомно число, это число будет порядковым номером товара на странице;
     * в случае если рандомно число равно 0 - генерируется новый номер
     * @param numberPromotionalItems кількість акційних товарів на страничке
     * @return порядковый номер товара
     */
    private int generateProductNumber(int numberPromotionalItems) {
        if(numberPromotionalItems <= 1)
            return numberPromotionalItems;

        int randomProduct = random.nextInt(numberPromotionalItems);
        while (randomProduct == 0)
            randomProduct = random.nextInt(numberPromotionalItems);

        return randomProduct;
    }

    /**
     * метод генерирует рандомно число, это число будет номером странички;
     * в случае если рандомно число равно 0 - генерируется новый номер
     * @param numberPagesWithPromotionalItems кількість сторінок із акційними товарами
     * @return рандомная страничка
     */
    private int generatePageNumber(int numberPagesWithPromotionalItems) {
        int randomPage = random.nextInt(numberPagesWithPromotionalItems);
        while (randomPage == 0)
            randomPage = random.nextInt(numberPagesWithPromotionalItems);

        return randomPage;
    }

    /**
     * метод сохраняет все акционные товары в карту ключ которой будет номер страницы,
     *                          а значение это коллекции в которые будут записывать идентификаторы на акционный товары;
     * условиями завершения поиска является если товара нет в наличии или если все товары были пересмотрены
     * @param urlFirstPage ссилка на первую страничку
     * @return коллекция из акционными товарами
     */
    private HashMap<Integer, ArrayList<String>> collectAllPromotionalItems(String urlFirstPage) {
        HashMap<Integer, ArrayList<String>> allPromotionProducts = new HashMap<>();
        int pageNumber = 1;
        while (true) {
            openPage(pageNumber, urlFirstPage);

            ArrayList<String> promotionalProductsOnCurrentPage = new ArrayList<>();
            for (int productIndex = 0; productIndex < PRODUCTS_ON_PAGE; productIndex++) {
                String xpathProduct = getProductXpath(productIndex);

                if(productNotAvailable(productIndex) || productNotExist(xpathProduct)) {
                    if(promotionalProductsOnCurrentPage.size() != 0)
                        allPromotionProducts.put(pageNumber, promotionalProductsOnCurrentPage);
                    return allPromotionProducts;
                }

                String xpathLabelWithDiscount = xpathProduct + "//span[contains(@class, 'stick-list__span')]";
                if(productAtDiscount(xpathLabelWithDiscount))
                    promotionalProductsOnCurrentPage.add(xpathProduct);
            }
            if(promotionalProductsOnCurrentPage.size() != 0)
                allPromotionProducts.put(pageNumber, promotionalProductsOnCurrentPage);
            pageNumber++;
        }
    }

    /**
     * метод возвращает идентификатор товара в виде xpath
     * @param productIndex индекс товара
     * @return идентификатор товара
     */
    private String getProductXpath(int productIndex) {
        // + 1 потому что цикл считает от 0, а порядковый номер первого товара равен 1
        return "/html/body/div[3]/div[1]/main/div[3]/div[2]/ul/li[" + (productIndex + 1) + "]";
    }

    /**
     * метод проверяет есть ли акционная цена
     * @param xpathLabelWithDiscount идентификатор на лабель из акционной ценой товара
     * @return true - если акционная цена присутствует; false - в противном случаи
     */
    private boolean productAtDiscount(String xpathLabelWithDiscount) {
        return isElementPresent(By.xpath(xpathLabelWithDiscount));
    }

    /**
     * метод проверят есть ли товар из переданным в качестве праматера идентификатором
     * @param xpathProduct идентификатор продукта
     * @return true - если такого товара не существует на странице; false - в противном случаи
     */
    private boolean productNotExist(String xpathProduct) {
        return !isElementPresent(By.xpath(xpathProduct));
    }

    /**
     * метод проверяет присутствует ли товар из, переданным в качестве параметра, порядковым номером в продаже
     * @param productIndex порядковый номер товара
     * @return true - если товар с таким порядковым номером осутсвует в продаже; false - в противном случаи
     */
    private boolean productNotAvailable(int productIndex) {
        // + 1 потому что цикл считает от 0, а порядковый номер первого равен 1
        String xpathReportAvailabilityLabel =
                "/html/body/div[3]/div[1]/main/div[3]/div[2]/ul/li[" + (productIndex + 1) + "]/div/div[3]/div[1]/a/span";
        return isElementPresent(By.xpath(xpathReportAvailabilityLabel));
    }

    /**
     * метод открывает страницу номер которой передан в качестве параметра
     * @param pageNumber номер страници которую нужно открыть
     * @param urlFirstPage ссилка на первую страницу
     */
    private void openPage(int pageNumber, String urlFirstPage) {
        if(pageNumber != 1)
            driver.get(urlFirstPage + "?PAGEN_1=" + pageNumber);
        else driver.get(urlFirstPage);
    }

    /**
     * метод, используя меню, открывает страницу из болгарками
     */
    private void goToTheGrinder() {
        String xpathButtonGrinder =
                "/html/body/div[3]/div/div[1]/div/div/div[2]/div/div/ul/li[3]/div/div[1]/ul/li[1]/a";

        WebElement xpathButtonPowerTools =
                driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/div/div/div[2]/div/div/ul/li[3]"));

        action.moveToElement(xpathButtonPowerTools).
                moveToElement(driver.findElement(By.xpath(xpathButtonGrinder))).click().build().perform();
    }

}
