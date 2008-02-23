package app.GUI;

import org.eclipse.osgi.util.NLS;

/**
 * Aplikazioaren internazionalizazioa kudeatuko duen klasea.
 * 
 * @author Blizarazu
 *
 */
/**
 * @author Blizarazu
 *
 */
/**
 * @author Blizarazu
 * 
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = "app.resources.languages.lang"; //$NON-NLS-1$
	// //////////////////////////////////////////////////////////////////////////
	//
	// Strings
	//
	// //////////////////////////////////////////////////////////////////////////
	public static String analisiAzkarra;
	public static String hasiGabe;
	public static String analisiZehatza;
	public static String analisiarenPropietateak;
	public static String analizatzekoTestua;
	public static String analizatzekoTestua2;
	public static String antzekoTestuakInternetenBilatu;
	public static String aurkitutakoWebOrriak;
	public static String biTestuKonparatu;
	public static String biTestuKonparatu_toolTipText;
	public static String bilaketaGunea;
	public static String bilatu;
	public static String testuZatiak;
	public static String antzekotasuna;
	public static String emaitzaIreki;
	public static String emaitzaIreki_toolTipText;
	public static String analisiAzkarrikEz;
	public static String emaitzakAztertu;
	public static String azkenEmaitzaIkusi;
	public static String emaitzakGorde;
	public static String esteka;
	public static String filtratu;
	public static String fitxategia;
	public static String webHelbidea;
	public static String hasi;
	public static String bilatzenHasi;
	public static String hizkuntza;
	public static String joan;
	public static String informazioa;
	public static String internetenBilatu;
	public static String internetenBilatu_toolTipText;
	public static String orriHonekinKonparatu;
	public static String konparatzekoTestua;
	public static String konparatzekoWebOrria;
	public static String konparatzekoTestua2;
	public static String paragrafoakKonparatuDira;
	public static String fitxategia_menu;
	public static String ikusi_menu;
	public static String aukerak_menu;
	public static String hizkuntza_menu;
	public static String analisiDeskrib1;
	public static String bilaketaDeskrib1;
	public static String euskara;
	public static String gaztelera;
	public static String ingelesa;
	public static String orria;
	public static String guztiaErakutsi;
	public static String filtratuaErakutsi;
	public static String testuWebKonparatu;
	public static String testuWebKonparatu_toolTipText;
	public static String testua;
	public static String testuakEzGorde;
	public static String testuakGorde;
	public static String batazbestekoAntzekotasuna;
	public static String webOrria;
	
	public static String kointziditutakoHitzak;
	public static String testuOsoa;
	public static String paragrafoak;
	public static String esaldiak;
	public static String biTestuAntzeko1;
	public static String biTestuAntzeko2;
	public static String analisiAzkEginda;
	public static String errorea;
	public static String ezinFitxIrakurri;
	public static String esaldiakKonparatuDira;
	public static String adiFitxEzAldatu;
	public static String testuakEzAldatu;
	public static String xmlFitx;
	public static String ezinKonfFitxIrak;
	public static String ezinKonfFitxIreki;
	protected static String ezinEmaFitxIrak;
	protected static String ezinEmaFitxIreki;
	public static String ezinOrriaKargatu;
	public static String ezWebOrri1;
	public static String ezWebOrri2;
	public static String ezinKonfGorde;
	public static String anZehatzDesk1;
	public static String anTestuaTooltip;
	public static String konpTestuaTooltip;
	public static String testuenHizkuntza;
	public static String analisiDeskrib2;
	public static String esaldiToolTip;
	public static String esaldiakKonparatu;
	public static String paragrafoToolTip;
	public static String paragrafoakKonparatu;
	public static String analisiaHasieratzen;
	public static String analisiaEgiten;
	public static String analisiaBukatuta;
	public static String erroreBatSortuDa;
	public static String ezinMorfeusKonektatu;
	public static String morfeusErorita;
	public static String mezuaBidali1;
	public static String mezuaBidali2;
	public static String morfeusZerbitzuaEroritaDago;
	public static String ezinBilaketaEgin;
	public static String bilaketaBukatuta;
	public static String bilaketaEgiten;
	public static String bilaketaHasieratzen;
	public static String bilatuDeskr2;
	public static String testuarenHizkuntza;
	public static String anTestuHizkAukeratu;
	public static String jasanFitxGuzt;
	public static String docFitxategiak;
	public static String pdfFitxategiak;
	public static String txtFitxategiak;
	public static String datFitxategiak;
	public static String fitxategiGuztiak;
	public static String zehaztasunMaila;
	
	
	// //////////////////////////////////////////////////////////////////////////
	//
	// Constructor
	//
	// //////////////////////////////////////////////////////////////////////////
	private Messages() {
		// do not instantiate
	}

	// //////////////////////////////////////////////////////////////////////////
	//
	// Class initialization
	//
	// //////////////////////////////////////////////////////////////////////////
	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
}
