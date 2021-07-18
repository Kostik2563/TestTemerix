package ua.com.kulibin;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import ua.com.kulibin.testSettings.OtherSetting;

import java.util.Random;

public class WebDriverSettings implements OtherSetting {

    public ChromeDriver driver;
    public Actions action;
    public Random random;

    /**
     * все ниже описание действия будут исполняться перед запуском каждого из тестов
     */
    @BeforeTest
    public void actionBeforeTest() {
        System.setProperty("webdriver.chrome.driver", "/Users/Kostik/Downloads/chromedriver.exe");

        driver = new ChromeDriver();
        action = new Actions(driver);
        random = new Random();

        driver.manage().window().maximize();
        driver.get(URL_HOME);
    }

    /**
     * все ниже описание действия будут исполняться после окончания каждого из тестов
     */
    @AfterTest
    public void actionsAfterTest() {
        driver.quit();
    }

}
