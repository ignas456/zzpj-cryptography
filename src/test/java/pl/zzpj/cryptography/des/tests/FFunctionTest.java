package pl.zzpj.cryptography.des.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.zzpj.cryptography.ZzpjCryptographyApplication;
import pl.zzpj.cryptography.des.algorithm.FFunctionImpl;
import pl.zzpj.cryptography.des.algorithm.interfaces.FFunction;
import pl.zzpj.cryptography.des.algorithm.interfaces.KeyGenerator;
import pl.zzpj.cryptography.des.utils.interfaces.BitJuggler;
import pl.zzpj.cryptography.des.utils.interfaces.MatrixPermutation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ZzpjCryptographyApplication.class)
public class FFunctionTest {
	@Autowired
	private BitJuggler bitJuggler;
	@Autowired
	private MatrixPermutation matrixPermutation;
	
	@Test
	public void shouldPerformFFunction(){
		//given
		byte[] key = {1,2,3,4,5,6,7,8};
		byte[] bytesToCompute = {1,2,3,4};
		byte[][] expectedSubKeys = {{0, 0, 0, 19, 42, -126},
				{0, 0, 0, 16, 35, 7},
				{0, 0, 0, -74, 0, -124},
			    {0, 0, 0, 64, 35, -61},
				{0, 0, 0, 54, -96, 9},
				{0, 0, 0, 98, 21, 66},
				{0, 0, 0, 12, -95, 42},
				{0, 0, 0, 100, 92, 64},
				{0, 0, 0, 74, -104, 64},
				{0, 0, 0, -64, -59, 56},
				{0, 0, 0, 9, 30, 8},
				{0, 0, 0, -40, 80, 48},
				{0, 0, 0, 1, 74, 44},
				{0, 0, 0, -112, 56, -112},
				{0, 0, 0, -95, 2, 53},
				{0, 0, 0, -93, 66, -128}};
		int roundNumber = 1;
		byte[] expected = {22, -11, -41, 96};
		
		KeyGenerator keyGenerator = Mockito.mock(KeyGenerator.class);
		Mockito.when(keyGenerator.generateSubKeys(key)).thenReturn(expectedSubKeys);
		FFunction fFunction = new FFunctionImpl(keyGenerator, bitJuggler, matrixPermutation);
		
		//when
		fFunction.calculateKSubKeys(key);
		byte[] outcome = fFunction.perform(bytesToCompute, roundNumber);
		
		//then
		assertThat(outcome).isEqualTo(expected);
		
	}
}
