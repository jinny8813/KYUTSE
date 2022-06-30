import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ExamMachine {
	private static Set<QuestionBank> questionRepository = new HashSet<>(); // 題目總覽

	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

	static final String USER = "root";
	static final String PASS = "Nknu@123456";

	public ExamMachine() {

	}

	public ExamMachine(Object getsubject, Object numsofques, Object selection) {
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

			System.out.print(numsofques);
			System.out.print(selection);

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
		System.out.println("Goodbye!");
	}

	// 指定出幾題
	public ArrayList<QuestionBank> generatePaper(int num) {
		System.out.println("系統正在隨機出題目，請稍等");

		ArrayList<QuestionBank> questionRepository = new ArrayList<>(this.questionRepository);// 把set集合暂时转化为list集合，为的是利用索引来随机
		HashSet<QuestionBank> paper = new HashSet<>();// 用來儲存題目，可以重複

		// 用這個while來不斷生成一張題目卷
		while (paper.size() != num) {
			QuestionBank question = questionRepository.get(new Random().nextInt(questionRepository.size()));
			paper.add(question);
		}

		ArrayList<QuestionBank> newPaper = new ArrayList<>(paper);
		ArrayList<QuestionBank> finalPaper = new ArrayList<>();// 空的ArrayList，用來存有打亂順序的，有選項的答案，沒有題號的題目

		// 這個for迴圈用來打亂順序
		for (int i = 0; i < paper.size(); i++) {
			QuestionBank question = newPaper.get(i);

			// 選項隨機換位置，每次都不一樣
			// 生成不同的號碼，生成隨機順序的選項
			int[] indexs = this.randomIndexs();
			String[] oldOptions = question.getOption();
			String[] newOptions = new String[oldOptions.length];
			char firstOrder = 'A';

			for (int j = 0; j < oldOptions.length; j++) {
				String optionOrder = String.valueOf((char) (firstOrder + j));// 将char字符转化为String
				String option = oldOptions[indexs[j]];
				newOptions[j] = optionOrder + "." + option;
				if (option.equals(question.getAnswer())) {
					question.setAnswer(newOptions[j]);
				}
			}
			question.setOption(newOptions);
			finalPaper.add(question);
		}
		return finalPaper;
	}

	// 生成亂數的標籤(難)，生成隨機選項的時候要用他
	private int[] randomIndexs() {
		int[] indexs = new int[4];
		ArrayList<Integer> my_list = new ArrayList<Integer>();
		my_list.add(0);
		my_list.add(1);
		my_list.add(2);
		my_list.add(3);
		Collections.shuffle(my_list);

		for (int i = 0; i < 4; i++) {
			indexs[i] = my_list.get(i);
		}
		return indexs;
	}

	// 改考卷，生成分數
	public float checkPaper(ArrayList<QuestionBank> paper, String[] answer) {
		float oneQueScore = 100F / paper.size();// 一道题的分数
		float finalScore = 0;
		for (int i = 0; i < paper.size(); i++) {
			String trueAnswer = paper.get(i).getAnswer().substring(0, 1); // 把選項拉出來，charAt也可以
			if (trueAnswer.equalsIgnoreCase(answer[i])) {
				finalScore += oneQueScore;
			}
		}
		return finalScore;
	}

	public float preCheck(String[] answerccc, String[] answerin) {
		float oneQueScore = 100F / (answerccc.length);//
		float finalScore = 0;
		for (int i = 0; i < answerccc.length; i++) {
			if (answerccc[i].equals(answerin[i])) {
				finalScore += oneQueScore;
			}
		}
		return finalScore;
	}

	public String correctAns(ArrayList<QuestionBank> paper) {		// 標準答案格式化
		String str = "";
		int count = 0;
		for (int i = 0; i < paper.size(); i++) {
			String trueAnswer = paper.get(i).getAnswer().substring(0, 1);
			str = str + trueAnswer;
			count++;
			if (count == 5) {
				str = str + "_";
				count = 0;
			}
		}
		return str;
	}
}
