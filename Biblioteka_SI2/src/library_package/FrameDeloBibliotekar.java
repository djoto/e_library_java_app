package library_package;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
//import javax.swing.JFrame;

public class FrameDeloBibliotekar extends FrameDeloKorisnik{
	
	public FrameDeloBibliotekar(UserWindow userWindow, String idDela) {
		super(idDela);
		
		FrameDeloBibliotekar frameKorisnik = this;
		
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
        
        JButton btnIznajmi = new JButton("IZNAJMI");
        btnIznajmi.setBackground(Color.ORANGE);
        btnIznajmi.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		FrameIznajmiDelo frameIznajmiDelo = new FrameIznajmiDelo(frameKorisnik, idDela);
        		frameIznajmiDelo.setVisible(true);
        	}
        });
        btnIznajmi.setBounds(116, 352, 149, 25);
        super.getContentPane().add(btnIznajmi);
	}

}
