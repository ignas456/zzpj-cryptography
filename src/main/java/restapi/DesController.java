package restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.zzpj.cryptography.interfaces.IDes;

@RestController("/api/des")
public class DesController {
	private IDes des;
	
	@Autowired
	public DesController(IDes des) {
		this.des = des;
	}
	
	@RequestMapping("/encrypt/{text}")
	public HttpEntity<String> encryptText(@RequestParam("text") String text) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
