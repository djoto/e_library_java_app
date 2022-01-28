package library_package;

import java.awt.EventQueue;

import javax.swing.JFrame;
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
import javax.swing.UIManager;
import javax.swing.JPasswordField;
import java.awt.Color;

public class Prijava {

	private JFrame frmBibliotekaPrijava;
	private JTextField textFieldImePrijava;
	private JPasswordField passwordFieldPrijava;
	
	private MySQLConnection connection = new MySQLConnection();
	private Connection con = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Prijava window = new Prijava();
					window.frmBibliotekaPrijava.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Prijava() {
		initialize();
	}

	public JFrame getFrame() {
		return frmBibliotekaPrijava;
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmBibliotekaPrijava = new JFrame();
		frmBibliotekaPrijava.setTitle("Biblioteka - Prijava");
		frmBibliotekaPrijava.getContentPane().setBackground(new Color(255, 255, 153));
		frmBibliotekaPrijava.setBounds(420, 130, 420, 377);
		frmBibliotekaPrijava.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBibliotekaPrijava.getContentPane().setLayout(null);
		ImageIcon img = new ImageIcon("library_icon.png");
		frmBibliotekaPrijava.setIconImage(img.getImage());
	
		
		JLabel lblImePrijava = new JLabel("KORISNIČKO IME:");
		lblImePrijava.setBounds(78, 66, 120, 19);
		frmBibliotekaPrijava.getContentPane().add(lblImePrijava);
		
		textFieldImePrijava = new JTextField();
		textFieldImePrijava.setBounds(216, 63, 129, 22);
		frmBibliotekaPrijava.getContentPane().add(textFieldImePrijava);
		textFieldImePrijava.setColumns(10);
		
		JLabel lblLozinkaPrijava = new JLabel("LOZINKA:");
		lblLozinkaPrijava.setBounds(78, 131, 120, 19);
		frmBibliotekaPrijava.getContentPane().add(lblLozinkaPrijava);
		
		passwordFieldPrijava = new JPasswordField();
		passwordFieldPrijava.setBounds(216, 128, 129, 22);
		frmBibliotekaPrijava.getContentPane().add(passwordFieldPrijava);
		
		JButton btnPrijava = new JButton("PRIJAVA");
		btnPrijava.setBackground(Color.ORANGE);
		btnPrijava.setBounds(138, 209, 129, 25);
		btnPrijava.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean success = prijavaUBiblioteku();
				if (success) {
					getFrame().dispose();
				}
				
			}
		});
		frmBibliotekaPrijava.getContentPane().add(btnPrijava);
		
		JButton btnRegistracija = new JButton("REGISTRACIJA");
		btnRegistracija.setBackground(Color.ORANGE);
		btnRegistracija.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Registracija windowRegistracija = new Registracija();
				windowRegistracija.setVisible(true);
				getFrame().dispose();
			}
		});
		btnRegistracija.setBounds(138, 265, 129, 25);
		frmBibliotekaPrijava.getContentPane().add(btnRegistracija);
		
	}
	
	public boolean prijavaUBiblioteku() {
		String korisnickoIme = textFieldImePrijava.getText();
		String passString = new String(passwordFieldPrijava.getPassword());
		String passHash = null;
		try {
			passHash = hashWithSHA256(passString);
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Integer idKorisnika = null;
		String tipKorisnika = null;
		
		boolean success = false;
		
		this.con = connection.createConnection();
	
		String query = "select id_korisnika, tip_korisnika from Korisnik where korisnicko_ime='"+korisnickoIme+"' and password_hash='"+passHash+"'";
		
	    try (Statement stmt = this.con.createStatement()) {
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	idKorisnika = rs.getInt("id_korisnika");
	        	tipKorisnika = rs.getString("tip_korisnika");
	        	success = true;
	        }
	     } catch (SQLException e) {
	    	  Warning w = new Warning("Upozorenje");
	    	  w.showMessage(e.getMessage());
	     }
	    
		connection.closeConnection();
		
		if (success){
			podesiOznakuPrijavljen(idKorisnika, "Da");
			WindowFactoryPrijava windowFactoryPrijava = new WindowFactoryPrijava(); 
			UserWindow userWindow = windowFactoryPrijava.getWindowToShow(idKorisnika, tipKorisnika);
			userWindow.getWindowFrame().setVisible(true);
		}
		else {
	    	  Warning w = new Warning("Upozorenje");
	    	  w.showMessage("Neuspešna prijava!");
		}
		
		return success;
		
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
	
	
	public void podesiOznakuPrijavljen(Integer idKorisnika, String DaNe) {
		
		this.con = connection.createConnection();
			
		String query = "update Korisnik set prijavljen='"+DaNe+"' where id_korisnika="+idKorisnika;
			
		PreparedStatement preparedStatement;
		try {
			preparedStatement = this.con.prepareStatement(query);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			Warning errDialog = new Warning("Greška");
			errDialog.showMessage("Došlo je do greške!");
		}
		
		connection.closeConnection();
	}
	
}