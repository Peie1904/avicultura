package org.peie.avicultura.viewer;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.peie.avicultura.helper.AviInternalFrame;
import org.peie.avicultura.helper.AviculturaException;
import org.peie.avicultura.helper.DbHelper;

public class NeuesZuchtpaarFrame {

	private JDesktopPane desktopPane;
	private DbHelper dbhelper;
	private JPanel taskBar;
	private final static int fieldLength = 15;
	private JTextField mutterVogelField;
	private JTextField vaterVogelField;
	private AviInternalFrame internalFrame;

	public NeuesZuchtpaarFrame(JDesktopPane desktopPane, DbHelper dbhelper,
			JPanel taskBar) {
		super();
		this.desktopPane = desktopPane;
		this.dbhelper = dbhelper;
		this.taskBar = taskBar;

	}

	/**
	 * @wbp.parser.entryPoint
	 */
	public void showFrame() {
		internalFrame = new AviInternalFrame(taskBar, "Neues Zuchtpaar");

		Dimension screenDesktop = desktopPane.getSize();

		int heigth = 150;// screenDesktop.height - 400;
		int width = 300;// screenDesktop.width - 400;

		Dimension minSize = new Dimension(300, 300);

		internalFrame.setClosable(true);
		internalFrame.setMaximumSize(screenDesktop);
		internalFrame.setSize(width, heigth);
		internalFrame.setMinimumSize(minSize);
		// internalFrame.setBounds(10, 11, 596, 416);
		internalFrame.setResizable(true);
		internalFrame.setMaximizable(true);
		internalFrame.setVisible(true);

		GridBagLayout gbl = new GridBagLayout();
		internalFrame.getContentPane().setLayout(gbl);
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.insets = new Insets(2, 2, 2, 2);

		Label vaterVogel = new Label("Vater Vogel: ");
		vaterVogel.setSize(new Dimension(150, 150));

		gbc.gridx = 0;
		gbc.gridy = 0;

		gbl.setConstraints(vaterVogel, gbc);
		internalFrame.getContentPane().add(vaterVogel);

		vaterVogelField = new JTextField();

		vaterVogelField.setColumns(fieldLength);

		gbc.gridx = 1;
		gbc.gridy = 0;

		gbl.setConstraints(vaterVogelField, gbc);

		internalFrame.getContentPane().add(vaterVogelField);

		Label mutterVogel = new Label("Mutter Vogel: ");
		mutterVogel.setSize(new Dimension(150, 150));

		gbc.gridx = 0;
		gbc.gridy = 1;

		gbl.setConstraints(mutterVogel, gbc);
		internalFrame.add(mutterVogel);

		mutterVogelField = new JTextField();

		mutterVogelField.setColumns(fieldLength);

		gbc.gridx = 1;
		gbc.gridy = 1;

		gbl.setConstraints(mutterVogelField, gbc);

		internalFrame.getContentPane().add(mutterVogelField);

		JButton send = new JButton("anlegen");

		gbc.gridx = 1;
		gbc.gridy = 2;

		gbl.setConstraints(send, gbc);

		internalFrame.getContentPane().add(send);

		desktopPane.add(internalFrame);

		send.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String mutter = mutterVogelField.getText();
				String vatter = vaterVogelField.getText();

				int code = 1;

				try {
					code = dbhelper.checkBirdPair(vatter, mutter);
				} catch (AviculturaException e) {

					e.viewError(internalFrame);
				}

				System.out.println("code: " + code);

				switch (code) {
				case DbHelper.CHECK_VATER_NOT:
					JOptionPane.showMessageDialog(internalFrame,
							"Kann Vater nicht finden: " + vatter, "Fehler",
							JOptionPane.ERROR_MESSAGE);
					break;
				case DbHelper.CHECK_MUTTER_NOT:
					JOptionPane.showMessageDialog(internalFrame,
							"Kann Mutter nicht finden: " + mutter, "Fehler",
							JOptionPane.ERROR_MESSAGE);
					break;
				case DbHelper.CHECK_GENDER_NOT:
					JOptionPane.showMessageDialog(internalFrame,
							"Beide Vögel vom selben Geschlecht", "Fehler",
							JOptionPane.ERROR_MESSAGE);
					break;
				case DbHelper.CHECK_TYPE_NOT_EQUAL:
					JOptionPane.showMessageDialog(internalFrame,
							"Nicht gleiche Vogelrasse", "Fehler",
							JOptionPane.ERROR_MESSAGE);
					break;
				case DbHelper.CHECK_GENDER_NOT_10:
					JOptionPane.showMessageDialog(internalFrame,
							"Vater kein Hahn", "Fehler",
							JOptionPane.ERROR_MESSAGE);
					break;
				case DbHelper.CHECK_GENDER_NOT_01:
					JOptionPane.showMessageDialog(internalFrame,
							"Mutter keine Henne", "Fehler",
							JOptionPane.ERROR_MESSAGE);
					break;
				case 0:
					JOptionPane.showMessageDialog(internalFrame, "Paar: "
							+ vatter + " " + mutter, "Paar angelegt",
							JOptionPane.PLAIN_MESSAGE);

					mutterVogelField.setText("");
					vaterVogelField.setText("");
					break;
				default:
					JOptionPane.showMessageDialog(internalFrame, "Code: "
							+ code, "Fehler", JOptionPane.ERROR_MESSAGE);
					break;
				}

				/*
				 * if (code == DbHelper.CHECK_VATER_NOT) {
				 * JOptionPane.showMessageDialog(internalFrame,
				 * "Kann Vater nicht finden: " + vatter, "Fehler",
				 * JOptionPane.ERROR_MESSAGE); }
				 * 
				 * if (code == DbHelper.CHECK_MUTTER_NOT) {
				 * JOptionPane.showMessageDialog(internalFrame,
				 * "Kann Mutter nicht finden: " + mutter, "Fehler",
				 * JOptionPane.ERROR_MESSAGE); }
				 * 
				 * if (code == DbHelper.CHECK_GENDER_NOT) {
				 * JOptionPane.showMessageDialog(internalFrame,
				 * "Beide Vögel vom selben Geschlecht", "Fehler",
				 * JOptionPane.ERROR_MESSAGE); }
				 * 
				 * if (code == DbHelper.CHECK_TYPE_NOT_EQUAL) {
				 * JOptionPane.showMessageDialog(internalFrame,
				 * "Nicht gleiche Vogelrasse", "Fehler",
				 * JOptionPane.ERROR_MESSAGE); }
				 * 
				 * if (code == DbHelper.CHECK_GENDER_NOT_10) {
				 * JOptionPane.showMessageDialog(internalFrame,
				 * "Vater kein Hahn", "Fehler", JOptionPane.ERROR_MESSAGE); }
				 * 
				 * if (code == DbHelper.CHECK_GENDER_NOT_01) {
				 * JOptionPane.showMessageDialog(internalFrame,
				 * "Mutter keine Henne", "Fehler", JOptionPane.ERROR_MESSAGE); }
				 * 
				 * if (code == DbHelper.CHECK_PAIR_EXSISTS) {
				 * JOptionPane.showMessageDialog(internalFrame,
				 * "Paar schon vorhanden", "Fehler", JOptionPane.ERROR_MESSAGE);
				 * }
				 * 
				 * if (code == 0) { JOptionPane.showMessageDialog(internalFrame,
				 * "Paar: " + vatter + " " + mutter, "Paar angelegt",
				 * JOptionPane.PLAIN_MESSAGE);
				 * 
				 * mutterVogelField.setText(""); vaterVogelField.setText(""); }
				 */

			}
		});
	}

}
