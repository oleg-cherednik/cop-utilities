/*
 * Copyright © 2013 RITCON LTD. (www.ritcon.ru)
 *
 * The copyright of the computer program is the property of RITCON LTD. The program may be used and/or copied
 * only with the written permission of RITCON LTD. or in accordance with the terms and conditions stipulated in the
 * agreement/contract under which the program has been supplied.
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
