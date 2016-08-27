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

	private static final int BLOCK_LENGHT = 8;
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
	
	public void setKey(byte[] key) throws InvalidKeyException {
		this.validateKey(key);
		fFunction.calculateKSubKeys(key);
	}
	
	/**
	 * Syfruje przesłany strumień bajtów
	 * @param source źródłowy strumień bajtów
	 * @return Zaszyfrowany strumień bajtów
	 */
	public final byte[] encrypt(byte[] source) {
		byte[][] sourceDividedInto8ByteBlocks = arrayUtils.transformToTwoDimensionsArray(source);

		byte[][] encryptedSourceIn8ByteBlocks = new byte[sourceDividedInto8ByteBlocks.length][BLOCK_LENGHT];

		for (int i = 0; i < encryptedSourceIn8ByteBlocks.length; i++) {
			encryptedSourceIn8ByteBlocks[i] = this.encrypt8ByteBlock(sourceDividedInto8ByteBlocks[i]);
		}

		return arrayUtils.transformToOneDimensionArray(encryptedSourceIn8ByteBlocks);
	}
	
	/**
	 * Deszyfruje przesłany strumień bajtów
	 * @param source źródłowy strumień bajtów
	 * @return Zdeszyfrowany strumień bajtów
	 */
	public final byte[] decrypt(byte[] source) {
		byte[][] sourceDividedInto8ByteBlocks = arrayUtils.transformToTwoDimensionsArray(source);

		byte[][] decryptedSourceIn8ByteBlocks = new byte[sourceDividedInto8ByteBlocks.length][BLOCK_LENGHT];

		for (int i = 0; i < decryptedSourceIn8ByteBlocks.length; i++) {
			decryptedSourceIn8ByteBlocks[i] = this.decrypt8ByteBlock(sourceDividedInto8ByteBlocks[i]);
		}

		decryptedSourceIn8ByteBlocks = this.removeUnnecessaryBytes(decryptedSourceIn8ByteBlocks);
		
		return arrayUtils.transformToOneDimensionArray(decryptedSourceIn8ByteBlocks);
	}
	
	private byte[] encrypt8ByteBlock(byte[] sourceBlock) {
		return this.performDESAlgorithm(sourceBlock, DESOperations.ENCRYPT);
	}
	
	private byte[] decrypt8ByteBlock(byte[] sourceBlock) {
		return this.performDESAlgorithm(sourceBlock, DESOperations.DECRYPT);
	}
	
	private byte[] performDESAlgorithm(byte[] block, DESOperations operation) {
		byte[] permutedOrginalBlock = matrixPermutation.permute(block, DESPermutationTables.IP);

		int blockBitsNumber = permutedOrginalBlock.length / 2 * 8;
		
		byte[] leftBlockPart = bitJuggler.getBits(permutedOrginalBlock, 0, blockBitsNumber);
		byte[] rightBlockPart = bitJuggler.getBits(permutedOrginalBlock, blockBitsNumber, blockBitsNumber);

		for (int i = 0; i < 16; i++) {
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
