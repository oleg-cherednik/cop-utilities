/*
 * Copyright © 2013 oleg.cherednik (http://code.google.com/u/oleg.cherednik/)
 *
 * The copyright of the computer program is the property of oleg.cherednik. The program may
 * be used and/or copied in accordance with the terms and conditions of GNU Leser General Public License.
 */
/*
 * $Id$
 * $URL$
 */
package cop.utils.file;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Oleg Cherednik
 * @since 06.07.2013
 */
public final class FileUtils {
	private FileUtils() {
	}

	public static String getExtension(String filename) {
		if (filename == null)
			throw new NullPointerException("Filename is not set");
		if (filename.contains(File.pathSeparator))
			throw new IllegalArgumentException("Illegal filename: " + filename);

		filename = filename.trim();
		int pos = filename.lastIndexOf('.');
		return pos > 0 ? filename.substring(pos + 1) : null;
	}

	public static File getAbsolutePath(File base, File relativeFile) {
		checkAbsolutePath(base);
		checkRelativePath(relativeFile);

		for (String part : getPathParts(relativeFile))
			if (!".".equals(part))
				base = "..".equals(part) ? base.getParentFile() : new File(base, part);

		return base;
	}

	public static File getRelativePath(File base, File absoluteFile) {
		checkAbsolutePath(base);
		checkAbsolutePath(absoluteFile);

		String[] basePath = getPathParts(base);
		String[] filePath = getPathParts(absoluteFile);

		if (!basePath[0].equals(filePath[0]))
			return new File('.' + File.separator + filePath[0]);

		int i = 0;
		int j = 0;
		final int maxBasePath = basePath.length;
		final int maxFilePath = filePath.length;

		StringBuilder buf = new StringBuilder(".");

		for (int max = Math.min(maxBasePath, maxFilePath); i < max && j < max; i++, j++)
			if (!basePath[i].equals(filePath[j]))
				break;

		for (; i < maxBasePath; i++)
			buf.append(File.separator).append("..");
		for (; j < maxFilePath; j++)
			buf.append(File.separator).append(filePath[j]);

		return new File(buf.toString());
	}

	private static void checkAbsolutePath(File file) {
		if (file == null)
			throw new NullPointerException("File is not set");
		if (file.getPath().startsWith("."))
			throw new IllegalArgumentException("File is not absolute: " + file);
	}

	private static void checkRelativePath(File file) {
		if (file == null)
			throw new NullPointerException("File is not set");
		if (file.isAbsolute() || !file.getPath().startsWith("."))
			throw new IllegalArgumentException("File is not relative: " + file);
	}

	private static String[] getPathParts(File file) {
		assert file != null;

		File parent;
		List<String> path = new ArrayList<>();

		do {
			if ((parent = file.getParentFile()) != null) {
				path.add(file.getName());
				file = parent;
			} else
				path.add(file.getPath());
		} while (parent != null);

		Collections.reverse(path);

		return path.toArray(new String[path.size()]);
	}
}
