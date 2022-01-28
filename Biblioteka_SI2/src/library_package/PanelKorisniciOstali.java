package library_package;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;

public class PanelKorisniciOstali extends JPanel {
	private JTextField textFieldIdKorisnika;

	
	private DefaultTableModel contentModel;

	private MySQLConnection connection = new MySQLConnection();
	private Connection con = null;
	/**
	 * Create the panel.
	 */
	public PanelKorisniciOstali(UserWindow userWindow) {
		setBackground(new Color(255, 255, 153));
		setLayout(null);
		
		JPanel innerPanelOstaliTabela = new JPanel();
		innerPanelOstaliTabela.setBounds(0, 0, 704, 333);
		
		innerPanelOstaliTabela.setLayout(new BorderLayout(0, 0));
		
		TableModel tableModel = new TableModel();
		String query = podesiUpitZaOstale();
		contentModel = tableModel.generateModel(query);
		
		JTable table = new JTable(contentModel);
		table.setDefaultEditor(Object.class, null);
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)table.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment( SwingConstants.CENTER );
        JScrollPane scrollPane = new JScrollPane( table );
        scrollPane.getViewport().setBackground(new Color(255, 255, 153));
        innerPanelOstaliTabela.add( scrollPane );
		
		add(innerPanelOstaliTabela);
		
		
		
		JLabel lblUnesiteIdKorisnika = new JLabel("Unesite ID korisnika za više podataka:");
		lblUnesiteIdKorisnika.setHorizontalAlignment(SwingConstants.TRAILING);
		lblUnesiteIdKorisnika.setBounds(202, 362, 237, 15);
		add(lblUnesiteIdKorisnika);
		
		textFieldIdKorisnika = new JTextField();
		textFieldIdKorisnika.setColumns(10);
		textFieldIdKorisnika.setBounds(440, 360, 67, 19);
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
						warn.showMessage("Za uneti ID ne postoji ni bibliotekar ni administrator u bazi!");
					}
				}
				else {
					warn.showMessage("Polje za ID mora biti popunjeno!");
				}
			}
		});
		btnPogledajKorisnika.setBounds(274, 392, 156, 25);
		add(btnPogledajKorisnika);
		
	}
	
	public String podesiUpitZaOstale() {
		String query;
		
		query = "select concat(id_korisnika) as 'ID korisnika', concat(ime_korisnika, ' ', prezime_korisnika) as 'Ime i prezime', concat(korisnicko_ime) as 'Korisničko ime', concat(tip_korisnika) as 'Tip korisnika' from Korisnik where tip_korisnika='bibliotekar' or tip_korisnika='admin' order by ime_korisnika";

		return query;
	}
	
	public boolean postojiIdKorisnika(UserWindow userWindow, String idKorisnika) {
		
		boolean exists = false;
		
		this.con = connection.createConnection();
		
		String query = null;
		
		query = "select * from Korisnik where tip_korisnika='bibliotekar' or tip_korisnika='admin' and id_korisnika="+idKorisnika;

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
}
