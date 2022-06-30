import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PersonalInfo {
	private static JFrame frame;
	private static JPanel panel;

	private static JLabel passlabel;
	private static JLabel namelabel;
	private static JLabel majorlabel;
	private static JLabel homelabel;
	private static JLabel label, label1, label2, label3;

	private static JFormattedTextField jformattedtextfield, mjformattedtextfield, njformattedtextfield;

	private static JButton button_click;
	private static JButton button_back;

	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

	static final String USER = "root";
	static final String PASS = "Nknu@123456";

	public void setMajor(String str) {
		Connection conn = null;
		Statement stmt = null;
		Login login = new Login(2);
		String user = login.getUser();
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "";
			sql = "UPDATE account " + "SET major = '" + str + "' WHERE account = '" + user + "' ";
			stmt.executeUpdate(sql);
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
		System.out.println("Goodbye!");
	}

	public void setName(String str) {
		Connection conn = null;
		Statement stmt = null;
		Login login = new Login(2);
		String user = login.getUser();
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "";
			sql = "UPDATE account " + "SET name = '" + str + "' WHERE account = '" + user + "' ";
			stmt.executeUpdate(sql);
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
		System.out.println("Goodbye!");
	}

	public void setPassword(String str) {
		Connection conn = null;
		Statement stmt = null;
		Login login = new Login(2);
		String user = login.getUser();
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "";
			sql = "UPDATE account " + "SET password = '" + str + "' WHERE account = '" + user + "' ";
			stmt.executeUpdate(sql);
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
		System.out.println("Goodbye!");
	}

	public ArrayList getPersonalInfo() {
		Connection conn = null;
		Statement stmt = null;
		Login login = new Login(2);
		String user = login.getUser();
		ArrayList alist = new ArrayList();
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "";
			sql = "SELECT account ,password,name,major FROM account WHERE account = '" + user + "' ";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				alist.add(rs.getString("account"));
				alist.add(rs.getString("password"));
				alist.add(rs.getString("name"));
				alist.add(rs.getString("major"));
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
		System.out.println("Goodbye!");

		return alist;
	}

	public PersonalInfo() {
		panel = new JPanel();
		panel.setBackground(SystemColor.WHITE);

		frame = new JFrame("AccountInfo!");
		frame.setBounds(50, 50, 720, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);

		panel.setLayout(null);

		homelabel = new JLabel("AccountInfo");
		homelabel.setBounds(10, 20, 150, 25);
		panel.add(homelabel);

		ArrayList alist = new ArrayList();
		alist = getPersonalInfo();

		label = new JLabel("帳號: " + alist.get(0).toString());
		label.setBounds(10, 50, 150, 25);
		panel.add(label);

		passlabel = new JLabel("密碼: " + alist.get(1).toString());
		passlabel.setBounds(10, 80, 150, 25);
		panel.add(passlabel);

		label = new JLabel("修改密碼");
		label.setBounds(200, 80, 70, 25);
		panel.add(label);
		jformattedtextfield = new JFormattedTextField();
		jformattedtextfield.setBounds(270, 80, 150, 25);
		panel.add(jformattedtextfield);
		button_click = new JButton("確定");
		button_click.setBounds(450, 80, 80, 25);
		button_click.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String password = jformattedtextfield.getText();
				setPassword(password);
				label1.setText("Update!");
				passlabel.setText("密碼: " + password);
			}
		});
		panel.add(button_click);
		label1 = new JLabel();
		label1.setBounds(540, 80, 80, 25);
		panel.add(label1);

		namelabel = new JLabel("姓名: " + (String) alist.get(2));
		namelabel.setBounds(10, 110, 150, 25);
		panel.add(namelabel);

		label = new JLabel("修改姓名");
		label.setBounds(200, 110, 70, 25);
		panel.add(label);
		njformattedtextfield = new JFormattedTextField();
		njformattedtextfield.setBounds(270, 110, 150, 25);
		panel.add(njformattedtextfield);
		button_click = new JButton("確定");
		button_click.setBounds(450, 110, 80, 25);
		button_click.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = njformattedtextfield.getText();
				setName(name);
				label2.setText("Update!");
				namelabel.setText("密碼: " + name);
			}
		});
		panel.add(button_click);
		label2 = new JLabel();
		label2.setBounds(540, 110, 80, 25);
		panel.add(label2);

		majorlabel = new JLabel("主修: " + (String) alist.get(3));
		majorlabel.setBounds(10, 140, 150, 25);
		panel.add(majorlabel);

		label = new JLabel("修改主修");
		label.setBounds(200, 140, 70, 25);
		panel.add(label);
		mjformattedtextfield = new JFormattedTextField();
		mjformattedtextfield.setBounds(270, 140, 150, 25);
		panel.add(mjformattedtextfield);
		button_click = new JButton("確定");
		button_click.setBounds(450, 140, 80, 25);
		button_click.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String major = mjformattedtextfield.getText();
				setMajor(major);
				label3.setText("Update!");
				majorlabel.setText("密碼: " + major);
			}
		});
		panel.add(button_click);
		label3 = new JLabel();
		label3.setBounds(540, 140, 80, 25);
		panel.add(label3);

		button_back = new JButton("返回上一頁");
		button_back.setBounds(570, 20, 120, 25);
		button_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		panel.add(button_back);

		frame.setVisible(true);
	}
}
