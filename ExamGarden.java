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

public class ExamGarden {
	private static JFrame frame;
	private static JPanel panel;
	
	private static JLabel homelabel;
	
	private static JButton button_exam;
	private static JButton button_free;
	private static JButton button_grade;
	private static JButton button_back;

	ArrayList<QuestionBank> paper;

	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";


	static final String USER = "root";
	static final String PASS = "Nknu@123456";

	public ExamGarden() {
		panel = new JPanel();
		panel.setBackground(SystemColor.WHITE);
		
		frame = new JFrame("ExamGarden!");
		frame.setBounds(50, 50, 720, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);

		panel.setLayout(null);

		homelabel = new JLabel("ExamGarden");
		homelabel.setBounds(10, 20, 120, 25);
		panel.add(homelabel);

		button_exam = new JButton("考試作答");
		button_exam.setBounds(100, 20, 120, 25);
		button_exam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PreTextPaper PreTextPaper = new PreTextPaper();
			}
		});
		panel.add(button_exam);

		button_free = new JButton("自由練習");
		button_free.setBounds(250, 20, 120, 25);
		button_free.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExercisePaper ExercisePaper = new ExercisePaper();
			}
		});
		panel.add(button_free);

		button_grade = new JButton("歷史成績查詢");
		button_grade.setBounds(400, 20, 120, 25);
		button_grade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PersonalScore personalScore = new PersonalScore();
			}
		});
		panel.add(button_grade);

		button_back = new JButton("返回上一頁");
		button_back.setBounds(550, 20, 120, 25);
		button_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		panel.add(button_back);

		frame.setVisible(true);
	}
}
