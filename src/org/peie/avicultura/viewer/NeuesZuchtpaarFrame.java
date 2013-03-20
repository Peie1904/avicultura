package org.peie.avicultura.viewer;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;

import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JPanel;
import javax.swing.JTable;

import org.peie.avicultura.helper.AviInternalFrame;
import org.peie.avicultura.helper.DbHelper;

public class NeuesZuchtpaarFrame {
	
	private JDesktopPane desktopPane;
	private DbHelper dbhelper;
	private JPanel taskBar;
	
	
	public NeuesZuchtpaarFrame(JDesktopPane desktopPane, DbHelper dbhelper,
			JPanel taskBar){
		super();
		this.desktopPane = desktopPane;
		this.dbhelper = dbhelper;
		this.taskBar = taskBar;
		
	}
	
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void showFrame(){
		AviInternalFrame internalFrame = new AviInternalFrame(taskBar, "Neues Zuchtpaar");
		
		Dimension screenDesktop = desktopPane.getSize();

		int heigth = 300;//screenDesktop.height - 400;
		int width = 300;//screenDesktop.width - 400;

		Dimension minSize = new Dimension(200, 200);
		
		internalFrame.setClosable(true);
		internalFrame.setMaximumSize(screenDesktop);
		internalFrame.setSize(width, heigth);
		internalFrame.setMinimumSize(minSize);
		// internalFrame.setBounds(10, 11, 596, 416);
		internalFrame.setResizable(true);
		internalFrame.setMaximizable(true);
		internalFrame.setVisible(true);
		
		GridBagLayout gbl = new GridBagLayout();
		internalFrame.setLayout(gbl);
		GridBagConstraints gbc = new GridBagConstraints();
		
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		gbc.insets = new Insets(2, 2, 2, 2);
		
		Label vaterVogel = new Label("Vater Vogel: ");
		vaterVogel.setSize(new Dimension(150, 150));
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		gbl.setConstraints(vaterVogel, gbc);
		internalFrame.add(vaterVogel);
		
		JComboBox<String> vaterVogelListe = new JComboBox<String>();
		
		vaterVogelListe.addItem("N/A");
		vaterVogelListe.setSize(new Dimension(150, 150));
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		
		gbl.setConstraints(vaterVogelListe, gbc);
		
		internalFrame.add(vaterVogelListe);
		
		Label mutterVogel = new Label("Mutter Vogel: ");
		mutterVogel.setSize(new Dimension(150, 150));
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		gbl.setConstraints(mutterVogel, gbc);
		internalFrame.add(mutterVogel);
		
		
		
		

		desktopPane.add(internalFrame);
	}

}
