package pl.zzpj.cryptography.des.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import pl.zzpj.cryptography.des.algorithm.DES;
import pl.zzpj.cryptography.des.exceptions.InvalidKeyException;


public class DESTest {
	
	private DES sut;
	private byte[] key = {1, 2, 3, 4, 5, 6, 7, 8};
	
	@Before
	public void init() throws InvalidKeyException {
		this.sut = new DES(this.key);
	}
	
	@Test(expected=InvalidKeyException.class)
	public void shouldThrowInvalidKeyExceptionForNullKey() throws InvalidKeyException {
		byte[] key = null;
		
		new DES(key);
	}
	
	@Test(expected=InvalidKeyException.class)
	public void shouldThrowInvalidKeyExceptionForInvalidLenghtKey() throws InvalidKeyException {
		byte[] key = {1, 2};
		
		new DES(key);
	}
	
	@Test
	public void shouldEncryptAndDecriptStringMessage(){
		String message = "Ala ma chrabąszcza";
		byte[] messageByteStream = message.getBytes();
		
		byte[] encrypted = this.sut.encrypt(messageByteStream);
		byte[] decrypted = this.sut.decrypt(encrypted);
		String msgAfterDecription = new String(decrypted);
		
		assertThat(msgAfterDecription).isEqualTo(message);
	}
	
}