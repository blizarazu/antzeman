package app.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Vector;

import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import app.exceptions.MorfreusIsDownException;

/**
 * Morfeus euskarazko analizatzaile morfologikoa erabili ahal izateko bezeroa
 * da.
 * 
 * @author Blizarazu
 * 
 */
public class MorfeusBezeroa {

	private String url;
	private Integer maila;

	private Vector<MorfEsaldi> morfEsaldiak;

	/**
	 * MorfeusBezeroa hasieratzen du emandako URLarekin.
	 * 
	 * @param url
	 *            Morfeus zerbitzariaren URLa
	 */
	public MorfeusBezeroa(String url) {
		this.url = url;
		this.maila = new Integer(3);
		this.morfEsaldiak = new Vector<MorfEsaldi>();
		this.morfEsaldiak.addElement(new MorfEsaldi());
	}

	/**
	 * Pasatutako stringari Morfeus zerbitzariaren bitartez analisi morfologikoa
	 * egingo zaio.
	 * 
	 * @param str
	 *            analizatuko den stringa
	 * @return Morfeus zerbitzariak emandako emaitzak esaldietan stringaren
	 *         esaldietan banatuta.
	 * @throws IOException
	 * @throws ServiceException
	 * @throws MorfreusIsDownException
	 */
	public Vector<MorfEsaldi> analizatu(String str) throws IOException,
			ServiceException, MorfreusIsDownException {
		
		StringReader sr = new StringReader(str);
		BufferedReader br = new BufferedReader(sr);
		String strLine;
		while ((strLine = br.readLine()) != null) {
			String irteera = this.eskaeraBidali(strLine);
			this.irteeraProzesatu(irteera);
		}
		return morfEsaldiak;
	}

	/**
	 * Morfeus itzultzen duen testua prozezatuko da hortik hitzak, lemak eta
	 * etiketak ateratzeko eta esaldiak banatzeko.
	 * 
	 * @param irteera
	 *            Morfeusen irteera stringa
	 * @throws IOException
	 * @throws MorfreusIsDownException
	 */
	public void irteeraProzesatu(String irteera) throws IOException,
			MorfreusIsDownException {
		StringReader sr = new StringReader(irteera);
		BufferedReader br = new BufferedReader(sr);
		Vector<String> vIrt = new Vector<String>();
		String irteeraToken;
		String strLine = br.readLine();
		while (strLine != null) {
			irteeraToken = strLine + "\n";
			if ((strLine = br.readLine()) != null) {
				while (!strLine.trim().startsWith("/<")) {
					irteeraToken += strLine + "\n";
					if ((strLine = br.readLine()) == null)
						break;
				}
			}
			vIrt.addElement(irteeraToken.trim());
		}

		String hitza, lema;
		String[] tags;
		String info;
		boolean aurPuntua = false;
		for (String s : vIrt) {
			String[] tokenak = s.split("\\>\\/\n\t\\(");
			String[] s1 = tokenak[0].split("\\>\\/\\<");
			hitza = s1[0].replaceAll("/<", "").trim();
			if (s1.length > 1)
				info = s1[1].replaceAll(">/", "").trim();
			else
				info = "";
			if (tokenak.length > 1) {
				String[] s2 = tokenak[1].trim().replaceAll("\\)", "").split(
						"\n");
				String[] s3 = s2[0].trim().split("  ");
				lema = s3[0].replaceAll("\"", "").trim().toLowerCase();
				tags = s3[s3.length - 1].trim().split(" ");
			} else {
				lema = hitza;
				tags = new String[] { info };
			}
			if (info.equals("HAS_MAI")) {
				if (aurPuntua)
					morfEsaldiak.addElement(new MorfEsaldi());
				aurPuntua = false;
			} else if (hitza.equals("@@##"))
				break;
			else if (info.equals("PUNT_PUNT") || info.equals("PUNT_GALD")
					|| info.equals("PUNT_ESKL"))
				aurPuntua = true;
			else
				aurPuntua = false;
			morfEsaldiak.lastElement().addElement(new Token(hitza, lema, tags));

		}
	}

