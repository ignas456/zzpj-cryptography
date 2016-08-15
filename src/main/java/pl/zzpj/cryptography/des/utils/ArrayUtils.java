package pl.zzpj.cryptography.des.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ArrayUtils {

	private final static int BLOCK_LENGTH = 8;
	
	/**
	 * Rozszerza rozmiar tablicy powiekszając ją do podanej długości. Nowe
	 * komórki uzupełniane za zerami.
	 * @param array tablica którą chcemy poszerzyć
	 * @param newLength długość o jaką chcemy poszerzyć tablicę
	 * @return poszerzona tablica.
	 */
	public static byte[] extendArraySize(byte[] array, int newLength) {
		if (array == null)
			throw new IllegalArgumentException("Source array is null");

		if (newLength < 0)
			throw new IllegalArgumentException("newLength is negative number");

		byte[] result = new byte[newLength];
		System.arraycopy(array, 0, result, 0, array.length);

		return result;
	}

	/**
	 * Przekształca dwuwymiarową tablicę w tablicę jednowymiarową.
	 * @param source źródłowa tablica
	 * @return Tablica jedno wymiarowa.
	 */
	public static byte[] transformToOneDimensionArray(byte[][] source) {
		if (source == null) 
			throw new IllegalArgumentException("source is null");
		
		List<Byte> byteList = change2DByteArrayToByteList(source);

		return prefromByteListUnboxingToByteArray(byteList);
	}
	
	/**
	 * Przekształca jednowymiarową tablicę bajtów na tablicę dwuwymiarową
	 * gdzie długość drugiego wymiaru to 8. Ostatnie wolne miejsca usupełnia zerami.
	 * @param source źródłowa tablica bajtów
	 * @return Tablica dwu wymiarowa, gdzie drugi wymiar to 8.
	 */
	public static byte[][] transformToTwoDimensionsArray(byte[] source) {
		if (source == null) 
			throw new IllegalArgumentException("source is null");
		
		byte[][] result = new byte[(source.length - 1) / BLOCK_LENGTH + 1][BLOCK_LENGTH];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = Arrays.copyOfRange(source, i * BLOCK_LENGTH, (i + 1) * BLOCK_LENGTH);
		}
		
		return result;
	}

	private ArrayUtils() { }
	
	private static List<Byte> change2DByteArrayToByteList(byte[][] source) {
		List<Byte> result = new ArrayList<Byte>();

		for (byte[] byteArray : source) {
			for (byte b : byteArray) {
				result.add(b);
			}
		}
		
		return result;
	}
	
	private static byte[] prefromByteListUnboxingToByteArray(List<Byte> source) {
		byte[] result = new byte[source.size()];

		for (int i = 0; i < result.length; i++) {
			result[i] = source.get(i);
		}
		
		return result;
	}
}
