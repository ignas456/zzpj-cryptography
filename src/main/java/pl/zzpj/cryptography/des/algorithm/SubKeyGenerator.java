package pl.zzpj.cryptography.des.algorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.zzpj.cryptography.des.utils.interfaces.BitJuggler;
import pl.zzpj.cryptography.des.utils.interfaces.MatrixPermutation;
import pl.zzpj.cryptography.interfaces.KeyGenerator;

@Service
public class SubKeyGenerator implements KeyGenerator {
	
	private static final int NUMBER_OF_SUBKEYS = 16;
	
	@Autowired
	private BitJuggler bitJuggler;
	
	@Autowired
	private MatrixPermutation matrixPermutation;
	
	private byte[] C0, D0;
	private byte[][] subKeys;
	
	public SubKeyGenerator(){
		this.subKeys = new byte[NUMBER_OF_SUBKEYS][];
	}
	
	public byte[][] generateSubKeys(byte[] key) { 
	    byte[] permutedKey = matrixPermutation.permute(key, DESPermutationTables.PC1);
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
        
	    return this.subKeys;
	}
	
	/**
	 * Metoda dzieli dany klucz na dwie części
	 * @param permutedKey klucz do podzielenia
	 */
	private void splitPermutedKey(byte[] permutedKey){
		this.C0 = bitJuggler.getBits(permutedKey, 0, 28);
		this.D0 = bitJuggler.getBits(permutedKey, 28, 28);
	}
	
	/**
	 * Generuje podklucz dla danej rundy
	 * @param roundNumber numer rundy
	 */
	private void generateSubKeyForRound(int roundNumber){
		this.C0 = bitJuggler.rotateSelectedBitsLeft(this.C0, 28, DESPermutationTables.SUBKEY_ROTATIONS[roundNumber]);
		this.D0 = bitJuggler.rotateSelectedBitsLeft(this.D0, 28, DESPermutationTables.SUBKEY_ROTATIONS[roundNumber]);	
	      
		byte[] C0AndD0Together = bitJuggler.concatBitSeries(this.C0, 28, this.D0, 28);
		C0AndD0Together = matrixPermutation.permute(C0AndD0Together, DESPermutationTables.PC2);
		this.subKeys[roundNumber] = C0AndD0Together; 
	}

}
