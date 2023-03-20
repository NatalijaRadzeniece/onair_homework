package newegg.shop.objects.helpers;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class CurrencyConverter {

    public static Double getPriceNumber(final String price) throws ParseException {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        return  format.parse(price).doubleValue();
    }
}
