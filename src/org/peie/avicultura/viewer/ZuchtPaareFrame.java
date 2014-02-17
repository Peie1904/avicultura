package org.peie.avicultura.viewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.peie.avicultura.helper.AviInternalFrame;
import org.peie.avicultura.helper.AviculturaException;
import org.peie.avicultura.helper.ChildObj;
import org.peie.avicultura.helper.DbHelper;
import org.peie.avicultura.helper.Helper;
import org.peie.avicultura.helper.ZuchtPaareObj;
import org.peie.avicultura.pdf.AufzuchtBlattWriter;
import org.peie.avicultura.viewer.Application.PopupPrintListener;

public class ZuchtPaareFrame {

	private JDesktopPane desktopPane;
	private JFrame frame;
	private DbHelper dbhelper;
	private JTable table;
	private JPanel taskBar;
	private JTable tableCild;
	private FileNameExtensionFilter filter = new FileNameExtensionFilter(
			"PDF Files", "pdf");
	public JPopupMenu popup;

	public ZuchtPaareFrame(JFrame frame, JDesktopPane desktopPane,
			DbHelper dbhelper, JPanel taskBar) {
		super();
		this.desktopPane = desktopPane;
		this.dbhelper = dbhelper;
		this.taskBar = taskBar;
		this.frame = frame;
	}

