package library_package;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FrameRezervacijaBibliotekar extends FrameRezervacijaKorisnik{
	
	private MySQLConnection connection = new MySQLConnection();
	private Connection con = null;
	
	private JTextField textFieldImePrezimeBrIndeksa;
	
	private String idStudenta;
	private String idDela;
	private String brojPrimjeraka;
	
	public FrameRezervacijaBibliotekar(UserWindow userWindow, String idRezervacije) {
		super(idRezervacije);
		
		JLabel lblStudent = new JLabel("Student:");
		lblStudent.setHorizontalAlignment(SwingConstants.TRAILING);
		lblStudent.setBounds(120, 183, 123, 15);
		super.getContentPanel().add(lblStudent);
		
		textFieldImePrezimeBrIndeksa = new JTextField();
		textFieldImePrezimeBrIndeksa.setEditable(false);
		textFieldImePrezimeBrIndeksa.setBounds(247, 179, 196, 19);
		super.getContentPanel().add(textFieldImePrezimeBrIndeksa);
		textFieldImePrezimeBrIndeksa.setColumns(10);
		textFieldImePrezimeBrIndeksa.setText(super.imeStudenta()+" "+super.prezimeStudenta()+"  "+super.brIndeksaStudenta());
		
		
		idStudenta = super.idStudenta();
		idDela = super.idDela();
		brojPrimjeraka = super.brojRezervisanih();
		
		JButton btnIznajmi = new JButton("IZNAJMI");
		btnIznajmi.setBackground(Color.ORANGE);
		btnIznajmi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dodajPozajmicu(idStudenta, idDela, brojPrimjeraka);
				AzuriranjeDela azuriranjeDela = new AzuriranjeDela();
				azuriranjeDela.azurirajRaspolozivoStanje(brojPrimjeraka, idDela, "oduzmi");
				FunkcijeRezervacija funkcijeRezervacija = new FunkcijeRezervacija();
				funkcijeRezervacija.obrisiRezervaciju(userWindow, idRezervacije);
				closeFrameRezervacija();
			}
		});
		btnIznajmi.setBounds(198, 226, 98, 25);
		super.getContentPanel().add(btnIznajmi);
		
		JButton btnZatvori = new JButton("ZATVORI");
		btnZatvori.setBackground(Color.ORANGE);
		btnZatvori.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				closeFrameRezervacija();
			}
		});
		btnZatvori.setBounds(198, 273, 98, 25);
		super.getContentPanel().add(btnZatvori);
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

}
