package pl.zzpj.cryptography.des.utils;

public final class BitJuggler {

	/**
	 * Wykonuje operacje XOR na dwóch podanch tablicach bajtów.
	 * Jeżeli tablice są różnych rozmiarów, wynikiem jest tablica długości
	 * takiej jak tablica wejściowa o większej długości.
	 * @param src1 pierwsza wejściowa tablica bajtów.
	 * @param src2 druga wejściowa tablica bajtów.
	 * @return tablica bajtów zawierająca wynik operacji XOR na tablicach wejściowych.
	 */
	public static byte[] xorArrays(byte[] src1, byte[] src2) {
		if (src1 == null || src2 == null)
			throw new IllegalArgumentException("Source array is null");
		
		if (src1.length > src2.length) 
			src2 = extendArraySize(src2, src1.length);
		
		if (src1.length < src2.length) 
			src1 = extendArraySize(src1, src2.length);
		
		byte[] result = new byte[src1.length];
		for (int i = 0; i < src1.length; i++) {
			result[i] = (byte) (src1[i] ^ src2[i]);
		}

		return result;
	}

	/**
	 * Rozszerza rozmiar tablicy powiekszając ją do podanej długości.
	 * Nowe komórki uzupełniane za zerami.
	 * @param array tablica którą chcemy poszerzyć
	 * @param newLength długość o jaką chcemy poszerzyć tablicę
	 * @return poszerzona tablica.
	 */
	public static byte[] extendArraySize(byte[] array, int newLength) {
		if (array == null)
			throw new IllegalArgumentException("Source array is null");
		
		byte[] result = new byte[newLength];
		System.arraycopy(array, 0, result, 0, array.length);
		
		return result;
	}
	
	/**
	 * Zwraca wartość bitu na podanej pozycji w całej tablicy bajtów.
	 * Bity są liczone od lewej do prawej od indeksu 0.
	 * @param source źródło bajtów.
	 * @param position pozycja pożądanego bitu.
	 * @return wartosc bitu w postaci wartości całkowitej.
	 */
	public static int getBit(byte[] source, int position) {
		if (source == null) 
			throw new IllegalArgumentException("Source array is null");
		
		int bytePosition = position / 8;
		int bitPositionInByte = position % 8;

		byte trackedByte = source[bytePosition];
		int resultBit = trackedByte >> (8 - (bitPositionInByte + 1)) & 0x0001;

		return resultBit;
	}

	/**
	 * Pobiera określone bity z podanej tablicy bajtów.
	 * @param source źródło bajtów.
	 * @param startPosition pozycja od której ma zostać rozpoczęte pobieranie (włacznie).
	 * @param bitsNumber ilosc bitów do pobrania.
	 * @return Tablica bajów zawierajaca pobrane bity. Wynikiem jest podana ilość
	 *         bitów od lewej z uzupełnieniem do wielokrotności liczby 8 zerami.
	 */
	public static byte[] getBits(byte[] source, int startPosition, int bitsNumber) {
		if (source == null) 
			throw new IllegalArgumentException("Source array is null");
		
		int newByteArraySize = ((bitsNumber - 1) / 8) + 1;
		byte[] result = new byte[newByteArraySize];

		for (int i = 0; i < bitsNumber; i++) {
			int selectedBit = getBit(source, startPosition + i);
			setBit(result, i, selectedBit);
		}

		return result;
	}

	/**
	 * Ustawia bit na podanej pozycji na podana wartość. Numer bitu liczony jest
	 * od lewej do prawej zaczynając od 0.
	 * @param destination źródłowa tablica bajtów.
	 * @param position pozycja bitu do ustawienia.
	 * @param value wartość na ktora chcemy ustawic bit (1 lub 0).
	 */
	public static void setBit(byte[] destination, int position, int value) {
		if (destination == null)
			throw new IllegalArgumentException("Source array is null");
		
		if (value != 0 && value != 1)
			throw new IllegalArgumentException("value is not 1 or 0");
		
		int bytePosition = (position) / 8;
		byte changedByte = destination[bytePosition];

		if (1 == value) {
			changedByte |= 1 << (8 - ((position % 8) + 1));
		} else {
			changedByte &= ~(1 << (8 - ((position % 8) + 1)));
		}

		destination[bytePosition] = changedByte;
	}

