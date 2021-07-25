package ua.com.kulibin.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import ua.com.kulibin.WebDriverFeatures;

public class ProductPage extends WebDriverFeatures {

    // конструктор
    public ProductPage(WebDriver driver) {
        super(driver);
    }

    // цена до акции
    @FindBy(css = ".item_old_price.old-price")
    private WebElement oldPrice;

    // актуальная цена
    @FindBy(css = ".col-xs-8 .price")
    private WebElement currentPrice;

    // наименование товара
    @FindBy(css = ".row.fixed-wrap.catalog-item-detail [itemprop = 'name']")
    private WebElement labelName;

    // локатор лабеля из сообщением "Оповестить о наличии"
    By reportAvailability = By.cssSelector(".notification.icon-catalog_product4");

    // локатор тикета из процентом акции
    By promotionalTicket = By.cssSelector(".stick-list__span");

    // локатор регистрационной формы
    By registrationForm = By.cssSelector(".fancybox-opened");

    // кнопка закрытия регистрационной формы
    By buttonCloseRegistrationForm = By.cssSelector(".custom-close");

    /**
     * метод возвращает цену до акции
     * @return цена до акции
     */
    public String getOldPrice() {
        return oldPrice.getText();
    }

    /**
     * метод возвращает актуальную цену
     * @return актуальаная цена
     */
    public String getActualPrice() {
        return currentPrice.getText();
    }

    /**
     * метод проверяет если товар из переданным в качестве параметром порядковым номером на странице,
     * если есть то смотрит открыта ли форма для подписки, если да - закрывает её, скролит к продукту и открывает его
     * @param index порядковый номер товара на странице
     */
    public void openProduct(int index) {
        Assert.assertTrue(isElementPresent(getProduct(index)));
        WebElement product = driver.findElement(getProduct(index));
        closeRegistrationForm();
        scrollToProduct(product);
        product.click();
    }

    /**
     * метод закрывает регистрационную форму, после этого проверяет закрыта ли она
     */
    private void closeRegistrationForm() {
        if(isElementPresent(registrationForm))
            driver.findElement(buttonCloseRegistrationForm).click();
        Assert.assertFalse(isElementPresent(registrationForm),"Регистрационная форма не закрыта.");
    }

    /**
     * метод проверяет наличие актуальной цены, если её нет - выводится на экран наименование товара
     */
    public void checkCurrentPrice() {
        Assert.assertNotEquals(getActualPrice(),"", "Актуальная цена не найдена. " + getProductName());
    }

    /**
     * метод проверяет наличие цены до скидки, если её нет - выводится на экран наименование товара
     */
    public void checkOldPrice() {
        Assert.assertNotEquals(getOldPrice(), "", "Старая цена не найдена. " + getProductName());
    }

    /**
     * метод возвращает актуальную цену товара индекс которого перед в виде параметра,
     * этот метод нужен чтобы не проваливаясь в карту товара получить его цену
     * @param index порядковый номер товара
     * @return актуальная цена
     */
    public String getActualPriceOfProductByIndex(int index) {
        Assert.assertTrue(isElementPresent(By.cssSelector(getProductLocator(index) + " .price")),
                "Актуальная цена не найдена.");
        return driver.findElement(By.cssSelector(getProductLocator(index) + " .price")).getText();
    }

    /**
     * метод проверяет являться ли товар акционным
     * @param index порядковый номер товара
     * @return 1 - если товар екционный; 0 - в противном случаи
     */
    public int promotionalTicket(int index) {
        return driver.findElements(By.cssSelector(getProductLocator(index) + " .stick-list__span")).size();
    }

    /**
     * метод возврарщает наименование товара по индексу, это нужно в случаи если не нужно переходить в карту товара
     * @param index порядковый номер товара
     * @return наименование товара
     */
    public String getProductName(int index) {
        Assert.assertTrue(isElementPresent((By.cssSelector(getProductLocator(index) + " .s_title"))),
                "Наименование товара не указано.");
        return driver.findElement(By.cssSelector(getProductLocator(index) + " .s_title")).getText();
    }

    /**
     * @return наименование товара
     */
    public String getProductName() {
        return labelName.getText();
    }

    /**
     * @param index порядковый номер товара
     * @return локатор на флаг товара
     */
    public boolean checkProductFlag(int index, String nameFlag) {
        return isElementPresent
                (By.cssSelector(getProductLocator(index) + " .item-brand-country [src *= " + nameFlag + "]"));
    }

    /**
     * @param index порядковый номер товара
     * @return локатор на товар
     */
    public By getProduct(int index) {
        Assert.assertTrue(isElementPresent(By.cssSelector(getProductLocator(index) + " .holder")),
                "Не удалось найти товар с таким порядковым номером.");
        return By.cssSelector(getProductLocator(index) + " .holder");
    }

    /**
     * @param index порядковый номер товара
     * @return идентификатор товара
     */
    public String getProductLocator(int index) {
        return ".js-catalog>li:nth-child(" + index + ")";
    }

}
