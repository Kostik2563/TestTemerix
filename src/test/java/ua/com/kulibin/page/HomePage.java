package ua.com.kulibin.page;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import ua.com.kulibin.testSettings.OtherSetting;

public class HomePage implements OtherSetting {
    // инициализация драйвера
    private final WebDriver driver;
    // конструктор
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * открываеться главная страница сайта
     */
    public void open() {
        driver.get(URL_HOME);
        Assert.assertEquals(driver.getCurrentUrl(), URL_HOME, "The wrong page is open section.");
    }
}
