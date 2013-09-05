package cop.icoman.exceptions;

import cop.icoman.ImageKey;

/**
 * @author Oleg Cherednik
 * @since 05.09.2013
 */
public class DuplicationKeyException extends IconManagerException {
	private static final long serialVersionUID = 599978908932500627L;

	public DuplicationKeyException(ImageKey key) {
		super("key = '" + key + '\'');
	}
}
