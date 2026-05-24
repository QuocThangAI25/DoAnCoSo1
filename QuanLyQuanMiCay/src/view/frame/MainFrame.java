package view.frame;



import model.NhanVien;

import util.UiTheme;

import view.panel.BanPanel;

import view.panel.HoaDonPanel;

import view.panel.MenuPanel;

import view.panel.QuanLyMenuPanel;

import view.panel.QuanLyNhanVienPanel;

import view.panel.ThongKePanel;



import javax.swing.*;

import java.awt.*;



public class MainFrame extends JFrame {

    private NhanVien nhanVien;

    private JTabbedPane tabbedPane;

    private BanPanel banPanel;

    private JPanel goiMonPanel;

    private MenuPanel menuPanel;

    private HoaDonPanel hoaDonPanel;

    private ThongKePanel thongKePanel;

    private QuanLyMenuPanel quanLyMenuPanel;

    private QuanLyNhanVienPanel quanLyNhanVienPanel;

    private JTabbedPane quanLyTabbedPane;



    public MainFrame(NhanVien nhanVien) {

        this.nhanVien = nhanVien;

        initUI();

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }



    private void initUI() {

        setTitle("TVT - QUẢN LÝ QUÁN MÌ CAY - Chào " + nhanVien.getTen());

        setSize(1300, 750);

        getContentPane().setBackground(UiTheme.DARK);



        JMenuBar menuBar = new JMenuBar();

        UiTheme.menuBar(menuBar);



        JMenu menuHeThong = new JMenu("Hệ thống");

        UiTheme.topMenu(menuHeThong);



        JMenuItem menuDangXuat = new JMenuItem("Đăng xuất");

        JMenuItem menuThoat = new JMenuItem("Thoát");

        menuDangXuat.addActionListener(e -> dangXuat());

        menuThoat.addActionListener(e -> System.exit(0));

        menuHeThong.add(menuDangXuat);

        menuHeThong.add(menuThoat);

        menuBar.add(menuHeThong);



        JMenu menuTroGiup = new JMenu("Trợ giúp");

        UiTheme.topMenu(menuTroGiup);



        JMenuItem menuThongTin = new JMenuItem("Thông tin");

        menuThongTin.addActionListener(e -> thongTin());

        menuTroGiup.add(menuThongTin);

        menuBar.add(menuTroGiup);



        setJMenuBar(menuBar);



        tabbedPane = new JTabbedPane();

        tabbedPane.setBackground(UiTheme.BLACK);

        tabbedPane.setForeground(UiTheme.TEXT);

        tabbedPane.setFont(UiTheme.bold(13));



        banPanel = new BanPanel(this);

        tabbedPane.addTab(UiTheme.tabTitle("Sơ đồ bàn"), banPanel);



        goiMonPanel = new JPanel(new GridLayout(1, 2, 10, 0));

        hoaDonPanel = new HoaDonPanel(this);

        menuPanel = new MenuPanel(hoaDonPanel);

        goiMonPanel.add(menuPanel);

        goiMonPanel.add(hoaDonPanel);

        UiTheme.panel(goiMonPanel);

        tabbedPane.addTab(UiTheme.tabTitle("Gọi món"), goiMonPanel);



        thongKePanel = new ThongKePanel();

        tabbedPane.addTab(UiTheme.tabTitle("Thống kê"), thongKePanel);



        tabbedPane.addChangeListener(e -> onMainTabChanged());



        if (nhanVien.getVaiTro().equals("admin")) {

            quanLyTabbedPane = new JTabbedPane();

            quanLyTabbedPane.setBackground(UiTheme.BLACK);

            quanLyTabbedPane.setForeground(UiTheme.TEXT);

            quanLyTabbedPane.setFont(UiTheme.bold(13));



            quanLyMenuPanel = new QuanLyMenuPanel(() -> menuPanel.refreshMenu());

            quanLyTabbedPane.addTab(UiTheme.adminTabTitle("Quản lý Menu"), quanLyMenuPanel);



            quanLyNhanVienPanel = new QuanLyNhanVienPanel();

            quanLyTabbedPane.addTab(UiTheme.adminTabTitle("Quản lý Nhân viên"), quanLyNhanVienPanel);



            quanLyTabbedPane.addChangeListener(e -> onQuanLySubTabChanged());



            tabbedPane.addTab(UiTheme.tabTitle("Quản lý"), quanLyTabbedPane);

        }



        add(tabbedPane);

    }



    private void onMainTabChanged() {

        Component selected = tabbedPane.getSelectedComponent();

        if (selected == goiMonPanel) {

            menuPanel.refreshMenu();

        } else if (selected == thongKePanel) {

            thongKePanel.loadIfNeeded();

        } else if (selected == quanLyTabbedPane) {

            onQuanLySubTabChanged();

        }

    }



    private void onQuanLySubTabChanged() {

        if (quanLyTabbedPane == null) {

            return;

        }

        Component sub = quanLyTabbedPane.getSelectedComponent();

        if (sub == quanLyMenuPanel) {

            quanLyMenuPanel.loadIfNeeded();

        } else if (sub == quanLyNhanVienPanel) {

            quanLyNhanVienPanel.loadIfNeeded();

        }

    }



    public void chonBan(int soBan) {

        menuPanel.setCurrentBan(soBan);

        hoaDonPanel.setCurrentBan(soBan, nhanVien.getId());

        tabbedPane.setSelectedIndex(1);

    }



    public void refreshBanPanel() {

        banPanel.refresh();

    }



    private void dangXuat() {

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn đăng xuất?", "Xác nhận",

                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {

            this.dispose();

            new LoginFrame().setVisible(true);

        }

    }



    private void thongTin() {

        JOptionPane.showMessageDialog(this,

                "TVT - QUẢN LÝ QUÁN MÌ CAY\nPhiên bản: 1.0\n\nHỗ trợ: tvt@quanly.com",

                "Thông tin", JOptionPane.INFORMATION_MESSAGE);

    }
 // Thêm method này vào cuối class, trước dấu đóng ngoặc nhọn cuối cùng
    public NhanVien getNhanVien() {
        return this.nhanVien;
    }

}


