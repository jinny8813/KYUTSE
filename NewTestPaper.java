import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import java.util.*;

public class NewTestPaper {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "Nknu@123456";

	private static JFrame frame;
	private static JPanel panel;
	
	private static JComboBox jComboBox_numbers;
	private static JComboBox jComboBox_degree;
	private static JComboBox jComboBox_subject;
	
	private static JLabel label;
	private static JLabel homelabel;
	private static JLabel showlabel;
	private static JLabel queslabel;
	
	private static JButton button_back;
	private static JButton button_click;
	
	private static JScrollPane scrollable;
	
	private static JFormattedTextField jformattedtextfield;
	private static JFormattedTextField qjformattedtextfield;
	
	private Object getsubject;
	private Object getdegree;
	private Object numsofques;
	private Object quesname;

	ArrayList<QuestionBank> paper;

	public void setMySQLQBank() {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();

			String sql = "INSERT INTO questionbank " + "VALUES ('" + quesname.toString()
					+ "',null,null,null,null,null,null)";
			stmt.executeUpdate(sql);
			sql = "ALTER TABLE pretextscore ADD COLUMN " + quesname.toString() + " INT";
			stmt.executeUpdate(sql);

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");

	}

	public void setMySQL() {
		Connection conn = null;
		Statement stmt = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();

			String qname = quesname.toString();
			String sql = "CREATE TABLE " + qname + "(num INTEGER not NULL, " + " ques VARCHAR(255), "
					+ " ans VARCHAR(255), " + " PRIMARY KEY (num ))";

			stmt.executeUpdate(sql);

			ExamMachine examMachine = new ExamMachine(getsubject, numsofques, getdegree);
			paper = examMachine.generatePaper(Integer.parseInt(numsofques.toString()));

			for (int i = 0; i < paper.size(); i++) {
				QuestionBank question = paper.get(i);

				question.setOrderNum(i + 1);
				int num = question.getOrderNum();

				String str = question.getTitle();	//question.getDegree() + "_" + 
				System.out.println(str);
				String[] options = question.getOption();
				for (int j = 0; j < options.length; j++) {
					str = str + "______" + options[j];
				}
				String str2 = question.getAnswer();
				System.out.println(str2);
				sql = "INSERT INTO " + qname + " VALUES (" + num + ", '" + str + "','" + str2 + "')";
				stmt.executeUpdate(sql);
			}
			int a = Integer.parseInt(numsofques.toString());
			sql = "INSERT INTO " + qname + " VALUES (" + (a + 1) + ", '" + getsubject.toString() + "','100')";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO " + qname + " VALUES (" + (a + 2) + ", '" + numsofques.toString() + "','100')";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO " + qname + " VALUES (" + (a + 3) + ", '" + getdegree.toString() + "','100')";
			stmt.executeUpdate(sql);
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
	}

	public String setNewTestPaper(Object quesname, Object getsubject, Object numsofques, Object getdegree) {

		System.out.println(quesname);
		System.out.println(getsubject);
		System.out.println(getdegree);
		System.out.println(numsofques);

		String str = "<html><body>Num)Ques______A______B______C______D <br>";

		ExamMachine examMachine = new ExamMachine(getsubject, numsofques, getdegree);
		paper = examMachine.generatePaper(Integer.parseInt(numsofques.toString()));

		for (int i = 0; i < paper.size(); i++) {
			QuestionBank question = paper.get(i);

			question.setOrderNum(i + 1);// 设置题号
			str = str + question.getOrderNum() + ")" + question.getTitle();
			System.out.println(question.getOrderNum() + ")" + question.getTitle());

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

	public NewTestPaper() {
		panel = new JPanel();
		panel.setBackground(SystemColor.WHITE);
		
		frame = new JFrame("NewTestPaper!");
		frame.setBounds(50, 50, 1080, 720);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);

		panel.setLayout(null);

		homelabel = new JLabel("NewTestPaper");
		homelabel.setBounds(10, 20, 150, 25);
		panel.add(homelabel);

		String subject[] = { "國文", "數學", "英文" };
		jComboBox_subject = new JComboBox(subject);
		jComboBox_subject.setBounds(160, 20, 80, 25);
		panel.add(jComboBox_subject);

		String degree[] = { "全", "中", "難", "易" };
		jComboBox_degree = new JComboBox(degree);
		jComboBox_degree.setBounds(260, 20, 80, 25);
		panel.add(jComboBox_degree);

		String numbers[] = { "4", "5", "10", "20" };
		jComboBox_numbers = new JComboBox(numbers);
		jComboBox_numbers.setBounds(360, 20, 80, 25);
		panel.add(jComboBox_numbers);

		queslabel = new JLabel("請輸入題目名稱");
		queslabel.setBounds(460, 20, 100, 25);
		panel.add(queslabel);

		qjformattedtextfield = new JFormattedTextField();
		qjformattedtextfield.setBounds(560, 20, 200, 25);
		panel.add(qjformattedtextfield);

		button_click = new JButton("確定");
		button_click.setBounds(790, 20, 80, 25);
		button_click.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getsubject = jComboBox_subject.getItemAt(jComboBox_subject.getSelectedIndex());
				getdegree = jComboBox_degree.getItemAt(jComboBox_degree.getSelectedIndex());
				numsofques = jComboBox_numbers.getItemAt(jComboBox_numbers.getSelectedIndex());
				quesname = qjformattedtextfield.getText();
				String data = "<html><body>" + "科目: " + getsubject + ", 題數: " + numsofques + ", 難易度: " + getdegree
						+ "<br>" + quesname;
				showlabel.setText(data);
				String str = setNewTestPaper(quesname, getsubject, numsofques, getdegree);
				label.setText(str);
				setMySQL();
				setMySQLQBank();
			}
		});
		panel.add(button_click);

		showlabel = new JLabel();
		showlabel.setBounds(10, 50, 300, 80);
		panel.add(showlabel);

		label = new JLabel();
		label.setBounds(10, 120, 1000, 420);
		panel.add(label);

		scrollable = new JScrollPane(label);
		scrollable.setBounds(10, 120, 1000, 420);
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
