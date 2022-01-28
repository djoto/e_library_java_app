package library_package;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Color;
import java.awt.event.ActionEvent;

public class FrameZahtevZaRegistraciju extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldIdZahteva;
	private JTextField textFieldImePrezime;
	private JTextField textFieldKorisnickoIme;
	private JTextField textFieldBrIndeksa;
	private JTextField textFieldEmail;
	
	private MySQLConnection connection = new MySQLConnection();
	private Connection con = null;
	
	private String imeStudenta;
	private String prezimeStudenta;
	private String passwordHashStudenta;
	
	/**
	 * Create the frame.
	 */
	public FrameZahtevZaRegistraciju(UserWindow userWindow, String idZahteva) {
		setResizable(false);
		ImageIcon img = new ImageIcon("library_icon.png");
		setIconImage(img.getImage());
		setTitle("Biblioteka - Zahtev za registraciju");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(420, 120, 450, 387);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblIdZahteva = new JLabel("ID zahteva:");
		lblIdZahteva.setHorizontalAlignment(SwingConstants.TRAILING);
		lblIdZahteva.setBounds(99, 28, 92, 15);
		contentPane.add(lblIdZahteva);
		
		JLabel lblImePrezime = new JLabel("Ime i prezime:");
		lblImePrezime.setHorizontalAlignment(SwingConstants.TRAILING);
		lblImePrezime.setBounds(99, 65, 92, 15);
		contentPane.add(lblImePrezime);
		
		JLabel lblKorisnickoIme = new JLabel("Korisničko ime:");
		lblKorisnickoIme.setHorizontalAlignment(SwingConstants.TRAILING);
		lblKorisnickoIme.setBounds(99, 104, 92, 15);
		contentPane.add(lblKorisnickoIme);
		
		JLabel lblBrIndeksa = new JLabel("Broj indeksa:");
		lblBrIndeksa.setHorizontalAlignment(SwingConstants.TRAILING);
		lblBrIndeksa.setBounds(99, 145, 92, 15);
		contentPane.add(lblBrIndeksa);
		
		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setHorizontalAlignment(SwingConstants.TRAILING);
		lblEmail.setBounds(99, 184, 92, 15);
		contentPane.add(lblEmail);
		
		textFieldIdZahteva = new JTextField();
		textFieldIdZahteva.setEditable(false);
		textFieldIdZahteva.setBounds(193, 24, 58, 19);
		contentPane.add(textFieldIdZahteva);
		textFieldIdZahteva.setColumns(10);
		
		textFieldImePrezime = new JTextField();
		textFieldImePrezime.setEditable(false);
		textFieldImePrezime.setBounds(193, 61, 152, 19);
		contentPane.add(textFieldImePrezime);
		textFieldImePrezime.setColumns(10);
		
		textFieldKorisnickoIme = new JTextField();
		textFieldKorisnickoIme.setEditable(false);
		textFieldKorisnickoIme.setBounds(193, 100, 152, 19);
		contentPane.add(textFieldKorisnickoIme);
		textFieldKorisnickoIme.setColumns(10);
		
		textFieldBrIndeksa = new JTextField();
		textFieldBrIndeksa.setEditable(false);
		textFieldBrIndeksa.setBounds(193, 141, 104, 19);
		contentPane.add(textFieldBrIndeksa);
		textFieldBrIndeksa.setColumns(10);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setEditable(false);
		textFieldEmail.setBounds(193, 180, 152, 19);
		contentPane.add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		preuzmiPodatkeOZahtevu(idZahteva);
		
		JButton btnOdobriRegistraciju = new JButton("ODOBRI REGISTRACIJU");
		btnOdobriRegistraciju.setBackground(Color.ORANGE);
		btnOdobriRegistraciju.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dodajKorisnika();
				obrisiZahtev(idZahteva);
				PanelZahteviZaRegistraciju panelZahteviZaRegistraciju = new PanelZahteviZaRegistraciju(userWindow);
				userWindow.getPanelManager().show(panelZahteviZaRegistraciju);
				getFrame().dispose();
			}
		});
		btnOdobriRegistraciju.setBounds(133, 236, 186, 25);
		contentPane.add(btnOdobriRegistraciju);
		
		JButton btnZatvori = new JButton("ZATVORI");
		btnZatvori.setBackground(Color.ORANGE);
		btnZatvori.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getFrame().dispose();
			}
		});
		btnZatvori.setBounds(176, 290, 98, 25);
		contentPane.add(btnZatvori);
	}
	
	public JFrame getFrame() {
		return this;
	}
	
	
	public void preuzmiPodatkeOZahtevu(String idZahteva) {
		
		this.con = connection.createConnection();
		
		String query = "select id_zahteva, ime_studenta, prezime_studenta, korisnicko_ime_studenta, password_hash_studenta, br_indeksa_studenta, e_mail_studenta from ZahtevZaRegistraciju where id_zahteva="+idZahteva;
		
	    try (Statement stmt = this.con.createStatement()) {
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
		    	textFieldIdZahteva.setText(String.valueOf(rs.getInt("id_zahteva")));
		    	imeStudenta = rs.getString("ime_studenta");
		    	prezimeStudenta = rs.getString("prezime_studenta");
		    	textFieldImePrezime.setText(imeStudenta+" "+prezimeStudenta);
		    	textFieldKorisnickoIme.setText(rs.getString("korisnicko_ime_studenta"));
		    	passwordHashStudenta = rs.getString("password_hash_studenta");
		    	textFieldBrIndeksa.setText(rs.getString("br_indeksa_studenta"));
		    	textFieldEmail.setText(rs.getString("e_mail_studenta"));
	        }
	     } catch (SQLException e) {
				Warning errorDialog = new Warning("Greška");
				errorDialog.showMessage(e.getMessage());
	     }
		
		connection.closeConnection();
		
	}
	
	
	public void dodajKorisnika() {
		
		boolean success = false;
		
		this.con = connection.createConnection();
		
		String insertIntoKorisnik = "insert into Korisnik (ime_korisnika, prezime_korisnika, tip_korisnika, korisnicko_ime, br_indeksa, e_mail, clan_od, password_hash, prijavljen) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStmtKorisnik;
		try {
			preparedStmtKorisnik = con.prepareStatement(insertIntoKorisnik);
			preparedStmtKorisnik.setString(1, imeStudenta);
			preparedStmtKorisnik.setString(2, prezimeStudenta);
			preparedStmtKorisnik.setString(3, "student");
			preparedStmtKorisnik.setString(4, textFieldKorisnickoIme.getText());
			preparedStmtKorisnik.setString(5, textFieldBrIndeksa.getText());
			preparedStmtKorisnik.setString(6, textFieldEmail.getText()); 
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
			String currentDateTime = format.format(date);
			preparedStmtKorisnik.setString(7, currentDateTime);
			preparedStmtKorisnik.setString(8, passwordHashStudenta);
			preparedStmtKorisnik.setString(9, "Ne");
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
			successDialog.showMessage("Uspešno ste dodali korisnika!");
		}
		else {
			Warning successDialog = new Warning("Greška");
			successDialog.showMessage("Došlo je do greške pri dodavanju korisnika!");
		}
		connection.closeConnection();
		
	}
	
	
	public void obrisiZahtev(String idZahteva) {
		this.con = connection.createConnection();
		
		String updateStr = "delete from ZahtevZaRegistraciju where id_zahteva="+idZahteva;
		
		PreparedStatement preparedStatement;
		try {
			preparedStatement = con.prepareStatement(updateStr);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			Warning errorDialog = new Warning("Greška");
			errorDialog.showMessage(e.getMessage());
		}
		
		connection.closeConnection();
		
	}

}
