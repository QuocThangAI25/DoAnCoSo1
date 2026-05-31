package view.panel;

import model.NhanVien;
import service.ThongKeService;
import util.NumberUtils;
import util.UiTheme;
import view.frame.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ThongKePanel extends JPanel {
    private MainFrame mainFrame;
    private JLabel lblDoanhThu, lblSoHoaDon, lblSoMon;
    private JList<String> listTopMon;
    private DefaultListModel<String> listModel;

    private JComboBox<String> cbLoaiThoiGian;
    private JSpinner spnNgay;
    private JComboBox<ComboItem> cbNhanVien;

    private JPanel chartPanelArea;
    private boolean loaded;

    private static class ComboItem {
        int id;
        String label;

        public ComboItem(int id, String label) {
            this.id = id;
            this.label = label;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    public ThongKePanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initUI();
    }

    public void loadIfNeeded() {
        if (!loaded) {
            loadComboBoxNhanVien();
            loadThongKe();
            loaded = true;
        }
    }

    private void initUI() {
        setLayout(new BorderLayout());
        UiTheme.panel(this);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- HEADER BỘ LỌC ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        UiTheme.panel(topPanel);
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, UiTheme.RED_DARK));

        topPanel.add(new JLabel("Lọc theo:"));
        String[] loaiTG = { "Theo ngày", "Theo tháng", "Theo năm" };
        cbLoaiThoiGian = new JComboBox<>(loaiTG);
        cbLoaiThoiGian.setFont(UiTheme.plain(13));
        topPanel.add(cbLoaiThoiGian);

        topPanel.add(new JLabel("Thời gian:"));
        SpinnerDateModel dateModel = new SpinnerDateModel();
        spnNgay = new JSpinner(dateModel);
        spnNgay.setEditor(new JSpinner.DateEditor(spnNgay, "dd/MM/yyyy"));
        spnNgay.setValue(new Date());
        spnNgay.setFont(UiTheme.bold(13));
        topPanel.add(spnNgay);

        cbLoaiThoiGian.addActionListener(e -> {
            String type = cbLoaiThoiGian.getSelectedItem().toString();
            JSpinner.DateEditor editor;
            if (type.equals("Theo tháng")) {
                editor = new JSpinner.DateEditor(spnNgay, "MM/yyyy");
            } else if (type.equals("Theo năm")) {
                editor = new JSpinner.DateEditor(spnNgay, "yyyy");
            } else {
                editor = new JSpinner.DateEditor(spnNgay, "dd/MM/yyyy");
            }
            spnNgay.setEditor(editor);
            spnNgay.setValue(new Date());
        });

        topPanel.add(new JLabel("Nhân viên:"));
        cbNhanVien = new JComboBox<>();
        cbNhanVien.setFont(UiTheme.plain(13));
        cbNhanVien.setPreferredSize(new Dimension(150, 25));
        topPanel.add(cbNhanVien);

        JButton btnXem = new JButton("Tải thống kê");
        UiTheme.primaryButton(btnXem);
        btnXem.addActionListener(e -> loadThongKe());
        topPanel.add(btnXem);

        add(topPanel, BorderLayout.NORTH);

        // --- MAIN PANEL ---
        JPanel mainPanel = new JPanel(new BorderLayout(15, 0));
        UiTheme.panel(mainPanel);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        // TRÁI: SỐ LIỆU CHUNG
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        UiTheme.panel(leftPanel);
        leftPanel.setPreferredSize(new Dimension(380, 0));

        JPanel infoPanel = new JPanel(new GridBagLayout());
        UiTheme.card(infoPanel);
        infoPanel.setBorder(UiTheme.titledBorder("SỐ LIỆU CHUNG"));
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

        leftPanel.add(infoPanel);
        leftPanel.add(Box.createVerticalStrut(15));

        JPanel topMonPanel = new JPanel(new BorderLayout());
        UiTheme.card(topMonPanel);
        topMonPanel.setBorder(UiTheme.titledBorder("TOP 5 MÓN BÁN CHẠY"));
        listModel = new DefaultListModel<>();
        listTopMon = new JList<>(listModel);
        listTopMon.setFont(UiTheme.bold(14));
        listTopMon.setBackground(UiTheme.CARD);
        listTopMon.setForeground(UiTheme.TEXT);
        listTopMon.setSelectionBackground(UiTheme.RED);
        listTopMon.setSelectionForeground(UiTheme.WHITE);
        listTopMon.setFixedCellHeight(35);
        JScrollPane listScroll = new JScrollPane(listTopMon);
        UiTheme.scrollPane(listScroll);
        topMonPanel.add(listScroll, BorderLayout.CENTER);

        leftPanel.add(topMonPanel);
        mainPanel.add(leftPanel, BorderLayout.WEST);

        // PHẢI: BIỂU ĐỒ DOANH THU
        JPanel rightPanel = new JPanel(new BorderLayout());
        UiTheme.card(rightPanel);
        rightPanel.setBorder(UiTheme.titledBorder("BIỂU ĐỒ DOANH THU"));

        chartPanelArea = new JPanel(new BorderLayout());
        chartPanelArea.setBackground(UiTheme.CARD);
        JLabel lblPlaceholder = new JLabel("Tải thống kê để xem biểu đồ...", SwingConstants.CENTER);
        UiTheme.label(lblPlaceholder);
        chartPanelArea.add(lblPlaceholder, BorderLayout.CENTER);

        rightPanel.add(chartPanelArea, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void loadComboBoxNhanVien() {
        cbNhanVien.removeAllItems();
        NhanVien currentNV = mainFrame.getNhanVien();

        if (currentNV.getVaiTro().equalsIgnoreCase("admin")) {
            cbNhanVien.addItem(new ComboItem(-1, "-- Tất cả nhân viên --"));
            Map<Integer, String> nvMap = ThongKeService.getDanhSachNhanVien();
            for (Map.Entry<Integer, String> entry : nvMap.entrySet()) {
                cbNhanVien.addItem(new ComboItem(entry.getKey(), entry.getValue()));
            }
            cbNhanVien.setEnabled(true);
        } else {
            cbNhanVien.addItem(new ComboItem(currentNV.getId(), currentNV.getTen() + " (Bạn)"));
            cbNhanVien.setEnabled(false);
        }
    }

    private void loadThongKe() {
        Date ngay = (Date) spnNgay.getValue();
        String loaiThoiGian = cbLoaiThoiGian.getSelectedItem().toString();
        ComboItem selectedNV = (ComboItem) cbNhanVien.getSelectedItem();
        int nvId = (selectedNV != null) ? selectedNV.getId() : -1;

        double doanhThu = ThongKeService.getDoanhThu(ngay, loaiThoiGian, nvId);
        int soHoaDon = ThongKeService.getSoHoaDon(ngay, loaiThoiGian, nvId);
        int soMon = ThongKeService.getTongMonBan(ngay, loaiThoiGian, nvId);

        lblDoanhThu.setText(NumberUtils.formatVND(doanhThu));
        lblSoHoaDon.setText(String.valueOf(soHoaDon));
        lblSoMon.setText(String.valueOf(soMon));

        listModel.clear();
        List<Map.Entry<String, Integer>> topMonDataAll = ThongKeService.getTopMonBanChay(ngay, loaiThoiGian, nvId, 5);
        int i = 1;
        for (Map.Entry<String, Integer> entry : topMonDataAll) {
            listModel.addElement(i + ". " + entry.getKey() + " - " + entry.getValue() + " phần");
            i++;
        }
        if (topMonDataAll.isEmpty())
            listModel.addElement("Chưa có dữ liệu thống kê...");

        Map<Integer, Double> chartData = ThongKeService.getDoanhThuBieuDo(ngay, loaiThoiGian, nvId);
        updateBarChart(chartData, loaiThoiGian, ngay);
    }

    private void updateBarChart(Map<Integer, Double> chartData, String loaiThoiGian, Date ngay) {
        StringBuilder labels = new StringBuilder();
        StringBuilder values = new StringBuilder();

        int start = 1, end = 12;
        String prefix = "";

        if ("Theo năm".equals(loaiThoiGian)) {
            start = 1;
            end = 12;
            prefix = "T";
        } else if ("Theo tháng".equals(loaiThoiGian)) {
            start = 1;
            Calendar cal = Calendar.getInstance();
            cal.setTime(ngay);
            end = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            prefix = "";
        } else { // Theo ngày
            start = 0;
            end = 23;
            prefix = "h";
        }

        double maxVal = 0; // 🟢 Biến dùng để lưu giữ cột cao nhất

        for (int i = start; i <= end; i++) {
            String label = ("Theo ngày".equals(loaiThoiGian)) ? i + prefix : prefix + i;
            labels.append(label).append("|");

            double doanhThu = chartData.getOrDefault(i, 0.0);
            double val = Math.round((doanhThu / 1000.0) * 10.0) / 10.0;
            values.append(val).append(",");

            // Tìm giá trị lớn nhất trong tất cả các cột
            if (val > maxVal) {
                maxVal = val;
            }
        }

        String labelStr = labels.toString().replaceAll("\\|+$", "");
        String valueStr = values.toString().replaceAll(",+$", "");

        // 🟢 Nâng "trần nhà" (trục Y) lên 20% so với cột cao nhất để tạo khoảng trống
        // cho số
        final double suggestedMax = (maxVal == 0) ? 100 : (maxVal + maxVal * 0.2);
        chartPanelArea.removeAll();
        JLabel loading = new JLabel("Đang tải biểu đồ Doanh thu...", SwingConstants.CENTER);
        UiTheme.label(loading);
        chartPanelArea.add(loading, BorderLayout.CENTER);
        chartPanelArea.revalidate();
        chartPanelArea.repaint();

        SwingWorker<Image, Void> worker = new SwingWorker<Image, Void>() {
            @Override
            protected Image doInBackground() throws Exception {
                // 🟢 Đã thêm "padding:20" vào legend và "suggestedMax" vào trục Y
                String jsonChart = "{type:'bar',data:{labels:['" + labelStr.replace("|", "','")
                        + "'],datasets:[{label:'Doanh thu (Nghìn VNĐ)',data:[" + valueStr
                        + "],backgroundColor:'rgb(231, 76, 60)'}]},options:{layout:{padding:{top:25}},plugins:{datalabels:{anchor:'end',align:'top',color:'white',font:{size:10,weight:'bold'}}},legend:{labels:{fontColor:'white',padding:20}},scales:{xAxes:[{ticks:{fontColor:'white',fontSize:10}}],yAxes:[{ticks:{fontColor:'white',beginAtZero:true,suggestedMax:"
                        + suggestedMax + "}}]}}}";

                String encodedJson = URLEncoder.encode(jsonChart, "UTF-8");
                String chartUrl = "https://quickchart.io/chart?c=" + encodedJson + "&w=600&h=400&bkg=transparent";

                return javax.imageio.ImageIO.read(new URL(chartUrl));
            }

            @Override
            protected void done() {
                try {
                    Image image = get();
                    chartPanelArea.removeAll();
                    JPanel chartCanvas = new JPanel() {
                        @Override
                        protected void paintComponent(Graphics g) {
                            super.paintComponent(g);
                            g.drawImage(image, 10, 10, getWidth() - 20, getHeight() - 20, this);
                        }
                    };
                    chartCanvas.setOpaque(false);
                    chartPanelArea.add(chartCanvas, BorderLayout.CENTER);
                } catch (Exception e) {
                    chartPanelArea.removeAll();
                    JLabel error = new JLabel("Không thể tải biểu đồ (Cần có Internet)", SwingConstants.CENTER);
                    UiTheme.label(error);
                    chartPanelArea.add(error, BorderLayout.CENTER);
                }
                chartPanelArea.revalidate();
                chartPanelArea.repaint();
            }
        };
        worker.execute();
    }
}