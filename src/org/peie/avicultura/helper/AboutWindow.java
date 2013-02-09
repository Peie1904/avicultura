package org.peie.avicultura.helper;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.peie.avicultura.main.Avicultura;
import org.peie.avicultura.viewer.AppNewBirdWindow;
import org.peie.avicultura.viewer.Application;


public class AboutWindow {
	
	private static BufferedImage iconFrame;

	/**
	 * @wbp.parser.entryPoint
	 */
	public static void show(ImageIcon icon){
		JFrame frame = new JFrame("Über avicultura");
		frame.setBounds(100, 100, 319, 300);
		frame.getContentPane().setLayout(null);
		URL url = ClassLoader.getSystemResource(Application.ICONS_LOGO_SMALL_PNG);
		
		try {
			iconFrame = ImageIO.read(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		frame.setIconImage(iconFrame);
		
		JLabel panel = new JLabel(icon);
		panel.setBounds(10, 11, 284, 190);
		frame.getContentPane().add(panel);
		
		JLabel lblAuthorMichaelPeirick = new JLabel("Author: Michael Peirick");
		lblAuthorMichaelPeirick.setFont(
				new Font(AppNewBirdWindow.LABEL_FONT, AppNewBirdWindow.LABEL_FONT_STYLE, AppNewBirdWindow.LABEL_FONT_SIZE));
		lblAuthorMichaelPeirick.setBounds(10, 217, 122, 14);
		frame.getContentPane().add(lblAuthorMichaelPeirick);
		
		JLabel lblVersionBeta = new JLabel("Version: "+Avicultura.VERSION);
		lblVersionBeta.setFont(
				new Font(AppNewBirdWindow.LABEL_FONT, AppNewBirdWindow.LABEL_FONT_STYLE, AppNewBirdWindow.LABEL_FONT_SIZE));
		lblVersionBeta.setBounds(10, 242, 122, 14);
		frame.getContentPane().add(lblVersionBeta);
		
		
		frame.setVisible(true);
		
		
	}
}
