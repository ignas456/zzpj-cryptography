package pl.zzpj.cryptography.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class BlockFileStream {
  
  /**
   * Puszek1
   * @param filename
   * @return
   * @throws IOException
   */
  public static byte[] readFileBlocks(String filename) throws IOException {
    byte[] bytes = Files.readAllBytes((new File(filename)).toPath());
    return bytes;
  }
  
  /**
   * Puszek2
   * @param filename
   * @param blocks
   * @throws IOException
   */
  public static void writeFileBlocks(String filename, byte[] blocks) throws IOException {
    FileOutputStream stream = new FileOutputStream(filename);
    stream.write(blocks);
    stream.close();
  }
}
