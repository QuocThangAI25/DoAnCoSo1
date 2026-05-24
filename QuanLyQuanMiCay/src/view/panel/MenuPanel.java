package view.panel;



import model.Mon;

import service.MonService;

import service.ThanhToanService;

import util.NumberUtils;

import util.UiTheme;



import javax.swing.*;

import java.awt.*;

import java.util.List;



public class MenuPanel extends JPanel {

    private HoaDonPanel hoaDonPanel;

    private int currentBan = -1;

    private JTabbedPane tabbedPane;

    private JPanel panelMiCay, panelNuocUong, panelTopping;

    private MonService monService;



    public MenuPanel(HoaDonPanel hoaDonPanel) {

        this.hoaDonPanel = hoaDonPanel;

        monService = new MonService();

        initUI();

    }



    private void initUI() {

        setLayout(new BorderLayout());

        UiTheme.panel(this);

        setBorder(UiTheme.titledBorder("MENU GỌI MÓN"));



        tabbedPane = new JTabbedPane();

        tabbedPane.setBackground(UiTheme.BLACK);

        tabbedPane.setForeground(UiTheme.TEXT);

        tabbedPane.setFont(UiTheme.bold(13));



        panelMiCay = new JPanel();

        panelMiCay.setLayout(new BoxLayout(panelMiCay, BoxLayout.Y_AXIS));

        UiTheme.panel(panelMiCay);

        panelNuocUong = new JPanel();

        panelNuocUong.setLayout(new BoxLayout(panelNuocUong, BoxLayout.Y_AXIS));

        UiTheme.panel(panelNuocUong);

        panelTopping = new JPanel();

        panelTopping.setLayout(new BoxLayout(panelTopping, BoxLayout.Y_AXIS));

        UiTheme.panel(panelTopping);



        JScrollPane scrollMiCay = new JScrollPane(panelMiCay);

        JScrollPane scrollNuocUong = new JScrollPane(panelNuocUong);

        JScrollPane scrollTopping = new JScrollPane(panelTopping);

        UiTheme.scrollPane(scrollMiCay);

        UiTheme.scrollPane(scrollNuocUong);

        UiTheme.scrollPane(scrollTopping);



        tabbedPane.addTab("Mì Cay", scrollMiCay);

        tabbedPane.addTab("Nước Uống", scrollNuocUong);

        tabbedPane.addTab("Topping", scrollTopping);



        add(tabbedPane, BorderLayout.CENTER);

    }



    private void loadMenu() {

        loadMonTheoLoai("Mi Cay", panelMiCay);

        loadMonTheoLoai("Nuoc Uong", panelNuocUong);

        loadMonTheoLoai("Topping", panelTopping);

    }



    private void loadMonTheoLoai(String loai, JPanel panel) {

        panel.removeAll();

        List<Mon> monList = monService.getByLoai(loai);



        for (Mon mon : monList) {

            JPanel itemPanel = taoItemMon(mon);

            panel.add(itemPanel);

            panel.add(Box.createVerticalStrut(5));

        }



        panel.revalidate();

        panel.repaint();

    }



    private JPanel taoItemMon(Mon mon) {

        JPanel panel = new JPanel(new BorderLayout());

        UiTheme.menuItemCard(panel);

        panel.setPreferredSize(new Dimension(250, 80));



        JPanel infoPanel = new JPanel(new GridLayout(2, 1));

        UiTheme.card(infoPanel);



        JLabel lblTen = new JLabel(mon.getTenHienThi());

        lblTen.setFont(UiTheme.bold(14));

        lblTen.setForeground(UiTheme.TEXT);



        JLabel lblGia = new JLabel(NumberUtils.formatVND(mon.getGia()));

        lblGia.setFont(UiTheme.bold(13));

        lblGia.setForeground(UiTheme.RED_GLOW);



        infoPanel.add(lblTen);

        infoPanel.add(lblGia);



        JButton btnOrder = new JButton("+ Gọi");

        UiTheme.primaryButton(btnOrder);

        btnOrder.addActionListener(e -> goiMon(mon));



        panel.add(infoPanel, BorderLayout.CENTER);

        panel.add(btnOrder, BorderLayout.EAST);



        return panel;

    }



    private void goiMon(Mon mon) {

        if (currentBan == -1) {

            JOptionPane.showMessageDialog(this, "Vui lòng chọn bàn trước!");

            return;

        }



        if (hoaDonPanel.getCurrentHoaDonId() == -1) {

            JOptionPane.showMessageDialog(this, "Vui lòng chọn bàn hợp lệ!");

            return;

        }



        ThanhToanService.themMonVaoHoaDon(hoaDonPanel.getCurrentHoaDonId(), mon, 1);

        hoaDonPanel.loadHoaDon(hoaDonPanel.getCurrentHoaDonId());



        JOptionPane.showMessageDialog(this, "Đã thêm " + mon.getTenHienThi() + " vào hóa đơn!");

    }



    public void setCurrentBan(int ban) {

        this.currentBan = ban;

    }



    public void refreshMenu() {

        loadMenu();

    }

}


