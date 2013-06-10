package cop.utils.file;

import javax.validation.constraints.NotNull;

/**
 * @author Oleg Cherednik
 * @since 09.06.2013
 */
public final class FinderRoot {
	private final String path;
	private final boolean findInSubPaths;

	public FinderRoot(String path, boolean findInSubPaths) {
		this.path = path != null ? path.trim() : "";
		this.findInSubPaths = findInSubPaths;

		if (this.path.isEmpty())
			throw new IllegalArgumentException("'path' is not set");
	}

	@NotNull
	public String getPath() {
		return path;
	}

	public boolean isFindInSubPaths() {
		return findInSubPaths;
	}
}
