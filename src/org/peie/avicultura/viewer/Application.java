package org.peie.avicultura.viewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.File;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.apache.log4j.Logger;
import org.peie.avicultura.helper.AboutWindow;
import org.peie.avicultura.helper.AviProperties;
import org.peie.avicultura.helper.AviculturaException;
import org.peie.avicultura.helper.BirdObject;
import org.peie.avicultura.helper.DbHelper;
import org.peie.avicultura.helper.Helper;
import org.peie.avicultura.helper.SearchResults;
import org.peie.avicultura.helper.StammBlattObj;
import org.peie.avicultura.main.Avicultura;
import org.peie.avicultura.pdf.StammBlattWriter;
import org.peie.avicultura.pdf.ZuchtBuchWriter;

public class Application {

	private static final String JUNGTIERSTAMMBLATT = "Jungtierstammblatt";
	public static final String ICONS_LOGO_SMALL_PNG = "icons/logo_small.png";
	public static final String ICONS_SKY_LIGHT_ICONS_PNG_16X16_ACTIVE_NO_PNG = "icons/skyLight/icons/PNG/48x48/_active__no.png";
	public static final String ICONS_SKY_LIGHT_ICONS_PNG_16X16_ACTIVE_COPY_PNG = "icons/skyLight/icons/PNG/16x16/_active__copy.png";
	public static final String ICONS_SKY_LIGHT_ICONS_PNG_48X48_ACTIVE_DOCUMENT_PNG = "icons/skyLight/icons/PNG/48x48/_active__document.png";
	public static final String ICONS_SKY_LIGHT_ICONS_PNG_16X16_ACTIVE_DOCUMENT_PNG = "icons/skyLight/icons/PNG/16x16/_active__document.png";
	public static final String ICONS_SKY_LIGHT_ICONS_PNG_16X16_ACTIVE_REMOVE_PNG = "icons/skyLight/icons/PNG/16x16/_active__remove.png";
	public static final String ICONS_SKY_LIGHT_ICONS_PNG_16X16_ACTIVE_FIND_PNG = "icons/skyLight/icons/PNG/16x16/_active__find.png";
	public static final String ICONS_SKY_LIGHT_ICONS_PNG_16X16_ACTIVE_NEW_PNG = "icons/skyLight/icons/PNG/16x16/_active__new.png";
	public static final String ICONS_SKY_LIGHT_ICONS_PNG_16X16_ACTIVE_SAVE_PNG = "icons/skyLight/icons/PNG/16x16/_active__save.png";
	public static final String ICONS_SKY_LIGHT_ICONS_PNG_16X16_ACTIVE_PDF_PNG = "icons/skyLight/icons/PNG/16x16/_active__pdf.png";
	public static final String ICONS_SKY_LIGHT_ICONS_PNG_16X16_ACTIVE_SETTING_2_PNG = "icons/skyLight/icons/PNG/16x16/_active__settings_2.png";
	public static final String ICONS_SKY_LIGHT_ICONS_PNG_16X16_ACTIVE_ABOUT_PNG = "icons/skyLight/icons/PNG/16x16/_active__about.png";

	private static final String KOPIEREN = "Kopieren";
	private static final String LOESCHEN = "Löschen";
	private static final String BEARBEITEN = "Bearbeiten";
	private static final int LABEL_FONT_STYLE = Font.PLAIN;
	private static final String LABEL_FONT = "Arial";
	private static final int LABEL_FONT_SIZE = 11;
	private FileNameExtensionFilter filter = new FileNameExtensionFilter(
			"PDF Files", "pdf");

	private static final String VOGEL = "Vogel";
	private static final String RING_NUMMER = "Ring Nummer";
	private JFrame frame;
	private final static Logger LOG = Logger.getLogger(Application.class);
	private JTextField searchTextField;
	private JTable table;

