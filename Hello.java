import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.net.URL;

import javax.swing.*;

public class Hello {
	private static JFrame frame;
	private static JPanel panel;

	private static JLabel kyulabel;
	private static JLabel piclabel;

	private static JButton button_login;

	public Hello() {
		
		panel = new JPanel();
		panel.setBackground(SystemColor.WHITE);
		
		frame = new JFrame("Hello!");
		frame.setBounds(50, 50, 500, 500);
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//panel_content.setBackground(SystemColor.inactiveCaptionBorder);
		
		panel.setLayout(null);

		kyulabel = new JLabel("Hello KYUTES!");
		kyulabel.setBounds(30, 200, 200, 50);
		kyulabel.setFont(new java.awt.Font("",1,25));
		panel.add(kyulabel);

		button_login = new JButton("Login");
		button_login.setBounds(300, 200, 100, 50);
		button_login.setFont(new java.awt.Font("",1,20));		//Font.PLAIN
		button_login.addActionListener(new OurListener());
		button_login.setBackground(SystemColor.inactiveCaptionBorder);
		panel.add(button_login);

		piclabel = new JLabel(new ImageIcon("D:\\上課報告區\\軟體工程\\picture1.jpg"));
		piclabel.setBounds(0, 0, 500, 170);
		panel.add(piclabel);

		frame.setVisible(true);
	}

	private class OurListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Login login = new Login();
			frame.dispose();
		}
	}
}
