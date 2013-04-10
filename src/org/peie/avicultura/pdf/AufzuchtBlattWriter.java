package org.peie.avicultura.pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.joda.time.DateTime;
import org.peie.avicultura.helper.AviculturaException;
import org.peie.avicultura.helper.ChildObj;
import org.peie.avicultura.helper.DbHelper;
import org.peie.avicultura.helper.Helper;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class AufzuchtBlattWriter {

	private String vater;
	private String mutter;
	private String farbeVater;
	private String farbeMutter;
	private String zuchtPaarId;
	private String jahr;
	private String vogelArt;
	private Document document;
	private String datum;
	private static final float PADDING = 5f;

	public AufzuchtBlattWriter(String vogelArt, String vater, String mutter,
			String farbeVater, String farbeMutter, String zuchtPaarId,
			File aufzuchtBlatt) throws AviculturaException {
		this.vater = vater;
		this.mutter = mutter;
		this.farbeMutter = farbeMutter;
		this.farbeVater = farbeVater;

		this.zuchtPaarId = zuchtPaarId;
		this.vogelArt = vogelArt;

		DateTime dt = new DateTime();

		jahr = dt.toString("yyyy");
		datum = dt.toString("dd.MM.yyyy HH:mm");

		document = new Document(PageSize.A4);

		try {
			PdfWriter
					.getInstance(document, new FileOutputStream(aufzuchtBlatt));
		} catch (FileNotFoundException e) {
			throw new AviculturaException(AviculturaException.IO_ERROR,
					"File not found error ", e);
		} catch (DocumentException e) {
			throw new AviculturaException(AviculturaException.IO_ERROR,
					"document error ", e);
		}

	}

	public void writeAufzuchtVorlage() throws AviculturaException {
		document.open();

		try {

			PdfPTable table = getHead("Aufzuchten");
			document.add(table);

			for (int i = 1; i < 4; i++) {
				PdfPTable brutTable = getAufzuchtTable(i);

				document.add(brutTable);
			}

		} catch (DocumentException e) {
			throw new AviculturaException(AviculturaException.IO_ERROR,
					"document error ", e);
		}

		document.close();

	}

	public void writeAufzuchtPaarListe(DbHelper dbhelper)
			throws AviculturaException {
		document.open();

		try {

			PdfPTable table = getHead("Zuchtpaar " + zuchtPaarId, false);
			document.add(table);

			PdfPTable tableContent = getCildList(dbhelper
					.getChilds(zuchtPaarId));
			document.add(tableContent);

		} catch (DocumentException e) {
			throw new AviculturaException(AviculturaException.IO_ERROR,
					"document error ", e);
		}

		document.close();

	}

	private PdfPTable getCildList(List<ChildObj> coList)
			throws AviculturaException {

		float[] widths = { 0.2f, 0.2f, 0.2f, 0.2f, 0.2f };

		BaseFont helvetica = null;
		try {
			helvetica = BaseFont.createFont(BaseFont.HELVETICA,
					BaseFont.CP1252, BaseFont.EMBEDDED);
		} catch (DocumentException e) {
			throw new AviculturaException(AviculturaException.IO_ERROR,
					e.getMessage(), e);
		} catch (IOException e) {
			throw new AviculturaException(AviculturaException.IO_ERROR,
					e.getMessage(), e);
		}

		Font font_small = new Font(helvetica, 10, Font.NORMAL);
		Font font_small_italic = new Font(helvetica, 10, Font.ITALIC);

		PdfPTable table = new PdfPTable(widths);

		int border = Rectangle.BOX;
		int align = Element.ALIGN_CENTER;

		table.setWidthPercentage(100);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.setSpacingAfter(1.0f);
		PdfPCell cell;
		Chunk chunk;

		chunk = new Chunk("Lfd.", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setHorizontalAlignment(align);
		cell.setBorder(border);
		cell.setColspan(1);
		cell.setPadding(PADDING);

		table.addCell(cell);

		chunk = new Chunk("Ringnr.", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setHorizontalAlignment(align);
		cell.setBorder(border);
		cell.setColspan(1);
		cell.setPadding(PADDING);

		table.addCell(cell);

		chunk = new Chunk("Beringt am", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setHorizontalAlignment(align);
		cell.setBorder(border);
		cell.setColspan(1);
		cell.setPadding(PADDING);

		table.addCell(cell);

		chunk = new Chunk("Farbe.", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setHorizontalAlignment(align);
		cell.setBorder(border);
		cell.setColspan(1);
		cell.setPadding(PADDING);

		table.addCell(cell);

		chunk = new Chunk("Geschlecht", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setHorizontalAlignment(align);
		cell.setBorder(border);
		cell.setColspan(1);
		cell.setPadding(PADDING);

		table.addCell(cell);

		int lfd = 0;
		for (ChildObj co : coList) {
			lfd++;
			chunk = new Chunk(""+lfd, font_small_italic);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(align);
			cell.setBorder(border);
			cell.setColspan(1);
			cell.setPadding(PADDING);

			table.addCell(cell);

			chunk = new Chunk(co.getRINGNO(), font_small_italic);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(align);
			cell.setBorder(border);
			cell.setColspan(1);
			cell.setPadding(PADDING);

			table.addCell(cell);

			String ringAt = "N/A";

			if (co.getRINGAT() != 0) {
				ringAt = new DateTime(co.getRINGAT()).toString("dd.MM.yyyy");
			}

			chunk = new Chunk(ringAt, font_small_italic);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(align);
			cell.setBorder(border);
			cell.setColspan(1);
			cell.setPadding(PADDING);

			table.addCell(cell);

			chunk = new Chunk(co.getCOLOR(), font_small_italic);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(align);
			cell.setBorder(border);
			cell.setColspan(1);
			cell.setPadding(PADDING);

			table.addCell(cell);

			chunk = new Chunk(Helper.getGenderStr(co.getGENDER()),
					font_small_italic);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(align);
			cell.setBorder(border);
			cell.setColspan(1);
			cell.setPadding(PADDING);

			table.addCell(cell);

		}

		return table;

	}

	private PdfPTable getHead(String headerLine) throws AviculturaException {
		return getHead(headerLine, true);
	}

	private PdfPTable getHead(String headerLine, boolean withBoxAndComment)
			throws AviculturaException {
		BaseFont helvetica = null;
		BaseFont helvetica_oblique = null;
		BaseFont helveticaBold = null;
		try {
			helvetica = BaseFont.createFont(BaseFont.HELVETICA,
					BaseFont.CP1252, BaseFont.EMBEDDED);
			helvetica_oblique = BaseFont.createFont(BaseFont.HELVETICA_OBLIQUE,
					BaseFont.CP1252, BaseFont.EMBEDDED);

			helveticaBold = BaseFont.createFont(BaseFont.HELVETICA_BOLD,
					BaseFont.CP1252, BaseFont.EMBEDDED);
		} catch (DocumentException e) {
			throw new AviculturaException(AviculturaException.IO_ERROR,
					e.getMessage(), e);
		} catch (IOException e) {
			throw new AviculturaException(AviculturaException.IO_ERROR,
					e.getMessage(), e);
		}

		Font font_small = new Font(helvetica, 10, Font.NORMAL);
		Font font_small_italic = new Font(helvetica_oblique, 10, Font.ITALIC);
		Font fontHeadLine = new Font(helveticaBold, 22, Font.BOLD);
		Font fontHeadLineSmall = new Font(helveticaBold, 16, Font.HELVETICA);
		Chunk chunk;

		float[] widths = { 0.1666f, 0.1666f, 0.1666f, 0.1666f, 0.1666f, 0.1666f };

		PdfPTable table = new PdfPTable(widths);
		table.setWidthPercentage(100);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.setSpacingAfter(1.0f);
		PdfPCell cell;

		// first row

		chunk = new Chunk(headerLine, fontHeadLine);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		// cell.setBorderColorLeft(Color.WHITE);
		cell.setBorder(Rectangle.BOTTOM);
		cell.setBorderColorBottom(CMYKColor.DARK_GRAY);
		cell.setBorderWidthBottom(2);
		cell.setColspan(6);
		cell.setPaddingBottom(10f);

		table.addCell(cell);

		// 2. row

		chunk = new Chunk(vogelArt, fontHeadLineSmall);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(6);
		cell.setPadding(PADDING);

		table.addCell(cell);

		// 3. row

		chunk = new Chunk("Ringnr. Hahn", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(1);
		cell.setPadding(PADDING);

		table.addCell(cell);

		chunk = new Chunk(vater, font_small_italic);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(1);
		cell.setPadding(PADDING);

		table.addCell(cell);

		chunk = new Chunk("", font_small_italic);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(1);
		cell.setPadding(PADDING);

		table.addCell(cell);

		chunk = new Chunk("", font_small_italic);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(1);
		cell.setPadding(PADDING);

		table.addCell(cell);

		chunk = new Chunk("Ringnr. Henne", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(1);
		cell.setPadding(PADDING);

		table.addCell(cell);

		chunk = new Chunk(mutter, font_small_italic);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(1);
		cell.setPadding(PADDING);

		table.addCell(cell);

		// 4. row

		chunk = new Chunk("Farbe Hahn", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(1);
		cell.setPadding(PADDING);

		table.addCell(cell);

		chunk = new Chunk(farbeVater, font_small_italic);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(1);
		cell.setPadding(PADDING);

		table.addCell(cell);

		chunk = new Chunk("", font_small_italic);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(1);
		cell.setPadding(PADDING);

		table.addCell(cell);

		chunk = new Chunk("", font_small_italic);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(1);
		cell.setPadding(PADDING);

		table.addCell(cell);

		chunk = new Chunk("Farbe. Henne", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(1);
		cell.setPadding(PADDING);

		table.addCell(cell);

		chunk = new Chunk(farbeMutter, font_small_italic);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		cell.setBorder(Rectangle.NO_BORDER);
		cell.setColspan(1);
		cell.setPadding(PADDING);

		table.addCell(cell);

		// 5. row

		if (withBoxAndComment) {

			chunk = new Chunk("Bemerkungen:", font_small);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(6);
			cell.setPadding(PADDING);

			table.addCell(cell);
		}else{
			
			
			
			chunk = new Chunk("Stand: "+datum, font_small);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(6);
			cell.setPadding(PADDING);

			table.addCell(cell);
		}

		// 5. row

		chunk = new Chunk("", font_small_italic);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		// cell.setBorderColorLeft(Color.WHITE);
		cell.setBorder(Rectangle.BOTTOM);
		cell.setBorderColorBottom(CMYKColor.DARK_GRAY);
		cell.setBorderWidthBottom(2);
		cell.setColspan(6);
		cell.setPaddingBottom(10f);

		table.addCell(cell);

		// 6. row

		if (withBoxAndComment) {

			chunk = new Chunk("Box:", font_small);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(1);
			cell.setPadding(PADDING);

			table.addCell(cell);

			chunk = new Chunk("", font_small_italic);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(1);
			cell.setPadding(PADDING);

			table.addCell(cell);

			chunk = new Chunk("Zuchtjahr:", font_small);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(1);
			cell.setPadding(PADDING);

			table.addCell(cell);

			chunk = new Chunk(jahr, font_small_italic);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(1);
			cell.setPadding(PADDING);

			table.addCell(cell);

			chunk = new Chunk("Zuchtpaar: ", font_small);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(1);
			cell.setPadding(PADDING);

			table.addCell(cell);

			chunk = new Chunk(zuchtPaarId, font_small_italic);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(1);
			cell.setPadding(PADDING);

			table.addCell(cell);
		}else{
			chunk = new Chunk("", font_small);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setColspan(6);
			cell.setPadding(PADDING);

			table.addCell(cell);
		}

		return table;
	}

	private PdfPTable getAufzuchtTable(int brut) throws AviculturaException {
		float[] widths = { 0.1428571428571429f, 0.1428571428571429f,
				0.1428571428571429f, 0.1428571428571429f, 0.1428571428571429f,
				0.1428571428571429f, 0.1428571428571429f };

		BaseFont helvetica = null;
		try {
			helvetica = BaseFont.createFont(BaseFont.HELVETICA,
					BaseFont.CP1252, BaseFont.EMBEDDED);
		} catch (DocumentException e) {
			throw new AviculturaException(AviculturaException.IO_ERROR,
					e.getMessage(), e);
		} catch (IOException e) {
			throw new AviculturaException(AviculturaException.IO_ERROR,
					e.getMessage(), e);
		}

		Font font_small = new Font(helvetica, 10, Font.NORMAL);

		PdfPTable table = new PdfPTable(widths);

		int border = Rectangle.BOX;
		int align = Element.ALIGN_CENTER;

		table.setWidthPercentage(100);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.setSpacingAfter(1.0f);
		PdfPCell cell;
		Chunk chunk;

		chunk = new Chunk("Brut: " + brut, font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setHorizontalAlignment(align);
		cell.setBorder(border);
		cell.setColspan(1);
		cell.setPadding(PADDING);

		table.addCell(cell);

		chunk = new Chunk("Ei abgelegt", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setHorizontalAlignment(align);
		cell.setBorder(border);
		cell.setColspan(1);
		cell.setPadding(PADDING);

		table.addCell(cell);

		chunk = new Chunk("schlupf am", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setHorizontalAlignment(align);
		cell.setBorder(border);
		cell.setColspan(1);
		cell.setPadding(PADDING);

		table.addCell(cell);

		chunk = new Chunk("Ringnr.", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setHorizontalAlignment(align);
		cell.setBorder(border);
		cell.setColspan(1);
		cell.setPadding(PADDING);

		table.addCell(cell);

		chunk = new Chunk("beringt am", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setHorizontalAlignment(align);
		cell.setBorder(border);
		cell.setColspan(1);
		cell.setPadding(PADDING);

		table.addCell(cell);

		chunk = new Chunk("Farbe", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setHorizontalAlignment(align);
		cell.setBorder(border);
		cell.setColspan(1);
		cell.setPadding(PADDING);

		table.addCell(cell);

		chunk = new Chunk("Geschlecht", font_small);

		cell = new PdfPCell(new Paragraph(chunk));
		cell.setHorizontalAlignment(align);
		cell.setBorder(border);
		cell.setColspan(1);
		cell.setPadding(PADDING);

		table.addCell(cell);

		// eier

		for (int u = 1; u < 7; u++) {

			chunk = new Chunk("Ei " + u, font_small);

			cell = new PdfPCell(new Paragraph(chunk));
			cell.setHorizontalAlignment(align);
			cell.setBorder(border);
			cell.setColspan(1);
			cell.setPadding(PADDING);

			table.addCell(cell);

			for (int i = 0; i < 6; i++) {

				String inhalt = "";

				if (i == 5) {
					inhalt = "O 1.0\nO 0.1";
				}

				chunk = new Chunk(inhalt, font_small);

				cell = new PdfPCell(new Paragraph(chunk));
				cell.setHorizontalAlignment(align);
				cell.setBorder(border);
				cell.setColspan(1);
				cell.setPadding(PADDING);

				table.addCell(cell);
			}

		}

		return table;
	}
}
