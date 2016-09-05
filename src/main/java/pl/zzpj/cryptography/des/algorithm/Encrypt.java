package pl.zzpj.cryptography.des.algorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.zzpj.cryptography.des.algorithm.interfaces.FFunction;
import pl.zzpj.cryptography.des.algorithm.interfaces.Strategy;

@Component
public class Encrypt implements Strategy {

	@Autowired
	FFunction fFunction;

	@Override
	public byte[] performFFunction(byte[] input, int iteration) {
		return fFunction.perform(input, iteration);
	}

	@Override
	public byte[][] removeUnnecessaryBytes(byte[][] source) {
		return source;
	}

}
