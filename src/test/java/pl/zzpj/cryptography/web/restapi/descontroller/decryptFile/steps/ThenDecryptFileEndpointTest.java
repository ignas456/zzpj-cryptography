package pl.zzpj.cryptography.web.restapi.descontroller.decryptFile.steps;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.ResultActions;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

public class ThenDecryptFileEndpointTest
	extends Stage<ThenDecryptFileEndpointTest>{

	@ExpectedScenarioState
	private ResultActions result;
	@ExpectedScenarioState
	private byte[] expectedContent;
	
	public ThenDecryptFileEndpointTest should_return_decrypted_file() throws Exception {
		result
			.andExpect(status().isOk())
			.andExpect(content().bytes(expectedContent));
		return this;
	}
}
