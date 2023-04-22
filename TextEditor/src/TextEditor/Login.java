package TextEditor;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

public class Login extends JPanel implements ActionListener{
	JLabel userl = new JLabel("User Name:");
	JTextField user = new JTextField();
	JLabel passl = new  JLabel("Password:");
	JPasswordField pass = new JPasswordField();
	JPanel loginP = new JPanel(new GridLayout(3,2));
	JPanel panel = new JPanel();
	JButton login = new JButton("Login");
	JButton register = new JButton("Register");
	CardLayout cl;
	Login(){
		setLayout(new CardLayout());
		loginP.add(userl);
		loginP.add(user);
		loginP.add(passl);
		loginP.add(pass);
		login.addActionListener(this);
		register.addActionListener(this);
		loginP.add(login);
		loginP.add(register);
		panel.add(loginP);
		add(panel, "login");
		cl = (CardLayout) getLayout();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == login) {
			try {
				BufferedReader read = new BufferedReader(new FileReader("passwords.txt"));
				String details = read.readLine();
				String password = null;
				while(details != null) {
				StringTokenizer st = new StringTokenizer(details);
				if(user.getText().equals(st.nextToken())) {
						password = st.nextToken();
				}
				details = read.readLine();
				}
				read.close();
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				md.update(new String(pass.getPassword()).getBytes());
				byte byteData[] = md.digest();
				StringBuffer sb = new StringBuffer();
				for(int i = 0; i < byteData.length; i++) {
					sb.append(Integer.toString((byteData[i] + 0xFF) + 0x100, 16).substring(1));
				}
				if(password.equals(sb.toString())) {
					add(new FileBrowser(user.getText()),"fb");
					cl.show(this, "fb");
				}
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		if(e.getSource() == register) {
		add(new Register(), "register");
		cl.show(this, "register");
	}
	}
	
	public static void main(String args[]) {
		JFrame frame = new JFrame("Editor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,500);
		Login login = new Login();
		frame.add(login);
		frame.setVisible(true);
	}

}
