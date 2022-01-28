package library_package;


import java.awt.BorderLayout;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;



import java.sql.Connection;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;


public class FrameDeloKorisnik extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldIDDela;
	private JTextField textFieldNazivDela;
	private JTextField textFieldTipDela;
	private JTextField textFieldVremeIzdanja;
	private JTextField textFieldIzdavac;
	private JTextField textFieldISBNBroj;
	private JTextField textFieldUkupnaKolicina;
	private JTextField textFieldRaspolozivo;

	
	
	private DefaultTableModel contentModelAutori;
	private DefaultTableModel contentModelEditori;
	private DefaultTableModel contentModelMentori;
	private DefaultTableModel contentModelPoglavlja;
	
	private MySQLConnection connection = new MySQLConnection();
	private Connection con = null;


	/**
	 * Create the frame.
	 */
	public FrameDeloKorisnik(String idDela) {
		ImageIcon img = new ImageIcon("library_icon.png");
		setIconImage(img.getImage());
		setTitle("Podaci o delu");
		setResizable(false);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(270, 120, 833, 520);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblIDDela = new JLabel("ID dela:");
		lblIDDela.setHorizontalAlignment(SwingConstants.TRAILING);
		lblIDDela.setBounds(43, 24, 128, 15);
		contentPane.add(lblIDDela);
		
		JLabel lblNazivDela = new JLabel("Naziv dela:");
		lblNazivDela.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNazivDela.setBounds(43, 51, 128, 15);
		contentPane.add(lblNazivDela);
		
		JLabel lblTipDela = new JLabel("Tip dela:");
		lblTipDela.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTipDela.setBounds(43, 78, 128, 15);
		contentPane.add(lblTipDela);
		
		JLabel lblVremeIzdanja = new JLabel("Vreme/Godina izdanja:");
		lblVremeIzdanja.setHorizontalAlignment(SwingConstants.TRAILING);
		lblVremeIzdanja.setBounds(43, 105, 128, 15);
		contentPane.add(lblVremeIzdanja);
		
		JLabel lblIzdavac = new JLabel("Izdavač:");
		lblIzdavac.setHorizontalAlignment(SwingConstants.TRAILING);
		lblIzdavac.setBounds(43, 132, 128, 15);
		contentPane.add(lblIzdavac);
		
		JLabel lblISBNbroj = new JLabel("ISBN broj:");
		lblISBNbroj.setHorizontalAlignment(SwingConstants.TRAILING);
		lblISBNbroj.setBounds(43, 160, 128, 15);
		contentPane.add(lblISBNbroj);
		
		JLabel lblUkupnaKolicina = new JLabel("Ukupna količina:");
		lblUkupnaKolicina.setHorizontalAlignment(SwingConstants.TRAILING);
		lblUkupnaKolicina.setBounds(43, 187, 128, 15);
		contentPane.add(lblUkupnaKolicina);
		
		JLabel lblRaspolozivo = new JLabel("Raspoloživo stanje:");
		lblRaspolozivo.setHorizontalAlignment(SwingConstants.TRAILING);
		lblRaspolozivo.setBounds(43, 214, 128, 15);
		contentPane.add(lblRaspolozivo);
		
		JLabel lblNaslovnaStrana = new JLabel("Naslovna strana:");
		lblNaslovnaStrana.setHorizontalAlignment(SwingConstants.TRAILING);
		lblNaslovnaStrana.setBounds(43, 241, 128, 15);
		contentPane.add(lblNaslovnaStrana);
		
		textFieldIDDela = new JTextField();
		textFieldIDDela.setEditable(false);
		textFieldIDDela.setBounds(178, 20, 65, 19);
		contentPane.add(textFieldIDDela);
		textFieldIDDela.setColumns(10);
		
		textFieldNazivDela = new JTextField();
		textFieldNazivDela.setEditable(false);
		textFieldNazivDela.setBounds(178, 47, 284, 19);
		contentPane.add(textFieldNazivDela);
		textFieldNazivDela.setColumns(10);
		
		textFieldTipDela = new JTextField();
		textFieldTipDela.setEditable(false);
		textFieldTipDela.setBounds(178, 74, 284, 19);
		contentPane.add(textFieldTipDela);
		textFieldTipDela.setColumns(10);
		
		textFieldVremeIzdanja = new JTextField();
		textFieldVremeIzdanja.setEditable(false);
		textFieldVremeIzdanja.setBounds(178, 101, 284, 19);
		contentPane.add(textFieldVremeIzdanja);
		textFieldVremeIzdanja.setColumns(10);
		
		textFieldIzdavac = new JTextField();
		textFieldIzdavac.setEditable(false);
		textFieldIzdavac.setBounds(178, 130, 284, 19);
		contentPane.add(textFieldIzdavac);
		textFieldIzdavac.setColumns(10);
		
		textFieldISBNBroj = new JTextField();
		textFieldISBNBroj.setEditable(false);
		textFieldISBNBroj.setBounds(178, 156, 284, 19);
		contentPane.add(textFieldISBNBroj);
		textFieldISBNBroj.setColumns(10);
		
		textFieldUkupnaKolicina = new JTextField();
		textFieldUkupnaKolicina.setEditable(false);
		textFieldUkupnaKolicina.setBounds(178, 183, 65, 19);
		contentPane.add(textFieldUkupnaKolicina);
		textFieldUkupnaKolicina.setColumns(10);
		
		textFieldRaspolozivo = new JTextField();
		textFieldRaspolozivo.setEditable(false);
		textFieldRaspolozivo.setBounds(178, 210, 65, 19);
		contentPane.add(textFieldRaspolozivo);
		textFieldRaspolozivo.setColumns(10);
		
		
		
		TableModel tableModel = new TableModel();
		
		
		JPanel innerPanelAutori = new JPanel();
		innerPanelAutori.setBounds(497, 24, 219, 77);
		//innerPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		innerPanelAutori.setLayout(new BorderLayout(0, 0));
		
		String queryAutori = podesiUpit(idDela, "autor");
		contentModelAutori = tableModel.generateModel(queryAutori);
		
		JTable tableAutori = new JTable(contentModelAutori);
		tableAutori.setDefaultEditor(Object.class, null);
		DefaultTableCellRenderer rendererAutori = (DefaultTableCellRenderer)tableAutori.getDefaultRenderer(Object.class);
		rendererAutori.setHorizontalAlignment( SwingConstants.CENTER );
        JScrollPane scrollPaneAutori = new JScrollPane( tableAutori );
        innerPanelAutori.add( scrollPaneAutori );
		
        contentPane.add(innerPanelAutori);
		
        
        
        
        
		JPanel innerPanelEditori = new JPanel();
		innerPanelEditori.setBounds(497, 122, 219, 77);
		//innerPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		innerPanelEditori.setLayout(new BorderLayout(0, 0));
		
		String queryEditori = podesiUpit(idDela, "editor");
		contentModelEditori = tableModel.generateModel(queryEditori);
		
		JTable tableEditori = new JTable(contentModelEditori);
		tableEditori.setDefaultEditor(Object.class, null);
		DefaultTableCellRenderer rendererEditori = (DefaultTableCellRenderer)tableEditori.getDefaultRenderer(Object.class);
		rendererEditori.setHorizontalAlignment( SwingConstants.CENTER );
        JScrollPane scrollPaneEditori = new JScrollPane( tableEditori );
        innerPanelEditori.add( scrollPaneEditori );
		
        contentPane.add(innerPanelEditori);
		
        
        
		JPanel innerPanelMentori = new JPanel();
		innerPanelMentori.setBounds(497, 222, 219, 77);
		innerPanelMentori.setLayout(new BorderLayout(0, 0));
		
		String queryMentori = podesiUpit(idDela, "mentor");
		contentModelMentori = tableModel.generateModel(queryMentori);
		
		JTable tableMentori = new JTable(contentModelMentori);
		tableMentori.setDefaultEditor(Object.class, null);
		DefaultTableCellRenderer rendererMentori = (DefaultTableCellRenderer)tableMentori.getDefaultRenderer(Object.class);
		rendererMentori.setHorizontalAlignment( SwingConstants.CENTER );
        JScrollPane scrollPaneMentori = new JScrollPane( tableMentori );
        innerPanelMentori.add( scrollPaneMentori );
		
        contentPane.add(innerPanelMentori);
        
        
        
        
        JPanel innerPanelPoglavlja = new JPanel();
        innerPanelPoglavlja.setBounds(390, 322, 418, 83);
        
        innerPanelPoglavlja.setLayout(new BorderLayout(0, 0));
		
		String queryPoglavlja = podesiUpit(idDela, "autor_poglavlja");
		contentModelPoglavlja = tableModel.generateModel(queryPoglavlja);
		
		JTable tablePoglavlja = new JTable(contentModelPoglavlja);
		tablePoglavlja.setDefaultEditor(Object.class, null);
		DefaultTableCellRenderer rendererPoglavlja = (DefaultTableCellRenderer)tablePoglavlja.getDefaultRenderer(Object.class);
		rendererPoglavlja.setHorizontalAlignment( SwingConstants.CENTER );
        JScrollPane scrollPanePoglavlja = new JScrollPane( tablePoglavlja );
        innerPanelPoglavlja.add( scrollPanePoglavlja );
        
        contentPane.add(innerPanelPoglavlja);
        
        
        
        JButton btnZatvori = new JButton("ZATVORI");
        btnZatvori.setBackground(Color.ORANGE);
        btnZatvori.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		getFrame().dispose();
        	}
        });
        btnZatvori.setBounds(364, 442, 98, 25);
        contentPane.add(btnZatvori);
             
        
        ucitajPodatkeODelu(idDela);
		
	}	
	
	
	public JPanel getContentPane() {
		return this.contentPane;
	}
	
	public Integer brojRaspolozivih(){
		return Integer.valueOf(this.textFieldRaspolozivo.getText());
	}
	
	
	public void ucitajPodatkeODelu(String idDela) {
		
		this.con = connection.createConnection();
		
		textFieldIDDela.setText(idDela);
		
		String query = "select d.naziv_dela, t.naziv_tipa_dela, d.vreme_izdanja, d.izdavac, d.ISBN_broj, d.preview_putanja, d.ukupna_kolicina, d.trenutno_stanje FROM AutorskoDelo d, TipDela t where t.id_tipa=d.id_tipa and d.id_dela="+idDela;
		
	    try (Statement stmt = this.con.createStatement()) {
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	textFieldNazivDela.setText(rs.getString("naziv_dela"));
	        	textFieldTipDela.setText(rs.getString("naziv_tipa_dela"));
	        	DateFormat dateFormat;
	        	if(rs.getString("naziv_tipa_dela").equals("Časopis")) {
	        		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        	}
	        	else {
	        		dateFormat = new SimpleDateFormat("yyyy");
	        	}
	        	textFieldVremeIzdanja.setText(dateFormat.format(rs.getDate("vreme_izdanja")));
	        	textFieldIzdavac.setText(rs.getString("izdavac"));
	        	textFieldISBNBroj.setText(rs.getString("ISBN_broj"));
	        	textFieldUkupnaKolicina.setText((String) rs.getString("ukupna_kolicina"));
	        	textFieldRaspolozivo.setText((String) rs.getString("trenutno_stanje"));
	        	JLabel lblPreview = setPreviewLabel(rs.getString("preview_putanja"));
	        	contentPane.add(lblPreview);
	        }
	     } catch (SQLException e) {
	    	  Warning w = new Warning("Upozorenje");
	    	  w.showMessage(e.getMessage());
	     }
        
		
		connection.closeConnection();
		
		
	}
	
	
	public void podesiRaspolozivoUkupnoStanje(String idDela) {
		
		this.con = connection.createConnection();
		
		String query = "select d.trenutno_stanje, d.ukupna_kolicina from AutorskoDelo as d where id_dela="+idDela;
		
	    try (Statement stmt = this.con.createStatement()) {
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	        	textFieldRaspolozivo.setText((String) rs.getString("trenutno_stanje"));
	        	textFieldUkupnaKolicina.setText((String) rs.getString("ukupna_kolicina"));
	        }
	     } catch (SQLException e) {
	    	  Warning w = new Warning("Upozorenje");
	    	  w.showMessage(e.getMessage());
	     }
		
		connection.closeConnection();
		
	}
	
	public JLabel setPreviewLabel(String path) {
			JLabel lblPreview = new JLabel("preview");
	        lblPreview.setBounds(178, 241, 55, 15);
	        lblPreview.setForeground(Color.BLUE.darker());
	        lblPreview.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	        lblPreview.addMouseListener(new MouseAdapter() {
	        	 
	            @Override
	            public void mouseClicked(MouseEvent e) {
	                // the user clicks on the label
	
	            	File f = new File(path);
	                Desktop dt = Desktop.getDesktop();
	                try {
						dt.open(f);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						//e1.printStackTrace();
					}
	            }
	         
	            @Override
	            public void mouseEntered(MouseEvent e) {
	                // the mouse has entered the label
	            	lblPreview.setText("<html><a href=''>preview</a></html>");
	            }
	         
	            @Override
	            public void mouseExited(MouseEvent e) {
	                // the mouse has exited the label
	            	lblPreview.setText("preview");
	            }
	        });
	        
	        return lblPreview;
	}
	
	public JFrame getFrame() {
		return this;
	}
	
	
	public String podesiUpit(String idDela, String tip_vlasnika) {
		
		String columnName = "";
		
		switch(tip_vlasnika) {
			case "autor":
				columnName = "Autori";
				break;
			case "editor":
				columnName = "Editori";
				break;
			case "mentor":
				columnName = "Mentori";
				break;
			case "autor_poglavlja":
				columnName = "Autori poglavlja";
				break;
			default:
				columnName = "Nepoznata kolona";
		}
		
		if(!tip_vlasnika.equals("autor_poglavlja")) {
		
			return "select concat(zvanje_ime_prezime) as '" + columnName + "' from VlasnikDela where tip_vlasnika='"+tip_vlasnika+"' and id_dela="+idDela;
		}
		else {
			return "select concat(p.naziv_poglavlja) as 'Naziv poglavlja', concat(v.zvanje_ime_prezime) as '" + columnName + "' from VlasnikDela v, Poglavlje p where v.id_poglavlja=p.id_poglavlja and v.tip_vlasnika='"+tip_vlasnika+"' and p.id_dela="+idDela;
		}
	}
	
}