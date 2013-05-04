package cop.test;

/**
 * Find minimum length between given chars in given string
 * 
 * @author Oleg Cherednik
 * @since 20.02.2013
 */
public class MinCharLen {
	public static void main(String[] args) {
		String str = "when all is said and done";
		char ch1 = 'l';
		char ch2 = 'l';
		
		System.out.println(getMinLength(str, ch1, ch2));
	}
	
	private static int getMinLength(String str, char ch1, char ch2) {
		int minLen = Integer.MAX_VALUE;
		int pos1 = -1;
		int pos2 = -1;
		int pos = -1;
		
		for(char ch : str.toCharArray()) {
			pos++;
			
			if(ch != ch1 && ch != ch2)
				continue;
			
			if(ch == ch1)
				pos1 = pos;
			if(ch == ch2)
				pos2 = pos;
			
			if(pos1 >= 0 && pos2 >=0)
				minLen = Math.min(minLen, Math.abs(pos1 - pos2));
		}
		
		return minLen != Integer.MAX_VALUE ? minLen : -1;
	}
}
