package pl.zzpj.cryptography.des.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import pl.zzpj.cryptography.des.utils.BitPrinter;

public class BitPrinterTest {

	@Test
	public void shouldReturnBitRepresentationOfByteArrayInString() {
		byte[] sourceArray = {91, 124};
		String expectedString = "01011011 01111100";
		
		String operationResult = BitPrinter.toString(sourceArray);
		
		assertThat(operationResult).isEqualTo(expectedString);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void toStringShouldThrowIllegalArgumentExceptionForNullSource() {
		byte[] sourceArray = null;
		
		BitPrinter.toString(sourceArray);
	}

}
