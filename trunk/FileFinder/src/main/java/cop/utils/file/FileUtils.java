/*
 * Copyright © 2013 oleg.cherednik (http://code.google.com/u/oleg.cherednik/)
 *
 * The copyright of the computer program is the property of oleg.cherednik The program may
 * be used and/or copied in accordance with the terms and conditions of GNU Leser General Public License.
 */
package cop.utils.file;

/**
 * @author Oleg Cherednik
 * @since 09.06.2013
 */
public final class FileUtils {
	private FileUtils() {
	}

	public static String getExtension(String name) {
		if (name == null)
			return null;

		name = name.trim();
		int pos = name.lastIndexOf('.');
		return pos >= 0 ? name.substring(pos + 1) : null;
	}
}
