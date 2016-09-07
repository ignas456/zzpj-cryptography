package pl.zzpj.cryptography.des.algorithm;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

import pl.zzpj.cryptography.des.algorithm.interfaces.DesWorker;
import pl.zzpj.cryptography.des.utils.interfaces.ArrayUtils;
import pl.zzpj.cryptography.des.utils.interfaces.BitJuggler;
import pl.zzpj.cryptography.des.utils.interfaces.MatrixPermutation;

public class DesRecursiveWorker extends RecursiveTask<byte[]> implements DesWorker {

	private static final long serialVersionUID = 1L;
	private static final int THRESHOLD = 1024 * 1024 / 4; // 0,25 MB
	
	private DesAlgorithm desAlgorithm;
	private DesWorkerFactory workerFactory;
	
	public DesRecursiveWorker(DesAlgorithm desAlgorithm) {
		this.desAlgorithm = desAlgorithm;
	}
	
	@Override
	public byte[] perform() {
		return this.compute();
	}

	@Override
	protected byte[] compute() {
		if (desAlgorithm.getSource().length <= THRESHOLD) {
			return desAlgorithm.perform();
		}
		
		int start = 0;
		int mid = THRESHOLD;
		int end = desAlgorithm.getSource().length;
		
		byte[] firstHalf = Arrays.copyOfRange(desAlgorithm.getSource(), start, mid);
		byte[] secondHalf = Arrays.copyOfRange(desAlgorithm.getSource(), mid, end);
		
		DesRecursiveWorker worker1 = (DesRecursiveWorker) workerFactory.createWorker(firstHalf, desAlgorithm.getStrategy());
		DesRecursiveWorker worker2 = (DesRecursiveWorker) workerFactory.createWorker(secondHalf, desAlgorithm.getStrategy());
		
		invokeAll(worker1, worker2);
		firstHalf = worker1.join();
		secondHalf = worker2.join();
		
		byte[] result = new byte[firstHalf.length + secondHalf.length];
		System.arraycopy(firstHalf, 0, result, 0, firstHalf.length);
		System.arraycopy(secondHalf, 0, result, firstHalf.length, secondHalf.length);
		return result;
	}
	
	@Override
	public void setWorkerFactory(DesWorkerFactory workerFactory) {
		this.workerFactory = workerFactory;
	}
	
	@Override
	public void setBitJuggler(BitJuggler bitJuggler) {
		desAlgorithm.setBitJuggler(bitJuggler);
	}
	
	@Override
	public void setMatrixPermutation(MatrixPermutation matrixPermutation) {
		desAlgorithm.setMatrixPermutation(matrixPermutation);
	}
	
	@Override
	public void setArrayUtils(ArrayUtils arrayUtils) {
		desAlgorithm.setArrayUtils(arrayUtils);
	}
	
}


