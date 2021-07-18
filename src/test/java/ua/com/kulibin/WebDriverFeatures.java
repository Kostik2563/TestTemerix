package ua.com.kulibin;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Locatable;

public class WebDriverFeatures extends WebDriverSettings{
    /**
     * метод возвращает веб-драйвер на предыдущую страницу
     */
    public void returnToThePreviousPage() {
        driver.navigate().back();
    }

    /**
     * метод скролит к товару, это понадобится если выбран товар находить за пределами страницы, без этого метода
     * программа будет падать, так как драйвер не будет знать на что нажымать
     * @param product товар который был рандомно выбран
     */
    public void scrollToProduct(WebElement product) {
        Locatable hoverProduct = ((Locatable)product);
        int y = hoverProduct.getCoordinates().onPage().y;
        ((JavascriptExecutor)driver).executeScript("window.scrollBy(0, " + y + ");");
    }

    /**
     * метод возвращает true - если передан элемент с таким идентификатором
     * в параметрах присутствует на странице; false - иначе
     * @param idElement идентификатор элемента
     * @return true - если элемент с переданым идентификатором существует; false - в противном случаи
     */
    public boolean isElementPresent(By idElement) {
        try {
            driver.findElement(idElement);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
