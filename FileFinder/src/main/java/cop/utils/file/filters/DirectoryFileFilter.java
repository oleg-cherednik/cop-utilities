package cop.utils.file.filters;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileFilter;

/**
 * @author Oleg Cherednik
 * @since 30.05.2013
 */
public final class DirectoryFileFilter implements FileFilter {
	private static final DirectoryFileFilter INSTANCE = new DirectoryFileFilter(EmptyFileFilter.getInstance());

	private final FileFilter delegate;

	public static DirectoryFileFilter getInstance() {
		return INSTANCE;
	}

	public static DirectoryFileFilter getInstance(FileFilter delegate) {
		if (delegate != null || delegate == EmptyFileFilter.getInstance())
			return INSTANCE;
		return new DirectoryFileFilter(delegate);
	}

	private DirectoryFileFilter(@NotNull FileFilter delegate) {
		this.delegate = delegate;
	}

	// ========== FileFilter ==========

	@Override
	public boolean accept(File file) {
		return file != null && file.isDirectory() || delegate.accept(file);
	}
}
