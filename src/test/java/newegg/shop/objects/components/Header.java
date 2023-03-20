package newegg.shop.objects.components;

import newegg.shop.objects.BasePage;
import newegg.shop.objects.helpers.CurrencyConverter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Header extends BasePage {

    public Header(WebDriver driver) {
        super(driver);
    }

    private final By search = By.cssSelector("[type='search']");
    private final By cart = By.cssSelector(".header2021-cart");
    private final By cartItemCount = By.cssSelector(".text-gray.font-s span");
    private final By cartTotalPrice = By.cssSelector(".header2021-nav-subtitle");
    private final By searchOptions = By.cssSelector(".tag-primary");

    private String searchOptionText;

    private String getSearchFieldText() {
        return waitElementToBeVisible(search).getAttribute("value");
    }

    public void clickRandomSearchOption() {
        WebElement searchOption;
        waitElementToBeVisible(search).click();
        waitElementToBeVisible(searchOptions); // waits for 1st element in list would be visible
        searchOption = getRandomListElement(getElements(searchOptions));

        searchOptionText = searchOption.getText();
        searchOption.click();
    }

    public void checkSearchOptionResult() {
        assertAll(
                () -> assertEquals(searchOptionText, getSearchFieldText(),
                        "Search field text is " + getSearchFieldText() + ", but should be: " + searchOptionText),
                () -> assertEquals(searchOptionText, getPageHeaderText(),
                        "Page title is " + getPageHeaderText() + ", but should be: " + searchOptionText));
    }

    public void clickCart() {
        waitElementToBeVisible(cart).click();
    }

    public String getCartItemCount() {
        return waitElementToBeVisible(cart).findElement(cartItemCount).getText();
    }

    public Double getCartTotalPrice() throws ParseException {
        return CurrencyConverter.getPriceNumber(waitElementToBeVisible(cart).findElement(cartTotalPrice).getText());
    }
}