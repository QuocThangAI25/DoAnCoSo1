package view;

import dao.NhanVienDao;
import model.NhanVien;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField txtTaiKhoan;
    private JPasswordField txtMatKhau;
    private NhanVienDao nhanVienDAO;
    
    public LoginFrame() {
        nhanVienDAO = new NhanVienDao();
        initUI();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void initUI() {
        setTitle("ĐĂNG NHẬP - QUÁN MÌ CAY TVT");
        setSize(400, 300);
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Tiêu đề
        JLabel lblTitle = new JLabel("TVT - QUẢN LÝ QUÁN MÌ CAY");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(new Color(255, 69, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(lblTitle, gbc);
        
        // Tài khoản
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Tài khoản:"), gbc);
        txtTaiKhoan = new JTextField(15);
        gbc.gridx = 1;
        mainPanel.add(txtTaiKhoan, gbc);
        
        // Mật khẩu
        gbc.gridy = 2;
        gbc.gridx = 0;
        mainPanel.add(new JLabel("Mật khẩu:"), gbc);
        txtMatKhau = new JPasswordField(15);
        gbc.gridx = 1;
        mainPanel.add(txtMatKhau, gbc);
        
        // Nút đăng nhập
        JButton btnLogin = new JButton("ĐĂNG NHẬP");
        btnLogin.setBackground(new Color(0, 150, 0));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.addActionListener(e -> login());
        
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        mainPanel.add(btnLogin, gbc);
        
        add(mainPanel);
        
        // Phím Enter để đăng nhập
        getRootPane().setDefaultButton(btnLogin);
    }
    
    private void login() {
        String taiKhoan = txtTaiKhoan.getText().trim();
        String matKhau = new String(txtMatKhau.getPassword());
        
        if (taiKhoan.isEmpty() || matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tài khoản và mật khẩu!");
            return;
        }
        
        NhanVien nhanVien = nhanVienDAO.authenticate(taiKhoan, matKhau);
        
        if (nhanVien != null) {
            JOptionPane.showMessageDialog(this, "Chào mừng " + nhanVien.getTen() + "!");
            this.dispose();
            MainFrame mainFrame = new MainFrame(nhanVien);
            mainFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!");
            txtMatKhau.setText("");
        }
    }
}