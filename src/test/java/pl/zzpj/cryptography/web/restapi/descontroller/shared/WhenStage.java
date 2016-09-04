package pl.zzpj.cryptography.web.restapi.descontroller.shared;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.BeforeStage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

public class WhenStage extends Stage<WhenStage> {
	
	@ExpectedScenarioState
	private WebApplicationContext context;
	@ExpectedScenarioState
	private MockHttpServletRequestBuilder request;
	@ProvidedScenarioState
	private ResultActions result;
	
	private MockMvc mockMvc;
	
	@BeforeStage
	public void setUp() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.build();
	}
	
	public WhenStage request_invoked() throws Exception {
		result = mockMvc.perform(request);
		return this;
	}
}
