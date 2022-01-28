package library_package;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import javax.swing.JButton;

public class FrameRezervacijaStudent extends FrameRezervacijaKorisnik{
	
	
	public FrameRezervacijaStudent(UserWindow userWindow, String idRezervacije) {
		super(idRezervacije);
		
		JButton btnOtkazi = new JButton("OTKAŽI");
		btnOtkazi.setBackground(Color.ORANGE);
		btnOtkazi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AzuriranjeDela azuriranjeDela = new AzuriranjeDela();
				azuriranjeDela.azurirajRaspolozivoStanje(preuzmiBrojRezervisanih(), preuzmiIdDela(), "dodaj");
				FunkcijeRezervacija funkcijeRezervacija = new FunkcijeRezervacija();
				funkcijeRezervacija.obrisiRezervaciju(userWindow, idRezervacije);
				closeFrameRezervacija();
			}
		});
		btnOtkazi.setBounds(203, 222, 98, 25);
		super.getContentPanel().add(btnOtkazi);
		
		
		JButton btnZatvori = new JButton("ZATVORI");
		btnZatvori.setBackground(Color.ORANGE);
		btnZatvori.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				closeFrameRezervacija();
			}
		});
		btnZatvori.setBounds(203, 260, 98, 25);
		super.getContentPanel().add(btnZatvori);
	}
	

}
