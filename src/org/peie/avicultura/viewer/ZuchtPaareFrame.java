package org.peie.avicultura.viewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.peie.avicultura.helper.AviculturaException;
import org.peie.avicultura.helper.DbHelper;
import org.peie.avicultura.helper.ZuchtPaareObj;

public class ZuchtPaareFrame {
	
	private JDesktopPane desktopPane;
	private DbHelper dbhelper;
	private JTable table;

	public ZuchtPaareFrame(JDesktopPane desktopPane,DbHelper dbhelper) {
		super();
		this.desktopPane = desktopPane;
		this.dbhelper = dbhelper;
	}
	
	public void showInternalFrame() throws AviculturaException{
		
		Dimension screenDesktop = desktopPane.getSize();

		int heigth = screenDesktop.height - 200;
		int width = screenDesktop.width - 200;

		Dimension minSize = new Dimension(700, 500);
		
		final JInternalFrame internalFrame = new JInternalFrame(
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
		
		List<ZuchtPaareObj> zpoList = dbhelper.getZuchtPaareData();
		
		final Object[][] inTable = new Object[zpoList.size()][8];
		
		int i = 0;
		
		for (ZuchtPaareObj zpo : zpoList) {
			
			inTable[i][0] = zpo.getPaparing();
			inTable[i][1] = zpo.getPapavogel();
			inTable[i][2] = zpo.getPapafarbe();
			
			inTable[i][3] = zpo.getMamaring();
			inTable[i][4] = zpo.getMamavogel();
			inTable[i][5] = zpo.getMamafarbe();
			
			i++;
		}
		
		table = new JTable();

		table.setModel(new DefaultTableModel(inTable, new String[] {
				"Vater Ringnr.","Vater Art","Vater Farbe","Mutter Ringnr.","Mutter Art","Mutter Farbe" }) {
			/**
					 * 
					 */
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] { String.class, String.class,
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
