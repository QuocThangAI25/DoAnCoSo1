package view;

import model.Ban;
import service.QuanLyBanService;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BanPanel extends JPanel {
    private MainFrame mainFrame;
    private JPanel panelBan;
    private List<Ban> danhSachBan;
    
    public BanPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initUI();
        loadData();
    }
    
    private void initUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Tiêu đề
        JLabel lblTitle = new JLabel("SƠ ĐỒ BÀN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(255, 69, 0));
        add(lblTitle, BorderLayout.NORTH);
        
        // Panel chứa các bàn
        panelBan = new JPanel(new GridLayout(2, 4, 15, 15));
        panelBan.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(panelBan, BorderLayout.CENTER);
    }
    
    public void loadData() {
        panelBan.removeAll();
        danhSachBan = QuanLyBanService.getAllBan();
        
        for (Ban ban : danhSachBan) {
            JButton btnBan = taoNutBan(ban);
            panelBan.add(btnBan);
        }
        
        panelBan.revalidate();
        panelBan.repaint();
    }
    
    private JButton taoNutBan(Ban ban) {
        JButton btn = new JButton("<html><center>Bàn " + ban.getSoBan() + "<br>" + ban.getTrangThai() + "</center></html>");
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(120, 100));
        
        if (ban.getTrangThai().equals("Trống")) {
            btn.setBackground(new Color(144, 238, 144)); // Xanh nhạt
        } else {
            btn.setBackground(new Color(255, 200, 100)); // Cam
        }
        
        btn.addActionListener(e -> mainFrame.chonBan(ban.getSoBan()));
        
        return btn;
    }
    
    public void refresh() {
        loadData();
    }
}