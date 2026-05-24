package view.dialog;

import util.NumberUtils;
import util.QRCodeUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.*;

public class ChuyenKhoanDialog extends JDialog {
    
    public ChuyenKhoanDialog(JFrame parent, double soTien, String maHoaDon) {
        super(parent, "Thanh toán chuyển khoản", true);
        initUI(soTien, maHoaDon);
        setLocationRelativeTo(parent);
        setSize(480, 680);
        setResizable(false);
    }
    
    private void initUI(double soTien, String maHoaDon) {
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // ========== Tiêu đề ==========
        JLabel title = new JLabel("THANH TOÁN CHUYỂN KHOẢN");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(0, 80, 160));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(25));
        
        // ========== Số tiền (nổi bật) ==========
        JPanel amountPanel = new JPanel();
        amountPanel.setLayout(new BoxLayout(amountPanel, BoxLayout.Y_AXIS));
        amountPanel.setBackground(new Color(255, 245, 245));
        amountPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 0, 0), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        amountPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel amountTitle = new JLabel("SỐ TIỀN CẦN THANH TOÁN");
        amountTitle.setFont(new Font("Arial", Font.BOLD, 14));
        amountTitle.setForeground(new Color(180, 0, 0));
        amountTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        amountPanel.add(amountTitle);
        
        amountPanel.add(Box.createVerticalStrut(8));
        
        JLabel amountValue = new JLabel(NumberUtils.formatVND(soTien));
        amountValue.setFont(new Font("Arial", Font.BOLD, 28));
        amountValue.setForeground(new Color(220, 0, 0));
        amountValue.setAlignmentX(Component.CENTER_ALIGNMENT);
        amountPanel.add(amountValue);
        
        mainPanel.add(amountPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        
        // ========== Mã hóa đơn ==========
        JPanel infoPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JLabel maLabel = new JLabel("Mã đơn hàng:");
        maLabel.setFont(new Font("Arial", Font.BOLD, 14));
        maLabel.setForeground(Color.BLACK);
        
        JLabel maValue = new JLabel(maHoaDon);
        maValue.setFont(new Font("Arial", Font.BOLD, 14));
        maValue.setForeground(new Color(0, 100, 0));
        
        JLabel ndLabel = new JLabel("Nội dung CK:");
        ndLabel.setFont(new Font("Arial", Font.BOLD, 14));
        ndLabel.setForeground(Color.BLACK);
        
        String noiDung = "TT " + maHoaDon;
        JLabel ndValue = new JLabel(noiDung);
        ndValue.setFont(new Font("Arial", Font.BOLD, 14));
        ndValue.setForeground(new Color(0, 0, 180));
        
        infoPanel.add(maLabel);
        infoPanel.add(maValue);
        infoPanel.add(ndLabel);
        infoPanel.add(ndValue);
        
        mainPanel.add(infoPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        
        // ========== Thông tin ngân hàng ==========
        JPanel bankPanel = new JPanel();
        bankPanel.setLayout(new BoxLayout(bankPanel, BoxLayout.Y_AXIS));
        bankPanel.setBackground(new Color(245, 250, 255));
        bankPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 80, 160), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel bankTitle = new JLabel("THÔNG TIN NGÂN HÀNG");
        bankTitle.setFont(new Font("Arial", Font.BOLD, 14));
        bankTitle.setForeground(new Color(0, 80, 160));
        bankTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        bankPanel.add(bankTitle);
        
        bankPanel.add(Box.createVerticalStrut(12));
        
        JPanel bankDetailPanel = new JPanel(new GridLayout(3, 2, 10, 12));
        bankDetailPanel.setBackground(new Color(245, 250, 255));
        
        JLabel bankNameLabel = new JLabel("Ngân hàng:");
        bankNameLabel.setFont(new Font("Arial", Font.BOLD, 13));
        bankNameLabel.setForeground(Color.BLACK);
        JLabel bankNameValue = new JLabel("VIETCOMBANK");
        bankNameValue.setFont(new Font("Arial", Font.PLAIN, 13));
        bankNameValue.setForeground(Color.BLACK);
        
        JLabel accNumberLabel = new JLabel("Số tài khoản:");
        accNumberLabel.setFont(new Font("Arial", Font.BOLD, 13));
        accNumberLabel.setForeground(Color.BLACK);
        JLabel accNumberValue = new JLabel("1234567890");
        accNumberValue.setFont(new Font("Arial", Font.PLAIN, 13));
        accNumberValue.setForeground(Color.BLACK);
        
        JLabel accNameLabel = new JLabel("Chủ tài khoản:");
        accNameLabel.setFont(new Font("Arial", Font.BOLD, 13));
        accNameLabel.setForeground(Color.BLACK);
        JLabel accNameValue = new JLabel("TVT - QUÁN MÌ CAY");
        accNameValue.setFont(new Font("Arial", Font.PLAIN, 13));
        accNameValue.setForeground(Color.BLACK);
        
        bankDetailPanel.add(bankNameLabel);
        bankDetailPanel.add(bankNameValue);
        bankDetailPanel.add(accNumberLabel);
        bankDetailPanel.add(accNumberValue);
        bankDetailPanel.add(accNameLabel);
        bankDetailPanel.add(accNameValue);
        
        bankPanel.add(bankDetailPanel);
        mainPanel.add(bankPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        
        // ========== Mã QR ==========
        JPanel qrContainer = new JPanel(new BorderLayout());
        qrContainer.setBackground(Color.WHITE);
        qrContainer.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY),
            "Quét mã QR để thanh toán",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 12),
            Color.BLACK
        ));
        
        String qrContent = generateVietQRContent(soTien, noiDung);
        JPanel qrPanel = QRCodeUtils.createQRCodePanel(qrContent, 260);
        qrContainer.add(qrPanel, BorderLayout.CENTER);
        mainPanel.add(qrContainer);
        mainPanel.add(Box.createVerticalStrut(20));
        
        // ========== Hướng dẫn ==========
        JPanel guidePanel = new JPanel();
        guidePanel.setBackground(new Color(240, 240, 240));
        guidePanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        
        JTextArea guideArea = new JTextArea(
            "HƯỚNG DẪN THANH TOÁN:\n" +
            "1. Mở ứng dụng ngân hàng trên điện thoại\n" +
            "2. Chọn chức năng quét mã QR\n" +
            "3. Quét mã QR bên trên\n" +
            "4. Kiểm tra lại số tiền và nội dung\n" +
            "5. Xác nhận chuyển khoản\n" +
            "6. Sau khi chuyển thành công, nhấn 'Đã thanh toán'"
        );
        guideArea.setFont(new Font("Arial", Font.PLAIN, 11));
        guideArea.setForeground(Color.BLACK);
        guideArea.setBackground(new Color(240, 240, 240));
        guideArea.setEditable(false);
        guideArea.setFocusable(false);
        guidePanel.add(guideArea);
        mainPanel.add(guidePanel);
        mainPanel.add(Box.createVerticalStrut(15));
        
        // ========== Nút bấm ==========
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton btnCopy = new JButton("📋 Sao chép nội dung CK");
        btnCopy.setFont(new Font("Arial", Font.BOLD, 12));
        btnCopy.setBackground(new Color(80, 80, 80));
        btnCopy.setForeground(Color.WHITE);
        btnCopy.setFocusPainted(false);
        btnCopy.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCopy.addActionListener(e -> {
            Toolkit.getDefaultToolkit()
                .getSystemClipboard()
                .setContents(new java.awt.datatransfer.StringSelection(noiDung), null);
            JOptionPane.showMessageDialog(this, "✅ Đã sao chép: " + noiDung);
        });
        
        JButton btnPaid = new JButton("✅ Đã thanh toán");
        btnPaid.setFont(new Font("Arial", Font.BOLD, 13));
        btnPaid.setBackground(new Color(0, 120, 0));
        btnPaid.setForeground(Color.WHITE);
        btnPaid.setFocusPainted(false);
        btnPaid.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPaid.addActionListener(e -> dispose());
        
        buttonPanel.add(btnCopy);
        buttonPanel.add(btnPaid);
        mainPanel.add(buttonPanel);
        
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private String generateVietQRContent(double soTien, String noiDung) {
        String bankCode = "VIETCOMBANK";
        String accountNumber = "1234567890";
        String encodedNoiDung = noiDung.replace(" ", "%20");
        
        return String.format("https://img.vietqr.io/%s/%s.png?amount=%d&addInfo=%s",
                bankCode, accountNumber, (long) soTien, encodedNoiDung);
    }
}