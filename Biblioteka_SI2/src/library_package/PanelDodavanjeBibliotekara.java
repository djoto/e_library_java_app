package library_package;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;

public class PanelDodavanjeBibliotekara extends JPanel {
	private JTextField textFieldIme;
	private JTextField textFieldPrezime;
	private JTextField textFieldKorisnickoIme;
	private JTextField textFieldEmail;
	private JPasswordField passwordFieldLozinka;
	
	private MySQLConnection connection = new MySQLConnection();
	private Connection con = null;

	/**
	 * Create the panel.
	 */
	public PanelDodavanjeBibliotekara(UserWindow userWindow) {
		setBackground(new Color(255, 255, 153));
		setLayout(null);
		
		JLabel lblDodavanjeBibliotekara = new JLabel("Dodavanje bibliotekara");
		lblDodavanjeBibliotekara.setHorizontalAlignment(SwingConstants.CENTER);
		lblDodavanjeBibliotekara.setFont(new Font("Dialog", Font.ITALIC, 15));
		lblDodavanjeBibliotekara.setBounds(12, 29, 679, 24);
		add(lblDodavanjeBibliotekara);
		
		JLabel lblIme = new JLabel("Ime:");
		lblIme.setBounds(233, 93, 95, 15);
		add(lblIme);
		
		JLabel lblPrezime = new JLabel("Prezime:");
		lblPrezime.setBounds(233, 139, 95, 15);
		add(lblPrezime);
		
		JLabel lblKorisnickoIme = new JLabel("Korisničko ime:");
		lblKorisnickoIme.setBounds(233, 183, 95, 15);
		add(lblKorisnickoIme);
		
		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setBounds(233, 231, 95, 15);
		add(lblEmail);
		
		JLabel lblLozinka = new JLabel("Lozinka:");
		lblLozinka.setBounds(233, 278, 95, 15);
		add(lblLozinka);
		
		textFieldIme = new JTextField();
		textFieldIme.setBounds(332, 89, 123, 19);
		add(textFieldIme);
		textFieldIme.setColumns(10);
		
		textFieldPrezime = new JTextField();
		textFieldPrezime.setBounds(332, 135, 123, 19);
		add(textFieldPrezime);
		textFieldPrezime.setColumns(10);
		
		textFieldKorisnickoIme = new JTextField();
		textFieldKorisnickoIme.setBounds(332, 179, 123, 19);
		add(textFieldKorisnickoIme);
		textFieldKorisnickoIme.setColumns(10);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(332, 227, 123, 19);
		add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		passwordFieldLozinka = new JPasswordField();
		passwordFieldLozinka.setBounds(332, 274, 123, 19);
		add(passwordFieldLozinka);
		
		JButton btnDodajBibliotekara = new JButton("DODAJ BIBLIOTEKARA");
		btnDodajBibliotekara.setBackground(Color.ORANGE);
		btnDodajBibliotekara.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (ispravanUnos()) {
					dodajBibliotekara();
				}
			}
		});
		btnDodajBibliotekara.setBounds(259, 339, 171, 25);
		add(btnDodajBibliotekara);

	}
	
	public boolean ispravanUnos() {		
		
		Warning warningDialog = new Warning("Pogrešan unos");
		
		if (!(textFieldIme.getText().trim().length() > 0)) {
			warningDialog.showMessage("Polje za ime mora biti popunjeno!");
			return false;
		}
		if (!(textFieldPrezime.getText().trim().length() > 0)) {
			warningDialog.showMessage("Polje za prezime mora biti popunjeno!");
			return false;
		}
		if (!(textFieldKorisnickoIme.getText().trim().length() > 0)) {
			warningDialog.showMessage("Polje za korisničko ime mora biti popunjeno!");
			return false;
		}
		if (!(textFieldEmail.getText().trim().length() > 0)) {
			warningDialog.showMessage("Polje za e-mail biti popunjeno!");
			return false;
		}
		String passString = new String(passwordFieldLozinka.getPassword());
		if ((!(passString.trim().length() > 0)) || (passwordFieldLozinka.getPassword().length < 8)) {
			warningDialog.showMessage("Polje za lozinku mora imati najmanje 8 karaktera!");
			return false;
		}
		
		return true;
	}
	
	public void dodajBibliotekara() {
		boolean success = false;
		
		this.con = connection.createConnection();
		
		String insertIntoKorisnik = "insert into Korisnik (ime_korisnika, prezime_korisnika, tip_korisnika, korisnicko_ime, e_mail, clan_od, password_hash, prijavljen) values (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStmtKorisnik;
		try {
			preparedStmtKorisnik = con.prepareStatement(insertIntoKorisnik);
			preparedStmtKorisnik.setString(1, textFieldIme.getText());
			preparedStmtKorisnik.setString(2, textFieldPrezime.getText());
			preparedStmtKorisnik.setString(3, "bibliotekar");
			preparedStmtKorisnik.setString(4, textFieldKorisnickoIme.getText());
			preparedStmtKorisnik.setString(5, textFieldEmail.getText()); 
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
			String currentDateTime = format.format(date);
			preparedStmtKorisnik.setString(6, currentDateTime);
			Registracija registracija = new Registracija();
			String passwordHashBibliotekar = "";
			try {
				passwordHashBibliotekar = registracija.hashWithSHA256(new String(passwordFieldLozinka.getPassword()));
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			preparedStmtKorisnik.setString(7, passwordHashBibliotekar);
			preparedStmtKorisnik.setString(8, "Ne");
		    // execute the preparedstatement
		    preparedStmtKorisnik.execute();
		    success = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			success = false;
			//e.printStackTrace();
		}
		if (success) {
			Warning successDialog = new Warning("Uspeh");
			successDialog.showMessage("Uspešno ste dodali bibliotekara!");
		}
		else {
			Warning successDialog = new Warning("Greška");
			successDialog.showMessage("Došlo je do greške pri dodavanju bibliotekara!");
		}
		connection.closeConnection();
	}
}
