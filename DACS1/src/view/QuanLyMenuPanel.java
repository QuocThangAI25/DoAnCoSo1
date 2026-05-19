package view;

import dao.MonDao;
import model.Mon;
import utils.NumberUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class QuanLyMenuPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField txtTen, txtGia;
    private JComboBox<String> cbLoai, cbCapDo;
    private MonDao monDAO;
    
    public QuanLyMenuPanel() {
        monDAO = new MonDao();
        initUI();
        loadData();
    }
    
    private void initUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Table
        String[] columns = {"ID", "Tên món", "Loại", "Cấp độ", "Giá", "Trạng thái"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        
        // Panel nhập liệu
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder("Thêm/Sửa món"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(new JLabel("Tên món:"), gbc);
        txtTen = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(txtTen, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(new JLabel("Loại:"), gbc);
        cbLoai = new JComboBox<>(new String[]{"Mi Cay", "Nuoc Uong", "Topping"});
        gbc.gridx = 1;
        inputPanel.add(cbLoai, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(new JLabel("Cấp độ cay:"), gbc);
        cbCapDo = new JComboBox<>(new String[]{"0 (Không cay)", "3", "5", "7 (Đặc biệt)", "10 (Siêu cay)", "Không áp dụng"});
        gbc.gridx = 1;
        inputPanel.add(cbCapDo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        inputPanel.add(new JLabel("Giá:"), gbc);
        txtGia = new JTextField(20);
        gbc.gridx = 1;
        inputPanel.add(txtGia, gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        JButton btnLamMoi = new JButton("Làm mới");
        
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
        
        // Sự kiện chọn dòng
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtTen.setText(tableModel.getValueAt(row, 1).toString());
                    cbLoai.setSelectedItem(tableModel.getValueAt(row, 2).toString());
                    cbCapDo.setSelectedItem(tableModel.getValueAt(row, 3).toString());
                    txtGia.setText(tableModel.getValueAt(row, 4).toString().replace("₫", "").replace(".", "").trim());
                }
            }
        });
    }
    
    private void loadData() {
        tableModel.setRowCount(0);
        List<Mon> monList = monDAO.getAll();
        
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
        if (capDoStr.contains("0")) return 0;
        if (capDoStr.contains("3")) return 3;
        if (capDoStr.contains("5")) return 5;
        if (capDoStr.contains("7")) return 7;
        if (capDoStr.contains("10")) return 10;
        return -1;
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
        
        Mon mon = new Mon(0, ten, loai, capDo, gia, true);
        monDAO.add(mon);
        loadData();
        clearInput();
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
        
        Mon mon = new Mon(id, ten, loai, capDo, gia, true);
        monDAO.update(mon);
        loadData();
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
            monDAO.delete(id);
            loadData();
            clearInput();
            JOptionPane.showMessageDialog(this, "Xóa món thành công!");
        }
    }
    
    private void clearInput() {
        txtTen.setText("");
        cbLoai.setSelectedIndex(0);
        cbCapDo.setSelectedIndex(0);
        txtGia.setText("");
        table.clearSelection();
    }
}