package view.dialog;

import service.NhanVienService;
import util.UiTheme;

import javax.swing.*;
import java.awt.*;

public class DoiMatKhauLoginDialog extends JDialog {
    private JTextField txtTaiKhoan;
    private JPasswordField txtCu, txtMoi, txtXacNhan;
    private NhanVienService service;

    public DoiMatKhauLoginDialog(Frame owner, NhanVienService service, String initialTaiKhoan) {
        super(owner, "Đổi mật khẩu", true);
        this.service = service;
        initUI(initialTaiKhoan);
        setSize(380, 300);
        setLocationRelativeTo(owner);
    }

    private void initUI(String initialTaiKhoan) {
        JPanel panel = new JPanel(new GridBagLayout());
        UiTheme.card(panel);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Tài khoản:"), gbc);
        txtTaiKhoan = new JTextField(18);
        txtTaiKhoan.setText(initialTaiKhoan == null ? "" : initialTaiKhoan);
        txtTaiKhoan.setFont(UiTheme.bold(13));
        txtTaiKhoan.setBackground(UiTheme.PANEL);
        txtTaiKhoan.setForeground(UiTheme.TEXT);
        txtTaiKhoan.setBorder(BorderFactory.createLineBorder(UiTheme.RED_DARK, 1));
        gbc.gridx = 1;
        panel.add(txtTaiKhoan, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Mật khẩu cũ:"), gbc);
        txtCu = new JPasswordField(18);
        stylePassField(txtCu);
        gbc.gridx = 1;
        panel.add(txtCu, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Mật khẩu mới:"), gbc);
        txtMoi = new JPasswordField(18);
        stylePassField(txtMoi);
        gbc.gridx = 1;
        panel.add(txtMoi, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Xác nhận mật khẩu:"), gbc);
        txtXacNhan = new JPasswordField(18);
        stylePassField(txtXacNhan);
        gbc.gridx = 1;
        panel.add(txtXacNhan, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        UiTheme.panel(btnPanel);
        JButton btnLuu = new JButton("Lưu");
        JButton btnHuy = new JButton("Hủy");
        UiTheme.primaryButton(btnLuu);
        UiTheme.ghostButton(btnHuy);
        btnLuu.addActionListener(e -> save());
        btnHuy.addActionListener(e -> dispose());
        btnPanel.add(btnHuy);
        btnPanel.add(btnLuu);
        panel.add(btnPanel, gbc);

        setContentPane(panel);
    }

    private void stylePassField(JPasswordField f) {
        f.setFont(UiTheme.bold(13));
        f.setBackground(UiTheme.PANEL);
        f.setForeground(UiTheme.TEXT);
        f.setCaretColor(UiTheme.RED_GLOW);
        f.setBorder(BorderFactory.createLineBorder(UiTheme.RED_DARK, 1));
    }

    private void save() {
        String taiKhoan = txtTaiKhoan.getText().trim();
        String oldPass = new String(txtCu.getPassword());
        String newPass = new String(txtMoi.getPassword());
        String confirm = new String(txtXacNhan.getPassword());

        if (taiKhoan.isEmpty() || oldPass.isEmpty() || newPass.isEmpty() || confirm.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        if (!service.xacThucMatKhau(taiKhoan, oldPass)) {
            JOptionPane.showMessageDialog(this, "Tài khoản hoặc mật khẩu cũ không đúng!");
            return;
        }

        if (newPass.length() < 6) {
            JOptionPane.showMessageDialog(this, "Mật khẩu mới phải từ 6 ký tự!");
            return;
        }

        if (!newPass.equals(confirm)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!");
            return;
        }

        // Lấy id bằng cách tìm theo tài khoản
        try {
            int id = service.getByTaiKhoan(taiKhoan).getId();
            service.capNhatMatKhau(id, newPass);
            JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công!");
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy tài khoản hoặc lỗi hệ thống!");
        }
    }
}
