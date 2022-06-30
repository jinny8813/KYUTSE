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

public class PersonalScore {
	private static JFrame frame;
	private static JPanel panel;
	
	private static JLabel homelabel;
	private static JLabel label;
	
	private static JButton Button_back;
	
	private static JTable tableT;
	private static JTable tableE;
	
	private static DefaultTableModel tableModelT;
	private static DefaultTableModel tableModelE;
	
	private static JScrollPane scrollableT;
	private static JScrollPane scrollableE;

	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

	static final String USER = "root";
	static final String PASS = "Nknu@123456";

	public ArrayList getPersonalScore() {
		Connection conn = null;
		Statement stmt = null;
		Login login = new Login(2);
		String acc = login.getUser();
		ArrayList list = new ArrayList();
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "";
			sql = "SELECT ques,quesver,score FROM " + acc;
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				list.add(rs.getString("ques"));
				list.add(rs.getString("quesver"));
				list.add(rs.getInt("score"));

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
		System.out.println("Goodbye!");

		return list;
	}

	public PersonalScore() {
		panel = new JPanel();
		panel.setBackground(SystemColor.WHITE);
		
		frame = new JFrame("PersonalScore!");
		frame.setBounds(50, 50, 1080, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);

		panel.setLayout(null);

		homelabel = new JLabel("PersonalScore");
		homelabel.setBounds(10, 20, 150, 25);
		panel.add(homelabel);

		ArrayList list = new ArrayList();
		list = getPersonalScore();

		String[] columnsT = new String[] { "question", "score" };
		
		tableModelT = new DefaultTableModel(columnsT, 0);
		tableT = new JTable(tableModelT);
		
		String[] columnsE = new String[] { "question", "score" };
		tableModelE = new DefaultTableModel(columnsE, 0);
		tableE = new JTable(tableModelE);

		int count = 0;
		for (int i = 0; i < list.size(); i += 3) {
			if (list.get(i + 1).equals("考試")) {
				Object[] objT = { list.get(0 + count * 3), list.get(2 + count * 3) };
				tableModelT.addRow(objT);
			} else if (list.get(i + 1).equals("練習")) {
				Object[] objE = { list.get(0 + count * 3), list.get(2 + count * 3) };
				tableModelE.addRow(objE);
			}
			count++;
		}

		label = new JLabel("考試成績");
		label.setBounds(10, 50, 150, 25);
		panel.add(label);
		
		scrollableT = new JScrollPane(tableT);
		scrollableT.setBounds(10, 80, 360, 180);
		panel.add(scrollableT);

		label = new JLabel("練習成績");
		label.setBounds(10, 270, 150, 25);
		panel.add(label);
		
		scrollableE = new JScrollPane(tableE);
		scrollableE.setBounds(10, 300, 360, 180);
		panel.add(scrollableE);

		Button_back = new JButton("返回上一頁");
		Button_back.setBounds(900, 20, 120, 25);
		Button_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		panel.add(Button_back);

		frame.setVisible(true);
	}
}