	/**
	 * Ustawia określone bity przesłanej tablicy na bity przesłane w tablicy pomocniczej.
	 * @param destination tablica bitów w której zmieniamy bity.
	 * @param destStartPosition pozycja (wlacznie) od której ma się zacząć ustawianie bitów. Numeracja od 0.
	 * @param source bity które mają zostać wprowadzone.
	 * @param srcStartPosition pozycja (wlacznie) od której mają być czytane bity. Numeracja od 0.
	 * @param length ilość bitów z tablicy źródłowej, które mają zostać przepisane.
	 */
	public static void setBits(byte[] destination, int destStartPosition, byte[] source, int srcStartPosition, int length) {
		if (destination == null || source == null) 
			throw new IllegalArgumentException("array is null");
		
		if (destStartPosition < 0 || srcStartPosition < 0)
			throw new IllegalArgumentException("Start position is negative number");
		
		for (int i = destStartPosition, j = srcStartPosition; i < destStartPosition + length; i++, j++) {
			setBit(destination, i, getBit(source, j));
		}
	}

	/**
	 * Rotuje wybrane bity z tablicy bajtów w lewą stronę, a natępnie umieszcza
	 * je w nowej tablicy bajtów z uzupełnieniem zerami. 
	 * @param source źródłowa tablica bajtow.
	 * @param bitsNumber ilość bitów do rotowania liczona od lewej do prawej.
	 * @param step wielkość kroku.
	 * @return Tablica bajtów z wynikiem rotacji.
	 */
	public static byte[] rotateSelectedBitsLeft(byte[] source, int bitsNumber, int step) {
		int bytesNumber = (bitsNumber - 1) / 8 + 1;
		byte[] result = new byte[bytesNumber];
		for (int i = 0; i < bitsNumber; i++) {
			int selectedBit = getBit(source, (i + step) % bitsNumber);
			setBit(result, i, selectedBit);
		}
		return result;
	}

	/**
	 * Rozdziela podany ciąg bitów na mniejsze podciągi. 
	 * Ilosc wszystkich bitów musi sie dać podzilić bez reszty przez parametr lenght.
	 * @param source źródłowa tablica bajtów.
	 * @param lenght ilosc bitów w każdym podciagu.
	 * @return Tablica bajtów gdzie każdy bajt zawiera jeden podciag o podanej długości.
	 */
	public static byte[] separateBits(byte[] source, int lenght) {
		if (source == null)
			throw new IllegalArgumentException("source array is null");
		
		if (((source.length * 8) % lenght != 0) && lenght < 0 && lenght > 8)
			throw new IllegalArgumentException("wrong lenght");
		
		int newByteArraySize = (8 * source.length - 1) / lenght + 1;
		byte[] result = new byte[newByteArraySize];
		for (int i = 0; i < newByteArraySize; i++) {
			for (int j = 0; j < lenght; j++) {
				int selectedBit = getBit(source, lenght * i + j);
				setBit(result, 8 * i + j, selectedBit);
			}
		}
		return result;
	}

	/**
	 * Łączy dwa ciagi bitów w jeden czytany od lewej do prawej.
	 * @param firstSeries pierwszy ciąg bitów.
	 * @param firstLenght ilość bitów z pierwszego ciągu liczona od 0.
	 * @param secondSeries drugi ciąg bitów.
	 * @param secondLenght ilość bitów z drugiego ciągu liczona od 0.
	 * @return Tablica bajtów zawierająca połączony ciąg bitów.
	 */
	public static byte[] concatBitSeries(byte[] firstSeries, int firstLenght, byte[] secondSeries, int secondLenght) {
		int newByteArraySize = (firstLenght + secondLenght - 1) / 8 + 1;
		byte[] result = new byte[newByteArraySize];
		int j = 0;

		for (int i = 0; i < firstLenght; i++) {
			int selectedBit = getBit(firstSeries, i);
			setBit(result, j, selectedBit);
			j++;
		}
		for (int i = 0; i < secondLenght; i++) {
			int selectedBit = getBit(secondSeries, i);
			setBit(result, j, selectedBit);
			j++;
		}
		
		return result;
	}

	private BitJuggler() { }
	
}
