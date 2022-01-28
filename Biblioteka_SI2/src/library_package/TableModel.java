package library_package;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class TableModel {
	
	private MySQLConnection connection = new MySQLConnection();
	private Connection con = null;
	
	public TableModel() {
		
	}
	
	public DefaultTableModel generateModel(String query) {
		
		this.con = connection.createConnection();
		
        Vector columnNames = new Vector();
        Vector data = new Vector();
		
        //String sql = "Select * from Vlasnik";
        Statement stmt;
		try {
			stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery( query );
	        ResultSetMetaData md = (ResultSetMetaData) rs.getMetaData();
	        int columns = md.getColumnCount();
	        
	        for (int i = 1; i <= columns; i++) {
	          
		        columnNames.addElement(md.getColumnName(i));

	        }

	        //  Get row data
	        boolean empty = true;
	        while (rs.next()) {
	            Vector<Object> row = new Vector<Object>(columns);

	            for (int i = 1; i <= columns; i++) {

	            		row.addElement( rs.getObject(i) );
	          
	            }

	            data.addElement( row );

	            empty = false;
	        }
	        
	        if (empty) {
	        	
	        	Vector<Object> row = new Vector<Object>(1);
	            for (int i = 1; i <= columns; i++) {

	            	row.addElement((String) "Nema podataka!" );
            		
	            }
	        	//row.addElement((String) "Nema podataka!" );
	        	data.addElement( row );
	        	
	        	//return null;
	        }
	        
	        rs.close();
	        stmt.close();
		
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
		
		connection.closeConnection();
		
		return model;
	}

}
