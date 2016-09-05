package pl.zzpj.cryptography.des.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.zzpj.cryptography.ZzpjCryptographyApplication;
import pl.zzpj.cryptography.des.algorithm.interfaces.Des;
import pl.zzpj.cryptography.des.exceptions.InvalidKeyException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ZzpjCryptographyApplication.class)
public class DESTest {
	
	@Autowired
	private Des sut;
	private byte[] key = {1, 2, 3, 4, 5, 6, 7, 8};
	
	@Test(expected=InvalidKeyException.class)
	public void shouldThrowInvalidKeyExceptionForNullKey() throws InvalidKeyException {
		byte[] key = null;
		
		sut.setKey(key);
	}
	
	@Test(expected=InvalidKeyException.class)
	public void shouldThrowInvalidKeyExceptionForInvalidLenghtKey() throws InvalidKeyException {
		byte[] key = {1, 2};
		
		sut.setKey(key);
	}
	
	@Test
	public void shouldEncryptAndDecriptStringMessage() throws InvalidKeyException{
		sut.setKey(key);
		String message = "Ala ma chrabąszcza";
		byte[] messageByteStream = message.getBytes();
		
		byte[] encrypted = this.sut.encrypt(messageByteStream);
		byte[] decrypted = this.sut.decrypt(encrypted);
		String msgAfterDecription = new String(decrypted);
		
		assertThat(msgAfterDecription).isEqualTo(message);
	}
	
}
