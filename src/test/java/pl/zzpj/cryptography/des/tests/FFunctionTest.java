package pl.zzpj.cryptography.des.tests;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import pl.zzpj.cryptography.des.algorithm.FFunctionImpl;
import pl.zzpj.cryptography.des.algorithm.SubKeyGenerator;

public class FFunctionTest {
	
//	private static SubKeyGenerator mockedSubKeyGenerator;
//	
//	@BeforeClass
//	public static void prepare(){
//		byte[][] expectedSubKeys = {{0, 0, 0, 19, 42, -126},
//				{0, 0, 0, 16, 35, 7},
//				{0, 0, 0, -74, 0, -124},
//			    {0, 0, 0, 64, 35, -61},
//				{0, 0, 0, 54, -96, 9},
//				{0, 0, 0, 98, 21, 66},
//				{0, 0, 0, 12, -95, 42},
//				{0, 0, 0, 100, 92, 64},
//				{0, 0, 0, 74, -104, 64},
//				{0, 0, 0, -64, -59, 56},
//				{0, 0, 0, 9, 30, 8},
//				{0, 0, 0, -40, 80, 48},
//				{0, 0, 0, 1, 74, 44},
//				{0, 0, 0, -112, 56, -112},
//				{0, 0, 0, -95, 2, 53},
//				{0, 0, 0, -93, 66, -128}};
//		
//		mockedSubKeyGenerator = Mockito.mock(SubKeyGenerator.class);
//		when(mockedSubKeyGenerator.generateSubKeys()).thenReturn(expectedSubKeys);
//	}
//	
//	@Test
//	public void shouldPerformFFunction(){
//		//given
//		byte[] bytesToCompute = {1,2,3,4};
//		FFunction fFunction = new FFunction(mockedSubKeyGenerator);
//		int roundNumber = 1;
//		byte[] expected = {22, -11, -41, 96};
//		
//		//when
//		byte[] outcome = fFunction.perform(bytesToCompute, roundNumber);
//		
//		//then
//		assertThat(outcome).isEqualTo(expected);
//		
//	}
}
