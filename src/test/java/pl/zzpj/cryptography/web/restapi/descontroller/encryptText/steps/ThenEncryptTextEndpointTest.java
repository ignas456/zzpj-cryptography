package pl.zzpj.cryptography.web.restapi.descontroller.encryptText.steps;

import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

public class ThenEncryptTextEndpointTest 
	extends Stage<ThenEncryptTextEndpointTest> {
	
	@ExpectedScenarioState
	private ResultActions result;
	private String expectedContent = "KTGv5UEIRHg2vdZn8eZOrA==";
	
	public ThenEncryptTextEndpointTest should_return_encrypted_text_in_base64_format() throws Exception {
		result
			.andExpect(status().isOk())
			.andExpect(content().bytes(expectedContent.getBytes()));
		return this;
	}
}
