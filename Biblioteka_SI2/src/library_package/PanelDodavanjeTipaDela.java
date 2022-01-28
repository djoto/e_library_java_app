package library_package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
//import java.text.SimpleDateFormat;
//import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelDodavanjeTipaDela extends JPanel {

	private MySQLConnection connection = new MySQLConnection();
	private Connection con = null;
	private JTextField textFieldNazivTipaDela;
	
	public PanelDodavanjeTipaDela(UserWindow userWindow) {
		setBackground(new Color(255, 255, 153));
		setLayout(null);
		
		JLabel lblUnesiteNazivTipa = new JLabel("Unesite naziv tipa dela:");
		lblUnesiteNazivTipa.setHorizontalAlignment(SwingConstants.CENTER);
		lblUnesiteNazivTipa.setBounds(12, 132, 678, 15);
		add(lblUnesiteNazivTipa);
		
		JLabel lblDodavanjeNovogTipa = new JLabel("Dodavanje novog tipa dela");
		lblDodavanjeNovogTipa.setHorizontalAlignment(SwingConstants.CENTER);
		lblDodavanjeNovogTipa.setFont(new Font("Dialog", Font.ITALIC, 15));
		lblDodavanjeNovogTipa.setBounds(12, 46, 678, 15);
		add(lblDodavanjeNovogTipa);
		
		textFieldNazivTipaDela = new JTextField();
		textFieldNazivTipaDela.setFont(new Font("Dialog", Font.PLAIN, 14));
		textFieldNazivTipaDela.setBounds(255, 165, 194, 25);
		add(textFieldNazivTipaDela);
		textFieldNazivTipaDela.setColumns(10);
		
		JButton btnDodaj = new JButton("DODAJ");
		btnDodaj.setBackground(Color.ORANGE);
		btnDodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (textFieldNazivTipaDela.getText().trim().length() > 0) {
					dodajTipDela(textFieldNazivTipaDela.getText());
				}
				else {
					Warning warningDialog = new Warning("Pogrešan unos");
					warningDialog.showMessage("Polje za naziv tipa dela mora biti popunjeno!");
				}
			}
		});
		btnDodaj.setBounds(305, 229, 98, 25);
		add(btnDodaj);
		
	}

	public void dodajTipDela(String nazivTipaDela) {
		this.con = connection.createConnection();
		
		boolean success = false;
		String insertIntoTipDela = "insert into TipDela (naziv_tipa_dela) values (?)";
		PreparedStatement preparedStmtTipDela;
		try {
			preparedStmtTipDela = con.prepareStatement(insertIntoTipDela);
			preparedStmtTipDela.setString(1, nazivTipaDela);			
			// execute the preparedstatement
		    preparedStmtTipDela.execute();
		    success = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			success = false;
			//e.printStackTrace();
		}
		if (success) {
			Warning successDialog = new Warning("Uspeh");
			successDialog.showMessage("Uspešno ste dodali novi tip dela!");
		}
		else {
			Warning successDialog = new Warning("Greška");
			successDialog.showMessage("Došlo je do greške pri dodavanju tipa dela!");
		}
		
		connection.closeConnection();
	}
	
}
