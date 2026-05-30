package view.dialog;

import service.NhanVienService;
import util.UiTheme;
import view.frame.MainFrame;

import javax.swing.*;
import java.awt.*;

public class DoiMatKhauDialog extends JDialog {
    private JPasswordField txtCu, txtMoi, txtXacNhan;
    private NhanVienService service = new NhanVienService();
    private MainFrame mainFrame;

    public DoiMatKhauDialog(MainFrame owner) {
        super(owner, "Đổi mật khẩu", true);
        this.mainFrame = owner;
        initUI();
    }

    private void initUI() {
        setSize(350, 250);
        setLocationRelativeTo(mainFrame);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UiTheme.PANEL);

        JPanel content = new JPanel(new GridLayout(3, 2, 10, 10));
        content.setOpaque(false);
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtCu = new JPasswordField(15);
        txtMoi = new JPasswordField(15);
        txtXacNhan = new JPasswordField(15);

        // Style các trường
        stylePassField(txtCu);
        stylePassField(txtMoi);
        stylePassField(txtXacNhan);

        content.add(new JLabel("Mật khẩu cũ:"));
        content.add(txtCu);
        content.add(new JLabel("Mật khẩu mới:"));
        content.add(txtMoi);
        content.add(new JLabel("Xác nhận mới:"));
        content.add(txtXacNhan);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setOpaque(false);
        JButton btnLuu = new JButton("Lưu thay đổi");
        UiTheme.primaryButton(btnLuu);
        btnLuu.addActionListener(e -> doiMatKhau());

        btnPanel.add(btnLuu);
        add(content, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);
    }

    private void stylePassField(JPasswordField f) {
        f.setFont(UiTheme.plain(14));
        f.setBackground(UiTheme.CARD);
        f.setForeground(UiTheme.TEXT);
        f.setBorder(BorderFactory.createLineBorder(UiTheme.RED_DARK));
    }

    private void doiMatKhau() {
        String oldPass = new String(txtCu.getPassword());
        String newPass = new String(txtMoi.getPassword());
        String confirmPass = new String(txtXacNhan.getPassword());

        if (!service.xacThucMatKhau(mainFrame.getNhanVien().getTaiKhoan(), oldPass)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu cũ không đúng!");
            return;
        }

        if (newPass.length() < 6) {
            JOptionPane.showMessageDialog(this, "Mật khẩu mới phải từ 6 ký tự!");
            return;
        }

        if (!newPass.equals(confirmPass)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!");
            return;
        }

        service.capNhatMatKhau(mainFrame.getNhanVien().getId(), newPass);
        JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công!");
        dispose();
    }
}