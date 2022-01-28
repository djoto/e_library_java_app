package library_package;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.awt.Color;

//import java.awt.EventQueue;

public class WindowBibliotekar implements UserWindow {

	private JFrame frameBibliotekar;
	
	private String userType = "bibliotekar";
	private int userId;
	
	private Controller manager;
	
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WindowBibliotekar window = new WindowBibliotekar(3);
					window.frameBibliotekar.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public WindowBibliotekar(int userId) {
		this.userId = userId;
		initialize();
	}
	
	public WindowBibliotekar getWindow() {
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
		return frameBibliotekar;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
	
		frameBibliotekar = new JFrame();
		frameBibliotekar.getContentPane().setBackground(new Color(255, 255, 153));
		frameBibliotekar.setResizable(false);
		frameBibliotekar.setTitle("Biblioteka - Bibliotekar");
		frameBibliotekar.setBounds(300, 110, 700, 500);
		frameBibliotekar.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameBibliotekar.getContentPane().setLayout(null);
		ImageIcon img = new ImageIcon("library_icon.png");
		frameBibliotekar.setIconImage(img.getImage());
		
		manager = new Controller() {
            @Override
            public void show(JPanel pane) {
            	frameBibliotekar.getContentPane().removeAll();
            	frameBibliotekar.setContentPane(pane);
            	frameBibliotekar.getContentPane().revalidate();
            	frameBibliotekar.getContentPane().repaint();
            }
        };
        
		PanelFilter filterPanel = new PanelFilter(getWindow());

		manager.show(filterPanel);
        
        
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(255, 255, 153));
		frameBibliotekar.setJMenuBar(menuBar);
		
		
		
		JMenu mnBiblioteka = new JMenu("Biblioteka");
		menuBar.add(mnBiblioteka);
		
		JMenuItem mntmPretragaBiblioteke = new JMenuItem("Pretraga biblioteke");
		mntmPretragaBiblioteke.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelFilter filterPanel = new PanelFilter(getWindow());
				manager.show(filterPanel);
			}
		});
		mnBiblioteka.add(mntmPretragaBiblioteke);
		
		
		
		JMenuItem mntmKorisnici = new JMenuItem("Korisnici - Studenti");
		mntmKorisnici.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelKorisnici panelKorisnici = new PanelKorisnici(getWindow());
				manager.show(panelKorisnici);
			}
		});
		mnBiblioteka.add(mntmKorisnici);
		
		
		
		JMenuItem mntmPozajmice = new JMenuItem("Pozajmice");
		mntmPozajmice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelPozajmice panelPozajmice;
				panelPozajmice = new PanelPozajmice(getWindow(), "all");
				getPanelManager().show(panelPozajmice);
			}
		});
		mnBiblioteka.add(mntmPozajmice);
		
		
		
		JMenuItem mntmRezervacije = new JMenuItem("Rezervacije");
		mntmRezervacije.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelRezervacije panelRezervacije = new PanelRezervacije(getWindow());
				manager.show(panelRezervacije);
			}
		});
		mnBiblioteka.add(mntmRezervacije);
		
		
		
		JMenuItem mntmObavetenja = new JMenuItem("Obaveštenja");
		mntmObavetenja.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelObavestenja panelObavestenja = new PanelObavestenja(getWindow());
				manager.show(panelObavestenja);
			}
		});
		mnBiblioteka.add(mntmObavetenja);
		
		
		
		JMenuItem mntmZahteviZaRegistraciju = new JMenuItem("Zahtevi za registraciju");
		mntmZahteviZaRegistraciju.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelZahteviZaRegistraciju panelZahteviZaRegistraciju = new PanelZahteviZaRegistraciju(getWindow());
				manager.show(panelZahteviZaRegistraciju);
			}
		});
		mnBiblioteka.add(mntmZahteviZaRegistraciju);
		
		
		
		
		JMenu mnDodaj = new JMenu("Dodaj");
		mnBiblioteka.add(mnDodaj);
		
		JMenuItem mntmDodajNovoDelo = new JMenuItem("Dodaj novo delo");
		mntmDodajNovoDelo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelDodavanjeDela panelDodavanjeDela = new PanelDodavanjeDela(getWindow());
				manager.show(panelDodavanjeDela);
			}
		});
		mnDodaj.add(mntmDodajNovoDelo);
		
		JMenuItem mntmDodajNoviTip = new JMenuItem("Dodaj novi tip dela");
		mntmDodajNoviTip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelDodavanjeTipaDela panelDodavanjeTipaDela = new PanelDodavanjeTipaDela(getWindow());
				manager.show(panelDodavanjeTipaDela);
			}
		});
		mnDodaj.add(mntmDodajNoviTip);
		
		
		
		
		
		JMenu mnMojProfil = new JMenu("Moj profil");
		menuBar.add(mnMojProfil);
		
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
