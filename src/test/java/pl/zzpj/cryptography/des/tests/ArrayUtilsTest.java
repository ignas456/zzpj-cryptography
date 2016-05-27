package pl.zzpj.cryptography.des.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import pl.zzpj.cryptography.des.utils.ArrayUtils;

public class ArrayUtilsTest {

	@Test
	public void shouldExtendArraySizeBy2() {
		byte[] sourceArray = {1, 2};
		byte[] expectedArray = {1, 2, 0, 0};
		
		byte[] operationResult = ArrayUtils.extendArraySize(sourceArray, sourceArray.length + 2);
		
		assertThat(operationResult).isEqualTo(expectedArray);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void extendArraySizeShouldThrowIllegalArgumentExceptionForNullSource() {
		byte[] sourceArray = null;
		
		ArrayUtils.extendArraySize(sourceArray, 2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void extendArraySizeShouldThrowIllegalArgumentExceptionForNegativeNewLength() {
		byte[] sourceArray = {1, 2};
		
		ArrayUtils.extendArraySize(sourceArray, -1);
	}

	@Test
	public void shouldTransformOneDimensionArrayToTwoDimensionsArray() {
		byte[] sourceArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
		byte[][] expectedArray = { 
				{1, 2, 3, 4, 5, 6, 7, 8},
				{9, 10, 11, 12, 13, 14, 0, 0}
			};
		
		byte[][] operationResult = ArrayUtils.transformToTwoDimensionsArray(sourceArray);
		
		assertThat(operationResult).isEqualTo(expectedArray);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void transformToTwoDimensionsArrayShouldThrowIllegalArgumentExceptionForNullSource() {
		byte[] sourceArray = null;
		
		ArrayUtils.transformToTwoDimensionsArray(sourceArray);
	}
	
	@Test
	public void shouldTransformTwoDimensionsArrayToOneDimensionArray() {
		byte[][] sourceArray = { 
				{1, 2, 3, 4, 5, 6, 7, 8},
				{9, 10, 11, 12, 13, 14, 0, 0}
			};
		byte[] expectedArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0, 0};
		
		byte[] operationResult = ArrayUtils.transformToOneDimensionArray(sourceArray);
		
		assertThat(operationResult).isEqualTo(expectedArray);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void transformToOneDimensionArrayShouldThrowIllegalArgumentExceptionForNullSource() {
		byte[][] sourceArray = null;
		
		ArrayUtils.transformToOneDimensionArray(sourceArray);
	}
}
