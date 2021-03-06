package org.peie.avicultura.viewer;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
//import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import org.apache.log4j.Logger;
import org.peie.avicultura.helper.AviInternalFrame;
import org.peie.avicultura.helper.AviculturaException;
import org.peie.avicultura.pdf.PrintPdf;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PDFPrintPage;
import com.sun.pdfview.PagePanel;

public class AppPdfViewer {

	private AviInternalFrame internalFrame;
	private JDesktopPane desktopPane;
	private JPanel scrollPane;
	private PagePanel panel;
	private int pageCountNow;
	private PDFFile pdffile;
	private PDFPage page;
	private JLabel labelSeite;
	private File file;
	private FileChannel channel;
	private BufferedImage iconPdf;
	private Logger LOG = Logger.getLogger(AppDateField.class);

	public AppPdfViewer(JDesktopPane desktopPane, JPanel taskbar)
			throws AviculturaException {
		this.desktopPane = desktopPane;
		pageCountNow = 1;
		internalFrame = new AviInternalFrame(taskbar);
		URL urlPdf = ClassLoader
				.getSystemResource(Application.ICONS_SKY_LIGHT_ICONS_PNG_16X16_ACTIVE_PDF_PNG);

		try {
			iconPdf = ImageIO.read(urlPdf);
		} catch (IOException e1) {
			throw new AviculturaException(AviculturaException.IO_ERROR,
					"IO error ", e1);
		}

		internalFrame.addInternalFrameListener(new InternalFrameListener() {

			@Override
			public void internalFrameOpened(InternalFrameEvent arg0) {

			}

			@Override
			public void internalFrameIconified(InternalFrameEvent arg0) {

			}

			@Override
			public void internalFrameDeiconified(InternalFrameEvent arg0) {

			}

			@Override
			public void internalFrameDeactivated(InternalFrameEvent arg0) {

			}

			@Override
			public void internalFrameClosing(InternalFrameEvent arg0) {

				try {
					channel.close();
				} catch (IOException e) {
					LOG.error("IO error ", e);
				}

			}

			@Override
			public void internalFrameClosed(InternalFrameEvent arg0) {

			}

			@Override
			public void internalFrameActivated(InternalFrameEvent arg0) {

			}
		});

	}