	/**
	 * Morfeusi analizatzeko string bat bidaliko dio.
	 * 
	 * @param str
	 *            analizatu nahi den stringa
	 * @return Morfeusen emaitza stringa
	 * @throws ServiceException
	 * @throws IOException
	 * @throws MorfreusIsDownException
	 * @throws IOException
	 */
	private String eskaeraBidali(String str) throws ServiceException,
			MorfreusIsDownException {
		String analisia = "";
		try {
			String analisiString = ". " + str + "\n@@##\n";
			BASE64Encoder encoder = new BASE64Encoder();
			String esaldia64 = encoder.encode(analisiString.getBytes());

			Service service = new Service();
			Call call = (Call) service.createCall();

			call.setTimeout(new Integer(60000));
			call.setTargetEndpointAddress(new java.net.URL(url));
			call.setOperationName("prozesua");
			call.addParameter("maila", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("testua", XMLType.XSD_STRING, ParameterMode.IN);
			call.setReturnType(XMLType.XSD_STRING);

			String analisia64 = (String) call.invoke(new Object[] { maila,
					esaldia64 });

			BASE64Decoder dec = new sun.misc.BASE64Decoder();
			analisia = new String(dec.decodeBuffer(analisia64));
		} catch (IOException e) {
			throw new MorfreusIsDownException(str, "aotegui004@ikasle.ehu.es");
		}
		if (analisia.compareTo("EZIN_KONEKTATU") == 0)
			throw new MorfreusIsDownException(str, "aotegui004@ikasle.ehu.es");
		else
			return analisia;
	}

	/**
	 * Morfeusen irteeratik ateratako esaldiak gordetzeko egitura.
	 * 
	 * @author Blizarazu
	 * 
	 */
	public class MorfEsaldi extends Vector<Token> {

		private static final long serialVersionUID = 1L;

		/**
		 * MorfEsaldi
		 */
		public MorfEsaldi() {
			super();
		}

		/**
		 * Esandako tokena itzultzen du.
		 * 
		 * @param i
		 *            zein posizioko tokena lortu nahi dugun
		 * @return i posizioan dagoen tokena
		 */
		public Token getToken(int i) {
			return this.elementAt(i);
		}
	}

	/**
	 * Morfeusek banatutako token bakoitza gordetzeko egitura.
	 * 
	 * @author Blizarazu
	 * 
	 */
	public class Token {

		private String hitza;
		private String lema;
		private String[] tags;

		/**
		 * Token hasieratzen du emandako parametroekin.
		 * 
		 * @param hitza
		 *            tokenari dagokion hitza
		 * @param lema
		 *            hitzaren lema
		 * @param tags
		 *            hitzaren etiketak
		 */
		public Token(String hitza, String lema, String[] tags) {
			this.hitza = hitza;
			this.lema = lema;
			this.tags = tags;
		}

		/**
		 * Tokenari dagkion hitza itzultzen du.
		 * 
		 * @return Tokenari dagokion hitza
		 */
		public String getHitza() {
			return hitza;
		}

		/**
		 * Tokenean hitza gordetzen du.
		 * 
		 * @param hitza
		 *            gorde nahi den hitza
		 */
		public void setHitza(String hitza) {
			this.hitza = hitza;
		}

		/**
		 * Tokenari dagkion hitzaren lema itzultzen du.
		 * 
		 * @return Tokenari dagpkion hitzaren lema
		 */
		public String getLema() {
			return lema;
		}

		/**
		 * Tokenean lema gordetzen du.
		 * 
		 * @param lema
		 *            gorde nahi den lema
		 */
		public void setLema(String lema) {
			this.lema = lema;
		}

		/**
		 * Tokenari dagkion hitzaren etiketak itzultzen ditu.
		 * 
		 * @return Tokenari dagopkion hitzaren etiketak
		 */
		public String[] getTags() {
			return tags;
		}

		/**
		 * Tokenean etiketak gordetzen du.
		 * 
		 * @param tags
		 *            gorde nahi diren etiketak
		 */
		public void setTags(String[] tags) {
			this.tags = tags;
		}
	}
}
