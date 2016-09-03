package pl.zzpj.cryptography.des.utils.interfaces;

public interface ArrayUtils {

	/**
	 * Rozszerza rozmiar tablicy powiekszając ją do podanej długości. Nowe
	 * komórki uzupełniane za zerami.
	 * @param array tablica którą chcemy poszerzyć
	 * @param newLength długość o jaką chcemy poszerzyć tablicę
	 * @return poszerzona tablica.
	 */
	public byte[] extendArraySize(byte[] array, int newLength);

	/**
	 * Przekształca dwuwymiarową tablicę w tablicę jednowymiarową.
	 * @param source źródłowa tablica
	 * @return Tablica jedno wymiarowa.
	 */
	public byte[] transformBlocksToArray(byte[][] source);
	
	/**
	 * Przekształca jednowymiarową tablicę bajtów na tablicę dwuwymiarową
	 * gdzie długość drugiego wymiaru to 8. Ostatnie wolne miejsca usupełnia zerami.
	 * @param source źródłowa tablica bajtów
	 * @return Tablica dwu wymiarowa, gdzie drugi wymiar to 8.
	 */
	public byte[][] transformArrayToBlocks(byte[] source);
	
}
