package org.peie.avicultura.viewer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import org.peie.avicultura.helper.Helper;

public class DatePopUp {

	private static JComboBox<String> comboBoxDay;
	private static JComboBox<String> comboBoxMonth;
	private static JComboBox<String> comboBoxYear;
	private static String outDate;
	private static AppDateField objGlobal;
	private static DecimalFormat df;
	private static JFrame datum;

	/**
	 * @wbp.parser.entryPoint
	 */
	public static String dateCreate(int x, int y, AppDateField obj) {
		// this.setText("HALOO");

		objGlobal = obj;
		int year;
		String day;
		String month;
		int yearNow;
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		df = new DecimalFormat("00");

		if (Helper.isDateFormat(obj.getText(), false)) {
			String[] tmp = obj.getText().split("[.]");
			day = tmp[0];
			month = tmp[1];
			year = Helper.stringToInt(tmp[2]);
			yearNow = cal.get(Calendar.YEAR);

		} else {

			year = cal.get(Calendar.YEAR);
			yearNow = year;
			day = df.format(cal.get(Calendar.DAY_OF_MONTH));
			month = df.format(cal.get(Calendar.MONTH) + 1);
		}

		int diffYear = 20;

		if (year < (yearNow - diffYear)) {
			diffYear = yearNow - year + 4;
		}

		datum = new JFrame("Datumsauswahl");
		datum.setResizable(false);
		datum.setLocation(x, y);

		datum.setSize(221, 109);
		datum.getContentPane().setLayout(null);

		comboBoxDay = new JComboBox<String>();
		comboBoxDay.setBounds(10, 11, 40, 20);
		for (int i = 1; i < 32; i++) {
			comboBoxDay.addItem(df.format(i));

		}

		datum.getContentPane().add(comboBoxDay);

		comboBoxMonth = new JComboBox<String>();
		comboBoxMonth.setBounds(60, 11, 40, 20);
		for (int i = 1; i < 13; i++) {
			comboBoxMonth.addItem(df.format(i));
		}

		datum.getContentPane().add(comboBoxMonth);

		comboBoxYear = new JComboBox<String>();
		comboBoxYear.setBounds(110, 11, 80, 20);

		for (int i = yearNow; i > yearNow - diffYear; i--) {
			comboBoxYear.addItem(df.format(i));
		}
		datum.getContentPane().add(comboBoxYear);

		JButton btnNewButton = new JButton("\u00DCbernehmen");
		btnNewButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				outDate = comboBoxDay.getSelectedItem() + "."
						+ comboBoxMonth.getSelectedItem() + "."
						+ comboBoxYear.getSelectedItem();

				objGlobal.setText(outDate);

				datum.setVisible(false);

			}
		});
		btnNewButton.setBounds(78, 42, 112, 23);
		datum.getContentPane().add(btnNewButton);

		datum.setVisible(true);

		comboBoxDay.setSelectedItem(day);
		comboBoxMonth.setSelectedItem(month);
		comboBoxYear.setSelectedItem("" + year);

		return outDate;

	}
}
