import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Teacher_Home {
	private static JFrame frame;
	private static JPanel panel;
	
	private static JLabel homelabel;
	
	private static JButton button_questionbank;
	private static JButton button_newpaper;
	private static JButton button_previous;
	private static JButton button_report;
	private static JButton button_logout;

	public Teacher_Home() {
		panel = new JPanel();
		panel.setBackground(SystemColor.WHITE);
		
		frame = new JFrame("Teacher!");
		frame.setBounds(50, 50, 400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);

		homelabel = new JLabel("Home");
		homelabel.setBounds(10, 20, 80, 25);
		panel.add(homelabel);

		button_questionbank = new JButton("題庫管理");
		button_questionbank.setBounds(100, 20, 80, 25);
		button_questionbank.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ItemBank itemBank = new ItemBank();
			}
		});
		panel.add(button_questionbank);

		button_newpaper = new JButton("建立試卷");
		button_newpaper.setBounds(100, 20, 80, 25);
		button_newpaper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewTestPaper newTestPaper = new NewTestPaper();
			}
		});
		panel.add(button_newpaper);

		button_previous = new JButton("歷史試題資料");
		button_previous.setBounds(100, 20, 80, 25);
		button_previous.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PreTextInfo preTextInfo = new PreTextInfo();
			}
		});
		panel.add(button_previous);

		button_report = new JButton("成績一覽");
		button_report.setBounds(100, 20, 80, 25);
		button_report.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReportCard reportCard = new ReportCard();
			}
		});
		panel.add(button_report);

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
