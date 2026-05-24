package view.frame;

import model.NhanVien;
import service.NhanVienService;
import util.UiTheme;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField txtTaiKhoan;
    private JPasswordField txtMatKhau;
    private NhanVienService nhanVienService;

    public LoginFrame() {
        nhanVienService = new NhanVienService();
        initUI();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initUI() {
        setTitle("ĐĂNG NHẬP - QUÁN MÌ CAY TVT");
        setSize(420, 340);
        setResizable(false);
        getContentPane().setBackground(UiTheme.BLACK);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(UiTheme.PANEL);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiTheme.RED, 3),
                BorderFactory.createEmptyBorder(24, 28, 24, 28)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblTitle = new JLabel("TVT - QUẢN LÝ QUÁN MÌ CAY", SwingConstants.CENTER);
        UiTheme.title(lblTitle, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(lblTitle, gbc);

        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel lblTk = new JLabel("Tài khoản:");
        UiTheme.label(lblTk);
        mainPanel.add(lblTk, gbc);
        txtTaiKhoan = new JTextField(16);
        txtTaiKhoan.setFont(UiTheme.bold(13));
        txtTaiKhoan.setBackground(UiTheme.CARD);
        txtTaiKhoan.setForeground(UiTheme.TEXT);
        txtTaiKhoan.setCaretColor(UiTheme.RED_GLOW);
        txtTaiKhoan.setBorder(BorderFactory.createLineBorder(UiTheme.RED_DARK, 1));
        gbc.gridx = 1;
        mainPanel.add(txtTaiKhoan, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel lblMk = new JLabel("Mật khẩu:");
        UiTheme.label(lblMk);
        mainPanel.add(lblMk, gbc);
        txtMatKhau = new JPasswordField(16);
        txtMatKhau.setFont(UiTheme.bold(13));
        txtMatKhau.setBackground(UiTheme.CARD);
        txtMatKhau.setForeground(UiTheme.TEXT);
        txtMatKhau.setCaretColor(UiTheme.RED_GLOW);
        txtMatKhau.setBorder(BorderFactory.createLineBorder(UiTheme.RED_DARK, 1));
        gbc.gridx = 1;
        mainPanel.add(txtMatKhau, gbc);

        JButton btnLogin = new JButton("ĐĂNG NHẬP");
        UiTheme.primaryButton(btnLogin);
        btnLogin.setPreferredSize(new Dimension(200, 40));
        btnLogin.addActionListener(e -> login());

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        mainPanel.add(btnLogin, gbc);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(UiTheme.BLACK);
        wrapper.add(mainPanel);
        add(wrapper);
        getRootPane().setDefaultButton(btnLogin);
    }

    private void login() {
        String taiKhoan = txtTaiKhoan.getText().trim();
        String matKhau = new String(txtMatKhau.getPassword());

        if (taiKhoan.isEmpty() || matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ tài khoản và mật khẩu!");
            return;
        }

        NhanVien nhanVien = nhanVienService.dangNhap(taiKhoan, matKhau);

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
