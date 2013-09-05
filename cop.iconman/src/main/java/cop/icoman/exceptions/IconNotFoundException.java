package cop.icoman.exceptions;

import cop.icoman.ImageKey;

/**
 * @author Oleg Cherednik
 * @since 05.09.2013
 */
public class IconNotFoundException extends IconManagerException {
	private static final long serialVersionUID = -6470950436657824550L;

	public IconNotFoundException(int id, int total) {
		super("id = " + id + ", total = " + total);
	}

	public IconNotFoundException(ImageKey key) {
		super("key = '" + key + '\'');
	}

	public IconNotFoundException(String name) {
		super("icon file = '" + name + '\'');
	}
}
