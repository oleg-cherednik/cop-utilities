/*
 * Copyright © 2013 oleg.cherednik (http://code.google.com/u/oleg.cherednik/)
 *
 * The copyright of the computer program is the property of oleg.cherednik. The program may
 * be used and/or copied in accordance with the terms and conditions of GNU Leser General Public License.
 */
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

	private EmptyFileFilter() {
	}

	// ========== FileFilter ==========

	@Override
	public boolean accept(File file) {
		return false;
	}
}
