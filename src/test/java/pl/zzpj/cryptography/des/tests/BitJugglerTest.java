package pl.zzpj.cryptography.des.tests;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import pl.zzpj.cryptography.des.utils.BitJuggler;


public class BitJugglerTest {

	@Test
	public void shouldPerformXOROpertionOnTheByteArrays() {
		byte[] firstArray = {1, 2, 0};
		byte[] secondArray = {2, 1};
		byte[] expectedArray = {3, 3, 0};
		
		byte[] XorOperationResult = BitJuggler.xorArrays(firstArray, secondArray);
		
		assertThat(XorOperationResult).isEqualTo(expectedArray);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void xorArraysShouldThrowIllegalArgumentExceptionForNullSource() {
		byte[] firstArray = {1, 2, 0};
		byte[] secondArray = null;
		
		BitJuggler.xorArrays(firstArray, secondArray);
	}
	
	@Test
	public void shouldReturnOneBitFromByteArray() {
		byte[] sourceArray = {1, 2};
		int bitPosition = 14;
		int expectedValue = 1;
		
		int operationResult = BitJuggler.getBit(sourceArray, bitPosition);
		
		assertThat(operationResult).isEqualTo(expectedValue);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void getBitShouldThrowIllegalArgumentExceptionForNullSource() {
		byte[] sourceArray = null;
		int bitPosition = 14;
		
		BitJuggler.getBit(sourceArray, bitPosition);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void getBitShouldThrowIllegalArgumentExceptionForNegativePosition() {
		byte[] sourceArray = {1, 2};
		int bitPosition = -1;
		
		BitJuggler.getBit(sourceArray, bitPosition);
	}
	
	@Test
	public void shouldReturnBitsFromByteArray() {
		byte[] sourceArray = {91, 124};
		int bitsNumber = 10;
		byte[] expectedBites = {91, 64};
		
		byte[] operationResult = BitJuggler.getBits(sourceArray, 0, bitsNumber);
		
		assertThat(operationResult).isEqualTo(expectedBites);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void getBitsShouldThrowIllegalArgumentExceptionForNullSource() {
		byte[] sourceArray = null;
		int bitsNumber = 10;
		
		BitJuggler.getBits(sourceArray, 0, bitsNumber);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void getBitsShouldThrowIllegalArgumentExceptionForNegativeStartPosition() {
		byte[] sourceArray = {91, 124};
		int bitsNumber = 10;
		
		BitJuggler.getBits(sourceArray, -1, bitsNumber);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void getBitsShouldThrowIllegalArgumentExceptionForNegativeBitsNumber() {
		byte[] sourceArray = {91, 124};
		int bitsNumber = -1;
		
		BitJuggler.getBits(sourceArray, 0, bitsNumber);
	}
	
	@Test
	public void shouldSetBit() {
		byte[] sourceArray = {91, 124};
		int posiotion = 10;
		int newBitValue = 0;
		byte[] expectedArray = {91, 92};
		
		BitJuggler.setBit(sourceArray, posiotion, newBitValue);
		
		assertThat(sourceArray).isEqualTo(expectedArray);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setBitShouldThrowIllegalArgumentExceptionForNullSource() {
		byte[] sourceArray = null;
		int position = 10;
		int newBitValue = 0;
		
		BitJuggler.setBit(sourceArray, position, newBitValue);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setBitShouldThrowIllegalArgumentExceptionForNegativePosition() {
		byte[] sourceArray = {91, 124};
		int position = -1;
		int newBitValue = 0;
		
		BitJuggler.setBit(sourceArray, position, newBitValue);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setBitShouldThrowIllegalArgumentExceptionForWrongValue() {
		byte[] sourceArray = {91, 124};
		int position = 10;
		int newBitValue = 3;
		
		BitJuggler.setBit(sourceArray, position, newBitValue);
	}
	
	@Test
	public void shouldSetBits() {
		byte[] destinatiedArray = {0, 0};
		byte[] sourceArray = {127, 127};
		byte[] expectedArray = {15, 0};
		
		BitJuggler.setBits(destinatiedArray, 4, sourceArray, 1, 4);
		
		assertThat(destinatiedArray).isEqualTo(expectedArray);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setBitsShouldThrowIllegalArgumentExceptionForNullSource() {
		byte[] destinatiedArray = null;
		byte[] sourceArray = {127, 127};
		
		BitJuggler.setBits(destinatiedArray, 4, sourceArray, 1, 4);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setBitsShouldThrowIllegalArgumentExceptionForNegativePosition() {
		byte[] destinatiedArray = {0, 0};
		byte[] sourceArray = {127, 127};
		
		BitJuggler.setBits(destinatiedArray, -4, sourceArray, 1, 4);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void setBitsShouldThrowIllegalArgumentExceptionForNegativeLenght() {
		byte[] destinatiedArray = {0, 0};
		byte[] sourceArray = {127, 127};
		
		BitJuggler.setBits(destinatiedArray, 4, sourceArray, 1, -4);
	}
	
	@Test
	public void shouldRotateSelectedBitsToTheLeft() {
		byte[] sourceArray = {127, 1};
		int bitsNumber = 12;
		int step = 2;
		byte[] expectedArray = {-4, 16};

		byte[] operationResult = BitJuggler.rotateSelectedBitsLeft(sourceArray, bitsNumber, step);
		
		assertThat(operationResult).isEqualTo(expectedArray);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void rotateSelectedBitsLeftShouldThrowIllegalArgumentExceptionForNullSource() {
		byte[] sourceArray = null;
		int bitsNumber = 12;
		int step = 2;

		BitJuggler.rotateSelectedBitsLeft(sourceArray, bitsNumber, step);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void rotateSelectedBitsLeftShouldThrowIllegalArgumentExceptionForNegativeNumericArgument() {
		byte[] sourceArray = {127, 1};
		int bitsNumber = -12;
		int step = 2;

		BitJuggler.rotateSelectedBitsLeft(sourceArray, bitsNumber, step);
	}
	
	@Test
	public void shouldSeparateBits() {
		byte[] sourceArray = {127, 1, 12, 2, 2,1};
		byte[] expectedArray = {124, -64, 16, 48, 0, -128, 32, 4};
		int length = 6;

		byte[] operationResult = BitJuggler.separateBits(sourceArray, length);
		
		assertThat(operationResult).isEqualTo(expectedArray);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void separateBitsShouldThrowIllegalArgumentExceptionForNullSource() {
		byte[] sourceArray = null;
		int length = 6;

		BitJuggler.separateBits(sourceArray, length);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void separateBitsShouldThrowIllegalArgumentExceptionForNegativeLength() {
		byte[] sourceArray = {127, 1, 12, 2, 2,1};
		int length = -12;

		BitJuggler.separateBits(sourceArray, length);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void separateBitsShouldThrowIllegalArgumentExceptionForWrongLength() {
		byte[] sourceArray = {127, 1, 12, 2, 2};
		int length = 3;

		BitJuggler.separateBits(sourceArray, length);
	}
	
	@Test
	public void shouldConcatBitsSeries() {
		byte[] firstSeries = {127, 1};
		byte[] secondSeries = {96, 2};
		byte[] expectedArray = {127, 24};
		int firstLenght = 10;
		int secondLenght = 3;

		byte[] operationResult = BitJuggler.concatBitSeries(firstSeries, firstLenght, secondSeries, secondLenght);
		
		assertThat(operationResult).isEqualTo(expectedArray);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void concatBitSeriesShouldThrowIllegalArgumentExceptionForNullSource() {
		byte[] firstSeries = null;
		byte[] secondSeries = {96, 2};
		int firstLenght = 10;
		int secondLenght = 3;

		BitJuggler.concatBitSeries(firstSeries, firstLenght, secondSeries, secondLenght);
	}

	@Test(expected=IllegalArgumentException.class)
	public void concatBitSeriesShouldThrowIllegalArgumentExceptionForNegativeLenght() {
		byte[] firstSeries = {127, 1};
		byte[] secondSeries = {96, 2};
		int firstLenght = -10;
		int secondLenght = 3;

		BitJuggler.concatBitSeries(firstSeries, firstLenght, secondSeries, secondLenght);
	}
	
}
