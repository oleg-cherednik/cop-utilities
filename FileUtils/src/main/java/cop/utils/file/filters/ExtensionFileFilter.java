/*
 * Copyright © 2013 RITCON LTD. (www.ritcon.ru)
 *
 * The copyright of the computer program is the property of RITCON LTD. The program may be used and/or copied
 * only with the written permission of RITCON LTD. or in accordance with the terms and conditions stipulated in the
 * agreement/contract under which the program has been supplied.
 */
package cop.utils.file.filters;

import cop.utils.file.FileUtilsLocal;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileFilter;

/**
 * @author Oleg Cherednik
 * @since 09.06.2013
 */
public final class ExtensionFileFilter implements FileFilter {
	@NotNull
	private final FileFilter delegate;
	@NotNull
	private final String extension;

	public ExtensionFileFilter(String extension) {
		this(extension, null);
	}

	public ExtensionFileFilter(String extension, FileFilter delegate) {
		this.extension = extension != null && !extension.trim().isEmpty() ? extension.trim() : "";
		this.delegate = delegate != null ? delegate : EmptyFileFilter.getInstance();
	}

	// ========== FileFilter ==========

	@Override
	public boolean accept(File file) {
		if (file == null || !file.isFile())
			return delegate.accept(file);
		return extension.equalsIgnoreCase(FileUtilsLocal.getExtension(file.getName()));
	}
}
