package pl.zzpj.cryptography.web.restapi.descontroller.decryptText;

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
import pl.zzpj.cryptography.web.restapi.descontroller.decryptText.steps.GivenDecryptTextEndpoint;
import pl.zzpj.cryptography.web.restapi.descontroller.decryptText.steps.ThenDecryptTextEndpoint;
import pl.zzpj.cryptography.web.restapi.descontroller.shared.WhenStage;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(ZzpjCryptographyApplication.class)
@WebAppConfiguration
public class DecryptTextEndpointTest 
	extends ScenarioTest<GivenDecryptTextEndpoint, WhenStage, ThenDecryptTextEndpoint> {

	 @Autowired
	 @ProvidedScenarioState
	 private WebApplicationContext context;
	 
	 @Test
	 public void should_return_decrypted_text() throws Exception {
		 given().some_valid_key()
		 	.and().some_encrypted_text()
		 	.and().a_request_to_endpoint();
		 when().request_invoked();
		 then().should_return_decrypted_text();
	 }
	 
	 @Test
	 public void should_return_bad_request_for_invalid_key() throws Exception {
		 given().some_invalid_key()
		 	.and().some_encrypted_text()
		 	.and().a_request_to_endpoint();
		 when().request_invoked();
		 then().should_return_bad_request();
	 }
	 
	 @Test
	 public void should_return_bad_request_for_invalid_text_in_request() throws Exception {
		 given().some_valid_key()
		 	.and().some_text_that_is_not_base64_encoded()
		 	.and().a_request_to_endpoint();
		 when().request_invoked();
		 then().should_return_bad_request();
	 }
}
