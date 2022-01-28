package library_package;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;

public class FrameObavestenjeKorisnik extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldPrekoracenje;
	private JTextField txtIdPozajmice;
	private JTextField textFieldDatumUzimanja;
	private JTextField textFieldBrUzetih;
	private JTextField textFieldIdDela;
	private JTextField textFieldNazivDela;
	private JTextField textFieldTipDela;
	private JTextField textFieldRaspolozivoStanje;
	private JTextField textFieldIdStudenta;
	private JTextField textFieldImePrezime;
	private JTextField textFieldBrIndeksa;
	private JTextField textFieldEmail;
	
	private MySQLConnection connection = new MySQLConnection();
	private Connection con = null;

	/**
	 * Create the frame.
	 */
	public FrameObavestenjeKorisnik(UserWindow userWindow, String idObavestenja) {
		setResizable(false);
		setTitle("Obaveštenje");
		ImageIcon img = new ImageIcon("library_icon.png");
		setIconImage(img.getImage());
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(350, 120, 605, 452);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textFieldPrekoracenje = new JTextField();
		textFieldPrekoracenje.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldPrekoracenje.setEditable(false);
		textFieldPrekoracenje.setFont(new Font("Dialog", Font.PLAIN, 14));
		textFieldPrekoracenje.setBounds(158, 22, 275, 27);
		contentPane.add(textFieldPrekoracenje);
		textFieldPrekoracenje.setColumns(10);
		
		JLabel lblOPozajmici = new JLabel("O pozajmici:");
		lblOPozajmici.setHorizontalAlignment(SwingConstants.CENTER);
		lblOPozajmici.setBounds(39, 76, 247, 15);
		contentPane.add(lblOPozajmici);
		
		JLabel lblODelu = new JLabel("O delu:");
		lblODelu.setHorizontalAlignment(SwingConstants.CENTER);
		lblODelu.setBounds(183, 224, 237, 15);
		contentPane.add(lblODelu);
		
		JLabel lblOStudentu = new JLabel("O studentu:");
		lblOStudentu.setHorizontalAlignment(SwingConstants.CENTER);
		lblOStudentu.setBounds(315, 76, 237, 15);
		contentPane.add(lblOStudentu);
		
		JLabel lblIdPozajmice = new JLabel("ID pozajmice:");
		lblIdPozajmice.setHorizontalAlignment(SwingConstants.TRAILING);
		lblIdPozajmice.setBounds(39, 107, 132, 15);
		contentPane.add(lblIdPozajmice);
		
		JLabel lblDatumUzimanja = new JLabel("Datum uzimanja:");
		lblDatumUzimanja.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDatumUzimanja.setBounds(39, 134, 132, 15);
		contentPane.add(lblDatumUzimanja);
		
		JLabel lblBrojUzetihPrimeraka = new JLabel("Broj uzetih primeraka:");
		lblBrojUzetihPrimeraka.setHorizontalAlignment(SwingConstants.TRAILING);
		lblBrojUzetihPrimeraka.setBounds(39, 162, 132, 15);
		contentPane.add(lblBrojUzetihPrimeraka);
		
		txtIdPozajmice = new JTextField();
		txtIdPozajmice.setEditable(false);
		txtIdPozajmice.setBounds(174, 103, 55, 19);
		contentPane.add(txtIdPozajmice);
		txtIdPozajmice.setColumns(10);
		
		textFieldDatumUzimanja = new JTextField();
		textFieldDatumUzimanja.setEditable(false);
		textFieldDatumUzimanja.setBounds(174, 130, 89, 19);
		contentPane.add(textFieldDatumUzimanja);
		textFieldDatumUzimanja.setColumns(10);
		
		textFieldBrUzetih = new JTextField();
		textFieldBrUzetih.setEditable(false);
		textFieldBrUzetih.setBounds(174, 158, 55, 19);
		contentPane.add(textFieldBrUzetih);
		textFieldBrUzetih.setColumns(10);
		
		JLabel lblIdDela = new JLabel("ID dela:");
		lblIdDela.setHorizontalAlignment(SwingConstants.TRAILING);
		lblIdDela.setBounds(183, 255, 112, 15);
		contentPane.add(lblIdDela);
		
		JLabel lblNazivDela = new JLabel("Naziv dela:");
		lblNazivDela.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNazivDela.setBounds(183, 282, 112, 15);
		contentPane.add(lblNazivDela);
		
		JLabel lblTipDela = new JLabel("Tip dela:");
		lblTipDela.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTipDela.setBounds(183, 309, 112, 15);
		contentPane.add(lblTipDela);
		
		JLabel lblRaspolozivoStanje = new JLabel("Raspoloživo stanje:");
		lblRaspolozivoStanje.setHorizontalAlignment(SwingConstants.TRAILING);
		lblRaspolozivoStanje.setBounds(183, 336, 112, 15);
		contentPane.add(lblRaspolozivoStanje);
		
		textFieldIdDela = new JTextField();
		textFieldIdDela.setEditable(false);
		textFieldIdDela.setBounds(298, 251, 55, 19);
		contentPane.add(textFieldIdDela);
		textFieldIdDela.setColumns(10);
		
		textFieldNazivDela = new JTextField();
		textFieldNazivDela.setEditable(false);
		textFieldNazivDela.setBounds(298, 278, 146, 19);
		contentPane.add(textFieldNazivDela);
		textFieldNazivDela.setColumns(10);
		
		textFieldTipDela = new JTextField();
		textFieldTipDela.setEditable(false);
		textFieldTipDela.setBounds(298, 305, 146, 19);
		contentPane.add(textFieldTipDela);
		textFieldTipDela.setColumns(10);
		
		textFieldRaspolozivoStanje = new JTextField();
		textFieldRaspolozivoStanje.setEditable(false);
		textFieldRaspolozivoStanje.setBounds(298, 332, 55, 19);
		contentPane.add(textFieldRaspolozivoStanje);
		textFieldRaspolozivoStanje.setColumns(10);
		
		JLabel lblIdStudenta = new JLabel("ID studenta:");
		lblIdStudenta.setHorizontalAlignment(SwingConstants.TRAILING);
		lblIdStudenta.setBounds(328, 107, 89, 15);
		contentPane.add(lblIdStudenta);
		
		JLabel lblImePrezime = new JLabel("Ime i prezime:");
		lblImePrezime.setHorizontalAlignment(SwingConstants.TRAILING);
		lblImePrezime.setBounds(328, 134, 89, 15);
		contentPane.add(lblImePrezime);
		
		JLabel lblBrIndeksa = new JLabel("Broj indeksa:");
		lblBrIndeksa.setHorizontalAlignment(SwingConstants.TRAILING);
		lblBrIndeksa.setBounds(328, 162, 89, 15);
		contentPane.add(lblBrIndeksa);
		
		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setHorizontalAlignment(SwingConstants.TRAILING);
		lblEmail.setBounds(328, 189, 89, 15);
		contentPane.add(lblEmail);
		
		textFieldIdStudenta = new JTextField();
		textFieldIdStudenta.setEditable(false);
		textFieldIdStudenta.setBounds(419, 103, 55, 19);
		contentPane.add(textFieldIdStudenta);
		textFieldIdStudenta.setColumns(10);
		
		textFieldImePrezime = new JTextField();
		textFieldImePrezime.setEditable(false);
		textFieldImePrezime.setBounds(419, 130, 130, 19);
		contentPane.add(textFieldImePrezime);
		textFieldImePrezime.setColumns(10);
		
		textFieldBrIndeksa = new JTextField();
		textFieldBrIndeksa.setEditable(false);
		textFieldBrIndeksa.setBounds(419, 158, 89, 19);
		contentPane.add(textFieldBrIndeksa);
		textFieldBrIndeksa.setColumns(10);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setEditable(false);
		textFieldEmail.setBounds(419, 185, 130, 19);
		contentPane.add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		preuzmiPodatkeObavestenja(userWindow, idObavestenja);
		
		JButton btnZatvori = new JButton("ZATVORI");
		btnZatvori.setBackground(Color.ORANGE);
		btnZatvori.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getFrame().dispose();
			}
		});
		btnZatvori.setBounds(250, 378, 98, 25);
		contentPane.add(btnZatvori);
	}
	
	public JFrame getFrame() {
		return this;
	}
	
	public void preuzmiPodatkeObavestenja(UserWindow userWindow, String idObavestenja) {
		this.con = connection.createConnection();
		
		String query = "select concat(o.tip_obavestenja, ' ', '(ID=', o.id_obavestenja, ')') as NaslovTip, o.id_pozajmice, p.datum_uzimanja, p.broj_pozajmljenih, p.id_studenta, concat(k.ime_korisnika, ' ', k.prezime_korisnika) as ImePrezime, k.br_indeksa, k.e_mail, p.id_dela, d.naziv_dela, t.naziv_tipa_dela, d.trenutno_stanje from Obavestenje o, Pozajmica p, Korisnik k, AutorskoDelo d, TipDela t where o.id_pozajmice=p.id_pozajmice and p.id_studenta=k.id_korisnika and p.id_dela=d.id_dela and d.id_tipa=t.id_tipa and o.id_obavestenja="+idObavestenja;
		
		
	    try (Statement stmt = this.con.createStatement()) {
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	
	        	textFieldPrekoracenje.setText(rs.getString("NaslovTip"));
	        	txtIdPozajmice.setText(String.valueOf(rs.getInt("id_pozajmice")));
	        	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        	textFieldDatumUzimanja.setText(dateFormat.format(rs.getDate("datum_uzimanja")));
	        	textFieldBrUzetih.setText(String.valueOf(rs.getInt("broj_pozajmljenih")));
	        	textFieldIdDela.setText(String.valueOf(rs.getInt("id_dela")));
	        	textFieldNazivDela.setText(rs.getString("naziv_dela"));
	        	textFieldTipDela.setText(rs.getString("naziv_tipa_dela"));
	        	textFieldRaspolozivoStanje.setText(String.valueOf(rs.getInt("trenutno_stanje")));
	        	textFieldIdStudenta.setText(String.valueOf(rs.getInt("id_studenta")));
	        	textFieldImePrezime.setText(rs.getString("ImePrezime"));
	        	textFieldBrIndeksa.setText(rs.getString("br_indeksa"));
	        	textFieldEmail.setText(rs.getString("e_mail"));
	        	
	        }
	     } catch (SQLException e) {
	    	  Warning w = new Warning("Upozorenje");
	    	  w.showMessage(e.getMessage());
	     }
		
		
		connection.closeConnection();
	}
	
}
