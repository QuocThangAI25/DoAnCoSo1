package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Controller.QuanLyKhoaHocController;

public class QuanLyKhoaHocView extends JPanel {
	private JTable JTable_table;
	private DefaultTableModel DefaultTableModel_tablemodel;
	private JButton JButton_them;
	private JButton JButton_xoa;
	private JButton JButton_sua;
	private JButton JButton_doimoi;
	private JTextField JTextField_makh1;
	private JTextField JTextField_hocphi1;
	private JTextField JTextField_thoiluong1;
	private JTextField JTextField_ngkhaigiang1;
	private JTextField JTextField_trangthai1;
	private JTextField JTextField_tenkh1;
	private JLabel JLabel_makh;
	private JLabel JLabel_tenkhoahoc;
	private JLabel JLabel_hocphi;
	private JLabel JLabel_thoiluong;
	private JLabel JLabel_ngkhaigiang;
	private JLabel JLabel_trangthai;
	private JLabel JLabel_lblMa;
	private JLabel JLabel_lblHocPhi;
	private JLabel JLabel_lblTen;
	private JLabel JLabel_lblThoiLuong;
	private JLabel JLabel_lblNgayKG;
	private JLabel JLabel_lblTrangThai;
	public QuanLyKhoaHocView() {
		initUI();
	}
	private void initUI() {
		Font font = new Font("Arial", Font.BOLD, 16);
		Font font2 = new Font("Arial", Font.BOLD, 13);
		this.setLayout(new BorderLayout(10, 10));
		JPanel tieude = new JPanel();
		tieude.setBackground(new Color(46, 204, 113));
		JLabel lblTitle = new JLabel("QUẢN LÝ KHÓA HỌC");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 26));
		lblTitle.setForeground(Color.WHITE);
		tieude.setLayout(new GridBagLayout());
		tieude.add(lblTitle);
		lblTitle.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(QuanLyKhoaHocView.class.getResource("khoahoc.png"))));
		JPanel formnhap = new JPanel();
		formnhap.setLayout(new GridLayout(6, 2));
		JLabel_makh = new JLabel("MA KHOA HOC: ");
		JLabel_makh.setFont(font);
		JTextField_makh1 = new JTextField(50);
		JTextField_makh1.setFont(font2);
		JLabel_tenkhoahoc = new JLabel("TEN KHOA HOC: ");
		JLabel_tenkhoahoc.setFont(font);
		JTextField_tenkh1 = new JTextField(50);
		JTextField_tenkh1.setFont(font2);
		JLabel_hocphi = new JLabel("HOC PHI: ");
		JLabel_hocphi.setFont(font);
		JTextField_hocphi1 = new JTextField(20);
		JTextField_hocphi1.setFont(font2);
		JLabel_thoiluong = new JLabel("THỜI LƯỢNG: ");
		JLabel_thoiluong.setFont(font);
		JTextField_thoiluong1 = new JTextField(15);
		JTextField_thoiluong1.setFont(font2);
		JLabel_ngkhaigiang = new JLabel("NGAY KHAI GIẢNG: ");
		JLabel_ngkhaigiang.setFont(font);
		JTextField_ngkhaigiang1 = new JTextField(50);
		JTextField_ngkhaigiang1.setFont(font2);
		JLabel_trangthai = new JLabel("TRẠNG THÁI: ");
		JLabel_trangthai.setFont(font);
		JTextField_trangthai1 = new JTextField(50);
		JTextField_trangthai1.setFont(font2);
		
		formnhap.add(JLabel_makh); formnhap.add(JTextField_makh1); formnhap.add(JLabel_tenkhoahoc); formnhap.add(JTextField_tenkh1);
		formnhap.add(JLabel_hocphi); formnhap.add(JTextField_hocphi1); formnhap.add(JLabel_thoiluong); formnhap.add(JTextField_thoiluong1); 
		formnhap.add(JLabel_ngkhaigiang); formnhap.add(JTextField_ngkhaigiang1); formnhap.add(JLabel_trangthai); formnhap.add(JTextField_trangthai1);
		
		Dimension kichco = new Dimension(105, 40);
		JPanel button = new JPanel();
		button.setLayout(new java.awt.FlowLayout( java.awt.FlowLayout.CENTER, 5, 10));
		JButton_them = new JButton("THÊM");
		JButton_them.setFont(font2);
		JButton_them.setPreferredSize(kichco);
		JButton_xoa = new JButton("XÓA");
		JButton_xoa.setFont(font2);
		JButton_xoa.setPreferredSize(kichco);
		JButton_sua = new JButton("SỬA");
		JButton_sua.setFont(font2);
		JButton_sua.setPreferredSize(kichco);
		JButton_doimoi = new JButton("ĐỔI MỚI");
		JButton_doimoi.setFont(font2);
		JButton_doimoi.setPreferredSize(kichco);
		button.add(JButton_them); button.add(JButton_xoa); button.add(JButton_sua); button.add(JButton_doimoi);
		
		// ===== PANEL THÔNG TIN KHÓA HỌC (BÊN PHẢI) =====
		JPanel thongTinPanel = new JPanel();
		thongTinPanel.setBorder(
		    javax.swing.BorderFactory.createTitledBorder("THÔNG TIN KHÓA HỌC")
		);
		thongTinPanel.setLayout(new GridLayout(6, 2, 8, 8));

		JLabel_lblMa = new JLabel("-");
		JLabel_lblTen = new JLabel("-");
		JLabel_lblHocPhi = new JLabel("-");
		JLabel_lblThoiLuong = new JLabel("-");
		JLabel_lblNgayKG = new JLabel("-");
		JLabel_lblTrangThai = new JLabel("-");

		thongTinPanel.add(new JLabel("Mã khóa học:"));
		thongTinPanel.add(JLabel_lblMa);

		thongTinPanel.add(new JLabel("Tên khóa học:"));
		thongTinPanel.add(JLabel_lblTen);

		thongTinPanel.add(new JLabel("Học phí:"));
		thongTinPanel.add(JLabel_lblHocPhi);

		thongTinPanel.add(new JLabel("Thời lượng:"));
		thongTinPanel.add(JLabel_lblThoiLuong);

		thongTinPanel.add(new JLabel("Ngày khai giảng:"));
		thongTinPanel.add(JLabel_lblNgayKG);

		thongTinPanel.add(new JLabel("Trạng thái:"));
		thongTinPanel.add(JLabel_lblTrangThai);

		JTable_table = new JTable();
		DefaultTableModel_tablemodel = new DefaultTableModel(new String[] {"MA KHOA HOC", "TEN KHOA HOC", "HOC PHI", "THOI LUONG", "NGAY KHAI GIANG", "TRANG THAI"}, 0);
		JTable_table = new JTable(DefaultTableModel_tablemodel);
		JScrollPane scroll = new JScrollPane(JTable_table);
		
		
		
		JPanel bangnhap = new JPanel();
		bangnhap.setLayout(new BorderLayout());
		bangnhap.add(tieude, BorderLayout.NORTH);
		bangnhap.add(formnhap, BorderLayout.CENTER);
		bangnhap.add(button, BorderLayout.SOUTH);
		
		JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 0));
		topPanel.add(bangnhap);      // bên trái
		topPanel.add(thongTinPanel); // bên phải

		JPanel tong = new JPanel();
		tong.setLayout(new BorderLayout());
		tong.add(topPanel, BorderLayout.NORTH);
		tong.add(scroll, BorderLayout.CENTER);
		
		add(tong);
	}
	public JButton getbtnthem(){
		return JButton_them;
	}
	public JButton getbtnsua(){
		return JButton_sua;
	}
	public JButton getbtnxoa(){
		return JButton_xoa;
	}
	public JButton getbtndoimoi(){
		return JButton_doimoi;
	}
	public JTextField getxtkh() {
		return JTextField_makh1;
	}
	public JTextField getxttenkh() {
		return JTextField_tenkh1;
	}
	public JTextField getxthp() {
		return JTextField_hocphi1;
	}
	public JTextField getxttl() {
		return JTextField_thoiluong1;
	}
	public JTextField getxtnkh() {
		return JTextField_ngkhaigiang1;
	}
	public JTextField getxttt() {
		return JTextField_trangthai1;
	}
	
	// bảng
	public JLabel getlbkh() {
		return JLabel_lblMa;
	}
	public JLabel getlbtenkh() {
		return JLabel_lblTen;
	}
	public JLabel getlbhp() {
		return JLabel_lblHocPhi;
	}
	public JLabel getlbtl() {
		return JLabel_lblThoiLuong;
	}
	public JLabel getlbngaykg() {
		return JLabel_lblNgayKG;
	}
	public JLabel getlbtt() {
		return JLabel_lblTrangThai;
	}
	public JTable gettable() {
		return JTable_table;
	}
}


