package library_package;


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

import java.awt.Color;
//import javax.swing.JButton;
//import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
//import java.awt.event.ActionEvent;

public class FramePozajmicaKorisnik extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldIdPozajmice;
	private JTextField textFieldNazivDela;
	private JTextField textFieldImePrezime;
	private JTextField textFieldBrIndeksa;
	private JTextField textFieldDatumUzimanja;
	private JTextField textFieldDatumVracanja;
	private JTextField textFieldBrPozajmljenih;
	private JTextField textFieldVraceno;
	
	private int idDela;

	
	private MySQLConnection connection = new MySQLConnection();
	private Connection con = null;

	/**
	 * Create the frame.
	 */
	public FramePozajmicaKorisnik(String idPozajmice) {
		setResizable(false);
		setTitle("Pozajmica");
		ImageIcon img = new ImageIcon("library_icon.png");
		setIconImage(img.getImage());
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(380, 120, 532, 420);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblIdPozajmice = new JLabel("ID pozajmice:");
		lblIdPozajmice.setHorizontalAlignment(SwingConstants.TRAILING);
		lblIdPozajmice.setBounds(127, 26, 133, 15);
		contentPane.add(lblIdPozajmice);
		
		JLabel lblNazivDela = new JLabel("Naziv dela:");
		lblNazivDela.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNazivDela.setBounds(127, 62, 133, 15);
		contentPane.add(lblNazivDela);
		
		JLabel lblImePrezime = new JLabel("Ime i prezime studenta:");
		lblImePrezime.setHorizontalAlignment(SwingConstants.TRAILING);
		lblImePrezime.setBounds(127, 99, 133, 15);
		contentPane.add(lblImePrezime);
		
		JLabel lblBrIndeksa = new JLabel("Broj indeksa studenta:");
		lblBrIndeksa.setHorizontalAlignment(SwingConstants.TRAILING);
		lblBrIndeksa.setBounds(127, 137, 133, 15);
		contentPane.add(lblBrIndeksa);
		
		JLabel lblDatumUzimanja = new JLabel("Datum uzimanja:");
		lblDatumUzimanja.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDatumUzimanja.setBounds(127, 174, 133, 15);
		contentPane.add(lblDatumUzimanja);
		
		JLabel lblDatumVracanja = new JLabel("Datum vraćanja:");
		lblDatumVracanja.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDatumVracanja.setBounds(127, 211, 133, 15);
		contentPane.add(lblDatumVracanja);
		
		JLabel lblBrPozajmljenih = new JLabel("Broj pozajmljenih:");
		lblBrPozajmljenih.setHorizontalAlignment(SwingConstants.TRAILING);
		lblBrPozajmljenih.setBounds(127, 249, 133, 15);
		contentPane.add(lblBrPozajmljenih);
		
		JLabel lblVraceno = new JLabel("Vraćeno:");
		lblVraceno.setHorizontalAlignment(SwingConstants.TRAILING);
		lblVraceno.setBounds(127, 287, 133, 15);
		contentPane.add(lblVraceno);
		
		textFieldIdPozajmice = new JTextField();
		textFieldIdPozajmice.setEditable(false);
		textFieldIdPozajmice.setBounds(262, 22, 49, 19);
		contentPane.add(textFieldIdPozajmice);
		textFieldIdPozajmice.setColumns(10);
		
		textFieldNazivDela = new JTextField();
		textFieldNazivDela.setEditable(false);
		textFieldNazivDela.setBounds(262, 58, 171, 19);
		contentPane.add(textFieldNazivDela);
		textFieldNazivDela.setColumns(10);
		
		textFieldImePrezime = new JTextField();
		textFieldImePrezime.setEditable(false);
		textFieldImePrezime.setBounds(262, 95, 171, 19);
		contentPane.add(textFieldImePrezime);
		textFieldImePrezime.setColumns(10);
		
		textFieldBrIndeksa = new JTextField();
		textFieldBrIndeksa.setEditable(false);
		textFieldBrIndeksa.setBounds(262, 133, 92, 19);
		contentPane.add(textFieldBrIndeksa);
		textFieldBrIndeksa.setColumns(10);
		
		textFieldDatumUzimanja = new JTextField();
		textFieldDatumUzimanja.setEditable(false);
		textFieldDatumUzimanja.setBounds(262, 170, 92, 19);
		contentPane.add(textFieldDatumUzimanja);
		textFieldDatumUzimanja.setColumns(10);
		
		textFieldDatumVracanja = new JTextField();
		textFieldDatumVracanja.setEditable(false);
		textFieldDatumVracanja.setBounds(262, 207, 92, 19);
		contentPane.add(textFieldDatumVracanja);
		textFieldDatumVracanja.setColumns(10);
		
		textFieldBrPozajmljenih = new JTextField();
		textFieldBrPozajmljenih.setEditable(false);
		textFieldBrPozajmljenih.setBounds(262, 245, 49, 19);
		contentPane.add(textFieldBrPozajmljenih);
		textFieldBrPozajmljenih.setColumns(10);
		
		textFieldVraceno = new JTextField();
		textFieldVraceno.setEditable(false);
		textFieldVraceno.setBounds(262, 283, 49, 19);
		contentPane.add(textFieldVraceno);
		textFieldVraceno.setColumns(10);
		
		preuzmiPodatkeOPozajmici(idPozajmice);

	}
	
	public Integer brojPozajmljenih() {
		return Integer.valueOf(textFieldBrPozajmljenih.getText());
	}
	
	public JPanel getContentPanel() {
		return contentPane;
	}
	
	public JFrame getFrame() {
		return this;
	}
	
	public int idDela() {
		return idDela;
	}
	
	public void preuzmiPodatkeOPozajmici(String idPozajmice) {
		
		this.con = connection.createConnection();
		
		String query = "select p.id_pozajmice, d.id_dela, d.naziv_dela, concat(k.ime_korisnika, ' ', k.prezime_korisnika) as ImePrezime, k.br_indeksa, p.datum_uzimanja, p.datum_vracanja, p.broj_pozajmljenih, p.vraceno from Pozajmica p, Korisnik k, AutorskoDelo d where p.id_dela=d.id_dela and p.id_studenta=k.id_korisnika and p.id_pozajmice="+idPozajmice;
		
	    try (Statement stmt = this.con.createStatement()) {
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	textFieldIdPozajmice.setText(String.valueOf(rs.getInt("id_pozajmice")));
	        	idDela = rs.getInt("id_dela");
	        	textFieldNazivDela.setText(rs.getString("naziv_dela"));
	        	textFieldImePrezime.setText(rs.getString("ImePrezime"));
	        	textFieldBrIndeksa.setText(rs.getString("br_indeksa"));
	        	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        	textFieldDatumUzimanja.setText(dateFormat.format(rs.getDate("datum_uzimanja")));
	        	if (rs.getString("datum_vracanja")==null) {
	        		textFieldDatumVracanja.setText("Nije vraćeno");
	        	}
	        	else {
	        		textFieldDatumVracanja.setText(dateFormat.format(rs.getDate("datum_vracanja")));
	        	}
	        	textFieldBrPozajmljenih.setText(String.valueOf(rs.getInt("broj_pozajmljenih")));
	        	textFieldVraceno.setText(rs.getString("vraceno"));
	        }
	     } catch (SQLException e) {
				Warning errorDialog = new Warning("Greška");
				errorDialog.showMessage(e.getMessage());
	     }
		
		connection.closeConnection();
		
	}
}
