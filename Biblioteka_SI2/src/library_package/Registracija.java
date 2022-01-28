package library_package;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Color;

public class Registracija extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldIme;
	private JTextField textFieldPrezime;
	private JTextField textFieldBrIndeksa;
	private JTextField textFieldKorisnickoIme;
	private JPasswordField passwordFieldRegistracija;
	private JTextField textFieldEmail;
	
	private MySQLConnection connection = new MySQLConnection();
	private Connection con = null;

	/**
	 * Create the frame.
	 */
	public Registracija() {
		setResizable(false);
		ImageIcon img = new ImageIcon("library_icon.png");
		setIconImage(img.getImage());
		setTitle("Biblioteka - Registracija");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 100, 378, 504);
		contentPane = new JPanel();
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 153));
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		textFieldIme = new JTextField();
		textFieldIme.setBounds(179, 37, 141, 22);
		panel.add(textFieldIme);
		textFieldIme.setColumns(10);
		
		textFieldPrezime = new JTextField();
		textFieldPrezime.setBounds(179, 85, 141, 22);
		panel.add(textFieldPrezime);
		textFieldPrezime.setColumns(10);
		
		textFieldBrIndeksa = new JTextField();
		textFieldBrIndeksa.setBounds(179, 137, 141, 22);
		panel.add(textFieldBrIndeksa);
		textFieldBrIndeksa.setColumns(10);
		
		textFieldKorisnickoIme = new JTextField();
		textFieldKorisnickoIme.setBounds(179, 192, 141, 22);
		panel.add(textFieldKorisnickoIme);
		textFieldKorisnickoIme.setColumns(10);
		
		JLabel lblImeRegistracija = new JLabel("IME:");
		lblImeRegistracija.setBounds(62, 40, 99, 16);
		panel.add(lblImeRegistracija);
		
		JLabel lblPrezimeRegistracija = new JLabel("PREZIME:");
		lblPrezimeRegistracija.setBounds(62, 88, 99, 16);
		panel.add(lblPrezimeRegistracija);
		
		JLabel lblBrojIndeksa = new JLabel("BROJ INDEKSA:");
		lblBrojIndeksa.setBounds(62, 140, 105, 16);
		panel.add(lblBrojIndeksa);
		
		JLabel lblKorisnickoIme = new JLabel("KORISNIČKO IME:");
		lblKorisnickoIme.setBounds(62, 195, 105, 22);
		panel.add(lblKorisnickoIme);
		
		JLabel lblLozinkaRegistracija = new JLabel("LOZINKA:");
		lblLozinkaRegistracija.setBounds(62, 252, 105, 16);
		panel.add(lblLozinkaRegistracija);
		
		passwordFieldRegistracija = new JPasswordField();
		passwordFieldRegistracija.setBounds(179, 249, 141, 22);
		panel.add(passwordFieldRegistracija);
	
		JLabel lblNewLabel = new JLabel("EMAIL:");
		lblNewLabel.setBounds(62, 309, 105, 22);
		panel.add(lblNewLabel);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(179, 307, 141, 22);
		panel.add(textFieldEmail);
		textFieldEmail.setColumns(10);
	
		
		JButton btnPosaljiZahtev = new JButton("POŠALJI ZAHTEV ZA REGISTRACIJU");
		btnPosaljiZahtev.setBackground(Color.ORANGE);
		btnPosaljiZahtev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (ispravniPodaci()){
					posaljiZahtevZaRegistraciju();
				}
			}
		});
		btnPosaljiZahtev.setBounds(62, 362, 248, 25);
		panel.add(btnPosaljiZahtev);
		
		JButton btnNazad = new JButton("NAZAD NA PRIJAVU");
		btnNazad.setBackground(Color.ORANGE);
		btnNazad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Prijava windowPrijava = new Prijava();
				windowPrijava.getFrame().setVisible(true);
				getFrame().dispose();
			}
		});
		btnNazad.setBounds(108, 408, 157, 25);
		panel.add(btnNazad);
				
	}
	
	public JFrame getFrame() {
		return this;
	}
	
	public String hashWithSHA256(String textToHash) throws NoSuchAlgorithmException{
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(textToHash.getBytes(StandardCharsets.UTF_8));
		StringBuilder sb = new StringBuilder();
	    for (byte b : hash) {
	        sb.append(String.format("%02X ", b));
	    }
		return sb.toString().replaceAll(" ", "");
	}
	
	public boolean ispravniPodaci() {
		
		Warning warningDialog = new Warning("Upozorenje");
		
		if (!(textFieldIme.getText().trim().length() > 0)) {
			warningDialog.showMessage("Polje za ime mora biti popunjeno!");
			return false;
		}
		if (!(textFieldPrezime.getText().trim().length() > 0)) {
			warningDialog.showMessage("Polje za prezime mora biti popunjeno!");
			return false;
		}
		if (!(textFieldBrIndeksa.getText().trim().length() > 0)) {
			warningDialog.showMessage("Polje za broj indeksa mora biti popunjeno!");
			return false;
		}
		if (!(textFieldKorisnickoIme.getText().trim().length() > 0)) {
			warningDialog.showMessage("Polje za korisničko ime mora biti popunjeno!");
			return false;
		}
		else {
			if(postojiKorisnickoIme(textFieldKorisnickoIme.getText())) {
				warningDialog.showMessage("Korisničko ime već postoji!\nPokušajte sa nekim drugim imenom.");
				return false;
			}
		}
		String passString = new String(passwordFieldRegistracija.getPassword());
		if ((!(passString.trim().length() > 0)) || (passwordFieldRegistracija.getPassword().length < 8)) {
			warningDialog.showMessage("Polje za lozinku mora imati najmanje 8 karaktera!");
			return false;
		}
		if (!(textFieldEmail.getText().trim().length() > 0)) {
			warningDialog.showMessage("Polje za E-mail mora biti popunjeno!");
			return false;
		}

		return true;
	}
	
	public void posaljiZahtevZaRegistraciju() {
		
		boolean success = false;
		
		this.con = connection.createConnection();
		
		String insertIntoZahtevZaRegistraciju = "insert into ZahtevZaRegistraciju (ime_studenta, prezime_studenta, korisnicko_ime_studenta, password_hash_studenta, br_indeksa_studenta, e_mail_studenta) values (?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStmtZahtevZaRegistraciju;
		try {
			preparedStmtZahtevZaRegistraciju = con.prepareStatement(insertIntoZahtevZaRegistraciju);
			preparedStmtZahtevZaRegistraciju.setString(1, textFieldIme.getText());
			preparedStmtZahtevZaRegistraciju.setString(2, textFieldPrezime.getText());
			preparedStmtZahtevZaRegistraciju.setString(3, textFieldKorisnickoIme.getText());
			try {
				preparedStmtZahtevZaRegistraciju.setString(4, hashWithSHA256(new String(passwordFieldRegistracija.getPassword())));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			preparedStmtZahtevZaRegistraciju.setString(5, textFieldBrIndeksa.getText());
			preparedStmtZahtevZaRegistraciju.setString(6, textFieldEmail.getText());

			//System.out.println(modelSpinner.getValue().getClass().getName());

		    // execute the preparedstatement
		    preparedStmtZahtevZaRegistraciju.execute();
		    success = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			success = false;
			//e.printStackTrace();
		}
		
		if (success) {
			Warning successDialog = new Warning("Uspeh");
			successDialog.showMessage("Poslali ste zahtev za registraciju!\nVaša zahtev će biti razmotren u roku 24 sata.");
		}
		else {
			Warning successDialog = new Warning("Greška");
			successDialog.showMessage("Došlo je do greške pri generisanju zahteva!");
		}
		
		connection.closeConnection();
		
	}
	
	public boolean postojiKorisnickoIme(String unesenoIme) {
		
		this.con = connection.createConnection();
		
		boolean postoji = false;
		
		String query = "select k.korisnicno_ime from Korisnik k where k.korisnicko_ime='"+unesenoIme+"'";
		
	    try (Statement stmt = this.con.createStatement()) {
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	postoji = true;
	        }
	     } catch (SQLException e) {
	    	  Warning w = new Warning("Upozorenje");
	    	  w.showMessage(e.getMessage());
	     }
		
		connection.closeConnection();
		
		return postoji;
		
	}
	
	
}