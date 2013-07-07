/*
 * Copyright © 2013 oleg.cherednik (http://code.google.com/u/oleg.cherednik/)
 *
 * The copyright of the computer program is the property of oleg.cherednik. The program may
 * be used and/or copied in accordance with the terms and conditions of GNU Leser General Public License.
 */
package cop.utils.file.finder;

import cop.utils.file.filters.EmptyFileFilter;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Oleg Cherednik
 * @since 09.06.2013
 */
public final class FileFinder {
	private final int threadAmount;
	@NotNull
	private final FileFilter fileFilter;

	public static Builder createBuilder() {
		return new Builder();
	}

	private FileFinder(Builder builder) {
		fileFilter = builder.fileFilter;
		threadAmount = builder.threadAmount;
	}

	@NotNull
	public Set<String> find(Collection<FinderRoot> roots) {
		roots = getUniqueRoots(roots);

		if (roots == null || roots.isEmpty())
			return Collections.emptySet();

		final Queue<Future<Set<String>>> futures = new LinkedBlockingQueue<>();
		ExecutorService pool = Executors.newFixedThreadPool(threadAmount);

		for (FinderRoot root : roots)
			new FinderTask(fileFilter, new File(root.getPath()), root.isFindInSubPaths(), futures, pool);

		return getResults(futures);
	}

	// ========== static ==========

	@NotNull
	private static List<FinderRoot> getUniqueRoots(Collection<FinderRoot> roots) {
		if (roots == null || roots.isEmpty())
			return Collections.emptyList();

		Map<String, FinderRoot> map = new HashMap<>(roots.size());
		FinderRoot tmp;

		for (FinderRoot root : roots) {
			if (root == null)
				continue;

			tmp = map.get(root.getPath());

			if (tmp == null || !tmp.isFindInSubPaths())
				map.put(root.getPath(), root);
		}

		if (map.isEmpty())
			return Collections.emptyList();
		return Collections.unmodifiableList(new ArrayList<>(map.values()));
	}

	@NotNull
	private static Set<String> getResults(Queue<Future<Set<String>>> futures) {
		Set<String> res = new TreeSet<>();

		try {
			while (!futures.isEmpty()) {
				res.addAll(futures.poll().get());
			}
		} catch(Exception ignored) {
		}

		return res.isEmpty() ? Collections.<String>emptySet() : Collections.unmodifiableSet(res);
	}

	// ========== builder ==========

	public static class Builder {
		private int threadAmount = 10;
		@NotNull
		private FileFilter fileFilter = EmptyFileFilter.getInstance();

		private Builder() {
		}

		public FileFinder createFinder() {
			if (fileFilter == EmptyFileFilter.getInstance())
				throw new IllegalArgumentException("'fileFilter' is not set");
			return new FileFinder(this);
		}

		public Builder setThreadAmount(int threadAmount) {
			this.threadAmount = threadAmount > 0 ? threadAmount : 10;
			return this;
		}

		public Builder setFileFilter(FileFilter fileFilter) {
			this.fileFilter = fileFilter != null ? fileFilter : EmptyFileFilter.getInstance();
			return this;
		}
	}
}
