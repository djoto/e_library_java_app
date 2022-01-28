package library_package;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class FrameDeloAdmin extends FrameDeloKorisnik{
	
	private MySQLConnection connection = new MySQLConnection();
	private Connection con = null;
	
	public FrameDeloAdmin(UserWindow userWindow, String idDela) {
		super(idDela);
		
		FrameDeloAdmin frameKorisnik = this;
		
	    JButton btnDodajNaStanje = new JButton("DODAJ NA STANJE");
	    btnDodajNaStanje.setBackground(Color.ORANGE);
        btnDodajNaStanje.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		FrameDodajPrimerakDela frameDodajPrimerakDela = new FrameDodajPrimerakDela(frameKorisnik, idDela);
        		frameDodajPrimerakDela.setVisible(true);
        	}
        });
        btnDodajNaStanje.setBounds(116, 304, 149, 25);
        super.getContentPane().add(btnDodajNaStanje);
        
        JButton btnObrisi = new JButton("OBRIŠI");
        btnObrisi.setBackground(Color.ORANGE);
        btnObrisi.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		int input = JOptionPane.showConfirmDialog(null, "Da li želite obrisati delo?\nNapomena: Brisanjem dela brišu se svi podaci vezani za to delo!", "Potvrda brisanja dela",JOptionPane.YES_NO_CANCEL_OPTION);
        		if (input == 0) {
        			obrisiDelo(idDela);
        			getFrame().dispose();
        			PanelFilter filterPanel = new PanelFilter(userWindow);
        			userWindow.getPanelManager().show(filterPanel);
        		}
        	}
        });
        btnObrisi.setBounds(116, 352, 149, 25);
        super.getContentPane().add(btnObrisi);
	} 
	
	public void obrisiDelo(String idDela) {
		this.con = connection.createConnection();
		
		String deleteFromObavestenje = "delete from Obavestenje where id_pozajmice in (select id_pozajmice from Pozajmica where id_dela="+idDela+")";
		String deleteFromPozajmica = "delete from Pozajmica where id_dela="+idDela;
		String deleteFromVlasnikPoglavlja = "delete from VlasnikDela where id_poglavlja in (select id_poglavlja from Poglavlje where id_dela="+idDela+")";
		String deleteFromPoglavlje = "delete from Poglavlje where id_dela="+idDela;
		String deleteFromVlasnikDela = "delete from VlasnikDela where id_dela="+idDela;
		String deleteFromRezervacija = "delete from Rezervacija where id_dela="+idDela;
		String deleteFromAutorskoDelo = "delete from AutorskoDelo where id_dela="+idDela;
		
		PreparedStatement preparedStatement;
		try {
			preparedStatement = con.prepareStatement(deleteFromObavestenje);
			preparedStatement.executeUpdate();
			preparedStatement = con.prepareStatement(deleteFromPozajmica);
			preparedStatement.executeUpdate();
			preparedStatement = con.prepareStatement(deleteFromVlasnikPoglavlja);
			preparedStatement.executeUpdate();
			preparedStatement = con.prepareStatement(deleteFromPoglavlje);
			preparedStatement.executeUpdate();
			preparedStatement = con.prepareStatement(deleteFromVlasnikDela);
			preparedStatement.executeUpdate();
			preparedStatement = con.prepareStatement(deleteFromRezervacija);
			preparedStatement.executeUpdate();
			preparedStatement = con.prepareStatement(deleteFromAutorskoDelo);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			Warning errorDialog = new Warning("Greška");
			errorDialog.showMessage(e.getMessage());
		}
		
		connection.closeConnection();
	}

}
