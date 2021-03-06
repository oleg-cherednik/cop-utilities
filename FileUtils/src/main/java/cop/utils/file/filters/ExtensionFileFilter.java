/*
 * Copyright © 2013 oleg.cherednik (http://code.google.com/u/oleg.cherednik/)
 *
 * The copyright of the computer program is the property of oleg.cherednik. The program may
 * be used and/or copied in accordance with the terms and conditions of GNU Leser General Public License.
 */
package cop.utils.file.filters;

import cop.utils.file.FileUtils;

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
		return extension.equalsIgnoreCase(FileUtils.getExtension(file.getName()));
	}
}
