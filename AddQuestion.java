import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddQuestion {
	private static JFrame frame;
	private static JPanel panel;
	
	private static JComboBox jComboBox_subject;
	private static JComboBox jComboBox_degree;
	
	private static JButton button_back;
	private static JButton button_confirm;
	
	private static JLabel Alabel;
	private static JLabel Blabel;
	private static JLabel Clabel;
	private static JLabel Dlabel;
	private static JLabel ansLabel;
	private static JLabel R_ansLabel;
	private static JLabel quetionLabel;
	
	private static JTextField AnswerInput1;
	private static JTextField AnswerInput2;
	private static JTextField AnswerInput3;
	private static JTextField AnswerInput4;
	private static JTextField RigthAnswer;
	private static JTextField quetionInput;

	AddQuestion() {
		panel = new JPanel();
		frame = new JFrame();
		frame.setBounds(50, 50, 800, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);

		panel.setLayout(null);

		String subject[] = { "國文", "數學", "英文" };
		jComboBox_subject = new JComboBox(subject);
		jComboBox_subject.setBounds(200, 20, 80, 25);
		panel.add(jComboBox_subject);

		String degree[] = { "難", "中", "易" };
		jComboBox_degree = new JComboBox(degree);
		jComboBox_degree.setBounds(300, 20, 80, 25);
		panel.add(jComboBox_degree);

		quetionLabel = new JLabel("Quetion :");
		quetionLabel.setBounds(200, 60, 80, 25);
		panel.add(quetionLabel);
		
		quetionInput = new JTextField();
		quetionInput.setBounds(300, 60, 165, 25);
		panel.add(quetionInput);

		ansLabel = new JLabel("Answer :");
		ansLabel.setBounds(200, 100, 80, 25);
		panel.add(ansLabel);

		Alabel = new JLabel("A :");
		Alabel.setBounds(270, 100, 80, 25);
		panel.add(Alabel);
		
		Blabel = new JLabel("B :");
		Blabel.setBounds(270, 130, 80, 25);
		panel.add(Blabel);
		
		Clabel = new JLabel("C :");
		Clabel.setBounds(270, 160, 80, 25);
		panel.add(Clabel);
		
		Dlabel = new JLabel("D :");
		Dlabel.setBounds(270, 190, 80, 25);
		panel.add(Dlabel);

		AnswerInput1 = new JTextField();
		AnswerInput1.setBounds(300, 100, 90, 25);
		panel.add(AnswerInput1);
		
		AnswerInput2 = new JTextField();
		AnswerInput2.setBounds(300, 130, 90, 25);
		panel.add(AnswerInput2);
		
		AnswerInput3 = new JTextField();
		AnswerInput3.setBounds(300, 160, 90, 25);
		panel.add(AnswerInput3);
		
		AnswerInput4 = new JTextField();
		AnswerInput4.setBounds(300, 190, 90, 25);
		panel.add(AnswerInput4);

		R_ansLabel = new JLabel("RigthAnswer :");
		R_ansLabel.setBounds(200, 220, 80, 25);
		panel.add(R_ansLabel);
		
		RigthAnswer = new JTextField();
		RigthAnswer.setBounds(300, 220, 165, 25);
		panel.add(RigthAnswer);

		button_confirm = new JButton("確認");
		button_confirm.setBounds(300, 290, 165, 25);
		button_confirm.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String b[] = new String[8];
				b[0] = quetionInput.getText();
				b[1] = AnswerInput1.getText();
				b[2] = AnswerInput2.getText();
				b[3] = AnswerInput3.getText();
				b[4] = AnswerInput4.getText();
				b[5] = RigthAnswer.getText();
				b[6] = jComboBox_subject.getItemAt(jComboBox_subject.getSelectedIndex()).toString();
				String degree = jComboBox_degree.getItemAt(jComboBox_degree.getSelectedIndex()).toString();
				if (degree.equals("難")) {
					b[7] = "3";
				} else if (degree.equals("中")) {
					b[7] = "2";
				} else if (degree.equals("易")) {
					b[7] = "1";
				}
				if (b[0].length() != 0 && b[1].length() != 0 && b[2].length() != 0 && b[3].length() != 0
						&& b[4].length() != 0 && b[5].length() != 0) {
					ItemBank itemBank = new ItemBank(2);
					itemBank.setItemBank(1, b);
					frame.dispose();
				} else {
					int t = JOptionPane.showConfirmDialog(null, "請勿輸入空白字串", null, JOptionPane.DEFAULT_OPTION);
					switch (t) {
					case JOptionPane.DEFAULT_OPTION:
					}
				}
			}
		});
		panel.add(button_confirm);

		button_back = new JButton("返回上一頁");
		button_back.setBounds(570, 20, 120, 25);
		button_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		panel.add(button_back);

		frame.setVisible(true);
	}
}
