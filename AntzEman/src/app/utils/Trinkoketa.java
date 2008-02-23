/**
 * 
 */
package app.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

/**
 * Stringak trinkotzeko erabili daitekeen klasea.
 * 
 * @author blizarazu
 * 
 */
public class Trinkoketa {

	/**
	 * String bat trinkotzen du GZIP trinkoketa erabiliz.
	 * 
	 * @param str
	 *            trinkotu nahi den stringa
	 * @return stringa trinkotuta duen byten bufferra
	 * @throws IOException
	 */
	public static final byte[] trinkotu(String str) throws IOException {
		ByteArrayOutputStream out2 = new ByteArrayOutputStream();
		GZIPOutputStream gout = new GZIPOutputStream(out2);
		gout.write(str.getBytes());
		gout.finish();
		gout.flush();
		gout.close();
		byte[] compressed2 = out2.toByteArray();

		return compressed2;
	}

	/**
	 * String batek trinkotu ondoren edukiko duen tamaina itzultzen du.
	 * 
	 * @param str
	 *            bere tamaina trinkotua zein den jakin nahi dugun stringa
	 * @return pasatako stringaren tamaina trinkotu eta gero
	 * @throws IOException
	 */
	public static final int getTamainaTrinkotuta(String str) throws IOException {
		return trinkotu(str).length;
	}
}
