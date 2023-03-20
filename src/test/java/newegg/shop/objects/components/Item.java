package newegg.shop.objects.components;

import newegg.shop.objects.BasePage;
import newegg.shop.objects.helpers.CurrencyConverter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.ParseException;
import java.util.NoSuchElementException;

public class Item extends BasePage {

    public Item(WebDriver driver) {
        super(driver);
    }

    public final By item = By.cssSelector(".item-cell");
    public final By itemPrice = By.cssSelector(".price-current");
    private final By itemInfo = By.cssSelector(".item-title");
    private final By itemCartBtn = By.cssSelector(".btn.btn-primary.btn-mini");

    private WebElement randomItem;

    public void addRandomItemToCart() {
        randomItem = selectItemWithCartBtn();
        scrollIntoView(randomItem);
        randomItem.findElement(itemCartBtn).click();
    }

    public Double getRandomItemPrice() throws ParseException {
        return CurrencyConverter.getPriceNumber(randomItem.findElement(itemPrice).getText());
    }

    public String getItemInfo() {
        return randomItem.findElement(itemInfo).getText();
    }

    private WebElement selectItemWithCartBtn() {
        return getElements(item).stream()
                .filter(el -> el.findElement(itemCartBtn).isDisplayed())
                .filter(element -> element.findElement(itemPrice).isDisplayed())
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("Could not find item on page with 'add to cart' button and price"));
    }
}