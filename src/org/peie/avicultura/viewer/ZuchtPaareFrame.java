package org.peie.avicultura.viewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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

	public ZuchtPaareFrame(JDesktopPane desktopPane, DbHelper dbhelper,
			JPanel taskBar) {
		super();
		this.desktopPane = desktopPane;
		this.dbhelper = dbhelper;
		this.taskBar = taskBar;
	}

	public void showInternalFrame() throws AviculturaException {

		Dimension screenDesktop = desktopPane.getSize();

		int heigth = screenDesktop.height - 200;
		int width = screenDesktop.width - 200;

		Dimension minSize = new Dimension(700, 500);

		final AviInternalFrame internalFrame = new AviInternalFrame(taskBar,
				"Zuchtpaare");
		// internalFrame.setFrameIcon(new ImageIcon(iconFindBird));
		internalFrame.setClosable(true);
		internalFrame.setMaximumSize(screenDesktop);
		internalFrame.setSize(width, heigth);
		internalFrame.setMinimumSize(minSize);
		// internalFrame.setBounds(10, 11, 596, 416);
		internalFrame.setResizable(true);
		internalFrame.setMaximizable(true);

		desktopPane.add(internalFrame);

		//dbhelper.fillPairTables();

		List<ZuchtPaareObj> zpoList = dbhelper.getZuchtPaareDataForTable();

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
		
		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();

				Point mousePos = MouseInfo.getPointerInfo().getLocation();

				int xMouse = mousePos.x + 40;
				int yMouse = mousePos.y + 10;

				if (e.getClickCount() == 2
						&& e.getButton() == MouseEvent.BUTTON1) {
					int row = table.getSelectedRow();
					String zuchtPaarId = table.getValueAt(row, 0).toString();

					zuchtPaarAnzeige(zuchtPaarId);

				}

				if (e.getButton() == MouseEvent.BUTTON3) {

					table.clearSelection();
					int fire = table.rowAtPoint(new Point(x, y));
					table.addRowSelectionInterval(fire, fire);
					String zuchtPaarId = table.getValueAt(fire, 0).toString();

					
				}
			}
		});

		table.setModel(new DefaultTableModel(inTable, new String[] {
				"Zuchtpaar", "Vater Ringnr.", "Vater Art", "Vater Farbe",
				"Mutter Ringnr.", "Mutter Art", "Mutter Farbe" }) {
			/**
					 * 
					 */
			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[] { Integer.class, String.class,
					String.class, Object.class, Object.class, Object.class,
					Object.class, Object.class, Long.class };

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
	
	
	private void zuchtPaarAnzeige(String zuchtPaarId){
		
		Dimension screenDesktop = desktopPane.getSize();

		int heigth = screenDesktop.height - 200;
		int width = screenDesktop.width - 200;

		Dimension minSize = new Dimension(700, 500);

		final AviInternalFrame internalFrame = new AviInternalFrame(taskBar,
				"Zuchtpaar: "+zuchtPaarId);
		// internalFrame.setFrameIcon(new ImageIcon(iconFindBird));
		internalFrame.setClosable(true);
		internalFrame.setMaximumSize(screenDesktop);
		internalFrame.setSize(width, heigth);
		internalFrame.setMinimumSize(minSize);
		// internalFrame.setBounds(10, 11, 596, 416);
		internalFrame.setResizable(true);
		internalFrame.setMaximizable(true);

		desktopPane.add(internalFrame);
		
		
		internalFrame.setVisible(true);
		
	}

}
