package pl.zzpj.cryptography.web.restapi.descontroller.decryptFile.steps;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import pl.zzpj.cryptography.des.exceptions.InvalidKeyException;
import pl.zzpj.cryptography.interfaces.IDes;

public class GivenDecryptFileEndpointTest
	extends Stage<GivenDecryptFileEndpointTest> {
	
	@ExpectedScenarioState
	private IDes des;
	@ProvidedScenarioState
	private MockHttpServletRequestBuilder request;
	@ProvidedScenarioState
	private byte[] expectedContent;
	
	private String key;
	private MockMultipartFile file;
	
	public GivenDecryptFileEndpointTest some_key() {
		key = "ABCD1234";
		return this;
	}
	
	public GivenDecryptFileEndpointTest some_encrypted_file() throws InvalidKeyException {
		des.setKey(key.getBytes());
		byte[] fileContent = des.encrypt(expectedContent);
		file = new MockMultipartFile("file", "orig", null, fileContent);
		return this;
	}
	
	public GivenDecryptFileEndpointTest a_request_to_endpoint() {
		request = fileUpload("/api/des/decrypt/file")
				.file(file)
				.param("key", key);
		return this;
	}
	
	public GivenDecryptFileEndpointTest some_expected_data() {
		expectedContent = "some_content".getBytes();
		return this;
	}
}
