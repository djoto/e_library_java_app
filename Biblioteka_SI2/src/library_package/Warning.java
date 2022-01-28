package library_package;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Warning {
	
	JFrame frame;
	
	public Warning(String content) {
		frame  = new JFrame(content);
		frame.getContentPane().setBackground(new Color(255, 255, 153));
		ImageIcon img = new ImageIcon("warning_icon.png");
		frame.setIconImage(img.getImage());
	}
	
	public void showMessage(String message){
		JOptionPane.showMessageDialog(frame, message);
	}
}
