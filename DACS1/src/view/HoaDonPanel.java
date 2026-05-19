package view;

import model.ChiTietHoaDon;
import model.HoaDon;
import service.QuanLyBanService;
import service.ThanhToanService;
import utils.NumberUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HoaDonPanel extends JPanel {
    private MainFrame mainFrame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblTamTinh, lblTongCong;
    private JTextField txtGiamGia;
    private int currentHoaDonId = -1;
    private int currentBan = -1;
    private int nhanVienId = -1;
    
    public HoaDonPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initUI();
    }
    
    private void initUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("HÓA ĐƠN"));
        
        // Table
        String[] columns = {"STT", "Tên món", "Số lượng", "Đơn giá", "Thành tiền"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };
        table = new JTable(tableModel);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel tổng tiền
        JPanel totalPanel = new JPanel(new GridBagLayout());
        totalPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Tạm tính
        gbc.gridx = 0; gbc.gridy = 0;
        totalPanel.add(new JLabel("Tạm tính:"), gbc);
        lblTamTinh = new JLabel("0 ₫");
        lblTamTinh.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 1;
        totalPanel.add(lblTamTinh, gbc);
        
        // Giảm giá
        gbc.gridx = 0; gbc.gridy = 1;
        totalPanel.add(new JLabel("Giảm giá:"), gbc);
        JPanel giamGiaPanel = new JPanel(new BorderLayout());
        txtGiamGia = new JTextField("0", 10);
        JButton btnApGiam = new JButton("Áp dụng");
        btnApGiam.addActionListener(e -> apGiamGia());
        giamGiaPanel.add(txtGiamGia, BorderLayout.CENTER);
        giamGiaPanel.add(btnApGiam, BorderLayout.EAST);
        gbc.gridx = 1;
        totalPanel.add(giamGiaPanel, gbc);
        
        // Tổng cộng
        gbc.gridx = 0; gbc.gridy = 2;
        totalPanel.add(new JLabel("TỔNG CỘNG:"), gbc);
        lblTongCong = new JLabel("0 ₫");
        lblTongCong.setFont(new Font("Arial", Font.BOLD, 18));
        lblTongCong.setForeground(Color.RED);
        gbc.gridx = 1;
        totalPanel.add(lblTongCong, gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton btnThanhToan = new JButton("THANH TOÁN");
        btnThanhToan.setBackground(new Color(0, 150, 0));
        btnThanhToan.setForeground(Color.WHITE);
        btnThanhToan.addActionListener(e -> thanhToan());
        
        JButton btnInHoaDon = new JButton("IN HÓA ĐƠN");
        btnInHoaDon.setBackground(new Color(0, 100, 200));
        btnInHoaDon.setForeground(Color.WHITE);
        btnInHoaDon.addActionListener(e -> inHoaDon());
        
        btnPanel.add(btnThanhToan);
        btnPanel.add(btnInHoaDon);
        totalPanel.add(btnPanel, gbc);
        
        add(totalPanel, BorderLayout.SOUTH);
        
        // Xử lý sửa số lượng
        tableModel.addTableModelListener(e -> {
            if (e.getColumn() == 2 && currentHoaDonId != -1) {
                int row = e.getFirstRow();
                try {
                    int soLuongMoi = Integer.parseInt(tableModel.getValueAt(row, 2).toString());
                    if (soLuongMoi <= 0) {
                        xoaMon(row);
                    } else {
                        int ctId = (int) tableModel.getValueAt(row, 0);
                        List<ChiTietHoaDon> list = ThanhToanService.getChiTietHoaDon(currentHoaDonId);
                        for (ChiTietHoaDon ct : list) {
                            if (ct.getId() == ctId) {
                                ThanhToanService.capNhatSoLuongMon(ct, soLuongMoi);
                                break;
                            }
                        }
                        loadHoaDon(currentHoaDonId);
                    }
                } catch (NumberFormatException ex) {
                    loadHoaDon(currentHoaDonId);
                }
            }
        });
    }
    
    private void apGiamGia() {
        if (currentHoaDonId != -1) {
            try {
                double giamGia = NumberUtils.parseVND(txtGiamGia.getText());
                ThanhToanService.capNhatGiamGia(currentHoaDonId, giamGia);
                loadHoaDon(currentHoaDonId);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số tiền giảm giá hợp lệ!");
            }
        }
    }
    
    private void xoaMon(int row) {
        int confirm = JOptionPane.showConfirmDialog(this, "Xóa món này khỏi hóa đơn?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int ctId = (int) tableModel.getValueAt(row, 0);
            ThanhToanService.xoaMonKhoiHoaDon(ctId, currentHoaDonId);
            loadHoaDon(currentHoaDonId);
        } else {
            loadHoaDon(currentHoaDonId);
        }
    }
    
    private void thanhToan() {
        if (currentHoaDonId != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận thanh toán hóa đơn?", "Thanh toán", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                ThanhToanService.thanhToan(currentHoaDonId);
                QuanLyBanService.updateTrangThaiBan(currentBan, "Trống", -1);
                JOptionPane.showMessageDialog(this, "Thanh toán thành công!");
                mainFrame.refreshBanPanel();
                clearHoaDon();
                currentBan = -1;
                currentHoaDonId = -1;
            }
        }
    }
    
    private void inHoaDon() {
        if (currentHoaDonId != -1 && tableModel.getRowCount() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("========== TVT MÌ CAY ==========\n");
            sb.append("Ngày: ").append(new java.util.Date()).append("\n");
            sb.append("================================\n");
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                sb.append(tableModel.getValueAt(i, 1)).append(" x")
                  .append(tableModel.getValueAt(i, 2)).append("\t")
                  .append(tableModel.getValueAt(i, 4)).append("\n");
            }
            sb.append("================================\n");
            sb.append("Tạm tính: ").append(lblTamTinh.getText()).append("\n");
            sb.append("Giảm giá: -").append(txtGiamGia.getText()).append(" ₫\n");
            sb.append("TỔNG CỘNG: ").append(lblTongCong.getText()).append("\n");
            sb.append("================================\n");
            sb.append("Cảm ơn quý khách!\n");
            
            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
            JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "In hóa đơn", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void setCurrentBan(int ban, int nvId) {
        this.currentBan = ban;
        this.nhanVienId = nvId;
        
        HoaDon hoaDon = QuanLyBanService.getHoaDonByBan(ban);
        if (hoaDon == null) {
            hoaDon = QuanLyBanService.taoHoaDonMoi(ban, nvId);
            QuanLyBanService.updateTrangThaiBan(ban, "Đang phục vụ", hoaDon.getId());
            mainFrame.refreshBanPanel();
        }
        loadHoaDon(hoaDon.getId());
    }
    
    public void loadHoaDon(int hoaDonId) {
        this.currentHoaDonId = hoaDonId;
        tableModel.setRowCount(0);
        
        HoaDon hoaDon = ThanhToanService.getHoaDon(hoaDonId);
        if (hoaDon == null) return;
        
        List<ChiTietHoaDon> list = ThanhToanService.getChiTietHoaDon(hoaDonId);
        for (ChiTietHoaDon ct : list) {
            tableModel.addRow(new Object[]{
                ct.getId(),
                ct.getTenMon(),
                ct.getSoLuong(),
                NumberUtils.formatVND(ct.getDonGia()),
                NumberUtils.formatVND(ct.getThanhTien())
            });
        }
        
        lblTamTinh.setText(NumberUtils.formatVND(hoaDon.getTongTien()));
        txtGiamGia.setText(String.valueOf((int) hoaDon.getGiamGia()));
        lblTongCong.setText(NumberUtils.formatVND(hoaDon.getThanhTien()));
    }
    
    private void clearHoaDon() {
        tableModel.setRowCount(0);
        lblTamTinh.setText("0 ₫");
        txtGiamGia.setText("0");
        lblTongCong.setText("0 ₫");
        currentHoaDonId = -1;
    }
    
    public int getCurrentHoaDonId() {
        return currentHoaDonId;
    }
}