package view.panel;

import model.HoaDon;
import model.ChiTietHoaDon;
import service.QuanLyBanService;
import service.ThanhToanService;
import util.NumberUtils;
import util.UiTheme;
import view.frame.MainFrame;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.EventObject;
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
        UiTheme.panel(this);
        setBorder(UiTheme.titledBorder("HÓA ĐƠN"));

        String[] columns = {"STT", "Tên món", "Số lượng", "Đơn giá", "Thành tiền"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };
        table = new JTable(tableModel);
        UiTheme.table(table);
        table.setRowHeight(28);
        setupQuantityEditor();
        JScrollPane scrollPane = new JScrollPane(table);
        UiTheme.scrollPane(scrollPane);
        add(scrollPane, BorderLayout.CENTER);

        JPanel totalPanel = new JPanel(new GridBagLayout());
        UiTheme.card(totalPanel);
        totalPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblTamTinhTitle = new JLabel("Tạm tính:");
        UiTheme.label(lblTamTinhTitle);
        totalPanel.add(lblTamTinhTitle, gbc);
        lblTamTinh = new JLabel("0 ₫");
        UiTheme.accent(lblTamTinh, 14);
        gbc.gridx = 1;
        totalPanel.add(lblTamTinh, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblGiamGiaTitle = new JLabel("Giảm giá:");
        UiTheme.label(lblGiamGiaTitle);
        totalPanel.add(lblGiamGiaTitle, gbc);
        JPanel giamGiaPanel = new JPanel(new BorderLayout());
        UiTheme.card(giamGiaPanel);
        txtGiamGia = new JTextField("0", 10);
        txtGiamGia.setFont(UiTheme.bold(13));
        txtGiamGia.setBackground(UiTheme.PANEL);
        txtGiamGia.setForeground(UiTheme.TEXT);
        txtGiamGia.setCaretColor(UiTheme.RED_GLOW);
        txtGiamGia.setBorder(BorderFactory.createLineBorder(UiTheme.RED_DARK, 1));
        JButton btnApGiam = new JButton("Áp dụng");
        UiTheme.ghostButton(btnApGiam);
        btnApGiam.addActionListener(e -> apGiamGia());
        giamGiaPanel.add(txtGiamGia, BorderLayout.CENTER);
        giamGiaPanel.add(btnApGiam, BorderLayout.EAST);
        gbc.gridx = 1;
        totalPanel.add(giamGiaPanel, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblTongTitle = new JLabel("TỔNG CỘNG:");
        UiTheme.label(lblTongTitle);
        lblTongTitle.setFont(UiTheme.bold(16));
        totalPanel.add(lblTongTitle, gbc);
        lblTongCong = new JLabel("0 ₫");
        UiTheme.accent(lblTongCong, 20);
        gbc.gridx = 1;
        totalPanel.add(lblTongCong, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        UiTheme.panel(btnPanel);
        JButton btnThanhToan = new JButton("THANH TOÁN");
        UiTheme.primaryButton(btnThanhToan);
        btnThanhToan.addActionListener(e -> thanhToan());

        JButton btnInHoaDon = new JButton("IN HÓA ĐƠN");
        UiTheme.outlineButton(btnInHoaDon);
        btnInHoaDon.addActionListener(e -> inHoaDon());

        btnPanel.add(btnThanhToan);
        btnPanel.add(btnInHoaDon);
        totalPanel.add(btnPanel, gbc);

        add(totalPanel, BorderLayout.SOUTH);
    }

    private void setupQuantityEditor() {
        JTextField qtyField = new JTextField();
        qtyField.setFont(UiTheme.bold(13));
        qtyField.setBackground(UiTheme.CARD);
        qtyField.setForeground(UiTheme.TEXT);
        qtyField.setCaretColor(UiTheme.RED_GLOW);

        DefaultCellEditor editor = new DefaultCellEditor(qtyField) {
            @Override
            public boolean isCellEditable(EventObject e) {
                return currentHoaDonId != -1 && super.isCellEditable(e);
            }

            @Override
            public boolean stopCellEditing() {
                try {
                    String text = getCellEditorValue().toString().trim();
                    int value = Integer.parseInt(text);
                    if (value <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(HoaDonPanel.this, "Số lượng phải là số nguyên dương!");
                    return false;
                }
                return super.stopCellEditing();
            }
        };

        editor.addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(javax.swing.event.ChangeEvent e) {
                int row = table.getEditingRow();
                if (row < 0) {
                    row = table.getSelectedRow();
                }
                if (row >= 0) {
                    capNhatSoLuongTuBang(row);
                }
            }

            @Override
            public void editingCanceled(javax.swing.event.ChangeEvent e) {
            }
        });

        TableColumn qtyColumn = table.getColumnModel().getColumn(2);
        qtyColumn.setCellEditor(editor);
    }

    private void capNhatSoLuongTuBang(int row) {
        if (currentHoaDonId == -1 || row < 0 || row >= tableModel.getRowCount()) {
            return;
        }
        try {
            int soLuongMoi = Integer.parseInt(tableModel.getValueAt(row, 2).toString().trim());
            if (soLuongMoi <= 0) {
                xoaMon(row);
                return;
            }
            int ctId = (int) tableModel.getValueAt(row, 0);
            List<ChiTietHoaDon> list = ThanhToanService.getChiTietHoaDon(currentHoaDonId);
            for (ChiTietHoaDon ct : list) {
                if (ct.getId() == ctId) {
                    ThanhToanService.capNhatSoLuongMon(ct, soLuongMoi);
                    break;
                }
            }
            loadHoaDon(currentHoaDonId);
        } catch (NumberFormatException ex) {
            loadHoaDon(currentHoaDonId);
        }
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
            textArea.setFont(UiTheme.plain(12));
            textArea.setBackground(UiTheme.CARD);
            textArea.setForeground(UiTheme.TEXT);
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
