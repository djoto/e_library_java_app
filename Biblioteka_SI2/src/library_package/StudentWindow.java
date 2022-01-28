package library_package;

//import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


import javax.swing.JMenu;
import java.awt.Color;

public class StudentWindow implements UserWindow {

	private JFrame frmBibliotekaStudent;
	
	private String userType = "student";
	private int userId;

	private Controller manager;
	
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentWindow window = new StudentWindow(1);
					window.frmBibliotekaStudent.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public StudentWindow(int userId) {
		this.userId = userId;
		initialize(this.userId);
	}
	
	public StudentWindow getWindow() {
		return this;
	}
	
	public String getUserTypeString() {
		return this.userType;
	}
	
	public Controller getPanelManager() {
		return manager;
	}
	
	public int getUserId() {
		return this.userId;
	}

	public JFrame getWindowFrame() {
		return frmBibliotekaStudent;
	}
	/**
	 * Initialize the contents of the frame.
	 */
	
	
	private void initialize(int userId) {
		frmBibliotekaStudent = new JFrame();
		frmBibliotekaStudent.getContentPane().setBackground(new Color(255, 255, 153));
		frmBibliotekaStudent.setResizable(false);
		frmBibliotekaStudent.setTitle("Biblioteka - Student");
		frmBibliotekaStudent.setBounds(300, 110, 700, 500);
		frmBibliotekaStudent.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBibliotekaStudent.getContentPane().setLayout(null);
		ImageIcon img = new ImageIcon("library_icon.png");
		frmBibliotekaStudent.setIconImage(img.getImage());
		
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(255, 255, 153));
		frmBibliotekaStudent.setJMenuBar(menuBar);
		
		JMenu mnStanjeBiblioteke = new JMenu("Stanje biblioteke");
		menuBar.add(mnStanjeBiblioteke);
		
		
		manager = new Controller() {
            @Override
            public void show(JPanel pane) {
            	frmBibliotekaStudent.getContentPane().removeAll();
            	frmBibliotekaStudent.setContentPane(pane);
            	frmBibliotekaStudent.getContentPane().revalidate();
            	frmBibliotekaStudent.getContentPane().repaint();
            }
        };
        
		PanelFilter filterPanel = new PanelFilter(getWindow());

		manager.show(filterPanel);
		
		
		JMenuItem mntmPretragaBiblioteke = new JMenuItem("Pretraga biblioteke");
		mntmPretragaBiblioteke.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelFilter filterPanel = new PanelFilter(getWindow());
				manager.show(filterPanel);
			}
		});
		mnStanjeBiblioteke.add(mntmPretragaBiblioteke);
		
		JMenu mnMojProfil = new JMenu("Moj profil");
		menuBar.add(mnMojProfil);
		
		JMenuItem mntmMojProfil = new JMenuItem("Moji podaci i pozajmice");
		mntmMojProfil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				UserDataPanel profilPanel = new UserDataPanel(getWindow(), userId);
				manager.show(profilPanel);
			}
		});
		mnMojProfil.add(mntmMojProfil);
		
		
		JMenuItem mntmMojeRezervacije = new JMenuItem("Moje rezervacije");
		mntmMojeRezervacije.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelRezervacije panelRezervacije = new PanelRezervacije(getWindow());
				manager.show(panelRezervacije);
			}
		});
		mnMojProfil.add(mntmMojeRezervacije);
		
		JMenuItem mntmObavetenja = new JMenuItem("Moja obaveštenja");
		mntmObavetenja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelObavestenja panelObavestenja = new PanelObavestenja(getWindow());
				manager.show(panelObavestenja);
			}
		});
		mnMojProfil.add(mntmObavetenja);
		
		JMenuItem mntmOdjava = new JMenuItem("Odjava");
		mntmOdjava.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Prijava windowPrijava = new Prijava();
				windowPrijava.podesiOznakuPrijavljen(userId, "Ne");
				windowPrijava.getFrame().setVisible(true);
				getWindowFrame().dispose();
			}
		});
		mnMojProfil.add(mntmOdjava);
		
	}

	
}
