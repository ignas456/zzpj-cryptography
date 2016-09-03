package pl.zzpj.cryptography.des.algorithm;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.zzpj.cryptography.des.exceptions.InvalidKeyException;
import pl.zzpj.cryptography.des.utils.interfaces.ArrayUtils;
import pl.zzpj.cryptography.des.utils.interfaces.BitJuggler;
import pl.zzpj.cryptography.des.utils.interfaces.MatrixPermutation;
import pl.zzpj.cryptography.interfaces.IDes;
import pl.zzpj.cryptography.interfaces.IFFunction;

@Component
public class DES implements IDes {

	private static final int BLOCK_LENGTH = 8;
	private static final int ROUNDS_NUMBER = 16;
	
	@Autowired
	private BitJuggler bitJuggler;
	
	@Autowired
	private MatrixPermutation matrixPermutation;
	
	@Autowired
	private ArrayUtils arrayUtils;
	
	private IFFunction fFunction;
	
	@Autowired
	public DES(IFFunction fFunction) {
		this.fFunction = fFunction;
	}
	
	@Override
	public void setKey(byte[] key) throws InvalidKeyException {
		this.validateKey(key);
		fFunction.calculateKSubKeys(key);
	}
	
	@Override
	public final byte[] encrypt(byte[] source) {
		byte[][] encryptedSourceBlocks = this.performAlgorithm(source, DESOperations.ENCRYPT);
		return arrayUtils.transformBlocksToArray(encryptedSourceBlocks);
	}
	
	@Override
	public final byte[] decrypt(byte[] source) {
		byte[][] decryptedSourceBlocks = this.performAlgorithm(source, DESOperations.DECRYPT);
		return arrayUtils.transformBlocksToArray(decryptedSourceBlocks);
	}
	
	private byte[][] performAlgorithm(byte[] source, DESOperations operation) {
		
		byte[][] sourceBlocks = arrayUtils.transformArrayToBlocks(source);
		byte[][] targetBlocks = new byte[sourceBlocks.length][BLOCK_LENGTH];

		for (int i = 0; i < targetBlocks.length; i++) {
			targetBlocks[i] = this.performBlockAlgorithm(sourceBlocks[i], operation);
		}
		
		if (operation == DESOperations.DECRYPT) {
			return this.removeUnnecessaryBytes(targetBlocks);
		} else {
			return targetBlocks;
		}
	}
	
	private byte[] performBlockAlgorithm(byte[] block, DESOperations operation) {
		byte[] permutedOrginalBlock = matrixPermutation.permute(block, DESPermutationTables.IP);

		int blockBitsNumber = permutedOrginalBlock.length / 2 * 8;
		
		byte[] leftBlockPart = bitJuggler.getBits(permutedOrginalBlock, 0, blockBitsNumber);
		byte[] rightBlockPart = bitJuggler.getBits(permutedOrginalBlock, blockBitsNumber, blockBitsNumber);

		for (int i = 0; i < ROUNDS_NUMBER; i++) {
			byte[] memoredRightPart = rightBlockPart;

			switch (operation) {
			case ENCRYPT:
				rightBlockPart = fFunction.perform(rightBlockPart, i);
				break;
			case DECRYPT:
				rightBlockPart = fFunction.perform(rightBlockPart, ROUNDS_NUMBER - 1 - i);
				break;
			}

			rightBlockPart = bitJuggler.xorArrays(leftBlockPart, rightBlockPart);
			leftBlockPart = memoredRightPart;

		}

		byte[] result = bitJuggler.concatBitSeries(rightBlockPart, rightBlockPart.length * 8, leftBlockPart, leftBlockPart.length * 8);
		result = matrixPermutation.permute(result, DESPermutationTables.FP);

		return result;
	}
	
	private void validateKey(byte[] key) throws InvalidKeyException {
		if (key == null)		throw new InvalidKeyException("key is null");
		if (key.length != 8) 	throw new InvalidKeyException("key length is not 8");
	}
	
	private byte[][] removeUnnecessaryBytes(byte[][] source) {
		int counter = 0;
		for (int i = source.length - 1; i >= 0; i--) {
			for (int j = source[i].length - 1; j >= 0; j--) {
				if (source[i][j] == 0) {
					counter++;
				} else {
					break;
				}
			}
		}
		
		if (counter > 7)
			counter = 0;
		
		source[source.length - 1] = Arrays.copyOfRange(source[source.length - 1], 0, 8 - counter);
		
		return source;
	}

}
