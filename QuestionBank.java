import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

public class QuestionBank {

	private static JFrame frame;
	private static JPanel panel;
	
	private static JScrollPane scrollable;
	
	private static JComboBox jComboBox_subject;
	private static JComboBox jComboBox_degree;
	
	private static JButton button_confirm;
	
	private static JLabel showlabel;
	private static JLabel homelabel;
	private static JLabel label;

	private Object selection;
	private Object getsubject;
	private static Set<QuestionBank> questionRepository = new HashSet<>();// 所有題庫

	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

	static final String USER = "root";
	static final String PASS = "Nknu@123456";

	private Integer orderNum;// 題號
	private String degree;
	private String title;// 題幹
	private String[] option;// 選項
	private String answer;// 答案

	public QuestionBank(String degree, String title, String[] option, String answer) {
		this.degree = degree;
		this.title = title;
		this.option = option;
		this.answer = answer;
	}

	@Override
	public int hashCode() {
		// 返回题干字符串的 hashcode
		return this.title.hashCode();
	}

	public void setOption(String[] str) {
		this.option = str;
	}

	public String[] getOption() {
		return this.option;
	}

	public void setAnswer(String str) {
		this.answer = str;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setOrderNum(int num) {
		this.orderNum = num;
	}

	public int getOrderNum() {
		return this.orderNum;
	}

	public String getDegree() {
		return this.degree;
	}

	public String getTitle() {
		return this.title;
	}

	public String setQuestionBank(Object getsubject, Object selection) {

		System.out.print(getsubject);
		System.out.print(selection);

		Connection conn = null;
		Statement stmt = null;
		questionRepository.removeAll(questionRepository);
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
					questionRepository.add(new QuestionBank(Degree, Question, new String[] { A, B, C, D }, Answer));
				} else if (selection.equals("難")) {
					if (Degree.equals("3"))
						questionRepository.add(new QuestionBank(Degree, Question, new String[] { A, B, C, D }, Answer));
				} else if (selection.equals("中")) {
					if (Degree.equals("2"))
						questionRepository.add(new QuestionBank(Degree, Question, new String[] { A, B, C, D }, Answer));
				} else if (selection.equals("易")) {
					if (Degree.equals("1"))
						questionRepository.add(new QuestionBank(Degree, Question, new String[] { A, B, C, D }, Answer));
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

		String str = "<html><body>Degree_Ques______A______B______C______D<br>";

		ArrayList<QuestionBank> paper = new ArrayList<>(this.questionRepository);

		for (int i = 0; i < paper.size(); i++) {
			QuestionBank question = paper.get(i);
			str = str + question.getDegree() + "_" + question.getTitle();

			String[] options = question.getOption();
			for (int j = 0; j < options.length; j++) {
				str = str + "______" + options[j];
			}
			str = str + "<br>";
		}

		str = str + "</body></html>";

		System.out.println("Goodbye!");

		return str;

	}

	public QuestionBank() {

		panel = new JPanel();
		panel.setBackground(SystemColor.WHITE);
		
		frame = new JFrame("QuestionBank!");
		frame.setBounds(50, 50, 1080, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);

		panel.setLayout(null);

		homelabel = new JLabel("QuestionBank");
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

		button_confirm = new JButton("確定");
		button_confirm.setBounds(400, 20, 80, 25);
		button_confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selection = jComboBox_degree.getItemAt(jComboBox_degree.getSelectedIndex());
				getsubject = jComboBox_subject.getItemAt(jComboBox_subject.getSelectedIndex());
				String data = "科目: " + getsubject + ", 難易度: " + selection;
				showlabel.setText(data);
				String str = setQuestionBank(getsubject, selection);
				label.setText(str);
			}
		});
		panel.add(button_confirm);

		showlabel = new JLabel();
		showlabel.setBounds(10, 50, 300, 25);
		panel.add(showlabel);

		label = new JLabel();
		label.setBounds(10, 80, 1000, 540);
		panel.add(label);

		scrollable = new JScrollPane(label);
		scrollable.setBounds(10, 80, 1000, 540);
		panel.add(scrollable);

		frame.setVisible(true);

	}
}
