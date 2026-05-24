package view.panel;

import model.Mon;
import service.MonService;
import util.NumberUtils;
import util.UiTheme;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class QuanLyMenuPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtTen, txtGia;
    private JComboBox<String> cbLoai, cbCapDo, cbTrangThai;
    private MonService monService;
    private Runnable onMenuChanged;
    private boolean loaded;

    public QuanLyMenuPanel(Runnable onMenuChanged) {
        this.onMenuChanged = onMenuChanged;
        monService = new MonService();
        initUI();
    }

    public void loadIfNeeded() {
        if (!loaded) {
            loadData();
            loaded = true;
        }
    }

    private void initUI() {
        setLayout(new BorderLayout());
        UiTheme.panel(this);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columns = {"ID", "Tên món", "Loại", "Cấp độ", "Giá", "Trạng thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        UiTheme.table(table);
        table.setRowHeight(28);
        JScrollPane scrollPane = new JScrollPane(table);
        UiTheme.scrollPane(scrollPane);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        UiTheme.card(inputPanel);
        inputPanel.setBorder(UiTheme.titledBorder("Thêm/Sửa món"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblTen = new JLabel("Tên món:");
        UiTheme.label(lblTen);
        inputPanel.add(lblTen, gbc);
        txtTen = new JTextField(20);
        styleField(txtTen);
        gbc.gridx = 1;
        inputPanel.add(txtTen, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblLoai = new JLabel("Loại:");
        UiTheme.label(lblLoai);
        inputPanel.add(lblLoai, gbc);
        cbLoai = new JComboBox<>(new String[]{"Mi Cay", "Nuoc Uong", "Topping"});
        gbc.gridx = 1;
        inputPanel.add(cbLoai, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblCapDo = new JLabel("Cấp độ cay:");
        UiTheme.label(lblCapDo);
        inputPanel.add(lblCapDo, gbc);
        cbCapDo = new JComboBox<>(new String[]{"0 (Không cay)", "3", "5", "7 (Đặc biệt)", "10 (Siêu cay)", "Không áp dụng"});
        gbc.gridx = 1;
        inputPanel.add(cbCapDo, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lblGia = new JLabel("Giá:");
        UiTheme.label(lblGia);
        inputPanel.add(lblGia, gbc);
        txtGia = new JTextField(20);
        styleField(txtGia);
        gbc.gridx = 1;
        inputPanel.add(txtGia, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        JLabel lblTt = new JLabel("Trạng thái:");
        UiTheme.label(lblTt);
        inputPanel.add(lblTt, gbc);
        cbTrangThai = new JComboBox<>(new String[]{"Đang bán", "Ngừng bán"});
        gbc.gridx = 1;
        inputPanel.add(cbTrangThai, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new FlowLayout());
        UiTheme.panel(btnPanel);
        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        JButton btnLamMoi = new JButton("Làm mới");
        UiTheme.primaryButton(btnThem);
        UiTheme.outlineButton(btnSua);
        UiTheme.ghostButton(btnXoa);
        UiTheme.ghostButton(btnLamMoi);

        btnThem.addActionListener(e -> themMon());
        btnSua.addActionListener(e -> suaMon());
        btnXoa.addActionListener(e -> xoaMon());
        btnLamMoi.addActionListener(e -> clearInput());

        btnPanel.add(btnThem);
        btnPanel.add(btnSua);
        btnPanel.add(btnXoa);
        btnPanel.add(btnLamMoi);
        inputPanel.add(btnPanel, gbc);

        add(inputPanel, BorderLayout.SOUTH);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    int id = (int) tableModel.getValueAt(row, 0);
                    Mon mon = monService.getById(id);
                    if (mon != null) {
                        txtTen.setText(mon.getTen());
                        cbLoai.setSelectedItem(mon.getLoai());
                        cbCapDo.setSelectedItem(getCapDoString(mon.getCapDoCay()));
                        txtGia.setText(NumberUtils.formatPlainAmount(mon.getGia()));
                        cbTrangThai.setSelectedItem(mon.isConBan() ? "Đang bán" : "Ngừng bán");
                    }
                }
            }
        });
    }

    private void styleField(JTextField field) {
        field.setFont(UiTheme.bold(13));
        field.setBackground(UiTheme.PANEL);
        field.setForeground(UiTheme.TEXT);
        field.setCaretColor(UiTheme.RED_GLOW);
        field.setBorder(BorderFactory.createLineBorder(UiTheme.RED_DARK, 1));
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Mon> monList = monService.getAllForQuanLy();

        for (Mon mon : monList) {
            String capDo = getCapDoString(mon.getCapDoCay());
            String gia = NumberUtils.formatVND(mon.getGia());
            String trangThai = mon.isConBan() ? "Đang bán" : "Ngừng bán";
            tableModel.addRow(new Object[]{mon.getId(), mon.getTen(), mon.getLoai(), capDo, gia, trangThai});
        }
    }

    private String getCapDoString(int capDo) {
        if (capDo == -1) return "Không áp dụng";
        if (capDo == 0) return "0 (Không cay)";
        if (capDo == 7) return "7 (Đặc biệt)";
        if (capDo == 10) return "10 (Siêu cay)";
        return String.valueOf(capDo);
    }

    private int getCapDoValue(String capDoStr) {
        if (capDoStr.contains("Không áp dụng")) return -1;
        if (capDoStr.contains("10")) return 10;
        if (capDoStr.contains("7")) return 7;
        if (capDoStr.contains("5")) return 5;
        if (capDoStr.contains("3")) return 3;
        if (capDoStr.contains("0")) return 0;
        return -1;
    }

    private boolean isDangBan() {
        return "Đang bán".equals(cbTrangThai.getSelectedItem());
    }

    private void themMon() {
        String ten = txtTen.getText().trim();
        String loai = cbLoai.getSelectedItem().toString();
        int capDo = getCapDoValue(cbCapDo.getSelectedItem().toString());
        double gia = NumberUtils.parseVND(txtGia.getText());

        if (ten.isEmpty() || gia <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        Mon mon = new Mon(0, ten, loai, capDo, gia, isDangBan());
        monService.them(mon);
        loadData();
        clearInput();
        notifyMenuChanged();
        JOptionPane.showMessageDialog(this, "Thêm món thành công!");
    }

    private void suaMon() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn món cần sửa!");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);
        String ten = txtTen.getText().trim();
        String loai = cbLoai.getSelectedItem().toString();
        int capDo = getCapDoValue(cbCapDo.getSelectedItem().toString());
        double gia = NumberUtils.parseVND(txtGia.getText());

        if (ten.isEmpty() || gia <= 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        Mon mon = new Mon(id, ten, loai, capDo, gia, isDangBan());
        monService.capNhat(mon);
        loadData();
        notifyMenuChanged();
        JOptionPane.showMessageDialog(this, "Sửa món thành công!");
    }

    private void xoaMon() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn món cần xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa món này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = (int) tableModel.getValueAt(row, 0);
            monService.xoa(id);
            loadData();
            clearInput();
            notifyMenuChanged();
            JOptionPane.showMessageDialog(this, "Xóa món thành công!");
        }
    }

    private void notifyMenuChanged() {
        if (onMenuChanged != null) {
            onMenuChanged.run();
        }
    }

    private void clearInput() {
        txtTen.setText("");
        cbLoai.setSelectedIndex(0);
        cbCapDo.setSelectedIndex(0);
        txtGia.setText("");
        cbTrangThai.setSelectedIndex(0);
        table.clearSelection();
    }
}
