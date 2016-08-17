package pl.zzpj.cryptography.web.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.zzpj.cryptography.interfaces.IDes;

@RestController
@RequestMapping("/api/des")
public class DESController {
	
	private final IDes des;
	
	@Autowired
	public DESController(IDes des) {
		this.des = des;
	}
	
	@RequestMapping("/encrypt")
	public HttpEntity encryptText() {
		return new ResponseEntity(HttpStatus.OK);
	}

}
