package pl.zzpj.cryptography.des.algorithm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.zzpj.cryptography.des.utils.BitJuggler;
import pl.zzpj.cryptography.des.utils.MatrixUtils;
import pl.zzpj.cryptography.interfaces.IFFunction;
import pl.zzpj.cryptography.interfaces.KeyGenerator;

@Service
public class FFunction implements IFFunction {
	
	private final KeyGenerator keyGenerator;
	private byte[][] subKeys;
	
	@Autowired
	public FFunction(KeyGenerator subKeyGenerator){
		this.keyGenerator = subKeyGenerator;
	}
	
	public void calculateKSubKeys(byte key[]) {
		subKeys = keyGenerator.generateSubKeys(key);
	}
	
	public byte[] perform(byte[] input, int roundNumber){
		byte[] result;
	    
	    result = MatrixUtils.permute(input, DESPermutationTables.E);
	    result = BitJuggler.xorArrays(result, this.subKeys[roundNumber]);
	    result = this.useSBoxes(result);
	    result = MatrixUtils.permute(result, DESPermutationTables.P);
	    
	    return result;
	}
	
	private byte[] useSBoxes(byte[] input) {
	    byte[] inputAfterSeparation = BitJuggler.separateBits(input, 6);      // inputAfterSeparation.length = 8
	    byte[] rowAndColumn = {0, 0}; // 00000000 00000000 --> 0-7 row bits, 8-15 column bits
	    byte[] result = new byte[4];
	    
	    for (int i = 0, j = 0; i < inputAfterSeparation.length; i++, j += 4) {
	      byte[] currentByte = { inputAfterSeparation[i] };
	      
	      // Row
	      BitJuggler.setBit(rowAndColumn, 6, BitJuggler.getBit(currentByte, 0));
	      BitJuggler.setBit(rowAndColumn, 7, BitJuggler.getBit(currentByte, 5));
	      
	      // Column
	      BitJuggler.setBits(rowAndColumn, 12, currentByte, 1, 4);
	      
	      byte row = rowAndColumn[0];
	      byte column = rowAndColumn[1];
	      byte[] sBoxValue = { DESPermutationTables.S_BOXES[i][row][column] };
	      
	      BitJuggler.setBits(result, j, sBoxValue, 4, 4);
	      
	    }
	    
	    return result;
	  }
}
