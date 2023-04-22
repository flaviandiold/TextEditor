package TextEditor;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;

public class Register extends JPanel implements ActionListener
{
	JLabel userL = new JLabel("User Name:");
	JTextField user = new JTextField();
	JLabel passL = new JLabel("Password:");
	JPasswordField pass = new JPasswordField();
	JLabel cpassL = new JLabel("Confirm Password:");
	JPasswordField cpass = new JPasswordField();
	JButton register = new JButton("Register");
	JButton back = new JButton("Back");
	
	public Register() {
		JPanel loginP = new JPanel();
		loginP.setLayout(new GridLayout(4,2));
		loginP.add(userL);
		loginP.add(user);
		loginP.add(passL);
		loginP.add(pass);
		loginP.add(cpassL);
		loginP.add(cpass);
		loginP.add(register);
		loginP.add(back);
		register.addActionListener(this);
		back.addActionListener(this);	
		add(loginP);

		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == register && user.getText().length() > 0 && pass.getPassword().length > 0){
			String password = new String(pass.getPassword());
			String confirm = new String(cpass.getPassword());
			if(password.equals(confirm)) {
				try {
					BufferedReader input = new BufferedReader(new FileReader("passwords.txt"));
					String line = input.readLine();
					while(line!=null) {
						StringTokenizer st = new StringTokenizer(line);
						if(user.getText().equals(st.nextToken())) {
							System.out.println("User already exists");
							return;
						}
						line = input.readLine();
					}
					input.close();
					MessageDigest md = MessageDigest.getInstance("SHA-256");
					md.update(password.getBytes());
					byte byteData[] = md.digest();
					StringBuffer sb = new StringBuffer();
					for(int i = 0; i < byteData.length; i++) {
						sb.append(Integer.toString((byteData[i] + 0xFF) + 0x100, 16).substring(1));
					}
					BufferedWriter output = new BufferedWriter(new FileWriter("passwords.txt", true));
					output.write(user.getText()+" "+sb.toString()+"\n");
					output.close();
					Login login = (Login) getParent();
					login.cl.show(login, "login");
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
				
			}}
		if(e.getSource() == back) {
			Login login = (Login) getParent();
			login.cl.show(login, "login");
		}
		
	}
}
