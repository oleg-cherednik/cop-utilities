/*
 * Copyright © 2013 oleg.cherednik (http://code.google.com/u/oleg.cherednik/)
 *
 * The copyright of the computer program is the property of oleg.cherednik. The program may
 * be used and/or copied in accordance with the terms and conditions of GNU Leser General Public License.
 */
package cop.utils.file.finder;

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
