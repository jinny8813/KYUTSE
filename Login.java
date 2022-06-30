import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.*;

public class Login {
	private static JFrame frame;
	private static JPanel panel;

	private static JLabel passwordlabel;
	private static JLabel userlabel;
	private static JLabel piclabel;
	private static JLabel successlabel;
	
	private static JButton button_login;
	
	private static JTextField userText;

	private static JPasswordField passwordText;

	static String user;
	static String password;

	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

	static final String USER = "root";
	static final String PASS = "Nknu@123456";

	public String checkUser(String user, String inpassword) {
		String str = "";
		String temp[] = {};

		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "";
			sql = "SELECT account ,password FROM account";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String account = rs.getString("account");
				int password = rs.getInt("password");

				System.out.println(account);
				System.out.println(password);
				
				if (account.equals(user) && String.valueOf(password).equals(inpassword)) {
					temp = account.split("");
					str = temp[0];
					System.out.print(str+ " ");
					break;
				}
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}

		if (str.isEmpty()) {
			str = "n";
		}
		System.out.println("Goodbye!");

		return str;
	}

	public Login(String user, String password) {
		this.user = user;
		this.password = password;
	}

	public Login(int num) {

	}

	public String getUser() {
		return this.user;
	}

	public String getPassword() {
		return this.password;
	}

	public Login() {
		panel = new JPanel();
		panel.setBackground(SystemColor.WHITE);
		
		frame = new JFrame("Login!");
		frame.setBounds(50, 50, 500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);

		panel.setLayout(null);

		userlabel = new JLabel("User");
		userlabel.setBounds(20, 220, 80, 25);
		userlabel.setFont(new java.awt.Font("",1,25));
		panel.add(userlabel);

		userText = new JTextField();
		userText.setBounds(200, 220, 165, 25);
		panel.add(userText);

		passwordlabel = new JLabel("Password");
		passwordlabel.setBounds(20, 260, 140, 25);
		passwordlabel.setFont(new java.awt.Font("",1,25));
		panel.add(passwordlabel);

		passwordText = new JPasswordField();
		passwordText.setBounds(200, 260, 165, 25);
		panel.add(passwordText);

		button_login = new JButton("Login");
		button_login.setBounds(25, 310, 90, 45);
		button_login.setFont(new java.awt.Font("",1,20));
		button_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				user = userText.getText();
				password = passwordText.getText();
				Login login = new Login(user, password);
				String userType = login.checkUser(user, password);
				if (userType.equals("r")) {
					Supervisor_Home supervisor = new Supervisor_Home();
					frame.dispose();
				} else if (userType.equals("t")) {
					Teacher_Home teacher = new Teacher_Home();
					frame.dispose();
				} else if (userType.equals("s")) {
					Student_Home student = new Student_Home();
					frame.dispose();
				} else {
					successlabel.setText("Login failed");
				}
			}
		});
		panel.add(button_login);
		
		piclabel = new JLabel(new ImageIcon("D:\\上課報告區\\軟體工程\\picture_1_1.jpg"));
		piclabel.setBounds(-10, 0, 500, 200);
		panel.add(piclabel);

		successlabel = new JLabel("");
		successlabel.setBounds(10, 110, 300, 25);
		panel.add(successlabel);

		frame.setVisible(true);

	}
}
