package view.panel;

import service.ThongKeService;
import util.NumberUtils;
import util.UiTheme;

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
    private boolean loaded;

    public ThongKePanel() {
        initUI();
    }

    public void loadIfNeeded() {
        if (!loaded) {
            loadThongKe(new Date());
            loaded = true;
        }
    }

    private void initUI() {
        setLayout(new BorderLayout());
        UiTheme.panel(this);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        UiTheme.panel(topPanel);
        JLabel lblNgay = new JLabel("Chọn ngày:");
        UiTheme.label(lblNgay);
        topPanel.add(lblNgay);

        SpinnerDateModel dateModel = new SpinnerDateModel();
        spnNgay = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spnNgay, "dd/MM/yyyy");
        spnNgay.setEditor(dateEditor);
        spnNgay.setValue(new Date());
        spnNgay.setFont(UiTheme.bold(13));
        topPanel.add(spnNgay);

        JButton btnXem = new JButton("Tải lại");
        UiTheme.primaryButton(btnXem);
        btnXem.addActionListener(e -> {
            Date ngay = (Date) spnNgay.getValue();
            loadThongKe(ngay);
        });
        topPanel.add(btnXem);

        add(topPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        UiTheme.panel(mainPanel);

        JPanel infoPanel = new JPanel(new GridBagLayout());
        UiTheme.card(infoPanel);
        infoPanel.setBorder(UiTheme.titledBorder("THỐNG KÊ"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblDtTitle = new JLabel("Doanh thu:");
        UiTheme.label(lblDtTitle);
        infoPanel.add(lblDtTitle, gbc);
        lblDoanhThu = new JLabel("0 ₫");
        UiTheme.accent(lblDoanhThu, 20);
        gbc.gridx = 1;
        infoPanel.add(lblDoanhThu, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblHdTitle = new JLabel("Số hóa đơn:");
        UiTheme.label(lblHdTitle);
        infoPanel.add(lblHdTitle, gbc);
        lblSoHoaDon = new JLabel("0");
        lblSoHoaDon.setFont(UiTheme.bold(18));
        lblSoHoaDon.setForeground(UiTheme.TEXT);
        gbc.gridx = 1;
        infoPanel.add(lblSoHoaDon, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblMonTitle = new JLabel("Tổng món bán:");
        UiTheme.label(lblMonTitle);
        infoPanel.add(lblMonTitle, gbc);
        lblSoMon = new JLabel("0");
        lblSoMon.setFont(UiTheme.bold(18));
        lblSoMon.setForeground(UiTheme.TEXT);
        gbc.gridx = 1;
        infoPanel.add(lblSoMon, gbc);

        JPanel topMonPanel = new JPanel(new BorderLayout());
        UiTheme.card(topMonPanel);
        topMonPanel.setBorder(UiTheme.titledBorder("TOP 3 MÓN BÁN CHẠY"));
        listModel = new DefaultListModel<>();
        listTopMon = new JList<>(listModel);
        listTopMon.setFont(UiTheme.bold(14));
        listTopMon.setBackground(UiTheme.CARD);
        listTopMon.setForeground(UiTheme.TEXT);
        listTopMon.setSelectionBackground(UiTheme.RED);
        listTopMon.setSelectionForeground(UiTheme.WHITE);
        listTopMon.setFixedCellHeight(44);
        JScrollPane listScroll = new JScrollPane(listTopMon);
        UiTheme.scrollPane(listScroll);
        topMonPanel.add(listScroll, BorderLayout.CENTER);

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
