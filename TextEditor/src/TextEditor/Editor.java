package TextEditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;

public class Editor extends JPanel implements ActionListener
{
	File file;
	JButton save = new JButton("Save");
	JButton savec = new JButton("Save and close");
	JTextArea text = new JTextArea(20,40);
	
	public Editor(String path) {
		file = new File(path);
		save.addActionListener(this);
		savec.addActionListener(this);
		if(file.exists()) {
			try {
				BufferedReader read = new BufferedReader(new FileReader(file));
				String line = read.readLine();
				while(line!=null) {
					text.append(line+"\n");
					line = read.readLine();
				}
				read.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		add(save);
		add(savec);
		add(text);

		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(text.getText());
			writer.close();
			if(e.getSource() == savec) {
				Login login = (Login) getParent();
				login.cl.show(login, "fb");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
