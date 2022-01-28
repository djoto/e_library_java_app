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

public class PanelZahteviZaRegistraciju extends JPanel {
	private JTextField textFieldIdZahteva;
	
	private DefaultTableModel contentModel;
	
	private MySQLConnection connection = new MySQLConnection();
	private Connection con = null;

	/**
	 * Create the panel.
	 */
	public PanelZahteviZaRegistraciju(UserWindow userWindow) {
		setBackground(new Color(255, 255, 153));
		setLayout(null);
		
		JPanel innerPanelTabelaZahtevi = new JPanel();
		innerPanelTabelaZahtevi.setBounds(0, 0, 707, 331);
		
		innerPanelTabelaZahtevi.setLayout(new BorderLayout(0, 0));
		
		TableModel tableModel = new TableModel();
		String query = podesiUpitZaZahteve();
		contentModel = tableModel.generateModel(query);
		
		JTable table = new JTable(contentModel);
		table.setDefaultEditor(Object.class, null);
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)table.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment( SwingConstants.CENTER );
        JScrollPane scrollPane = new JScrollPane( table );
        scrollPane.getViewport().setBackground(new Color(255, 255, 153));
        innerPanelTabelaZahtevi.add( scrollPane );
		
		add(innerPanelTabelaZahtevi);
		
		
		
		JLabel lblUnesiteIdZahteva = new JLabel("Unesite ID zahteva za više podataka:");
		lblUnesiteIdZahteva.setHorizontalAlignment(SwingConstants.TRAILING);
		lblUnesiteIdZahteva.setBounds(200, 362, 230, 15);
		add(lblUnesiteIdZahteva);
		
		textFieldIdZahteva = new JTextField();
		textFieldIdZahteva.setColumns(10);
		textFieldIdZahteva.setBounds(435, 358, 67, 19);
		add(textFieldIdZahteva);
		
		JButton btnPogledajZahtev = new JButton("Pogledaj zahtev");
		btnPogledajZahtev.setBackground(Color.ORANGE);
		btnPogledajZahtev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String idZahteva = textFieldIdZahteva.getText();
				Warning warn = new Warning("Upozorenje");
				
				if (textFieldIdZahteva.getText().trim().length() > 0) {
					if (postojiIdZahteva(idZahteva)) {
						FrameZahtevZaRegistraciju frameZahtevZaRegistraciju = new FrameZahtevZaRegistraciju(userWindow, idZahteva);
						frameZahtevZaRegistraciju.setVisible(true);
					}
					else {
						warn.showMessage("Za uneti ID ne postoji zahtev u bazi!");
					}
				}
				else {
					warn.showMessage("Polje za ID mora biti popunjeno!");
				}
			}
		});
		btnPogledajZahtev.setBounds(287, 390, 138, 25);
		add(btnPogledajZahtev);

	}
	
	public String podesiUpitZaZahteve() {
		String query;

		query = "select concat(id_zahteva) as 'ID zahteva', concat(ime_studenta, ' ', prezime_studenta) as 'Ime i prezime studenta', concat(br_indeksa_studenta) as 'Broj indeksa' from ZahtevZaRegistraciju order by id_zahteva";

		return query;
	}
	
	public boolean postojiIdZahteva(String idZahteva) {
		
		boolean exists = false;
		
		this.con = connection.createConnection();
		
		String query = "select * from ZahtevZaRegistraciju where id_zahteva="+idZahteva;
		
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
