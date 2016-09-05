package pl.zzpj.cryptography.web.restapi.descontroller.encryptFile.steps;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.zzpj.cryptography.des.algorithm.interfaces.Des;
import pl.zzpj.cryptography.des.exceptions.InvalidKeyException;

public class GivenEncryptFileEndpointTest 
	extends Stage<GivenEncryptFileEndpointTest> {
	
	@ExpectedScenarioState
	private Des des;
	@ProvidedScenarioState
	private MockHttpServletRequestBuilder request;
	@ProvidedScenarioState
	private byte[] expectedContent;
	private String fileContent;
	private String key;
	private MockMultipartFile file;
	
	public GivenEncryptFileEndpointTest some_key() {
		key = "ABCD1234";
		return this;
	}
	
	public GivenEncryptFileEndpointTest some_file_with_content() {
		file = new MockMultipartFile("file", "orig", null, fileContent.getBytes());
		return this;
	}
	
	public GivenEncryptFileEndpointTest some_file_content() {
		fileContent = "test_file_content";
		return this;
	}
	
	public GivenEncryptFileEndpointTest expected_file_content() throws InvalidKeyException {
		des.setKey(key.getBytes());
		expectedContent = des.encrypt(fileContent.getBytes());
		return this;
	}
	
	public GivenEncryptFileEndpointTest a_request_to_endpoint() {
		request = fileUpload("/api/des/encrypt/file")
				.file(file)
				.param("key", key);
		return this;
	}
}
