import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Supervisor_Home {
	private static JFrame frame;
	private static JPanel panel;
	
	private static JLabel homelabel;
	
	private static JButton button_questionbank;
	private static JButton button_account;
	private static JButton button_import;
	private static JButton button_logout;

	public Supervisor_Home() {
		panel = new JPanel();
		panel.setBackground(SystemColor.WHITE);
		
		frame = new JFrame("Supervisor!");
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
		button_account = new JButton("帳號管理");
		button_account.setBounds(100, 20, 80, 25);
		button_account.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AccountInfo accountInfo = new AccountInfo();
			}
		});
		panel.add(button_account);

		button_import = new JButton("匯入資料");
		button_import.setBounds(120, 20, 80, 25);
		button_import.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AccountFile accountFile = new AccountFile();
			}
		});
		panel.add(button_import);

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
