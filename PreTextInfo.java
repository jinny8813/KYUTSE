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
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PreTextInfo {
	private static JFrame frame;
	private static JPanel panel;
	
	private static JComboBox jComboBox_textname;
	
	private static JScrollPane scrollable;
	
	private static JButton button_back;
	private static JButton button_click;
	
	private static JLabel homelabel;
	private static JLabel showlabel;
	private static JLabel label;

	private Object getsubject;
	private Object getdegree;
	private Object numsofques;

	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

	static final String USER = "root";
	static final String PASS = "Nknu@123456";

	ArrayList<QuestionBank> paper;

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

	public ArrayList preTextInfo(String textname) {
		Connection conn = null;
		Statement stmt = null;
		String str = "";
		String ansccc = "";
		ArrayList strlist = new ArrayList();
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "";
			sql = "SELECT num,ques,ans FROM " + textname;
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int num = rs.getInt("num");
				String ques = rs.getString("ques");
				String ans = rs.getString("ans");
				str = str + num + "," + ques + ",";
				ansccc = ansccc + ans + ",";
				System.out.print("ok");
			}
			strlist.add(str);
			strlist.add(ansccc);
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
		return strlist;
	}

	public PreTextInfo() {
		panel = new JPanel();
		panel.setBackground(SystemColor.WHITE);
		
		frame = new JFrame("PreTextInfo!");
		frame.setBounds(50, 50, 1080, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);

		panel.setLayout(null);

		homelabel = new JLabel("TestPaper");
		homelabel.setBounds(10, 20, 120, 25);
		panel.add(homelabel);

		ArrayList strlist = new ArrayList();
		strlist = getQuesName();
		String textname[] = new String[strlist.size()];
		for (int i = 0; i < textname.length; i++) {
			textname[i] = strlist.get(i).toString();
		}

		jComboBox_textname = new JComboBox(textname);
		jComboBox_textname.setBounds(160, 20, 200, 25);
		panel.add(jComboBox_textname);

		button_click = new JButton("確定");
		button_click.setBounds(400, 20, 120, 25);
		button_click.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pretextname = jComboBox_textname.getItemAt(jComboBox_textname.getSelectedIndex()).toString();
				ArrayList mylist = preTextInfo(pretextname);
				String str[] = mylist.get(0).toString().split(",");
				String finalstr = "<html><body>Num)Degree_Ques______A______B______C______D <br>";
				for (int i = 0; i < str.length - 6; i += 2) {
					finalstr = finalstr + str[i] + ")" + str[1 + i] + "<br>";
				}
				finalstr = finalstr + "</body></html>";
				String data = "科目: " + str[str.length - 5] + ", 題數: " + str[str.length - 3] + ", 難易度: "
						+ str[str.length - 1];
				showlabel.setText(data);
				label.setText(finalstr);
			}
		});
		panel.add(button_click);

		showlabel = new JLabel();
		showlabel.setBounds(10, 50, 300, 25);
		panel.add(showlabel);

		label = new JLabel();
		label.setBounds(10, 80, 1000, 420);
		panel.add(label);

		scrollable = new JScrollPane(label);
		scrollable.setBounds(10, 80, 1000, 540);
		panel.add(scrollable);

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
