package pl.zzpj.cryptography.web.restapi.descontroller.decryptFile;

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
import pl.zzpj.cryptography.des.algorithm.interfaces.Des;
import pl.zzpj.cryptography.web.restapi.descontroller.decryptFile.steps.GivenDecryptFileEndpointTest;
import pl.zzpj.cryptography.web.restapi.descontroller.decryptFile.steps.ThenDecryptFileEndpointTest;
import pl.zzpj.cryptography.web.restapi.descontroller.shared.WhenStage;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(ZzpjCryptographyApplication.class)
@WebAppConfiguration
public class DecryptFileEndpointTest 
	extends ScenarioTest<GivenDecryptFileEndpointTest, WhenStage,
		ThenDecryptFileEndpointTest> {
	
	 @Autowired
	 @ProvidedScenarioState
	 private WebApplicationContext context;
	 
	 @Autowired
	 @ProvidedScenarioState
	 private Des des;
	 
	 @Test
	 public void should_return_decrypted_file() throws Exception {
		 given().some_key()
		 	.and().some_expected_data()
		 	.and().some_encrypted_file()
		 	.and().a_request_to_endpoint();
		 when().request_invoked();
		 then().should_return_decrypted_file();
	 }
}
