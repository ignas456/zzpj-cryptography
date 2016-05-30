package pl.zzpj.cryptography.io.tests;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.zzpj.cryptography.io.IOByteStream;

public class IOByteStreamTest {
	
	private final static String FILE_NAME = "test";
	private final static String EXAMPLE_MSG = "Ala ma kota";

	@BeforeClass
	public static void beforeAllTests() throws IOException {
		FileOutputStream stream = new FileOutputStream(FILE_NAME);
		stream.write(new String(EXAMPLE_MSG).getBytes());
	    stream.close();
	}
	
	@Test
	public void shouldReadExistingFile() throws IOException {
		byte[] result = IOByteStream.readFile(FILE_NAME);
		String message = new String(result);
		
		assertThat(message).isEqualTo(EXAMPLE_MSG);
	}
	
	@Test(expected=IOException.class)
	public void readFileShouldThrowIOExceptionForNotFoundFile() throws IOException {
		IOByteStream.readFile("xyzxyz");
	}
	
	@Test
	public void shouldWriteFile() throws IOException {
		String message = new String();
		IOByteStream.writeFile("example", message.getBytes());
		
		assertThat(Files.exists(Paths.get("example"), LinkOption.NOFOLLOW_LINKS)).isEqualTo(true);
	}
	
	@AfterClass
	public static void afterAllTests() throws IOException {
		Files.delete(new File(FILE_NAME).toPath());
		Files.delete(Paths.get("example"));
	}

}
