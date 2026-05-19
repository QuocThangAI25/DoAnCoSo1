package ViewNew;

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
	private JComboBox<String> cbMaHV;
	private JComboBox<String> cbMaGV;
	private JComboBox<String> cbloai;
	private JComboBox<String> cbdanhmuc;
	private JTextField txtTenHV;
	private JTextField JTextField_magd1;
	private JTextField JTextField_ngay1;
	private JTextField JTextField_sotien1;
	private JTextField JTextField_ghichu1;
	private JTextField JTextField_tenGV1;

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
		JPanel form = new JPanel(new GridLayout(10, 2, 5, 5));

		JLabel magd = new JLabel("MÃ GIAO DỊCH: ");
		magd.setFont(font);
		JTextField_magd1 = new JTextField(20);
		JTextField_magd1.setFont(font1);
		

		JLabel ngay = new JLabel("NGÀY: ");
		ngay.setFont(font);
		JTextField_ngay1 = new JTextField(20);
		JTextField_ngay1.setFont(font1);
		String[] danhMucThu = {
			    "Thu học phí khóa học",
			    "Thu phí tài liệu"};
		String[] danhMucChi = {
			    "Chi lương giảng viên",
			    "Chi in tài liệu",
			    "Giảm học phí"
			};
		JLabel loai = new JLabel("LOẠI: ");
		loai.setFont(font);
		cbloai = new JComboBox<>(new String[]{"Thu", "Chi"});
		

		JLabel danhmuc = new JLabel("DANH MỤC: ");
		danhmuc.setFont(font);
		cbdanhmuc = new JComboBox<>();
		form.add(cbdanhmuc);
		cbdanhmuc.setModel(new DefaultComboBoxModel<>(danhMucThu));
		cbloai.addActionListener(e -> {
		    String loai1 = cbloai.getSelectedItem().toString();

		    if (loai1.equals("Thu")) {
		        cbdanhmuc.setModel(new DefaultComboBoxModel<>(danhMucThu));
		    } else {
		        cbdanhmuc.setModel(new DefaultComboBoxModel<>(danhMucChi));
		    }
		});
		JLabel mahv = new JLabel("MÃ HỌC VIÊN: ");
		mahv.setFont(font);
		cbMaHV = new JComboBox<>();
		JLabel tenhv = new JLabel("TÊN HỌC VIÊN: ");
		tenhv.setFont(font);
		txtTenHV = new JTextField();
		txtTenHV.setFont(font1);
		txtTenHV.setEditable(false);
		JLabel maGV = new JLabel("MÃ GIÁO VIÊN: ");
		maGV.setFont(font);
		cbMaGV = new JComboBox<>();
		JLabel tenGV = new JLabel("TÊN GIÁO VIÊN: ");
		tenGV.setFont(font);
		JTextField_tenGV1 = new JTextField(20);
		JTextField_tenGV1.setFont(font1);
		JLabel sotien = new JLabel("SỐ TIỀN: ");
		sotien.setFont(font);
		JTextField_sotien1 = new JTextField(20);
		JTextField_sotien1.setFont(font1);
		JLabel ghichu = new JLabel("GHI CHÚ: ");
		ghichu.setFont(font);
		JTextField_ghichu1 = new JTextField(20);
		JTextField_ghichu1.setFont(font1);
		form.add(magd); form.add(JTextField_magd1); form.add(ngay); form.add(JTextField_ngay1); form.add(loai); form.add(cbloai);;
		form.add(danhmuc); form.add(cbdanhmuc); form.add(mahv); form.add(cbMaHV); form.add(tenhv); form.add(txtTenHV);
	    form.add(maGV); form.add(cbMaGV); form.add(tenGV); form.add(JTextField_tenGV1); form.add(sotien); form.add(JTextField_sotien1); 
	    form.add(ghichu); form.add(JTextField_ghichu1);
		
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
	public JButton getbtthem() {
		return JButton_them;
	}
	public JButton getbtxoa() {
		return JButton_xoa;
	}
	public JButton getbtsua() {
		return JButton_sua;
	}
	public JButton getbtdoimoi() {
		return JButton_doimoi;
	}
	public JTable getTable() {
	    return table;
	}

	public DefaultTableModel getTableModel() {
	    return tablemodel;
	}

	// ===== FORM INPUT =====
	public JTextField getTxtMaGD() {
	    return JTextField_magd1;
	}

	public JTextField getTxtNgay() {
	    return JTextField_ngay1;
	}

	public JTextField getTxtSoTien() {
	    return JTextField_sotien1;
	}

	public JTextField getTxtGhiChu() {
	    return JTextField_ghichu1;
	}

	// ===== COMBOBOX =====
	public JComboBox<String> getCbLoai() {
	    return cbloai;
	}

	public JComboBox<String> getCbDanhMuc() {
	    return cbdanhmuc;
	}

	public JComboBox<String> getCbMaHV() {
	    return cbMaHV;
	}
	public JComboBox<String> getcbMaGV(){
		return cbMaGV;
	}
	public JTextField gettenGV() {
		return JTextField_tenGV1;
	}

	// ===== HỌC VIÊN =====
	public JTextField getTxtTenHV() {
	    return txtTenHV;
	}
	

}
