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
