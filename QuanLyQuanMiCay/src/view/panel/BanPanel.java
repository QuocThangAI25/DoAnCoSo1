package view.panel;



import model.Ban;

import service.QuanLyBanService;

import util.UiTheme;

import view.frame.MainFrame;



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

        UiTheme.panel(this);

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));



        JLabel lblTitle = new JLabel("SƠ ĐỒ BÀN", SwingConstants.CENTER);

        UiTheme.title(lblTitle, 26);

        add(lblTitle, BorderLayout.NORTH);



        panelBan = new JPanel(new GridLayout(2, 4, 15, 15));

        UiTheme.panel(panelBan);

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

        boolean trong = ban.getTrangThai().equals("Trống");

        JButton btn = new JButton("<html><center><b>Bàn " + ban.getSoBan() + "</b><br>" + ban.getTrangThai() + "</center></html>");

        btn.setPreferredSize(new Dimension(120, 100));

        UiTheme.banButton(btn, trong);

        btn.addActionListener(e -> mainFrame.chonBan(ban.getSoBan()));

        return btn;

    }



    public void refresh() {

        loadData();

    }

}


