package org.peie.avicultura.viewer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.peie.avicultura.helper.AviculturaException;
import org.peie.avicultura.helper.DbHelper;

public class AppImport {

	public final static String APPDATA = System.getenv("APPDATA");
	private static File dataFile;
	private static File speciesFile;
	private static JButton btnImport;
	private static JLabel lblImporttierenVonBbb;
	private static DbHelper dbhelper;

	/**
	 * @wbp.parser.entryPoint
	 */
	public static void importFromBBB(DbHelper dbhelperPara) {
		JFrame importFrame = new JFrame("Import");
		dbhelper = dbhelperPara;

		dataFile = new File(APPDATA, "data.bbb");
		speciesFile = new File(APPDATA, "birdspecies.bbb");

		importFrame.setResizable(false);
		// importFrame.setLocation(x, y);

		importFrame.setSize(221, 109);
		importFrame.getContentPane().setLayout(null);

		lblImporttierenVonBbb = new JLabel("Importtieren von BBB?");
		lblImporttierenVonBbb.setHorizontalAlignment(SwingConstants.CENTER);
		lblImporttierenVonBbb.setBounds(10, 11, 195, 14);
		importFrame.getContentPane().add(lblImporttierenVonBbb);

		btnImport = new JButton("Import");

		if (dataFile.exists() && speciesFile.exists() && dbhelper.checkImport()) {
			btnImport.setEnabled(true);
		} else {
			lblImporttierenVonBbb.setText("Importtieren von BBB nicht möglich");
			btnImport.setEnabled(false);
		}

		btnImport.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				boolean error = true;

				lblImporttierenVonBbb.setText("Wir importiert!!!!");

				try {
					dbhelper.importVisualBasicVersion(APPDATA);
					error = false;
				} catch (AviculturaException e) {
					error = true;
				}

				btnImport.setEnabled(false);

				if (error) {
					lblImporttierenVonBbb.setText("import fehlgeschlagen");
				} else {
					lblImporttierenVonBbb.setText("import fertig!!!!");
				}

			}
		});
		btnImport.setBounds(59, 36, 89, 23);
		importFrame.getContentPane().add(btnImport);

		importFrame.setVisible(true);

	}
}
