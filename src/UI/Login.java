package UI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import com.sun.java.swing.plaf.nimbus.*;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_3;
	private JPasswordField passwordField;
	private JTextField textField_2;
	
	public String ipAddress, User, password, name;
	public FTPClient ftpClient = new FTPClient();
	public int portNum;

//	/**
//	 * Launch the application.
//	 * @throws UnknownHostException 
//	 */
//	public static void main(String[] args) throws UnknownHostException {
//		Login login = new Login();
//		login.loginRun();
//	}

	/**
	 * 
	 */
	public void loginRun() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
//					UIManager.setLookAndFeel(new NimbusLookAndFeel());
//					org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
					UIManager.setLookAndFeel(new NimbusLookAndFeel());//设置一个非常漂亮的外观
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws UnknownHostException 
	 */
	public Login() throws UnknownHostException {
		
		final Login login = this;								//传递Login()，登陆后关闭界面
		InetAddress ip = InetAddress.getLocalHost();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("FtpNetDisk");
		setBounds(100, 100, 513, 340);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setOpaque(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ImageIcon icon=new ImageIcon("src/OtherComponent/login.jpg");  				//路径格式与JTree不同
		JLabel label_5 = new JLabel(icon);
		getLayeredPane().add(label_5,new Integer(Integer.MIN_VALUE));
		label_5.setBounds(0, 0, 600, 630);
		
		JLabel label = new JLabel("\u4E3B\u673A\u5730\u5740\uFF1A");
		label.setFont(new Font("微软雅黑", Font.BOLD, 12));
		label.setBounds(131, 36, 70, 23);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel(" \u7528\u6237\u540D \uFF1A");
		label_1.setFont(new Font("微软雅黑", Font.BOLD, 12));
		label_1.setBounds(131, 69, 70, 23);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel(" \u5BC6  \u7801  \uFF1A");
		label_2.setFont(new Font("微软雅黑", Font.BOLD, 12));
		label_2.setBounds(131, 102, 70, 23);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel(" \u7AEF  \u53E3  \uFF1A");
		label_3.setFont(new Font("微软雅黑", Font.BOLD, 12));
		label_3.setBounds(131, 135, 70, 23);
		contentPane.add(label_3);
		
		textField = new JTextField();
		textField.setBounds(225, 37, 120, 22);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.setText(ip.getHostAddress());
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(225, 70, 120, 22);
		contentPane.add(textField_1);
		textField_1.setText("FtpUser");
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(225, 136, 120, 22);
		contentPane.add(textField_3);
		textField_3.setText("21");
		
		JButton btnNewButton = new JButton("\u767B  \u9646");			//登陆
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String password = new String(passwordField.getPassword());
				try {
					ftpLogin(textField.getText(), textField_1.getText(), password , Integer.parseInt(textField_3.getText()), login);
				} catch (NumberFormatException e) {
					
					e.printStackTrace();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				name = textField_2.getText();
			}
		});
		btnNewButton.setBounds(131, 222, 93, 34);
		contentPane.add(btnNewButton);
		
		JButton button = new JButton("\u91CD  \u7F6E");					//重置
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textField.setText("");
				textField_1.setText("");
				passwordField.setText("");
				textField_3.setText("");
			}
		});
		button.setBounds(254, 222, 93, 34);
		contentPane.add(button);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(225, 103, 120, 23);
		contentPane.add(passwordField);
		passwordField.setText("Anjianwei");
		
		JLabel label_4 = new JLabel(" \u6635  \u79F0  \uFF1A");
		label_4.setFont(new Font("微软雅黑", Font.BOLD, 12));
		label_4.setBounds(131, 168, 70, 23);
		contentPane.add(label_4);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(225, 168, 120, 22);
		textField_2.setText("Anla");
		contentPane.add(textField_2);
	}
	
	/**
	 * @throws SQLException 
	 * 
	 */
	public static void ftpLogin(String ipAddress, String User, String password, int portNum, Login login) throws SQLException {
		try {  
			login.ftpClient.connect(ipAddress, portNum);// 连接FTP服务器  
			login.ftpClient.login(User, password);// 登陆FTP服务器  
			if (!FTPReply.isPositiveCompletion(login.ftpClient.getReplyCode())) {  
				System.out.println("未连接到FTP，用户名或密码错误。");
				login.ftpClient.disconnect();  
			} else {
				System.out.println("FTP连接成功。");
				login.ftpClient.setControlEncoding("GBK");
				login.ipAddress = ipAddress;
				login.User = User;
				login.password = password;
				login.portNum = portNum;
				MainInterface mainUI = new MainInterface(login);
				mainUI.mainInterfaceRun();
				login.dispose();
			}  
		} catch (SocketException e) {  
			e.printStackTrace();  
			System.out.println("FTP的IP地址可能错误，请正确配置。");  
		} catch (IOException e) {  
			e.printStackTrace();  
			System.out.println("FTP的端口错误,请正确配置。");  
		}
	}
}


