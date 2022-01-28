package library_package;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class MySQLConnection {
    
	
	private Connection con = null;

    private String url = "jdbc:mysql://localhost:3306/biblioteka_SI2";
    private String username = "root";
    private String password = "password";
    
    public MySQLConnection() {

    }
    
    public MySQLConnection(String url, String username, String password) {
    	this.url = url;
    	this.username = username;
    	this.password = password;
    }
    
    public Connection createConnection(){
    	try {	
		    try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		    this.con = DriverManager.getConnection(this.url, this.username, this.password);
		    return this.con;
		    		    
    	} catch (SQLException ex) {
    		//throw new Error("Error ", ex);
    		return null;
    	}
    }
    
    
    public void closeConnection() {
    	try {
            if (this.con != null) {
                this.con.close();
            }
        } catch (SQLException ex) {
            //System.out.println(ex.getMessage());
        }
    }

}