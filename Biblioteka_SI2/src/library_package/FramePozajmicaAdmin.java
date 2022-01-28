package library_package;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class FramePozajmicaAdmin extends FramePozajmicaKorisnik{
	
	public FramePozajmicaAdmin(UserWindow userWindow, String idPozajmice) {
		super(idPozajmice);
		
		super.setBounds(380, 120, 532, 420);
				
		JButton btnZatvori = new JButton("ZATVORI");
		btnZatvori.setBackground(Color.ORANGE);
		btnZatvori.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getFrameStudent().dispose();
			}
		});
		btnZatvori.setBounds(217, 330, 98, 25);
		super.getContentPanel().add(btnZatvori);

	}
	
	public JFrame getFrameStudent() {
		return super.getFrame();
	}

}