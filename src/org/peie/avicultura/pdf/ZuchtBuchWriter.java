package org.peie.avicultura.pdf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.peie.avicultura.helper.AviculturaException;
import org.peie.avicultura.helper.BirdObject;
import org.peie.avicultura.helper.Helper;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class ZuchtBuchWriter {
	private static Document documentBestand = new Document(PageSize.A4, 2, 2,
			36, 36);
	private static String[][] dataBestand;
	private static PdfPTable tableBestand;

	static void seite_eins(Document document, String[][] data, int line)
			throws DocumentException, FileNotFoundException, IOException {

		BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA,
				BaseFont.CP1252, BaseFont.EMBEDDED);
		BaseFont times = BaseFont.createFont(BaseFont.TIMES_ROMAN,
				BaseFont.CP1252, BaseFont.EMBEDDED);

		Font contain_font = new Font(times, 8, Font.ITALIC);
		Font font_small = new Font(helvetica, 8, Font.NORMAL);
		Chunk chunk; // = new
						// Chunk("Sponsor this example and send me 1\u20ac. These are some special characters: \u0152\u0153\u0160\u0161\u0178\u017D\u0192\u02DC\u2020\u2021\u2030",
						// font);

		float[] widths = { 0.05f, 0.07f, 0.07f, 0.11f, 0.04f, 0.20f, 0.11f,
				0.04f, 0.20f, 0.11f };

		PdfPTable table = new PdfPTable(widths);
		table.setWidthPercentage(100);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell;

		cell = new PdfPCell(new Paragraph(""));

		cell.setColspan(1);

		table.addCell(cell);

		cell = new PdfPCell(new Paragraph(""));
		cell.setColspan(1);
		table.addCell(cell);

		cell = new PdfPCell(new Paragraph("Selbstgezüchtete Vögel"));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setColspan(2);

		table.addCell(cell);

		cell = new PdfPCell(new Paragraph("Erworbene Vögel"));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setColspan(3);
		table.addCell(cell);

		cell = new PdfPCell(new Paragraph("Abgegebene Vögel"));
		cell.setColspan(3);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		chunk = new Chunk("Lfd.\nNr.", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setColspan(1);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		chunk = new Chunk("Vogelart", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setColspan(1);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		chunk = new Chunk("Brutbeginn\n" + "------------\n" + "Beringung am",
				font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setColspan(1);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		chunk = new Chunk("Kennzeichen   geschl.\n"
				+ "                       ---------\n"
				+ "(Ring-Nr.)         offen", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setColspan(1);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		chunk = new Chunk("Erwor-\nben am", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setColspan(1);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		chunk = new Chunk("von\n(Name und Anschrift)", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setColspan(1);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		chunk = new Chunk("Kennzeichen   geschl.\n"
				+ "                       ---------\n"
				+ "(Ring-Nr.)         offen", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setColspan(1);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		chunk = new Chunk("abgege-\nben am", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setColspan(1);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		chunk = new Chunk("von\n(Name und Anschrift)", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setColspan(1);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		chunk = new Chunk("Kennzeichen   geschl.\n"
				+ "                       ---------\n"
				+ "(Ring-Nr.)         offen", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setColspan(1);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		for (int i = 1; i <= 10; i++) {

			chunk = new Chunk("" + i + "", font_small);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setColspan(1);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);

			table.addCell(cell);

		}

		for (int i = (0 + line); i < (11 + line); i++) {
			for (int j = 0; j < 10; j++) {
				// System.out.println("" + data[i][j]);
				chunk = new Chunk(data[i][j], contain_font);

				cell = new PdfPCell(new Paragraph(chunk));
				cell.setColspan(1);
				cell.setFixedHeight(42f);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			}
		}

		document.add(table);

	}

	static void seite_zwei(Document document, String[][] data, int line)
			throws DocumentException, FileNotFoundException, IOException {

		BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA,
				BaseFont.CP1252, BaseFont.EMBEDDED);
		BaseFont times = BaseFont.createFont(BaseFont.TIMES_ROMAN,
				BaseFont.CP1252, BaseFont.EMBEDDED);

		Font contain_font = new Font(times, 8, Font.ITALIC);
		Font font_small = new Font(helvetica, 8, Font.NORMAL);
		Chunk chunk; // = new
						// Chunk("Sponsor this example and send me 1\u20ac. These are some special characters: \u0152\u0153\u0160\u0161\u0178\u017D\u0192\u02DC\u2020\u2021\u2030",
						// font);

		float[] widths = { 0.04f, 0.04f, 0.11f, 0.07f, 0.04f, 0.22f, 0.04f,
				0.11f, 0.11f, 0.11f, 0.11f };

		PdfPTable table = new PdfPTable(widths);
		table.setWidthPercentage(100);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		PdfPCell cell;

		cell = new PdfPCell(new Paragraph(""));

		cell.setColspan(1);

		table.addCell(cell);

		cell = new PdfPCell(new Paragraph("Abgang durch Tod"));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setColspan(3);

		table.addCell(cell);

		cell = new PdfPCell(new Paragraph(
				"Tierärztliche Behandlung bei Psittaciden"));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		cell.setColspan(4);
		table.addCell(cell);

		cell = new PdfPCell(new Paragraph("Bemerkungen"));
		cell.setColspan(3);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);

		chunk = new Chunk("Lfd.\nNr.", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setColspan(1);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		chunk = new Chunk("am", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setColspan(1);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		chunk = new Chunk("Kennzeichen   geschl.\n"
				+ "                       ---------\n"
				+ "(Ring-Nr.)         offen", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setColspan(1);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		chunk = new Chunk("Ursache", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setColspan(1);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		chunk = new Chunk("Beginn", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setColspan(1);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		chunk = new Chunk("Art und Dosierung des Arzneimittels", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setColspan(1);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		chunk = new Chunk("Ende", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setColspan(1);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		chunk = new Chunk("Ergebnis\nKontrolluntersuchung", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setColspan(1);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		chunk = new Chunk("Einfuhrdokumente\nErwerbsbescheinigung", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setColspan(1);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		chunk = new Chunk("Sichtvermerk\nVertrauensmann", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setColspan(1);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		chunk = new Chunk("Sichtvermerk\nBehörde", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setColspan(1);
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell);

		for (int i = 11; i <= 21; i++) {

			chunk = new Chunk("" + i + "", font_small);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setColspan(1);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);

			table.addCell(cell);

		}

		for (int i = (0 + line); i < (11 + line); i++) {
			for (int j = 10; j < 21; j++) {
				// System.out.println("" + data[i][j]);
				chunk = new Chunk(data[i][j], contain_font);

				cell = new PdfPCell(new Paragraph(chunk));
				cell.setColspan(1);
				cell.setFixedHeight(42f);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(cell);
			}
		}

		document.add(table);

	}

	static void bestandHead() {

		PdfPCell cell;
		Chunk chunk;

		try {

			BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA,
					BaseFont.CP1252, BaseFont.EMBEDDED);
			Font font_small = new Font(helvetica, 8, Font.NORMAL);
			float[] widths2 = { 0.335f, 0.33f, 0.335f, };

			PdfPTable table2 = new PdfPTable(widths2);
			table2.setWidthPercentage(100);
			table2.setHorizontalAlignment(Element.ALIGN_CENTER);

			cell = new PdfPCell(new Paragraph("Vogelart"));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

			tableBestand.addCell(cell);

			cell = new PdfPCell(new Paragraph("(Ring-Nr.)"));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(35f);
			cell.setColspan(3);
			table2.addCell(cell);

			chunk = new Chunk("geschlossen", font_small);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setFixedHeight(35f);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

			table2.addCell(cell);

			chunk = new Chunk("offen", font_small);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

			table2.addCell(cell);

			chunk = new Chunk("ohne", font_small);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

			table2.addCell(cell);

			// cell = new PdfPCell(new
			// Paragraph("(Ring-Nr.)\n---------------------\ngeschlossen|offen|ohne"));
			cell = new PdfPCell(table2);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setColspan(3);
			tableBestand.addCell(cell);

			cell = new PdfPCell(new Paragraph("seit\nwann im\nBesitz"));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

			tableBestand.addCell(cell);

			cell = new PdfPCell(new Paragraph("Sichtvermerk\nder Behörde"));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

			tableBestand.addCell(cell);

		} catch (Exception ex) {
			Logger.getLogger(ZuchtBuchWriter.class.getName()).log(Level.SEVERE,
					null, ex);
		}

	}

	static void bestandsliste() {

		try {
			Chunk chunk;

			BaseFont times = BaseFont.createFont(BaseFont.TIMES_ROMAN,
					BaseFont.CP1252, BaseFont.EMBEDDED);

			Font contain_font = new Font(times, 8, Font.ITALIC);
			float[] widths = { 0.33f, 0.11f, 0.11f, 0.11f, 0.15f, 0.20f };

			tableBestand = new PdfPTable(widths);

			tableBestand.setWidthPercentage(90);

			tableBestand.setHorizontalAlignment(Element.ALIGN_CENTER);

			PdfPCell cell;

			/*
			 * cell = new PdfPCell(new Paragraph("Vogelart"));
			 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			 * 
			 * 
			 * 
			 * tableBestand.addCell(cell);
			 * 
			 * cell = new PdfPCell(new Paragraph("(Ring-Nr.)"));
			 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			 * cell.setFixedHeight(35f); cell.setColspan(3);
			 * table2.addCell(cell);
			 * 
			 * chunk = new Chunk("geschlossen", font_small);
			 * 
			 * cell = new PdfPCell(new Paragraph(chunk));
			 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * cell.setFixedHeight(35f);
			 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			 * 
			 * table2.addCell(cell);
			 * 
			 * chunk = new Chunk("offen", font_small);
			 * 
			 * cell = new PdfPCell(new Paragraph(chunk));
			 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			 * 
			 * table2.addCell(cell);
			 * 
			 * chunk = new Chunk("ohne", font_small);
			 * 
			 * cell = new PdfPCell(new Paragraph(chunk));
			 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			 * 
			 * table2.addCell(cell);
			 * 
			 * 
			 * 
			 * 
			 * 
			 * //cell = new PdfPCell(new
			 * Paragraph("(Ring-Nr.)\n---------------------\ngeschlossen|offen|ohne"
			 * )); cell = new PdfPCell(table2);
			 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			 * cell.setColspan(3); tableBestand.addCell(cell);
			 * 
			 * cell = new PdfPCell(new Paragraph("seit\nwann im\nBesitz"));
			 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			 * 
			 * tableBestand.addCell(cell);
			 * 
			 * cell = new PdfPCell(new Paragraph("Sichtvermerk\nder Behörde"));
			 * cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			 * cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			 * 
			 * tableBestand.addCell(cell);
			 */

			bestandHead();

			int count = 0;

			for (int i = 0; i < dataBestand.length; i++) {

				// System.out.println(dataBestand[i][7] + " " +
				// dataBestand[i][7].length() + " " +
				// dataBestand[i][11].length() + " " + dataBestand[i][11]);

				if (dataBestand[i][7].length() == 0
						&& dataBestand[i][11].length() == 0) {

					chunk = new Chunk(dataBestand[i][1], contain_font);

					cell = new PdfPCell(new Paragraph(chunk));
					cell.setColspan(1);
					cell.setFixedHeight(42f);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					tableBestand.addCell(cell);

					// geschl.

					String gschl, offen, not;

					if (dataBestand[i][23].equalsIgnoreCase("geschl.")) {
						gschl = dataBestand[i][22];
						offen = "";
						not = "";
					} else if (dataBestand[i][23].equalsIgnoreCase("offen")) {
						gschl = "";
						offen = dataBestand[i][22];
						not = "";
					} else {
						gschl = "";
						offen = "";
						not = "x";
					}

					Chunk chunkGeschl = new Chunk(gschl, contain_font);
					Chunk chunkOpen = new Chunk(offen, contain_font);
					Chunk chunkNot = new Chunk(not, contain_font);

					cell = new PdfPCell(new Paragraph(chunkGeschl));
					cell.setColspan(1);
					cell.setFixedHeight(42f);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					tableBestand.addCell(cell);

					cell = new PdfPCell(new Paragraph(chunkOpen));
					cell.setColspan(1);
					cell.setFixedHeight(42f);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					tableBestand.addCell(cell);

					cell = new PdfPCell(new Paragraph(chunkNot));
					cell.setColspan(1);
					cell.setFixedHeight(42f);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					tableBestand.addCell(cell);

					chunk = new Chunk(dataBestand[i][21], contain_font);

					cell = new PdfPCell(new Paragraph(chunk));
					cell.setColspan(1);
					cell.setFixedHeight(42f);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					tableBestand.addCell(cell);

					chunk = new Chunk(dataBestand[i][20], contain_font);

					cell = new PdfPCell(new Paragraph(chunk));
					cell.setColspan(1);
					cell.setFixedHeight(42f);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					tableBestand.addCell(cell);

					count++;

					// System.out.println("dd "+(count%16));

					if ((count % 16) == 0) {
						bestandHead();
					}

				}

			}

			documentBestand.add(tableBestand);

		} catch (Exception ex) {
			Logger.getLogger(ZuchtBuchWriter.class.getName()).log(Level.SEVERE,
					null, ex);
		}

	}

	static void printBestandsListe(String pdf, String[][] data) {
		try {

			dataBestand = data;

			PdfWriter.getInstance(documentBestand, new FileOutputStream(pdf));
			documentBestand.open();

			bestandsliste();

			documentBestand.close();
		} catch (Exception ex) {
			Logger.getLogger(ZuchtBuchWriter.class.getName()).log(Level.SEVERE,
					null, ex);
		}

	}

	static void print_pdf(String pdf, String[][] data)
			throws DocumentException, FileNotFoundException, IOException {

		Document document = new Document(PageSize.A4.rotate(), 2, 2, 36, 36);
		PdfWriter.getInstance(document, new FileOutputStream(pdf));
		document.open();

		int line = 0;

		// System.out.println("" + data.length);

		double seiten = (data.length / 11.0);

		int z = (int) Math.ceil(seiten);

		// System.out.println("" + z);
		String[][] datain = new String[11 * z][21];

		for (int r = 0; r < (11 * z); r++) {
			for (int u = 0; u < 21; u++) {
				datain[r][u] = "";
			}

		}

		for (int r = 0; r < (data.length); r++) {
			for (int u = 0; u < 21; u++) {
				datain[r][u] = data[r][u];
			}

		}

		for (int i = 0; i < z; i++) {

			line = i * 11;
			// System.out.println("line" + line);

			seite_eins(document, datain, line);

			seite_zwei(document, datain, line);

		}

		document.close();

	}

	static String sortdate(String in) {
		String out = "";
		String[] con;

		in = in.replace('.', 'q');

		// System.out.println(""+in);

		con = in.split("q");
		// System.out.println(""+con.length);
		out = con[2] + "-" + con[1] + "-" + con[0];

		return out;
	}

	static String zumbruch(String in) {
		String out = "";
		char[] test;

		// System.out.println(""+in);

		out = in.replace('|', (char) 10);
		out = out.replace((char) 195, (char) 0);
		out = out.replace((char) 182, (char) 246);
		out = out.replace((char) 164, (char) 228);
		out = out.replace((char) 188, (char) 252);

		test = out.toCharArray();

		for (int i = 0; i < test.length; i++) {
			// System.out.println(test[i]+" = "+ (int) test[i]);
		}

		// System.out.println(""+out);

		return out;
	}

	public void createZuchtBuch(List<BirdObject> list, File pdf)
			throws AviculturaException {

		String[][] data = new String[list.size()][24];

		for (int i = 0; i < list.size(); i++) {
			for (int z = 0; z < 24; z++) {
				data[i][z] = "leer";
			}
		}

		Map<Long, BirdObject> sort = new TreeMap<Long, BirdObject>();

		for (int i = 0; i < list.size(); i++) {
			BirdObject bird = list.get(i);
			if (bird.getRingAt().length() != 0) {
				long key = Helper.dateToTimeStamp(bird.getRingAt());

				while (true) {
					if (sort.containsKey(key)) {
						key = key + 1;
					} else {
						break;
					}
				}

				sort.put(key, bird);
			} else {
				long key = Helper.dateToTimeStamp(bird.getBuyAt());

				while (true) {
					if (sort.containsKey(key)) {
						key = key + 1;
					} else {
						break;
					}
				}
				sort.put(key, bird);
			}
		}

		Iterator<Entry<Long, BirdObject>> it = sort.entrySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			Entry<Long, BirdObject> pairs = it.next();
			System.out.println("|" + pairs.getKey() + " = " + pairs.getValue());
			// }

			// for (int i = 0; i < list.size();i++){
			BirdObject bird = pairs.getValue();

			String ringType = "geschl.";
			data[i][0] = "" + (i + 1);
			data[i][1] = bird.getSpeciesID();

			if (bird.isRingType()) {
				ringType = "offen";
			}

			if (bird.getRingAt().length() != 0) {
				data[i][2] = bird.getBreedStart() + "\n------\n"
						+ bird.getRingAt();
				data[i][3] = bird.getRingNo() + " " + ringType;

			} else {
				data[i][2] = "";
				data[i][3] = "";
			}

			if (bird.getBuyAt().length() != 0) {
				data[i][4] = bird.getBuyAt();
				data[i][5] = bird.getBuyFrom();
				data[i][6] = bird.getRingNo() + " " + ringType;
			} else {
				data[i][4] = "";
				data[i][5] = "";
				data[i][6] = "";
			}

			if (bird.getSellAt().length() != 0) {
				data[i][7] = bird.getSellAt();
				data[i][8] = bird.getSellTo();
				data[i][9] = bird.getRingNo() + " " + ringType;
			} else {
				data[i][7] = "";
				data[i][8] = "";
				data[i][9] = "";
			}

			data[i][10] = data[i][0];

			if (bird.getDeathAt().length() != 0) {
				data[i][11] = bird.getDeathAt();
				data[i][12] = bird.getRingNo() + " " + ringType;
				data[i][13] = bird.getDeathWhy();
			} else {
				data[i][11] = "";
				data[i][12] = "";
				data[i][13] = "";
			}

			data[i][14] = bird.getMedicStart();
			data[i][15] = bird.getMedicComment();
			data[i][16] = bird.getMedicEnd();
			data[i][17] = bird.getMedicCheck();
			data[i][18] = bird.getDocu();
			data[i][19] = bird.getObtman();
			data[i][20] = bird.getGov();
			i++;
		}

		try {
			print_pdf(pdf.getAbsolutePath(), data);
			// printBestandsListe("fire2.pdf", data);
		} catch (FileNotFoundException e) {
			throw new AviculturaException(AviculturaException.IO_ERROR,
					"file not found error", e);
		} catch (DocumentException e) {
			throw new AviculturaException(AviculturaException.IO_ERROR,
					"Document error", e);
		} catch (IOException e) {
			throw new AviculturaException(AviculturaException.IO_ERROR,
					"IO error", e);
		}

	}

	/**
	 * @param args
	 *            the command line arguments
	 */
	public void execute(String[] args) throws DocumentException,
			FileNotFoundException, IOException {

		String[][] data;
		BufferedReader infile;
		String tmp, ke = "", value = "";
		String[] key, line;
		ArrayList<String> inhalt = new ArrayList<String>();
		HashMap<String, String> content = new HashMap<String, String>();
		int count = 0;
		String bbbfile;
		String appdata = System.getenv("APPDATA");

		System.out.println("" + appdata);

		bbbfile = appdata + "\\data.bbb";
		String pdffile = appdata + "\\nachweisbuch.pdf";
		String bestandsPdf = appdata + "\\bestandsliste.pdf";

		infile = new BufferedReader(new FileReader(bbbfile));

		while ((tmp = infile.readLine()) != null) {
			// System.out.println(""+tmp);
			key = tmp.split("<=>");
			line = key[1].split(";");
			value = key[1];

			if (line[4].length() == 0) {

				ke = sortdate(line[5]) + "_" + key[0];

				// content.put(tmp, tmp);

			} else {
				ke = sortdate(line[4]) + "_" + key[0];
			}

			// System.out.println(ke+" ? "+value);

			content.put(ke, value);
			inhalt.add(ke);
			count++;

		}

		String[] sortieren = new String[inhalt.size()];

		for (int x = 0; x < inhalt.size(); x++) {
			sortieren[x] = inhalt.get(x).toString();
		}

		Arrays.sort(sortieren);

		data = new String[count][24];
		String ringart;

		for (int x = 0; x < sortieren.length; x++) {

			// key = inhalt.get(x).toString().split("<=>");
			line = content.get(sortieren[x]).split(";");

			if (line[2].equals("False")) {
				ringart = "geschl.";
			} else {
				ringart = "offen";
			}

			System.out.println("Schreibe RingNr.:" + line[1]);

			data[x][0] = "" + (x + 1);
			data[x][1] = zumbruch(line[0]);

			if (line[4].length() != 0) {
				data[x][2] = line[3] + "\n------\n" + line[4];
				data[x][3] = line[1] + " " + ringart;
				data[x][21] = line[4];
				data[x][22] = line[1];
				data[x][23] = ringart;
			} else {
				data[x][2] = "";
				data[x][3] = "";
			}

			if (data[x][21] == null) {
				data[x][21] = line[5];
				data[x][22] = line[1];
				data[x][23] = ringart;
			}

			data[x][4] = line[5];
			data[x][5] = zumbruch(line[6]);
			if (line[6].length() != 0) {
				// data[x][2] = line[3] + "\n------\n" + line[4];
				data[x][6] = line[1] + " " + ringart;
			} else {
				// data[x][2] = "";
				data[x][6] = "";
			}
			// data[x][6] = "7";
			data[x][7] = zumbruch(line[7]);
			data[x][8] = zumbruch(line[8]);
			// System.out.println(""+zumbruch(line[8]));

			if (line[8].length() != 0) {
				// data[x][2] = line[3] + "\n------\n" + line[4];
				data[x][9] = line[1] + " " + ringart;
			} else {
				// data[x][2] = "";
				data[x][9] = "";
			}

			// data[x][9] = "10";
			data[x][10] = "" + (x + 1);
			data[x][11] = line[9];
			if (line[9].length() != 0) {
				// data[x][2] = line[3] + "\n------\n" + line[4];
				data[x][12] = line[1] + " " + ringart;
			} else {
				// data[x][2] = "";
				data[x][12] = "";
			}
			// data[x][12] = "";
			data[x][13] = zumbruch(line[10]);
			data[x][14] = line[11];
			data[x][15] = zumbruch(line[13]);
			data[x][16] = line[12];
			data[x][17] = zumbruch(line[14]);
			data[x][18] = zumbruch(line[15]);
			data[x][19] = zumbruch(line[16]);
			data[x][20] = zumbruch(line[17]);
		}

		// Arrays.sort(data);

		for (int x = 0; x < data.length; x++) {
			System.out.println(data[x][1] + " " + data[x][22] + " - "
					+ data[x][23] + " = " + data[x][21] + " " + data[x][20]);
		}

		infile.close();
		for (int i = 0; i < 29; i++) {
			for (int j = 0; j < 21; j++) {
			}
		}

		printBestandsListe(bestandsPdf, data);

		print_pdf(pdffile, data);

		System.out.println("" + pdffile);

	}
}
