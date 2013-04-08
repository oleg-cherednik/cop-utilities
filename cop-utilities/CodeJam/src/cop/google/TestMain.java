package cop.google;

public class TestMain
{
	public static void main(String[] arg)
	{

		int i, N = 20;
		
		for(i = 0; i < N; i--)
			System.out.print("*");

		// boolean[] buf = { false, false, false, false };
		// boolean[] code = doCode(buf);
		//
		// doDamage(code, 0);
		//
		//
		// boolean[] res = doDecode(code);
		//
		// int a = 0;
		// a++;

	}

	private static boolean[] doCode(boolean[] buf)
	{
		boolean[] code = new boolean[7];

		code[0] = buf[0];
		code[1] = buf[1];
		code[2] = buf[2];
		code[3] = buf[3];

		code[4] = code[0] ^ code[1];
		code[5] = code[1] ^ code[2];
		code[6] = code[2] ^ code[3];

		return code;
	}

	private static void doDamage(boolean[] buf, int i)
	{
		buf[i] = !buf[i];
	}

	private static boolean[] doDecode(boolean[] buf)
	{
		boolean[] code = new boolean[4];

		return code;
	}
}
