package view.dialog;

import service.NhanVienService;
import util.UiTheme;

import javax.swing.*;
import java.awt.*;

public class CapNhatMatKhauDialog extends JDialog {
    private JPasswordField txtMoi, txtXacNhan;
    private int nhanVienId;
    private NhanVienService service;

    public CapNhatMatKhauDialog(Frame owner, int nhanVienId, NhanVienService service) {
        super(owner, "Cập nhật mật khẩu", true);
        this.nhanVienId = nhanVienId;
        this.service = service;
        initUI();
        setSize(360, 180);
        setLocationRelativeTo(owner);
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridBagLayout());
        UiTheme.card(panel);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Mật khẩu mới:"), gbc);
        txtMoi = new JPasswordField(18);
        stylePassField(txtMoi);
        gbc.gridx = 1;
        panel.add(txtMoi, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Xác nhận mật khẩu:"), gbc);
        txtXacNhan = new JPasswordField(18);
        stylePassField(txtXacNhan);
        gbc.gridx = 1;
        panel.add(txtXacNhan, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
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
        String a = new String(txtMoi.getPassword()).trim();
        String b = new String(txtXacNhan.getPassword()).trim();
        if (a.isEmpty() || b.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ mật khẩu mới và xác nhận!");
            return;
        }
        if (!a.equals(b)) {
            JOptionPane.showMessageDialog(this, "Mật khẩu xác nhận không khớp!");
            return;
        }
        if (a.length() < 4) {
            JOptionPane.showMessageDialog(this, "Mật khẩu phải có ít nhất 4 ký tự!");
            return;
        }

        service.capNhatMatKhau(nhanVienId, a);
        JOptionPane.showMessageDialog(this, "Cập nhật mật khẩu thành công!");
        dispose();
    }
}
