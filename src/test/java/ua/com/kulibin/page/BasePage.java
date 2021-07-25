package ua.com.kulibin.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class BasePage extends ProductPage {

    // ссилка на первую страницу, нужна для перехода на страницы номер которых больше 1
    public String linkFirstPage;

    // локатор кнопки "Каталог товаров"
    @FindBy(css = ".catalog-nav")
    public WebElement productCatalogButton;

    // секция Электроинструмент в каталоге товаров
    @FindBy (css = "ul.sub-nav>li>[href *= 'elektroinstrument']")
    public WebElement sectionPowerTools;

    // элемент перехода на последнюю страницу
    @FindBy(css = ".paging ul li:last-child a")
    private WebElement numberOfPages;

    // путь к странице
    String pathPage = "?PAGEN_1=";

    // локатор для поиска количества товаров на странице
    By numberOfProducts = By.cssSelector(".catalog.catalog-full.js-catalog>li");

    // конструктор
    public BasePage(WebDriver driver) {
        super(driver);
    }

    /**
     * метод открывает сраницу номер который передан параметром
     * @param linkFirstPage ссилка на первую страницу
     * @param numberPage номер страницы которую нужно открыть
     */
    public void openPage(String linkFirstPage, int numberPage) {
        driver.get(linkFirstPage + pathPage + numberPage);
    }

    /**
     * метод возвращает целое число, это число будет количеством страниц из товарами
     * @return количество страниц из товарами
     */
    public int getNumberOfPages() {
        return Integer.parseInt(numberOfPages.getText());
    }

    /**
     * метод возвращает количество товаров на странице, сразу проверяя что оно не равно 0
     * @return количество продуктов на странице
     */
    public int getNumberOfProducts() {
        int number = driver.findElements(numberOfProducts).size();
        Assert.assertTrue(number != 0);
        return number;
    }

    /**
     * @return true - если на странице есть акционные товари false - в противном случаи
     */
    public boolean pageHasPromotionalProducts() {
        return driver.findElements(promotionalTicket).size() > 0;
    }

    /**
     * @return процент скидки
     */
    public String getPercentageDiscount() {
        return driver.findElement(promotionalTicket).getText();
    }

    /**
     * метод возвращает наименование страницы
     * @return наименование страницы
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * метод возвращает ссилку на страницу
     * @return ссилку на страницу
     */
    public String getPageUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * метод проверяет название текущей страницы
     */
    public void checkTitle(String title) {
        Assert.assertTrue(getPageTitle().contains(title), "Открыта неверная страница.");
    }

    /**
     * метод открывает рандомную страницу и проверяет есть ли на ней акционные товары, если нет то с помощью рекурсии
     * открываеться следуящая рандомная страница, при этом рандом не сможет дать больше число чем предыдущее
     * @param pageNumbers количество страниц из товарами этого раздела
     */
    public void openRandomPageWithPromotionalItems(int pageNumbers) {
        int randomPage = randomPageNumber(pageNumbers);
        openPage(linkFirstPage, randomPage);
        if(!pageHasPromotionalProducts())
            openRandomPageWithPromotionalItems(randomPage);
    }


    /**
     * метод рандомно ищёт акционный товар и возвращает его порядковый номер
     * @param numberProducts количество товаров на странице
     * @return порядковый номер
     */
    public int getIndexPromotionalProduct(int numberProducts) {
        int indexProduct = randomSequenceNumberProduct(numberProducts);
        while (promotionalTicket(indexProduct) == 0)
            indexProduct = randomSequenceNumberProduct(numberProducts);

        return indexProduct;
    }

}
