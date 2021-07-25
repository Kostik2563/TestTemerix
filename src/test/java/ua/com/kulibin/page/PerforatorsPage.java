package ua.com.kulibin.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class PerforatorsPage extends BasePage {

    // секция Перфораторы в секции Эллетроинструмент
    @FindBy (css = "ul.sub-nav>li>div.sub-drop.drop1 div.col [href $= 'perforatory/']")
    private WebElement sectionPerforators;

    // конструктор
    public PerforatorsPage(WebDriver driver) {
        super(driver);
        action = new Actions(driver);
    }

    /**
     * метод, используя меню, открывает страницу из дрелями; после этого проверяеться тот ли раздел открыт
     */
    public void openSection() {
        productCatalogButton.click();
        action.moveToElement(sectionPowerTools).moveToElement(sectionPerforators).click().build().perform();
        linkFirstPage = getPageUrl();
        checkTitle("Перфоратор");
    }

    /**
     * метод открывает страницу номер которой передан в виде параметра; после этого проверяеться тот ли раздел открыт
     * @param numberPage номер страницы
     */
    public void openPage(int numberPage) {
        openPage(linkFirstPage, numberPage);
        checkTitle("Перфоратор");
    }

    /**
     * метод проходиться по товарах и смотрит присутствуют ли цены на всех товарах открытой страницы,
     * если на каком-то товаре цена не найдена - на экран выводиться его наименование
     */
    public void checkPricesOnPage() {
        int numberProducts = getNumberOfProducts();
        for (int i = 1; i <= numberProducts; i++) {
            Assert.assertNotEquals("", getActualPriceOfProductByIndex(i),
                    "Актуальная цена не найдена -> " + getProductName(i));
        }
    }
}
