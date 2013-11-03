package cop.icoman.exceptions;

/**
 * @author Oleg Cherednik
 * @since 05.09.2013
 */
public class UnsupportedImageException extends IconManagerException {
	private static final long serialVersionUID = -4376701236760245808L;

	public UnsupportedImageException(String message) {
		super(message);
	}
}
