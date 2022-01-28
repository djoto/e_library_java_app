package library_package;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.awt.event.ActionEvent;

public class PanelRezervacije extends JPanel {
	private JTextField textFieldIdRezervacije;
	private DefaultTableModel contentModel;
	
	private MySQLConnection connection = new MySQLConnection();
	private Connection con = null;
	
	/**
	 * Create the panel.
	 */
	public PanelRezervacije(UserWindow userWindow) {
		setBackground(new Color(255, 255, 153));
		setLayout(null);
		
		FunkcijeRezervacija funkcijeRezervacija = new FunkcijeRezervacija();
		funkcijeRezervacija.provjeriTrajanjeSvihRezervacija(userWindow);
		
		JPanel panelTabelaRezervacija = new JPanel();
		panelTabelaRezervacija.setBounds(0, 0, 701, 333);
		//panelTabelaRezervacija.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelTabelaRezervacija.setLayout(new BorderLayout(0, 0));
		
		TableModel tableModel = new TableModel();
		String query = podesiUpitZaRezervacije(userWindow);
		contentModel = tableModel.generateModel(query);
		
		JTable table = new JTable(contentModel);
		table.setDefaultEditor(Object.class, null);
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)table.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment( SwingConstants.CENTER );
        JScrollPane scrollPane = new JScrollPane( table );
        scrollPane.getViewport().setBackground(new Color(255, 255, 153));
        panelTabelaRezervacija.add( scrollPane );
		
		
		add(panelTabelaRezervacija);
		
		JLabel lblUnesiteIdRezervacije = new JLabel("Unesite ID rezervacije za više podataka:");
		lblUnesiteIdRezervacije.setBounds(204, 357, 237, 15);
		add(lblUnesiteIdRezervacije);
		
		textFieldIdRezervacije = new JTextField();
		textFieldIdRezervacije.setBounds(439, 353, 67, 19);
		add(textFieldIdRezervacije);
		textFieldIdRezervacije.setColumns(10);
		
		JButton btnPogledajRezervaciju = new JButton("Pogledaj rezervaciju");
		btnPogledajRezervaciju.setBackground(Color.ORANGE);
		btnPogledajRezervaciju.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String idRezervacije = textFieldIdRezervacije.getText();
				Warning warn = new Warning("Upozorenje");
				
				FunkcijeRezervacija funkcijeRezervacija = new FunkcijeRezervacija();
				
				if (textFieldIdRezervacije.getText().trim().length() > 0) {
					if (funkcijeRezervacija.postojiIdRezervacije(userWindow, idRezervacije)) {
						FrameFactoryRezervacija frameFactoryRezervacija = new FrameFactoryRezervacija();
						FrameRezervacijaKorisnik frameRezervacijaKorisnik = frameFactoryRezervacija.getFrame(userWindow, idRezervacije);
						frameRezervacijaKorisnik.setVisible(true);
					}
					else {
						warn.showMessage("Za uneti ID ne postoji rezervacija u bazi!");
					}
				}
				else {
					warn.showMessage("Polje za ID mora biti popunjeno!");
				}
				
			}
		});
		btnPogledajRezervaciju.setBounds(273, 385, 156, 25);
		add(btnPogledajRezervaciju);

	}
	
	public String podesiUpitZaRezervacije(UserWindow userWindow) {
		String query;
		
		String userId = String.valueOf(userWindow.getUserId());
		String userType = userWindow.getUserTypeString();
		
		if (userType.equals("student")) {
			query = "select concat(r.id_rezervacije) as 'ID rezervacije', concat(d.naziv_dela) as 'Naziv dela', concat(r.broj_rezervisanih) as 'Broj rezervisanih' from Rezervacija r, AutorskoDelo d where d.id_dela=r.id_dela and r.id_studenta="+userId+" order by r.id_rezervacije";
		}
		else {
			query = "select concat(r.id_rezervacije) as 'ID rezervacije', concat(d.naziv_dela) as 'Naziv dela', concat(k.ime_korisnika, ' ', k.prezime_korisnika, '  ', k.br_indeksa) as Student, concat(r.broj_rezervisanih) as 'Broj rezervisanih' from Rezervacija r, AutorskoDelo d, Korisnik k where d.id_dela=r.id_dela and r.id_studenta=k.id_korisnika order by r.id_rezervacije";
		}
		
		return query;
	}
	
	
	

}
