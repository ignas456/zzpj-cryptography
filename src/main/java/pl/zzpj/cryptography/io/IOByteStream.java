package pl.zzpj.cryptography.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IOByteStream {
  
  /**
   * Odczytuje zawartość pliku
   * @param filename nazwa pliku
   * @return Strumień bajtów będący zawartościa pliku
   * @throws IOException
   */
  public static byte[] readFile(String filename) throws IOException {
    byte[] bytes = Files.readAllBytes(Paths.get(filename));
    return bytes;
  }
  
  /**
   * Zapisuje strumień bajtów do pliku
   * @param filename nazwa pliku
   * @param blocks strumień bajtów do zapisania
   * @throws IOException
   */
  public static void writeFile(String filename, byte[] blocks) throws IOException {
    FileOutputStream stream = new FileOutputStream(filename);
    stream.write(blocks);
    stream.close();
  }
}
