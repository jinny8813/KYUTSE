import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;

import java.util.*;

public class PreTextPaper {
	private static JFrame frame;
	private static JPanel panel;
	
	private static JLabel label;
	private static JLabel eglabel;
	private static JLabel anslabel;
	private static JLabel showlabel;
	private static JLabel homelabel;
	private static JLabel alertlabel;
	private static JLabel scorelabel;
	private static JLabel correctlabel;

	private static JButton button_ans;
	private static JButton button_back;
	private static JButton button_click;
	
	private static JComboBox jComboBox_textname;

	private static JFormattedTextField jformattedtextfield;
	
	private Object getsubject;
	private Object getdegree;
	private Object numsofques;

	private int delay = 1000;
	private int sec = 01;
	private static JLabel jl = new JLabel();
	private int min = 40;
	private int num = 0;
	private boolean timeout = false;
	private Timer timer = new Timer(delay, new TimerListener());

	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

	static final String USER = "root";
	static final String PASS = "Nknu@123456";

	ArrayList<QuestionBank> paper;

	public void setReportCard(String textname, float score) {
		Connection conn = null;
		Statement stmt = null;
		ArrayList report = new ArrayList();
		Login login = new Login(2);
		String username = login.getUser();
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "";
			sql = "UPDATE questionbank SET " + username + " = " + score + " WHERE ques in ('" + textname + "')";
			stmt.executeUpdate(sql);
			sql = "UPDATE pretextscore SET " + textname + " = " + score + " WHERE student in ('" + username + "')";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO `runoob`.`" + username + "` (`ques`, `quesver`, `score`) VALUES ('" + textname
					+ "', '考試', '" + score + "')";
			// INSERT INTO `runoob`.`s77001` (`ques`, `quesver`, `score`) VALUES ('bbb',
			// 'aaa', '100');
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

	public ArrayList preTextPaper(String textname) {
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

	public PreTextPaper() {
		panel = new JPanel();
		panel.setBackground(SystemColor.WHITE);
		
		frame = new JFrame("PreTestPaper!");
		frame.setBounds(50, 50, 1080, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);

		panel.setLayout(null);

		homelabel = new JLabel("TestPaper");
		homelabel.setBounds(10, 20, 150, 25);
		homelabel.setFont(new java.awt.Font("",1,20));
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

		jl.setBounds(600, 20, 60, 20);
		panel.add(jl);

		button_click = new JButton("開始作答");
		button_click.setBounds(400, 20, 120, 25);
		button_click.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pretextname = jComboBox_textname.getItemAt(jComboBox_textname.getSelectedIndex()).toString();
				ArrayList mylist = preTextPaper(pretextname);
				String str[] = mylist.get(0).toString().split(",");
				String finalstr = "<html><body>Num)Degree_Ques______A______B______C______D <br>";
				for (int i = 0; i < str.length - 6; i += 2) {
					finalstr = finalstr + str[i] + ")" + str[1 + i] + "<br>";
					num += 1;
				}
				finalstr = finalstr + "</body></html>";
				String data = "科目: " + str[str.length - 5] + ", 題數: " + str[str.length - 3] + ", 難易度: "
						+ str[str.length - 1];
				showlabel.setText(data);
				label.setText(finalstr);
				timer.start();
			}
		});
		panel.add(button_click);
		
		alertlabel = new JLabel("限時40分鐘!時間到即結算成績!");
		alertlabel.setBounds(650, 20, 250, 25);
		alertlabel.setForeground(Color.red);
		alertlabel.setFont(new java.awt.Font("",1,15));
		panel.add(alertlabel);

		button_back = new JButton("返回上一頁");
		button_back.setBounds(890, 20, 120, 25);
		button_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		panel.add(button_back);

		showlabel = new JLabel();
		showlabel.setBounds(10, 50, 300, 25);
		panel.add(showlabel);

		label = new JLabel();
		label.setBounds(10, 80, 1000, 420);
		panel.add(label);

		JScrollPane scrollable = new JScrollPane(label);
		scrollable.setBounds(10, 80, 1000, 420);
		panel.add(scrollable);

		anslabel = new JLabel("Answer area");
		anslabel.setBounds(10, 510, 80, 25);
		panel.add(anslabel);

		jformattedtextfield = new JFormattedTextField();
		jformattedtextfield.setBounds(100, 510, 420, 25);
		panel.add(jformattedtextfield);

		eglabel = new JLabel("用逗號隔開 e.g: A,A,A,A,A");
		eglabel.setBounds(10, 540, 200, 25);
		panel.add(eglabel);

		button_ans = new JButton("送出答案");
		button_ans.setBounds(550, 510, 120, 25);
		button_ans.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String ans = jformattedtextfield.getText();
				System.out.println(ans.length());
				num += num - 1;
				int x = ans.length();
				System.out.println(num);
				if (ans.length() < num) {
					for (int i = x; i < num; i++) {
						ans += ",";
						ans += "@";
					}
				}

				String[] answers = ans.split(",");
				
				String pretextname = jComboBox_textname.getItemAt(jComboBox_textname.getSelectedIndex()).toString();
				ArrayList mylist = preTextPaper(pretextname);
				String strccc[] = mylist.get(1).toString().split(",");
				String ansccc[] = new String[strccc.length - 3];
				String ccc = "";
				for (int i = 0; i < strccc.length - 3; i++) {
					ansccc[i] = strccc[i].substring(0, 1);
					ccc = ccc + ansccc[i];
				}
				
				ExamMachine ExamMachine = new ExamMachine();
				float score = ExamMachine.preCheck(ansccc, answers);
				setReportCard(pretextname, score);
				scorelabel.setText("總分: " + String.valueOf(score));
				correctlabel.setText("正確答案: " + ccc);
			}

		});
		panel.add(button_ans);

		scorelabel = new JLabel();
		scorelabel.setBounds(10, 570, 300, 25);
		panel.add(scorelabel);

		correctlabel = new JLabel();
		correctlabel.setBounds(10, 600, 300, 25);
		panel.add(correctlabel);

		frame.setVisible(true);
	}

	private class TimerListener implements ActionListener { 

		public void actionPerformed(ActionEvent e) {

			sec--;
			if (sec == 0 && min == 0) {
				timer.stop();
				timeout = true;
				String ans = jformattedtextfield.getText();
				System.out.println(ans.length());
				num += num - 1;
				int x = ans.length();
				System.out.println(num);
				if (ans.length() < num) {
					for (int i = x; i < num; i++) {
						ans += ",";
						ans += "@";
					}
				}

				String[] answers = ans.split(",");

				String pretextname = jComboBox_textname.getItemAt(jComboBox_textname.getSelectedIndex()).toString();
				ArrayList mylist = preTextPaper(pretextname);
				String strccc[] = mylist.get(1).toString().split(",");
				String ansccc[] = new String[strccc.length - 3];
				String ccc = "";
				for (int i = 0; i < strccc.length - 3; i++) {
					ansccc[i] = strccc[i].substring(0, 1);
					ccc = ccc + ansccc[i];
				}
				ExamMachine ExamMachine = new ExamMachine();
				float score = ExamMachine.preCheck(ansccc, answers);
				setReportCard(pretextname, score);
				scorelabel.setText("總分: " + String.valueOf(score));
				correctlabel.setText("正確答案: " + ccc);
				timer.stop();
			} else if (sec == 0) {
				min -= 1;
				sec = 59;
			}
			jl.setText("時間" + min + ":" + sec);
			jl.setFont(new java.awt.Font("",1,15));
			jl.setBounds(550, 20, 80, 25);
			jl.repaint();

		}

	}

}
