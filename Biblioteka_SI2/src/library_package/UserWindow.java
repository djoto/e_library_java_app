package library_package;

import javax.swing.JFrame;


public interface UserWindow {
	
	public UserWindow getWindow();
	
	public Controller getPanelManager();
	
	public String getUserTypeString();
	
	public int getUserId();
	
	public JFrame getWindowFrame();
	
}
