import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Student_Home {
	private static JFrame frame;
	private static JPanel panel;
	
	private static JLabel homelabel;
	
	private static JButton button_exam;
	private static JButton button_info;
	private static JButton button_logout;

	
	public Student_Home() {
		panel = new JPanel();
		panel.setBackground(SystemColor.WHITE);
		
		frame = new JFrame("Student!");
		frame.setBounds(50, 50, 400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);

		homelabel = new JLabel("Home");
		homelabel.setBounds(10, 20, 80, 25);
		panel.add(homelabel);

		button_exam = new JButton("測驗練習");
		button_exam.setBounds(100, 20, 80, 25);
		button_exam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExamGarden examGarden = new ExamGarden();
			}
		});
		panel.add(button_exam);

		button_info = new JButton("修改個人資料");
		button_info.setBounds(100, 20, 80, 25);
		button_info.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PersonalInfo PersonalInfo = new PersonalInfo();
			}
		});
		panel.add(button_info);

		button_logout = new JButton("登出");
		button_logout.setBounds(570, 520, 120, 25);
		button_logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login login = new Login();
				frame.dispose();
			}
		});
		panel.add(button_logout);

		frame.setVisible(true);

	}
}
