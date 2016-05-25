package pl.lodz.p.zzpj.utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;


public class BitJugglerTest {

	@Test
	public void shouldPerformXOROpertionOnTheByteArrays() {
		byte[] firstArray = {1, 2, 0};
		byte[] secondArray = {2, 1};
		byte[] expectedArray = {3, 3, 0};
		
		byte[] XorOperationResult = BitJuggler.xorArrays(firstArray, secondArray);
		
		assertThat(XorOperationResult).isEqualTo(expectedArray);
	}
	
	@Test
	public void shouldExtendArraySizeBy2() {
		byte[] sourceArray = {1, 2};
		byte[] expectedArray = {1, 2, 0, 0};
		
		byte[] operationResult = BitJuggler.extendArraySize(sourceArray, sourceArray.length + 2);
		
		assertThat(operationResult).isEqualTo(expectedArray);
	}
	
	@Test
	public void shouldReturnOneBitFromByteArray() {
		byte[] sourceArray = {1, 2};
		int bitPosition = 14;
		int expectedValue = 1;
		
		int operationResult = BitJuggler.getBit(sourceArray, bitPosition);
		
		assertThat(operationResult).isEqualTo(expectedValue);
	}
	
	@Test
	public void shouldReturnBitsFromByteArray() {
		byte[] sourceArray = {91, 124};
		int bitsNumber = 10;
		byte[] expectedBites = {91, 64};
		
		byte[] operationResult = BitJuggler.getBits(sourceArray, 0, bitsNumber);
		
		assertThat(operationResult).isEqualTo(expectedBites);
	}
	
	@Test
	public void shouldSetBit() {
		byte[] sourceArray = {91, 124};
		int bitNumber = 10;
		int newBitValue = 0;
		byte[] expectedArray = {91, 92};
		
		BitJuggler.setBit(sourceArray, bitNumber, newBitValue);
		
		assertThat(sourceArray).isEqualTo(expectedArray);
	}
	
	@Test
	public void shouldReturnBitRepresentationOfByteArrayInString() {
		byte[] sourceArray = {91, 124};
		String expectedString = "01011011 01111100";
		
		String operationResult = BitJuggler.toString(sourceArray);
		
		assertThat(operationResult).isEqualTo(expectedString);
	}
	
	@Test
	public void shouldSetBits() {
		byte[] destinatiedArray = {0, 0};
		byte[] sourceArray = {127, 127};
		byte[] expectedArray = {15, 0};
		
		BitJuggler.setBits(destinatiedArray, 4, sourceArray, 1, 4);
		
		assertThat(destinatiedArray).isEqualTo(expectedArray);
	}
	
	@Test
	public void shouldRotateSelectedBitsToTheLeft() {
		byte[] sourceArray = {127, 1};
		byte[] expectedArray = {-4, 16};

		byte[] operationResult = BitJuggler.rotateSelectedBitsLeft(sourceArray, 12, 2);
		
		assertThat(operationResult).isEqualTo(expectedArray);
	}
	
	@Test
	public void shouldSeparateBits() {
		byte[] sourceArray = {127, 1, 12, 2, 2,1};
		byte[] expectedArray = {124, -64, 16, 48, 0, -128, 32, 4};
		int length = 6;

		byte[] operationResult = BitJuggler.separateBits(sourceArray, length);
		
		assertThat(operationResult).isEqualTo(expectedArray);
	}
	
	@Test
	public void shouldConcatBitsSeries() {
		byte[] firstSeries = {127, 1};
		byte[] secondSeries = {96, 2};
		byte[] expectedArray = {127, 24};

		byte[] operationResult = BitJuggler.concatBitSeries(firstSeries, 10, secondSeries, 3);
		
		assertThat(operationResult).isEqualTo(expectedArray);
	}

}
