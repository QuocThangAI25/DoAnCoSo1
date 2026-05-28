package view.panel;

import model.Ban;
import service.QuanLyBanService;
import util.UiTheme;
import view.frame.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BanPanel extends JPanel {
    private MainFrame mainFrame;
    private JPanel panelBan;
    private List<Ban> danhSachBan;

    // Khai báo biến quản lý Tầng
    private int currentFloor = 1;
    private final int TABLES_PER_FLOOR = 20;
    private List<JButton> floorButtons = new ArrayList<>();

    public BanPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initUI();
        loadData();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        UiTheme.panel(this);
        setBorder(BorderFactory.createEmptyBorder(12, 15, 15, 15));

        // --- 1. HEADER PANEL (Thanh chọn Tầng + Tiêu đề) ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        // Khu vực chọn Tầng (Bên trái)
        JPanel floorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        floorPanel.setOpaque(false);

        String[] floors = { "Tầng 1", "Tầng 2", "Tầng 3" };
        for (int i = 0; i < floors.length; i++) {
            int floorIndex = i + 1;
            JButton btnFloor = new JButton(floors[i]);
            btnFloor.setPreferredSize(new Dimension(85, 32));

            btnFloor.addActionListener(e -> {
                currentFloor = floorIndex;
                updateFloorButtonsStyle();
                loadData();
            });
            floorButtons.add(btnFloor);
            floorPanel.add(btnFloor);
        }
        updateFloorButtonsStyle(); // Cập nhật màu sắc cho nút Tầng 1 lúc vừa khởi động

        // Tiêu đề SƠ ĐỒ BÀN (Ở giữa)
        JLabel lblTitle = new JLabel("SƠ ĐỒ BÀN", SwingConstants.CENTER);
        UiTheme.title(lblTitle, 24);

        // Tạo một panel đệm bên phải để ép Tiêu đề vào chính giữa màn hình (cân bằng
        // với floorPanel)
        JPanel eastSpacer = new JPanel();
        eastSpacer.setOpaque(false);
        eastSpacer.setPreferredSize(new Dimension(280, 10));

        headerPanel.add(floorPanel, BorderLayout.WEST);
        headerPanel.add(lblTitle, BorderLayout.CENTER);
        headerPanel.add(eastSpacer, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // --- 2. GRID BÀN ---
        // Lưới 4 hàng x 5 cột = 20 bàn, khoảng cách giữa các ô là 12px
        panelBan = new JPanel(new GridLayout(4, 5, 12, 12));
        panelBan.setOpaque(false);
        add(panelBan, BorderLayout.CENTER);
    }

    // Hàm cập nhật màu sắc khi chuyển tầng
    private void updateFloorButtonsStyle() {
        for (int i = 0; i < floorButtons.size(); i++) {
            JButton btn = floorButtons.get(i);
            if (i + 1 == currentFloor) {
                UiTheme.primaryButton(btn); // Nút màu đỏ cho tầng đang chọn
            } else {
                UiTheme.ghostButton(btn); // Nút màu tối cho tầng chưa chọn
            }
        }
    }

    public void loadData() {
        panelBan.removeAll();
        danhSachBan = QuanLyBanService.getAllBan();

        // Tính toán khoảng ID bàn hiển thị cho tầng hiện tại
        int start = (currentFloor - 1) * TABLES_PER_FLOOR + 1;
        int end = currentFloor * TABLES_PER_FLOOR;
        int count = 0;

        for (Ban ban : danhSachBan) {
            if (ban.getSoBan() >= start && ban.getSoBan() <= end) {
                JButton btnBan = taoNutBan(ban);
                panelBan.add(btnBan);
                count++;
            }
        }

        // Trick: Thêm các khối tàng hình để giữ nguyên cấu trúc 4x5 nếu tầng đó chưa
        // tạo đủ 20 bàn
        while (count < TABLES_PER_FLOOR) {
            JPanel emptySlot = new JPanel();
            emptySlot.setOpaque(false);
            panelBan.add(emptySlot);
            count++;
        }

        panelBan.revalidate();
        panelBan.repaint();
    }

    private JButton taoNutBan(Ban ban) {
        boolean trong = ban.getTrangThai().equals("Trống");

        // Tùy chỉnh lại HTML để chữ to, rõ ràng và căn giữa đẹp hơn
        String html = "<html><center><div style='font-family:Segoe UI, sans-serif; margin-bottom: 2px;'>"
                + "<b style='font-size:16px'>Bàn " + ban.getSoBan() + "</b></div>"
                + "<span style='font-size:11px;'>" + ban.getTrangThai() + "</span></center></html>";

        JButton btn = new JButton(html);
        UiTheme.banButton(btn, trong);
        btn.addActionListener(e -> mainFrame.chonBan(ban.getSoBan()));
        return btn;
    }

    public void refresh() {
        loadData();
    }
}