package cop.icoman.exceptions;

import cop.icoman.ImageKey;

/**
 * @author Oleg Cherednik
 * @since 05.09.2013
 */
public class DuplicateException extends IconManagerException {
	private static final long serialVersionUID = 599978908932500627L;

	public DuplicateException(ImageKey key) {
		super("image key = '" + key + '\'');
	}

	public DuplicateException(String icon) {
		super("icon = '" + icon + '\'');
	}
}
