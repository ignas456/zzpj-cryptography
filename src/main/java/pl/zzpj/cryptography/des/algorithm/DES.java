package pl.zzpj.cryptography.des.algorithm;

import java.util.Arrays;

import pl.zzpj.cryptography.des.exceptions.InvalidKeyException;
import pl.zzpj.cryptography.des.utils.ArrayUtils;
import pl.zzpj.cryptography.des.utils.BitJuggler;
import pl.zzpj.cryptography.des.utils.MatrixUtils;

public class DES {

	private static final int BLOCK_LENGHT = 8;
	private static final int ROUNDS_NUMBER = 16;
	
	private FFunction fFunction;

	public DES(byte[] key) throws InvalidKeyException {
		this.validateKey(key);
		this.fFunction = new FFunction(new SubKeyGenerator(key));
	}

	/**
	 * Syfruje przesłany strumień bajtów
	 * @param source źródłowy strumień bajtów
	 * @return Zaszyfrowany strumień bajtów
	 */
	public final byte[] encrypt(byte[] source) {
		byte[][] sourceDividedInto8ByteBlocks = ArrayUtils.transformToTwoDimensionsArray(source);

		byte[][] encryptedSourceIn8ByteBlocks = new byte[sourceDividedInto8ByteBlocks.length][BLOCK_LENGHT];

		for (int i = 0; i < encryptedSourceIn8ByteBlocks.length; i++) {
			encryptedSourceIn8ByteBlocks[i] = this.encrypt8ByteBlock(sourceDividedInto8ByteBlocks[i]);
		}

		return ArrayUtils.transformToOneDimensionArray(encryptedSourceIn8ByteBlocks);
	}
	
	/**
	 * Deszyfruje przesłany strumień bajtów
	 * @param source źródłowy strumień bajtów
	 * @return Zdeszyfrowany strumień bajtów
	 */
	public final byte[] decrypt(byte[] source) {
		byte[][] sourceDividedInto8ByteBlocks = ArrayUtils.transformToTwoDimensionsArray(source);

		byte[][] decryptedSourceIn8ByteBlocks = new byte[sourceDividedInto8ByteBlocks.length][BLOCK_LENGHT];

		for (int i = 0; i < decryptedSourceIn8ByteBlocks.length; i++) {
			decryptedSourceIn8ByteBlocks[i] = this.decrypt8ByteBlock(sourceDividedInto8ByteBlocks[i]);
		}

		decryptedSourceIn8ByteBlocks = this.removeUnnecessaryBytes(decryptedSourceIn8ByteBlocks);
		
		return ArrayUtils.transformToOneDimensionArray(decryptedSourceIn8ByteBlocks);
	}
	
	private byte[] encrypt8ByteBlock(byte[] sourceBlock) {
		return this.performDESAlgorythm(sourceBlock, false);
	}
	
	private byte[] decrypt8ByteBlock(byte[] sourceBlock) {
		return this.performDESAlgorythm(sourceBlock, true);
	}
	
	private byte[] performDESAlgorythm(byte[] block, boolean operation) {
		byte[] permutedOrginalBlock = MatrixUtils.permute(block, DESPermutationTables.IP);

		int blockBitsNumber = permutedOrginalBlock.length / 2 * 8;
		
		byte[] leftBlockPart = BitJuggler.getBits(permutedOrginalBlock, 0, blockBitsNumber);
		byte[] rightBlockPart = BitJuggler.getBits(permutedOrginalBlock, blockBitsNumber, blockBitsNumber);

		for (int i = 0; i < 16; i++) {
			byte[] memoredRightPart = rightBlockPart;

			if (operation) {
				rightBlockPart = fFunction.perform(rightBlockPart, ROUNDS_NUMBER - 1 - i);
			} else {
				rightBlockPart = fFunction.perform(rightBlockPart, i);
			}

			rightBlockPart = BitJuggler.xorArrays(leftBlockPart, rightBlockPart);
			leftBlockPart = memoredRightPart;

		}

		byte[] result = BitJuggler.concatBitSeries(rightBlockPart, rightBlockPart.length * 8, leftBlockPart, leftBlockPart.length * 8);
		result = MatrixUtils.permute(result, DESPermutationTables.FP);

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
