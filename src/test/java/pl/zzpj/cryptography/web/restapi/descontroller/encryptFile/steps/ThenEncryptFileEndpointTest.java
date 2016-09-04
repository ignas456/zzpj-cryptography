package pl.zzpj.cryptography.web.restapi.descontroller.encryptFile.steps;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.ResultActions;

import com.tngtech.jgiven.annotation.ExpectedScenarioState;

public class ThenEncryptFileEndpointTest {
	
	@ExpectedScenarioState
	private ResultActions result;
	@ExpectedScenarioState
	private byte[] expectedContent;
	
	public ThenEncryptFileEndpointTest should_return_encrypted_file_in() throws Exception {
		result
			.andExpect(status().isOk())
			.andExpect(content().bytes(expectedContent));
		return this;
	}
}
