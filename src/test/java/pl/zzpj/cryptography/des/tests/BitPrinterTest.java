package pl.zzpj.cryptography.des.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.zzpj.cryptography.ZzpjCryptographyApplication;
import pl.zzpj.cryptography.des.utils.BitPrinterImpl;
import pl.zzpj.cryptography.des.utils.interfaces.BitJuggler;
import pl.zzpj.cryptography.des.utils.interfaces.BitPrinter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ZzpjCryptographyApplication.class)
public class BitPrinterTest {
	
	@Autowired
	private BitPrinter bitPrinter;

	@Test
	public void shouldReturnBitRepresentationOfByteArrayInString() {
		//given
		byte[] sourceArray = {91, 124};
		int[] expectedBits = {0,1,0,1,1,0,1,1,0,1,1,1,1,1,0,0};
		String expectedString = "01011011 01111100";
		BitJuggler bitJuggler = Mockito.mock(BitJuggler.class);
		for(int i = 0; i < expectedBits.length;i++) {
			Mockito.when(bitJuggler.getBit(sourceArray, i)).thenReturn(expectedBits[i]);
		}
		BitPrinter bitPrinter = new BitPrinterImpl(bitJuggler);
		//when
		String operationResult = bitPrinter.printBits(sourceArray);
		//then
		assertThat(operationResult).isEqualTo(expectedString);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void toStringShouldThrowIllegalArgumentExceptionForNullSource() {
		byte[] sourceArray = null;
		
		bitPrinter.printBits(sourceArray);
	}

}
