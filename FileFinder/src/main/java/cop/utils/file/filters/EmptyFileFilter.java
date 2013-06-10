package cop.utils.file.filters;

import java.io.File;
import java.io.FileFilter;

/**
 * @author Oleg Cherednik
 * @since 09.06.2013
 */
public final class EmptyFileFilter implements FileFilter {
	private static final EmptyFileFilter INSTANCE = new EmptyFileFilter();

	public static EmptyFileFilter getInstance() {
		return INSTANCE;
	}

	public EmptyFileFilter() {
	}

	// ========== FileFilter ==========

	@Override
	public boolean accept(File file) {
		return false;
	}
}
