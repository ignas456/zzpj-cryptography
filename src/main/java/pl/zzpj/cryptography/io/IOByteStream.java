package pl.zzpj.cryptography.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IOByteStream {
  
  public static byte[] readFile(String filename) throws IOException {
    byte[] bytes = Files.readAllBytes(Paths.get(filename));
    return bytes;
  }
  
  public static void writeFile(String filename, byte[] blocks) throws IOException {
    FileOutputStream stream = new FileOutputStream(filename);
    stream.write(blocks);
    stream.close();
  }
}
