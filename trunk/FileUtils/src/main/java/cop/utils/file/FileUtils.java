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

	public static File getRelativePath(File base, File file) {
		String[] basePath = getAbsoluteFilePath(base);
		String[] filePath = getAbsoluteFilePath(file);

		if (!basePath[0].equals(filePath[0]))
			return new File(filePath[0]);

		int i = 0;
		int j = 0;
		final int maxBasePath = basePath.length;
		final int maxFilePath = filePath.length;

		StringBuilder buf = new StringBuilder();

		for (int max = Math.max(maxBasePath, maxFilePath); i < max && j < max; i++, j++)
			if (!basePath[i].equals(filePath[j]))
				break;

		for (; i < maxBasePath; i++)
			buf.append("..").append(File.separator);
		for (; j < maxFilePath - 1; j++)
			buf.append(filePath[j]).append(File.separator);

		buf.append(filePath[j]);

		return new File(buf.toString());
	}

	public static String[] getAbsoluteFilePath(File file) {
		if (file == null)
			throw new NullPointerException("File is not set");
		if (!file.isAbsolute())
			throw new IllegalArgumentException("File is not absolute: " + file.getPath());

		file = file.getAbsoluteFile();
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
