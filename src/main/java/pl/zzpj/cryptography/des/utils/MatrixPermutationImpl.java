package pl.zzpj.cryptography.des.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.zzpj.cryptography.des.utils.interfaces.BitJuggler;
import pl.zzpj.cryptography.des.utils.interfaces.MatrixPermutation;

@Service
public class MatrixPermutationImpl implements MatrixPermutation {
	
	private BitJuggler bitJuggler;
	
	@Autowired
	public MatrixPermutationImpl(BitJuggler bitJuggler) {
		this.bitJuggler = bitJuggler;
	}
	
	public byte[] permute(byte[] input, byte[] permutationTable) {
	    byte[] result = new byte[(permutationTable.length - 1) / 8 + 1];
	    for (int i = 0; i < permutationTable.length; i++) {
	      int value = bitJuggler.getBit(input, permutationTable[i] - 1);
	      bitJuggler.setBit(result, i, value);
	    }
	    
	    return result;
	  }
}
