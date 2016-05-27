package pl.zzpj.cryptography.des.utils;

public final class BitPrinter {

	/**
	 * Zwraca bitową reprezentację tablicy bajtów jako łańcuch znakowy.
	 * @param source źródłowa tablica bajtów.
	 * @return Łancuch znakowy reprezentujący ciąg bitów.
	 */
	public static String toString(byte[] source) {
		if (source == null)
			throw new IllegalArgumentException("source array is null");
		
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < source.length * 8; i++) {
			if (i % 8 == 0 && i != 0)
				sb.append(" ");
			sb.append(BitJuggler.getBit(source, i));
		}

		return sb.toString();
	}
	
	private BitPrinter() {}
	
}
