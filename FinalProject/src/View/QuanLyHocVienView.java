package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class QuanLyHocVienView extends JPanel{
	private DefaultTableModel DefaultTableModel_tablemodel;
	private JTable JTable_table;
	private JTextField JTextField_mahv;
	private JTextField JTextField_tenhv;
	private JTextField JTextField_lop;
	private JTextField JTextField_sdt;
	private JTextField JTextField_email1;
	private JButton JButton_them;
	private JButton JButton_xoa;
	private JButton JButton_sua;
	private JButton JButton_doimoi;
	private JLabel JLabel_mahocvien;
	private JLabel JLabel_tenhocvien;
	private JLabel JLabel_malop;
	private JLabel JLabel_sodienthoai;
	private JLabel JLabel_email;
	private JTextField JTextField_mahv3;
	private JButton JButton_tim;
	private JButton JButton_reset;

	public QuanLyHocVienView() {
		initUI();
	}
	private void initUI() {
		this.setLayout(new BorderLayout(10, 10));
		Font font = new Font("Arial", Font.BOLD, 20);
		Font font2 = new Font("Arial", Font.BOLD, 14);
		JPanel tieude = new JPanel();
		tieude.setBackground(new Color(95, 163, 247));
		JLabel lblTitle = new JLabel("QUẢN LÝ HỌC VIÊN");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 26));
		lblTitle.setForeground(Color.WHITE);
		tieude.setLayout(new GridBagLayout());
		tieude.add(lblTitle);
		lblTitle.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(QuanLyHocVienView.class.getResource("studentnew.png"))));
		
		JPanel panelthongtin = new JPanel(new BorderLayout());
		panelthongtin.setBackground(new Color(248, 250, 255));
		JPanel formnhap = new JPanel();
		formnhap.setLayout(new GridLayout(5, 2, 15, 12));
		TitledBorder border = BorderFactory.createTitledBorder(
		        BorderFactory.createLineBorder(new Color(182, 200, 245), 1),
		        "Thông tin học viên"
		);
		border.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
		border.setTitleColor(new Color(47, 111, 219));
		panelthongtin.setBorder(border);
		panelthongtin.add(formnhap, BorderLayout.CENTER);
		panelthongtin.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);
		JLabel_mahocvien = new JLabel("MA HOC VIEN: ");
		JLabel_mahocvien.setFont(font);
		JTextField_mahv = new JTextField(20);
		JTextField_mahv.setFont(font2);
		JLabel_tenhocvien = new JLabel("TEN HOC VIEN: ");
		JLabel_tenhocvien.setFont(font);
		JTextField_tenhv = new JTextField(20);
		JTextField_tenhv.setFont(font2);
		JLabel_malop = new JLabel("MA LOP: ");
		JLabel_malop.setFont(font);
		JTextField_lop = new JTextField(20);
		JTextField_lop.setFont(font2);
		JLabel_sodienthoai = new JLabel("SĐT: ");
		JLabel_sodienthoai.setFont(font);
		JTextField_sdt = new JTextField(25);
		JTextField_sdt.setFont(font2);
		JLabel_email = new JLabel("EMAIL: ");
		JLabel_email.setFont(font);
		JTextField_email1 = new JTextField(20);
		JTextField_email1.setFont(font2);
		
		formnhap.add(JLabel_mahocvien); formnhap.add(JTextField_mahv); formnhap.add(JLabel_tenhocvien); formnhap.add(JTextField_tenhv);
		formnhap.add(JLabel_malop); formnhap.add(JTextField_lop); formnhap.add(JLabel_sodienthoai); formnhap.add(JTextField_sdt); 
		formnhap.add(JLabel_email); formnhap.add(JTextField_email1);
		
		Dimension kichco = new Dimension(105, 40);
		JPanel button = new JPanel();
		button.setLayout(new java.awt.FlowLayout( java.awt.FlowLayout.CENTER, 5, 10));
		JButton_them = new JButton("THÊM");
		JButton_them.setFont(font2);
		JButton_them.setPreferredSize(kichco);
		JButton_them.setBackground(new Color(0, 153, 76));
		JButton_them.setForeground(Color.WHITE);
		JButton_xoa = new JButton("XÓA");
		JButton_xoa.setFont(font2);
		JButton_xoa.setPreferredSize(kichco);
		JButton_xoa.setBackground(new Color(204, 0, 0));
		JButton_xoa.setForeground(Color.WHITE);
		JButton_sua = new JButton("SỬA");
		JButton_sua.setFont(font2);
		JButton_sua.setPreferredSize(kichco);
		JButton_sua.setBackground(new Color(255, 153, 51));
		JButton_sua.setForeground(Color.WHITE);
		JButton_doimoi = new JButton("ĐỔI MỚI");
		JButton_doimoi.setFont(font2);
		JButton_doimoi.setPreferredSize(kichco);
		JButton_doimoi.setBackground(new Color(180, 180, 180));
		JButton_doimoi.setForeground(Color.WHITE);
		button.add(JButton_them); button.add(JButton_xoa); button.add(JButton_sua); button.add(JButton_doimoi);
		
		
		JPanel timKiem = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
		timKiem.setBorder(BorderFactory.createTitledBorder("TÌM KIẾM"));
		
		JLabel mahv2 = new JLabel("MÃ HỌC VIÊN");
		JTextField_mahv3 = new JTextField(20);
		JButton_tim = new JButton("TÌM");
		JButton_reset = new JButton("LÀM MỚi");
		timKiem.add(mahv2); timKiem.add(JTextField_mahv3); timKiem.add(JButton_tim); timKiem.add(JButton_reset);
		
		
		
		DefaultTableModel_tablemodel = new DefaultTableModel(new String[] {"MA HOC VIEN", "TEN HOC VIEN", "MA LOP", "SDT", "EMAIL"}, 0);
		
		JTable_table = new JTable(DefaultTableModel_tablemodel);
		JScrollPane scroll = new JScrollPane(JTable_table);
		JPanel formnhapp = new JPanel();
		formnhapp.setLayout(new BorderLayout());
		formnhapp.add(tieude, BorderLayout.NORTH);
		formnhapp.add(panelthongtin, BorderLayout.CENTER);
		formnhapp.add(button, BorderLayout.SOUTH);
		
		JPanel formDuoi = new JPanel();
		formDuoi.setLayout(new BorderLayout());
		formDuoi.add(timKiem, BorderLayout.NORTH);
		formDuoi.add(scroll, BorderLayout.CENTER);
		JPanel bangnhap = new JPanel();
		bangnhap.setLayout(new BorderLayout());
		bangnhap.add(formnhapp, BorderLayout.NORTH);
		bangnhap.add(formDuoi, BorderLayout.CENTER);
		this.add(bangnhap);
	}
	
	public JTextField getmahv() {
		return JTextField_mahv;
	}
	public JTextField gettenhv() {
		return JTextField_tenhv;
	}
	public JTextField getlop() {
		return JTextField_lop;
	}
	public JTextField getsdt() {
		return JTextField_sdt;
	}
	public JTextField getemail() {
		return JTextField_email1;
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

	public JTable gettable() {
		return JTable_table;
	}
	public JTextField getSearch() {
		return JTextField_mahv3;
	}
	public JButton getTim() {
		return JButton_tim;
	}
	public JButton getReset() {
		return JButton_reset;
	}
	
	

}
