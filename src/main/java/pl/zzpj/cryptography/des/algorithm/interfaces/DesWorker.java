package pl.zzpj.cryptography.des.algorithm.interfaces;

import pl.zzpj.cryptography.des.algorithm.DesWorkerFactory;
import pl.zzpj.cryptography.des.utils.interfaces.ArrayUtils;
import pl.zzpj.cryptography.des.utils.interfaces.BitJuggler;
import pl.zzpj.cryptography.des.utils.interfaces.MatrixPermutation;

public interface DesWorker {
	public void setWorkerFactory(DesWorkerFactory workerFactory);

	public void setBitJuggler(BitJuggler bitJuggler);

	public void setMatrixPermutation(MatrixPermutation matrixPermutation);

	public void setArrayUtils(ArrayUtils arrayUtils);

	public byte[] perform();
}
