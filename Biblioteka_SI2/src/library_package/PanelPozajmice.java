package library_package;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;

public class PanelPozajmice extends JPanel {

	private MySQLConnection connection = new MySQLConnection();
	private Connection con = null;
	private JTextField textFieldIdPozajmice;
	
	private DefaultTableModel contentModel;
	/**
	 * Create the panel.
	 */
	public PanelPozajmice(UserWindow userWindow, String idStudenta) {
		setBackground(new Color(255, 255, 153));
		setLayout(null);
		
		
		JPanel panelTabelaPozajmica = new JPanel();
		panelTabelaPozajmica.setBounds(0, 0, 709, 333);
		
		panelTabelaPozajmica.setLayout(new BorderLayout(0, 0));
		
		TableModel tableModel = new TableModel();
		String query = podesiUpitZaPozajmice(idStudenta);
		contentModel = tableModel.generateModel(query);
		
		JTable table = new JTable(contentModel);
		table.setDefaultEditor(Object.class, null);
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)table.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment( SwingConstants.CENTER );
        JScrollPane scrollPane = new JScrollPane( table );
        scrollPane.getViewport().setBackground(new Color(255, 255, 153));
        panelTabelaPozajmica.add( scrollPane );
		
		add(panelTabelaPozajmica);
		
		
		JLabel lblInfoOPozajmici = new JLabel("Za više informacija od pozajmici, unesite ID pozajmice:");
		lblInfoOPozajmici.setBounds(155, 354, 310, 15);
		add(lblInfoOPozajmici);
		
		textFieldIdPozajmice = new JTextField();
		textFieldIdPozajmice.setColumns(10);
		textFieldIdPozajmice.setBounds(470, 352, 81, 19);
		add(textFieldIdPozajmice);
		
		JButton btnPogledajPozajmicu = new JButton("Pogledaj pozajmicu");
		btnPogledajPozajmicu.setBackground(Color.ORANGE);
		btnPogledajPozajmicu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String idPozajmice = textFieldIdPozajmice.getText();
				Warning warn = new Warning("Upozorenje");
				
				if (textFieldIdPozajmice.getText().trim().length() > 0) {
					if (postojiIdPozajmice(userWindow, idPozajmice, idStudenta)) {
						FrameFactoryPozajmica frameFactoryPozajmica = new FrameFactoryPozajmica();
						FramePozajmicaKorisnik framePozajmicaKorisnik = frameFactoryPozajmica.getFrame(userWindow, idPozajmice);
						framePozajmicaKorisnik.setVisible(true);
					}
					else {
						warn.showMessage("Nema podataka!");
					}
				}
				else {
					warn.showMessage("Polje za ID mora biti popunjeno!");
				}
			}
		});
		btnPogledajPozajmicu.setBounds(334, 391, 152, 25);
		add(btnPogledajPozajmicu);
		
		JButton btnOsvezi = new JButton("Osveži tabelu");
		btnOsvezi.setBackground(Color.ORANGE);
		btnOsvezi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PanelPozajmice panelPozajmice = new PanelPozajmice(userWindow, idStudenta);
				userWindow.getPanelManager().show(panelPozajmice);
			}
		});
		btnOsvezi.setBounds(211, 391, 111, 25);
		add(btnOsvezi);


	}
	
	public String podesiUpitZaPozajmice(String idStudenta) {
		
		String query;

		if (!idStudenta.equals("all")) {
			query = "select concat(p.id_pozajmice) as 'ID pozajmice', concat(d.naziv_dela) as 'Naziv dela', concat(p.broj_pozajmljenih) as 'Broj pozajmljenih', concat(p.vraceno) as 'Vraćeno' from Pozajmica p, AutorskoDelo d where p.id_dela=d.id_dela and p.id_studenta="+idStudenta+" order by p.id_pozajmice";
		}
		else {
			query = "select concat(p.id_pozajmice) as 'ID pozajmice', concat(k.ime_korisnika, ' ', k.prezime_korisnika) as 'Student', concat(d.naziv_dela) as 'Naziv dela', concat(p.vraceno) as 'Vraćeno' from Pozajmica p, Korisnik k, AutorskoDelo d where p.id_dela=d.id_dela and p.id_studenta=k.id_korisnika order by p.id_pozajmice";
		}
		
		return query;
		
	}
	
	public boolean postojiIdPozajmice(UserWindow userWindow, String idPozajmice, String idStudenta) {
		
		boolean exists = false;
		
		this.con = connection.createConnection();
		
		String query = null;
		
		if (userWindow.getUserTypeString().equals("student")) {
			query = "select * from Pozajmica where id_pozajmice="+idPozajmice+" and id_studenta="+String.valueOf(userWindow.getUserId());
		}
		else if (idStudenta.equals("all")){
			query = "select * from Pozajmica where id_pozajmice="+idPozajmice;
		}
		else {
			query = "select * from Pozajmica where id_pozajmice="+idPozajmice+" and id_studenta="+idStudenta;
		}
	
		
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
