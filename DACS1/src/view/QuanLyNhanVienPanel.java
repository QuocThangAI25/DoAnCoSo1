package view;

import dao.NhanVienDao;
import model.NhanVien;

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
    private NhanVienDao nhanVienDAO;
    
    public QuanLyNhanVienPanel() {
        nhanVienDAO = new NhanVienDao();
        initUI();
        loadData();
    }
    
    private void initUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Table
        String[] columns = {"ID", "Họ tên", "Tài khoản", "Vai trò", "Trạng thái"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel nhập liệu
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thêm/Sửa nhân viên"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Họ tên
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Họ tên:"), gbc);
        txtTen = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(txtTen, gbc);
        
        // Tài khoản
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Tài khoản:"), gbc);
        txtTaiKhoan = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(txtTaiKhoan, gbc);
        
        // Mật khẩu
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Mật khẩu:"), gbc);
        txtMatKhau = new JPasswordField(20);
        gbc.gridx = 1;
        inputPanel.add(txtMatKhau, gbc);
        
        // Vai trò
        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(new JLabel("Vai trò:"), gbc);
        cbVaiTro = new JComboBox<>(new String[]{"nhan_vien", "admin"});
        gbc.gridx = 1;
        inputPanel.add(cbVaiTro, gbc);
        
        // Trạng thái
        gbc.gridx = 0; gbc.gridy = 4;
        inputPanel.add(new JLabel("Trạng thái:"), gbc);
        cbTrangThai = new JComboBox<>(new String[]{"Đang làm việc", "Đã nghỉ"});
        gbc.gridx = 1;
        inputPanel.add(cbTrangThai, gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Cập nhật");
        JButton btnXoa = new JButton("Xóa");
        JButton btnLamMoi = new JButton("Làm mới");
        
        btnThem.addActionListener(e -> themNhanVien());
        btnSua.addActionListener(e -> suaNhanVien());
        btnXoa.addActionListener(e -> xoaNhanVien());
        btnLamMoi.addActionListener(e -> clearInput());
        
        btnPanel.add(btnThem);
        btnPanel.add(btnSua);
        btnPanel.add(btnXoa);
        btnPanel.add(btnLamMoi);
        inputPanel.add(btnPanel, gbc);
        
        add(inputPanel, BorderLayout.SOUTH);
        
        // Sự kiện chọn dòng
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
    }
    
    private void loadData() {
        tableModel.setRowCount(0);
        List<NhanVien> list = nhanVienDAO.getAll();
        
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
        
        // Kiểm tra tài khoản đã tồn tại
        if (nhanVienDAO.getByTaiKhoan(taiKhoan) != null) {
            JOptionPane.showMessageDialog(this, "Tài khoản đã tồn tại!");
            return;
        }
        
        NhanVien nv = new NhanVien(0, ten, taiKhoan, matKhau, vaiTro, null, true);
        nhanVienDAO.add(nv);
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
        
        // Cập nhật thông tin
        NhanVien nv = new NhanVien(id, ten, taiKhoan, "", vaiTro, null, trangThai);
        nhanVienDAO.update(nv);
        
        // Đổi mật khẩu nếu có nhập mới
        if (!matKhau.isEmpty()) {
            if (matKhau.length() < 4) {
                JOptionPane.showMessageDialog(this, "Mật khẩu mới phải có ít nhất 4 ký tự!");
                return;
            }
            nhanVienDAO.updatePassword(id, matKhau);
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
        
        // Không cho xóa tài khoản admin đang đăng nhập
        if (ten.equals("Quản trị viên")) {
            JOptionPane.showMessageDialog(this, "Không thể xóa tài khoản Quản trị viên!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc muốn xóa nhân viên " + ten + "?", 
            "Xác nhận xóa", 
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            nhanVienDAO.delete(id);
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