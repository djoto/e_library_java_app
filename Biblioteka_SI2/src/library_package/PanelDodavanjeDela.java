package library_package;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import javax.swing.JSpinner;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.JFileChooser;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.swing.JTextArea; 

public class PanelDodavanjeDela extends JPanel {
	private JTextField textFieldNazivDela;
	private JTextField textFieldIzdavac;
	private JTextField textFieldISBN;
	private JTextField textFieldPreview;
	
	private JDateChooser dateChooser;
	private JComboBox comboBox;
	private JSpinner spinnerBrPrimeraka;
	private JTextArea textAreaAutori;
	private JTextArea textAreaEditori;
	private JTextArea textAreaMentori;
	private JTextArea textAreaAutoriPoglavlja;
	private JTextArea textAreaNaziviPoglavlja;
	
	private SpinnerNumberModel modelSpinner;
	
	private MySQLConnection connection = new MySQLConnection();
	private Connection con = null;
	/**
	 * Create the panel.
	 */
	public PanelDodavanjeDela(UserWindow userWindow) {
		setBackground(new Color(255, 255, 153));
		setLayout(null);
		
		JLabel lblNazivDela = new JLabel("Naziv dela:");
		lblNazivDela.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNazivDela.setBounds(28, 18, 145, 15);
		add(lblNazivDela);
		
		JLabel lblTipDela = new JLabel("Tip dela:");
		lblTipDela.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTipDela.setBounds(28, 55, 145, 15);
		add(lblTipDela);
		
		JLabel lblDatumgodinaIzdanja = new JLabel("Datum/Godina izdanja:");
		lblDatumgodinaIzdanja.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDatumgodinaIzdanja.setBounds(28, 93, 145, 15);
		add(lblDatumgodinaIzdanja);
		
		JLabel lblIzdava = new JLabel("Izdavač:");
		lblIzdava.setHorizontalAlignment(SwingConstants.TRAILING);
		lblIzdava.setBounds(28, 131, 145, 15);
		add(lblIzdava);
		
		JLabel lblIsbnBroj = new JLabel("ISBN broj:");
		lblIsbnBroj.setHorizontalAlignment(SwingConstants.TRAILING);
		lblIsbnBroj.setBounds(28, 169, 145, 15);
		add(lblIsbnBroj);
		
		JLabel lblPreviewNaslovneStrane = new JLabel("Preview naslovne strane:");
		lblPreviewNaslovneStrane.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPreviewNaslovneStrane.setBounds(28, 243, 145, 15);
		add(lblPreviewNaslovneStrane);
		
		JLabel lblBrojPrimeraka = new JLabel("Broj primeraka:");
		lblBrojPrimeraka.setHorizontalAlignment(SwingConstants.TRAILING);
		lblBrojPrimeraka.setBounds(28, 209, 145, 15);
		add(lblBrojPrimeraka);
		
		textFieldNazivDela = new JTextField();
		textFieldNazivDela.setBounds(177, 14, 235, 19);
		add(textFieldNazivDela);
		textFieldNazivDela.setColumns(10);
		
		
		PanelFilter filter = new PanelFilter(userWindow);
		String[] items = filter.vrsteAutorskihDela(false);
		comboBox = new JComboBox(items);
		comboBox.setBounds(177, 51, 235, 19);
		add(comboBox);
		
		dateChooser = new JDateChooser();
		dateChooser.setBounds(177, 89, 145, 19);
		JTextFieldDateEditor editor = (JTextFieldDateEditor) dateChooser.getDateEditor();
		editor.setEditable(false);
		add(dateChooser);
		
		textFieldIzdavac = new JTextField();
		textFieldIzdavac.setBounds(177, 127, 235, 19);
		add(textFieldIzdavac);
		textFieldIzdavac.setColumns(10);
		
		textFieldISBN = new JTextField();
		textFieldISBN.setBounds(177, 165, 235, 19);
		add(textFieldISBN);
		textFieldISBN.setColumns(10);
		
		textFieldPreview = new JTextField();
		textFieldPreview.setEditable(false);
		textFieldPreview.setBounds(177, 239, 235, 19);
		add(textFieldPreview);
		textFieldPreview.setColumns(10);
		
		Integer value = 1;
		Integer min = 1;
		Integer max = 1000;
		Integer step = 1;
		modelSpinner = new SpinnerNumberModel(value, min, max, step);		
		spinnerBrPrimeraka = new JSpinner(modelSpinner);
		((JSpinner.DefaultEditor) spinnerBrPrimeraka.getEditor()).getTextField().setEditable(false);
		spinnerBrPrimeraka.setBounds(177, 204, 65, 20);
		add(spinnerBrPrimeraka);
		
		JButton btnBrowse = new JButton("browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser();
				//fileChooser.setCurrentDirectory(new File(System.getProperty("preview_images")));
				int result = fileChooser.showOpenDialog(getPanel());
				if (result == JFileChooser.APPROVE_OPTION) {
					File source = fileChooser.getSelectedFile();
					Random rd = new Random(); // creating Random object
				    // System.out.println(Math.abs(rd.nextLong()));
				    String destString = "preview_images/"+FilenameUtils.removeExtension(source.getName())+"_"+String.valueOf(Math.abs(rd.nextLong()))+"."+FilenameUtils.getExtension(source.getName());
					File dest = new File(destString);
					try {
					    FileUtils.copyFile(source, dest);
					} catch (IOException e) {
					    e.printStackTrace();
					}
				    textFieldPreview.setText(destString);
				}
			}
		});
		btnBrowse.setBounds(255, 259, 76, 20);
		add(btnBrowse);

		
		JScrollPane scrollPaneAutori = new JScrollPane();
		scrollPaneAutori.setBounds(447, 32, 221, 55);
		add(scrollPaneAutori);
		textAreaAutori = new JTextArea();
		scrollPaneAutori.setViewportView(textAreaAutori);

		JLabel lblAutori = new JLabel("Autori:");
		lblAutori.setHorizontalAlignment(SwingConstants.CENTER);
		lblAutori.setBounds(447, 14, 221, 15);
		add(lblAutori);
		
		JScrollPane scrollPaneEditori = new JScrollPane();
		scrollPaneEditori.setBounds(447, 124, 221, 55);
		add(scrollPaneEditori);
		textAreaEditori = new JTextArea();
		scrollPaneEditori.setViewportView(textAreaEditori);
		
		JLabel lblEditori = new JLabel("Editori:");
		lblEditori.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditori.setBounds(447, 106, 221, 15);
		add(lblEditori);
		
		JScrollPane scrollPaneMentori = new JScrollPane();
		scrollPaneMentori.setBounds(447, 215, 221, 55);
		add(scrollPaneMentori);
		textAreaMentori = new JTextArea();
		scrollPaneMentori.setViewportView(textAreaMentori);
		
		JLabel lblMentori = new JLabel("Mentori:");
		lblMentori.setHorizontalAlignment(SwingConstants.CENTER);
		lblMentori.setBounds(447, 197, 221, 15);
		add(lblMentori);
		
		JScrollPane scrollPaneAutoriPoglavlja = new JScrollPane();
		scrollPaneAutoriPoglavlja.setBounds(336, 327, 332, 55);
		add(scrollPaneAutoriPoglavlja);
		textAreaAutoriPoglavlja = new JTextArea();
		scrollPaneAutoriPoglavlja.setViewportView(textAreaAutoriPoglavlja);
		
		JLabel lblAutoriZaPoglavlja = new JLabel("Autori za poglavlja:");
		lblAutoriZaPoglavlja.setHorizontalAlignment(SwingConstants.CENTER);
		lblAutoriZaPoglavlja.setBounds(336, 308, 332, 15);
		add(lblAutoriZaPoglavlja);
		
		JScrollPane scrollPaneNaziviPoglavlja = new JScrollPane();
		scrollPaneNaziviPoglavlja.setBounds(28, 327, 294, 55);
		add(scrollPaneNaziviPoglavlja);
		textAreaNaziviPoglavlja = new JTextArea();
		scrollPaneNaziviPoglavlja.setViewportView(textAreaNaziviPoglavlja);
		
		JLabel lblNaziviPoglavlja = new JLabel("Nazivi poglavlja:");
		lblNaziviPoglavlja.setHorizontalAlignment(SwingConstants.CENTER);
		lblNaziviPoglavlja.setBounds(28, 308, 294, 15);
		add(lblNaziviPoglavlja);
		
		
		JButton btnDodajDelo = new JButton("DODAJ DELO");
		btnDodajDelo.setBackground(Color.ORANGE);
		btnDodajDelo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			    if (ispravanUnos()) {
			    	dodajDelo();
			    }
			}
		});
		btnDodajDelo.setBounds(346, 405, 120, 25);
		add(btnDodajDelo);
		
		JButton btnUputstvo = new JButton("UPUTSTVO");
		btnUputstvo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameUputstvoZaDodavanjeDela frameUputstvoZaDodavanjeDela = new FrameUputstvoZaDodavanjeDela();
				frameUputstvoZaDodavanjeDela.setVisible(true);
			}
		});
		btnUputstvo.setBackground(Color.ORANGE);
		btnUputstvo.setBounds(206, 405, 120, 25);
		add(btnUputstvo);
		
	}
	
	
	public JPanel getPanel() {
		return this;
	}
	
	public boolean ispravanUnos() {		
		
		Warning warningDialog = new Warning("Pogrešan unos");
		
		if (!(textFieldNazivDela.getText().trim().length() > 0)) {
			warningDialog.showMessage("Polje za naziv dela mora biti popunjeno!");
			return false;
		}
		if(dateChooser.getDate()==null) {
			warningDialog.showMessage("Polje za datum izdavanja dela mora biti popunjeno!");
			return false;
		}
		if (!(textFieldIzdavac.getText().trim().length() > 0)) {
			warningDialog.showMessage("Polje za izdavača dela mora biti popunjeno!");
			return false;
		}
		if (!(textFieldPreview.getText().trim().length() > 0)) {
			warningDialog.showMessage("Polje za preview naslovne strane dela mora biti popunjeno!");
			return false;
		}
		if ((!(textAreaAutori.getText().trim().length() > 0)) && (!(textAreaEditori.getText().trim().length() > 0)) && (!(textAreaMentori.getText().trim().length() > 0))) {
			warningDialog.showMessage("Delo mora imati barem jedno od sledećeg: autori, editori, mentori");
			return false;
		}
		if (textAreaNaziviPoglavlja.getText().split("\\r?\\n").length != textAreaAutoriPoglavlja.getText().split("\\r?\\n").length) {
			warningDialog.showMessage("Svako poglavlje mora imati barem jednog autora");
			return false;
		}
		
		return true;
	}
	
	public void dodajDelo() {
		
		this.con = connection.createConnection();
			
		String idTipaDela = null;
		String query = "select id_tipa from TipDela where naziv_tipa_dela='"+comboBox.getSelectedItem().toString()+"'";
	    try (Statement stmt = this.con.createStatement()) {
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	idTipaDela = String.valueOf(rs.getInt("id_tipa"));
	        }
	     } catch (SQLException e) {
	    	  Warning w = new Warning("Upozorenje");
	    	  w.showMessage(e.getMessage());
	     }
	
			
	    boolean success = false;
		String insertIntoAutorskoDelo = "insert into AutorskoDelo (id_tipa, naziv_dela, vreme_izdanja, izdavac, ISBN_broj, preview_putanja, ukupna_kolicina, trenutno_stanje) values (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStmtAutorskoDelo;
		try {
			preparedStmtAutorskoDelo = con.prepareStatement(insertIntoAutorskoDelo);
			preparedStmtAutorskoDelo.setInt(1, Integer.valueOf(idTipaDela));
			preparedStmtAutorskoDelo.setString(2, textFieldNazivDela.getText());
			Date date = dateChooser.getDate();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
			String currentDateTime = format.format(date);
			preparedStmtAutorskoDelo.setString(3, currentDateTime);
			preparedStmtAutorskoDelo.setString(4, textFieldIzdavac.getText());
			preparedStmtAutorskoDelo.setString(5, textFieldISBN.getText());
			preparedStmtAutorskoDelo.setString(6, textFieldPreview.getText());
			preparedStmtAutorskoDelo.setInt(7, Integer.valueOf(String.valueOf(modelSpinner.getValue())));
			preparedStmtAutorskoDelo.setInt(8, Integer.valueOf(String.valueOf(modelSpinner.getValue())));
			
			// execute the preparedstatement
		    preparedStmtAutorskoDelo.execute();
		    success = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			success = false;
			//e.printStackTrace();
		}
		if (success) {
			Warning successDialog = new Warning("Uspeh");
			successDialog.showMessage("Uspešno ste dodali delo!");
		}
		else {
			Warning successDialog = new Warning("Greška");
			successDialog.showMessage("Došlo je do greške pri dodavanju dela!");
		}

		
		
		int poslednjiIdDela = poslednjiIdDela();
		
		if (textAreaAutori.getText().trim().length() > 0) {
			String autori[] = textAreaAutori.getText().split("\\r?\\n");
			int i;
			for (i=0; i<autori.length; i++) {
				String insertIntoVlasnikDela = "insert into VlasnikDela (zvanje_ime_prezime, tip_vlasnika, id_dela) values (?, ?, ?)";
				PreparedStatement preparedStmtVlasnikDela;
				try {
					preparedStmtVlasnikDela = con.prepareStatement(insertIntoVlasnikDela);
					preparedStmtVlasnikDela.setString(1, autori[i]);
					preparedStmtVlasnikDela.setString(2, "autor");
					preparedStmtVlasnikDela.setInt(3, poslednjiIdDela);
					
				    preparedStmtVlasnikDela.execute();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		if (textAreaEditori.getText().trim().length() > 0) {
			String editori[] = textAreaEditori.getText().split("\\r?\\n");
			int i;
			for (i=0; i<editori.length; i++) {
				String insertIntoVlasnikDela = "insert into VlasnikDela (zvanje_ime_prezime, tip_vlasnika, id_dela) values (?, ?, ?)";
				PreparedStatement preparedStmtVlasnikDela;
				try {
					preparedStmtVlasnikDela = con.prepareStatement(insertIntoVlasnikDela);
					preparedStmtVlasnikDela.setString(1, editori[i]);
					preparedStmtVlasnikDela.setString(2, "editor");
					preparedStmtVlasnikDela.setInt(3, poslednjiIdDela);
					
				    preparedStmtVlasnikDela.execute();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}	
		}
		if (textAreaMentori.getText().trim().length() > 0) {
			String mentori[] = textAreaMentori.getText().split("\\r?\\n");
			int i;
			for (i=0; i<mentori.length; i++) {
				String insertIntoVlasnikDela = "insert into VlasnikDela (zvanje_ime_prezime, tip_vlasnika, id_dela) values (?, ?, ?)";
				PreparedStatement preparedStmtVlasnikDela;
				try {
					preparedStmtVlasnikDela = con.prepareStatement(insertIntoVlasnikDela);
					preparedStmtVlasnikDela.setString(1, mentori[i]);
					preparedStmtVlasnikDela.setString(2, "mentor");
					preparedStmtVlasnikDela.setInt(3, poslednjiIdDela);
					
				    preparedStmtVlasnikDela.execute();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		if ((textAreaNaziviPoglavlja.getText().trim().length() > 0) && (textAreaAutoriPoglavlja.getText().trim().length() > 0)) {
			String poglavlja[] = textAreaNaziviPoglavlja.getText().split("\\r?\\n");
			String autoriPoglavlja[] = textAreaAutoriPoglavlja.getText().split("\\r?\\n");
			int i;
			for (i=0; i<poglavlja.length; i++) {
				String insertIntoPoglavlje = "insert into Poglavlje (naziv_poglavlja, id_dela) values (?, ?)";
				PreparedStatement preparedStmtPoglavlje;
				try {
					preparedStmtPoglavlje = con.prepareStatement(insertIntoPoglavlje);
					preparedStmtPoglavlje.setString(1, poglavlja[i]);
					preparedStmtPoglavlje.setInt(2, poslednjiIdDela);
					
				    preparedStmtPoglavlje.execute();
				
					String insertIntoVlasnikDela = "insert into VlasnikDela (zvanje_ime_prezime, tip_vlasnika, id_poglavlja) values (?, ?, ?)";
					PreparedStatement preparedStmtVlasnikDela;
					try {
						preparedStmtVlasnikDela = con.prepareStatement(insertIntoVlasnikDela);
						preparedStmtVlasnikDela.setString(1, autoriPoglavlja[i]);
						preparedStmtVlasnikDela.setString(2, "autor_poglavlja");
						preparedStmtVlasnikDela.setInt(3, poslednjiIdPoglavlja());
						
					    preparedStmtVlasnikDela.execute();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		connection.closeConnection();
	}
	
	public int poslednjiIdDela() {
		
		int idDela = 0;
		String query = "select id_dela from AutorskoDelo where id_dela = (select max(id_dela) from AutorskoDelo)";
	    try (Statement stmt = this.con.createStatement()) {
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	idDela = rs.getInt("id_dela");
	        }
	     } catch (SQLException e) {
	    	  Warning w = new Warning("Upozorenje");
	    	  w.showMessage(e.getMessage());
	     }
		return idDela;
	}
	
	public int poslednjiIdPoglavlja() {
		
		int idPoglavlja = 0;
		String query = "select id_poglavlja from Poglavlje where id_poglavlja = (select max(id_poglavlja) from Poglavlje)";
	    try (Statement stmt = this.con.createStatement()) {
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	idPoglavlja = rs.getInt("id_poglavlja");
	        }
	     } catch (SQLException e) {
	    	  Warning w = new Warning("Upozorenje");
	    	  w.showMessage(e.getMessage());
	     }
		return idPoglavlja;
	}
}
