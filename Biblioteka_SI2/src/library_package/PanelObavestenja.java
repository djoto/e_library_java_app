package library_package;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelObavestenja extends JPanel {

	private DefaultTableModel contentModel;
	
	private MySQLConnection connection = new MySQLConnection();
	private Connection con = null;
	private JTextField textFieldIdObavestenja;
	
	/**
	 * Create the panel.
	 */
	public PanelObavestenja(UserWindow userWindow) {
		setBackground(new Color(255, 255, 153));
		setLayout(null);
		
		provjeriRokoveZaVracanje();
		
		JPanel panelTabelaObavestenja = new JPanel();
		panelTabelaObavestenja.setBounds(0, 0, 701, 331);
		
		//panelTabelaObavestenja.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelTabelaObavestenja.setLayout(new BorderLayout(0, 0));
		
		TableModel tableModel = new TableModel();
		String query = podesiUpitZaObavestenja(userWindow);
		contentModel = tableModel.generateModel(query);
		
		JTable table = new JTable(contentModel);
		table.setDefaultEditor(Object.class, null);
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)table.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment( SwingConstants.CENTER );
        JScrollPane scrollPane = new JScrollPane( table );
        scrollPane.getViewport().setBackground(new Color(255, 255, 153));
        panelTabelaObavestenja.add( scrollPane );
        
		add(panelTabelaObavestenja);	
		
		
		JLabel lblUnesiteIdObavestenja = new JLabel("Unesite ID obaveštenja za više podataka:");
		lblUnesiteIdObavestenja.setBounds(195, 356, 243, 15);
		add(lblUnesiteIdObavestenja);
		
		textFieldIdObavestenja = new JTextField();
		textFieldIdObavestenja.setColumns(10);
		textFieldIdObavestenja.setBounds(439, 354, 67, 19);
		add(textFieldIdObavestenja);
		
		JButton btnPogledajObavestenje = new JButton("Pogledaj obaveštenje");
		btnPogledajObavestenje.setBackground(Color.ORANGE);
		btnPogledajObavestenje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String idObavestenja = textFieldIdObavestenja.getText();
				Warning warn = new Warning("Upozorenje");
				
				if (textFieldIdObavestenja.getText().trim().length() > 0) {
					if (postojiIdObavestenja(userWindow, idObavestenja)) {
						FrameObavestenjeKorisnik frameObavestenjeKorisnik = new FrameObavestenjeKorisnik(userWindow, idObavestenja);;
						frameObavestenjeKorisnik.setVisible(true);
						podesiKaoPregledano(userWindow, idObavestenja);
						userWindow.getPanelManager().show(new PanelObavestenja(userWindow));
					}
					else {
						warn.showMessage("Za uneti ID ne postoji obaveštenje u bazi!");
					}
				}
				else {
					warn.showMessage("Polje za ID mora biti popunjeno!");
				}

			}
		});
		btnPogledajObavestenje.setBounds(273, 386, 156, 25);
		add(btnPogledajObavestenje);

	}
	
	
	public void podesiKaoPregledano(UserWindow userWindow, String idObavestenja) {
		this.con = connection.createConnection();
		
		String query = null;
		
		if (userWindow.getUserTypeString().equals("student")) {		
			query = "update Obavestenje set pregledao_student='Da' where id_obavestenja="+idObavestenja;
		}
		else {
			query = "update Obavestenje set pregledao_bibliotekar='Da' where id_obavestenja="+idObavestenja;
		}
		
		PreparedStatement preparedStatement;
		try {
			preparedStatement = this.con.prepareStatement(query);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			Warning errDialog = new Warning("Greška");
			errDialog.showMessage("Došlo je do greške pri ažuriranju obaveštenja!");
		}
		
		connection.closeConnection();
	}
	
	public String podesiUpitZaObavestenja(UserWindow userWindow) {
		String query;
		
		String userId = String.valueOf(userWindow.getUserId());
		String userType = userWindow.getUserTypeString();
		
		if (userType.equals("student")) {
			query = "select concat(o.id_obavestenja) as 'ID obaveštenja', concat(o.tip_obavestenja) as 'Tip obaveštenja', concat(o.pregledao_student) as Pregledano from Obavestenje o, Pozajmica p where o.id_pozajmice=p.id_pozajmice and p.id_studenta="+userId+" order by o.id_obavestenja";
		}
		else {
			query = "select concat(o.id_obavestenja) as 'ID obaveštenja', concat(o.tip_obavestenja) as 'Tip obaveštenja', concat(o.pregledao_bibliotekar) as Pregledano from Obavestenje o order by o.id_obavestenja";
		}
		
		return query;
	}
	
	
	public void provjeriRokoveZaVracanje(){
		this.con = connection.createConnection();
		
		String query = "select p.id_pozajmice, p.datum_uzimanja, p.vraceno from Pozajmica p";
		
		String idPozajmice;
		Date datumUzimanja = null;
		String vraceno;
				
	    try (Statement stmt = this.con.createStatement()) {
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	    		
	        	idPozajmice = String.valueOf(rs.getInt("id_pozajmice"));
	        	vraceno = String.valueOf(rs.getString("vraceno"));
	        	
	        	if (vraceno.equals("Ne")) {
	        		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    		try {
						datumUzimanja = (Date) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateFormat.format(rs.getDate("datum_uzimanja"))+" "+rs.getTime("datum_uzimanja"));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    		Timestamp timestamp = new Timestamp(datumUzimanja.getTime());
		    		//System.out.println((((new Date()).getTime()-timestamp.getTime())/1000)/60);
		    		int razlikaDani = (int) (((((new Date()).getTime()-timestamp.getTime())/1000)/60)/60)/24;
		    		if(razlikaDani > 30) {
		    			dodajObavestenje(idPozajmice);
		    		}	    		
	        	}	    	

	        }
	     } catch (SQLException e) {
				Warning errorDialog = new Warning("Greška");
				errorDialog.showMessage(e.getMessage());
	     }
		
		connection.closeConnection();
	}
	
	
	
	public void dodajObavestenje(String idPozajmice) {
		
		this.con = connection.createConnection();
		
		String insertIntoObavestenje = "insert into Obavestenje (tip_obavestenja, id_pozajmice, pregledao_student, pregledao_bibliotekar) values (?, ?, ?, ?)";
		PreparedStatement preparedStmtObavestenje;
		try {
			preparedStmtObavestenje = con.prepareStatement(insertIntoObavestenje);
		    preparedStmtObavestenje.setString(1, "Prekoračenje roka za vraćanje");
		    preparedStmtObavestenje.setInt(2, Integer.valueOf(idPozajmice));
		    preparedStmtObavestenje.setString(3, "Ne");
		    preparedStmtObavestenje.setString(4, "Ne");
		    // execute the preparedstatement
		    preparedStmtObavestenje.execute();
		} catch (SQLException e) {
			Warning errorDialog = new Warning("Greška");
			errorDialog.showMessage(e.getMessage());
		}
		
		connection.closeConnection();
		
	}
	
	
	public boolean postojiIdObavestenja(UserWindow userWindow,String idObavestenja) {
		
		boolean exists = false;
		
		this.con = connection.createConnection();
		
		String query = null;
		
		if (userWindow.getUserTypeString().equals("student")) {
			query = "select * from Obavestenje o, Pozajmica p where p.id_pozajmice=o.id_pozajmice and o.id_obavestenja="+idObavestenja+" and p.id_studenta="+String.valueOf(userWindow.getUserId());
		}
		else {
			query = "select * from Obavestenje where id_obavestenja="+idObavestenja;
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
