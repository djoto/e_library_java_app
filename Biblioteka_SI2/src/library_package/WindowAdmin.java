package library_package;

//import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import java.awt.Color;

public class WindowAdmin implements UserWindow {

	private JFrame frameAdmin;
	
	private String userType = "admin";
	private int userId;
	
	private Controller manager;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WindowAdmin window = new WindowAdmin(4);
					window.frameAdmin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public WindowAdmin(int userId) {
		this.userId = userId;
		initialize();
	}

	@Override
	public UserWindow getWindow() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Controller getPanelManager() {
		// TODO Auto-generated method stub
		return manager;
	}

	@Override
	public String getUserTypeString() {
		// TODO Auto-generated method stub
		return userType;
	}

	@Override
	public int getUserId() {
		// TODO Auto-generated method stub
		return userId;
	}

	@Override
	public JFrame getWindowFrame() {
		// TODO Auto-generated method stub
		return frameAdmin;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameAdmin = new JFrame();
		frameAdmin.getContentPane().setBackground(new Color(255, 255, 153));
		frameAdmin.setResizable(false);
		frameAdmin.setTitle("Biblioteka - Administrator");
		frameAdmin.setBounds(300, 110, 700, 500);
		frameAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameAdmin.getContentPane().setLayout(null);
		ImageIcon img = new ImageIcon("library_icon.png");
		frameAdmin.setIconImage(img.getImage());
		
		manager = new Controller() {
            @Override
            public void show(JPanel pane) {
            	frameAdmin.getContentPane().removeAll();
            	frameAdmin.setContentPane(pane);
            	frameAdmin.getContentPane().revalidate();
            	frameAdmin.getContentPane().repaint();
            }
        };
        
		PanelFilter filterPanel = new PanelFilter(getWindow());

		manager.show(filterPanel);
        
        
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(255, 255, 153));
		frameAdmin.setJMenuBar(menuBar);
		
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
		
		
		
		JMenuItem mntmKorisniciOstali = new JMenuItem("Korisnici - ostali");
		mntmKorisniciOstali.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelKorisniciOstali panelKorisniciOstali = new PanelKorisniciOstali(getWindow());
				manager.show(panelKorisniciOstali);
			}
		});
		mnBiblioteka.add(mntmKorisniciOstali);
		
		
		
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
		
		JMenuItem mntmDodajBibliotekara = new JMenuItem("Dodaj bibliotekara");
		mntmDodajBibliotekara.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelDodavanjeBibliotekara panelDodavanjeBibliotekara = new PanelDodavanjeBibliotekara(getWindow());
				manager.show(panelDodavanjeBibliotekara);
			}
		});
		mnDodaj.add(mntmDodajBibliotekara);
		
		
		
		
		
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