	public void showInternalFrame() throws AviculturaException {

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

		final Object[][] inTable = new Object[zpoList.size()][9];

		int i = 0;

		for (ZuchtPaareObj zpo : zpoList) {

			inTable[i][0] = zpo.getBirdpairno();
			inTable[i][1] = zpo.getPaparing();
			inTable[i][2] = zpo.getPapavogel();
			inTable[i][3] = zpo.getPapafarbe();

			inTable[i][4] = zpo.getMamaring();
			inTable[i][5] = zpo.getMamavogel();
			inTable[i][6] = zpo.getMamafarbe();
			inTable[i][7] = zpo.getGrandFather();
			inTable[i][8] = zpo.getGrandMother();

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

				if (e.getClickCount() == 2
						&& e.getButton() == MouseEvent.BUTTON1) {
					int row = table.getSelectedRow();
					String zuchtPaarId = table.getValueAt(row, 0).toString();
					String vater = table.getValueAt(row, 1).toString();
					String mutter = table.getValueAt(row, 4).toString();
					String vogelType = table.getValueAt(row, 2).toString();
					String farbeVater = table.getValueAt(row, 3).toString();
					String farbeMutter = table.getValueAt(row, 6).toString();

					String opa = table.getValueAt(row, 7).toString();
					String oma = table.getValueAt(row, 8).toString();

					zuchtPaarAnzeige(zuchtPaarId, vater, mutter, vogelType,
							farbeVater, farbeMutter, opa, oma);

				} else if (e.getClickCount() == 1
						&& e.getButton() == MouseEvent.BUTTON3) {
					int x = e.getX();
					int y = e.getY();
					table.clearSelection();
					int selectedRow = table.rowAtPoint(new Point(x, y));
					table.addRowSelectionInterval(selectedRow, selectedRow);
					String zuchtPaarId = table.getValueAt(selectedRow, 0)
							.toString();
					System.out.println("ZPID " + zuchtPaarId);
					popUp(zuchtPaarId, internalFrame, selectedRow);
				}

			}
		});

		table.setModel(new DefaultTableModel(inTable, new String[]{"Zuchtpaar",
				"Vater Ringnr.", "Vater Art", "Vater Farbe", "Mutter Ringnr.",
				"Mutter Art", "Mutter Farbe", "Opa", "Oma"}) {

			private static final long serialVersionUID = 1L;
			Class[] columnTypes = new Class[]{Integer.class, String.class,
					String.class, Object.class, Object.class, Object.class,
					Object.class, Object.class, Object.class};

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

	private void deleteZuchtPaar(String zuchtPaarId,
			AviInternalFrame internalFrame, int selectedRow) {

		int check = JOptionPane.showConfirmDialog(frame, "Brutpaar "
				+ zuchtPaarId + " lˆschen", "Brutpaar lˆschen?",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
				new ImageIcon(Application.iconDelete));
		if (check == 0) {
			try {
				if (dbhelper.deleteBirdPair(zuchtPaarId)) {
					JOptionPane.showMessageDialog(frame, "Brutpaar"
							+ zuchtPaarId + " wurde gelˆscht",
							"Brutpaar gelˆscht", JOptionPane.PLAIN_MESSAGE,
							new ImageIcon(Application.iconDelete));
					((DefaultTableModel) table.getModel())
							.removeRow(selectedRow);

				} else {
					JOptionPane.showMessageDialog(frame, "Brutpaar"
							+ zuchtPaarId + " wurde NICHT gelˆscht",
							"Brutpaar NICHT gelˆscht",
							JOptionPane.ERROR_MESSAGE, new ImageIcon(
									Application.iconDelete));
				}
			} catch (AviculturaException e) {
				e.viewError(internalFrame);
			}
		}

	}

	private void popUp(final String zuchtPaarId,
			final AviInternalFrame internalFrame, final int selectedRow) {
		popup = new JPopupMenu();
		JLabel popUpLabel = new JLabel("Zuchtpaar: " + zuchtPaarId);
		popUpLabel.setFont(new Font(Application.LABEL_FONT,
				Application.LABEL_FONT_STYLE, Application.LABEL_FONT_SIZE));
		ActionListener menuListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				if (event.getActionCommand().toString()
						.equals(Application.LOESCHEN)) {
					deleteZuchtPaar(zuchtPaarId, internalFrame, selectedRow);
				}

			}
		};

		popup.add(popUpLabel);
		JMenuItem item;
		popup.add(item = new JMenuItem(Application.LOESCHEN, new ImageIcon(
				Application.iconDelete)));
		item.setHorizontalTextPosition(JMenuItem.RIGHT);
		item.addActionListener(menuListener);
		item.setFont(new Font(Application.LABEL_FONT,
				Application.LABEL_FONT_STYLE, Application.LABEL_FONT_SIZE));
		popup.setLabel("Justification");
		popup.setBorder(new BevelBorder(BevelBorder.RAISED));
		// popup.addPopupMenuListener(new PopupPrintListener());

		Point mousePos = MouseInfo.getPointerInfo().getLocation();

		int xMouse = mousePos.x;// + 40;
		int yMouse = mousePos.y;// + 10;

		popup.show(frame, xMouse, yMouse);

	}

	private void zuchtPaarAnzeige(final String zuchtPaarId, final String vater,
			final String mutter, final String vogelType,
			final String farbeVater, final String farbeMutter,
			final String opa, final String oma) {

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
		lblVater.setBounds(10, 36, 90, 14);
		internalFrame.getContentPane().add(lblVater);

		if (opa.length() > 0) {
			JLabel lblGrossVater = new JLabel("Groﬂvater: ");
			lblGrossVater.setBounds(10, 56, 90, 14);
			internalFrame.getContentPane().add(lblGrossVater);

			JLabel label_3 = new JLabel(opa);
			label_3.setBounds(95, 56, 86, 14);
			internalFrame.getContentPane().add(label_3);
		}

		JLabel label = new JLabel(vater);
		label.setBounds(95, 36, 86, 14);
		internalFrame.getContentPane().add(label);

		JLabel lblMutter = new JLabel("Mutter:");
		lblMutter.setBounds(200, 36, 90, 14);
		internalFrame.getContentPane().add(lblMutter);

		if (oma.length() > 0) {
			JLabel lblGrossMutter = new JLabel("Groﬂmutter:");
			lblGrossMutter.setBounds(200, 56, 90, 14);
			internalFrame.getContentPane().add(lblGrossMutter);

			JLabel label_2 = new JLabel(oma);
			label_2.setBounds(300, 56, 86, 14);
			internalFrame.getContentPane().add(label_2);
		}

		JLabel label_1 = new JLabel(mutter);
		label_1.setBounds(300, 36, 86, 14);
		internalFrame.getContentPane().add(label_1);

		JLabel lblNewLabel = new JLabel(vogelType);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 11, 107, 14);
		internalFrame.getContentPane().add(lblNewLabel);

		JButton btnAuzuchtvordruck = new JButton("Aufzuchtvordruck");
		btnAuzuchtvordruck.setBounds(10, 387, 189, 23);
		internalFrame.getContentPane().add(btnAuzuchtvordruck);

		JButton btnAuzuchtList = new JButton("Zuchtpaar drucken");
		btnAuzuchtList.setBounds(210, 387, 189, 23);
		internalFrame.getContentPane().add(btnAuzuchtList);

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
					.setModel(new DefaultTableModel(inTable, new String[]{
							"Lfd.", "Ringnummer", "Beringt am", "Farbe",
							"Geschlecht"}) {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;
						Class[] columnTypes = new Class[]{Integer.class,
								String.class, String.class, Object.class,
								Object.class};

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

		btnAuzuchtvordruck.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				String path = ".";
				String pdfName = "AufzuchtBlatt_" + zuchtPaarId + ".pdf";

				JFileChooser fileChooser = new JFileChooser();

				fileChooser.setCurrentDirectory(new File(path));

				fileChooser.setApproveButtonText("Speichern");
				fileChooser.setMultiSelectionEnabled(false);
				fileChooser.setFileFilter(filter);
				fileChooser.setSelectedFile(new File(path, pdfName));

				int result = fileChooser.showOpenDialog(internalFrame);
				if (result == JFileChooser.APPROVE_OPTION) {

					File aufzuchtBlatt = fileChooser.getSelectedFile();

					// File aufzuchtBlatt = new File("fire.pdf");

					try {
						AufzuchtBlattWriter abw = new AufzuchtBlattWriter(
								vogelType, vater, mutter, farbeVater,
								farbeMutter, zuchtPaarId, opa, oma,
								aufzuchtBlatt);
						abw.writeAufzuchtVorlage();

						AppPdfViewer viewer = new AppPdfViewer(desktopPane,
								taskBar);

						viewer.showPdf(aufzuchtBlatt);

					} catch (AviculturaException e) {
						e.viewError(internalFrame);
					}

				}

			}
		});

		btnAuzuchtList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String path = ".";
				String pdfName = "Zuchtpaar_" + zuchtPaarId + ".pdf";

				JFileChooser fileChooser = new JFileChooser();

				fileChooser.setCurrentDirectory(new File(path));

				fileChooser.setApproveButtonText("Speichern");
				fileChooser.setMultiSelectionEnabled(false);
				fileChooser.setFileFilter(filter);
				fileChooser.setSelectedFile(new File(path, pdfName));

				int result = fileChooser.showOpenDialog(internalFrame);
				if (result == JFileChooser.APPROVE_OPTION) {

					File aufzuchtBlatt = fileChooser.getSelectedFile();

					// File aufzuchtBlatt = new File("fire.pdf");

					try {
						AufzuchtBlattWriter abw = new AufzuchtBlattWriter(
								vogelType, vater, mutter, farbeVater,
								farbeMutter, zuchtPaarId, opa, oma,
								aufzuchtBlatt);

						abw.writeAufzuchtPaarListe(dbhelper);

						AppPdfViewer viewer = new AppPdfViewer(desktopPane,
								taskBar);

						viewer.showPdf(aufzuchtBlatt);

					} catch (AviculturaException e) {
						e.viewError(internalFrame);
					}
				}

			}
		});

	}

}
