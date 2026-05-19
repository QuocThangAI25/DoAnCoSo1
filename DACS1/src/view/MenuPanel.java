package view;

import dao.MonDao;
import model.Mon;
import service.ThanhToanService;
import utils.NumberUtils;

import javax.swing.*;
import java.awt.*;
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
        
        panelMiCay = new JPanel();
        panelMiCay.setLayout(new BoxLayout(panelMiCay, BoxLayout.Y_AXIS));
        panelNuocUong = new JPanel();
        panelNuocUong.setLayout(new BoxLayout(panelNuocUong, BoxLayout.Y_AXIS));
        panelTopping = new JPanel();
        panelTopping.setLayout(new BoxLayout(panelTopping, BoxLayout.Y_AXIS));
        
        JScrollPane scrollMiCay = new JScrollPane(panelMiCay);
        JScrollPane scrollNuocUong = new JScrollPane(panelNuocUong);
        JScrollPane scrollTopping = new JScrollPane(panelTopping);
        
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
        List<Mon> monList = monDAO.getByLoai(loai);
        
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
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(250, 80));
        
        // Thông tin món
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setBackground(Color.WHITE);
        
        JLabel lblTen = new JLabel(mon.getTenHienThi());
        lblTen.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel lblGia = new JLabel(NumberUtils.formatVND(mon.getGia()));
        lblGia.setFont(new Font("Arial", Font.PLAIN, 12));
        lblGia.setForeground(Color.RED);
        
        infoPanel.add(lblTen);
        infoPanel.add(lblGia);
        
        // Nút gọi món
        JButton btnOrder = new JButton("+ Gọi");
        btnOrder.setBackground(new Color(0, 150, 0));
        btnOrder.setForeground(Color.WHITE);
        btnOrder.setFont(new Font("Arial", Font.BOLD, 12));
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
}