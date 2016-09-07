package pl.zzpj.cryptography.des.algorithm;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pl.zzpj.cryptography.des.algorithm.interfaces.FFunction;
import pl.zzpj.cryptography.des.algorithm.interfaces.Strategy;

@Component
public class Decryptor implements Strategy {

	private static final int ROUNDS_NUMBER = 16;
	
	@Autowired
	FFunction fFunction;

	@Override
	public byte[] performFFunction(byte[] input, int iteration) {
		return fFunction.perform(input ,ROUNDS_NUMBER - 1 - iteration);
	}

	@Override
	public byte[][] removeUnnecessaryBytes(byte[][] source) {
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
