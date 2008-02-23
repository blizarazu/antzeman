package app.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.htmlparser.parserapplications.StringExtractor;
import org.htmlparser.util.ParserException;
import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;

import app.exceptions.NotWebPageException;

/**
 * Tetuak fitxategietatik edo web orrietatik ateratzeko erabiltzen den klasea.
 * 
 * @author Blizarazu
 * 
 */
public class TestuIrakurlea {

	/**
	 * TestuIrakurlea hasieratzen du.
	 */
	public TestuIrakurlea() {

	}

	/**
	 * Emandako web helbidetik, web helbide horri dagokion orriaren testua
	 * ateratzen du.
	 * 
	 * @param url
	 *            web helbidea
	 * @return web helbide horren edukia HTML etiketarik gabe
	 * @throws ParserException
	 * @throws IOException
	 * @throws NotWebPageException
	 */
	public String webIrakurri(String url) throws ParserException, IOException,
			NotWebPageException {
		StringExtractor se = new StringExtractor(url);
		String text = se.extractStrings(false);

		if (text.indexOf("ParserException") == -1) {
			String luz = getFitxLuzapena(url);
			if (String.CASE_INSENSITIVE_ORDER.compare(luz, "doc") == 0)
				throw new NotWebPageException(luz);
			if (String.CASE_INSENSITIVE_ORDER.compare(luz, "pdf") == 0)
				throw new NotWebPageException(luz);
			else {
				StringReader sr = new StringReader(text);
				BufferedReader br = new BufferedReader(sr);
				String strLine;

				String str = "";
				while ((strLine = br.readLine()) != null)
					str += strLine + "\n";
				// Close the input stream
				br.close();
				sr.close();
				return str;
			}
		} else
			throw new ParserException();
	}

	/**
	 * Emandako testu fitxategia irakurtzen du.
	 * 
	 * @param fitx
	 *            testu fitxategiaren bidea. Testu soileko fitxategia, DOC edo
	 *            PDF izan daiteke.
	 * @return testuaren edukia
	 * @throws IOException
	 */
	public String irakurri(String fitx) throws IOException {
		FileInputStream fstream = new FileInputStream(fitx);
		DataInputStream in = new DataInputStream(fstream);

		String testua = "";
		String luz = this.getFitxLuzapena(fitx);
		if (String.CASE_INSENSITIVE_ORDER.compare(luz, "doc") == 0)
			testua = this.readDOC(in);
		else if (String.CASE_INSENSITIVE_ORDER.compare(luz, "pdf") == 0)
			testua = this.readPDF(in);
		else
			testua = this.readText(in);

		fstream.close();
		in.close();

		return testua;
	}

	/**
	 * Emandako fitxategiaren izenaren luzapena itzultzen du.
	 * 
	 * @param fitx
	 *            fitxategiaren bidea.
	 * @return fitxategiaren luzapena, adibidez txt, doc, pdf...
	 */
	public String getFitxLuzapena(String fitx) {
		// Windowseko fitxategi bidea Unix motako bidera pasa fitxategiaren
		// izena hartu ahal izateko
		String s = fitx.replace('\\', '/');
		// Fitxategiaren izena hartu
		s = s.substring(s.lastIndexOf("/"));
		// Fitxategiaren luzapena lortu
		return s.substring(s.lastIndexOf('.') + 1);
	}

	/**
	 * Microsoft Wordeko Doc fitxategi bat irakurtzen du.
	 * 
	 * @param in
	 *            fitxategia duen InputStream bat
	 * @return fitxategiaren edukiaren testua
	 * @throws IOException
	 */
	public String readDOC(InputStream in) throws IOException {
		HWPFDocument hw = new HWPFDocument(in);
		WordExtractor we = new WordExtractor(hw);
		String text = we.getText();

		StringReader sr = new StringReader(text);
		BufferedReader br = new BufferedReader(sr);
		String strLine;

		String str = "";
		while ((strLine = br.readLine()) != null)
			str += strLine + "\n";
		// Close the input stream
		br.close();
		sr.close();
		return str;
	}

	/**
	 * PDF fitxategi bat irakurtzen du.
	 * 
	 * @param in
	 *            fitxategia duen InputStream bat
	 * @return fitxategiaren edukiaren testua
	 * @throws IOException
	 */
	public String readPDF(InputStream in) throws IOException {
		PDFParser pdf = new PDFParser(in);
		pdf.parse();
		PDDocument pddoc = pdf.getPDDocument();
		PDFTextStripper pdfts = new PDFTextStripper();
		String text = pdfts.getText(pddoc);
		pddoc.close();

		StringReader sr = new StringReader(text);
		BufferedReader br = new BufferedReader(sr);
		String strLine;

		String str = "";
		while ((strLine = br.readLine()) != null) {
			str += strLine;
			char azkenChar = strLine.charAt(strLine.length() - 1);
			if (azkenChar == '.')
				str += "\n";
		}
		// Close the input stream
		br.close();
		sr.close();
		return str;
	}

	/**
	 * Testu soileko fitxategi bat irakurtzen du.
	 * 
	 * @param in
	 *            fitxategia duen InputStream bat
	 * @return fitxategiaren edukiaren testua
	 * @throws IOException
	 */
	public String readText(InputStream in) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;

		String str = "";
		while ((strLine = br.readLine()) != null)
			str += strLine + "\n";
		// Close the input stream
		br.close();
		return str;
	}
}
