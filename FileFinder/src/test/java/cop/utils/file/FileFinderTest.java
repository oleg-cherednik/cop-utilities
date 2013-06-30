/*
 * Copyright © 2013 oleg.cherednik (http://code.google.com/u/oleg.cherednik/)
 *
 * The copyright of the computer program is the property of ${project.organization.name} The program may
 * be used and/or copied in accordance with the terms and conditions of GNU Leser General Public License.
 */
package cop.utils.file;

import cop.utils.file.filters.ExtensionFileFilter;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author Oleg Cherednik
 * @since 10.06.2013
 */
public class FileFinderTest {
	private static File tempPath;
	private static File parentPath;

	@BeforeClass
	public static void beforeClass() throws IOException {
		parentPath = createFiles(tempPath = getTemporaryPath(), "tmp");
		createFiles(parentPath, "tmp1");
		createFiles(parentPath, "tmp2");
	}

	@AfterClass
	public static void afterClass() {
		assertTrue("can't delete temporary files", deleteDir(parentPath));
	}

	@Test
	public void testFind() {
		FileFinder fileFinder = FileFinder.createBuilder().setFileFilter(new ExtensionFileFilter("jpg")).createFinder();
		Set<String> files = fileFinder.find(Collections.singleton(new FinderRoot(tempPath.getAbsolutePath(), true)));
		Iterator<String> it = files.iterator();

		assertEquals(parentPath + File.separator + "noname.jpg", it.next());
		assertEquals(parentPath + File.separator + "tmp1" + File.separator + "noname.jpg", it.next());
		assertEquals(parentPath + File.separator + "tmp2" + File.separator + "noname.jpg", it.next());
		assertFalse(it.hasNext());
	}

	private static File getTemporaryPath() throws IOException {
		File file = null;

		try {
			file = File.createTempFile("tmp", ".tmp");
			return file.getAbsoluteFile().getParentFile();
		} finally {
			if (file != null)
				file.delete();
		}
	}

	private static File createFiles(File parent, String name) throws IOException {
		File path = new File(parent, name);

		path.mkdir();

		new File(path, "noname.txt").createNewFile();
		new File(path, "noname.jpg").createNewFile();
		new File(path, "noname.avi").createNewFile();

		return path;
	}

	private static boolean deleteDir(File dir) {
		if (!dir.isDirectory())
			return dir.delete();

		for (String child : dir.list())
			if (!deleteDir(new File(dir, child)))
				return false;

		return dir.delete();
	}
}
