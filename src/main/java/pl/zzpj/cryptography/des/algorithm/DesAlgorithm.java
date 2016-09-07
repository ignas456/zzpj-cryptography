package pl.zzpj.cryptography.des.algorithm;

import pl.zzpj.cryptography.des.algorithm.interfaces.DesWorker;
import pl.zzpj.cryptography.des.algorithm.interfaces.Strategy;
import pl.zzpj.cryptography.des.utils.interfaces.ArrayUtils;
import pl.zzpj.cryptography.des.utils.interfaces.BitJuggler;
import pl.zzpj.cryptography.des.utils.interfaces.MatrixPermutation;

public class DesAlgorithm implements DesWorker {
	private static final int BLOCK_LENGTH = 8;
	private static final int BITS_IN_BYTE = 8;
	private static final int ROUNDS_NUMBER = 16;

	private BitJuggler bitJuggler;
	private MatrixPermutation matrixPermutation;
	private ArrayUtils arrayUtils;
	private Strategy strategy;
	private byte[] source;
	
	@Override
	public byte[] perform() {
		return performAlgorithm(this.source);
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

	@Override
	public DesWorker setWorkerFactory(DesWorkerFactory workerFactory) {
		return this;
	}

	@Override
	public DesWorker setBitJuggler(BitJuggler bitJuggler) {
		this.bitJuggler = bitJuggler;
		return this;
	}
	
	@Override
	public DesWorker setMatrixPermutation(MatrixPermutation matrixPermutation) {
		this.matrixPermutation = matrixPermutation;
		return this;
	}
	
	@Override
	public DesWorker setArrayUtils(ArrayUtils arrayUtils) {
		this.arrayUtils = arrayUtils;
		return this;
	}
	
	@Override
	public DesWorker setStrategy(Strategy strategy) {
		this.strategy = strategy;
		return this;
	}
	
	@Override
	public DesWorker setSource(byte[] source) {
		this.source = source;
		return this;
	}
	
	public byte[] getSource() {
		return this.source;
	}
	
	public Strategy getStrategy() {
		return this.strategy;
	}

}
