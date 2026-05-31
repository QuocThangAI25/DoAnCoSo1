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
import java.util.Collections;
import java.util.List;

public class BanPanel extends JPanel {
    private MainFrame mainFrame;
    private JPanel panelBan;
    private List<Ban> danhSachBan;

    private int currentFloor = 1;
    private JPanel floorPanel; // Chứa các nút chọn tầng
    private List<JButton> floorButtons = new ArrayList<>();

    public BanPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initUI();
        refresh();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        UiTheme.panel(this);
        setBorder(BorderFactory.createEmptyBorder(12, 15, 15, 15));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        floorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        floorPanel.setOpaque(false);

        JLabel lblTitle = new JLabel("SƠ ĐỒ BÀN", SwingConstants.CENTER);
        UiTheme.title(lblTitle, 24);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightPanel.setOpaque(false);
        rightPanel.setPreferredSize(new Dimension(280, 32));

        JButton btnThemBan = new JButton("+ Thêm bàn mới");
        UiTheme.outlineButton(btnThemBan);
        btnThemBan.setPreferredSize(new Dimension(140, 32));
        btnThemBan.addActionListener(e -> handleThemBanMoi());

        rightPanel.add(btnThemBan);

        headerPanel.add(floorPanel, BorderLayout.WEST);
        headerPanel.add(lblTitle, BorderLayout.CENTER);
        headerPanel.add(rightPanel, BorderLayout.EAST);

        add(headerPanel, BorderLayout.NORTH);

        // 🟢 Cấu trúc cuộn màn hình khi lưới bàn phình to (Tránh nút bị ép lùn xuống)
        panelBan = new JPanel();
        panelBan.setOpaque(false);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(panelBan, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(wrapper);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Cuộn mượt

        add(scrollPane, BorderLayout.CENTER);
    }

    public void refresh() {
        danhSachBan = QuanLyBanService.getAllBan();
        buildFloorButtons();
        loadTables();
    }

    // 🟢 Tự động vẽ các nút chọn tầng dựa trên dữ liệu Database
    private void buildFloorButtons() {
        floorPanel.removeAll();
        floorButtons.clear();

        List<Integer> activeFloors = new ArrayList<>();
        activeFloors.add(1);
        activeFloors.add(2);
        activeFloors.add(3);

        if (danhSachBan != null) {
            for (Ban b : danhSachBan) {
                if (!activeFloors.contains(b.getTang())) {
                    activeFloors.add(b.getTang());
                }
            }
        }
        Collections.sort(activeFloors);

        for (int floorIndex : activeFloors) {
            JButton btnFloor = new JButton("Tầng " + floorIndex);
            btnFloor.setPreferredSize(new Dimension(85, 32));
            btnFloor.addActionListener(e -> {
                currentFloor = floorIndex;
                updateFloorButtonsStyle();
                loadTables();
            });
            floorButtons.add(btnFloor);
            floorPanel.add(btnFloor);
        }
        updateFloorButtonsStyle();
        floorPanel.revalidate();
        floorPanel.repaint();
    }

    private void updateFloorButtonsStyle() {
        for (int i = 0; i < floorButtons.size(); i++) {
            JButton btn = floorButtons.get(i);
            int floorOfBtn = Integer.parseInt(btn.getText().replace("Tầng ", ""));
            if (floorOfBtn == currentFloor) {
                UiTheme.primaryButton(btn);
            } else {
                UiTheme.ghostButton(btn);
            }
        }
    }