	private JDesktopPane desktopPane;
	private DbHelper dbhelper;
	private BufferedImage icon, iconNewBird, iconFindBird, iconError,
			iconDelete, iconEdit, iconEditBig;
	private JPanel navigator;
	private static final JPanel navigatorPanel = new JPanel();
	private List<JLabel> navLables = new LinkedList<JLabel>();
	private AppNewBirdWindow newBirdWindow;
	private BufferedImage iconCopy;
	private BufferedImage iconPdf;
	private BufferedImage iconBackground;
	private BufferedImage iconSettings;
	private BufferedImage iconAbout;
	private AviProperties properties;

	public Application(AviProperties properties) {
		this.properties = properties;
	}

	public Application() {
		properties = new AviProperties();
	}

	/**
	 * @param tableModel
	 * @wbp.parser.entryPoint
	 */
	public void startAviculturaBrowser(final DbHelper dbhelper) {
		this.dbhelper = dbhelper;
		desktopPane = new JDesktopPane();

		newBirdWindow = new AppNewBirdWindow(dbhelper, desktopPane, properties);
		LOG.info("Start App");
		frame = new JFrame(Avicultura.APPLICATION + " " + Avicultura.VERSION);

		try {
			// URL url = Application.class.getResource( "icon.png" );
			URL url = ClassLoader.getSystemResource(ICONS_LOGO_SMALL_PNG);
			URL urlNewBird = ClassLoader
					.getSystemResource(ICONS_SKY_LIGHT_ICONS_PNG_16X16_ACTIVE_NEW_PNG);
			URL urlFindBird = ClassLoader
					.getSystemResource(ICONS_SKY_LIGHT_ICONS_PNG_16X16_ACTIVE_FIND_PNG);
			URL urlError = ClassLoader
					.getSystemResource(ICONS_SKY_LIGHT_ICONS_PNG_16X16_ACTIVE_NO_PNG);

			URL urlDelete = ClassLoader
					.getSystemResource(ICONS_SKY_LIGHT_ICONS_PNG_16X16_ACTIVE_REMOVE_PNG);
			URL urlEdit = ClassLoader
					.getSystemResource(ICONS_SKY_LIGHT_ICONS_PNG_16X16_ACTIVE_DOCUMENT_PNG);
			URL urlEditBig = ClassLoader
					.getSystemResource(ICONS_SKY_LIGHT_ICONS_PNG_48X48_ACTIVE_DOCUMENT_PNG);
			URL urlCopy = ClassLoader
					.getSystemResource(ICONS_SKY_LIGHT_ICONS_PNG_16X16_ACTIVE_COPY_PNG);
			URL urlPdf = ClassLoader
					.getSystemResource(ICONS_SKY_LIGHT_ICONS_PNG_16X16_ACTIVE_PDF_PNG);
			URL urlSettings2 = ClassLoader
					.getSystemResource(ICONS_SKY_LIGHT_ICONS_PNG_16X16_ACTIVE_SETTING_2_PNG);
			URL urlAbout = ClassLoader
					.getSystemResource(ICONS_SKY_LIGHT_ICONS_PNG_16X16_ACTIVE_ABOUT_PNG);
			URL urlBackgroud = ClassLoader
					.getSystemResource("icons/background.JPG");
			icon = ImageIO.read(url);
			iconNewBird = ImageIO.read(urlNewBird);
			iconFindBird = ImageIO.read(urlFindBird);
			iconError = ImageIO.read(urlError);

			iconDelete = ImageIO.read(urlDelete);
			iconEdit = ImageIO.read(urlEdit);
			iconEditBig = ImageIO.read(urlEditBig);
			iconCopy = ImageIO.read(urlCopy);
			iconPdf = ImageIO.read(urlPdf);
			iconBackground = ImageIO.read(urlBackgroud);
			iconSettings = ImageIO.read(urlSettings2);
			iconAbout = ImageIO.read(urlAbout);
			LOG.info("icon: " + url.toString());
			frame.setIconImage(icon);
		} catch (Exception e) {

			LOG.error("", e);
		}

		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

		frame.setSize(screen);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setLocation(0, 0);
		frame.setResizable(true);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnDatei = new JMenu("Datei");
		menuBar.add(mnDatei);

		JMenu mnHelp = new JMenu("Hilfe");
		menuBar.add(mnHelp);

		JMenuItem mntmImport = new JMenuItem("Import");
		mntmImport.setIcon(new ImageIcon(iconSettings));
		mnHelp.add(mntmImport);

		mnHelp.addSeparator();

		JMenuItem mntmAbout = new JMenuItem("Über...");
		mntmAbout.setIcon(new ImageIcon(iconAbout));
		mnHelp.add(mntmAbout);

		JMenuItem mntmNeuerVogel = new JMenuItem("Neuer Vogel");
		mntmNeuerVogel.setIcon(new ImageIcon(iconNewBird));
		mnDatei.add(mntmNeuerVogel);

		mnDatei.addSeparator();

		JMenuItem mntmSave = new JMenuItem("Zuchtbuch speichern...");
		mntmSave.setIcon(new ImageIcon(iconPdf));
		mnDatei.add(mntmSave);

		JMenuItem mntmOpen = new JMenuItem("Zuchtbuch öffnen...");
		mntmOpen.setIcon(new ImageIcon(iconPdf));
		mnDatei.add(mntmOpen);

		JLabel lblNewLabel_1 = new JLabel(" | ");
		menuBar.add(lblNewLabel_1);

		JLabel lblNewLabel = new JLabel("  Suche:  ");
		lblNewLabel.setFont(new Font(LABEL_FONT, LABEL_FONT_STYLE,
				LABEL_FONT_SIZE));
		menuBar.add(lblNewLabel);

		JSeparator separator = new JSeparator();
		separator.setBackground(Color.BLACK);
		separator.setForeground(Color.BLACK);
		menuBar.add(separator);

		final JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setFont(new Font(LABEL_FONT, LABEL_FONT_STYLE, LABEL_FONT_SIZE));

		comboBox.addItem(RING_NUMMER);
		comboBox.addItem(VOGEL);

		/*
		 * comboBox.setModel(new DefaultComboBoxModel(new String[] {
		 * RING_NUMMER, VOGEL }));
		 */
		menuBar.add(comboBox);

		searchTextField = new JTextField();
		// searchTextField.setText("");
		menuBar.add(searchTextField);
		searchTextField.setColumns(10);

		JButton btnSuchen = new JButton("Suchen");
		menuBar.add(btnSuchen);

		desktopPane.setForeground(Color.GRAY);
		desktopPane.setBackground(SystemColor.inactiveCaptionText);
		BorderLayout layout = new BorderLayout();
		BorderLayout layoutNav = new BorderLayout();

		frame.getContentPane().setLayout(layout);

		navigator = new JPanel(layoutNav);
		navigator.setBackground(SystemColor.inactiveCaptionText);
		navigator.setPreferredSize(new Dimension(150, 150));

		JTabbedPane tabBrowser = new JTabbedPane();
		tabBrowser.setBackground(Color.WHITE);
		LOG.info(navigator.getSize().height + " " + navigator.getSize().width
				+ " " + desktopPane.getHeight());

		// tabBrowser.setSize(navigator.getSize());

		// navigatorPanel = new JPanel();
		// System.out.println(navigatorPanel == null);
		navigatorPanel.setBackground(Color.WHITE);

		JPanel systemPanel = new JPanel();
		navigatorPanel.setBackground(Color.WHITE);

		tabBrowser.addTab("Navigator", navigatorPanel);
		tabBrowser.addTab("System", systemPanel);

		navigator.add(tabBrowser, BorderLayout.CENTER);

		frame.getContentPane().add(navigator, BorderLayout.WEST);

		frame.getContentPane().add(desktopPane, BorderLayout.CENTER);

		/*
		 * try { newBirdWindow.openNewBird(); } catch (AviculturaException e1) {
		 * e1.printStackTrace(); }
		 */

		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent we) {

				LOG.info("stop app");
				try {
					dbhelper.closeConnection();
				} catch (AviculturaException e) {
					JOptionPane.showMessageDialog(frame, e.getMessage(),
							"Fehler", JOptionPane.ERROR_MESSAGE, new ImageIcon(
									iconError));
				}

				System.exit(0);
			}
		});

		btnSuchen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String searchText = "";
				if (searchTextField.getText().length() == 0) {
					searchText = "*";
					searchTextField.setText(searchText);
				} else {
					searchText = searchTextField.getText();
				}

				try {
					openSearchFrame(searchText, comboBox.getSelectedItem()
							.toString());
				} catch (AviculturaException e1) {
					e1.viewError(frame);
				}
			}
		});

		mntmOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String path = ".";
				String pdfName = "Zuchtbuch.pdf";

				JFileChooser fileChooser = new JFileChooser();

				fileChooser.setCurrentDirectory(new File(path));

				// fileChooser.setApproveButtonText("Speichern");
				fileChooser.setMultiSelectionEnabled(false);
				fileChooser.setFileFilter(filter);
				fileChooser.setSelectedFile(new File(path, pdfName));

				int result = fileChooser.showOpenDialog(frame);
				if (result == JFileChooser.APPROVE_OPTION) {
					File datname = fileChooser.getSelectedFile();

					AppPdfViewer pdfViewer;
					try {
						pdfViewer = new AppPdfViewer(desktopPane);
						pdfViewer.showPdf(datname);
					} catch (AviculturaException e1) {
						e1.viewError(frame);
					}

					
				}

			}
		});

		mntmSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String path = ".";
				String pdfName = "Zuchtbuch.pdf";

				JFileChooser fileChooser = new JFileChooser();

				fileChooser.setCurrentDirectory(new File(path));

				fileChooser.setApproveButtonText("Speichern");
				fileChooser.setMultiSelectionEnabled(false);
				fileChooser.setFileFilter(filter);
				fileChooser.setSelectedFile(new File(path, pdfName));

				int result = fileChooser.showOpenDialog(frame);
				if (result == JFileChooser.APPROVE_OPTION) {
					File datname = fileChooser.getSelectedFile();

					LOG.info(datname.getAbsolutePath());

					ZuchtBuchWriter writer = new ZuchtBuchWriter();

					List<BirdObject> list;
					try {
						list = dbhelper.getBirdList();
						writer.createZuchtBuch(list, datname);

						AppPdfViewer pdfViewer = new AppPdfViewer(desktopPane);

						pdfViewer.showPdf(datname);
					} catch (AviculturaException e1) {

						e1.viewError(frame);
					}

				}

			}
		});

		mntmNeuerVogel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					newBirdWindow.openNewBird();
				} catch (AviculturaException e1) {
					e1.viewError(frame);
				}

			}
		});

		mntmImport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					AppImport.importFromBBB(dbhelper);
				} catch (AviculturaException e1) {
					e1.viewError(frame);
				}

			}
		});

		mntmAbout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AboutWindow.show(new ImageIcon(iconBackground));

			}
		});

	}

	public void setNavLableHidden(int counter) {
		navLables.get(counter).setVisible(false);
	}

	public void setLabelTitel(int counter, String text, BufferedImage iconIntern) {
		navLables.get(counter).removeAll();
		BorderLayout layout = new BorderLayout();
		JLabel bild = new JLabel(new ImageIcon(iconIntern));
		bild.setPreferredSize(new Dimension(20, 20));
		JLabel textL = new JLabel(text);
		textL.setFont(new Font(LABEL_FONT, LABEL_FONT_STYLE, LABEL_FONT_SIZE));

		JLabel nav = new JLabel();
		nav.setLayout(layout);
		nav.add(bild, BorderLayout.WEST);
		nav.add(textL, BorderLayout.CENTER);
		nav.setFont(new Font(LABEL_FONT, LABEL_FONT_STYLE, LABEL_FONT_SIZE));

		nav.setBackground(Color.BLACK);
		nav.setPreferredSize(new Dimension(140, 15));

		navLables.get(counter).add(nav);

	}

	public int newNavLabel(String text, final JInternalFrame frame,
			boolean showNumber, BufferedImage iconIntern) {
		int counter = navLables.size();
		String labelText = text + (counter + 1);

		if (!showNumber) {
			labelText = text;
		}

		BorderLayout layout = new BorderLayout();

		JLabel bild = new JLabel(new ImageIcon(iconIntern));
		bild.setPreferredSize(new Dimension(20, 20));
		JLabel textL = new JLabel(labelText);
		textL.setFont(new Font(LABEL_FONT, LABEL_FONT_STYLE, LABEL_FONT_SIZE));

		JLabel nav = new JLabel();
		nav.setLayout(layout);
		nav.add(bild, BorderLayout.WEST);
		nav.add(textL, BorderLayout.CENTER);
		nav.setFont(new Font(LABEL_FONT, LABEL_FONT_STYLE, LABEL_FONT_SIZE));

		nav.setBackground(Color.BLACK);
		nav.setPreferredSize(new Dimension(140, 15));
		navLables.add(nav);

		navigatorPanel.add(nav);

		nav.addMouseListener(new MouseListener() {

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

				try {
					frame.setSelected(true);
				} catch (PropertyVetoException e1) {
					LOG.error("frame error: ", e1);
					e1.printStackTrace();
				}
			}
		});

		return counter;

	}

	private void openSearchFrame(String searchString, String searchType)
			throws AviculturaException {
		Dimension screenDesktop = desktopPane.getSize();

		int heigth = screenDesktop.height - 200;
		int width = screenDesktop.width - 200;

		Dimension minSize = new Dimension(700, 500);

		searchString = searchString.replace((char) 42, (char) 37);

		Map<Long, SearchResults> search = dbhelper.searchFor(searchString,
				searchType);

		final Object[][] inTable = new Object[search.size()][8];

		Iterator<Entry<Long, SearchResults>> it = search.entrySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			Entry<Long, SearchResults> pairs = it.next();

			SearchResults bird = pairs.getValue();

			inTable[i][0] = bird.getRINGNO();
			inTable[i][1] = Helper.formatString(bird.getBIRDSPECIESNAME());
			inTable[i][2] = Helper.getDateString(bird.getBREEDSTART());
			inTable[i][3] = Helper.getDateString(bird.getRINGAT());
			inTable[i][4] = Helper.getDateString(bird.getBUYAT());
			inTable[i][5] = Helper.getDateString(bird.getSELLAT());
			inTable[i][6] = Helper.getDateString(bird.getDEATHAT());
			inTable[i][7] = pairs.getKey();
			i++;
		}

		final JInternalFrame internalFrame = new JInternalFrame(
				"Suchergebnis für: " + searchString + " " + searchType);
		internalFrame.setFrameIcon(new ImageIcon(iconFindBird));
		internalFrame.setClosable(true);
		internalFrame.setMaximumSize(screenDesktop);
		internalFrame.setSize(width, heigth);
		internalFrame.setMinimumSize(minSize);
		// internalFrame.setBounds(10, 11, 596, 416);
		internalFrame.setResizable(true);
		internalFrame.setMaximizable(true);
		final int counter = newNavLabel("Suchergebnis für: " + searchString,
				internalFrame, false, iconFindBird);

		internalFrame.addInternalFrameListener(new InternalFrameListener() {
			public void internalFrameClosed(InternalFrameEvent e) {
				navLables.get(counter).setVisible(false);
			}

			public void internalFrameClosing(InternalFrameEvent e) {
				navLables.get(counter).setVisible(false);
			}

			@Override
			public void internalFrameActivated(InternalFrameEvent arg0) {

			}

			@Override
			public void internalFrameDeactivated(InternalFrameEvent arg0) {

			}

			@Override
			public void internalFrameDeiconified(InternalFrameEvent arg0) {

			}

			@Override
			public void internalFrameIconified(InternalFrameEvent arg0) {

			}

			@Override
			public void internalFrameOpened(InternalFrameEvent arg0) {

			}
		});

		// internalFrame.setSize(screenDesktop);
		desktopPane.add(internalFrame);

		table = new JTable();

		table.setModel(new DefaultTableModel(inTable, new String[] {
				"Ring Nr.", "Vogelart", "Brutbeginn", "Beringt am",
				"Erworben am", "Verkauft am", "Gestorben am", "time stamp" }) {
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

		table.getColumnModel().getColumn(6).setPreferredWidth(95);

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
					String ringNo = table.getValueAt(row, 0).toString();

					// editBird(ringNo);

					popUpLabel.setText(ringNo);

					popup.show(frame, xMouse, yMouse);

				}

				if (e.getButton() == MouseEvent.BUTTON3) {

					table.clearSelection();
					int fire = table.rowAtPoint(new Point(x, y));
					table.addRowSelectionInterval(fire, fire);
					String ringNo = table.getValueAt(fire, 0).toString();

					popUpLabel.setText(ringNo);

					popup.show(frame, xMouse, yMouse);
				}
			}
		});

		// table.setEnabled(false);

		internalFrame.getContentPane().add(table.getTableHeader(),
				BorderLayout.NORTH);
		JScrollPane pane = new JScrollPane(table);
		internalFrame.getContentPane().add(pane, BorderLayout.CENTER);

		internalFrame.setVisible(true);
		PopupMenuExample();
	}

	private void editBird(String ringNo) throws AviculturaException {
		int check = JOptionPane.showConfirmDialog(frame, "Ring Nr.: " + ringNo,
				"Vogel bearbeiten?", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, new ImageIcon(iconEditBig));

		if (check == 0) {
			BirdObject birdEditObj = dbhelper.getBirdEdit(ringNo);
			AppNewBirdWindow editWindow = new AppNewBirdWindow(dbhelper,
					desktopPane, birdEditObj, properties);
			try {
				editWindow.openNewBird();
				editWindow.fillDataSheet(iconEdit, "Bearbeite Vogel " + ringNo);
			} catch (AviculturaException e) {
				e.viewError(frame);
			}

		}
	}

	private void copyBird(String ringNo) throws AviculturaException {
		int check = JOptionPane.showConfirmDialog(frame, "Ring Nr.: " + ringNo,
				"Vogel kopieren?", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, new ImageIcon(iconEditBig));

		if (check == 0) {
			BirdObject birdEditObj = dbhelper.getBirdEdit(ringNo);
			AppNewBirdWindow copyWindow = new AppNewBirdWindow(dbhelper,
					desktopPane, birdEditObj, properties);
			try {
				copyWindow.openNewBird();
				copyWindow.fillCopySheet(iconCopy, "Neuer Vogel kopiert von "
						+ ringNo);
			} catch (AviculturaException e) {
				e.viewError(frame);
			}

		}
	}

	private void deleteBird(String ringNo) throws AviculturaException {
		int check = JOptionPane.showConfirmDialog(frame, "Ring Nr.: " + ringNo,
				"Vogel löschen?", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE, new ImageIcon(iconDelete));
		if (check == 0) {
			boolean checkDelete = dbhelper.birdHide(ringNo);

			if (checkDelete) {
				JOptionPane.showMessageDialog(frame, "Ringnr.: " + ringNo,
						"Vogel gelöscht", JOptionPane.PLAIN_MESSAGE,
						new ImageIcon(icon));
			}
		}
	}

	private void createJungTierStammBlatt(String ringNo)
			throws AviculturaException {
		StammBlattObj sto = dbhelper.getStammBlattData(ringNo);

		if (sto != null) {

			JFileChooser fileChooser = new JFileChooser();

			// fileChooser.setCurrentDirectory(new File(path));

			// fileChooser.setApproveButtonText("Speichern");
			fileChooser.setMultiSelectionEnabled(false);
			// fileChooser.setFileFilter(filter);
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			// fileChooser.setSelectedFile(new File(path, pdfName));

			int result = fileChooser.showOpenDialog(frame);
			if (result == JFileChooser.APPROVE_OPTION) {
				File pdfFile = new File(fileChooser.getSelectedFile(),
						"Stammblatt-" + ringNo + ".pdf");

				StammBlattWriter stamm = new StammBlattWriter(sto, pdfFile);

				stamm.writer();

				JOptionPane.showMessageDialog(frame, pdfFile.getAbsolutePath(),
						"Jungtierstammblatt gespeichert",
						JOptionPane.ERROR_MESSAGE, new ImageIcon(iconPdf));
			}

		} else {
			JOptionPane.showMessageDialog(frame, "Ringnr.: " + ringNo,
					"Keine Eltern eingetragen", JOptionPane.ERROR_MESSAGE,
					new ImageIcon(iconError));
		}

	}

	public JPopupMenu popup;
	private JLabel popUpLabel;

	public void PopupMenuExample() {
		popup = new JPopupMenu();
		popUpLabel = new JLabel("Fire");
		popUpLabel.setFont(new Font(LABEL_FONT, LABEL_FONT_STYLE,
				LABEL_FONT_SIZE));
		ActionListener menuListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {

				try {

					if (event.getActionCommand().toString().equals(BEARBEITEN)) {
						editBird(popUpLabel.getText());
					}

					if (event.getActionCommand().toString().equals(KOPIEREN)) {
						copyBird(popUpLabel.getText());
					}

					if (event.getActionCommand().toString().equals(LOESCHEN)) {
						deleteBird(popUpLabel.getText());
					}

					if (event.getActionCommand().toString()
							.equals(JUNGTIERSTAMMBLATT)) {
						LOG.info(JUNGTIERSTAMMBLATT + " "
								+ popUpLabel.getText());
						createJungTierStammBlatt(popUpLabel.getText());
					}

				} catch (AviculturaException e) {

				}
			}
		};
		JMenuItem item;

		popup.add(popUpLabel);

		popup.add(item = new JMenuItem(BEARBEITEN, new ImageIcon(iconEdit)));
		item.setHorizontalTextPosition(JMenuItem.RIGHT);
		item.addActionListener(menuListener);
		item.setFont(new Font(LABEL_FONT, LABEL_FONT_STYLE, LABEL_FONT_SIZE));

		popup.add(item = new JMenuItem(KOPIEREN, new ImageIcon(iconCopy)));
		item.setHorizontalTextPosition(JMenuItem.RIGHT);
		item.addActionListener(menuListener);
		item.setFont(new Font(LABEL_FONT, LABEL_FONT_STYLE, LABEL_FONT_SIZE));

		popup.add(item = new JMenuItem(JUNGTIERSTAMMBLATT, new ImageIcon(
				iconPdf)));
		item.setHorizontalTextPosition(JMenuItem.RIGHT);
		item.addActionListener(menuListener);
		item.setFont(new Font(LABEL_FONT, LABEL_FONT_STYLE, LABEL_FONT_SIZE));

		popup.add(item = new JMenuItem(LOESCHEN, new ImageIcon(iconDelete)));
		item.setHorizontalTextPosition(JMenuItem.RIGHT);
		item.addActionListener(menuListener);
		item.setFont(new Font(LABEL_FONT, LABEL_FONT_STYLE, LABEL_FONT_SIZE));

		popup.setLabel("Justification");
		popup.setBorder(new BevelBorder(BevelBorder.RAISED));
		popup.addPopupMenuListener(new PopupPrintListener());

	}

	// An inner class to show when popup events occur
	class PopupPrintListener implements PopupMenuListener {
		public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			LOG.info("Popup menu will be visible!");
		}

		public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			LOG.info("Popup menu will be invisible!");
		}

		public void popupMenuCanceled(PopupMenuEvent e) {
			LOG.info("Popup menu is hidden!");
		}
	}
}
