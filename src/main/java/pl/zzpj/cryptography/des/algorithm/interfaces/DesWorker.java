package pl.zzpj.cryptography.des.algorithm.interfaces;

import pl.zzpj.cryptography.des.algorithm.DesWorkerFactory;
import pl.zzpj.cryptography.des.utils.interfaces.ArrayUtils;
import pl.zzpj.cryptography.des.utils.interfaces.BitJuggler;
import pl.zzpj.cryptography.des.utils.interfaces.MatrixPermutation;

public interface DesWorker {
	
	public DesWorker setWorkerFactory(DesWorkerFactory workerFactory);

	public DesWorker setBitJuggler(BitJuggler bitJuggler);

	public DesWorker setMatrixPermutation(MatrixPermutation matrixPermutation);

	public DesWorker setArrayUtils(ArrayUtils arrayUtils);
	
	public DesWorker setStrategy(Strategy strategy);
	
	public DesWorker setSource(byte[] source);

	public byte[] perform();
}
