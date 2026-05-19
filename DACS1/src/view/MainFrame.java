package view;

import model.NhanVien;
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
    
    public MainFrame(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
        initUI();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void initUI() {
        setTitle("TVT - QUẢN LÝ QUÁN MÌ CAY - Chào " + nhanVien.getTen());
        setSize(1300, 750);
        
        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menuHeThong = new JMenu("Hệ thống");
        JMenuItem menuDangXuat = new JMenuItem("Đăng xuất");
        JMenuItem menuThoat = new JMenuItem("Thoát");
        menuDangXuat.addActionListener(e -> dangXuat());
        menuThoat.addActionListener(e -> System.exit(0));
        menuHeThong.add(menuDangXuat);
        menuHeThong.add(menuThoat);
        menuBar.add(menuHeThong);
        
        JMenu menuTroGiup = new JMenu("Trợ giúp");
        JMenuItem menuThongTin = new JMenuItem("Thông tin");
        menuThongTin.addActionListener(e -> thongTin());
        menuTroGiup.add(menuThongTin);
        menuBar.add(menuTroGiup);
        
        setJMenuBar(menuBar);
        
        // Tabbed Pane
        tabbedPane = new JTabbedPane();
        
        // Tab 1: Sơ đồ bàn
        banPanel = new BanPanel(this);
        tabbedPane.addTab("Sơ đồ bàn", banPanel);
        
        // Tab 2: Gọi món (chia đôi màn hình)
        goiMonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        hoaDonPanel = new HoaDonPanel(this);
        menuPanel = new MenuPanel(hoaDonPanel);
        goiMonPanel.add(menuPanel);
        goiMonPanel.add(hoaDonPanel);
        tabbedPane.addTab("Gọi món", goiMonPanel);
        
        // Tab 3: Thống kê
        thongKePanel = new ThongKePanel();
        tabbedPane.addTab("Thống kê", thongKePanel);
        
        // Tab 4: Quản lý (chỉ admin mới thấy)
        if (nhanVien.getVaiTro().equals("admin")) {
            JTabbedPane quanLyTabbedPane = new JTabbedPane();
            
            quanLyMenuPanel = new QuanLyMenuPanel();
            quanLyTabbedPane.addTab("Quản lý Menu", quanLyMenuPanel);
            
            QuanLyNhanVienPanel quanLyNhanVienPanel = new QuanLyNhanVienPanel();
            quanLyTabbedPane.addTab("Quản lý Nhân viên", quanLyNhanVienPanel);
            
            tabbedPane.addTab("Quản lý", quanLyTabbedPane);
        }
        
        add(tabbedPane);
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
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn đăng xuất?", "Xác nhận", JOptionPane.YES_NO_OPTION);
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
}