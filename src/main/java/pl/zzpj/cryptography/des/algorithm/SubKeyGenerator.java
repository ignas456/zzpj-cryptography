package pl.zzpj.cryptography.des.algorithm;

import pl.zzpj.cryptography.des.utils.BitJuggler;
import pl.zzpj.cryptography.des.utils.MatrixUtils;

public class SubKeyGenerator {
	
	private byte[] key;
	
	public SubKeyGenerator(byte [] key){
		this.key = key;
	}
	
	public byte[][] generateSubKeys() { 
	    byte[] permutedKey = MatrixUtils.permute(key, DESPermutationTables.PC1);
	    byte[][] subKeys = this.performPermutedKeySplittingAndRotating(permutedKey);
	    
	    return subKeys;
	  }
	  
	  private byte[][] performPermutedKeySplittingAndRotating(byte[] permutedKey){
		  	byte[][] subKeys = new byte[16][];
		  	byte[] C0 = BitJuggler.getBits(permutedKey, 0, 28);
		    byte[] D0 = BitJuggler.getBits(permutedKey, 28, 28);
		    
		    for (int i = 0; i < 16; i++) {
		      C0 = BitJuggler.rotateSelectedBitsLeft(C0, 28, DESPermutationTables.SUBKEY_ROTATIONS[i]);
		      D0 = BitJuggler.rotateSelectedBitsLeft(D0, 28, DESPermutationTables.SUBKEY_ROTATIONS[i]);
		      
		      byte[] C0AndD0Together = BitJuggler.concatBitSeries(C0, 28, D0, 28);
		      C0AndD0Together = MatrixUtils.permute(C0AndD0Together, DESPermutationTables.PC2);
		      subKeys[i] = C0AndD0Together; 
		    }
		    return subKeys;
	  }

}
