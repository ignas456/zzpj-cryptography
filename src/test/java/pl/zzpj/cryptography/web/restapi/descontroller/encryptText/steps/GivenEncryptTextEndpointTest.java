package pl.zzpj.cryptography.web.restapi.descontroller.encryptText.steps;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.springframework.http.MediaType;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import net.minidev.json.JSONObject;

public class GivenEncryptTextEndpointTest 
	extends Stage<GivenEncryptTextEndpointTest> {
	
	@ProvidedScenarioState
	private String key;
	@ProvidedScenarioState
	private String text;
	@ProvidedScenarioState
	private MockHttpServletRequestBuilder request;
	
	public GivenEncryptTextEndpointTest some_key() {
		this.key = "ABCD1234";
		return this;
	}
	
	public GivenEncryptTextEndpointTest some_text() {
		this.text = "some_text";
		return this;
	}
	
	public GivenEncryptTextEndpointTest a_request_to_endpoint() {
		JSONObject json = new JSONObject();
		json.put("key", key);
		json.put("text", text);
		
		request = post("/api/des/encrypt/text")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toJSONString());
		return this;
	}
}
