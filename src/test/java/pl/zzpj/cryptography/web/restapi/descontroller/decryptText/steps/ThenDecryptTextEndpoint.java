package pl.zzpj.cryptography.web.restapi.descontroller.decryptText.steps;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.ResultActions;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

public class ThenDecryptTextEndpoint 
	extends Stage<ThenDecryptTextEndpoint> {
	
	@ExpectedScenarioState
	private ResultActions result;
	private String expectedContent = "some_text";
	
	public ThenDecryptTextEndpoint should_return_decrypted_text() throws Exception {
		result
			.andExpect(status().isOk())
			.andExpect(content().bytes(expectedContent.getBytes()));
		return this;
	}
	
	public ThenDecryptTextEndpoint should_return_bad_request() throws Exception {
		result
			.andExpect(status().isBadRequest());
		return this;
	}
}
