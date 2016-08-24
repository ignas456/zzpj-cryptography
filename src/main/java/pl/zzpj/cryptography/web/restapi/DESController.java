package pl.zzpj.cryptography.web.restapi;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import pl.zzpj.cryptography.des.exceptions.InvalidKeyException;
import pl.zzpj.cryptography.interfaces.IDes;

@RestController
@RequestMapping("/api/des")
public class DESController {
	
	private final IDes des;
	
	@Autowired
	public DESController(IDes des) {
		this.des = des;
	}
	
	@RequestMapping(path = "/encrypt/file", method = RequestMethod.POST, consumes={"multipart/form-data"})
	public @ResponseBody HttpEntity encryptFile(@RequestParam(value="file") MultipartFile file, @RequestParam("key") String key) {
		System.out.println(file.isEmpty());
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping(path = "/encrypt/text", method = RequestMethod.POST)
	public @ResponseBody HttpEntity encryptText(@RequestParam("text") String text, @RequestParam("key") String key) {
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping(path = "/decrypt/file", method = RequestMethod.POST)
	public @ResponseBody HttpEntity decryptFile(@RequestParam("file") MultipartFile file, @RequestParam("key") String key) {
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping(path = "/decrypt/text", method = RequestMethod.POST)
	public @ResponseBody HttpEntity decryptText(@RequestParam("text") String text, @RequestParam("key") String key) {
		return new ResponseEntity(HttpStatus.OK);
	}

}
