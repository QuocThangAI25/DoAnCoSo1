package util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import model.ChiTietHoaDon;
import model.HoaDon;

import java.io.FileOutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PDFUtils {

    /**
     * Xuất hóa đơn ra file PDF (Có hỗ trợ in Voucher)
     */
    public static void exportHoaDonToPDF(HoaDon hoaDon, List<ChiTietHoaDon> chiTietList,
            String filePath, String phuongThuc, String tenNhanVien,
            String maVoucher, String qrUrl) { // 🟢 Đã thêm tham số Voucher
        try {
            // Tạo document với kích thước A4, set lề (trái, phải, trên, dưới)
            Document document = new Document(PageSize.A4, 30, 30, 50, 50);
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Lấy font chữ hỗ trợ tiếng Việt
            BaseFont baseFont = getBaseFont();

            // Các font chữ
            Font fontTitle = new Font(baseFont, 20, Font.BOLD, BaseColor.RED); // Làm tiêu đề nổi bật hơn
            Font fontHeader = new Font(baseFont, 14, Font.BOLD);
            Font fontNormal = new Font(baseFont, 12, Font.NORMAL);
            Font fontBold = new Font(baseFont, 12, Font.BOLD);
            Font fontItalic = new Font(baseFont, 10, Font.ITALIC);

            // ==================== HEADER ====================
            Paragraph title = new Paragraph("TVT - QUÁN MÌ CAY", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Paragraph subTitle = new Paragraph("HÓA ĐƠN THANH TOÁN", fontHeader);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle);
            document.add(new Paragraph(" "));

            // ==================== THÔNG TIN HÓA ĐƠN ====================
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(100);
            infoTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);

            infoTable.addCell(new Phrase("Ngày: " + sdf.format(new Date()), fontNormal));
            infoTable.addCell(new Phrase("Nhân viên: " + tenNhanVien, fontNormal));
            infoTable.addCell(new Phrase("Mã HĐ: HD" + hoaDon.getId(), fontNormal));

            if (!phuongThuc.equals("CHƯA THANH TOÁN")) {
                infoTable.addCell(new Phrase("P.Thức: " + phuongThuc, fontBold));
            } else {
                infoTable.addCell(new Phrase(" ", fontNormal));
            }
            document.add(infoTable);
            document.add(new Paragraph(" "));

            // ==================== BẢNG CHI TIẾT ====================
            PdfPTable table = new PdfPTable(4); // Rút xuống 4 cột cho đỡ chật giấy
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            float[] columnWidths = { 4f, 1f, 2f, 2f }; // Tên, SL, Đơn giá, Thành tiền
            table.setWidths(columnWidths);

            // Header bảng
            addTableHeader(table, "Tên món", fontHeader);
            addTableHeader(table, "SL", fontHeader);
            addTableHeader(table, "Đơn giá", fontHeader);
            addTableHeader(table, "Thành tiền", fontHeader);

            // Dữ liệu bảng
            for (ChiTietHoaDon ct : chiTietList) {
                addTableCell(table, ct.getTenMon(), fontNormal, Element.ALIGN_LEFT);
                addTableCell(table, String.valueOf(ct.getSoLuong()), fontNormal, Element.ALIGN_CENTER);
                addTableCell(table, NumberUtils.formatVND(ct.getDonGia()), fontNormal, Element.ALIGN_RIGHT);
                addTableCell(table, NumberUtils.formatVND(ct.getThanhTien()), fontNormal, Element.ALIGN_RIGHT);
            }
            document.add(table);

            // ==================== TỔNG TIỀN ====================
            document.add(new Paragraph(" "));

            PdfPTable totalTable = new PdfPTable(2);
            totalTable.setWidthPercentage(100);
            totalTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            totalTable.setWidths(new float[] { 7f, 3f });

            totalTable.addCell(new Phrase(" ", fontNormal)); // Cột trái trống để căn lề phải
            Paragraph tamTinh = new Paragraph("Tạm tính: " + NumberUtils.formatVND(hoaDon.getTongTien()), fontNormal);
            tamTinh.setAlignment(Element.ALIGN_RIGHT);
            totalTable.addCell(tamTinh);

            totalTable.addCell(new Phrase(" ", fontNormal));
            Paragraph giamGia = new Paragraph("Giảm giá: -" + NumberUtils.formatVND(hoaDon.getGiamGia()), fontNormal);
            giamGia.setAlignment(Element.ALIGN_RIGHT);
            totalTable.addCell(giamGia);

            totalTable.addCell(new Phrase(" ", fontNormal));
            Paragraph total = new Paragraph("TỔNG CỘNG: " + NumberUtils.formatVND(hoaDon.getThanhTien()), fontBold);
            total.setAlignment(Element.ALIGN_RIGHT);
            totalTable.addCell(total);

            document.add(totalTable);

            // ==========================================
            // 🟢 CHÈN KHUNG VOUCHER (NẾU CÓ) VÀO PDF
            // ==========================================
            if (maVoucher != null && qrUrl != null) {
                document.add(new Paragraph(" "));
                document.add(new Paragraph(" "));

                // Tiêu đề quà tặng
                Font fontVoucherTitle = new Font(baseFont, 14, Font.BOLD, BaseColor.ORANGE);
                Paragraph voucherTitle = new Paragraph("--- 🎁 TẶNG VOUCHER -10% ---", fontVoucherTitle);
                voucherTitle.setAlignment(Element.ALIGN_CENTER);
                document.add(voucherTitle);

                Paragraph subVoucher = new Paragraph("(Dành cho lần thanh toán tiếp theo)", fontItalic);
                subVoucher.setAlignment(Element.ALIGN_CENTER);
                document.add(subVoucher);

                document.add(new Paragraph(" "));

                // Tải ảnh QR từ URL và vẽ vào PDF
                try {
                    Image qrImage = Image.getInstance(new URL(qrUrl));
                    qrImage.setAlignment(Element.ALIGN_CENTER);
                    // Thu nhỏ ảnh nếu nó quá to
                    qrImage.scaleToFit(120, 120);
                    document.add(qrImage);
                } catch (Exception ex) {
                    System.err.println("Không thể tải ảnh QR vào PDF: " + ex.getMessage());
                }

                // In mã Text ở dưới QR
                Paragraph voucherCodeText = new Paragraph("MÃ: " + maVoucher, fontHeader);
                voucherCodeText.setAlignment(Element.ALIGN_CENTER);
                document.add(voucherCodeText);

                document.add(new Paragraph(" "));
            }
            // ==========================================

            // ==================== FOOTER ====================
            document.add(new Paragraph(" "));
            Paragraph footer = new Paragraph("Cảm ơn quý khách! Hẹn gặp lại!", fontItalic);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
            System.out.println("✅ Đã xuất PDF: " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static BaseFont getBaseFont() {
        String[] fontPaths = {
                "c:/windows/fonts/arial.ttf",
                "c:/windows/fonts/tahoma.ttf",
                "c:/windows/fonts/times.ttf",
                "c:/windows/fonts/arialuni.ttf"
        };
        for (String fontPath : fontPaths) {
            try {
                return BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            } catch (Exception e) {
            }
        }
        try {
            return BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, false);
        } catch (Exception e) {
            return null;
        }
    }

    private static void addTableHeader(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(8);
        table.addCell(cell);
    }

    // Nâng cấp: Cho phép truyền vào chế độ căn chỉnh (Trái, Phải, Giữa)
    private static void addTableCell(PdfPTable table, String text, Font font, int alignment) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(6);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(alignment);
        cell.setBorderColor(BaseColor.LIGHT_GRAY); // Chỉnh màu viền bảng cho thanh thoát
        table.addCell(cell);
    }
}