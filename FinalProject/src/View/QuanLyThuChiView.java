package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class QuanLyThuChiView extends JPanel {
	private JTable table;
	private DefaultTableModel tablemodel;
	private JButton JButton_them;
	private JButton JButton_sua;
	private JButton JButton_xoa;
	private JButton JButton_doimoi;

	public QuanLyThuChiView() {
		initUI();
	}

	private void initUI() {
		Font font = new Font("Arial", Font.BOLD, 20);
		Font font1 = new Font("Arial", Font.BOLD, 14);
		this.setLayout(new BorderLayout(10, 10));
		JPanel tieude = new JPanel();
		tieude.setBackground(new Color(52, 152, 219));
		JLabel lblTitle = new JLabel("QUẢN LÝ THU – CHI HỌC PHÍ");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 26));
		lblTitle.setForeground(Color.WHITE);
		tieude.setLayout(new GridBagLayout());
		tieude.add(lblTitle);
		lblTitle.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(QuanLyThuChiView.class.getResource("money.png"))));
		


		JPanel formnhap = new JPanel();
		formnhap.setLayout(new BorderLayout());
		JPanel form = new JPanel(new GridLayout(6, 2, 5, 5));

		JLabel magd = new JLabel("MÃ GIAO DỊCH: ");
		magd.setFont(font);
		JTextField magd1 = new JTextField(20);
		magd1.setFont(font1);
		

		JLabel ngay = new JLabel("NGÀY: ");
		ngay.setFont(font);
		JTextField ngay1 = new JTextField(20);
		ngay1.setFont(font1);
		String[] danhMucThu = {
			    "Thu học phí khóa học",
			    "Thu học phí theo tháng",
			    "Thu phí theo buổi",
			    "Thu phí tài liệu"};
		String[] danhMucChi = {
			    "Chi lương giảng viên",
			    "Chi in tài liệu",
			    "Chi hoàn học phí",
			    "Giảm học phí"
			};
		JLabel loai = new JLabel("LOẠI: ");
		loai.setFont(font);
		JComboBox<String> cbLoai = new JComboBox<>(new String[]{"Thu", "Chi"});
		

		JLabel danhmuc = new JLabel("DANH MỤC: ");
		danhmuc.setFont(font);
		JComboBox<String> cbDanhMuc = new JComboBox<>();
		form.add(cbDanhMuc);
		cbDanhMuc.setModel(new DefaultComboBoxModel<>(danhMucThu));
		cbLoai.addActionListener(e -> {
		    String loai1 = cbLoai.getSelectedItem().toString();

		    if (loai1.equals("Thu")) {
		        cbDanhMuc.setModel(new DefaultComboBoxModel<>(danhMucThu));
		    } else {
		        cbDanhMuc.setModel(new DefaultComboBoxModel<>(danhMucChi));
		    }
		});
		JLabel sotien = new JLabel("SỐ TIỀN: ");
		sotien.setFont(font);
		JTextField sotien1 = new JTextField(20);
		sotien1.setFont(font1);
		JLabel ghichu = new JLabel("GHI CHÚ: ");
		ghichu.setFont(font);
		JTextField ghichu1 = new JTextField(20);
		ghichu1.setFont(font1);
		form.add(magd); form.add(magd1); form.add(ngay); form.add(ngay1); form.add(loai); form.add(cbLoai);;
		form.add(danhmuc); form.add(cbDanhMuc); form.add(sotien); form.add(sotien1); form.add(ghichu); form.add(ghichu1);
		
		Dimension kichco = new Dimension(105, 40);
		JPanel nutnhan = new JPanel();
		JButton_them = new JButton("THÊM");
		JButton_them.setFont(font1);
		JButton_them.setPreferredSize(kichco);
		JButton_sua = new JButton("SỬA");
		JButton_sua.setFont(font1);
		JButton_sua.setPreferredSize(kichco);
		JButton_xoa = new JButton("XÓA");
		JButton_xoa.setFont(font1);
		JButton_xoa.setPreferredSize(kichco);
		JButton_doimoi = new JButton("ĐỔI MỚI");
		JButton_doimoi.setFont(font1);
		JButton_doimoi.setPreferredSize(kichco);
		nutnhan.add(JButton_them);
		nutnhan.add(JButton_sua);
		nutnhan.add(JButton_xoa);
		nutnhan.add(JButton_doimoi);
		formnhap.add(tieude, BorderLayout.NORTH);
		formnhap.add(form, BorderLayout.CENTER);
		formnhap.add(nutnhan, BorderLayout.SOUTH);
		tablemodel = new DefaultTableModel(new String[] {"Mã Giao Dịch", "Ngày", "Loại", "Danh Mục", "Số Tiền", "Ghi Chú"}, 0);
		table = new JTable(tablemodel);
		JScrollPane scroll = new JScrollPane(table);
		
		JPanel bang = new JPanel();
		bang.setLayout(new BorderLayout());
		bang.add(formnhap, BorderLayout.NORTH);
		bang.add(scroll, BorderLayout.CENTER);
		this.add(bang);
		
		
		
		
		
		
		
		
		



		
	}
	
	

}
