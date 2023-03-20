package newegg.shop.tests;

import newegg.shop.objects.BasePage;
import newegg.shop.objects.components.CartRightPopUp;
import newegg.shop.objects.components.Header;
import newegg.shop.objects.components.Item;
import newegg.shop.objects.pageObjects.CartPage;
import newegg.shop.tests.setup.Setup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Cart total price: happy path")
public class CartTotalPriceTest extends Setup {
    private BasePage basePage;
    private Header header;
    private Item item;
    private CartRightPopUp cartRightPopUp;
    private CartPage cartPage;

    @Test
    @DisplayName("Add items to cart and check total price calculated correctly")
    public void cartTotalPriceTest() throws ParseException {
        navigateTo("https://www.newegg.com/Car-Subwoofers/SubCategory/ID-708");

        basePage = new BasePage(driver);
        basePage.acceptCookies();
        basePage.closeBannerIfPresent();
        basePage.closeRegionDialogIfPresent();

        item = new Item(driver);
        item.addRandomItemToCart();
        Double itemOnePrice = item.getRandomItemPrice();
        String itemOneInfo = item.getItemInfo();

        cartRightPopUp = new CartRightPopUp(driver);
        cartRightPopUp.closeModal();

        header = new Header(driver);
        Double cartOneItemPrice = header.getCartTotalPrice();

        assertAll(
                () -> assertEquals(header.getCartItemCount(), "1"),
                () -> assertEquals(cartOneItemPrice, itemOnePrice));

        navigateTo("https://www.newegg.com/Massagers/SubCategory/ID-737?Tid=9454");

        item.addRandomItemToCart();
        Double itemTwoPrice = item.getRandomItemPrice();
        String itemTwoInfo = item.getItemInfo();

        cartRightPopUp.closeModal();
        Double cartTotalItemPrice = header.getCartTotalPrice();

        Double totalPrice = itemOnePrice + itemTwoPrice;

        assertAll(
                () -> assertEquals(header.getCartItemCount(), "2"),
                () -> assertEquals(cartTotalItemPrice, totalPrice));

        header.clickCart();

        cartPage = new CartPage(driver);
        Double endPrice = totalPrice + cartPage.geDeliveryPrice();

        assertAll(
                () -> assertTrue(cartPage.getCartItemsInfo().containsKey(itemOneInfo)),
                () -> assertTrue(cartPage.getCartItemsInfo().containsKey(itemTwoInfo)),
                () -> assertEquals(cartPage.getCartItemsInfo().get(itemOneInfo), itemOnePrice),
                () -> assertEquals(cartPage.getCartItemsInfo().get(itemTwoInfo), itemTwoPrice),
                () -> assertEquals(totalPrice, cartPage.getItemsPrice()),
                () -> assertEquals(endPrice, cartPage.geCartTotalPrice())
        );
    }
}