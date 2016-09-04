package pl.zzpj.cryptography.web.restapi.descontroller.encryptText;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.junit.ScenarioTest;

import pl.zzpj.cryptography.ZzpjCryptographyApplication;
import pl.zzpj.cryptography.web.restapi.descontroller.encryptText.steps.GivenEncryptTextEndpointTest;
import pl.zzpj.cryptography.web.restapi.descontroller.encryptText.steps.ThenEncryptTextEndpointTest;
import pl.zzpj.cryptography.web.restapi.descontroller.shared.WhenStage;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(ZzpjCryptographyApplication.class)
@WebAppConfiguration
public class EncryptTextEndpointTest 
	extends ScenarioTest<GivenEncryptTextEndpointTest, WhenStage, ThenEncryptTextEndpointTest> {
	
	 @Autowired
	 @ProvidedScenarioState
	 private WebApplicationContext context;
	 
	 @Test
	 public void should_return_encrypted_text_when_request_is_invoked() throws Exception {
		 given().some_key()
		 	.and().some_text()
		 	.and().a_request_to_endpoint();
		 when().request_invoked();
		 then().should_return_encrypted_text_in_base64_format();
	 }
}
