package krypto.DES.utils;

public final class BitJuggler {

  /**
   * Wykonuje operacje XOR na dwoch podancy tablicach bajtow.
   * @param first Pierwsza wejsciowa tablica bajtow.
   * @param second Druga wejsciowa tablica bajtow.
   * @return Tablica bajtow.
   */
  public static byte[] xorArrays(byte[] first, byte[] second) {
    byte[] output = new byte[first.length];
    for (int i = 0; i < first.length; i++) {
      output[i] =  (byte) (first[i] ^ second[i]);
    }
    
    return output;
  }
  
  /**
   * Zwraca wartosc bitu na podanej pozycji w tablicy bajtow.
   * Bity sa liczone od lewej do prawej od 0.
   * @param input Wejsciowa tablica bajtow.
   * @param position Pozycja bita do odczytu.
   * @return Wartosc bitu na podanej pozycji.
   */
  public static int getBit(byte[] input, int position) {
    int bytePosition = position / 8;
    int bitPositionInByte = position % 8;
    
    byte tmp = input[bytePosition];
    int bit = tmp >> (8 - (bitPositionInByte + 1)) & 0x0001;
    
    return bit;
  }
  
  /**
   * Wycina okreslone bity z podanej tablicy bajtow.
   * @param input Wejsciowa tablica bajtow.
   * @param position Pozycja od ktorej chcemy zaczac wycinac bity (wlacznie).
   * @param n Ilosc bitow ktore chcemy wyciac.
   * @return Tablica bajow zawierajaca wyciete bity. Wynikiem jest podane n bitow od lewej.
   */
  public static byte[] getBits(byte[] input, int position, int n) {
    int bytesAmount = ((n - 1) / 8) + 1;
    byte[] output = new byte[bytesAmount];
    
    for (int i = 0; i < n; i++) {
      int value = getBit(input, position + i);
      setBit(output, i, value);
    }
    
    return output;
  }
  
  /**
   * Ustawia bit na podanej pozycji na podana wartosc.
   * Numer bitu liczony jest od lewej do prawej zaczynajac od 0.
   * Wartosc bitu to: 1 dla true, 0 dla false.
   * @param input Wejsciowa tablica bajtow.
   * @param position Pozycja bitu do ustawienia.
   * @param value Wartosc na ktora chcemy ustawic bit.
   */
  public static void setBit(byte[] input, int position, int value) {
    int bytePosition = (position) / 8;
    byte tmp = input[bytePosition];
    
    if (1 == value) {
      tmp |= 1 << (8 - ((position % 8) + 1));
    } else {
      tmp &= ~(1 << (8 - ((position % 8) + 1)));
    }
    
    input[bytePosition] = tmp;
  }
  
  /**
   * Ustawia okreslone bity tablicy wejsciowej na bity przeslane w tablicy pomocniczej. 
   * @param input Wejsciowa tablica bitow.
   * @param startPosition Pozycja od ktorej ma sie zaczac ustawianie bitow (wlacznie). Numeracja od 0.
   * @param bits Bity ktore maja zostac wprowadzone do tablicy wejsciowej.
   * @param startPosition2 Pozycja od ktorej maja byc czytane bity (wlacznie). Numeracja od 0.
   * @param length Ilosc bitow z tablicy bits ktore maja zostac urzyte.
   */
  public static void setBits(byte[] input, int startPosition, byte[] bits, int startPosition2, int length) {
    for (int i = startPosition, j = startPosition2; i < startPosition + length; i++, j++) {
      setBit(input, i, getBit(bits, j));
    }
  }
  
  /**
   * Przesowa bity w lewa strone.
   * @param input Wejsciowa tablica bajtow.
   * @param len Ilosc bitow do przesuniecia.
   * @param pas Wiekowsc przesuniecia.
   * @return Tablica bajtow z przesunietymi bitami.
   */
  public static byte[] rotLeft(byte[] input, int len, int pas) {
    int nrBytes = (len - 1) / 8 + 1;
    byte[] out = new byte[nrBytes];
    for (int i = 0; i < len; i++) {
         int val = getBit(input, (i + pas) % len);
         setBit(out, i, val);
    }
    return out;
  }
  
  /**
   * Rozdziela podany ciag bitow na pomniejsze ciagi. Ilosc bitow musi sie dac
   * podzilic bez reszty przez parametr lenght.
   * @param input Wejsciowy ciag bitow.
   * @param lenght Ilosc bitow w kazdym podciagu. 
   * @return Tablica bajtow gdzie kazdy Bajt zawiera jeden podciag.
   */
  public static byte[] separateBytes(byte[] input, int lenght) {
    int numOfBytes = (8 * input.length - 1) / lenght + 1;
    byte[] out = new byte[numOfBytes];
    for (int i = 0; i < numOfBytes; i++) {
         for (int j = 0; j < lenght; j++) {
              int val = getBit(input, lenght * i + j);
              setBit(out, 8 * i + j, val);
         }
    }
    return out;
  }
  
  /**
   * Laczy dwa ciagi bitow w jeden czytany od lewej do prawej. 
   * @param first Pierwszy ciag.
   * @param firstLenght Ilosc bitow z pierwszego ciagu liczona od pozycji 0.
   * @param second drugi ciag.
   * @param secondLenght Ilosc bitow z drugiego ciagu liczona od pozycji 0.
   * @return Tablica bajtow zawierajacy polaczony ciag bitow.
   */
  public static byte[] concatBits(byte[] first, int firstLenght, byte[] second, int secondLenght) {
    int numOfBytes = (firstLenght + secondLenght - 1) / 8 + 1;
    byte[] output = new byte[numOfBytes];
    int j = 0;

    for (int i = 0; i < firstLenght; i++) {
         int val = getBit(first, i);
         setBit(output, j, val);
         j++;
    }
    for (int i = 0; i < secondLenght; i++) {
      int val = getBit(second, i);
      setBit(output, j, val);
      j++;
    }
     return output;
  }
  
  /**
   * Zwraca ciag bitow w postaci lancucha znakowego.
   * @param input Wejsciowa tablica bitow.
   * @return Ciag bitow w postaci lancucha znakowego.
   */
  public static String toString(byte[] input) {
    StringBuilder sb = new StringBuilder();
    
    for (int i = 0; i < input.length * 8; i++) {
      if (i%8 == 0) sb.append(" ");
      sb.append(getBit(input, i));
    }
    
    return sb.toString();
  }
  
  private BitJuggler() {
  }
}
