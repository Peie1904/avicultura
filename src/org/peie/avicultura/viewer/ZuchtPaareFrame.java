package org.peie.avicultura.viewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;


import javax.swing.JDesktopPane;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.peie.avicultura.helper.AviInternalFrame;
import org.peie.avicultura.helper.AviculturaException;
import org.peie.avicultura.helper.DbHelper;
import org.peie.avicultura.helper.ZuchtPaareObj;

public class ZuchtPaareFrame {
	
	private JDesktopPane desktopPane;
	private DbHelper dbhelper;
	private JTable table;
	private JPanel taskBar;

	public ZuchtPaareFrame(JDesktopPane desktopPane,DbHelper dbhelper,JPanel taskBar) {
		super();
		this.desktopPane = desktopPane;
		this.dbhelper = dbhelper;
		this.taskBar = taskBar;
	}
	
	public void showInternalFrame() throws AviculturaException{
		
		Dimension screenDesktop = desktopPane.getSize();

		int heigth = screenDesktop.height - 200;
		int width = screenDesktop.width - 200;

		Dimension minSize = new Dimension(700, 500);
		
		final AviInternalFrame internalFrame = new AviInternalFrame(taskBar,
				"Zuchtpaare");
		//internalFrame.setFrameIcon(new ImageIcon(iconFindBird));
		internalFrame.setClosable(true);
		internalFrame.setMaximumSize(screenDesktop);
		internalFrame.setSize(width, heigth);
		internalFrame.setMinimumSize(minSize);
		// internalFrame.setBounds(10, 11, 596, 416);
		internalFrame.setResizable(true);
		internalFrame.setMaximizable(true);
		
		
		desktopPane.add(internalFrame);
		
		dbhelper.fillPairTables();
		
		List<ZuchtPaareObj> zpoList = dbhelper.getZuchtPaareData();
		
		final Object[][] inTable = new Object[zpoList.size()][8];
		
		int i = 0;
		
		for (ZuchtPaareObj zpo : zpoList) {
			
			inTable[i][0] = zpo.getBirdpairno();
			inTable[i][1] = zpo.getPaparing();
			inTable[i][2] = zpo.getPapavogel();
			inTable[i][3] = zpo.getPapafarbe();
			
			inTable[i][4] = zpo.getMamaring();
			inTable[i][5] = zpo.getMamavogel();
			inTable[i][6] = zpo.getMamafarbe();
			
			i++;
		}
		
		table = new JTable();

		table.setModel(new DefaultTableModel(inTable, new String[] {
				"Zuchtpaar","Vater Ringnr.","Vater Art","Vater Farbe","Mutter Ringnr.","Mutter Art","Mutter Farbe" }) {
			/**
					 * 
					 */
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] { Integer.class,String.class, String.class,
					Object.class, Object.class, Object.class, Object.class,
					Object.class, Long.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
		});

		table.setAutoCreateRowSorter(true);
		
		internalFrame.getContentPane().add(table.getTableHeader(),
				BorderLayout.NORTH);
		JScrollPane pane = new JScrollPane(table);
		internalFrame.getContentPane().add(pane, BorderLayout.CENTER);
		
		
		internalFrame.setVisible(true);
		
	}

}
