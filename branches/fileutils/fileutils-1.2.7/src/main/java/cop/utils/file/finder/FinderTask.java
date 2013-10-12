/*
 * Copyright © 2013 oleg.cherednik (http://code.google.com/u/oleg.cherednik/)
 *
 * The copyright of the computer program is the property of oleg.cherednik. The program may
 * be used and/or copied in accordance with the terms and conditions of GNU Leser General Public License.
 */
package cop.utils.file.finder;

import cop.utils.file.filters.DirectoryFileFilter;
import cop.utils.file.filters.EmptyFileFilter;

import java.io.File;
import java.io.FileFilter;
import java.util.Collections;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author Oleg Cherednik
 * @since 09.06.2013
 */
final class FinderTask implements Callable<Set<String>> {
	private final File file;
	private final boolean findInSubPaths;
	private final FileFilter fileFilter;
	private final Queue<Future<Set<String>>> futures;
	private final ExecutorService pool;

	public FinderTask(FileFilter fileFilter, File file, boolean findInSubPaths, Queue<Future<Set<String>>> futures,
			ExecutorService pool) {
		this.fileFilter = fileFilter != null ? fileFilter : EmptyFileFilter.getInstance();
		this.file = file;
		this.findInSubPaths = findInSubPaths;
		this.futures = futures;
		this.pool = pool;

		futures.add(pool.submit(this));
	}

	// ========== callable ==========

	@Override
	public Set<String> call() throws Exception {
		if (Thread.currentThread().isInterrupted())
			return Collections.emptySet();

		File root = new File(file.getPath());

		if (!root.exists())
			return Collections.emptySet();

		if (findInSubPaths)
			for (File file : root.listFiles(DirectoryFileFilter.getInstance()))
				new FinderTask(fileFilter, file, findInSubPaths, futures, pool);

		Set<String> paths = new TreeSet<String>();


		for (File file : root.listFiles(fileFilter)) {
			if (Thread.currentThread().isInterrupted())
				break;
			if (fileFilter.accept(file))
				paths.add(file.getAbsolutePath());
		}

		return paths.isEmpty() ? Collections.<String>emptySet() : Collections.unmodifiableSet(paths);
	}
}
