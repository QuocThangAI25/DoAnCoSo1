package view.panel;

import model.HoaDon;
import model.ChiTietHoaDon;
import service.QuanLyBanService;
import service.ThanhToanService;
import util.NumberUtils;
import util.UiTheme;
import view.dialog.ChuyenKhoanDialog;
import view.dialog.InvoicePreviewDialog;
import view.frame.MainFrame;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
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
        setLayout(new BorderLayout(0, 0));
        UiTheme.invoicePanel(this);
        setPreferredSize(new Dimension(460, 0));

        JLabel lblHeader = new JLabel();
        UiTheme.sectionHeader(lblHeader, "HÓA ĐƠN");
        add(lblHeader, BorderLayout.NORTH);

        String[] columns = { "STT", "Tên món", "Số lượng", "Đơn giá", "Thành tiền", "" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };
        table = new JTable(tableModel);
        UiTheme.table(table);
        table.setRowHeight(32);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFillsViewportHeight(true);
        styleInvoiceTable();
        setupQuantityEditor();

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());

                // Cột 5 là cột "Xóa"
                if (row >= 0 && col == 5) {
                    xoaMon(row);
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));
        scrollPane.getViewport().setBackground(UiTheme.PANEL);
        scrollPane.setBackground(UiTheme.PANEL);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomWrap = new JPanel(new BorderLayout());
        bottomWrap.setOpaque(false);
        bottomWrap.setBorder(BorderFactory.createEmptyBorder(8, 12, 12, 12));

        JPanel summaryPanel = new JPanel(new GridBagLayout());
        summaryPanel.setBackground(UiTheme.CARD);
        summaryPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiTheme.RED_DARK, 1),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        JLabel lblTamTinhTitle = new JLabel("Tạm tính:");
        UiTheme.label(lblTamTinhTitle);
        summaryPanel.add(lblTamTinhTitle, gbc);
        lblTamTinh = new JLabel("0 ₫");
        lblTamTinh.setFont(UiTheme.bold(14));
        lblTamTinh.setForeground(UiTheme.TEXT);
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        summaryPanel.add(lblTamTinh, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel lblGiamGiaTitle = new JLabel("Giảm giá:");
        UiTheme.label(lblGiamGiaTitle);
        summaryPanel.add(lblGiamGiaTitle, gbc);

        JPanel giamGiaPanel = new JPanel(new BorderLayout(6, 0));
        giamGiaPanel.setOpaque(false);
        txtGiamGia = new JTextField("0", 8);
        txtGiamGia.setFont(UiTheme.plain(13));
        txtGiamGia.setBackground(UiTheme.PANEL);
        txtGiamGia.setForeground(UiTheme.TEXT);
        txtGiamGia.setCaretColor(UiTheme.RED_GLOW);
        txtGiamGia.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiTheme.RED_DARK, 1),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        JButton btnApGiam = new JButton("Áp dụng");
        UiTheme.primaryButton(btnApGiam);
        btnApGiam.setFont(UiTheme.bold(11));
        btnApGiam.setPreferredSize(new Dimension(80, 32));
        btnApGiam.addActionListener(e -> apGiamGia());
        giamGiaPanel.add(txtGiamGia, BorderLayout.CENTER);
        giamGiaPanel.add(btnApGiam, BorderLayout.EAST);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        summaryPanel.add(giamGiaPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(12, 4, 4, 4);
        JPanel totalRow = new JPanel(new BorderLayout());
        totalRow.setOpaque(false);
        JLabel lblTongTitle = new JLabel("TỔNG CỘNG:");
        lblTongTitle.setFont(UiTheme.bold(16));
        lblTongTitle.setForeground(UiTheme.RED);
        lblTongCong = new JLabel("0 ₫");
        lblTongCong.setFont(UiTheme.bold(22));
        lblTongCong.setForeground(UiTheme.RED_GLOW);
        lblTongCong.setHorizontalAlignment(SwingConstants.RIGHT);
        totalRow.add(lblTongTitle, BorderLayout.WEST);
        totalRow.add(lblTongCong, BorderLayout.EAST);
        summaryPanel.add(totalRow, gbc);

        bottomWrap.add(summaryPanel, BorderLayout.CENTER);

        // Chỉ giữ lại một nút THANH TOÁN
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));
        btnPanel.setOpaque(false);
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JButton btnThanhToan = new JButton("THANH TOÁN");
        UiTheme.primaryButton(btnThanhToan);
        btnThanhToan.setFont(UiTheme.bold(14));
        btnThanhToan.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        btnThanhToan.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnThanhToan.addActionListener(e -> thanhToan());

        btnPanel.add(btnThanhToan);

        bottomWrap.add(btnPanel, BorderLayout.SOUTH);
        add(bottomWrap, BorderLayout.SOUTH);
    }

    private void styleInvoiceTable() {
        JTableHeader header = table.getTableHeader();
        header.setBackground(UiTheme.BLACK);
        header.setForeground(UiTheme.RED);
        header.setFont(UiTheme.bold(12));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 36));
        header.setReorderingAllowed(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? UiTheme.CARD : UiTheme.PANEL);
                }
                setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UiTheme.RED_DARK));
                return c;
            }
        };
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? UiTheme.CARD : UiTheme.PANEL);
                }
                setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UiTheme.RED_DARK));
                return c;
            }
        };

        for (int i = 0; i < table.getColumnCount(); i++) {
            TableColumn col = table.getColumnModel().getColumn(i);
            if (i == 1) {
                col.setCellRenderer(leftRenderer);
            } else {
                col.setCellRenderer(centerRenderer);
            }
        }

        DefaultTableCellRenderer sttRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                // Bỏ qua value gốc (ID DB), thay bằng số thứ tự (row + 1)
                Component c = super.getTableCellRendererComponent(t, String.valueOf(row + 1), isSelected, hasFocus, row,
                        column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? UiTheme.CARD : UiTheme.PANEL);
                }
                setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UiTheme.RED_DARK));
                setHorizontalAlignment(SwingConstants.CENTER);
                return c;
            }
        };
        table.getColumnModel().getColumn(0).setCellRenderer(sttRenderer);

        // Cột STT
        table.getColumnModel().getColumn(0).setMinWidth(35);
        table.getColumnModel().getColumn(0).setMaxWidth(35);
        // Cột Số lượng
        table.getColumnModel().getColumn(2).setMinWidth(55);
        table.getColumnModel().getColumn(2).setMaxWidth(55);
        // Cột Đơn giá
        table.getColumnModel().getColumn(3).setMinWidth(75);
        table.getColumnModel().getColumn(3).setMaxWidth(75);
        // Cột Thành tiền
        table.getColumnModel().getColumn(4).setMinWidth(85);
        table.getColumnModel().getColumn(4).setMaxWidth(85);
        // Cột 5: Nút XÓA
        table.getColumnModel().getColumn(5).setMinWidth(35);
        table.getColumnModel().getColumn(5).setMaxWidth(35);

        DefaultTableCellRenderer hiddenHeaderRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                        column);
                lbl.setBackground(UiTheme.PANEL); // Đồng bộ màu nền với Header
                lbl.setBorder(BorderFactory.createEmptyBorder()); // Xóa sạch viền (bóp mất ô vuông)
                return lbl;
            }
        };
        table.getColumnModel().getColumn(5).setHeaderRenderer(hiddenHeaderRenderer);
    }

    private void setupQuantityEditor() {
        JTextField qtyField = new JTextField();
        qtyField.setFont(UiTheme.bold(13));
        qtyField.setBackground(UiTheme.CARD);
        qtyField.setForeground(UiTheme.TEXT);
        qtyField.setCaretColor(UiTheme.RED_GLOW);
        qtyField.setHorizontalAlignment(SwingConstants.CENTER);

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

    private void xoaMon(int row) {
        int confirm = JOptionPane.showConfirmDialog(this, "Xóa món này khỏi hóa đơn?", "Xác nhận",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int ctId = (int) tableModel.getValueAt(row, 0);
            ThanhToanService.xoaMonKhoiHoaDon(ctId, currentHoaDonId);
            loadHoaDon(currentHoaDonId);
        } else {
            loadHoaDon(currentHoaDonId);
        }
    }

    private void thanhToan() {
        if (currentHoaDonId != -1 && tableModel.getRowCount() > 0) {
            JPanel paymentPanel = new JPanel(new GridLayout(3, 1, 10, 10));
            paymentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel lblTitle = new JLabel("Chọn phương thức thanh toán:");
            lblTitle.setFont(UiTheme.bold(14));

            JRadioButton rbTienMat = new JRadioButton("Tiền mặt", true);
            JRadioButton rbChuyenKhoan = new JRadioButton("Chuyển khoản");
            ButtonGroup group = new ButtonGroup();
            group.add(rbTienMat);
            group.add(rbChuyenKhoan);

            JPanel radioPanel = new JPanel(new GridLayout(2, 1));
            radioPanel.add(rbTienMat);
            radioPanel.add(rbChuyenKhoan);

            paymentPanel.add(lblTitle);
            paymentPanel.add(radioPanel);

            int option = JOptionPane.showConfirmDialog(this, paymentPanel,
                    "THANH TOÁN HÓA ĐƠN",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (option != JOptionPane.OK_OPTION) {
                return;
            }

            String phuongThuc = rbTienMat.isSelected() ? "TIỀN MẶT" : "CHUYỂN KHOẢN";
            HoaDon hoaDon = ThanhToanService.getHoaDon(currentHoaDonId);
            double tongTien = hoaDon.getThanhTien();

            if (phuongThuc.equals("CHUYỂN KHOẢN")) {
                String maHoaDon = "HD" + currentHoaDonId;
                ChuyenKhoanDialog qrDialog = new ChuyenKhoanDialog(mainFrame, tongTien, maHoaDon);
                qrDialog.setVisible(true);

                int confirm = JOptionPane.showConfirmDialog(this,
                        "Đã hoàn tất thanh toán chuyển khoản?",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);

                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    String.format("Xác nhận thanh toán hóa đơn?\n\nPhương thức: %s\nTổng tiền: %s\n\nTiếp tục?",
                            phuongThuc, NumberUtils.formatVND(tongTien)),
                    "XÁC NHẬN THANH TOÁN",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE);

            if (confirm == JOptionPane.YES_OPTION) {
                // Xử lý logic thanh toán
                ThanhToanService.thanhToan(currentHoaDonId);
                QuanLyBanService.updateTrangThaiBan(currentBan, "Trống", -1);

                hoaDon = ThanhToanService.getHoaDon(currentHoaDonId);
                List<ChiTietHoaDon> chiTietList = ThanhToanService.getChiTietHoaDon(currentHoaDonId);

                // Thông báo thành công
                JOptionPane.showMessageDialog(this,
                        String.format(
                                "THANH TOÁN THÀNH CÔNG!\n\nPhương thức: %s\nTổng tiền: %s\nNhân viên: %s",
                                phuongThuc, NumberUtils.formatVND(tongTien), mainFrame.getNhanVien().getTen()),
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);

                // Gọi giao diện Xem trước Hóa Đơn
                InvoicePreviewDialog previewDialog = new InvoicePreviewDialog(mainFrame, hoaDon, chiTietList,
                        phuongThuc);
                previewDialog.setVisible(true);

                // Sau khi tắt giao diện xem trước (In hoặc Hủy xong), dọn dẹp bàn
                mainFrame.refreshBanPanel();
                clearHoaDon();
                currentBan = -1;
                currentHoaDonId = -1;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Không có hóa đơn nào để thanh toán!");
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
        if (hoaDon == null)
            return;

        List<ChiTietHoaDon> list = ThanhToanService.getChiTietHoaDon(hoaDonId);
        for (ChiTietHoaDon ct : list) {
            tableModel.addRow(new Object[] {
                    ct.getId(),
                    ct.getTenMon(),
                    ct.getSoLuong(),
                    NumberUtils.formatVND(ct.getDonGia()),
                    NumberUtils.formatVND(ct.getThanhTien()),
                    "<html><font color='red'><b>XÓA</b></font></html>"
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