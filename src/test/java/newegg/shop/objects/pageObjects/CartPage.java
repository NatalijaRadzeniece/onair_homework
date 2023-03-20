package newegg.shop.objects.pageObjects;

import newegg.shop.objects.components.Item;
import newegg.shop.objects.helpers.CurrencyConverter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class CartPage extends Item {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    private final By itemInfo = By.cssSelector(".item-title");
    private final By itemPrice = By.cssSelector(".price-current");

    private final By itemsPrice = By.xpath("//*[text()='Item(s):']/..//span");
    private final By itemsDeliveryPrice = By.xpath("//*[text()='Est. Delivery:']/..//span//strong/..");

    private final By cartTotalPrice = By.cssSelector(".summary-content-total span");

    private Double getItemPrice(WebElement item) throws ParseException {
        return CurrencyConverter.getPriceNumber(item.findElement(itemPrice).getText());
    }

    public Double getItemsPrice() throws ParseException {
        return CurrencyConverter.getPriceNumber(waitElementToBeVisible(itemsPrice).getText());
    }

    public Double geCartTotalPrice() throws ParseException {
        return CurrencyConverter.getPriceNumber(waitElementToBeVisible(cartTotalPrice).getText());
    }

    public Double geDeliveryPrice() throws ParseException {
        return CurrencyConverter.getPriceNumber(waitElementToBeVisible(itemsDeliveryPrice).getText());
    }

    private String getItemInfo(WebElement item) {
        return item.findElement(itemInfo).getText();
    }

    public Map<String, Double> getCartItemsInfo() throws ParseException {
        Map<String, Double> cartItemInfo = new HashMap<>();
        waitElementToBeVisible(item);

        for (WebElement el : getElements(item)) {
            cartItemInfo.put(getItemInfo(el), getItemPrice(el));
        }
        return cartItemInfo;
    }
}