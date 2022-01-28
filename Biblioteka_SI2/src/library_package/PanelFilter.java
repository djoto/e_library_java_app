package library_package;

import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class PanelFilter extends JPanel {
	private JTextField textFieldNazivPretraga;
	private JTextField textFieldAutorPretraga;
	private JTextField textFieldMentorPretraga;
	private JTextField textFieldGodIzdPretraga;
	private JTextField textFieldISBNPretraga;
	
	private JCheckBox chckbxSvaDela;
	private JComboBox comboBoxTipDela;
	
	private MySQLConnection connection = new MySQLConnection();
	private Connection con = null;

	/**
	 * Create the panel.
	 */
	public PanelFilter(UserWindow userWindow) {
		setBackground(new Color(255, 255, 153));
		setLayout(null);
		//setLayout(new GridBagLayout());
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 153));
		panel.setBounds(178, 12, 343, 406);
		add(panel);
		panel.setLayout(null);
		//panel.setLayout(new GridBagLayout());
		
		chckbxSvaDela = new JCheckBox("Sva dela");
		chckbxSvaDela.setBounds(129, 330, 113, 25);
		panel.add(chckbxSvaDela);
		
		textFieldNazivPretraga = new JTextField();
		textFieldNazivPretraga.setBounds(132, 91, 181, 22);
		panel.add(textFieldNazivPretraga);
		textFieldNazivPretraga.setColumns(10);
		
		textFieldAutorPretraga = new JTextField();
		textFieldAutorPretraga.setBounds(132, 141, 181, 22);
		panel.add(textFieldAutorPretraga);
		textFieldAutorPretraga.setColumns(10);
		
		textFieldMentorPretraga = new JTextField();
		textFieldMentorPretraga.setBounds(132, 190, 181, 22);
		panel.add(textFieldMentorPretraga);
		textFieldMentorPretraga.setColumns(10);
		
		textFieldGodIzdPretraga = new JTextField();
		textFieldGodIzdPretraga.setBounds(132, 241, 181, 22);
		panel.add(textFieldGodIzdPretraga);
		textFieldGodIzdPretraga.setColumns(10);
		
		textFieldISBNPretraga = new JTextField();
		textFieldISBNPretraga.setBounds(132, 293, 181, 22);
		panel.add(textFieldISBNPretraga);
		textFieldISBNPretraga.setColumns(10);
		
		JButton btnPretrazi = new JButton("PRETRAŽI");
		btnPretrazi.setBackground(Color.ORANGE);
		btnPretrazi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String queryStr = setQueryString();
				PanelStanjeBiblioteke panelStanjeBiblioteke = new PanelStanjeBiblioteke(userWindow, queryStr);
				userWindow.getPanelManager().show(panelStanjeBiblioteke);

			}
		});
		btnPretrazi.setBounds(106, 369, 97, 25);
		panel.add(btnPretrazi);
		
		JLabel lblTipDela = new JLabel("Tip dela");
		lblTipDela.setBounds(23, 46, 56, 16);
		panel.add(lblTipDela);
		
		JLabel lblNazivDelaPretraga = new JLabel("Naziv dela");
		lblNazivDelaPretraga.setBounds(23, 94, 73, 16);
		panel.add(lblNazivDelaPretraga);
		
		JLabel lblAutorPretraga = new JLabel("Autor");
		lblAutorPretraga.setBounds(23, 144, 56, 16);
		panel.add(lblAutorPretraga);
		
		JLabel lblMentorPretraga = new JLabel("Mentor");
		lblMentorPretraga.setBounds(23, 193, 56, 16);
		panel.add(lblMentorPretraga);
		
		JLabel lblGodIzdanjaPretraga = new JLabel("Godina izdanja");
		lblGodIzdanjaPretraga.setBounds(23, 244, 97, 16);
		panel.add(lblGodIzdanjaPretraga);
		
		JLabel lblISBNPretraga = new JLabel("ISBN broj");
		lblISBNPretraga.setBounds(23, 296, 56, 16);
		panel.add(lblISBNPretraga);
		
		//String[] items = {"SVE", "Udžbenik", "Zbirka zadataka", "Praktikum", "Monografija", "Završni rad", "Diplomski rad", "Master rad", "Doktorska disertacija"};
		String[] items = vrsteAutorskihDela(true);
		comboBoxTipDela = new JComboBox(items);
		comboBoxTipDela.setBounds(132, 43, 181, 22);
		panel.add(comboBoxTipDela);
		
		JLabel lblPretragaBiblioteke = new JLabel("Pretraga biblioteke:");
		lblPretragaBiblioteke.setBounds(0, 9, 331, 25);
		panel.add(lblPretragaBiblioteke);
		lblPretragaBiblioteke.setFont(new Font("Dialog", Font.BOLD, 14));
		lblPretragaBiblioteke.setHorizontalAlignment(SwingConstants.CENTER);

	}
	
	public String[] vrsteAutorskihDela(boolean zaPretragu) {
		
		ArrayList<String> tipoviAutorskihDela = new ArrayList<String>();
		
		if (zaPretragu) {
			tipoviAutorskihDela.add("SVE");
		}
		
		this.con = connection.createConnection();
		
		String query = "select naziv_tipa_dela from TipDela";
		
	    try (Statement stmt = this.con.createStatement()) {
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	tipoviAutorskihDela.add(rs.getString("naziv_tipa_dela"));
	        }
	    } catch (SQLException e) {
	    	Warning w = new Warning("Upozorenje");
	    	w.showMessage(e.getMessage());
	    }
		
		connection.closeConnection();
		
		String[] arrayToReturn = tipoviAutorskihDela.toArray(new String[tipoviAutorskihDela.size()]);
		
		return arrayToReturn;
		
	}
	
	public String setQueryString() {
		String strSelect = "select distinct concat(d.id_dela) as 'ID dela', concat(d.naziv_dela) as 'Naziv dela', concat(d.trenutno_stanje) as 'Raspoloživo stanje', concat(t.naziv_tipa_dela) as 'Tip dela' ";
		String strFrom = " from TipDela as t, AutorskoDelo as d "; 
		String strWhere = " where t.id_tipa=d.id_tipa ";
		
		if (chckbxSvaDela.isSelected()) {
			return strSelect + strFrom + strWhere;
		}
		
		if (!comboBoxTipDela.getSelectedItem().toString().equals("SVE")) {
			strWhere += " and t.naziv_tipa_dela='"+comboBoxTipDela.getSelectedItem().toString().toLowerCase()+"' ";
		}
		
		if (!textFieldNazivPretraga.getText().equals("")) {
			strWhere += " and d.naziv_dela like '"+textFieldNazivPretraga.getText().toString()+"%' ";
		}
		
		if (!textFieldAutorPretraga.getText().equals("")) {
			strFrom += ", VlasnikDela as v ";
			strWhere += " and d.id_dela=v.id_dela and v.tip_vlasnika='autor' and (v.ime_vlasnika like '"+textFieldAutorPretraga.getText().toString()+"%' or v.prezime_vlasnika like '"+textFieldAutorPretraga.getText().toString()+"%') ";
		}
		
		if (!textFieldMentorPretraga.getText().equals("")) {
			strFrom += ", VlasnikDela as v ";
			strWhere += " and d.id_dela=v.id_dela and v.tip_vlasnika='mentor' and (v.ime_vlasnika like '"+textFieldMentorPretraga.getText().toString()+"%' or v.prezime_vlasnika like '"+textFieldMentorPretraga.getText().toString()+"%') ";
		}
		
		if (!textFieldGodIzdPretraga.getText().equals("")) {
			strWhere += " and d.vreme_izdanja like '"+textFieldGodIzdPretraga.getText().toString()+"%' ";
		}
		
		if (!textFieldISBNPretraga.getText().equals("")) {
			strWhere += " and d.ISBN_broj like '"+textFieldISBNPretraga.getText().toString()+"%' ";
		}
		
		
		return strSelect + strFrom + strWhere;
	}
}
