package library_package;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FrameRezervacijaAdmin extends FrameRezervacijaKorisnik {
	
	private JTextField textFieldImePrezimeBrIndeksa;
	
	public FrameRezervacijaAdmin(UserWindow userWindow, String idRezervacije) {
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
