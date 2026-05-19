package utils;

import java.text.NumberFormat;
import java.util.Locale;

public class NumberUtils {
    private static final NumberFormat VND_FORMAT = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    
    public static String formatVND(double amount) {
        return VND_FORMAT.format(amount);
    }
    
    public static double parseVND(String str) {
        try {
            String number = str.replace("₫", "").replace(".", "").replace(",", "").trim();
            return Double.parseDouble(number);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}