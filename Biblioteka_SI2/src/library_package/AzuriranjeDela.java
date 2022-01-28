package library_package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AzuriranjeDela {
	
	private MySQLConnection connection = new MySQLConnection();
	private Connection con = null;
	
	public void azurirajRaspolozivoStanje(String broj, String idDela, String dodaj_oduzmi) {
		this.con = connection.createConnection();
		
		String query = null;
		
		if (dodaj_oduzmi.equals("dodaj")) {		
			query = "update AutorskoDelo set trenutno_stanje=trenutno_stanje+"+broj+" where id_dela="+idDela;
		}
		
		if (dodaj_oduzmi.equals("oduzmi")) {		
			query = "update AutorskoDelo set trenutno_stanje=trenutno_stanje-"+broj+" where id_dela="+idDela;
		}
		
		PreparedStatement preparedStatement;
		try {
			preparedStatement = this.con.prepareStatement(query);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			Warning errDialog = new Warning("Greška");
			errDialog.showMessage("Došlo je do greške pri ažuriranju stanja!");
		}
		
		connection.closeConnection();
	}
	
	
	public void azurirajUkupnoStanje(String broj, String idDela, String dodaj_oduzmi) {
		this.con = connection.createConnection();
		
		String query = null;
		
		if (dodaj_oduzmi.equals("dodaj")) {		
			query = "update AutorskoDelo set ukupna_kolicina=ukupna_kolicina+"+broj+" where id_dela="+idDela;
		}
		
		if (dodaj_oduzmi.equals("oduzmi")) {		
			query = "update AutorskoDelo set ukupna_kolicina=ukupna_kolicina-"+broj+" where id_dela="+idDela;
		}
		
		PreparedStatement preparedStatement;
		try {
			preparedStatement = this.con.prepareStatement(query);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			Warning errDialog = new Warning("Greška");
			errDialog.showMessage("Došlo je do greške pri ažuriranju stanja!");
		}
		
		connection.closeConnection();
	}

}
