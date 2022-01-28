package library_package;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner;
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

public class FrameIznajmiDelo extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldIdKorisnika;
	
	private MySQLConnection connection = new MySQLConnection();
	private Connection con = null;


	/**
	 * Create the frame.
	 */
	public FrameIznajmiDelo(FrameDeloKorisnik frameDeloKorisnik, String idDela) {
		setResizable(false);
		ImageIcon img = new ImageIcon("library_icon.png");
		setIconImage(img.getImage());
		setTitle("Iznajmljivanje dela");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(480, 180, 410, 270);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUnesiteIdKorisnika = new JLabel("Unesite ID korisnika kome želite da iznajmite delo:");
		lblUnesiteIdKorisnika.setHorizontalAlignment(SwingConstants.CENTER);
		lblUnesiteIdKorisnika.setBounds(12, 22, 386, 15);
		contentPane.add(lblUnesiteIdKorisnika);
		
		textFieldIdKorisnika = new JTextField();
		textFieldIdKorisnika.setBounds(166, 49, 69, 19);
		contentPane.add(textFieldIdKorisnika);
		textFieldIdKorisnika.setColumns(10);
		
		JLabel lblUnesiteBrojPrimjeraka = new JLabel("Unesite broj primjeraka za iznajmljivanje:");
		lblUnesiteBrojPrimjeraka.setHorizontalAlignment(SwingConstants.CENTER);
		lblUnesiteBrojPrimjeraka.setBounds(12, 98, 386, 15);
		contentPane.add(lblUnesiteBrojPrimjeraka);
		
		
		Integer value = 1;
		Integer min = 1;
		Integer max = 10;
		Integer step = 1;
		SpinnerNumberModel modelSpinner = new SpinnerNumberModel(value, min, max, step);		
		JSpinner spinnerBrZaIznajmiti = new JSpinner(modelSpinner);
		((JSpinner.DefaultEditor) spinnerBrZaIznajmiti.getEditor()).getTextField().setEditable(false);
		spinnerBrZaIznajmiti.setBounds(174, 130, 51, 20);
		contentPane.add(spinnerBrZaIznajmiti);
		
		JButton btnIznajmi = new JButton("IZNAJMI");
		btnIznajmi.setBackground(Color.ORANGE);
		btnIznajmi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if ((!textFieldIdKorisnika.getText().equals("")) || postojiIdStudenta(textFieldIdKorisnika.getText())) {
					AzuriranjeDela azuriranjeDela = new AzuriranjeDela();
					azuriranjeDela.azurirajRaspolozivoStanje(String.valueOf(modelSpinner.getValue()), idDela, "oduzmi");
					dodajPozajmicu(textFieldIdKorisnika.getText(), idDela, String.valueOf(modelSpinner.getValue()));
					frameDeloKorisnik.podesiRaspolozivoUkupnoStanje(idDela);
					getFrame().dispose();
				}
				else {
					Warning w = new Warning("Upozorenje");
					w.showMessage("Ne postoji student sa unesenim identifikatorom");
				}
			}
		});
		btnIznajmi.setBounds(218, 185, 98, 25);
		contentPane.add(btnIznajmi);
		
		JButton btnZatvori = new JButton("ZATVORI");
		btnZatvori.setBackground(Color.ORANGE);
		btnZatvori.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getFrame().dispose();
			}
		});
		btnZatvori.setBounds(89, 185, 98, 25);
		contentPane.add(btnZatvori);
	}
	
	public JFrame getFrame() {
		return this;
	}
	
	public void dodajPozajmicu(String idStudenta, String idDela, String brojPrimjeraka) {
		boolean success = false;
		
		this.con = connection.createConnection();
		
		String insertIntoPozajmica = "insert into Pozajmica (id_studenta, id_dela, datum_uzimanja, broj_pozajmljenih, vraceno) values (?, ?, ?, ?, ?)";
		PreparedStatement preparedStmtPozajmica;
		try {
			preparedStmtPozajmica = con.prepareStatement(insertIntoPozajmica);
			preparedStmtPozajmica.setInt(1, Integer.valueOf(idStudenta));
			preparedStmtPozajmica.setInt(2, Integer.valueOf(idDela));
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
			String currentDateTime = format.format(date);
			preparedStmtPozajmica.setString(3, currentDateTime);
			preparedStmtPozajmica.setInt(4, Integer.valueOf(brojPrimjeraka));
			preparedStmtPozajmica.setString(5, "Ne");
		    // execute the preparedstatement
		    preparedStmtPozajmica.execute();
		    success = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			success = false;
			//e.printStackTrace();
		}
		
		if (success) {
			Warning successDialog = new Warning("Uspeh");
			successDialog.showMessage("Uspešno ste iznajmili delo!");
		}
		else {
			Warning successDialog = new Warning("Greška");
			successDialog.showMessage("Došlo je do greške pri iznajmljivanju!");
		}
		
		connection.closeConnection();
	}
	
	
	public boolean postojiIdStudenta(String idStudenta) {
		
		boolean exists = false;
		
		this.con = connection.createConnection();
		
		String query = null;
		
		query = "select * from Korisnik where tip_korisnika='student' and id_korisnika="+idStudenta;
		
		Statement stmt;
		try {
			stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery( query );
	        while (rs.next()) {
	        	exists = true;
	        	if (exists) {
	        		break;
	        	}
	        }
	        rs.close();
	        stmt.close();
		
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		connection.closeConnection();
		
		return exists;
	}

}
