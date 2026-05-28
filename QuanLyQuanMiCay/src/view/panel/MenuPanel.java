package view.panel;

import dao.MonDao;
import model.Mon;
import service.ThanhToanService;
import util.NumberUtils;
import util.UiTheme;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class MenuPanel extends JPanel {

    private static final int GRID_COLS = 4;
    private static final int CARD_IMG_W = 130;
    private static final int CARD_IMG_H = 75;
    private static final int CARD_RADIUS = 10;

    private HoaDonPanel hoaDonPanel;
    private JTabbedPane tabbedPane;
    private JPanel panelTatCa, panelMiCay, panelNuocUong, panelTopping;
    private JTextField txtSearch;
    private MonDao monDAO;

    // Biến cờ để tránh lỗi khi chữ mờ (placeholder) tự động thay đổi
    private boolean isPlaceholderActive = true;

    public MenuPanel(HoaDonPanel hoaDonPanel) {
        this.hoaDonPanel = hoaDonPanel;
        monDAO = new MonDao();
        initUI();
        loadMenu();

        // Phím tắt Ctrl + R để reload UI
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("control R"), "reloadAction");
        getActionMap().put("reloadAction", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                reloadUI();
                System.out.println("Giao diện đã được reload!");
            }
        });
    }

    public void reloadUI() {
        this.removeAll();
        initUI();
        loadMenu();
        this.revalidate();
        this.repaint();
    }

    private void initUI() {
        setLayout(new BorderLayout(0, 5)); // Thêm khoảng cách dọc
        UiTheme.panel(this);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // --- KHU VỰC HEADER (TIÊU ĐỀ + TÌM KIẾM) ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);

        JLabel lblHeader = new JLabel();
        UiTheme.sectionHeader(lblHeader, "MENU GỌI MÓN");
        headerPanel.add(lblHeader, BorderLayout.WEST);

        // Thiết kế Ô Tìm Kiếm
        JPanel searchWrap = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        searchWrap.setOpaque(false);

        // Tạo một container panel chứa ô gõ chữ
        JPanel searchBox = new JPanel(new BorderLayout());
        searchBox.setBackground(UiTheme.CARD);
        searchBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiTheme.RED_DARK, 1), // Viền ngoài cố định
                BorderFactory.createEmptyBorder(4, 8, 4, 8) // Khoảng cách đệm bên trong
        ));

        // Cấu hình JTextField thuần túy (Đã xóa bỏ hoàn toàn kính lúp)
        txtSearch = new JTextField("Tìm kiếm món ăn...");
        txtSearch.setPreferredSize(new Dimension(180, 22)); // Thu gọn lại cho vừa khung
        txtSearch.setFont(UiTheme.plain(13));
        txtSearch.setBackground(UiTheme.CARD);
        txtSearch.setForeground(UiTheme.MUTED);
        txtSearch.setCaretColor(UiTheme.RED_GLOW);
        txtSearch.setBorder(null); // Xóa bỏ viền mặc định của Textfield để không lỗi UI

        // Hợp nhất ô nhập vào khung searchBox
        searchBox.add(txtSearch, BorderLayout.CENTER);

        // Hiệu ứng đổi màu viền bao quanh khi click chuột (Focus)
        txtSearch.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (isPlaceholderActive) {
                    isPlaceholderActive = false;
                    txtSearch.setText("");
                    txtSearch.setForeground(UiTheme.TEXT);
                    // Khi click vào, làm sáng viền của cả hộp searchBox lên
                    searchBox.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(UiTheme.RED, 1),
                            BorderFactory.createEmptyBorder(4, 8, 4, 8)));
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtSearch.getText().trim().isEmpty()) {
                    isPlaceholderActive = true;
                    txtSearch.setForeground(UiTheme.MUTED);
                    txtSearch.setText("Tìm kiếm món ăn...");
                    // Khi rời chuột, trả lại viền tối màu
                    searchBox.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(UiTheme.RED_DARK, 1),
                            BorderFactory.createEmptyBorder(4, 8, 4, 8)));
                }
            }
        });

        // Tự động tìm kiếm ngay khi gõ
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                triggerSearch();
            }

            public void removeUpdate(DocumentEvent e) {
                triggerSearch();
            }

            public void changedUpdate(DocumentEvent e) {
                triggerSearch();
            }

            private void triggerSearch() {
                if (!isPlaceholderActive) {
                    loadMenu(); // Tự động load lại Menu theo từ khóa
                }
            }
        });

        searchWrap.add(searchBox);
        headerPanel.add(searchWrap, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);
        // ------------------------------------------

        if (tabbedPane != null) {
            remove(tabbedPane);
        }

        tabbedPane = new JTabbedPane();
        UiTheme.menuTabbedPane(tabbedPane);

        panelTatCa = createMenuGridPanel();
        panelMiCay = createMenuGridPanel();
        panelNuocUong = createMenuGridPanel();
        panelTopping = createMenuGridPanel();

        // Gắn Tab TẤT CẢ lên đầu tiên
        tabbedPane.addTab("Tất cả", wrapScroll(panelTatCa));
        tabbedPane.addTab("Mì Cay", wrapScroll(panelMiCay));
        tabbedPane.addTab("Nước Uống", wrapScroll(panelNuocUong));
        tabbedPane.addTab("Topping", wrapScroll(panelTopping));

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createMenuGridPanel() {
        JPanel p = new JPanel(new GridLayout(0, GRID_COLS, 10, 10));
        p.setBackground(UiTheme.DARK);
        p.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 20));
        return p;
    }

    private JScrollPane wrapScroll(JPanel grid) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(UiTheme.DARK);
        wrapper.add(grid, BorderLayout.NORTH);

        JScrollPane scroll = new JScrollPane(wrapper);
        UiTheme.menuScrollPane(scroll);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        scroll.getVerticalScrollBar().setUI(new javax.swing.plaf.basic.BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(70, 70, 70);
                this.trackColor = UiTheme.DARK;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton jbutton = new JButton();
                jbutton.setPreferredSize(new Dimension(0, 0));
                return jbutton;
            }
        });
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
        return scroll;
    }

    private void loadMenu() {
        String keyword = isPlaceholderActive ? "" : txtSearch.getText().trim().toLowerCase();

        loadMonTheoLoai("Tất cả", panelTatCa, keyword);
        loadMonTheoLoai("Mi Cay", panelMiCay, keyword);
        loadMonTheoLoai("Nuoc Uong", panelNuocUong, keyword);
        loadMonTheoLoai("Topping", panelTopping, keyword);
    }

    private void loadMonTheoLoai(String loai, JPanel panel, String keyword) {
        panel.removeAll();
        List<Mon> monList;

        // Nếu là tab "Tất cả", lấy toàn bộ data gộp lại
        if (loai.equals("Tất cả")) {
            monList = new ArrayList<>();
            monList.addAll(monDAO.getByLoai("Mi Cay"));
            monList.addAll(monDAO.getByLoai("Nuoc Uong"));
            monList.addAll(monDAO.getByLoai("Topping"));
        } else {
            monList = monDAO.getByLoai(loai);
        }

        // Lọc món theo từ khóa (Hiển thị nếu tên chứa từ khóa)
        for (Mon mon : monList) {
            String tenHienThi = mon.getTenHienThi().toLowerCase();
            if (keyword.isEmpty() || tenHienThi.contains(keyword)) {
                panel.add(createMenuItemPanel(mon));
            }
        }
        panel.revalidate();
        panel.repaint();
    }

    private JPanel createMenuItemPanel(Mon mon) {
        JPanel card = UiTheme.roundedCard(CARD_RADIUS);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(6, 4, 6, 4));

        JPanel imageWrap = new JPanel(new BorderLayout());
        imageWrap.setOpaque(false);
        imageWrap.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageWrap.setMaximumSize(new Dimension(CARD_IMG_W, CARD_IMG_H));
        imageWrap.setPreferredSize(new Dimension(CARD_IMG_W, CARD_IMG_H));
        imageWrap.add(buildImageLabel(mon), BorderLayout.CENTER);

        JLabel lblTen = new JLabel(mon.getTenHienThi(), SwingConstants.CENTER);
        lblTen.setFont(UiTheme.bold(13));
        lblTen.setForeground(UiTheme.WHITE);

        JLabel lblGia = new JLabel(NumberUtils.formatVND(mon.getGia()), SwingConstants.CENTER);
        lblGia.setFont(UiTheme.bold(14));
        lblGia.setForeground(UiTheme.RED_GLOW);

        JButton btnOrder = new JButton();
        UiTheme.addToOrderButton(btnOrder);
        btnOrder.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnOrder.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        btnOrder.addActionListener(e -> showGoiMonDialog(mon));

        card.add(imageWrap);
        card.add(Box.createVerticalStrut(4));
        card.add(wrapCentered(lblTen, 0, 0));
        card.add(Box.createVerticalStrut(2));
        card.add(wrapCentered(lblGia, 0, 0));
        card.add(Box.createVerticalGlue());
        card.add(btnOrder);

        return card;
    }

    private void showGoiMonDialog(Mon mon) {
        if (hoaDonPanel.getCurrentHoaDonId() == -1) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng chọn bàn trước khi gọi món!",
                    "Chưa chọn bàn",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Chọn số lượng", true);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(UiTheme.PANEL);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setUndecorated(true);

        JPanel mainPanel = new JPanel(new BorderLayout(0, 15));
        mainPanel.setBackground(UiTheme.PANEL);
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiTheme.RED, 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)));

        JLabel lblName = new JLabel(mon.getTenHienThi(), SwingConstants.CENTER);
        lblName.setFont(UiTheme.bold(16));
        lblName.setForeground(UiTheme.WHITE);

        JLabel lblPrice = new JLabel(NumberUtils.formatVND(mon.getGia()), SwingConstants.CENTER);
        lblPrice.setFont(UiTheme.bold(14));
        lblPrice.setForeground(UiTheme.RED_GLOW);

        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setOpaque(false);
        headerPanel.add(lblName);
        headerPanel.add(lblPrice);

        JPanel qtyPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        qtyPanel.setOpaque(false);

        JButton btnMinus = new JButton("−");
        JButton btnPlus = new JButton("+");
        UiTheme.qtyButton(btnMinus);
        UiTheme.qtyButton(btnPlus);
        btnMinus.setPreferredSize(new Dimension(35, 35));
        btnPlus.setPreferredSize(new Dimension(35, 35));

        JTextField txtQty = new JTextField("1");
        txtQty.setFont(UiTheme.bold(16));
        txtQty.setHorizontalAlignment(JTextField.CENTER);
        txtQty.setPreferredSize(new Dimension(50, 35));
        txtQty.setBackground(UiTheme.DARK);
        txtQty.setForeground(UiTheme.WHITE);
        txtQty.setCaretColor(UiTheme.RED_GLOW);
        txtQty.setBorder(BorderFactory.createLineBorder(UiTheme.RED_DARK));

        btnMinus.addActionListener(e -> {
            try {
                int val = Integer.parseInt(txtQty.getText());
                if (val > 1)
                    txtQty.setText(String.valueOf(val - 1));
                txtQty.requestFocus();
                txtQty.selectAll();
            } catch (Exception ex) {
                txtQty.setText("1");
            }
        });

        btnPlus.addActionListener(e -> {
            try {
                int val = Integer.parseInt(txtQty.getText());
                if (val < 99)
                    txtQty.setText(String.valueOf(val + 1));
                txtQty.requestFocus();
                txtQty.selectAll();
            } catch (Exception ex) {
                txtQty.setText("1");
            }
        });

        qtyPanel.add(btnMinus);
        qtyPanel.add(txtQty);
        qtyPanel.add(btnPlus);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnPanel.setOpaque(false);

        JButton btnConfirm = new JButton("Xác nhận");
        UiTheme.primaryButton(btnConfirm);
        btnConfirm.setPreferredSize(new Dimension(100, 35));

        JButton btnCancel = new JButton("Hủy");
        UiTheme.outlineButton(btnCancel);
        btnCancel.setPreferredSize(new Dimension(100, 35));

        btnCancel.addActionListener(e -> dialog.dispose());

        btnConfirm.addActionListener(e -> {
            try {
                int qty = Integer.parseInt(txtQty.getText());
                if (qty > 0) {
                    goiMon(mon, qty);
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Số lượng phải > 0");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng nhập số hợp lệ!");
            }
        });

        dialog.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                txtQty.requestFocusInWindow();
                txtQty.selectAll();
            }
        });

        dialog.getRootPane().setDefaultButton(btnConfirm);

        btnPanel.add(btnCancel);
        btnPanel.add(btnConfirm);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(qtyPanel, BorderLayout.CENTER);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        dialog.add(mainPanel);
        dialog.setVisible(true);
    }

    private static JPanel wrapCentered(JLabel lbl, int top, int bottom) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        row.setOpaque(false);
        row.setAlignmentX(Component.CENTER_ALIGNMENT);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, lbl.getPreferredSize().height + top + bottom + 4));
        row.setBorder(BorderFactory.createEmptyBorder(top, 4, bottom, 4));
        row.add(lbl);
        return row;
    }

    private JLabel buildImageLabel(Mon mon) {
        ImageIcon icon = loadImageForMon(mon);
        if (icon != null) {
            ImageIcon fitted = scaleIconToFit(icon, CARD_IMG_W, CARD_IMG_H);
            JLabel lbl = new JLabel(fitted, SwingConstants.CENTER);
            lbl.setOpaque(false);
            lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            lbl.setPreferredSize(new Dimension(CARD_IMG_W, CARD_IMG_H));
            return lbl;
        }

        JLabel lbl = new JLabel(getEmojiForMon(mon), SwingConstants.CENTER);
        lbl.setFont(UiTheme.plain(40));
        lbl.setForeground(UiTheme.MUTED);
        lbl.setOpaque(true);
        lbl.setBackground(UiTheme.PANEL);
        lbl.setPreferredSize(new Dimension(CARD_IMG_W, CARD_IMG_H));
        lbl.setBorder(BorderFactory.createLineBorder(UiTheme.RED_DARK, 1));
        return lbl;
    }

    private static ImageIcon scaleIconToFit(ImageIcon icon, int maxW, int maxH) {
        int w = icon.getIconWidth();
        int h = icon.getIconHeight();
        if (w <= 0 || h <= 0)
            return icon;
        double scale = Math.min((double) maxW / w, (double) maxH / h);
        int nw = Math.max(1, (int) Math.round(w * scale));
        int nh = Math.max(1, (int) Math.round(h * scale));
        Image scaled = icon.getImage().getScaledInstance(nw, nh, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    private String getEmojiForMon(Mon mon) {
        if (mon.getLoai().equals("Mi Cay"))
            return "🍜";
        else if (mon.getLoai().equals("Nuoc Uong"))
            return "🥤";
        else
            return "🧀";
    }

    private ImageIcon loadImageForMon(Mon mon) {
        String fileName = "";
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
        } else if (mon.getLoai().equals("Topping")) {
            if (mon.getTen().contains("Phô Mai"))
                fileName = "tobokicheese.jpg";
            else if (mon.getTen().contains("Trứng"))
                fileName = "tokboky.jpg";
            else
                fileName = "topping_default.jpg";
        }

        String resourcePath = "/images/" + fileName;
        java.net.URL imgUrl = getClass().getResource(resourcePath);
        if (imgUrl != null)
            return new ImageIcon(imgUrl);
        else
            return null;
    }

    private void goiMon(Mon mon, int soLuong) {
        if (hoaDonPanel.getCurrentHoaDonId() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn bàn hợp lệ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ThanhToanService.themMonVaoHoaDon(hoaDonPanel.getCurrentHoaDonId(), mon, soLuong);
        hoaDonPanel.loadHoaDon(hoaDonPanel.getCurrentHoaDonId());
    }

    public void refreshMenu() {
        loadMenu();
    }
}