package pl.zzpj.cryptography.des.algorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.zzpj.cryptography.des.algorithm.interfaces.DesWorker;
import pl.zzpj.cryptography.des.algorithm.interfaces.Strategy;
import pl.zzpj.cryptography.des.utils.interfaces.ArrayUtils;
import pl.zzpj.cryptography.des.utils.interfaces.BitJuggler;
import pl.zzpj.cryptography.des.utils.interfaces.MatrixPermutation;

@Service
public class DesWorkerFactory {
	public static final String SIMPLE = "Simple";
	public static final String RECURSIVE = "Recursive";
	
	private String workerType = RECURSIVE;
	
	private BitJuggler bitJuggler;
	private MatrixPermutation matrixPermutation;
	private ArrayUtils arrayUtils;
	
	@Autowired
	public DesWorkerFactory(
			ArrayUtils arrayUtils, 
			MatrixPermutation matrixPermutation, 
			BitJuggler bitJuggler) {
		this.arrayUtils = arrayUtils;
		this.matrixPermutation = matrixPermutation;
		this.bitJuggler = bitJuggler;
	}
	
	public DesWorker createWorker(byte[] source, Strategy strategy) {
		DesAlgorithm desAlgorithm = new DesAlgorithm();
		desAlgorithm.setArrayUtils(arrayUtils);
		desAlgorithm.setBitJuggler(bitJuggler);
		desAlgorithm.setMatrixPermutation(matrixPermutation);
		desAlgorithm.setStrategy(strategy);
		desAlgorithm.setSource(source);
		
		switch (workerType) {
		case "Simple":
			return desAlgorithm;
			
		case "Recursive":
			DesWorker worker = new DesRecursiveWorker(desAlgorithm);
			worker.setWorkerFactory(this);
			return worker;

		default:
			throw new IllegalStateException("Unknown worker type: " + workerType);
		}
	}

	public String getWorkerType() {
		return workerType;
	}

	public void setWorkerType(String workerType) {
		this.workerType = workerType;
	}
}
