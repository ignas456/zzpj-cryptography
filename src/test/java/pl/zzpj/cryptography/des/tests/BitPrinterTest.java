package pl.zzpj.cryptography.des.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.zzpj.cryptography.ZzpjCryptographyApplication;
import pl.zzpj.cryptography.des.utils.interfaces.BitPrinter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ZzpjCryptographyApplication.class)
public class BitPrinterTest {
	
	@Autowired
	BitPrinter bitPrinter;

	@Test
	public void shouldReturnBitRepresentationOfByteArrayInString() {
		byte[] sourceArray = {91, 124};
		String expectedString = "01011011 01111100";
		
		String operationResult = bitPrinter.printBits(sourceArray);
		
		assertThat(operationResult).isEqualTo(expectedString);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void printBitsShouldThrowIllegalArgumentExceptionForNullSource() {
		byte[] sourceArray = null;
		
		bitPrinter.printBits(sourceArray);
	}

}
