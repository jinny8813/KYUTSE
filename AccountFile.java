import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AccountFile {
	private File openFile;
	private static JFrame frame;
	private static JPanel panel;
	
	private static JButton button_back;
	private static JButton button_import;
	
	private static JLabel successlabel;

	// MySQL 8.0 以上版本 - JDBC 驅動名稱及數據庫 URL
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

	// 數據庫的自己的用户名跟密碼
	static final String USER = "root";
	static final String PASS = "Nknu@123456";

	public void setAccountFile(File openFile) {
		Connection conn = null;
		Statement stmt = null;

		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			String sql = "";
			try {
				InputStreamReader isr = new InputStreamReader(new FileInputStream(openFile.toPath().toString()));// 檔案讀取路徑
				BufferedReader reader = new BufferedReader(isr);
				String line = null;
				try {
					while ((line = reader.readLine()) != null) {
						String item[] = line.split(",");
						String data1 = item[0].trim();
						String data2 = item[1].trim();
						sql = "INSERT INTO `runoob`.`account` (`account`, `password`) VALUES ('" + data1 + "', '"
								+ data2 + "')";
						stmt.executeUpdate(sql);
						System.out.print(data1 + "\t" + data2 + "\n");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

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

	public AccountFile() {
		panel = new JPanel();
		panel.setBackground(SystemColor.WHITE);
		
		frame = new JFrame("AccountFile!");
		frame.setBounds(50, 50, 720, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);

		panel.setLayout(null);

		successlabel = new JLabel("");
		successlabel.setBounds(10, 50, 120, 25);
		panel.add(successlabel);

		button_import = new JButton("匯入資料");
		button_import.setBounds(10, 20, 120, 25);
		button_import.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				jfc.showDialog(new JLabel(), "選擇");
				openFile = jfc.getSelectedFile();
				if (openFile.isDirectory()) {
					System.out.println("資料夾:" + openFile.getAbsolutePath()); // 尋找要匯入的csv檔
				} else if (openFile.isFile()) {
					System.out.println("檔案:" + openFile.getAbsolutePath());
				}
				System.out.println(jfc.getSelectedFile().getName());
				setAccountFile(openFile);
				successlabel.setText("Update Successful!");
			}
		});
		panel.add(button_import);

		
		button_back = new JButton("返回上一頁");
		button_back.setBounds(500, 20, 120, 25);
		button_back.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		panel.add(button_back);

		frame.setVisible(true);

	}
}
