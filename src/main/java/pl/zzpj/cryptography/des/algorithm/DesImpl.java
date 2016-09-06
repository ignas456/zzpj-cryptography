package pl.zzpj.cryptography.des.algorithm;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.zzpj.cryptography.des.algorithm.interfaces.Des;
import pl.zzpj.cryptography.des.algorithm.interfaces.FFunction;
import pl.zzpj.cryptography.des.algorithm.interfaces.Strategy;
import pl.zzpj.cryptography.des.exceptions.InvalidKeyException;
import pl.zzpj.cryptography.des.utils.interfaces.ArrayUtils;
import pl.zzpj.cryptography.des.utils.interfaces.BitJuggler;
import pl.zzpj.cryptography.des.utils.interfaces.MatrixPermutation;

@Component
public class DesImpl implements Des {

	private static final int BLOCK_LENGTH = 8;
	
	private static final int BITS_IN_BYTE = 8;
	
	private static final int ROUNDS_NUMBER = 16;
	
	private FFunction fFunction;
	private BitJuggler bitJuggler;
	private MatrixPermutation matrixPermutation;
	private ArrayUtils arrayUtils;
	
	@Autowired
	private BeanFactory beanFactory;
	
	private Strategy strategy;
	
	
	@Autowired
	public DesImpl(FFunction fFunction,
				   BitJuggler bitJuggler,
				   MatrixPermutation matrixPermutation,
				   ArrayUtils arrayUtils) {
		this.fFunction = fFunction;
		this.bitJuggler = bitJuggler;
		this.matrixPermutation = matrixPermutation;
		this.arrayUtils = arrayUtils;
	}
	
	@Override
	public void setKey(byte[] key) throws InvalidKeyException {
		this.validateKey(key);
		fFunction.calculateKSubKeys(key);
	}
	

	@Override
	public final byte[] encrypt(byte[] source) {
		setStrategy(beanFactory.getBean(Encrypt.class));
		return this.performAlgorithm(source);
	}
	

	@Override
	public final byte[] decrypt(byte[] source) {
		setStrategy(beanFactory.getBean(Decrypt.class));
		return this.performAlgorithm(source);
	}
	
	private byte[] performAlgorithm(byte[] source) {
		
		byte[][] sourceBlocks = arrayUtils.transformArrayToBlocks(source);
		byte[][] targetBlocks = new byte[sourceBlocks.length][BLOCK_LENGTH];

		for (int i = 0; i < targetBlocks.length; i++) {
			targetBlocks[i] = this.performBlockAlgorithm(sourceBlocks[i]);
		}
		
		targetBlocks = strategy.removeUnnecessaryBytes(targetBlocks);
		
		return arrayUtils.transformBlocksToArray(targetBlocks);
	}
	
	private byte[] performBlockAlgorithm(byte[] block) {
		byte[] permutedOrginalBlock = matrixPermutation.permute(block, DESPermutationTables.IP);

		int blockBitsNumber = permutedOrginalBlock.length / 2 * BITS_IN_BYTE;
		
		byte[] leftBlockPart = bitJuggler.getBits(permutedOrginalBlock, 0, blockBitsNumber);
		byte[] rightBlockPart = bitJuggler.getBits(permutedOrginalBlock, blockBitsNumber, blockBitsNumber);

		for (int i = 0; i < ROUNDS_NUMBER; i++) {
			byte[] memoredRightPart = rightBlockPart;
			rightBlockPart = strategy.performFFunction(rightBlockPart, i);
			rightBlockPart = bitJuggler.xorArrays(leftBlockPart, rightBlockPart);
			leftBlockPart = memoredRightPart;
		}

		byte[] result = bitJuggler.concatBitSeries(rightBlockPart, rightBlockPart.length * BITS_IN_BYTE, leftBlockPart, leftBlockPart.length * BITS_IN_BYTE);
		result = matrixPermutation.permute(result, DESPermutationTables.FP);

		return result;
	}
	
	private void validateKey(byte[] key) throws InvalidKeyException {
		if (key == null)					throw new InvalidKeyException("key is null");
		if (key.length != BLOCK_LENGTH) 	throw new InvalidKeyException("key length is not 8");
	}
	
	// -------------------------------------------------------------------------------------------
	
	public Strategy getStrategy() {
		return strategy;
	}

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}
	

}
