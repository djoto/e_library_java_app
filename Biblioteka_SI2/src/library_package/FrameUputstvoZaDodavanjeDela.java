package library_package;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

import java.awt.image.BufferedImage;
import java.awt.*;

public class FrameUputstvoZaDodavanjeDela extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public FrameUputstvoZaDodavanjeDela() {
		ImageIcon img = new ImageIcon("library_icon.png");
		setIconImage(img.getImage());
		setTitle("Uputstvo za dodavanje dela");
		setResizable(false);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(270, 120, 833, 520);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 153));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);


		//JPanel panelImage = new JPanel();
		ImagePanel panelImage = new ImagePanel();
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File("uputstvo_za_dodavanje_dela.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		panelImage.setImage(image);
		panelImage.setBackground(new Color(255, 255, 153));
		panelImage.setBounds(0, 0, 833, 454);
		//JImageComponent ic = new JImageComponent("uputstvo_za_dodavanje_dela.png");
		//panelImage.add(ic);
		contentPane.add(panelImage);
		
		JButton btnZatvori = new JButton("ZATVORI");
		btnZatvori.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				getFrame().dispose();
			}
		});
		btnZatvori.setBackground(Color.ORANGE);
		btnZatvori.setBounds(368, 457, 98, 25);
		contentPane.add(btnZatvori);
	}
	
	public JFrame getFrame() {
		return this;
	}
	
	public class ImagePanel extends JPanel {

		private java.awt.Image image;
		private boolean stretched = true;
		private int xCoordinate;
		private int yCoordinate;

		public ImagePanel() {

		}

		public ImagePanel(Image image) {
		    this.image = image;
		}

		@Override
		protected void paintComponent(Graphics g) {
		    super.paintComponent(g);
		    if (image != null) {
		        if (isStretched()) {
		            g.drawImage(image, xCoordinate, yCoordinate, getWidth(), getHeight(), this);
		        } else {
		            g.drawImage(image, xCoordinate, yCoordinate, this);
		        }
		    }
		}

		public java.awt.Image getImage() {
		    return image;
		}

		public void setImage(java.awt.Image image) {
		    this.image = image;
		    repaint();
		}

		public boolean isStretched() {
		    return stretched;
		}

		public void setStretched(boolean stretched) {
		    this.stretched = stretched;
		    repaint();
		}

		public int getXCoordinate() {
		    return xCoordinate;
		}

		public void setXCoodinate(int xCoordinate) {
		    this.xCoordinate = xCoordinate;
		}

		public int getYCoordinate() {
		    return xCoordinate;
		}

		public void setYCoordinate(int yCoordinate) {
		    this.yCoordinate = yCoordinate;
		    repaint();
		}
	}

}
