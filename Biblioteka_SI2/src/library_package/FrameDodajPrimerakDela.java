package library_package;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.event.ActionEvent;

public class FrameDodajPrimerakDela extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public FrameDodajPrimerakDela(FrameDeloKorisnik frameDeloKorisnik, String idDela) {
		setResizable(false);
		ImageIcon img = new ImageIcon("library_icon.png");
		setIconImage(img.getImage());
		setTitle("Dodavanje primerka dela");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(480, 180, 370, 203);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUnesiteBrojPrimeraka = new JLabel("Unesite broj primeraka za dodavanje:");
		lblUnesiteBrojPrimeraka.setHorizontalAlignment(SwingConstants.CENTER);
		lblUnesiteBrojPrimeraka.setBounds(23, 22, 335, 15);
		contentPane.add(lblUnesiteBrojPrimeraka);
		
		Integer value = 1;
		Integer min = 1;
		Integer max = 100;
		Integer step = 1;
		SpinnerNumberModel modelSpinner = new SpinnerNumberModel(value, min, max, step);		
	    JSpinner spinnerBrPrimeraka = new JSpinner(modelSpinner);
	    ((JSpinner.DefaultEditor) spinnerBrPrimeraka.getEditor()).getTextField().setEditable(false);
		spinnerBrPrimeraka.setBounds(160, 56, 48, 25);
		contentPane.add(spinnerBrPrimeraka);
		
		JButton btnDodaj = new JButton("DODAJ");
		btnDodaj.setBackground(Color.ORANGE);
		btnDodaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AzuriranjeDela azuriranjeDela = new AzuriranjeDela();
				azuriranjeDela.azurirajRaspolozivoStanje(String.valueOf(modelSpinner.getValue()), idDela, "dodaj");
				azuriranjeDela.azurirajUkupnoStanje(String.valueOf(modelSpinner.getValue()), idDela, "dodaj");
				frameDeloKorisnik.podesiRaspolozivoUkupnoStanje(idDela);
				getFrame().dispose();
			}
		});
		btnDodaj.setBounds(194, 111, 98, 25);
		contentPane.add(btnDodaj);
		
		JButton btnZatvori = new JButton("ZATVORI");
		btnZatvori.setBackground(Color.ORANGE);
		btnZatvori.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getFrame().dispose();
			}
		});
		btnZatvori.setBounds(75, 111, 98, 25);
		contentPane.add(btnZatvori);
	}
	
	public JFrame getFrame() {
		return this;
	}
}
