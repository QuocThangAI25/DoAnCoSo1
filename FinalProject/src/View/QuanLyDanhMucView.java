package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class QuanLyDanhMucView extends JPanel {
	private DefaultTableModel DefaultTableModel_tablemodel;
	private JTable JTable_table;
	public QuanLyDanhMucView() {
		initUI();
}
	private void initUI() {
		Font font = new Font("Arial", Font.BOLD, 20);
		Font font2 = new Font("Arial", Font.BOLD, 14);
		this.setLayout(new BorderLayout(10, 10));
		JPanel tieude = new JPanel();
		tieude.setBackground(new Color(0, 51, 102));
		JLabel tieudechinh = new JLabel("QUẢN LÝ DANH MỤC");
		tieudechinh.setFont(new Font("Arial", Font.BOLD, 26));
		tieudechinh.setForeground(Color.WHITE);
		tieude.setLayout(new GridBagLayout());
		tieude.add(tieudechinh);
		
		// thiết kế phần nhập
		JPanel formnhap = new JPanel();
		formnhap.setLayout(new GridLayout(4, 2));
		JLabel madm = new JLabel("MÃ DANH MỤC: ");
		madm.setFont(font);
		JTextField madm1 = new JTextField(20);
		madm1.setFont(font2);
		JLabel tendm = new JLabel("TÊN DANH MỤC: ");
		tendm.setFont(font);
		JTextField tendm1 = new JTextField(20);
		tendm1.setFont(font2);
		JLabel loai = new JLabel("LOẠI: ");
		loai.setFont(font);
		JComboBox<String> danhmucloai = new JComboBox<String>(new String[] {"THU", "CHI"});
		danhmucloai.setFont(font2);
		JLabel mota = new JLabel("MÔ TẢ: ");
		mota.setFont(font);
		JTextField mota1 = new JTextField(30);
		mota1.setFont(font2);
		formnhap.add(madm); formnhap.add(madm1); formnhap.add(tendm); formnhap.add(tendm1);	formnhap.add(loai);
		formnhap.add(danhmucloai);	formnhap.add(mota);	formnhap.add(mota1);
		
		Dimension kichco = new Dimension(105, 40);
		JPanel nutnhan = new JPanel();
		nutnhan.setLayout(new java.awt.FlowLayout(new java.awt.FlowLayout().CENTER, 5, 10));
		JButton them = new JButton("THÊM");
		them.setFont(font2);
		them.setPreferredSize(kichco);
		JButton sua = new JButton("SỬA");
		sua.setFont(font2);
		sua.setPreferredSize(kichco);
		JButton xoa = new JButton("XÓA");
		xoa.setFont(font2);
		xoa.setPreferredSize(kichco);
		JButton doimoi = new JButton("ĐỔI MỚI");
		doimoi.setFont(font2);
		doimoi.setPreferredSize(kichco);
		nutnhan.add(them); nutnhan.add(sua); nutnhan.add(xoa); nutnhan.add(doimoi);
		
		JPanel form = new JPanel();
		form.setLayout(new BorderLayout(10, 10));
		form.add(tieude, BorderLayout.NORTH);
		form.add(formnhap, BorderLayout.CENTER);
		form.add(nutnhan, BorderLayout.SOUTH);
		
		DefaultTableModel_tablemodel = new DefaultTableModel(new String[] {"MA DANH MUC", "TEN DANH MUC", "LOAI", "MO TA"}, 0);
		JTable_table = new JTable(DefaultTableModel_tablemodel);
		JScrollPane scroll = new JScrollPane(JTable_table);
		
		JPanel modelmain = new JPanel();
		modelmain.setLayout(new BorderLayout());
		modelmain.add(form, BorderLayout.NORTH);
		modelmain.add(scroll, BorderLayout.CENTER);
		
		this.add(modelmain);
		
		
		
	}
}
