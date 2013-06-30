/*
 * Copyright © 2013 oleg.cherednik (http://code.google.com/u/oleg.cherednik/)
 *
 * The copyright of the computer program is the property of ${project.organization.name} The program may
 * be used and/or copied in accordance with the terms and conditions of GNU Leser General Public License.
 */
package cop.utils.file;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author Oleg Cherednik
 * @since 09.06.2013
 */
public class FileUtilsTest {
	@Test
	public void testGetExtension() {
		assertNull(FileUtils.getExtension(null));
		assertNull(FileUtils.getExtension(""));
		assertNull(FileUtils.getExtension("notepad"));
		assertEquals("", FileUtils.getExtension("notepad."));
		assertEquals("txt", FileUtils.getExtension("notepad.txt"));
	}
}
