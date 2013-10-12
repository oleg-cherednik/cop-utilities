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

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

/**
 * @author Oleg Cherednik
 * @since 06.07.2013
 */
public class FileUtilsTest {
	@Test
	public void testWindowsGetRelativePath() {
		File base = buildPath("d:", "one", "two1");
		File file = buildPath("d:", "one", "two2", "three2");

		assertEquals(buildPath("..", "two2", "three2"), FileUtils.getRelativePath(base, file));
	}

	@Test
	public void testUnixGetRelativePath() {
		File base = buildPath(File.separator, "one", "two1");
		File file = buildPath(File.separator, "one", "two2", "three2");

		assertEquals(buildPath("..", "two2", "three2"), FileUtils.getRelativePath(base, file));
	}

	// ========== static ==========

	private static File buildPath(String... parts) {
		StringBuilder buf = new StringBuilder();

		for (String part : parts) {
			if (buf.length() != 0)
				buf.append(File.separator);
			buf.append(part);
		}

		return new File(buf.toString());
	}
}
