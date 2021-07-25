package ua.com.kulibin;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import ua.com.kulibin.page.*;
import ua.com.kulibin.testSettings.OtherSetting;

public class WebDriverSettings extends NumberGenerator implements OtherSetting {

    public ChromeDriver driver;
    public Actions action;

    public DrillsPage drillsPage;
    public PerforatorsPage perforatorsPage;
    public ScrewdriversPage screwdriversPage;
    public GrindersPage grindersPage;

    /**
     * все ниже описание действия будут исполняться перед запуском каждого из тестов
     */
    @BeforeTest
    public void start() {
        System.setProperty("webdriver.chrome.driver", "/Users/Kostik/Downloads/chromedriver.exe");

        driver = new ChromeDriver();
        action = new Actions(driver);

        driver.manage().window().maximize();

        HomePage homePage = new HomePage(driver);
        homePage.open(); // открывается главная страница

        init();
    }

    /**
     * инициализация нужных страниц для тестов
     */
    private void init() {
        drillsPage = PageFactory.initElements(driver, DrillsPage.class);
        perforatorsPage = PageFactory.initElements(driver, PerforatorsPage.class);
        screwdriversPage = PageFactory.initElements(driver, ScrewdriversPage.class);
        grindersPage = PageFactory.initElements(driver, GrindersPage.class);
    }

    /**
     * все ниже описание действия будут исполняться после окончания каждого из тестов
     */
    @AfterTest
    public void stop() {
        driver.quit();
    }

}
