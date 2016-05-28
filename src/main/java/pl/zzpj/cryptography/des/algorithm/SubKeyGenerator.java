package pl.zzpj.cryptography.des.algorithm;

import pl.zzpj.cryptography.des.utils.BitJuggler;
import pl.zzpj.cryptography.des.utils.MatrixUtils;
import pl.zzpj.cryptography.interfaces.KeyGenerator;

public class SubKeyGenerator implements KeyGenerator{
	
	private static int NUMBER_OF_SUBKEYS = 16;
	
	private byte[] key;
	private byte[] C0,D0;
	private byte[][] subKeys;
	
	public SubKeyGenerator(byte [] key){
		this.key = key;
		subKeys = new byte[NUMBER_OF_SUBKEYS][];
	}
	
	public byte[][] generateSubKeys() { 
	    byte[] permutedKey = MatrixUtils.permute(key, DESPermutationTables.PC1);
	    byte[][] subKeys = this.performPermutedKeySplittingAndRotating(permutedKey);
	    
	    return subKeys;
	}
	
	/**
	 * Metoda jest odpowiedzialna za utworzenie podkluczy dla danego klucza
	 * @param permutedKey klucz z którego mają być wydziolone podklucze
	 * @return
	 */
	private byte[][] performPermutedKeySplittingAndRotating(byte[] permutedKey){
		splitPermutedKey(permutedKey);
        
        for (int i = 0; i < NUMBER_OF_SUBKEYS; i++)
        	generateSubKeyForRound(i);
        
	    return subKeys;
	}
	
	/**
	 * Metoda dzieli dany klucz na dwie części
	 * @param permutedKey klucz do podzielenia
	 */
	private void splitPermutedKey(byte[] permutedKey){
		C0 = BitJuggler.getBits(permutedKey, 0, 28);
        D0 = BitJuggler.getBits(permutedKey, 28, 28);
	}
	
	/**
	 * Generuje podklucz dla danej rundy
	 * @param roundNumber numer rundy
	 */
	private void generateSubKeyForRound(int roundNumber){
		C0 = BitJuggler.rotateSelectedBitsLeft(C0, 28, DESPermutationTables.SUBKEY_ROTATIONS[roundNumber]);
	    D0 = BitJuggler.rotateSelectedBitsLeft(D0, 28, DESPermutationTables.SUBKEY_ROTATIONS[roundNumber]);	
	      
		byte[] C0AndD0Together = BitJuggler.concatBitSeries(C0, 28, D0, 28);
		C0AndD0Together = MatrixUtils.permute(C0AndD0Together, DESPermutationTables.PC2);
        subKeys[roundNumber] = C0AndD0Together; 
	}

}
