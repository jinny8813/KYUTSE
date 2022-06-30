import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ItemBank {
	private static JFrame frame;
	private static JPanel panel;
	
	private static JLabel homelabel;
	private static JLabel showlabel;
	
	private static JComboBox jComboBox_subject;
	private static JComboBox jComboBox_degree;
	
	private static JButton button_back;
	private static JButton button_click;
	
	private static JScrollPane scrollable;
	
	private JButton button_delete = new JButton("刪除");
	private JButton button_add = new JButton("增加");
	
	private Object selection;
	private Object getsubject;

	// MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

	// 数据库的用户名与密码，需要根据自己的设置
	static final String USER = "root";
	static final String PASS = "Nknu@123456";

	public void setSelection(Object getsubject, Object selection) {
		this.getsubject = getsubject;
		this.selection = selection;
	}

	public ArrayList setItemBank(Object getsubject, Object selection) {

		System.out.print(getsubject);
		System.out.print(selection);

		Connection conn = null;
		Statement stmt = null;
		ArrayList alist = new ArrayList();
		alist.clear();
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "";
			if (getsubject.equals("國文")) {
				sql = "SELECT Degree, Question, A ,B ,C ,D ,Answer FROM Chinese";
			} else if (getsubject.equals("數學")) {
				sql = "SELECT Degree, Question, A ,B ,C ,D ,Answer FROM Maths";
			} else if (getsubject.equals("英文")) {
				sql = "SELECT Degree, Question, A ,B ,C ,D ,Answer FROM english";
			}
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				String Degree = rs.getString("Degree");
				String Question = rs.getString("Question");
				String A = rs.getString("A");
				String B = rs.getString("B");
				String C = rs.getString("C");
				String D = rs.getString("D");
				String Answer = rs.getString("Answer");

				if (selection.equals("全")) {
					alist.add(Degree);
					alist.add(Question);
					alist.add(A);
					alist.add(B);
					alist.add(C);
					alist.add(D);
					alist.add(Answer);
				} else if (selection.equals("難")) {
					if (Degree.equals("3")) {
						alist.add(Degree);
						alist.add(Question);
						alist.add(A);
						alist.add(B);
						alist.add(C);
						alist.add(D);
						alist.add(Answer);
					}
				} else if (selection.equals("中")) {
					if (Degree.equals("2")) {
						alist.add(Degree);
						alist.add(Question);
						alist.add(A);
						alist.add(B);
						alist.add(C);
						alist.add(D);
						alist.add(Answer);
					}
				} else if (selection.equals("易")) {
					if (Degree.equals("1")) {
						alist.add(Degree);
						alist.add(Question);
						alist.add(A);
						alist.add(B);
						alist.add(C);
						alist.add(D);
						alist.add(Answer);
					}
				}

				System.out.print("ok");

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

	public void setItemBank(int a, String[] b) {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "";
			if (a == 1) {
				if (b[6].equals("國文")) {
					sql = "INSERT INTO Chinese VALUES ('" + b[7] + "', '" + b[0] + "', '" + b[1] + "','" + b[2] + "', '"
							+ b[3] + "','" + b[4] + "','" + b[5] + "')";
				} else if (b[6].equals("數學")) {
					sql = "INSERT INTO Maths VALUES ('" + b[7] + "', '" + b[0] + "', '" + b[1] + "','" + b[2] + "', '"
							+ b[3] + "','" + b[4] + "','" + b[5] + "')";
				} else if (b[6].equals("英文")) {
					sql = "INSERT INTO english VALUES ('" + b[7] + "', '" + b[0] + "', '" + b[1] + "','" + b[2] + "', '"
							+ b[3] + "','" + b[4] + "','" + b[5] + "')";
				}
				stmt.executeUpdate(sql);
			} else {
				if (b[1].equals("國文")) {
					sql = "DELETE FROM Chinese " + "WHERE Question ='" + b[0] + "'";
				} else if (b[1].equals("數學")) {
					sql = "DELETE FROM Maths " + "WHERE Question ='" + b[0] + "'";
				} else if (b[1].equals("英文")) {
					sql = "DELETE FROM english " + "WHERE Question ='" + b[0] + "'";
				}
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

	public ItemBank(int a) {

	}

	public ItemBank() {
		panel = new JPanel();
		panel.setBackground(SystemColor.WHITE);
		
		frame = new JFrame("ItemBank!");
		frame.setBounds(50, 50, 1080, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);

		panel.setLayout(null);

		homelabel = new JLabel("ItemBank!");
		homelabel.setBounds(10, 20, 300, 25);
		panel.add(homelabel);

		String subject[] = { "國文", "數學", "英文" };
		jComboBox_subject = new JComboBox(subject);
		jComboBox_subject.setBounds(200, 20, 80, 25);
		panel.add(jComboBox_subject);

		String degree[] = { "中", "難", "易" };
		jComboBox_degree = new JComboBox(degree);
		jComboBox_degree.setBounds(300, 20, 80, 25);
		panel.add(jComboBox_degree);

		String[] columns = new String[] { "Degree", "Question", "A", "B", "C", "D", "Answer" };
		DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
		JTable table = new JTable(tableModel);

		button_click = new JButton("確定");
		button_click.setBounds(400, 20, 80, 25);
		button_click.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selection = jComboBox_degree.getItemAt(jComboBox_degree.getSelectedIndex());
				getsubject = jComboBox_subject.getItemAt(jComboBox_subject.getSelectedIndex());
				String data = "科目: " + getsubject + ", 難易度: " + selection;
				showlabel.setText(data);
				ArrayList alist = new ArrayList();
				tableModel.setRowCount(0);
				alist = setItemBank(getsubject, selection);
				int count = 0;
				for (int i = 0; i < alist.size(); i += 7) {
					Object[] objs = { alist.get(0 + count * 7), alist.get(1 + count * 7), alist.get(2 + count * 7),
							alist.get(3 + count * 7), alist.get(4 + count * 7), alist.get(5 + count * 7),
							alist.get(6 + count * 7) };
					tableModel.addRow(objs);
					count++;
				}
			}
		});
		panel.add(button_click);

		showlabel = new JLabel();
		showlabel.setBounds(10, 50, 300, 25);
		panel.add(showlabel);

		scrollable = new JScrollPane(table);
		scrollable.setBounds(10, 80, 1000, 420);
		panel.add(scrollable);

		button_add.setBounds(150, 540, 80, 25);
		button_add.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				AddQuestion ad = new AddQuestion();
			}
		});
		panel.add(button_add);

		button_delete.setBounds(250, 540, 80, 25);
		button_delete.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int i = table.getSelectedRow();
				if (i != -1) {
					int t = JOptionPane.showConfirmDialog(null, "你確認刪除嗎，刪除後不可恢復", null, JOptionPane.YES_NO_OPTION);
					switch (t) {
					case JOptionPane.YES_OPTION:
						String b[] = new String[2];
						b[0] = (String) tableModel.getValueAt(i, 1);
						b[1] = getsubject.toString();
						setItemBank(2, b);
					}
				}
			}
		});
		panel.add(button_delete);

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
}


