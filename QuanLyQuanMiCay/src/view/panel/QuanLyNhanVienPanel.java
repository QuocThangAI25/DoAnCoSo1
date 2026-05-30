package view.panel;

import model.NhanVien;
import service.NhanVienService;
import util.UiTheme;

import view.dialog.CapNhatMatKhauDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class QuanLyNhanVienPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtTen, txtTaiKhoan;
    private JPasswordField txtMatKhau;
    private JComboBox<String> cbVaiTro, cbTrangThai;
    private NhanVienService nhanVienService;
    private boolean loaded;

    public QuanLyNhanVienPanel() {
        nhanVienService = new NhanVienService();
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

        String[] columns = {"ID", "Họ tên", "Tài khoản", "Vai trò", "Trạng thái"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        UiTheme.table(table);
        table.setRowHeight(28);
        JScrollPane scrollPane = new JScrollPane(table);
        UiTheme.scrollPane(scrollPane);
        add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        UiTheme.card(inputPanel);
        inputPanel.setBorder(UiTheme.titledBorder("Thêm/Sửa nhân viên"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblTen = new JLabel("Họ tên:");
        UiTheme.label(lblTen);
        inputPanel.add(lblTen, gbc);
        txtTen = new JTextField(20);
        styleField(txtTen);
        gbc.gridx = 1;
        inputPanel.add(txtTen, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblTk = new JLabel("Tài khoản:");
        UiTheme.label(lblTk);
        inputPanel.add(lblTk, gbc);
        txtTaiKhoan = new JTextField(20);
        styleField(txtTaiKhoan);
        gbc.gridx = 1;
        inputPanel.add(txtTaiKhoan, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblMk = new JLabel("Mật khẩu:");
        UiTheme.label(lblMk);
        inputPanel.add(lblMk, gbc);
        txtMatKhau = new JPasswordField(20);
        stylePassword(txtMatKhau);
        gbc.gridx = 1;
        inputPanel.add(txtMatKhau, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        JLabel lblVt = new JLabel("Vai trò:");
        UiTheme.label(lblVt);
        inputPanel.add(lblVt, gbc);
        cbVaiTro = new JComboBox<>(new String[]{"nhan_vien", "admin"});
        gbc.gridx = 1;
        inputPanel.add(cbVaiTro, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        JLabel lblTt = new JLabel("Trạng thái:");
        UiTheme.label(lblTt);
        inputPanel.add(lblTt, gbc);
        cbTrangThai = new JComboBox<>(new String[]{"Đang làm việc", "Đã nghỉ"});
        gbc.gridx = 1;
        inputPanel.add(cbTrangThai, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new FlowLayout());
        UiTheme.panel(btnPanel);
        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Cập nhật");
        JButton btnXoa = new JButton("Xóa");
        JButton btnLamMoi = new JButton("Làm mới");
        JButton btnResetMk = new JButton("Reset mật khẩu");
        UiTheme.primaryButton(btnThem);
        UiTheme.outlineButton(btnSua);
        UiTheme.ghostButton(btnXoa);
        UiTheme.ghostButton(btnLamMoi);
        UiTheme.ghostButton(btnResetMk);

        btnThem.addActionListener(e -> themNhanVien());
        btnSua.addActionListener(e -> suaNhanVien());
        btnXoa.addActionListener(e -> xoaNhanVien());
        btnLamMoi.addActionListener(e -> clearInput());

        btnPanel.add(btnThem);
        btnPanel.add(btnSua);
        btnPanel.add(btnXoa);
        btnPanel.add(btnLamMoi);
        btnPanel.add(btnResetMk);
        inputPanel.add(btnPanel, gbc);

        add(inputPanel, BorderLayout.SOUTH);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtTen.setText(tableModel.getValueAt(row, 1).toString());
                    txtTaiKhoan.setText(tableModel.getValueAt(row, 2).toString());
                    cbVaiTro.setSelectedItem(tableModel.getValueAt(row, 3).toString());
                    cbTrangThai.setSelectedItem(tableModel.getValueAt(row, 4).toString());
                    txtMatKhau.setText("");
                }
            }
        });

        // Reset mật khẩu về mặc định
        btnResetMk.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần reset mật khẩu!");
                return;
            }
            int id = (int) tableModel.getValueAt(row, 0);
            String ten = tableModel.getValueAt(row, 1).toString();
            int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn reset mật khẩu của " + ten + " về mặc định?",
                "Xác nhận reset",
                JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                nhanVienService.resetMatKhau(id);
                loadData();
                JOptionPane.showMessageDialog(this, "Reset mật khẩu thành công! Mật khẩu mặc định là 123456");
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

    private void stylePassword(JPasswordField field) {
        field.setFont(UiTheme.bold(13));
        field.setBackground(UiTheme.PANEL);
        field.setForeground(UiTheme.TEXT);
        field.setCaretColor(UiTheme.RED_GLOW);
        field.setBorder(BorderFactory.createLineBorder(UiTheme.RED_DARK, 1));
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<NhanVien> list = nhanVienService.getAll();

        for (NhanVien nv : list) {
            String trangThai = nv.isTrangThai() ? "Đang làm việc" : "Đã nghỉ";
            tableModel.addRow(new Object[]{
                nv.getId(),
                nv.getTen(),
                nv.getTaiKhoan(),
                nv.getVaiTro(),
                trangThai
            });
        }
    }

    private void themNhanVien() {
        String ten = txtTen.getText().trim();
        String taiKhoan = txtTaiKhoan.getText().trim();
        String matKhau = new String(txtMatKhau.getPassword());
        String vaiTro = cbVaiTro.getSelectedItem().toString();

        if (ten.isEmpty() || taiKhoan.isEmpty() || matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        if (matKhau.length() < 4) {
            JOptionPane.showMessageDialog(this, "Mật khẩu phải có ít nhất 4 ký tự!");
            return;
        }

        if (nhanVienService.getByTaiKhoan(taiKhoan) != null) {
            JOptionPane.showMessageDialog(this, "Tài khoản đã tồn tại!");
            return;
        }

        NhanVien nv = new NhanVien(0, ten, taiKhoan, matKhau, vaiTro, null, true);
        nhanVienService.them(nv);
        loadData();
        clearInput();
        JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
    }

    private void suaNhanVien() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần sửa!");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);
        String ten = txtTen.getText().trim();
        String taiKhoan = txtTaiKhoan.getText().trim();
        String matKhau = new String(txtMatKhau.getPassword());
        String vaiTro = cbVaiTro.getSelectedItem().toString();
        boolean trangThai = cbTrangThai.getSelectedItem().equals("Đang làm việc");

        if (ten.isEmpty() || taiKhoan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        NhanVien nv = new NhanVien(id, ten, taiKhoan, "", vaiTro, null, trangThai);
        nhanVienService.capNhat(nv);

        if (!matKhau.isEmpty()) {
            if (matKhau.length() < 4) {
                JOptionPane.showMessageDialog(this, "Mật khẩu mới phải có ít nhất 4 ký tự!");
                return;
            }
            nhanVienService.capNhatMatKhau(id, matKhau);
        }

        loadData();
        clearInput();
        JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thành công!");
    }

    private void xoaNhanVien() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa!");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);
        String ten = tableModel.getValueAt(row, 1).toString();

        if (ten.equals("Quản trị viên")) {
            JOptionPane.showMessageDialog(this, "Không thể xóa tài khoản Quản trị viên!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc muốn xóa nhân viên " + ten + "?",
            "Xác nhận xóa",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            nhanVienService.xoa(id);
            loadData();
            clearInput();
            JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!");
        }
    }

    private void clearInput() {
        txtTen.setText("");
        txtTaiKhoan.setText("");
        txtMatKhau.setText("");
        cbVaiTro.setSelectedIndex(0);
        cbTrangThai.setSelectedIndex(0);
        table.clearSelection();
    }
}
