package ViewNew;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class QuanLyGiaoVienView extends JPanel {
	private DefaultTableModel DefaultTableModel_tablemodel;
	private JTable JTable_table;
	private JTextField JTextField_magv1;
	private JTextField JTextField_tengv1;
	private JTextField JTextField_sdt1;
	private JTextField JTextField_email1;
	private JTextField JTextField_luong1;
	private JButton JButton_them;
	private JButton JButton_sua;
	private JButton JButton_xoa;
	private JButton JButton_doimoi;
	private JTextField JTextField_timkiem2;
	private JButton JButton_tim;
	private JButton JButton_sapxep;
	private JButton JButton_doiMoi;
	public QuanLyGiaoVienView() {
		initUI();
}
	private void initUI() {
		Font font = new Font("Arial", Font.BOLD, 20);
		Font font2 = new Font("Arial", Font.BOLD, 14);
		this.setLayout(new BorderLayout(10, 10));
		JPanel tieude = new JPanel();
		tieude.setBackground(new Color(0, 51, 102));
		JLabel tieudechinh = new JLabel("QUẢN LÝ GIÁO VIÊN");
		tieudechinh.setFont(new Font("Arial", Font.BOLD, 26));
		tieudechinh.setForeground(Color.WHITE);
		tieude.setLayout(new GridBagLayout());
		tieude.add(tieudechinh);
		
		// thiết kế phần nhập
		JPanel panelthongtin = new JPanel(new BorderLayout());
		panelthongtin.setBackground(new Color(248, 250, 255));
		JPanel formnhap = new JPanel();
		formnhap.setLayout(new GridLayout(7, 2, 15, 12));
		TitledBorder border = BorderFactory.createTitledBorder(
		        BorderFactory.createLineBorder(new Color(188, 250, 245), 1),
		        "Thông Tin Giáo Viên"
		);
		border.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
		border.setTitleColor(new Color(47, 111, 219));
		panelthongtin.setBorder(border);
		panelthongtin.add(formnhap, BorderLayout.CENTER);
		panelthongtin.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);
		formnhap.setLayout(new GridLayout(5, 2));
		JLabel magv = new JLabel("MÃ GIÁO VIÊN: ");
		magv.setFont(font);
		JTextField_magv1 = new JTextField(20);
		JTextField_magv1.setFont(font2);
		JLabel tengv = new JLabel("TÊN GIÁO VIÊN: ");
		tengv.setFont(font);
		JTextField_tengv1 = new JTextField(20);
		JTextField_tengv1.setFont(font2);
		JLabel sdt = new JLabel("SĐT: ");
		sdt.setFont(font);
		JTextField_sdt1 = new JTextField(20);
		JTextField_sdt1.setFont(font2);
		JLabel email = new JLabel("EMAIL: ");
		email.setFont(font);
		JTextField_email1 = new JTextField(30);
		JTextField_email1.setFont(font2);
		JLabel luong = new JLabel("LƯƠNG: ");
		luong.setFont(font);
		JTextField_luong1 =  new JTextField(20);
		JTextField_luong1.setFont(font2);
		formnhap.add(magv); formnhap.add(JTextField_magv1); formnhap.add(tengv); formnhap.add(JTextField_tengv1); formnhap.add(sdt);
		formnhap.add(JTextField_sdt1);	formnhap.add(email);formnhap.add(JTextField_email1); formnhap.add(luong); formnhap.add(JTextField_luong1);
		
		Dimension kichco = new Dimension(105, 40);
		JPanel nutnhan = new JPanel();
		nutnhan.setLayout(new java.awt.FlowLayout(new java.awt.FlowLayout().CENTER, 5, 10));
		JButton_them = new JButton("THÊM");
		JButton_them.setFont(font2);
		JButton_them.setPreferredSize(kichco);
		JButton_sua = new JButton("SỬA");
		JButton_sua.setFont(font2);
		JButton_sua.setPreferredSize(kichco);
		JButton_xoa = new JButton("XÓA");
		JButton_xoa.setFont(font2);
		JButton_xoa.setPreferredSize(kichco);
		JButton_doimoi = new JButton("ĐỔI MỚI");
		JButton_doimoi.setFont(font2);
		JButton_doimoi.setPreferredSize(kichco);
		nutnhan.add(JButton_them); nutnhan.add(JButton_sua); nutnhan.add(JButton_xoa); nutnhan.add(JButton_doimoi);
		
		Dimension kichco2 = new Dimension(95, 30);
		JPanel timKiem = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
		timKiem.setBorder(BorderFactory.createTitledBorder("Tìm Kiếm"));
		timKiem.setForeground(Color.BLUE);
		JLabel timkiem1 = new JLabel("Mã Giáo Viên: ");
		timkiem1.setFont(font2);
		timkiem1.setForeground(Color.BLACK);
		JTextField_timkiem2 = new JTextField(10);
		JTextField_timkiem2.setFont(font2);
		JButton_tim = new JButton("Tìm");
		JButton_tim.setFont(font2);
		JButton_tim.setPreferredSize(kichco2);
		JButton_doiMoi = new JButton("Đổi Mới");
		JButton_doiMoi.setFont(font2);
		JButton_doiMoi.setPreferredSize(kichco2);
		JButton_sapxep = new JButton("Sắp Xếp");
		JButton_sapxep.setFont(font2);
		JButton_sapxep.setPreferredSize(kichco2);
		timKiem.add(timkiem1); timKiem.add(JTextField_timkiem2); timKiem.add(JButton_tim); timKiem.add(JButton_doiMoi); 
		timKiem.add(JButton_sapxep);
		JPanel form = new JPanel();
		form.setLayout(new BorderLayout(10, 10));
		form.add(tieude, BorderLayout.NORTH);
		form.add(panelthongtin, BorderLayout.CENTER);
		form.add(nutnhan, BorderLayout.SOUTH);
		
		DefaultTableModel_tablemodel = new DefaultTableModel(new String[] {"MA GIAO VIEN", "TEN GIAO VIEN", "SĐT", "EMAIL", "LUONG"}, 0);
		JTable_table = new JTable(DefaultTableModel_tablemodel);
		JScrollPane scroll = new JScrollPane(JTable_table);
		
		JPanel formduoi = new JPanel();
		formduoi.setLayout(new BorderLayout());
		formduoi.add(timKiem, BorderLayout.NORTH);
		formduoi.add(scroll, BorderLayout.CENTER);
		JPanel modelmain = new JPanel();
		modelmain.setLayout(new BorderLayout());
		modelmain.add(form, BorderLayout.NORTH);
		modelmain.add(formduoi, BorderLayout.CENTER);
		
		this.add(modelmain);
	}
	public JButton getthem() {
		return JButton_them;
	}
	public JButton getxoa() {
		return JButton_xoa;
	}
	public JButton getsua() {
		return JButton_sua;
	}
	public JButton getdoimoi() {
		return JButton_doimoi;
	}
	public JTextField getmaGV() {
		return JTextField_magv1;
	}
	public JTextField gettenGV() {
		return JTextField_tengv1;
	}
	public JTextField getsdt() {
		return JTextField_sdt1;
	}
	public JTextField getemail() {
		return JTextField_email1;
	}
	public JTextField getluong() {
		return JTextField_luong1;
	}
	public JButton getTim() {
		return JButton_tim;
	}
	public JButton getdoiMoi() {
		return JButton_doiMoi;
	}
	public JButton getsapXep() {
		return JButton_sapxep;
	}
	public JTextField gettim2() {
		return JTextField_timkiem2;
	}
	
	public JTable gettable() {
		return JTable_table;
	}
	
	
	
}
