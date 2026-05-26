package view.panel;

import dao.MonDao;
import model.Mon;
import service.ThanhToanService;
import util.NumberUtils;
import util.UiTheme;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class MenuPanel extends JPanel {
    private HoaDonPanel hoaDonPanel;
    private int currentBan = -1;
    private JTabbedPane tabbedPane;
    private JPanel panelMiCay, panelNuocUong, panelTopping;
    private MonDao monDAO;

    public MenuPanel(HoaDonPanel hoaDonPanel) {
        this.hoaDonPanel = hoaDonPanel;
        monDAO = new MonDao();
        initUI();
        loadMenu();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("MENU GỌI MÓN"));

        tabbedPane = new JTabbedPane();

        panelMiCay = new JPanel(new GridLayout(0, 2, 15, 15));
        panelMiCay.setBackground(UiTheme.PANEL);

        panelNuocUong = new JPanel(new GridLayout(0, 2, 15, 15));
        panelNuocUong.setBackground(UiTheme.PANEL);

        panelTopping = new JPanel(new GridLayout(0, 2, 15, 15));
        panelTopping.setBackground(UiTheme.PANEL);

        JScrollPane scrollMiCay = new JScrollPane(panelMiCay);
        JScrollPane scrollNuocUong = new JScrollPane(panelNuocUong);
        JScrollPane scrollTopping = new JScrollPane(panelTopping);

        scrollMiCay.getVerticalScrollBar().setUnitIncrement(16);
        scrollNuocUong.getVerticalScrollBar().setUnitIncrement(16);
        scrollTopping.getVerticalScrollBar().setUnitIncrement(16);

        tabbedPane.addTab("🍜 Mì Cay", scrollMiCay);
        tabbedPane.addTab("🥤 Nước Uống", scrollNuocUong);
        tabbedPane.addTab("🧀 Topping", scrollTopping);

        add(tabbedPane, BorderLayout.CENTER);
    }

    private void loadMenu() {
        loadMonTheoLoai("Mi Cay", panelMiCay);
        loadMonTheoLoai("Nuoc Uong", panelNuocUong);
        loadMonTheoLoai("Topping", panelTopping);
    }

    private void loadMonTheoLoai(String loai, JPanel panel) {
        panel.removeAll();
        List<Mon> monList = monDAO.getByLoai(loai);

        for (Mon mon : monList) {
            JPanel itemPanel = createMenuItemPanel(mon);
            panel.add(itemPanel);
        }

        panel.revalidate();
        panel.repaint();
    }

    private JPanel createMenuItemPanel(Mon mon) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        panel.setPreferredSize(new Dimension(280, 120));

        // Panel ảnh
        JPanel imagePanel = new JPanel();
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setPreferredSize(new Dimension(80, 80));

        JLabel imgLabel = new JLabel();
        imgLabel.setHorizontalAlignment(JLabel.CENTER);

        // Thử load ảnh
        ImageIcon icon = loadImageForMon(mon);
        if (icon != null) {
            Image img = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(img));
        } else {
            // Dùng emoji nếu không có ảnh
            imgLabel.setText(getEmojiForMon(mon));
            imgLabel.setFont(new Font("Segoe UI", Font.PLAIN, 45));
        }

        imagePanel.add(imgLabel);

        // Panel thông tin
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);

        JLabel lblTen = new JLabel(mon.getTenHienThi());
        lblTen.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTen.setForeground(new Color(50, 50, 50));

        JLabel lblGia = new JLabel(NumberUtils.formatVND(mon.getGia()));
        lblGia.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblGia.setForeground(new Color(220, 0, 0));

        infoPanel.add(lblTen);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(lblGia);

        // Panel số lượng
        JPanel orderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        orderPanel.setBackground(Color.WHITE);

        JSpinner spinnerSoLuong = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
        spinnerSoLuong.setPreferredSize(new Dimension(50, 28));
        spinnerSoLuong.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JButton btnOrder = new JButton("🛒 Gọi");
        btnOrder.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnOrder.setBackground(new Color(0, 150, 0));
        btnOrder.setForeground(Color.WHITE);
        btnOrder.setFocusPainted(false);
        btnOrder.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnOrder.addActionListener(e -> {
            int soLuong = (int) spinnerSoLuong.getValue();
            goiMon(mon, soLuong);
        });

        orderPanel.add(new JLabel("SL:"));
        orderPanel.add(spinnerSoLuong);
        orderPanel.add(btnOrder);

        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(orderPanel);

        panel.add(imagePanel, BorderLayout.WEST);
        panel.add(infoPanel, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Lấy emoji theo loại món
     */
    private String getEmojiForMon(Mon mon) {
        if (mon.getLoai().equals("Mi Cay")) {
            return "🍜";
        } else if (mon.getLoai().equals("Nuoc Uong")) {
            return "🥤";
        } else {
            return "🧀";
        }
    }

    /**
     * Tải ảnh cho món ăn
     */
    private ImageIcon loadImageForMon(Mon mon) {
        String fileName = "";

        // 1. Xác định tên file dựa trên thông tin món
        if (mon.getLoai().equals("Mi Cay")) {
            switch (mon.getCapDoCay()) {
                case 0:
                    fileName = "nen.jpg";
                    break;
                case 3:
                    fileName = "micayxuxich.jpg";
                    break;
                case 5:
                    fileName = "haisan.jpg";
                    break;
                case 7:
                    fileName = "thapcam.jpg";
                    break;
                case 10:
                    fileName = "micayholo.jpg";
                    break;
                default:
                    fileName = "mi-kim-chi-bo.jpg";
            }
        } else if (mon.getLoai().equals("Nuoc Uong")) {
            if (mon.getTen().contains("Trà Chanh"))
                fileName = "trachanh.jpg";
            else if (mon.getTen().contains("Trà Sữa"))
                fileName = "trasua.jpg";
            // else if (mon.getTen().contains("Sữa bò"))
            // fileName = "";
            // else
            // fileName = "nuoc_uong_default.jpg";
        } else if (mon.getLoai().equals("Topping")) {
            if (mon.getTen().contains("Phô Mai"))
                fileName = "tobokicheese.jpg";
            else if (mon.getTen().contains("Trứng"))
                fileName = "tokboky.jpg";
            else
                fileName = "topping_default.jpg";
        }

        // 2. Load ảnh từ thư mục src/images/
        // Dấu "/" đầu tiên chỉ định tìm từ gốc của Classpath (thư mục bin)
        String resourcePath = "/images/" + fileName;
        java.net.URL imgUrl = getClass().getResource(resourcePath);

        if (imgUrl != null) {
            return new ImageIcon(imgUrl);
        } else {
            System.err.println("Cảnh báo: Không tìm thấy ảnh tại đường dẫn: " + resourcePath);
            return null;
        }
    }

    private void goiMon(Mon mon, int soLuong) {
        if (currentBan == -1) {
            JOptionPane.showMessageDialog(this,
                    "⚠️ Vui lòng chọn bàn trước khi gọi món!",
                    "Chưa chọn bàn",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (hoaDonPanel.getCurrentHoaDonId() == -1) {
            JOptionPane.showMessageDialog(this,
                    "⚠️ Vui lòng chọn bàn hợp lệ!",
                    "Lỗi",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        ThanhToanService.themMonVaoHoaDon(hoaDonPanel.getCurrentHoaDonId(), mon, soLuong);
        hoaDonPanel.loadHoaDon(hoaDonPanel.getCurrentHoaDonId());

        JOptionPane.showMessageDialog(this,
                "✅ Đã thêm " + soLuong + " phần " + mon.getTenHienThi() + " vào hóa đơn!",
                "Thành công",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void setCurrentBan(int ban) {
        this.currentBan = ban;
    }

    public void refreshMenu() {
        loadMenu();
    }
}