package ua.com.kulibin.page;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class DrillsPage extends BasePage {

    // секция Дрели в секции Эллетроинструмент
    @FindBy (css = "ul.sub-nav>li>div.sub-drop.drop1 div.col [href $= 'dreli/']")
    private WebElement sectionDrills;

    // конструктор
    public DrillsPage(WebDriver driver) {
        super(driver);
        action = new Actions(driver);
    }

    /**
     * метод, используя меню, открывает страницу из дрелями; после этого проверяеться тот ли раздел открыт
     */
    public void openSection() {
        productCatalogButton.click();
        action.moveToElement(sectionPowerTools).moveToElement(sectionDrills).click().build().perform();
        linkFirstPage = getPageUrl();
        checkTitle("Дрель");
    }

    /**
     * метод открывает страницу на которой есть хотя бы один акционный товар
     *                                                      и проверяет соответствует ли открыт товар нужной категории
     * @param numberPages количество страниц
     */
    public void openPageWithPromotionalProducts(int numberPages) {
        openRandomPageWithPromotionalItems(numberPages);
        checkTitle("Дрель");
    }

    /**
     * метод проваливается в карту рандомно выбраного товара и проверяет соответствует ли открыт товар нужной категории
     */
    public void fallInRandomProductCard() {
        int indexPromotionalProduct = getIndexPromotionalProduct(getNumberOfProducts());
        openProduct(indexPromotionalProduct);
        checkTitle("Дрель");
    }

    /**
     * метод возвращаеться на предыдущую страницу
     */
    public void goBack() {
        returnToThePreviousPage();
    }

}