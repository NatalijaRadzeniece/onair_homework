package newegg.shop.tests.setup;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Setup {
    private static final Logger LOG = LoggerFactory.getLogger(Setup.class);
    private static final String CHROME_DRIVER = "webdriver.chrome.driver";
    private static final String PATH_TO_DRIVER = System.getProperty("user.dir");
    private static String SYSTEM_OS = System.getProperty("os.name");
    public WebDriver driver;

    @BeforeAll
    public static void setChromePath() {
        setPathBasedOnOs();
    }

    @BeforeEach
    public void createDriver() {
        LOG.info("Setting new Chrome driver");
        ChromeOptions option = new ChromeOptions();
//        Due https://github.com/SeleniumHQ/selenium/issues/11750 issue needed this argument.
        option.addArguments("--remote-allow-origins=*");
        option.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(option);
    }

    @AfterEach
    public void closeDriver() {
        if (driver != null) {
            driver.close();
            driver.quit();
        }
        LOG.info("Chrome driver closed");
    }

    public void navigateTo(final String url) {
        driver.get(!url.startsWith("https://") ? "https://" + url : url);
    }

    private static void setPathBasedOnOs() {
        if (SYSTEM_OS.contains("Mac"))
            System.setProperty(CHROME_DRIVER, PATH_TO_DRIVER + "/src/main/resources/chromedriver");
        else if (SYSTEM_OS.contains("Windows"))
            System.setProperty(CHROME_DRIVER, PATH_TO_DRIVER + "/src/main/resources/chromedriver.exe");
        else throw new IllegalStateException("Unexpected OS property: " + SYSTEM_OS);

        LOG.info("Setting Chrome path for " + SYSTEM_OS);
    }
}
