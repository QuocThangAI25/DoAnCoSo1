package view;

import service.ThongKeService;
import utils.DateUtils;
import utils.NumberUtils;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ThongKePanel extends JPanel {
    private JLabel lblDoanhThu, lblSoHoaDon, lblSoMon;
    private JList<String> listTopMon;
    private DefaultListModel<String> listModel;
    private JSpinner spnNgay;
    
    public ThongKePanel() {
        initUI();
        loadThongKe(new Date());
    }
    
    private void initUI() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel chọn ngày
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Chọn ngày:"));
        
        SpinnerDateModel dateModel = new SpinnerDateModel();
        spnNgay = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnNgay, "dd/MM/yyyy");
        spnNgay.setEditor(dateEditor);
        spnNgay.setValue(new Date());
        topPanel.add(spnNgay);
        
        JButton btnXem = new JButton("Xem thống kê");
        btnXem.addActionListener(e -> {
            Date ngay = (Date) spnNgay.getValue();
            loadThongKe(ngay);
        });
        topPanel.add(btnXem);
        
        add(topPanel, BorderLayout.NORTH);
        
        // Panel chính
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        
        // Panel thông số
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setBorder(BorderFactory.createTitledBorder("THỐNG KÊ"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        infoPanel.add(new JLabel("Doanh thu:"), gbc);
        lblDoanhThu = new JLabel("0 ₫");
        lblDoanhThu.setFont(new Font("Arial", Font.BOLD, 18));
        lblDoanhThu.setForeground(Color.RED);
        gbc.gridx = 1;
        infoPanel.add(lblDoanhThu, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        infoPanel.add(new JLabel("Số hóa đơn:"), gbc);
        lblSoHoaDon = new JLabel("0");
        lblSoHoaDon.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 1;
        infoPanel.add(lblSoHoaDon, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        infoPanel.add(new JLabel("Tổng món bán:"), gbc);
        lblSoMon = new JLabel("0");
        lblSoMon.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 1;
        infoPanel.add(lblSoMon, gbc);
        
        // Panel top món
        JPanel topMonPanel = new JPanel(new BorderLayout());
        topMonPanel.setBorder(BorderFactory.createTitledBorder("TOP 3 MÓN BÁN CHẠY"));
        listModel = new DefaultListModel<>();
        listTopMon = new JList<>(listModel);
        listTopMon.setFont(new Font("Arial", Font.PLAIN, 14));
        listTopMon.setFixedCellHeight(40);
        topMonPanel.add(new JScrollPane(listTopMon), BorderLayout.CENTER);
        
        mainPanel.add(infoPanel);
        mainPanel.add(topMonPanel);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void loadThongKe(Date ngay) {
        double doanhThu = ThongKeService.getDoanhThu(ngay);
        int soHoaDon = ThongKeService.getSoHoaDon(ngay);
        int soMon = ThongKeService.getTongMonBan(ngay);
        
        lblDoanhThu.setText(NumberUtils.formatVND(doanhThu));
        lblSoHoaDon.setText(String.valueOf(soHoaDon));
        lblSoMon.setText(String.valueOf(soMon));
        
        listModel.clear();
        List<Map.Entry<String, Integer>> topMon = ThongKeService.getTopMonBanChay(ngay, 3);
        int i = 1;
        for (Map.Entry<String, Integer> entry : topMon) {
            listModel.addElement(i + ". " + entry.getKey() + " - " + entry.getValue() + " phần");
            i++;
        }
        if (topMon.isEmpty()) {
            listModel.addElement("Chưa có dữ liệu");
        }
    }
}