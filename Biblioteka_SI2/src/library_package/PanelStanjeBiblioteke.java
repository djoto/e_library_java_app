package library_package;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;


public class PanelStanjeBiblioteke extends JPanel {

	private DefaultTableModel contentModel;
	
	private MySQLConnection connection = new MySQLConnection();
	private Connection con = null;
	private JTextField textFieldIDForInfo;
	/**
	 * Create the panel.
	 */
	public PanelStanjeBiblioteke(UserWindow userWindow, String query) {
		setBackground(new Color(255, 255, 153));
		setLayout(null);
		
		JPanel innerPanel = new JPanel();
		//innerPanel.setBackground(new Color(255, 255, 153));
		innerPanel.setBounds(0, 0, 700, 336);
		//innerPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		innerPanel.setLayout(new BorderLayout(0, 0));
		
		TableModel tableModel = new TableModel();
		contentModel = tableModel.generateModel(query);
		
		JTable table = new JTable(contentModel);
		table.setDefaultEditor(Object.class, null);
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)table.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment( SwingConstants.CENTER );
        JScrollPane scrollPane = new JScrollPane( table );
        scrollPane.getViewport().setBackground(new Color(255, 255, 153));
        innerPanel.add( scrollPane );
		add(innerPanel);
		
		JLabel lblInfoODelu = new JLabel("Za više informacija od delu, unesite ID dela:");
		lblInfoODelu.setBounds(171, 357, 253, 15);
		add(lblInfoODelu);
		
		textFieldIDForInfo = new JTextField();
		textFieldIDForInfo.setBounds(427, 355, 81, 19);
		add(textFieldIDForInfo);
		textFieldIDForInfo.setColumns(10);
		
		
		JButton btnPogledajDelo = new JButton("Pogledaj delo");
		btnPogledajDelo.setBackground(Color.ORANGE);
		btnPogledajDelo.setBackground(Color.ORANGE);
		btnPogledajDelo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String idDela = textFieldIDForInfo.getText();
				Warning warn = new Warning("Upozorenje");
				
				if (textFieldIDForInfo.getText().trim().length() > 0) {
					if (postojiIdDela(idDela)) {
						FrameFactoryDelo frameFactoryDelo = new FrameFactoryDelo();
						FrameDeloKorisnik frameDeloKorisnik = frameFactoryDelo.getFrame(userWindow, idDela);
						frameDeloKorisnik.setVisible(true);
					}
					else {
						warn.showMessage("Za uneti ID ne postoji delo u bazi!");
					}
				}
				else {
					warn.showMessage("Polje za ID mora biti popunjeno!");
				}
				
			}
		});
		btnPogledajDelo.setBounds(285, 390, 126, 25);
		add(btnPogledajDelo);
	
	}
	
	public DefaultTableModel getModel() {
		return contentModel;
	}
	
	public boolean postojiIdDela(String idDela) {
		
		boolean exists = false;
		
		this.con = connection.createConnection();
		
		String query = "select * from AutorskoDelo where id_dela="+idDela;
		
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
