package pl.zzpj.cryptography.des.keyGenerator;

import java.util.Random;

import org.springframework.beans.factory.annotation.Value;

public enum RandomKeyGenerator {
	INSTANCE;
	
	private final static String SIGNS = "ABCDEF1234567890";
	private final static Random RANDOM = new Random();
	private final static int KEY_SIGNS_NUMBER = 16;
	
	public String generateKey() {
		StringBuilder key = new StringBuilder();
		for(int i = 0; i < KEY_SIGNS_NUMBER; i++) {
			int index = RANDOM .nextInt(SIGNS.length());
			char randomChar = SIGNS.charAt(index);
			key.append(randomChar);
		}
		return key.toString();
	}
}
