package pl.zzpj.cryptography.des.algorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.zzpj.cryptography.des.algorithm.interfaces.FFunction;
import pl.zzpj.cryptography.des.algorithm.interfaces.KeyGenerator;
import pl.zzpj.cryptography.des.utils.interfaces.BitJuggler;
import pl.zzpj.cryptography.des.utils.interfaces.MatrixPermutation;

@Service
public class FFunctionImpl implements FFunction {
	
	private KeyGenerator keyGenerator;
	private BitJuggler bitJuggler;
	private MatrixPermutation matrixPermutation;
	
	private byte[][] subKeys;
	
	@Autowired
	public FFunctionImpl(KeyGenerator subKeyGenerator,
			BitJuggler bitJuggler,
			MatrixPermutation matrixPermutation){
		this.keyGenerator = subKeyGenerator;
		this.bitJuggler = bitJuggler;
		this.matrixPermutation = matrixPermutation;
	}
	
	public void calculateKSubKeys(byte key[]) {
		subKeys = keyGenerator.generateSubKeys(key);
	}
	
	public byte[] perform(byte[] input, int roundNumber){
		byte[] result;
	    
	    result = matrixPermutation.permute(input, DESPermutationTables.E);
	    result = bitJuggler.xorArrays(result, this.subKeys[roundNumber]);
	    result = this.useSBoxes(result);
	    result = matrixPermutation.permute(result, DESPermutationTables.P);
	    
	    return result;
	}
	
	private byte[] useSBoxes(byte[] input) {
	    byte[] inputAfterSeparation = bitJuggler.separateBits(input, 6);      // inputAfterSeparation.length = 8
	    byte[] rowAndColumn = {0, 0}; // 00000000 00000000 --> 0-7 row bits, 8-15 column bits
	    byte[] result = new byte[4];
	    
	    for (int i = 0, j = 0; i < inputAfterSeparation.length; i++, j += 4) {
	      byte[] currentByte = { inputAfterSeparation[i] };
	      
	      // Row
	      bitJuggler.setBit(rowAndColumn, 6, bitJuggler.getBit(currentByte, 0));
	      bitJuggler.setBit(rowAndColumn, 7, bitJuggler.getBit(currentByte, 5));
	      
	      // Column
	      bitJuggler.setBits(rowAndColumn, 12, currentByte, 1, 4);
	      
	      byte row = rowAndColumn[0];
	      byte column = rowAndColumn[1];
	      byte[] sBoxValue = { DESPermutationTables.S_BOXES[i][row][column] };
	      
	      bitJuggler.setBits(result, j, sBoxValue, 4, 4);
	      
	    }
	    
	    return result;
	  }
}
