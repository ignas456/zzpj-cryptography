package pl.zzpj.cryptography.web.restapi.descontroller.encryptFile;

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
import pl.zzpj.cryptography.interfaces.IDes;
import pl.zzpj.cryptography.web.restapi.descontroller.encryptFile.steps.GivenEncryptFileEndpointTest;
import pl.zzpj.cryptography.web.restapi.descontroller.encryptFile.steps.ThenEncryptFileEndpointTest;
import pl.zzpj.cryptography.web.restapi.descontroller.shared.WhenStage;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(ZzpjCryptographyApplication.class)
@WebAppConfiguration
public class EncryptFileEndpointTest
	extends ScenarioTest<GivenEncryptFileEndpointTest, WhenStage, 
		ThenEncryptFileEndpointTest>{
	
	 @Autowired
	 @ProvidedScenarioState
	 private WebApplicationContext context;
	 
	 @Autowired
	 @ProvidedScenarioState
	 private IDes des;
	 
	 @Test
	 public void should_return_encrypted_file() throws Exception {
		 given().some_file_content()
		 	.and().some_key()
		 	.and().some_file_with_content()
		 	.and().expected_file_content()
		 	.and().a_request_to_endpoint();
		 when().request_invoked();
		 then().should_return_encrypted_file_in();
	 }

}
