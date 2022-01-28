package library_package;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class UserDataPanel extends JPanel {
	private JTextField textFieldKorisnIme;
	private JTextField textFieldIDKorisnika;
	private JTextField textFieldIme;
	private JTextField textFieldPrezime;
	private JTextField textFieldTipKorisnika;
	private JTextField textFieldBrIndeksa;
	private JTextField textFieldEmail;
	private JTextField textFieldClanOd;
	private JTextField textFieldPrijavljen;
	
	private MySQLConnection connection = new MySQLConnection();
	private Connection con = null;

	/**
	 * Create the panel.
	 */
	public UserDataPanel(UserWindow userWindow, int userId) {
		setBackground(new Color(255, 255, 153));
		setLayout(null);
		
		JLabel lblPodaciOKorisniku = new JLabel("Podaci o korisniku:");
		lblPodaciOKorisniku.setHorizontalAlignment(SwingConstants.CENTER);
		lblPodaciOKorisniku.setFont(new Font("Dialog", Font.BOLD, 14));
		lblPodaciOKorisniku.setBounds(12, 16, 675, 28);
		add(lblPodaciOKorisniku);
		
		JLabel lblIDKorisnika = new JLabel("ID korisnika:");
		lblIDKorisnika.setHorizontalAlignment(SwingConstants.TRAILING);
		lblIDKorisnika.setBounds(235, 66, 97, 15);
		add(lblIDKorisnika);
		
		JLabel lblIme = new JLabel("Ime:");
		lblIme.setHorizontalAlignment(SwingConstants.TRAILING);
		lblIme.setBounds(235, 101, 97, 15);
		add(lblIme);
		
		JLabel lblPrezime = new JLabel("Prezime:");
		lblPrezime.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPrezime.setBounds(235, 138, 97, 15);
		add(lblPrezime);
		
		JLabel lblTipKorisnika = new JLabel("Tip korisnika:");
		lblTipKorisnika.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTipKorisnika.setBounds(235, 177, 97, 15);
		add(lblTipKorisnika);
		
		JLabel lblKorisnickoIme = new JLabel("Korisničko ime:");
		lblKorisnickoIme.setHorizontalAlignment(SwingConstants.TRAILING);
		lblKorisnickoIme.setBounds(235, 215, 97, 15);
		add(lblKorisnickoIme);
		
		JLabel lblBrojindeksa = new JLabel("BrojIndeksa");
		lblBrojindeksa.setHorizontalAlignment(SwingConstants.TRAILING);
		lblBrojindeksa.setBounds(235, 253, 97, 15);
		add(lblBrojindeksa);
		
		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setHorizontalAlignment(SwingConstants.TRAILING);
		lblEmail.setBounds(235, 290, 97, 15);
		add(lblEmail);
		
		JLabel lblClanOd = new JLabel("Član od:");
		lblClanOd.setHorizontalAlignment(SwingConstants.TRAILING);
		lblClanOd.setBounds(235, 327, 97, 20);
		add(lblClanOd);
		
		JLabel lblPrijavljen = new JLabel("Prijavljen:");
		lblPrijavljen.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPrijavljen.setBounds(235, 367, 97, 15);
		add(lblPrijavljen);
		
		textFieldKorisnIme = new JTextField();
		textFieldKorisnIme.setEditable(false);
		textFieldKorisnIme.setBounds(337, 211, 131, 19);
		add(textFieldKorisnIme);
		textFieldKorisnIme.setColumns(10);
		
		textFieldIDKorisnika = new JTextField();
		textFieldIDKorisnika.setEditable(false);
		textFieldIDKorisnika.setBounds(337, 62, 70, 19);
		add(textFieldIDKorisnika);
		textFieldIDKorisnika.setColumns(10);
		
		textFieldIme = new JTextField();
		textFieldIme.setEditable(false);
		textFieldIme.setBounds(337, 97, 131, 19);
		add(textFieldIme);
		textFieldIme.setColumns(10);
		
		textFieldPrezime = new JTextField();
		textFieldPrezime.setEditable(false);
		textFieldPrezime.setBounds(337, 134, 131, 19);
		add(textFieldPrezime);
		textFieldPrezime.setColumns(10);
		
		textFieldTipKorisnika = new JTextField();
		textFieldTipKorisnika.setEditable(false);
		textFieldTipKorisnika.setBounds(337, 173, 131, 19);
		add(textFieldTipKorisnika);
		textFieldTipKorisnika.setColumns(10);
		
		textFieldBrIndeksa = new JTextField();
		textFieldBrIndeksa.setEditable(false);
		textFieldBrIndeksa.setBounds(337, 249, 131, 19);
		add(textFieldBrIndeksa);
		textFieldBrIndeksa.setColumns(10);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setEditable(false);
		textFieldEmail.setBounds(337, 286, 131, 19);
		add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		textFieldClanOd = new JTextField();
		textFieldClanOd.setEditable(false);
		textFieldClanOd.setBounds(337, 325, 131, 19);
		add(textFieldClanOd);
		textFieldClanOd.setColumns(10);
		
		textFieldPrijavljen = new JTextField();
		textFieldPrijavljen.setEditable(false);
		textFieldPrijavljen.setBounds(337, 363, 70, 19);
		add(textFieldPrijavljen);
		textFieldPrijavljen.setColumns(10);

		setUserDataIntoFields(userId);
		
		JButton btnPrikaiPozajmice = new JButton("PRIKAŽI POZAJMICE");
		btnPrikaiPozajmice.setBackground(Color.ORANGE);
		btnPrikaiPozajmice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelPozajmice panelPozajmice;

				panelPozajmice = new PanelPozajmice(userWindow, String.valueOf(userId));

				userWindow.getPanelManager().show(panelPozajmice);
			}
		});
		//btnPrikaiPozajmice.setBounds(386, 411, 169, 25);
		//add(btnPrikaiPozajmice);
		
		JButton btnObrisiKorisnika = new JButton("OBRIŠI KORISNIKA");
		btnObrisiKorisnika.setBackground(Color.ORANGE);
		btnObrisiKorisnika.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!(userId == userWindow.getUserId())) {
					int input = JOptionPane.showConfirmDialog(null, "Da li želite obrisati korisnika?\nNapomena: Brisanjem korisnika brišu se svi podaci vezani za njega!\nBrisanjem studenta njegove pozajmice ostaće nevraćene!", "Potvrda brisanja dela",JOptionPane.YES_NO_CANCEL_OPTION);
	        		if (input == 0) {
	        			obrisiKorisnika(userId);
	        			PanelFilter filterPanel = new PanelFilter(userWindow);
	        			userWindow.getPanelManager().show(filterPanel);
	        		}
				}
				else {
					Warning warning = new Warning("Upozorenje");
					warning.showMessage("Nije dozvoljeno obrisati samog sebe iz baze!");
				}
			}
		});
		//btnObrisiKorisnika.setBounds(190, 411, 169, 25);
		//add(btnObrisiKorisnika);
		
		//btnPrikaiPozajmice.setBounds(263, 406, 169, 25);
		if (textFieldTipKorisnika.getText().equals("student")) {
			if (!userWindow.getUserTypeString().equals("admin")) {
				btnPrikaiPozajmice.setBounds(263, 406, 169, 25);
			}
			else {
				btnObrisiKorisnika.setBounds(190, 411, 169, 25);
				btnPrikaiPozajmice.setBounds(386, 411, 169, 25);
				add(btnObrisiKorisnika);
			}
			add(btnPrikaiPozajmice);
		}
		else {
			btnObrisiKorisnika.setBounds(263, 406, 169, 25);
			add(btnObrisiKorisnika);
		}
		
	}
	
	public void setUserDataIntoFields(int userId) {
		this.con = connection.createConnection();
		
		String query = "select k.id_korisnika, k.ime_korisnika, k.prezime_korisnika, k.tip_korisnika, k.korisnicko_ime, k.br_indeksa, ifNull(k.e_mail, 'Nema podataka') as e_mail, k.clan_od, k.prijavljen from Korisnik k where k.id_korisnika="+userId;
	
		textFieldIDKorisnika.setText(String.valueOf(userId));
		
	    try (Statement stmt = this.con.createStatement()) {
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {

	        	textFieldIme.setText(rs.getString("ime_korisnika"));
	        	textFieldPrezime.setText(rs.getString("prezime_korisnika"));
	        	textFieldTipKorisnika.setText(rs.getString("tip_korisnika"));
	        	textFieldKorisnIme.setText(rs.getString("korisnicko_ime"));
	        	textFieldBrIndeksa.setText(rs.getString("br_indeksa"));
	        	textFieldEmail.setText(rs.getString("e_mail"));
	        	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        	textFieldClanOd.setText(dateFormat.format(rs.getDate("clan_od")));
	        	textFieldPrijavljen.setText(rs.getString("prijavljen"));
	        	
	        }
	     } catch (SQLException e) {
	    	  Warning w = new Warning("Upozorenje");
	    	  w.showMessage(e.getMessage());
	     }
        
		
		connection.closeConnection();
	}
	
	
	public void obrisiKorisnika(int idKorisnika) {
		
		boolean success = false; 
		
		this.con = connection.createConnection();
		
		String deleteFromObavestenje = "delete from Obavestenje where id_pozajmice in (select id_pozajmice from Pozajmica where id_studenta="+idKorisnika+")";
		String deleteFromPozajmica = "delete from Pozajmica where id_studenta="+idKorisnika;
		String deleteFromRezervacija = "delete from Rezervacija where id_studenta="+idKorisnika;
		String deleteFromKorisnik = "delete from Korisnik where id_korisnika="+idKorisnika;
		
		PreparedStatement preparedStatement;
		try {
			preparedStatement = con.prepareStatement(deleteFromObavestenje);
			preparedStatement.executeUpdate();
			preparedStatement = con.prepareStatement(deleteFromPozajmica);
			preparedStatement.executeUpdate();
			preparedStatement = con.prepareStatement(deleteFromRezervacija);
			preparedStatement.executeUpdate();
			preparedStatement = con.prepareStatement(deleteFromKorisnik);
			preparedStatement.executeUpdate();
			success = true;
		} catch (SQLException e) {
			Warning errorDialog = new Warning("Greška");
			errorDialog.showMessage(e.getMessage());
		}
		
		Warning w = new Warning("Obaveštenje o brisanju");
		if (success) {
			w.showMessage("Uspešno ste obrisali korisnika i sve podatke vezane za njega!");
		}
		else {
			w.showMessage("Došlo je do greške u brisanju korisnika!");
		}		
		
		connection.closeConnection();
	}
	
}
