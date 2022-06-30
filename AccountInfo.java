import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;

public class AccountInfo {
	private static JFrame frame;
	private static JPanel panel;
	private static JLabel homelabel;
	private static JTable table;
	private static DefaultTableModel tableModel;

	//private JButton button_refresh = new JButton("刷新");
	private JButton button_delete = new JButton("刪除");
	private JButton button_add = new JButton("增加");
	private static JButton button_back;

	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

	static final String USER = "root";
	static final String PASS = "Nknu@123456";

	public ArrayList getAccountInfo() {
		Connection conn = null;
		Statement stmt = null;
		ArrayList alist = new ArrayList();
		alist.clear();
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "";
			sql = "SELECT account ,password,name,major FROM account";
			ResultSet rs = stmt.executeQuery(sql);
			int count = 0;
			while (rs.next()) {
				count++;
				alist.add(count);
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

	public void setAccountInfo(int a, String[] b) {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "";
			if (a == 1) {
				sql = "INSERT INTO account VALUES ('" + b[0] + "', '" + b[1] + "', '" + b[2] + "','" + b[3] + "')";
				stmt.executeUpdate(sql);
				sql = "CREATE TABLE " + b[0] + "(" + " ques VARCHAR(255), " + " quesver VARCHAR(255), " + " score INT, "
						+ " PRIMARY KEY (ques ))";
				stmt.executeUpdate(sql);
				sql = "INSERT INTO `runoob`.`pretextscore` (`student`) VALUES ('" + b[0] + "')";
				stmt.executeUpdate(sql);
			} else {
				sql = "DELETE FROM account " + "WHERE account ='" + b[0] + "'";
				stmt.executeUpdate(sql);
				sql = "DROP TABLE " + b[0];
				stmt.executeUpdate(sql);
				sql = "DELETE FROM pretextscore " + "WHERE student ='" + b[0] + "'";
				stmt.executeUpdate(sql);
			}
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

	public AccountInfo(int a) {

	}

	public AccountInfo() {
		panel = new JPanel();
		panel.setBackground(SystemColor.WHITE);
		
		frame = new JFrame("AccountInfo!");
		frame.setBounds(50, 50, 1080, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);

		panel.setLayout(null);

		homelabel = new JLabel("AccountInfo");
		homelabel.setBounds(10, 20, 150, 25);
		panel.add(homelabel);

		flush();
		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// table.getColumnModel().getColumn(0).setPreferredWidth(80);
		// table.setShowGrid(false);
		// table.getTableHeader().setResizingAllowed(true);

		button_add.setBounds(150, 20, 80, 25);
		button_add.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				AddAccount ad = new AddAccount();
				flush();
			}
		});
		panel.add(button_add);

		button_delete.setBounds(250, 20, 80, 25);
		button_delete.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int i = table.getSelectedRow();
				if (i != -1) {
					int t = JOptionPane.showConfirmDialog(null, "你確認刪除嗎，刪除後不可恢復", null, JOptionPane.YES_NO_OPTION);
					switch (t) {
					case JOptionPane.YES_OPTION:
						String b[] = new String[1];
						b[0] = (String) tableModel.getValueAt(i, 1);
						System.out.println(b[0]);
						setAccountInfo(2, b);
					}
				}
				flush();
			}
		});
		panel.add(button_delete);

//		button.setBounds(350, 20, 80, 25);
//		button.addMouseListener(new MouseAdapter() {
//			public void mouseClicked(MouseEvent e) {
//				flush();
//			}
//		});
//		panel.add(button);

		button_back = new JButton("返回上一頁");
		button_back.setBounds(890, 20, 120, 25);
		button_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		panel.add(button_back);

		frame.setVisible(true);
	}

	public void flush() {
		String[] columns = new String[] { "count", "account", "password", "name", "major" };
		tableModel = new DefaultTableModel(columns, 0);
		table = new JTable(tableModel);

		tableModel.setRowCount(0);
		ArrayList alist = new ArrayList();
		alist = getAccountInfo();
		int num = columns.length;
		int count = 0;
		for (int i = 0; i < alist.size(); i += num) {
			Object[] objs = new Object[num];
			for (int j = 0; j < objs.length; j++) {
				objs[j] = alist.get(j + count * num);
			}
			tableModel.addRow(objs);
			count++;
		}
		table.setModel(tableModel);

		table = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setModel(tableModel);

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);

		JScrollPane scrollable = new JScrollPane(table);
		scrollable.setBounds(10, 80, 1000, 420);
		panel.add(scrollable);
	}
}

