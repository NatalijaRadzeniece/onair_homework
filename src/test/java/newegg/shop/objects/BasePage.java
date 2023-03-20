package newegg.shop.objects;

import newegg.shop.objects.helpers.Randomizer;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BasePage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By cookies = By.cssSelector(".osano-cm-accept-all");
    private final By regionsDialog = By.cssSelector(".dialog.dialog-buttons-vertical");
    private final By regionCloseBtn = By.cssSelector(".dialog-close");
    private final By headerText = By.cssSelector("h1");
    private final By banner = By.cssSelector(".modal-content");
    private final By bannerCloseBtn = By.cssSelector(".close");

    public BasePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public WebElement waitElementToBeVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement getRandomListElement(List<WebElement> elements) {
        assertTrue(elements.size() != 0);
        return elements.get(Randomizer.geInt(elements.size()));
    }

    public List<WebElement> getElements(final By locator) {
        return driver.findElements(locator);
    }

    public void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void acceptCookies() {
        waitElementToBeVisible(cookies).click();
    }

    public void closeRegionDialogIfPresent() {
        if (!getElements(regionsDialog).isEmpty()) driver.findElement(regionCloseBtn).click();
    }

    public String getPageHeaderText() {
        return waitElementToBeVisible(headerText).getText().toLowerCase().replace("\"", "");
    }

    public void closeBannerIfPresent() {
        try {
            waitElementToBeVisible(banner);
            waitElementToBeVisible(bannerCloseBtn).click();
        } catch (TimeoutException ignored) {
            System.out.println("no banner presented");
        }
    }
}
