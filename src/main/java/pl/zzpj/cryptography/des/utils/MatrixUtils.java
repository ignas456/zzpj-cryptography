package pl.zzpj.cryptography.des.utils;

public class MatrixUtils {
	public static byte[] permute(byte[] input, byte[] permutationTable) {
	    byte[] result = new byte[(permutationTable.length - 1) / 8 + 1];
	    for (int i = 0; i < permutationTable.length; i++) {
	      int value = BitJuggler.getBit(input, permutationTable[i] - 1);
	      BitJuggler.setBit(result, i, value);
	    }
	    
	    return result;
	  }
}
