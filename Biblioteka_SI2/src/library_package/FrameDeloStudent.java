package library_package;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;


public class FrameDeloStudent extends FrameDeloKorisnik{

	private MySQLConnection connection = new MySQLConnection();
	private Connection con = null;
	
	private SpinnerNumberModel modelSpinner;

	
	public FrameDeloStudent(UserWindow userWindow, String idDela) {
		super(idDela);
		
        JPanel panelForStudent = setPanelForStudent(userWindow, idDela);
        super.getContentPane().add(panelForStudent);
		
	}
	
	public JPanel setPanelForStudent(UserWindow userWindow, String idDela) {
        JPanel panelForStudent = new JPanel();
        panelForStudent.setBackground(new Color(255, 255, 153));
        panelForStudent.setBounds(25, 296, 360, 109);
        panelForStudent.setLayout(null);
        
        JLabel lblBrojPrimeraka = new JLabel("Broj primeraka za rezervaciju (max 1/2 raspoloživih):");
        lblBrojPrimeraka.setHorizontalAlignment(SwingConstants.CENTER);
        lblBrojPrimeraka.setBounds(12, 12, 336, 15);
        panelForStudent.add(lblBrojPrimeraka);
        
        modelSpinner = new SpinnerNumberModel(0.0, 0.0, Math.floor(brojRaspolozivih()/2), 1.0); 

        JSpinner spinnerBrZaRezervaciju = new JSpinner(modelSpinner);
        ((JSpinner.DefaultEditor) spinnerBrZaRezervaciju.getEditor()).getTextField().setEditable(false);
        spinnerBrZaRezervaciju.setBounds(147, 39, 49, 20);
        panelForStudent.add(spinnerBrZaRezervaciju);
        
        JButton btnRezervisi = new JButton("REZERVIŠI");
        btnRezervisi.setBackground(Color.ORANGE);
        btnRezervisi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				// TODO Auto-generated method stub
				if (mozeteRezervisati(idDela, String.valueOf(userWindow.getUserId()))) {
					if ((Double) modelSpinner.getValue()!=0.0 && brojRaspolozivih()>1) {
					
						rezervisi(String.valueOf(userWindow.getUserId()), idDela);
						
						AzuriranjeDela azuriranjeDela = new AzuriranjeDela();
						azuriranjeDela.azurirajRaspolozivoStanje(String.valueOf(modelSpinner.getValue()), idDela, "oduzmi");
						
						podesiRaspolozivoUkupnoStanje(idDela);
					
					}
					else {
						Warning w = new Warning("Greška");
						w.showMessage("Unos mora biti veći od 0 ili nema dovoljno dostupnih za rezervisanje!");
					}
				}
				else {
					Warning w = new Warning("Upozorenje");
					w.showMessage("Neuspešna rezervacija! Već ste rezervisali ovo delo!");
				}
				
			}
        });
        btnRezervisi.setBounds(125, 72, 98, 25);
        panelForStudent.add(btnRezervisi);
        
        return panelForStudent;
			
	}
	
	public JFrame getFrame() {
		return this;
	}
	
	public boolean mozeteRezervisati(String idDela, String idStudenta) {
	
		this.con = connection.createConnection();
	
		boolean ok = true;
		
		String query = "select * from Rezervacija where id_dela="+idDela+" and id_studenta="+idStudenta;
		
	    try (Statement stmt = this.con.createStatement()) {
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	ok = false;
	        }
	    } catch (SQLException e) {
	    	Warning w = new Warning("Upozorenje");
	    	w.showMessage(e.getMessage());
	    }
		
		connection.closeConnection();
		
		return ok;
	}
	
	public void rezervisi(String idStudenta, String idDela) {
		
		boolean success = false;
		
		this.con = connection.createConnection();
		
		String insertIntoRezervacija = "insert into Rezervacija (id_studenta, id_dela, broj_rezervisanih) values (?, ?, ?)";
		PreparedStatement preparedStmtRezervacija;
		try {
			preparedStmtRezervacija = con.prepareStatement(insertIntoRezervacija);
			preparedStmtRezervacija.setInt(1, Integer.valueOf(idStudenta));
			preparedStmtRezervacija.setInt(2, Integer.valueOf(idDela));
			//System.out.println(modelSpinner.getValue().getClass().getName());
			preparedStmtRezervacija.setInt(3, ((Double) modelSpinner.getValue()).intValue());

		    // execute the preparedstatement
		    preparedStmtRezervacija.execute();
		    success = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			success = false;
			//e.printStackTrace();
		}
		
		if (success) {
			Warning successDialog = new Warning("Uspeh");
			successDialog.showMessage("Uspešno ste rezervisali delo!\nVaša rezervacija traje 24 sata.");
		}
		else {
			Warning successDialog = new Warning("Greška");
			successDialog.showMessage("Došlo je do greške pri rezervisanju!");
		}
		
		connection.closeConnection();
		
				
	}
	
	
}
