package view.panel;

import model.Ban;
import service.QuanLyBanService;
import util.UiTheme;
import view.frame.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        updateFloorButtonsStyle();

        // Tiêu đề SƠ ĐỒ BÀN (Ở giữa)
        JLabel lblTitle = new JLabel("SƠ ĐỒ BÀN", SwingConstants.CENTER);
        UiTheme.title(lblTitle, 24);

        JPanel eastSpacer = new JPanel();
        eastSpacer.setOpaque(false);
        eastSpacer.setPreferredSize(new Dimension(280, 10));

        headerPanel.add(floorPanel, BorderLayout.WEST);
        headerPanel.add(lblTitle, BorderLayout.CENTER);
        headerPanel.add(eastSpacer, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // --- 2. GRID BÀN ---
        panelBan = new JPanel(new GridLayout(4, 5, 12, 12));
        panelBan.setOpaque(false);
        add(panelBan, BorderLayout.CENTER);
    }

    private void updateFloorButtonsStyle() {
        for (int i = 0; i < floorButtons.size(); i++) {
            JButton btn = floorButtons.get(i);
            if (i + 1 == currentFloor) {
                UiTheme.primaryButton(btn);
            } else {
                UiTheme.ghostButton(btn);
            }
        }
    }

    public void loadData() {
        panelBan.removeAll();
        danhSachBan = QuanLyBanService.getAllBan();

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
        boolean datTruoc = ban.getTrangThai().equals("Đặt trước");

        String html = "<html><center><div style='font-family:Segoe UI, sans-serif; margin-bottom: 2px;'>"
                + "<b style='font-size:16px'>Bàn " + ban.getSoBan() + "</b></div>"
                + "<span style='font-size:11px;'>" + ban.getTrangThai() + "</span></center></html>";

        JButton btn = new JButton(html);
        UiTheme.banButton(btn, trong);

        // 🟢 CẬP NHẬT MÀU CAM CHO TRẠNG THÁI "ĐẶT TRƯỚC"
        if (datTruoc) {
            btn.setBackground(new Color(230, 126, 34)); // Màu cam rực chuẩn UI
            btn.setForeground(Color.WHITE);
            btn.setBorder(BorderFactory.createLineBorder(new Color(211, 84, 0), 2));
        }

        // --- SỰ KIỆN CLICK CHUỘT TRÁI (Chọn bàn thông thường) ---
        btn.addActionListener(e -> {
            if (datTruoc) {
                // Nếu bấm chuột trái vào bàn Cam, hỏi xác nhận bảo vệ thao tác
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Bàn số " + ban.getSoBan() + " đang được ĐẶT TRƯỚC.\nKhách đã đến và nhận bàn?",
                        "Xác nhận nhận bàn", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    QuanLyBanService.updateTrangThaiBan(ban.getSoBan(), "Đang phục vụ",
                            mainFrame.getNhanVien().getId());
                    refresh();
                    mainFrame.chonBan(ban.getSoBan());
                }
            } else {
                mainFrame.chonBan(ban.getSoBan());
            }
        });

        // --- SỰ KIỆN CLICK CHUỘT PHẢI (Menu ngữ cảnh) ---
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    showTableContextMenu(e, btn, ban);
                }
            }
        });

        return btn;
    }

    // 🟢 HÀM HIỂN THỊ MENU CHUỘT PHẢI THÔNG MINH
    private void showTableContextMenu(MouseEvent e, JButton btn, Ban ban) {
        JPopupMenu popupMenu = new JPopupMenu();

        if (ban.getTrangThai().equals("Trống")) {
            // Chỉ bàn trống mới được Đặt trước
            JMenuItem itemDatTruoc = new JMenuItem("📌 Đánh dấu Đặt trước");
            itemDatTruoc.addActionListener(evt -> {
                QuanLyBanService.updateTrangThaiBan(ban.getSoBan(), "Đặt trước", -1);
                refresh();
            });
            popupMenu.add(itemDatTruoc);

        } else if (ban.getTrangThai().equals("Đặt trước")) {
            // Bàn đặt trước có 2 tùy chọn: Nhận bàn hoặc Hủy
            JMenuItem itemKhachDen = new JMenuItem("✅ Khách đã đến (Mở bàn)");
            itemKhachDen.addActionListener(evt -> {
                QuanLyBanService.updateTrangThaiBan(ban.getSoBan(), "Đang phục vụ", mainFrame.getNhanVien().getId());
                refresh();
                mainFrame.chonBan(ban.getSoBan());
            });

            JMenuItem itemHuy = new JMenuItem("❌ Hủy đặt trước");
            itemHuy.addActionListener(evt -> {
                QuanLyBanService.updateTrangThaiBan(ban.getSoBan(), "Trống", -1);
                refresh();
            });

            popupMenu.add(itemKhachDen);
            popupMenu.addSeparator(); // Dòng kẻ ngang phân cách
            popupMenu.add(itemHuy);

        } else if (ban.getTrangThai().equals("Đang phục vụ")) {
            // Bàn đang có khách thì disable để thu ngân biết trạng thái
            JMenuItem itemDangPhucVu = new JMenuItem("Bàn đang phục vụ (Không thể đặt)");
            itemDangPhucVu.setEnabled(false);
            popupMenu.add(itemDangPhucVu);
        }

        // Hiển thị Popup ngay tại tọa độ chuột
        popupMenu.show(btn, e.getX(), e.getY());
    }

    public void refresh() {
        loadData();
    }
}