	/**
	 * @throws AviculturaException
	 * @wbp.parser.entryPoint
	 */
	public void showPdf(final File file) throws AviculturaException {
		this.file = file;

		internalFrame.getContentPane().setBackground(
				SystemColor.activeCaptionBorder);
		internalFrame.getContentPane().setFont(
				new Font(AppNewBirdWindow.LABEL_FONT,
						AppNewBirdWindow.LABEL_FONT_STYLE,
						AppNewBirdWindow.LABEL_FONT_SIZE));
		internalFrame.getContentPane().setLayout(null);
		internalFrame.setSize(desktopPane.getWidth(), desktopPane.getHeight());
		internalFrame.setButtonBild(new ImageIcon(iconPdf));
		internalFrame.setResizable(false);
		internalFrame.setTitle(file.getAbsolutePath());

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, internalFrame.getWidth(), 45);
		internalFrame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JButton button = new JButton("<-");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (pageCountNow >= 1) {

					pageCountNow--;
					if (pageCountNow == 0) {
						pageCountNow = 1;
					}

					page = pdffile.getPage(pageCountNow);
					panel.setSize((int) page.getWidth(), (int) page.getHeight());
					panel.showPage(page);
					labelSeite.setText("Seite " + (pageCountNow) + " von "
							+ pdffile.getNumPages());
				}
			}
		});
		button.setBounds(10, 11, 89, 23);
		panel_1.add(button);

		JButton button_1 = new JButton("->");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (pageCountNow <= pdffile.getNumPages()) {

					pageCountNow++;
					page = pdffile.getPage(pageCountNow);
					panel.setSize((int) page.getWidth(), (int) page.getHeight());
					panel.showPage(page);
					labelSeite.setText("Seite " + (pageCountNow) + " von "
							+ pdffile.getNumPages());
				}
			}
		});
		button_1.setBounds(109, 11, 89, 23);
		panel_1.add(button_1);

		labelSeite = new JLabel();
		labelSeite.setBounds(210, 11, 89, 23);
		labelSeite.setVisible(true);
		panel_1.add(labelSeite);

		JButton buttonPrint = new JButton("Drucken");
		buttonPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {
					print();
					//print2(file);
					//PrintPdf.print(file);
				} catch (AviculturaException e) {
					e.viewError(internalFrame);
				}
			}
		});
		buttonPrint.setBounds(350, 11, 89, 23);
		panel_1.add(buttonPrint);

		scrollPane = new JPanel();
		// scrollPane.setAutoscrolls(true);
		scrollPane.setBounds(0, 45, internalFrame.getWidth(),
				internalFrame.getHeight() - 45);
		// scrollPane.setBackground(Color.green);
		internalFrame.getContentPane().add(scrollPane);
		// internalFrame.setFrameIcon(new ImageIcon(iconNewBird));
		internalFrame.setMaximizable(true);
		internalFrame.setResizable(true);
		internalFrame.setClosable(true);
		// internalFrame.setBounds(77, 11, 700, 600);

		internalFrame.setVisible(true);
		desktopPane.add(internalFrame);

		RandomAccessFile raf;
		try {

			panel = new PagePanel();

			// internalFrame.getContentPane().add(panel);
			// panel_2.add(panel);
			scrollPane.add(panel);

			raf = new RandomAccessFile(file, "r");
			channel = raf.getChannel();
			ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0,
					channel.size());
			pdffile = new PDFFile(buf);

			System.out.println(pdffile.getNumPages());

			page = pdffile.getPage(pageCountNow);

			System.out.println(page.getHeight() + " " + page.getWidth());

			panel.setSize((int) page.getWidth(), (int) page.getHeight());

			panel.showPage(page);

			labelSeite.setText("Seite " + (pageCountNow) + " von "
					+ pdffile.getNumPages());

			// panel.requestFocus();

			// channel.close();

		} catch (IOException e) {
			throw new AviculturaException(AviculturaException.PDF_ERROR,
					"IO error PDF Viewer ", e);

		}

	}

	private void print2(File f) throws AviculturaException {

		try {
			// Create a PDFFile from a File reference
			// File f = new File("c:\\juixe\\techknow.pdf");
			FileInputStream fis = new FileInputStream(f);
			FileChannel fc = fis.getChannel();
			ByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
			PDFFile pdfFile = new PDFFile(bb); // Create PDF Print Page
			PDFPrintPage pages = new PDFPrintPage(pdfFile);

			// Create Print Job
			PrinterJob pjob = PrinterJob.getPrinterJob();
			PageFormat pf = PrinterJob.getPrinterJob().defaultPage();
			pjob.setJobName(f.getName());
			Book book = new Book();
			book.append(pages, pf, pdfFile.getNumPages());
			pjob.setPageable(book);

			// Send print job to default printer
			if (pjob.printDialog()) {
				pjob.print();
			}
		} catch (IOException e) {
			throw new AviculturaException(AviculturaException.PDF_ERROR,
					"IO error PDF Viewer ", e);
		} catch (PrinterException e) {
			throw new AviculturaException(AviculturaException.PDF_ERROR,
					"IO error PDF Viewer ", e);
		}
	}

	private void print() throws AviculturaException {
		PDFPrintPage pages = new PDFPrintPage(pdffile);

		// Create Print Job
		PrinterJob pjob = PrinterJob.getPrinterJob();
		PageFormat pf = PrinterJob.getPrinterJob().defaultPage();
		pf.setOrientation(PageFormat.PORTRAIT);

		Paper paper = new Paper();
		// Set to A4 size.
		// paper.setSize(594.936, 841.536);
		paper.setSize(841.536, 594.936);

		// Set the margins.
		// paper.setImageableArea(0, 0, 594.936, 841.536);
		paper.setImageableArea(0, 0, 841.536, 594.936);
		pf.setPaper(paper);

		pjob.setJobName(file.getName());
		Book book = new Book();
		book.append(pages, pf, pdffile.getNumPages());
		pjob.setPageable(book);

		// Send print job to default printer
		try {
			if (pjob.printDialog()) {
				pjob.print();
			}

		} catch (PrinterException e) {
			throw new AviculturaException(AviculturaException.PDF_ERROR,
					"IO error PDF Viewer ", e);
		}
	}
}
