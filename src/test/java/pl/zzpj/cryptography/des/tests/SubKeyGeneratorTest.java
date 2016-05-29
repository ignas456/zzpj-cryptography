package pl.zzpj.cryptography.des.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import pl.zzpj.cryptography.des.algorithm.SubKeyGenerator;

public class SubKeyGeneratorTest {
	
	@Test
	public void shouldGenerateSubKeysForGivenKey(){
		//given
		byte[] key = {1, 2, 3, 4, 5, 6, 7, 8};
		SubKeyGenerator sut = new SubKeyGenerator(key);
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
		
		//when
		byte[][] subKeys =  sut.generateSubKeys();
		
		//then
		assertThat(subKeys).isEqualTo(expectedSubKeys);
		
	}
}
