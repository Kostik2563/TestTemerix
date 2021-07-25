package ua.com.kulibin.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import ua.com.kulibin.testSettings.FourthTestSetting;

public class GrindersPage extends BasePage implements FourthTestSetting {

    // секция Болгарки в секции Эллетроинструмент
    @FindBy(css = "ul.sub-nav>li>div.sub-drop.drop1 div.col [href $= 'bolgarki/']")
    private WebElement sectionGrinder;

    // конструктор
    public GrindersPage(WebDriver driver) {
        super(driver);
        action = new Actions(driver);
    }

    /**
     * метод, используя меню, открывает страницу из шуроповертамы; после этого проверяеться тот ли раздел открыт
     */
    public void openSection() {
        productCatalogButton.click();
        action.moveToElement(sectionPowerTools).moveToElement(sectionGrinder).click().build().perform();
        linkFirstPage = getPageUrl();
        checkTitle("Болгарка");
    }

    /**
     * метод открывает рандомную страницу на которой есть хоть один акционный товар
     *                                                   и проверяет соответствует ли открыта страница нужному разделу
     * @param pageNumbers количество страниц
     */
    public void openPageWithPromotionalProducts(int pageNumbers) {
        openRandomPageWithPromotionalItems(pageNumbers);
        checkTitle("Болгарка");
    }

    /**
     * метод открывает рандомный акционный товар и проверяет соответствует ли открыта страница нужному разделу
     */
    public void openPromotionalProduct() {
        int indexPromotionalProduct = getIndexPromotionalProduct(getNumberOfProducts());
        openProduct(indexPromotionalProduct);
        checkTitle("шлифмашина");
    }

    /**
     * метод делает расчёт акционной цены относительно старой используя процент скидки,
     * если ожидаемая цена не соответствует актуальной, то выводим наименование товара
     */
    public void calculatePromotionalPrice() {
        double oldPrice = parsePrice(getOldPrice());
        double actualPrice = parsePrice(getActualPrice());
        int percentageDiscount = parsePercentage(getPercentageDiscount());
        double expectedPrice = oldPrice - (oldPrice / ONE_HUNDRED * percentageDiscount);
        Assert.assertEquals(actualPrice, expectedPrice,
                "Актуальная цена не соответствует ожидаемой -> " + getProductName());
    }

    /**
     * метод парсит строку из процентом скидки, отбрасывая буквы/пробелы/точки и т.д и возвращает целое число
     * @param percentageDiscount процент скидки в виде строки
     * @return целое число которое является процентом скидки
     */
    private int parsePercentage(String percentageDiscount) {
        return Integer.parseInt(percentageDiscount.replaceAll("\\D+","")); // удаляем всё кроме цифр
    }

    /**
     * метод парсит строку из ценой, отбрасывая буквы/пробелы/точки и т.д и возвращает целое число
     * @param price цена в виде строки
     * @return целое число которое является ценой
     */
    private double parsePrice(String price) {
        String priceInFormString = price.
                replaceAll("[а-я . s]",""). // удаляем все буквы точки и пробелы
                replace(',','.'); // делаем замену запятой на точку
        return Double.parseDouble(priceInFormString); // приводимо строку в число
    }

}
