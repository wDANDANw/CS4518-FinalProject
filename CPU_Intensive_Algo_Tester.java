
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Date;
import java.io.UnsupportedEncodingException;
import java.lang.Math;

public class CPU_Intensive_Algo_Tester {

	//	Helpful Link: https://www.geeksforgeeks.org/sha-256-hash-in-java/
	public static byte[] getSHA(String input) throws NoSuchAlgorithmException 
    {  
        // Static getInstance method is called with hashing SHA  
        MessageDigest md = MessageDigest.getInstance("SHA-256");  
  
        // digest() method called  
        // to calculate message digest of an input  
        // and return array of byte 
        return md.digest(input.getBytes(StandardCharsets.UTF_8));  
    } 
    
	// Helpful link: https://stackoverflow.com/questions/9655181/how-to-convert-a-byte-array-to-a-hex-string-in-java
	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for (int j = 0; j < bytes.length; j++) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = HEX_ARRAY[v >>> 4];
	        hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	public static void main(String args[]) throws NoSuchAlgorithmException {
		
		long start = System.currentTimeMillis();
		String print = null;
		BigInteger fact = BigInteger.ONE;
		double tanlol = 1;
		long LoopEnd = 100000;
		long numOfDevices = 10;
		
//		Algo 1: factorial :
//		Start : 1582329294493 |  End : 1582329316295 | Diff : 21802 | Loop to 100000 | Print : 5.54019E555970 -> Average 2180
//		for (int i = 1; i < numOfDevices; i ++) {
//			for(int j = 1; j < LoopEnd; j++) {
//				fact = fact.multiply(BigInteger.valueOf(i));
//				if(j%1000 == 0) {System.out.println("J now is " + j);}
//			}
//		}

//		Algo 2: 3 layers of tan+atan :
//		Start : 1582329409365 |  End : 1582329409784 | Diff : 419 | Loop to 100000 | Print : 1.2345678912605856E14 -> Average 42
//		for (int i = 0; i < numOfDevices; i++) {
//			for (int j = 0; j < LoopEnd; j++) {
//				double local = Math.tan(Math.atan(Math.tan(Math.atan(Math.tan(Math.atan(123456789.123456789))))));
//				Math.cbrt(local); 
//				tanlol += local;
//			}
//		}
		
//		Algo 3: 5 layers of tan+atan : 5838
//		Start : 1582329468536 |  End : 1582329469146 | Diff : 610 | Loop to 100000 | Print : 1.2345678912605856E14 -> Average 61
//		for (int i = 0; i < numOfDevices; i++) {
//			for (int j = 0; j < LoopEnd; j++) {
//				double local = Math.tan(Math.atan(Math.tan(Math.atan(Math.tan(Math.atan(Math.tan(Math.atan(Math.tan(Math.atan(123456789.123456789))))))))));
//				Math.cbrt(local); 
//				tanlol += local;
//			}
//		}
		
		
//		SHA ALGOS
//		Helpful Link: https://stackoverflow.com/questions/44087707/how-to-check-if-the-first-n-bytes-of-a-string-are-zeroes
		
//		Algo 4 : SHA256 - first 2 as 0
//		Start : 1582326642922 |  End : 1582326642969 | Total : 47 | Average : 4
//		Start : 1582326926047 |  End : 1582326926083 | Total : 36 | Average : 3
//		[13, 1, 4, 6, 5, 2, 1, 4, 0, 0]

//		Algo 5: SHA256 - first 4 as 0
//		Start : 1582326712191 |  End : 1582326712607 | Total : 416 | Average : 41
//		Start : 1582326879552 |  End : 1582326879967 | Total : 415 | Average : 41
//		[50, 100, 34, 64, 24, 13, 4, 51, 60, 15]
		
//		Algo 6: SHA256 - first 5 as 0
//		int d2_mask = (digest[2] >>> 4);
//		found = (digest[0] == 0 && digest[1] == 0 && d2_mask == 0);
//		Start : 1582326846442 |  End : 1582326851975 | Total : 5533 | Average : 553
//		[479, 920, 1216, 491, 126, 1086, 295, 535, 183, 202]
		
//		Algo 7: SHA256 - first 6 as 0
//		found = (digest[0] == 0 && digest[1] == 0 && digest[2] == 0);
//		Start : 1582327022091 |  End : 1582327119418 | Total : 97327 | Average : 9732
//		[4949, 8615, 13685, 3123, 13001, 12314, 4571, 3620, 30247, 3202]
		
		
//		Algo 8: SHA256 - first 7 as 0
//		int d3_mask = (digest[3] >>> 4);
//		found = (digest[0] == 0 && digest[1] == 0 && digest[2] == 0 && d3_mask == 0);
//		Start : 1582327192315 |  End : 1582328521928 | Total : 1329613 | Average : 132961
//		[52168, 273955, 125926, 34442, 99654, 312513, 30048, 275362, 99516, 26029]
		
//		String hashString = "TEST";
//		long nonce = 0L;
//		boolean found = false;
//		byte[] digest = new byte[32];
//		String textBytes;
//		long[] timeCostList = new long[numOfDevices];
//		long last = start;
//		
//		for (int i = 0; i < numOfDevices; i++) {
//			
//			String str = hashString + i;
//			System.out.println(str);
//			
//			do {
//				textBytes = (str + nonce);
//				digest = getSHA(textBytes);
//				int d3_mask = (digest[3] >>> 4);
//				found = (digest[0] == 0 && digest[1] == 0 && digest[2] == 0 && d3_mask == 0);
//				
//				++nonce;
//			} while (!found);
//			
//			
//			System.out.println("Found at SHA256: ( " + str + (nonce-1L) + " ) ");
//			System.out.println(Arrays.toString(digest));
//			
//			long timeThisRound = System.currentTimeMillis();	
//			timeCostList[i] = timeThisRound - last;
//			last = timeThisRound;
//		}
//		
//		LoopEnd = nonce;
		
		
		
		long end = System.currentTimeMillis();
		long diff = end - start;

//		Output for first three Algos
		BigDecimal bd = new BigDecimal(fact);
		NumberFormat formatter = new DecimalFormat("0.#####E0");
		String str = formatter.format(bd);
		System.out.println("Start : " + start + " |  End : " + end + " | Diff : " + diff + 
					" | Loop to " + LoopEnd + " | Print : " + tanlol);
		
		
//		Output for SHA256
//		System.out.println("Start : " + start + " |  End : " + end + " | Total : " + diff + " | Average : " + diff/10);
//		for (int i = 0; i < 10; i ++) {
//			System.out.println("Time for loop " + i + " is " + timeCostList[i]);
//		}
//		System.out.println(Arrays.toString(timeCostList));
		
			
	}
	
}
