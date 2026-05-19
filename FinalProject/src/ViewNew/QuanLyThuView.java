package ViewNew;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import Model.QuanLyHocVienModel;
import Model.QuanLyKhoaHocModel;

public class QuanLyThuView extends JPanel {
	private JTable table;
	private DefaultTableModel tablemodel;
	private JButton JButton_them;
	private JButton JButton_sua;
	private JButton JButton_xoa;
	private JButton JButton_doimoi;
	private JComboBox<QuanLyHocVienModel> cbMaHV;
	private JComboBox<QuanLyKhoaHocModel> cbmaKH;
	private JComboBox<String> cbloai;
	private JTextField txtTenHV;
	private JTextField JTextField_magd1;
	private JTextField JTextField_ngay1;
	private JTextField JTextField_sotien1;
	private JTextField JTextField_ghichu1;
	private JTextField JTextField_tenKH1;
	private JTextField JTextField_hinhThucThu1;
	private JTextField JTextField_magd2;
	private JButton JButton_tim;
	private JButton JButton_reset;
	private JButton JButton_sapxep;
	private JButton JButton_XuatPDF;

	public QuanLyThuView() {
		initUI();
	}

	private void initUI() {
		Font font = new Font("Arial", Font.BOLD, 20);
		Font font1 = new Font("Arial", Font.BOLD, 14);
		this.setLayout(new BorderLayout(10, 10));
		JPanel tieude = new JPanel();
		tieude.setBackground(new Color(52, 152, 219));
		JLabel lblTitle = new JLabel("QUẢN LÝ THU HỌC PHÍ");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 26));
		lblTitle.setForeground(Color.WHITE);
		tieude.setLayout(new GridBagLayout());
		tieude.add(lblTitle);
		lblTitle.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(QuanLyThuChiView.class.getResource("moneythu.png"))));
		JPanel panelthongtin = new JPanel(new BorderLayout());
		panelthongtin.setBackground(new Color(248, 250, 255));
		JPanel formnhap = new JPanel();
		formnhap.setLayout(new GridLayout(7, 2, 15, 12));
		TitledBorder border = BorderFactory.createTitledBorder(
		        BorderFactory.createLineBorder(new Color(182, 200, 245), 1),
		        "Thông Tin Giao Dịch"
		);
		border.setTitleFont(new Font("Segoe UI", Font.BOLD, 14));
		border.setTitleColor(new Color(47, 111, 219));
		panelthongtin.setBorder(border);
		panelthongtin.add(formnhap, BorderLayout.CENTER);
		panelthongtin.add(Box.createVerticalStrut(10), BorderLayout.SOUTH);
		formnhap.setLayout(new BorderLayout());
		JPanel form = new JPanel(new GridLayout(10, 2, 5, 5));

		JLabel magd = new JLabel("MÃ GIAO DỊCH: ");
		magd.setFont(font);
		JTextField_magd1 = new JTextField(20);
		JTextField_magd1.setFont(font1);
		JTextField_magd1.setEditable(false);
		JLabel loai = new JLabel("LOẠI: ");
		loai.setFont(font);
		cbloai = new JComboBox<>(new String[] {"Thu Học Phí", "Thu Tiền In Tài Liệu"});
		cbloai.setFont(font1);
		JLabel mahv = new JLabel("MÃ HỌC VIÊN: ");
		mahv.setFont(font);
		cbMaHV = new JComboBox<>();
		JLabel tenhv = new JLabel("TÊN HỌC VIÊN: ");
		tenhv.setFont(font);
		txtTenHV = new JTextField(20);
		txtTenHV.setFont(font1);
		txtTenHV.setEditable(false);
		JLabel makh = new JLabel("MÃ KHÓA HỌC: ");
		makh.setFont(font);
		cbmaKH = new JComboBox<>();
		JLabel tenKH = new JLabel("TÊN KHÓA HỌC: ");
		tenKH.setFont(font);
		JTextField_tenKH1 = new JTextField(20);
		JTextField_tenKH1.setFont(font1);
		JTextField_tenKH1.setEditable(false);
		JLabel hinhThucThu = new JLabel("HÌNH THỨC THU: ");
		hinhThucThu.setFont(font);
		JTextField_hinhThucThu1 = new JTextField(20);
		JTextField_hinhThucThu1.setFont(font1);
		JTextField_hinhThucThu1.setEditable(false);
		JLabel ngay = new JLabel("NGÀY: ");
		ngay.setFont(font);
		JTextField_ngay1 = new JTextField(20);
		JTextField_ngay1.setFont(font1);
		JLabel sotien = new JLabel("SỐ TIỀN: ");
		sotien.setFont(font);
		JTextField_sotien1 = new JTextField(20);
		JTextField_sotien1.setFont(font1);
		JTextField_sotien1.setEditable(false);
		JLabel ghichu = new JLabel("GHI CHÚ: ");
		ghichu.setFont(font);
		JTextField_ghichu1 = new JTextField(20);
		JTextField_ghichu1.setFont(font1);
		form.add(magd); form.add(JTextField_magd1); form.add(loai); form.add(cbloai); form.add(mahv); form.add(cbMaHV); form.add(tenhv); form.add(txtTenHV);;
		form.add(makh); form.add(cbmaKH); form.add(tenKH); form.add(JTextField_tenKH1); form.add(hinhThucThu); form.add(JTextField_hinhThucThu1);
	    form.add(ngay); form.add(JTextField_ngay1); form.add(sotien); form.add(JTextField_sotien1); form.add(ghichu); form.add(JTextField_ghichu1); 
		
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
		Dimension kichCo1 = new Dimension(150, 40);
		JButton_XuatPDF = new JButton("XUẤT HÓA ĐƠN");
		JButton_XuatPDF.setFont(font1);
		JButton_XuatPDF.setPreferredSize(kichCo1);
		nutnhan.add(JButton_them);
		nutnhan.add(JButton_sua);
		nutnhan.add(JButton_xoa);
		nutnhan.add(JButton_doimoi);
		nutnhan.add(JButton_XuatPDF);
		
		JPanel timKiem = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
		timKiem.setBorder(BorderFactory.createTitledBorder("TÌM KIẾM"));
		timKiem.setForeground(new Color(100, 111, 219));
		
		JLabel magd1 = new JLabel("MÃ GIAO DỊCH: ");
		JTextField_magd2 = new JTextField(20);
		JButton_tim = new JButton("TÌM");
		JButton_reset = new JButton("LÀM MỚi");
		JButton_sapxep = new JButton("Sắp Xếp");
		timKiem.add(magd1); timKiem.add(JTextField_magd2); timKiem.add(JButton_tim); timKiem.add(JButton_reset); timKiem.add(JButton_sapxep);
		
		
		formnhap.add(tieude, BorderLayout.NORTH);
		formnhap.add(form, BorderLayout.CENTER);
		formnhap.add(nutnhan, BorderLayout.SOUTH);
		tablemodel = new DefaultTableModel(new String[] {"Mã Giao Dịch", "Loại", "Mã Học Viên", "Tên Học Viên", "Mã Khóa Hoc", "Tên Khóa Học", "Hình Thức Thu", "Ngày", "Số Tiền", "Ghi Chú"}, 0);
		table = new JTable(tablemodel);
		JScrollPane scroll = new JScrollPane(table);
		
		JPanel formTim = new JPanel();
		formTim.setLayout(new BorderLayout());
		formTim.add(timKiem, BorderLayout.NORTH);
		formTim.add(scroll, BorderLayout.CENTER);
		JPanel bang = new JPanel();
		bang.setLayout(new BorderLayout());
		bang.add(panelthongtin, BorderLayout.NORTH);
		bang.add(formTim, BorderLayout.CENTER);
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
	public JButton getXuatHoaDon() {
		return JButton_XuatPDF;
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

	public JComboBox<QuanLyHocVienModel> getCbMaHV() {
	    return cbMaHV;
	}
	public JTextField getTxtTenHV() {
	    return txtTenHV;
	}
	public JComboBox<QuanLyKhoaHocModel> getCbMaKH() {
	    return cbmaKH;
	}
	public JTextField getTxtTenKH() {
	    return JTextField_tenKH1;
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
	public JComboBox<String> getCbLoaiThu() {
	    return cbloai;
	}

	public JTextField getTxtHinhThucThu() {
	    return JTextField_hinhThucThu1;
	}
	public JTextField getTimKiem() {
		return JTextField_magd2;
	}
	public JButton getTim() {
		return JButton_tim;
	}
	public JButton getLamMoi() {
		return JButton_reset;
	}
	public JButton getSapXep() {
		return JButton_sapxep;
	}

}
