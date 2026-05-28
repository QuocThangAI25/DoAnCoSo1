package view.dialog;

import model.ChiTietHoaDon;
import model.HoaDon;
import util.NumberUtils;
import util.UiTheme;
import util.PDFUtils;
import view.frame.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class InvoicePreviewDialog extends JDialog {
    private HoaDon hoaDon;
    private List<ChiTietHoaDon> chiTietList;
    private MainFrame mainFrame;
    private String phuongThuc;

    public InvoicePreviewDialog(MainFrame owner, HoaDon hoaDon, List<ChiTietHoaDon> chiTietList, String phuongThuc) {
        super(owner, "Xem trước hóa đơn", true);
        this.mainFrame = owner;
        this.hoaDon = hoaDon;
        this.chiTietList = chiTietList;
        this.phuongThuc = phuongThuc;
        initUI();
    }

    private void initUI() {
        setSize(450, 600);
        setLocationRelativeTo(mainFrame);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UiTheme.DARK);

        // 1. Vùng hiển thị hóa đơn (Mô phỏng giấy in bill màu trắng)
        JEditorPane htmlPane = new JEditorPane();
        htmlPane.setContentType("text/html");
        htmlPane.setEditable(false);
        htmlPane.setText(generateHtmlPreview());
        htmlPane.setBackground(Color.WHITE); // Nền trắng như giấy

        JScrollPane scrollPane = new JScrollPane(htmlPane);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // 2. Vùng nút bấm (In và Hủy)
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        btnPanel.setBackground(UiTheme.PANEL);
        btnPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UiTheme.RED_DARK));

        JButton btnHuy = new JButton("Hủy");
        UiTheme.outlineButton(btnHuy);
        btnHuy.setPreferredSize(new Dimension(100, 35));
        btnHuy.addActionListener(e -> dispose());

        JButton btnIn = new JButton("In Hóa Đơn");
        UiTheme.primaryButton(btnIn);
        btnIn.setPreferredSize(new Dimension(120, 35));
        btnIn.addActionListener(e -> inPDF());

        btnPanel.add(btnHuy);
        btnPanel.add(btnIn);

        add(btnPanel, BorderLayout.SOUTH);
    }

    // Tạo HTML mô phỏng hóa đơn
    private String generateHtmlPreview() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body style='font-family: sans-serif; padding: 10px; color: #000;'>");
        sb.append("<h2 style='text-align: center; margin-bottom: 5px;'>TVT - QUÁN MÌ CAY</h2>");
        sb.append("<h3 style='text-align: center; margin-top: 0;'>HÓA ĐƠN TẠM TÍNH</h3>");
        sb.append("<hr style='border: 1px dashed #000;'>");

        sb.append("<p><b>Ngày:</b> ").append(sdf.format(new Date())).append("<br>");
        sb.append("<b>Mã HĐ:</b> HD").append(hoaDon.getId()).append("<br>");
        sb.append("<b>Nhân viên:</b> ").append(mainFrame.getNhanVien().getTen()).append("</p>");

        sb.append("<table width='100%' style='border-collapse: collapse;'>");
        sb.append(
                "<tr style='border-bottom: 1px solid #000;'><th align='left' width='45%'>Món</th><th align='center' width='15%'>SL</th><th align='right' width='40%'>Thành tiền</th></tr>");

        for (ChiTietHoaDon ct : chiTietList) {
            sb.append("<tr>");
            sb.append("<td align='left'>").append(ct.getTenMon()).append("</td>");
            sb.append("<td align='center'>").append(ct.getSoLuong()).append("</td>");
            sb.append("<td align='right'>").append(NumberUtils.formatVND(ct.getThanhTien())).append("</td>");
            sb.append("</tr>");
        }
        sb.append("</table>");
        sb.append("<hr style='border: 1px dashed #000;'>");

        sb.append("<table width='100%'>");
        sb.append("<tr><td align='left'>Tạm tính:</td><td align='right'>")
                .append(NumberUtils.formatVND(hoaDon.getTongTien())).append("</td></tr>");
        sb.append("<tr><td align='left'>Giảm giá:</td><td align='right'>-")
                .append(NumberUtils.formatVND(hoaDon.getGiamGia())).append("</td></tr>");
        sb.append("<tr><td align='left'><h2>TỔNG:</h2></td><td align='right'><h2 style='color: red;'>")
                .append(NumberUtils.formatVND(hoaDon.getThanhTien())).append("</h2></td></tr>");
        sb.append("</table>");

        sb.append("</body></html>");
        return sb.toString();
    }

    // Xử lý chọn đường dẫn và in khi bấm "In Hóa Đơn"
    private void inPDF() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu hóa đơn PDF");
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Documents"));
        fileChooser.setSelectedFile(new File("HoaDon_" + hoaDon.getId() + ".pdf"));

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();
            if (!filePath.endsWith(".pdf")) {
                filePath += ".pdf";
            }
            PDFUtils.exportHoaDonToPDF(hoaDon, chiTietList, filePath, phuongThuc, mainFrame.getNhanVien().getTen());
            JOptionPane.showMessageDialog(this, "✅ Đã lưu hóa đơn PDF tại:\n" + filePath);
            dispose(); // Tắt popup sau khi in thành công
        }
    }
}