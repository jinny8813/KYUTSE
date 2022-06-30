import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ExercisePaper {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "Nknu@123456";

	private static JFormattedTextField jformattedtextfield;
	private static JFrame frame;
	private static JPanel panel;
	
	private static JComboBox jComboBox_numbers;
	private static JComboBox jComboBox_degree;
	private static JComboBox jComboBox_subject;
	
	private static JLabel homelabel;
	private static JLabel showlabel;
	private static JLabel quslabel;
	private static JLabel anslabel;
	private static JLabel eglabel;
	private static JLabel scorelabel;
	private static JLabel correctlabel;
	
	private static JButton button_ans;
	private static JButton button_back;
	private static JButton button_click;

	private Object getsubject;
	private Object getdegree;
	private Object numsofques;

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
			sql = "INSERT INTO `runoob`.`" + username + "` (`ques`, `quesver`, `score`) VALUES ('" + textname
					+ "', '練習', '" + score + "')";
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

	public String setNewTestPaper(Object getsubject, Object numsofques, Object getdegree) {

		System.out.println(getsubject);
		System.out.println(getdegree);
		System.out.println(numsofques);

		String str = "<html><body>Num)難易度_Ques______A______B______C______D <br>";

		ExamMachine examMachine = new ExamMachine(getsubject, numsofques, getdegree);
		paper = examMachine.generatePaper(Integer.parseInt(numsofques.toString()));

		for (int i = 0; i < paper.size(); i++) {
			QuestionBank question = paper.get(i);

			question.setOrderNum(i + 1);// 设置题号
			str = str + question.getOrderNum() + ")" + question.getDegree() + "_"  + question.getTitle();
			System.out.println(question.getOrderNum() + ")"+ question.getTitle());

			String[] options = question.getOption();
			for (int j = 0; j < options.length; j++) {
				str = str + "______" + options[j];
				System.out.println("\t" + options[j]);
			}
			str = str + "<br>";
		}

		str = str + "</body></html>";

		System.out.println("Goodbye!");

		return str;
	}

	public ExercisePaper() {
		panel = new JPanel();
		panel.setBackground(SystemColor.WHITE);
		
		frame = new JFrame("ExercisePaper!");
		frame.setBounds(50, 50, 1080, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);

		panel.setLayout(null);

		homelabel = new JLabel("ExercisePaper");
		homelabel.setBounds(10, 20, 150, 25);
		panel.add(homelabel);

		String subject[] = { "國文", "數學", "英文" };
		jComboBox_subject = new JComboBox(subject);
		jComboBox_subject.setBounds(200, 20, 80, 25);
		panel.add(jComboBox_subject);

		String degree[] = { "全", "中", "難", "易" };
		jComboBox_degree = new JComboBox(degree);
		jComboBox_degree.setBounds(300, 20, 80, 25);
		panel.add(jComboBox_degree);

		String numbers[] = { "4", "5", "10", "20" };
		jComboBox_numbers = new JComboBox(numbers);
		jComboBox_numbers.setBounds(400, 20, 80, 25);
		panel.add(jComboBox_numbers);

		button_click = new JButton("確定");
		button_click.setBounds(500, 20, 80, 25);
		button_click.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getsubject = jComboBox_subject.getItemAt(jComboBox_subject.getSelectedIndex());
				getdegree = jComboBox_degree.getItemAt(jComboBox_degree.getSelectedIndex());
				numsofques = jComboBox_numbers.getItemAt(jComboBox_numbers.getSelectedIndex());
				String data = "科目: " + getsubject + ", 題數: " + numsofques + ", 難易度: " + getdegree;
				showlabel.setText(data);
				String str = setNewTestPaper(getsubject, numsofques, getdegree);
				quslabel.setText(str);
			}
		});
		panel.add(button_click);

		showlabel = new JLabel();
		showlabel.setBounds(10, 50, 300, 25);
		panel.add(showlabel);

		quslabel = new JLabel();
		quslabel.setBounds(10, 80, 600, 420);
		panel.add(quslabel);

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
				String[] answers = ans.split(",");
				ExamMachine ExamMachine = new ExamMachine();
				float score = ExamMachine.checkPaper(paper, answers);
				scorelabel.setText("總分: " + String.valueOf(score));
				correctlabel.setText("正確答案: " + ExamMachine.correctAns(paper));
				setReportCard(showlabel.getText(), score);
			}
		});
		panel.add(button_ans);

		scorelabel = new JLabel();
		scorelabel.setBounds(10, 570, 300, 25);
		panel.add(scorelabel);

		correctlabel = new JLabel();
		correctlabel.setBounds(10, 600, 300, 25);
		panel.add(correctlabel);

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
