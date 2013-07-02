package cop.icoman;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Oleg Cherednik
 * @since 02.07.2013
 */
public class SwapDataInputStream extends DataInputStream {
	public SwapDataInputStream(InputStream in) {
		super(in);
	}

}
