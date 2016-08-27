package pl.zzpj.cryptography.des.utils.interfaces;

public interface BitJuggler {

	/**
	 * Wykonuje operacje XOR na dwóch podanch tablicach bajtów.
	 * Jeżeli tablice są różnych rozmiarów, wynikiem jest tablica długości
	 * takiej jak tablica wejściowa o większej długości.
	 * @param src1 pierwsza wejściowa tablica bajtów.
	 * @param src2 druga wejściowa tablica bajtów.
	 * @return tablica bajtów zawierająca wynik operacji XOR na tablicach wejściowych.
	 */
	public byte[] xorArrays(byte[] src1, byte[] src2);

	/**
	 * Zwraca wartość bitu na podanej pozycji w całej tablicy bajtów.
	 * Bity są liczone od lewej do prawej od indeksu 0.
	 * @param source źródło bajtów.
	 * @param position pozycja pożądanego bitu.
	 * @return wartosc bitu w postaci wartości całkowitej.
	 */
	public int getBit(byte[] source, int position);

	/**
	 * Pobiera określone bity z podanej tablicy bajtów.
	 * @param source źródło bajtów.
	 * @param startPosition pozycja od której ma zostać rozpoczęte pobieranie (włacznie).
	 * @param bitsNumber ilosc bitów do pobrania.
	 * @return Tablica bajów zawierajaca pobrane bity. Wynikiem jest podana ilość
	 *         bitów od lewej z uzupełnieniem do wielokrotności liczby 8 zerami.
	 */
	public byte[] getBits(byte[] source, int startPosition, int bitsNumber);

	/**
	 * Ustawia bit na podanej pozycji na podana wartość. Numer bitu liczony jest
	 * od lewej do prawej zaczynając od 0.
	 * @param destination źródłowa tablica bajtów.
	 * @param position pozycja bitu do ustawienia.
	 * @param value wartość na ktora chcemy ustawic bit (1 lub 0).
	 */
	public void setBit(byte[] destination, int position, int value);

	/**
	 * Ustawia określone bity przesłanej tablicy na bity przesłane w tablicy pomocniczej.
	 * @param destination tablica bitów w której zmieniamy bity.
	 * @param destStartPosition pozycja (wlacznie) od której ma się zacząć ustawianie bitów. Numeracja od 0.
	 * @param source bity które mają zostać wprowadzone.
	 * @param srcStartPosition pozycja (wlacznie) od której mają być czytane bity. Numeracja od 0.
	 * @param length ilość bitów z tablicy źródłowej, które mają zostać przepisane.
	 */
	public void setBits(byte[] destination, int destStartPosition, byte[] source, int srcStartPosition, int length);

	/**
	 * Rotuje wybrane bity z tablicy bajtów w lewą stronę, a natępnie umieszcza
	 * je w nowej tablicy bajtów z uzupełnieniem zerami. 
	 * @param source źródłowa tablica bajtow.
	 * @param bitsNumber ilość bitów do rotowania liczona od lewej do prawej.
	 * @param step wielkość kroku.
	 * @return Tablica bajtów z wynikiem rotacji.
	 */
	public byte[] rotateSelectedBitsLeft(byte[] source, int bitsNumber, int step);

	/**
	 * Rozdziela podany ciąg bitów na mniejsze podciągi. 
	 * Ilosc wszystkich bitów musi sie dać podzilić bez reszty przez parametr lenght.
	 * @param source źródłowa tablica bajtów.
	 * @param lenght ilosc bitów w każdym podciagu.
	 * @return Tablica bajtów gdzie każdy bajt zawiera jeden podciag o podanej długości.
	 */
	public byte[] separateBits(byte[] source, int lenght);

	/**
	 * Łączy dwa ciagi bitów w jeden czytany od lewej do prawej.
	 * @param firstSeries pierwszy ciąg bitów.
	 * @param firstLenght ilość bitów z pierwszego ciągu liczona od 0.
	 * @param secondSeries drugi ciąg bitów.
	 * @param secondLenght ilość bitów z drugiego ciągu liczona od 0.
	 * @return Tablica bajtów zawierająca połączony ciąg bitów.
	 */
	public byte[] concatBitSeries(byte[] firstSeries, int firstLenght, byte[] secondSeries, int secondLenght);
	
}
