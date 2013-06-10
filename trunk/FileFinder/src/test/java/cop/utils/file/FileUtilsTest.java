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
