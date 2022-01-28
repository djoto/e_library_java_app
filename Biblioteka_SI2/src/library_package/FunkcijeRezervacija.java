package library_package;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FunkcijeRezervacija {
	
	private MySQLConnection connection = new MySQLConnection();
	private Connection con = null;
	
	public void provjeriTrajanjeSvihRezervacija(UserWindow userWindow){
		this.con = connection.createConnection();
		
		String query = "select r.id_rezervacije, r.id_dela, r.vreme_rezervisanja, r.broj_rezervisanih from Rezervacija r";
		
		String idRezervacije;
		String idDela;
		Date vremeRezervisanja = null;
		String brojRezervisanih;
				
	    try (Statement stmt = this.con.createStatement()) {
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	    		idRezervacije = String.valueOf(rs.getInt("id_rezervacije"));
	    		idDela = String.valueOf(rs.getInt("id_dela"));
	    		brojRezervisanih = String.valueOf(rs.getInt("broj_rezervisanih"));
	    		
	    		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    		try {
					vremeRezervisanja = (Date) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateFormat.format(rs.getDate("vreme_rezervisanja"))+" "+rs.getTime("vreme_rezervisanja"));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    		Timestamp timestamp = new Timestamp(vremeRezervisanja.getTime());
	    		//System.out.println((((new Date()).getTime()-timestamp.getTime())/1000)/60);
	    		int razlikaMinute = (int) (((new Date()).getTime()-timestamp.getTime())/1000)/60;
	    		if(razlikaMinute > (24*60)) {
	    			AzuriranjeDela azuriranjeDela = new AzuriranjeDela();
	    			azuriranjeDela.azurirajRaspolozivoStanje(brojRezervisanih, idDela, "dodaj");
	    			obrisiRezervaciju(userWindow, idRezervacije);
	    		}	    		

	        }
	     } catch (SQLException e) {
				Warning errorDialog = new Warning("Greška");
				errorDialog.showMessage(e.getMessage());
	     }
		
		
		connection.closeConnection();
	}

	public void obrisiRezervaciju(UserWindow userWindow, String idRezervacije) {
		this.con = connection.createConnection();
		
		String updateStr = "delete from Rezervacija where id_rezervacije="+idRezervacije;
		
		PreparedStatement preparedStatement;
		try {
			preparedStatement = con.prepareStatement(updateStr);
			preparedStatement.executeUpdate();
			PanelRezervacije panelRezervacije = new PanelRezervacije(userWindow);
			userWindow.getPanelManager().show(panelRezervacije);
		} catch (SQLException e) {
			Warning errorDialog = new Warning("Greška");
			errorDialog.showMessage(e.getMessage());
		}
		
		connection.closeConnection();
		
	}
	
	public boolean postojiIdRezervacije(UserWindow userWindow, String idRezervacije) {
		
		boolean exists = false;
		
		this.con = connection.createConnection();
		
		String query = null;
		
		if (userWindow.getUserTypeString().equals("student")) {
			query = "select * from Rezervacija where id_rezervacije="+idRezervacije+" and id_studenta="+String.valueOf(userWindow.getUserId());
		}
		else {
			query = "select * from Rezervacija where id_rezervacije="+idRezervacije;
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
