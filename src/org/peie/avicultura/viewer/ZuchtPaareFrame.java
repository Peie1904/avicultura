package org.peie.avicultura.viewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JDesktopPane;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.peie.avicultura.helper.AviInternalFrame;
import org.peie.avicultura.helper.AviculturaException;
import org.peie.avicultura.helper.ChildObj;
import org.peie.avicultura.helper.DbHelper;
import org.peie.avicultura.helper.Helper;
import org.peie.avicultura.helper.ZuchtPaareObj;

public class ZuchtPaareFrame {

	private JDesktopPane desktopPane;
	private DbHelper dbhelper;
	private JTable table;
	private JPanel taskBar;
	private JTable tableCild;

	public ZuchtPaareFrame(JDesktopPane desktopPane, DbHelper dbhelper,
			JPanel taskBar) {
		super();
		this.desktopPane = desktopPane;
		this.dbhelper = dbhelper;
		this.taskBar = taskBar;
	}

	public void showInternalFrame() throws AviculturaException {

		Dimension screenDesktop = desktopPane.getSize();

		int heigth = 500;// screenDesktop.height - 200;
		int width = 700;// screenDesktop.width - 200;

		Dimension minSize = new Dimension(width, heigth);

		final AviInternalFrame internalFrame = new AviInternalFrame(taskBar,
				"Zuchtpaare");
		// internalFrame.setFrameIcon(new ImageIcon(iconFindBird));
		internalFrame.setClosable(true);
		internalFrame.setMaximumSize(minSize);
		internalFrame.setSize(width, heigth);
		internalFrame.setMinimumSize(minSize);
		// internalFrame.setBounds(10, 11, 596, 416);
		internalFrame.setResizable(true);
		internalFrame.setMaximizable(true);

		desktopPane.add(internalFrame);

		// dbhelper.fillPairTables();

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
					String vater = table.getValueAt(row, 1).toString();
					String mutter = table.getValueAt(row, 4).toString();
					String vogelType = table.getValueAt(row, 2).toString();

					zuchtPaarAnzeige(zuchtPaarId, vater, mutter, vogelType);

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

	private void zuchtPaarAnzeige(String zuchtPaarId, String vater,
			String mutter, String vogelType) {

		int heigth = 500;// screenDesktop.height - 200;
		int width = 540;// screenDesktop.width - 200;

		Dimension minSize = new Dimension(width, heigth);

		final AviInternalFrame internalFrame = new AviInternalFrame(taskBar,
				"Zuchtpaar: " + zuchtPaarId);
		// internalFrame.setFrameIcon(new ImageIcon(iconFindBird));
		internalFrame.setClosable(true);
		internalFrame.setMaximumSize(minSize);
		internalFrame.setSize(width, heigth);
		internalFrame.setMinimumSize(minSize);
		// internalFrame.setBounds(10, 11, 596, 416);
		internalFrame.setResizable(true);
		internalFrame.setMaximizable(true);

		desktopPane.add(internalFrame);

		internalFrame.setVisible(true);

		internalFrame.getContentPane().setLayout(null);

		JLabel lblVater = new JLabel("Vater: ");
		lblVater.setBounds(10, 36, 46, 14);
		internalFrame.getContentPane().add(lblVater);

		JLabel label = new JLabel(vater);
		label.setBounds(55, 36, 86, 14);
		internalFrame.getContentPane().add(label);

		JLabel lblMutter = new JLabel("Mutter:");
		lblMutter.setBounds(153, 36, 46, 14);
		internalFrame.getContentPane().add(lblMutter);

		JLabel label_1 = new JLabel(mutter);
		label_1.setBounds(209, 36, 86, 14);
		internalFrame.getContentPane().add(label_1);

		JLabel lblNewLabel = new JLabel(vogelType);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 11, 107, 14);
		internalFrame.getContentPane().add(lblNewLabel);

		try {
			List<ChildObj> coList = dbhelper.getChilds(zuchtPaarId);

			final Object[][] inTable = new Object[coList.size()][8];

			int i = 0;

			for (ChildObj childObj : coList) {

				inTable[i][0] = i + 1;
				inTable[i][1] = childObj.getRINGNO();
				inTable[i][2] = Helper.getDateString(childObj.getRINGAT(),
						"yyyy/MM/dd");
				inTable[i][3] = childObj.getCOLOR();
				inTable[i][4] = childObj.getGENDER();

				i++;

			}

			tableCild = new JTable();

			tableCild
					.setModel(new DefaultTableModel(inTable, new String[] {
							"Lfd.", "Ringnummer", "Beringt am", "Farbe",
							"Geschlecht" }) {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;
						Class[] columnTypes = new Class[] { Integer.class,
								String.class, String.class, Object.class,
								Object.class };

						public Class getColumnClass(int columnIndex) {
							return columnTypes[columnIndex];
						}

						public boolean isCellEditable(int rowIndex,
								int columnIndex) {
							return false;
						}
					});

			tableCild.setAutoCreateRowSorter(true);

			JPanel panel = new JPanel();
			panel.setBounds(10, 71, 500, 300);
			internalFrame.getContentPane().add(panel);
			panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

			panel.add(tableCild.getTableHeader(), BorderLayout.NORTH);
			JScrollPane pane = new JScrollPane(tableCild);
			panel.add(pane, BorderLayout.CENTER);

		} catch (AviculturaException e) {
			e.viewError(internalFrame);
		}

	}

}
