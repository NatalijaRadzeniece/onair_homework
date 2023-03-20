package newegg.shop.tests;

import newegg.shop.objects.BasePage;
import newegg.shop.objects.components.Header;
import newegg.shop.tests.setup.Setup;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Hot Search: happy path")
public class HotSearchNavigationTest extends Setup {
    private Header header;
    private BasePage basePage;

    @Test
    @DisplayName("Hot Search: hot options navigation")
    public void searchOptionsTest() {
        navigateTo("https://www.newegg.com/");
        basePage = new BasePage(driver);
        basePage.acceptCookies();
        basePage.closeRegionDialogIfPresent();
        basePage.closeBannerIfPresent();

        header = new Header(driver);
        header.clickRandomSearchOption();
        header.checkSearchOptionResult();
    }
}