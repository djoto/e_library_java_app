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
import javax.swing.JFrame;

public class FramePozajmicaBibliotekar extends FramePozajmicaKorisnik{
	
	private MySQLConnection connection = new MySQLConnection();
	private Connection con = null;
	
	public FramePozajmicaBibliotekar(UserWindow userWindow, String idPozajmice) {
		super(idPozajmice);
		
		super.setBounds(380, 120, 532, 460);
		
		int idPozajmljenogDela = super.idDela();
		int brojPozajmljenih = super.brojPozajmljenih();
		
		
		JButton btnVrati = new JButton("VRATI");
		btnVrati.setBackground(Color.ORANGE);
		btnVrati.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AzuriranjeDela azuriranjeDela = new AzuriranjeDela();
				azuriranjeDela.azurirajRaspolozivoStanje(String.valueOf(brojPozajmljenih), String.valueOf(idPozajmljenogDela), "dodaj");
				podesiDatumVraceno(idPozajmice);
				getFrameBibliotekar().dispose();
			}
		});
		btnVrati.setBounds(217, 330, 98, 25);
		super.getContentPanel().add(btnVrati);
		
		
		
		JButton btnZatvori = new JButton("ZATVORI");
		btnZatvori.setBackground(Color.ORANGE);
		btnZatvori.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getFrameBibliotekar().dispose();
			}
		});
		btnZatvori.setBounds(217, 370, 98, 25);
		super.getContentPanel().add(btnZatvori);
		
	}
	
	public void podesiDatumVraceno(String idPozajmice) {
		this.con = connection.createConnection();
		
		String query = null;

		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
		String currentDateTime = format.format(date);

		query = "update Pozajmica set vraceno='Da', datum_vracanja=? where id_pozajmice="+idPozajmice;
		
		PreparedStatement preparedStatement;
		try {
			preparedStatement = this.con.prepareStatement(query);
			preparedStatement.setString(1, currentDateTime);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			Warning errDialog = new Warning("Greška");
			errDialog.showMessage("Došlo je do greške pri ažuriranju pozajmice!");
		}
		
		connection.closeConnection();
	}
	
	public JFrame getFrameBibliotekar() {
		return super.getFrame();
	}

}
