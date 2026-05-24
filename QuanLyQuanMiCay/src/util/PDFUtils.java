package util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import model.ChiTietHoaDon;
import model.HoaDon;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PDFUtils {

    /**
     * Xuất hóa đơn ra file PDF
     * @param hoaDon Thông tin hóa đơn
     * @param chiTietList Danh sách chi tiết hóa đơn
     * @param filePath Đường dẫn lưu file PDF
     * @param phuongThuc Phương thức thanh toán (Tiền mặt / Chuyển khoản)
     * @param tenNhanVien Tên nhân viên thu tiền
     */
    public static void exportHoaDonToPDF(HoaDon hoaDon, List<ChiTietHoaDon> chiTietList, 
                                          String filePath, String phuongThuc, String tenNhanVien) {
        try {
            // Tạo document với kích thước A4
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Lấy font chữ hỗ trợ tiếng Việt
            BaseFont baseFont = getBaseFont();

            // Các font chữ
            Font fontTitle = new Font(baseFont, 18, Font.BOLD);
            Font fontHeader = new Font(baseFont, 14, Font.BOLD);
            Font fontNormal = new Font(baseFont, 12, Font.NORMAL);
            Font fontBold = new Font(baseFont, 12, Font.BOLD);
            Font fontSmall = new Font(baseFont, 10, Font.NORMAL);
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
            
            document.add(new Paragraph("Ngày: " + sdf.format(new Date()), fontNormal));
            document.add(new Paragraph("Mã hóa đơn: HD" + hoaDon.getId(), fontNormal));
            document.add(new Paragraph("Bàn số: " + hoaDon.getSoBan(), fontNormal));
            document.add(new Paragraph("Nhân viên: " + tenNhanVien, fontNormal));
            
            // Thêm phương thức thanh toán (nếu đã thanh toán)
            if (!phuongThuc.equals("CHƯA THANH TOÁN")) {
                Paragraph method = new Paragraph("Phương thức: " + phuongThuc, fontBold);
                method.setAlignment(Element.ALIGN_LEFT);
                document.add(method);
            }
            
            document.add(new Paragraph(" "));

            // ==================== BẢNG CHI TIẾT ====================
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Căn chỉnh độ rộng cột
            float[] columnWidths = {1f, 3f, 1f, 2f, 2f};
            table.setWidths(columnWidths);

            // Header bảng
            addTableHeader(table, "STT", fontHeader);
            addTableHeader(table, "Tên món", fontHeader);
            addTableHeader(table, "SL", fontHeader);
            addTableHeader(table, "Đơn giá", fontHeader);
            addTableHeader(table, "Thành tiền", fontHeader);

            // Dữ liệu bảng
            int stt = 1;
            for (ChiTietHoaDon ct : chiTietList) {
                addTableCell(table, String.valueOf(stt++), fontNormal);
                addTableCell(table, ct.getTenMon(), fontNormal);
                addTableCell(table, String.valueOf(ct.getSoLuong()), fontNormal);
                addTableCell(table, NumberUtils.formatVND(ct.getDonGia()), fontNormal);
                addTableCell(table, NumberUtils.formatVND(ct.getThanhTien()), fontNormal);
            }

            document.add(table);

            // ==================== TỔNG TIỀN ====================
            document.add(new Paragraph(" "));
            
            Paragraph tamTinh = new Paragraph("Tạm tính: " + NumberUtils.formatVND(hoaDon.getTongTien()), fontNormal);
            tamTinh.setAlignment(Element.ALIGN_RIGHT);
            document.add(tamTinh);
            
            Paragraph giamGia = new Paragraph("Giảm giá: -" + NumberUtils.formatVND(hoaDon.getGiamGia()), fontNormal);
            giamGia.setAlignment(Element.ALIGN_RIGHT);
            document.add(giamGia);

            Paragraph total = new Paragraph("TỔNG CỘNG: " + NumberUtils.formatVND(hoaDon.getThanhTien()), fontBold);
            total.setAlignment(Element.ALIGN_RIGHT);
            document.add(total);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            // ==================== FOOTER ====================
            Paragraph footer = new Paragraph("Cảm ơn quý khách! Hẹn gặp lại!", fontNormal);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);
            
            // Dòng chữ ký nhân viên
            Paragraph sign = new Paragraph("(Ký, ghi rõ họ tên)", fontSmall);
            sign.setAlignment(Element.ALIGN_RIGHT);
            document.add(sign);
            
            // Ghi chú nếu chưa thanh toán
            if (phuongThuc.equals("CHƯA THANH TOÁN")) {
                Paragraph note = new Paragraph("* Hóa đơn này chưa được thanh toán", fontItalic);
                note.setAlignment(Element.ALIGN_CENTER);
                note.setFont(fontItalic);
                document.add(note);
            }

            document.close();
            System.out.println("✅ Đã xuất PDF: " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Lấy font chữ hỗ trợ tiếng Việt
     */
    private static BaseFont getBaseFont() {
        // Thử các font có sẵn trên Windows
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
                // Thử font tiếp theo
            }
        }

        // Nếu không có font nào, dùng font mặc định (không hỗ trợ tiếng Việt)
        try {
            return BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, false);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Thêm header cho bảng PDF
     */
    private static void addTableHeader(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(8);
        table.addCell(cell);
    }

    /**
     * Thêm cell dữ liệu cho bảng PDF
     */
    private static void addTableCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(6);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        
        // Căn giữa cho cột STT và SL
        if (text.matches("\\d+")) {
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        } else {
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        }
        
        table.addCell(cell);
    }
}