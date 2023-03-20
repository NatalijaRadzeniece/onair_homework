package newegg.shop.objects.components;

import newegg.shop.objects.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartRightPopUp extends BasePage {

    public CartRightPopUp(WebDriver driver) {
        super(driver);
    }

    private final By closeBtn = By.cssSelector(".close");

    public void closeModal() {
        waitElementToBeVisible(closeBtn).click();
    }
}