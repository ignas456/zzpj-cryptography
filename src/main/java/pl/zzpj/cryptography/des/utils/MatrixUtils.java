package pl.zzpj.cryptography.des.utils;

public class MatrixUtils {
	
	/**
	 * Metoda przeprowadza permutacje lewej macierzy z prawą macierzą
	 * @param input macierz, które jest permutowana
	 * @param permutationTable macierz permutująca
	 * @return zpermutowana macierz
	 */
	public static byte[] permute(byte[] input, byte[] permutationTable) {
	    byte[] result = new byte[(permutationTable.length - 1) / 8 + 1];
	    for (int i = 0; i < permutationTable.length; i++) {
	      int value = BitJuggler.getBit(input, permutationTable[i] - 1);
	      BitJuggler.setBit(result, i, value);
	    }
	    
	    return result;
	  }
}