    // 🟢 Tự động tính toán số hàng để hiển thị kín bàn
    private void loadTables() {
        panelBan.removeAll();

        List<Ban> banTrongTang = new ArrayList<>();
        if (danhSachBan != null) {
            for (Ban ban : danhSachBan) {
                if (ban.getTang() == currentFloor) {
                    banTrongTang.add(ban);
                }
            }
        }

        int cols = 5;
        // Luôn giữ tối thiểu 20 slot (4 hàng) cho đẹp form, nếu dư thì tự cơi nới hàng
        int totalSlots = Math.max(20, banTrongTang.size());
        int rows = (int) Math.ceil((double) totalSlots / cols);

        panelBan.setLayout(new GridLayout(rows, cols, 12, 12));

        // Ép cứng chiều cao nút (VD: 75px) để lưới phình ra thay vì bị ép bẹp lại
        int buttonHeight = 75;
        panelBan.setPreferredSize(new Dimension(0, rows * buttonHeight + (rows - 1) * 12));

        int count = 0;
        for (Ban ban : banTrongTang) {
            panelBan.add(taoNutBan(ban));
            count++;
        }

        while (count < rows * cols) {
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
        boolean hong = ban.getTrangThai().equals("Không hoạt động");

        String html = "<html><center><div style='font-family:Segoe UI, sans-serif; margin-bottom: 2px;'>"
                + "<b style='font-size:16px'>Bàn " + ban.getSoBan() + "</b></div>"
                + "<span style='font-size:11px;'>" + ban.getTrangThai() + "</span></center></html>";

        JButton btn = new JButton(html);
        UiTheme.banButton(btn, trong);

        if (datTruoc) {
            btn.setBackground(new Color(230, 126, 34));
            btn.setForeground(Color.WHITE);
            btn.setBorder(BorderFactory.createLineBorder(new Color(211, 84, 0), 2));
        } else if (hong) {
            btn.setBackground(new Color(60, 60, 60));
            btn.setForeground(new Color(150, 150, 150));
            btn.setBorder(BorderFactory.createLineBorder(new Color(40, 40, 40), 2));
        }

        btn.addActionListener(e -> {
            if (hong) {
                JOptionPane.showMessageDialog(this, "Bàn này đang tạm khóa / không hoạt động!", "Thông báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (datTruoc) {
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

    private void showTableContextMenu(MouseEvent e, JButton btn, Ban ban) {
        JPopupMenu popupMenu = new JPopupMenu();

        // 1. Menu cho Bàn Trống
        if (ban.getTrangThai().equals("Trống")) {
            JMenuItem itemDatTruoc = new JMenuItem("📌 Đánh dấu Đặt trước");
            itemDatTruoc.addActionListener(evt -> {
                QuanLyBanService.updateTrangThaiBan(ban.getSoBan(), "Đặt trước", -1);
                refresh();
            });

            JMenuItem itemKhoaBan = new JMenuItem("🔒 Tạm khóa bàn (Bàn hỏng)");
            itemKhoaBan.addActionListener(evt -> {
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn muốn khóa tạm thời Bàn " + ban.getSoBan() + "?",
                        "Xác nhận khóa bàn", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    QuanLyBanService.updateTrangThaiBan(ban.getSoBan(), "Không hoạt động", -1);
                    refresh();
                }
            });

            popupMenu.add(itemDatTruoc);
            popupMenu.addSeparator();
            popupMenu.add(itemKhoaBan);

            // 2. Menu cho Bàn Đặt Trước
        } else if (ban.getTrangThai().equals("Đặt trước")) {
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
            popupMenu.addSeparator();
            popupMenu.add(itemHuy);

            // 3. Menu cho Bàn Không Hoạt Động
        } else if (ban.getTrangThai().equals("Không hoạt động")) {
            JMenuItem itemMoBan = new JMenuItem("🔓 Mở lại bàn (Đã sửa xong)");
            itemMoBan.addActionListener(evt -> {
                QuanLyBanService.updateTrangThaiBan(ban.getSoBan(), "Trống", -1);
                refresh();
            });
            popupMenu.add(itemMoBan);

            // 4. Menu cho Bàn Đang Phục Vụ
        } else if (ban.getTrangThai().equals("Đang phục vụ")) {
            JMenuItem itemDangPhucVu = new JMenuItem("Bàn đang phục vụ (Không thể can thiệp)");
            itemDangPhucVu.setEnabled(false);
            popupMenu.add(itemDangPhucVu);
        }

        if (!ban.getTrangThai().equals("Đang phục vụ")) {
            popupMenu.addSeparator();

            // 1. NÚT SỬA THÔNG TIN BÀN
            JMenuItem itemSua = new JMenuItem("✏️ Sửa số bàn / Chuyển tầng");
            itemSua.addActionListener(evt -> {
                JTextField txtSoBan = new JTextField(String.valueOf(ban.getSoBan()), 5);
                JTextField txtTang = new JTextField(String.valueOf(ban.getTang()), 5);

                JPanel panel = new JPanel(new GridLayout(2, 2, 10, 15));
                panel.add(new JLabel("Số bàn mới:"));
                panel.add(txtSoBan);
                panel.add(new JLabel("Chuyển đến Tầng:"));
                panel.add(txtTang);

                int res = JOptionPane.showConfirmDialog(this, panel, "Sửa Bàn " + ban.getSoBan(),
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (res == JOptionPane.OK_OPTION) {
                    try {
                        int newSo = Integer.parseInt(txtSoBan.getText().trim());
                        int newTang = Integer.parseInt(txtTang.getText().trim());

                        if (newSo <= 0 || newTang <= 0) {
                            JOptionPane.showMessageDialog(this, "Dữ liệu phải lớn hơn 0!", "Lỗi",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        boolean success = QuanLyBanService.suaThongTinBan(ban.getSoBan(), newSo, newTang);
                        if (success) {
                            JOptionPane.showMessageDialog(this, "Đã cập nhật thành công!");
                            currentFloor = newTang; // Tự động nhảy theo bàn sang tầng mới
                            refresh();
                        } else {
                            JOptionPane.showMessageDialog(this, "Số bàn mới đã có người sử dụng!", "Trùng lặp",
                                    JOptionPane.WARNING_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ!", "Lỗi",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            popupMenu.add(itemSua);
            // ==========================================
            // 🟢 TÍNH NĂNG XÓA BÀN VĨNH VIỄN
            // ==========================================
            JMenuItem itemXoaBan = new JMenuItem("🗑️ Xóa vĩnh viễn bàn này");
            itemXoaBan.setForeground(Color.RED);

            itemXoaBan.addActionListener(evt -> {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "⚠️ CẢNH BÁO: Bạn có chắc chắn muốn XÓA VĨNH VIỄN Bàn " + ban.getSoBan()
                                + " không?\nLưu ý: Chỉ có thể xóa bàn vừa tạo nhầm và chưa từng có giao dịch.",
                        "Xác nhận xóa bàn", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    boolean success = QuanLyBanService.xoaBan(ban.getSoBan());
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Đã xóa Bàn " + ban.getSoBan() + " thành công!", "Đã xóa",
                                JOptionPane.INFORMATION_MESSAGE);
                        refresh();
                    } else {
                        // 🟢 HIỂN THỊ THÔNG BÁO LỖI THÔNG MINH ĐỂ HƯỚNG DẪN NGƯỜI DÙNG
                        JOptionPane.showMessageDialog(this,
                                "❌ Không thể xóa Bàn " + ban.getSoBan() + "!\n\n" +
                                        "Nguyên nhân: Bàn này đã từng có lịch sử hóa đơn.\n" +
                                        "Hệ thống chặn thao tác xóa để bảo vệ dữ liệu doanh thu cũ.\n\n" +
                                        "👉 Giải pháp: Vui lòng nhấp chuột phải và chọn 'Tạm khóa bàn'.",
                                "Lỗi ràng buộc dữ liệu", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            popupMenu.add(itemXoaBan);
        }

        // Hiển thị menu tại vị trí chuột
        popupMenu.show(btn, e.getX(), e.getY());
    }

    // 🟢 XỬ LÝ NHẬP ĐẦU VÀO TÙY CHỈNH (Đã thêm check Tầng mới)
    private void handleThemBanMoi() {
        JTextField txtSoBan = new JTextField(5);
        txtSoBan.setFont(UiTheme.bold(14));
        JTextField txtTang = new JTextField(5);
        txtTang.setFont(UiTheme.bold(14));
        txtTang.setText(String.valueOf(currentFloor)); // Gợi ý luôn tầng đang đứng

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 15));
        panel.add(new JLabel("Nhập Số bàn mới:"));
        panel.add(txtSoBan);
        panel.add(new JLabel("Đặt ở Tầng số:"));
        panel.add(txtTang);

        int result = JOptionPane.showConfirmDialog(this, panel, "Thêm Bàn Mới", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                int soBanMoi = Integer.parseInt(txtSoBan.getText().trim());
                int tangMoi = Integer.parseInt(txtTang.getText().trim());

                if (soBanMoi <= 0 || tangMoi <= 0) {
                    JOptionPane.showMessageDialog(this, "Số bàn và Tầng phải lớn hơn 0!", "Lỗi nhập liệu",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 🟢 KIỂM TRA TẦNG ĐÃ TỒN TẠI HAY CHƯA
                boolean tangTonTai = false;
                for (JButton btn : floorButtons) {
                    if (btn.getText().equals("Tầng " + tangMoi)) {
                        tangTonTai = true;
                        break;
                    }
                }

                // Nếu Tầng chưa tồn tại, yêu cầu xác nhận
                if (!tangTonTai) {
                    int confirmFloor = JOptionPane.showConfirmDialog(this,
                            "Hiện tại hệ thống không có Tầng " + tangMoi
                                    + ".\nBạn có chắc chắn muốn tạo thêm tầng mới này không?",
                            "Xác nhận tạo Tầng", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                    if (confirmFloor != JOptionPane.YES_OPTION) {
                        return; // Ngừng lại nếu người dùng đổi ý (chọn No/Cancel)
                    }
                }

                // Tiến hành lưu vào cơ sở dữ liệu
                boolean isSuccess = QuanLyBanService.themBanMoi(soBanMoi, tangMoi);

                if (isSuccess) {
                    JOptionPane.showMessageDialog(this,
                            "Đã thêm Bàn " + soBanMoi + " vào Tầng " + tangMoi + " thành công!", "Thành công",
                            JOptionPane.INFORMATION_MESSAGE);
                    currentFloor = tangMoi;
                    refresh();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Bàn số " + soBanMoi + " đã tồn tại trong hệ thống!\nVui lòng chọn số bàn khác.",
                            "Trùng lặp", JOptionPane.WARNING_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập định dạng số hợp lệ!", "Lỗi nhập liệu",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}