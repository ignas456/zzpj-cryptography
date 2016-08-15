package pl.zzpj.cryptography.des.algorithm;

import java.util.Random;

import org.springframework.stereotype.Service;

import pl.zzpj.cryptography.interfaces.IRandomKeyGenerator;

@Service
public class RandomKeyGenerator implements IRandomKeyGenerator {

	public static final String alphabet = "ABCDEFGHIJKLMNOPRSTUWYZ";
	
	public byte[] generateKey() {
		String key = "";
		Random random = new Random();
		
		for(int i = 0; i < 8 ; i++) {
			key += alphabet.charAt(random.nextInt(alphabet.length()));
		}
		
		return key.getBytes();
	}
	
}
