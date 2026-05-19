package ViewNew;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import Controller.ChiController;
import Controller.QuanLyGiaoVienController;
import Controller.QuanLyHocVienController;
import Controller.QuanLyKhoaHocController;
//import Controller.QuanLyThuChiController;
import Controller.ThuController;

public class MainView extends JFrame {
	private CardLayout cardlayout;
	private JPanel mainpanel;
	private QuanLyHocVienView hocVienView;
	private QuanLyKhoaHocView khoaHocView;
//	private QuanLyThuChiView thuChiView;
	private QuanLyThuView quanLyThu;
	private QuanLyChiView quanLyChi;
//	private QuanLyDanhMucView danhMucView;
	private QuanLyGiaoVienView giaoVienView;
	private QuanLyKhoaHocController quanLyKhoaHoc;
	private QuanLyHocVienController quanLyHocVienView;
//	private QuanLyThuChiController quanLyThuChi;
	private QuanLyGiaoVienController quanLyGiaoVien;
	private ThuController thucontroller;
	private ChiController chicontroller;
	public MainView() {
		initFrame();
		initMenu();
		initComponents();
		setVisible(true);

	}
	private void initFrame() {
		this.setTitle("QUAN LY THU CHI HOC PHI");
		this.setSize(700, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);	
		
		URL iconmain = MainView.class.getResource("truong-icon.png");
		Image image = Toolkit.getDefaultToolkit().createImage(iconmain);
		this.setIconImage(image);

	}
	private void initMenu() {
		JMenuBar danhSachMenu = new JMenuBar();
		
		JMenu heThong = new JMenu("HE THONG");
		JMenuItem thoat = new JMenuItem("ĐĂNG XUẤT", KeyEvent.VK_0);
		thoat.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_0, InputEvent.ALT_DOWN_MASK));
		heThong.add(thoat);
		thoat.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(MainView.class.getResource("exitnew.png"))));
		
		// Danh mục
		JMenu danhMuc = new JMenu("DANH MUC");
		JMenuItem hocVien = new JMenuItem("QUAN LY HOC VIEN", KeyEvent.VK_1);
		hocVien.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.CTRL_DOWN_MASK));
		hocVien.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(MainView.class.getResource("listhv.png"))));
		JMenuItem khoaHoc = new JMenuItem("QUAN LY KHOA HOC", KeyEvent.VK_2);
		khoaHoc.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.CTRL_DOWN_MASK));
		khoaHoc.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(MainView.class.getResource("course.png"))));
//		JMenuItem danhMuc1 = new JMenuItem("QUAN LY DANH MUC", KeyEvent.VK_3);
//		danhMuc1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, InputEvent.CTRL_DOWN_MASK));
//		danhMuc1.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(MainView.class.getResource("danhmuc.png"))));
		JMenuItem giaoVien = new JMenuItem("QUAN LY GIAO VIEN", KeyEvent.VK_3);
		giaoVien.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, InputEvent.CTRL_DOWN_MASK));
		giaoVien.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(MainView.class.getResource("teacher.png"))));
		danhMuc.add(hocVien);
		danhMuc.add(khoaHoc);
//		danhMuc.add(danhMuc1);
		danhMuc.add(giaoVien);
		
		
		// Thu Chi
		JMenu thuChi = new JMenu("THU-CHI");
		JMenuItem quanLyThu = new JMenuItem("QUAN LY THU ", KeyEvent.VK_4);
		quanLyThu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_4, InputEvent.CTRL_DOWN_MASK));
		quanLyThu.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(MainView.class.getResource("tien.png"))));
		thuChi.add(quanLyThu);
		JMenuItem quanLyChi = new JMenuItem("QUAN LY CHI", KeyEvent.VK_5);
		quanLyChi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_5, InputEvent.CTRL_DOWN_MASK));
		quanLyChi.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(MainView.class.getResource("tien.png"))));
		thuChi.add(quanLyChi);
		
		// thanh công cụ
//		JToolBar jtoolbar = new JToolBar();
//		JButton copy = new JButton("COPY");
//		copy.setToolTipText("Nhấn Vào Đây Để Sao Chép");
//		JButton paste = new JButton("PASTE");
//		paste.setToolTipText("Nhấn Vào Đây Để Dán");
//		jtoolbar.add(copy); jtoolbar.add(paste);
//		add(jtoolbar, BorderLayout.NORTH);
		
		// add vào menubar
		danhSachMenu.add(heThong);
		danhSachMenu.add(danhMuc);
		danhSachMenu.add(thuChi);
		this.setJMenuBar(danhSachMenu);
		thoat.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		
		hocVien.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showHocVien();	
			}
		});
		khoaHoc.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showKhoaHoc();
			}
		});
		quanLyThu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showThu();
			}
		});
	    quanLyChi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showChi();
			}
		});
		giaoVien.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				showGiaoVien();
			}
		});
		
	}
	
	private void initComponents() {
		cardlayout = new CardLayout();
		mainpanel = new JPanel(cardlayout);
		
		hocVienView = new QuanLyHocVienView();
		khoaHocView = new QuanLyKhoaHocView();
//		thuChiView = new QuanLyThuChiView();
		quanLyThu = new QuanLyThuView();
		quanLyChi = new QuanLyChiView();
		giaoVienView = new QuanLyGiaoVienView();
		
		mainpanel.add(hocVienView, "HOC VIEN");
		mainpanel.add(khoaHocView, "KHOA HOC");
		mainpanel.add(quanLyThu, "THU");
		mainpanel.add(quanLyChi, "CHI");
		mainpanel.add(giaoVienView, "GIAO VIEN");
		this.add(mainpanel);
		
		quanLyKhoaHoc = new QuanLyKhoaHocController(khoaHocView, hocVienView);
		quanLyHocVienView = new QuanLyHocVienController(hocVienView);
		quanLyGiaoVien = new QuanLyGiaoVienController(giaoVienView);
		thucontroller = new ThuController(quanLyThu);
		chicontroller = new ChiController(quanLyChi);
//		quanLyThuChi = new QuanLyThuChiController(thuChiView);
		hocVienView.loadKhoaHocToCombo();
		cardlayout.show(mainpanel, "HOC VIEN");
	}
	public void showHocVien() {
		cardlayout.show(mainpanel, "HOC VIEN");
	}
	public void showKhoaHoc() {
		cardlayout.show(mainpanel, "KHOA HOC");
	}
	public void showThu() {
		cardlayout.show(mainpanel, "THU");
	}
	public void showChi() {
		cardlayout.show(mainpanel, "CHI");
	}
	public void showGiaoVien() {
		cardlayout.show(mainpanel, "GIAO VIEN");
	}
}
