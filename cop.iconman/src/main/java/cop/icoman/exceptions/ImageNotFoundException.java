package cop.icoman.exceptions;

import cop.icoman.ImageKey;

/**
 * @author Oleg Cherednik
 * @since 05.09.2013
 */
public class ImageNotFoundException extends IconManagerException {
	private static final long serialVersionUID = -6470950436657824550L;

	public ImageNotFoundException(int pos, int total) {
		super("pos = " + pos + ", total = " + total);
	}

	public ImageNotFoundException(ImageKey key) {
		super("key = '" + key + '\'');
	}
}
