package pl.zzpj.cryptography.des.utils.interfaces;

public interface MatrixPermutation {

	/**
	 * Metoda przeprowadza permutacje lewej macierzy z prawą macierzą
	 * @param input macierz, które jest permutowana
	 * @param permutationTable macierz permutująca
	 * @return zpermutowana macierz
	 */
	public byte[] permute(byte[] input, byte[] permutationTable);
	
}
