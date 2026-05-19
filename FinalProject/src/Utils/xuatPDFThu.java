package Utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

public class xuatPDFThu {

    // ===== FORMAT TIỀN =====
    private static String formatTien(double soTien) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
        return nf.format(soTien) + " VNĐ";
    }

    // ===== ĐỔI SỐ -> CHỮ =====
    private static String tienBangChu(double soTien) {
        long tien = (long) soTien;
        if (tien == 0) return "Không đồng";

        String[] donvi = {"", " nghìn", " triệu", " tỷ"};
        String result = "";
        int i = 0;

        while (tien > 0) {
            int part = (int) (tien % 1000);
            if (part > 0) {
                result = part + donvi[i] + " " + result;
            }
            tien /= 1000;
            i++;
        }
        return result.trim() + " đồng";
    }

    // ===== XUẤT PHIẾU THU =====
    public static void xuatPhieuThu(
            String ma,
            String loai,
            String nguoiNop,
            LocalDate ngay,
            double soTien,
            String ghiChu,
            String path
    ) {
        try {
            Rectangle pageSize = new Rectangle(350, 520);
            Document document = new Document(pageSize, 25, 25, 20, 20);
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();

            // ===== FONT =====
            BaseFont bf = BaseFont.createFont(
                    "C:/Windows/Fonts/arial.ttf",
                    BaseFont.IDENTITY_H,
                    BaseFont.EMBEDDED
            );

            Font titleFont = new Font(bf, 16, Font.BOLD);
            Font textFont = new Font(bf, 11);
            Font boldFont = new Font(bf, 11, Font.BOLD);
            Font italicFont = new Font(bf, 10, Font.ITALIC);

            // ===== LOGO =====
            Image logo = Image.getInstance(
                    xuatPDFThu.class.getResource("toplenn.jpeg")
            );
            logo.scaleAbsolute(70, 70);
            logo.setAlignment(Image.ALIGN_CENTER);
            document.add(logo);

            document.add(new Paragraph(" "));

            // ===== TITLE =====
            Paragraph title = new Paragraph("PHIẾU THU", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" "));

            // ===== CONTENT =====
            document.add(new Paragraph("Mã GD: " + ma, textFont));
            document.add(new Paragraph("Loại: " + loai, textFont));
            document.add(new Paragraph("Người nộp: " + nguoiNop, textFont));
            document.add(new Paragraph("Ngày: " + ngay, textFont));

            document.add(new Paragraph("Số tiền: " + formatTien(soTien), boldFont));
            document.add(new Paragraph("Bằng chữ: " + tienBangChu(soTien), italicFont));

            document.add(new Paragraph("Ghi chú: " + ghiChu, textFont));

            document.add(new Paragraph("\n\n"));

            // ===== KÝ =====
            Paragraph ky = new Paragraph("Người thu", textFont);
            ky.setAlignment(Element.ALIGN_RIGHT);
            document.add(ky);

            document.add(new Paragraph("\n\n\n"));

            Paragraph ky2 = new Paragraph("(Ký và ghi rõ họ tên)", italicFont);
            ky2.setAlignment(Element.ALIGN_RIGHT);
            document.add(ky2);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
