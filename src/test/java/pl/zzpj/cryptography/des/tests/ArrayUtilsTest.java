package pl.zzpj.cryptography.des.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.zzpj.cryptography.ZzpjCryptographyApplication;
import pl.zzpj.cryptography.des.utils.interfaces.ArrayUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ZzpjCryptographyApplication.class)
public class ArrayUtilsTest {

	@Autowired
	ArrayUtils arrayUtils;
	
	@Test
	public void shouldExtendArraySizeBy2() {
		byte[] sourceArray = {1, 2};
		byte[] expectedArray = {1, 2, 0, 0};
		
		byte[] operationResult = arrayUtils.extendArraySize(sourceArray, sourceArray.length + 2);
		
		assertThat(operationResult).isEqualTo(expectedArray);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void extendArraySizeShouldThrowIllegalArgumentExceptionForNullSource() {
		byte[] sourceArray = null;
		
		arrayUtils.extendArraySize(sourceArray, 2);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void extendArraySizeShouldThrowIllegalArgumentExceptionForNegativeNewLength() {
		byte[] sourceArray = {1, 2};
		
		arrayUtils.extendArraySize(sourceArray, -1);
	}

	@Test
	public void shouldTransformOneDimensionArrayToTwoDimensionsArray() {
		byte[] sourceArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
		byte[][] expectedArray = { 
				{1, 2, 3, 4, 5, 6, 7, 8},
				{9, 10, 11, 12, 13, 14, 0, 0}
			};
		
		byte[][] operationResult = arrayUtils.transformToTwoDimensionsArray(sourceArray);
		
		assertThat(operationResult).isEqualTo(expectedArray);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void transformToTwoDimensionsArrayShouldThrowIllegalArgumentExceptionForNullSource() {
		byte[] sourceArray = null;
		
		arrayUtils.transformToTwoDimensionsArray(sourceArray);
	}
	
	@Test
	public void shouldTransformTwoDimensionsArrayToOneDimensionArray() {
		byte[][] sourceArray = { 
				{1, 2, 3, 4, 5, 6, 7, 8},
				{9, 10, 11, 12, 13, 14, 0, 0}
			};
		byte[] expectedArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0, 0};
		
		byte[] operationResult = arrayUtils.transformToOneDimensionArray(sourceArray);
		
		assertThat(operationResult).isEqualTo(expectedArray);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void transformToOneDimensionArrayShouldThrowIllegalArgumentExceptionForNullSource() {
		byte[][] sourceArray = null;
		
		arrayUtils.transformToOneDimensionArray(sourceArray);
	}
}
