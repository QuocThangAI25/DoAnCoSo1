package util;

import java.text.NumberFormat;
import java.util.Locale;

public class NumberUtils {
    private static final NumberFormat VND_FORMAT = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    public static String formatVND(double amount) {
        return VND_FORMAT.format(amount);
    }

    /** Giá dạng số thuần để hiển thị trong ô nhập liệu (không ký hiệu tiền tệ). */
    public static String formatPlainAmount(double amount) {
        if (amount == (long) amount) {
            return String.valueOf((long) amount);
        }
        return String.valueOf(amount);
    }

    public static double parseVND(String str) {
        try {
            String number = str
                    .replace("₫", "")
                    .replace(".", "")
                    .replace(",", "")
                    .replace("\u00a0", "")
                    .replaceAll("\\s+", "")
                    .trim();
            return Double.parseDouble(number);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
