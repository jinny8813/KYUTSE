import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddAccount {
	private static JFrame frame;
	private static JPanel panel;
	
	private static JLabel accountlabel;
	private static JLabel passlabel;
	private static JLabel namelabel;
	private static JLabel majorlabel;
	
	private static JTextField accountjtf;
	private static JTextField passjtf;
	private static JTextField namejtf;
	private static JTextField majorjtf;
	
	private static JButton button_confrim;
	private static JButton button_back;

	AddAccount() {
		panel = new JPanel();
		panel.setBackground(SystemColor.WHITE);
		
		frame = new JFrame();
		frame.setBounds(50, 50, 400, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);

		panel.setLayout(null);

		accountlabel = new JLabel("account");
		accountlabel.setBounds(10, 20, 80, 25);
		panel.add(accountlabel);

		accountjtf = new JTextField();
		accountjtf.setBounds(100, 20, 165, 25);
		panel.add(accountjtf);

		passlabel = new JLabel("password");
		passlabel.setBounds(10, 50, 80, 25);
		panel.add(passlabel);

		passjtf = new JTextField();
		passjtf.setBounds(100, 50, 165, 25);
		panel.add(passjtf);

		namelabel = new JLabel("name");
		namelabel.setBounds(10, 80, 80, 25);
		panel.add(namelabel);

		namejtf = new JTextField();
		namejtf.setBounds(100, 80, 165, 25);
		panel.add(namejtf);

		majorlabel = new JLabel("major");
		majorlabel.setBounds(10, 110, 80, 25);
		panel.add(majorlabel);

		majorjtf = new JTextField();
		majorjtf.setBounds(100, 110, 165, 25);
		panel.add(majorjtf);

		button_confrim = new JButton("確認");
		button_confrim.setBounds(10, 140, 80, 25);
		button_confrim.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String b[] = new String[4];
				b[0] = accountjtf.getText();
				b[1] = passjtf.getText();
				b[2] = namejtf.getText();
				b[3] = majorjtf.getText();
				if (b[0].length() != 0 && b[1].length() != 0 && b[2].length() != 0 && b[3].length() != 0) {
					for (int i = 0; i < b.length; i++) {
						System.out.println(b[i]);
					}
					AccountInfo accountInfo = new AccountInfo(2);
					accountInfo.setAccountInfo(1, b);
					frame.dispose();
				} else {
					int t = JOptionPane.showConfirmDialog(null, "請勿輸入空白字串", null, JOptionPane.DEFAULT_OPTION);
					switch (t) {
					case JOptionPane.DEFAULT_OPTION:
					}
				}
			}
		});
		panel.add(button_confrim);

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

