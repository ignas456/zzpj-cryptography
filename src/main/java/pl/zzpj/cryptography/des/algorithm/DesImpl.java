package pl.zzpj.cryptography.des.algorithm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.zzpj.cryptography.des.algorithm.interfaces.Des;
import pl.zzpj.cryptography.des.algorithm.interfaces.DesWorker;
import pl.zzpj.cryptography.des.algorithm.interfaces.FFunction;
import pl.zzpj.cryptography.des.algorithm.interfaces.Strategy;
import pl.zzpj.cryptography.des.exceptions.InvalidKeyException;

@Component
public class DesImpl implements Des {
	private static final int BLOCK_LENGTH = 8;
	
	private FFunction fFunction;
	private DesWorkerFactory workerFactory;
	
	private Strategy strategy;
	private Encrypt encryptor;
	private Decrypt decryptor;
	
	@Autowired
	public DesImpl(FFunction fFunction,
			DesWorkerFactory workerFactory,
			Encrypt encryptor,
			Decrypt decryptor) {
		this.fFunction = fFunction;
		this.workerFactory = workerFactory;
		this.encryptor = encryptor;
		this.decryptor = decryptor;
	}
	
	@Override
	public void setKey(byte[] key) throws InvalidKeyException {
		this.validateKey(key);
		fFunction.calculateKSubKeys(key);
	}

	@Override
	public final byte[] encrypt(byte[] source) {
		DesWorker worker = workerFactory.createWorker(source, this.encryptor);
		return worker.perform();
	}

	@Override
	public final byte[] decrypt(byte[] source) {
		DesWorker worker = workerFactory.createWorker(source, this.decryptor);
		return worker.perform();
	}
	
	private void validateKey(byte[] key) throws InvalidKeyException {
		if (key == null)					throw new InvalidKeyException("key is null");
		if (key.length != BLOCK_LENGTH) 	throw new InvalidKeyException("key length is not 8");
	}
	
	public Strategy getStrategy() {
		return strategy;
	}

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}
	

}
