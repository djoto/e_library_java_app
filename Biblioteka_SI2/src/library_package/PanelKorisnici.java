package library_package;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;

public class PanelKorisnici extends JPanel {
	private JTextField textFieldIdKorisnika;
	private DefaultTableModel contentModel;

	private MySQLConnection connection = new MySQLConnection();
	private Connection con = null;
	/**
	 * Create the panel.
	 */
	public PanelKorisnici(UserWindow userWindow) {
		setBackground(new Color(255, 255, 153));
		setLayout(null);
		
		JPanel innerPanelKorisniciTabela = new JPanel();
		innerPanelKorisniciTabela.setBounds(0, 0, 705, 333);
		
		innerPanelKorisniciTabela.setLayout(new BorderLayout(0, 0));
		
		TableModel tableModel = new TableModel();
		String query = podesiUpitZaKorisnike();
		contentModel = tableModel.generateModel(query);
		
		JTable table = new JTable(contentModel);
		table.setDefaultEditor(Object.class, null);
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)table.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment( SwingConstants.CENTER );
        JScrollPane scrollPane = new JScrollPane( table );
        scrollPane.getViewport().setBackground(new Color(255, 255, 153));
        innerPanelKorisniciTabela.add( scrollPane );
		
		add(innerPanelKorisniciTabela);
		
		
		
		
		JLabel lblUnesiteIdKorisnika = new JLabel("Unesite ID korisnika za više podataka:");
		lblUnesiteIdKorisnika.setHorizontalAlignment(SwingConstants.TRAILING);
		lblUnesiteIdKorisnika.setBounds(189, 357, 237, 15);
		add(lblUnesiteIdKorisnika);
		
		textFieldIdKorisnika = new JTextField();
		textFieldIdKorisnika.setColumns(10);
		textFieldIdKorisnika.setBounds(427, 355, 67, 19);
		add(textFieldIdKorisnika);
		
		JButton btnPogledajKorisnika = new JButton("Pogledaj korisnika");
		btnPogledajKorisnika.setBackground(Color.ORANGE);
		btnPogledajKorisnika.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String idKorisnika = textFieldIdKorisnika.getText();
				
				Warning warn = new Warning("Upozorenje");
				
				if (textFieldIdKorisnika.getText().trim().length() > 0) {
					if (postojiIdKorisnika(userWindow, idKorisnika)) {
						UserDataPanel userDataPanel = new UserDataPanel(userWindow, Integer.valueOf(idKorisnika));
						userWindow.getPanelManager().show(userDataPanel);
					}
					else {
						warn.showMessage("Za uneti ID ne postoji student u bazi!");
					}
				}
				else {
					warn.showMessage("Polje za ID mora biti popunjeno!");
				}
			}
		});
		btnPogledajKorisnika.setBounds(261, 387, 156, 25);
		add(btnPogledajKorisnika);

	}
	
	
	public boolean postojiIdKorisnika(UserWindow userWindow, String idKorisnika) {
		
		boolean exists = false;
		
		this.con = connection.createConnection();
		
		String query = null;
		
		query = "select * from Korisnik where tip_korisnika='student' and id_korisnika="+idKorisnika;

		Statement stmt;
		try {
			stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery( query );
	        while (rs.next()) {
	        	exists = true;
	        	if (exists) {
	        		break;
	        	}
	        }
	        rs.close();
	        stmt.close();
		
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		connection.closeConnection();
		
		return exists;
	}
	
	public String podesiUpitZaKorisnike() {
		String query;
		
		query = "select concat(id_korisnika) as 'ID korisnika', concat(ime_korisnika, ' ', prezime_korisnika) as 'Ime i prezime', concat(korisnicko_ime) as 'Korisničko ime', concat(br_indeksa) as 'Broj indeksa' from Korisnik where tip_korisnika='student' order by ime_korisnika";

		return query;
	}
}
