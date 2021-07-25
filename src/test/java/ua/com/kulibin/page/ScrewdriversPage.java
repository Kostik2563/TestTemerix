package ua.com.kulibin.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import ua.com.kulibin.testSettings.ThirdTestSetting;

public class ScrewdriversPage extends BasePage implements ThirdTestSetting {

    // секция Шуроповерты в секции Эллетроинструмент
    @FindBy(css = "ul.sub-nav>li>div.sub-drop.drop1 div.col [href $= 'shurupoverty/']")
    private WebElement sectionScrewdrivers;

    //конструктор
    public ScrewdriversPage(WebDriver driver) {
        super(driver);
        action = new Actions(driver);
    }

    /**
     * метод, используя меню, открывает страницу из шуроповертамы; после этого проверяеться тот ли раздел открыт
     */
    public void openSection() {
        productCatalogButton.click();
        action.moveToElement(sectionPowerTools).moveToElement(sectionScrewdrivers).click().build().perform();
        linkFirstPage = getPageUrl();
        checkTitle("Шуруповерты");
    }

    /**
     * метод открывает страницу номер которой передан в виде параметра, после проверяет страница того ли раздела открыта
     * @param numberPage номер страницы
     */
    public void openPage(int numberPage) {
        openPage(linkFirstPage, numberPage);
        checkTitle("Шуруповерты");
    }

    /**
     * метод проходиться по товарам на странице и смотрит на флаг производителя, если наименование странны така же
     *                           как указано в настройках данного теста, то выводим на экран наименование продукта
     */
    public void checkFlagProduct() {
        int numberProducts = getNumberOfProducts();
        for (int i = 1; i <= numberProducts; i++) {
            if(checkProductFlag(i, NAME_THE_FLAG))
                System.out.println("Product name -> " + getProductName(i));
        }
    }

}
