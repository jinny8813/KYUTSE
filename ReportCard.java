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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ReportCard {
	private static JFrame frame;
	private static JPanel panel;
	
	private static JLabel homelabel;
	
	private static JTable table;
	
	private static JButton button_back;
	
	private static JScrollPane scrollable;
	
	private static DefaultTableModel tableModel;
	

	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

	static final String USER = "root";
	static final String PASS = "Nknu@123456";

	public ArrayList getReportCard(String str) {
		Connection conn = null;
		Statement stmt = null;
		ArrayList report = new ArrayList();
		String[] strs = str.split(",");
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "";

			sql = "SELECT " + str + " FROM pretextscore";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				for (int i = 0; i < strs.length; i++) {
					report.add(rs.getString(strs[i]));
				}
				System.out.println("ok");
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
		for (int i = 0; i < report.size(); i++) {
			System.out.print(report.get(i));
			if (i % 8 == 0)
				System.out.println();
		}

		System.out.println("Goodbye!");

		return report;
	}

	public ArrayList<String> getQuesName() {
		Connection conn = null;
		Statement stmt = null;
		ArrayList<String> str = new ArrayList<String>();
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "";
			sql = "SELECT ques FROM questionbank";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String ques = rs.getString("ques");
				str.add(ques);
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
		return str;
	}

	public ReportCard() {
		panel = new JPanel();
		panel.setBackground(SystemColor.WHITE);
		
		frame = new JFrame("ReportCard!");
		frame.setBounds(50, 50, 1080, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);

		panel.setLayout(null);

		homelabel = new JLabel("ReportCard");
		homelabel.setBounds(10, 20, 150, 25);
		panel.add(homelabel);

		ArrayList qname = new ArrayList();
		qname = getQuesName();
		String[] columns = new String[qname.size() + 1];
		columns[0] = "student";
		for (int i = 1; i < qname.size() + 1; i++) {
			columns[i] = qname.get(i - 1).toString();
		}
		
		tableModel = new DefaultTableModel(columns, 0);
		
		table = new JTable(tableModel);
		String str = "";
		for (int i = 0; i < columns.length; i++) {
			str = str + columns[i];
			if (i != columns.length - 1) {
				str = str + ",";
			}
		}
		System.out.println(str);
		
		ArrayList report = new ArrayList();
		report = getReportCard(str);
		int num = columns.length;
		int count = 0;
		for (int i = 0; i < report.size(); i += num) {
			Object[] objs = new Object[num];
			for (int j = 0; j < objs.length; j++) {
				objs[j] = report.get(j + count * num);
			}
			tableModel.addRow(objs);
			count++;
		}

		scrollable = new JScrollPane(table);
		scrollable.setBounds(10, 80, 1000, 420);
		panel.add(scrollable);

		button_back = new JButton("返回上一頁");
		button_back.setBounds(860, 20, 150, 25);
		button_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		panel.add(button_back);

		frame.setVisible(true);
	}
}
