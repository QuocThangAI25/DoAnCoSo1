package ViewNew;

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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import DAO.AdminDao;

public class LoginAdmin extends JFrame {
	public LoginAdmin() {
		init();
	}
	private void init() {
		setTitle("Đăng Nhập Admin");
		setSize(300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		Font font = new Font("Arial", Font.BOLD, 20);
		Font font1= new Font("Arial", Font.BOLD, 14);
		
		JPanel tieude = new JPanel();
		tieude.setBackground(new Color(0, 51, 102));
		JLabel tieudechinh = new JLabel("ĐĂNG NHẬP ADMIN");
		tieudechinh.setFont(new Font("Arial", Font.BOLD, 26));
		tieudechinh.setForeground(Color.WHITE);
		tieude.setLayout(new GridBagLayout());
		tieude.add(tieudechinh);
		URL khoa = LoginAdmin.class.getResource("keymain.png");
		Image image = Toolkit.getDefaultToolkit().createImage(khoa);
		this.setIconImage(image);

		this.setLayout(new BorderLayout(10, 10));
		JPanel formnhap = new JPanel();
		formnhap.setLayout(new GridLayout(3, 2, 0, 10));
		JLabel user = new JLabel("User: ");
		user.setFont(font);
		user.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(LoginAdmin.class.getResource("user.png"))));
		JTextField user1 = new JTextField(20);
		user1.setFont(font1);
		JLabel pass = new JLabel("PassWord: ");
		pass.setFont(font);
		pass.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(LoginAdmin.class.getResource("key.png"))));
		JPasswordField pass1 = new JPasswordField(20);
		pass1.setFont(font1);
		formnhap.add(user); formnhap.add(user1); formnhap.add(pass); formnhap.add(pass1);
		
		Dimension kichco = new Dimension(120, 40);
		JPanel button = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
		JButton dangnhap = new JButton("Đăng Nhập");
		dangnhap.setFont(font1);
		dangnhap.setBackground(new Color(0, 102, 204));
		dangnhap.setForeground(Color.WHITE);
		dangnhap.setPreferredSize(kichco);
		JButton thoat = new JButton("Thoát");
		thoat.setFont(font1);
		thoat.setBackground(new Color(0, 102, 204));
		thoat.setForeground(Color.WHITE);
		thoat.setPreferredSize(kichco);
		dangnhap.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (AdminDao.login(user1.getText(),
				        new String(pass1.getPassword()))) {

				    JOptionPane.showMessageDialog(dangnhap, "Đăng Nhập Thành Công");
				    new MainView().setVisible(true);
				    dispose();

				} else {
				    JOptionPane.showMessageDialog(dangnhap, "Sai Tài Khoản Hoặc Mật Khẩu");
				    user1.setText("");
				    pass1.setText("");
				}
			}
		});
		thoat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		button.add(dangnhap); button.add(thoat);
		JPanel tong = new JPanel();
		tong.setLayout(new BorderLayout());
		tong.add(formnhap, BorderLayout.NORTH);
		tong.add(button, BorderLayout.CENTER);
		
		this.add(tieude, BorderLayout.CENTER);
		this.add(tong, BorderLayout.SOUTH);
		setVisible(true);
	}
	public static void main(String[] args) {
		new LoginAdmin();
	}
}
