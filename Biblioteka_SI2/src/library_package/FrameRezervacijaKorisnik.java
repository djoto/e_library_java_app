package library_package;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class FrameRezervacijaKorisnik extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldIDRez;
	private JTextField textFieldIDDela;
	private JTextField textFieldNazivDela;
	private JTextField textFieldVremeRez;
	private JTextField textFieldBrRezervisanih;
	
	private String idStudenta;
	private String imeStudenta;
	private String prezimeStudenta;
	private String brIndeksaStudenta;
	
	private MySQLConnection connection = new MySQLConnection();
	private Connection con = null;

	/**
	 * Create the frame.
	 */
	public FrameRezervacijaKorisnik(String idRezervacije) {
		setResizable(false);
		ImageIcon img = new ImageIcon("library_icon.png");
		setIconImage(img.getImage());
		setTitle("Rezervacija");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 120, 503, 362);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblIDRezervacije = new JLabel("ID rezervacije:");
		lblIDRezervacije.setHorizontalAlignment(SwingConstants.TRAILING);
		lblIDRezervacije.setBounds(120, 46, 123, 15);
		contentPane.add(lblIDRezervacije);
		
		JLabel lblIDDela = new JLabel("ID dela:");
		lblIDDela.setHorizontalAlignment(SwingConstants.TRAILING);
		lblIDDela.setBounds(120, 73, 123, 15);
		contentPane.add(lblIDDela);
		
		JLabel lblNazivDela = new JLabel("Naziv dela");
		lblNazivDela.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNazivDela.setBounds(120, 100, 123, 15);
		contentPane.add(lblNazivDela);
		
		JLabel lblVremeRezervisanja = new JLabel("Vreme rezervisanja:");
		lblVremeRezervisanja.setHorizontalAlignment(SwingConstants.TRAILING);
		lblVremeRezervisanja.setBounds(120, 127, 123, 15);
		contentPane.add(lblVremeRezervisanja);
		
		JLabel lblBrRezervisanih = new JLabel("Broj rezervisanih:");
		lblBrRezervisanih.setHorizontalAlignment(SwingConstants.TRAILING);
		lblBrRezervisanih.setBounds(120, 155, 123, 15);
		contentPane.add(lblBrRezervisanih);
		
		JLabel lblPodaciORezervaciji = new JLabel("Podaci o rezervaciji:");
		lblPodaciORezervaciji.setFont(new Font("Dialog", Font.BOLD, 14));
		lblPodaciORezervaciji.setHorizontalAlignment(SwingConstants.CENTER);
		lblPodaciORezervaciji.setBounds(12, 6, 479, 28);
		contentPane.add(lblPodaciORezervaciji);
		
		textFieldIDRez = new JTextField();
		textFieldIDRez.setEditable(false);
		textFieldIDRez.setBounds(247, 42, 54, 19);
		contentPane.add(textFieldIDRez);
		textFieldIDRez.setColumns(10);
		
		textFieldIDDela = new JTextField();
		textFieldIDDela.setEditable(false);
		textFieldIDDela.setBounds(247, 69, 54, 19);
		contentPane.add(textFieldIDDela);
		textFieldIDDela.setColumns(10);
		
		textFieldNazivDela = new JTextField();
		textFieldNazivDela.setEditable(false);
		textFieldNazivDela.setBounds(247, 96, 196, 19);
		contentPane.add(textFieldNazivDela);
		textFieldNazivDela.setColumns(10);
		
		textFieldVremeRez = new JTextField();
		textFieldVremeRez.setEditable(false);
		textFieldVremeRez.setBounds(247, 123, 151, 19);
		contentPane.add(textFieldVremeRez);
		textFieldVremeRez.setColumns(10);
		
		textFieldBrRezervisanih = new JTextField();
		textFieldBrRezervisanih.setEditable(false);
		textFieldBrRezervisanih.setBounds(247, 151, 54, 19);
		contentPane.add(textFieldBrRezervisanih);
		textFieldBrRezervisanih.setColumns(10);
		

		preuzmiPodatkeORezervaciji(idRezervacije);
			
		
	}
	
	
	public void preuzmiPodatkeORezervaciji(String idRezervacije) {
		
		this.con = connection.createConnection();
		
		String query = "select r.id_rezervacije, r.id_dela, d.naziv_dela, r.vreme_rezervisanja, r.broj_rezervisanih, k.id_korisnika, k.ime_korisnika, k.prezime_korisnika, k.br_indeksa from Rezervacija r, AutorskoDelo d, Korisnik k where r.id_dela=d.id_dela and r.id_studenta=k.id_korisnika and r.id_rezervacije="+idRezervacije;
		
	    try (Statement stmt = this.con.createStatement()) {
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	       		textFieldIDRez.setText(String.valueOf(rs.getInt("id_rezervacije")));
	       		textFieldIDDela.setText(String.valueOf(rs.getInt("id_dela")));
	       		textFieldNazivDela.setText(rs.getString("naziv_dela"));
	       		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	       		textFieldVremeRez.setText(dateFormat.format(rs.getDate("vreme_rezervisanja"))+" "+rs.getTime("vreme_rezervisanja"));
	       		//System.out.println(dateFormat.format(rs.getDate("vreme_rezervisanja")));
	       		//System.out.println(rs.getTime("vreme_rezervisanja"));
	       		textFieldBrRezervisanih.setText(String.valueOf(rs.getInt("broj_rezervisanih")));
	       		idStudenta = String.valueOf(rs.getInt("id_korisnika"));
	       		imeStudenta = rs.getString("ime_korisnika");
	       		prezimeStudenta = rs.getString("prezime_korisnika");
	       		brIndeksaStudenta = rs.getString("br_indeksa");
	        }
	     } catch (SQLException e) {
				Warning errorDialog = new Warning("Greška");
				errorDialog.showMessage(e.getMessage());
	     }
		
		connection.closeConnection();
		
	}
	
	
	public String idStudenta() {
		return idStudenta;
	}
	public String imeStudenta() {
		return imeStudenta;
	}
	public String prezimeStudenta() {
		return prezimeStudenta;
	}
	public String brIndeksaStudenta() {
		return brIndeksaStudenta;
	}
	public String idDela() {
		return textFieldIDDela.getText();
	}
	public String brojRezervisanih() {
		return textFieldBrRezervisanih.getText(); 
	}
	
	
	public void closeFrameRezervacija() {
		this.dispose();
	}
	
	public String preuzmiBrojRezervisanih() {
		return textFieldBrRezervisanih.getText();
	}
	
	public String preuzmiIdDela() {
		return textFieldIDDela.getText();
	}
	
	
	public JPanel getContentPanel() {
		return contentPane;
	}

}
