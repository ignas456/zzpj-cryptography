package pl.zzpj.cryptography.web.restapi.descontroller.decryptText.steps;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import net.minidev.json.JSONObject;

public class GivenDecryptTextEndpoint 
	extends Stage<GivenDecryptTextEndpoint> {
	
	private String encryptedText;
	private String key;
	@ProvidedScenarioState
	private MockHttpServletRequestBuilder request;
	
	public GivenDecryptTextEndpoint some_encrypted_text() {
		this.encryptedText = "KTGv5UEIRHg2vdZn8eZOrA==";
		return this;
	}
	
	public GivenDecryptTextEndpoint some_text_that_is_not_base64_encoded() {
		this.encryptedText = "Jakiś@Test$";
		return this;
	}
	
	public GivenDecryptTextEndpoint some_valid_key() {
		this.key = "4142434431323334";
		return this;
	}
	
	public GivenDecryptTextEndpoint some_invalid_key() {
		this.key = "ABCD12";
		return this;
	}
	
	public GivenDecryptTextEndpoint a_request_to_endpoint() {
		JSONObject json = new JSONObject();
		json.put("key", key);
		json.put("text", encryptedText);
		
		request = post("/api/des/decrypt/text")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toJSONString());
		return this;
	}
}
