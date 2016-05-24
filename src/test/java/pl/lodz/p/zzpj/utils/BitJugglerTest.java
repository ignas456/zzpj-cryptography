package pl.lodz.p.zzpj.utils;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;

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
	public void shouldReturnSelectedBitFromByteArray() {
		byte[] sourceArray = {1, 2};
		int bitPosition = 14;
		int expectedValue = 1;
		
		int operationResult = BitJuggler.getBit(sourceArray, bitPosition);
		
		assertThat(operationResult).isEqualTo(expectedValue);
	}
	
	@Test
	public void shouldReturnSelectedBitsFromByteArray() {
		byte[] sourceArray = {91, 124};
		int bitsNumber = 10;
		byte[] expectedBites = {91, 64};
		
		byte[] operationResult = BitJuggler.getBits(sourceArray, 0, bitsNumber);
		
		assertThat(operationResult).isEqualTo(expectedBites);
	}
	
	@Test
	public void shouldSetSelectedBit() {
		byte[] sourceArray = {91, 124};
		int bitNumber = 10;
		int newBitValue = 0;
		byte[] expectedArray = {91, 92};
		
		BitJuggler.setBit(sourceArray, bitNumber, newBitValue);
		
		assertThat(sourceArray).isEqualTo(expectedArray);
	}
	
	@Test
	public void shouldReturnExpectedStringOfBitRepresentationOfByteArray() {
		byte[] sourceArray = {91, 124};
		String expectedString = "01011011 01111100";
		
		String operationResult = BitJuggler.toString(sourceArray);
		
		assertThat(operationResult).isEqualTo(expectedString);
	}

}
