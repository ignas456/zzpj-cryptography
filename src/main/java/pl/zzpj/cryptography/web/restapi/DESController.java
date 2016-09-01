package pl.zzpj.cryptography.web.restapi;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import pl.zzpj.cryptography.des.exceptions.InvalidKeyException;
import pl.zzpj.cryptography.interfaces.IDes;
import pl.zzpj.cryptography.web.restapi.models.TextEncryptionParams;
import pl.zzpj.cryptography.web.restapi.validators.TextEncryptionParamsValidator;

@RestController
@RequestMapping("/api/des")
public class DESController {
	
	private final static Charset defaultCharset = StandardCharsets.UTF_8;
	private final IDes des;
	
	@Autowired
	public DESController(IDes des) {
		this.des = des;
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
	    binder.setValidator(new TextEncryptionParamsValidator());
	}
	
	@RequestMapping(path = "/encrypt/text", method = RequestMethod.POST)
	public HttpEntity<String> encryptText(@RequestBody TextEncryptionParams params) throws InvalidKeyException {
		String key = params. getKey();
		String text = params.getText();
		
		des.setKey(key.getBytes(defaultCharset));
		byte[] plainData = text.getBytes(defaultCharset);
		byte[] encryptedData = des.encrypt(plainData);
		String encryptedText = Base64Utils.encodeToString(encryptedData);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		return new ResponseEntity<String>(encryptedText, headers, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/decrypt/text", method = RequestMethod.POST)
	public HttpEntity<String> decryptText(@Valid @RequestBody TextEncryptionParams params) throws InvalidKeyException {
		String key = params. getKey();
		String text = params.getText();
		
		des.setKey(key.getBytes(defaultCharset));
		byte[] encryptedData = Base64Utils.decodeFromString(text);
		byte[] decryptedData = des.decrypt(encryptedData);
		String decryptedText = new String(decryptedData, defaultCharset);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		return new ResponseEntity<String>(decryptedText, headers, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/encrypt/file", method = RequestMethod.POST)
	public HttpEntity<byte[]> encryptFile(@RequestParam("file") MultipartFile file, @RequestParam("key") String key) throws InvalidKeyException, IOException {
		des.setKey(key.getBytes(defaultCharset));
		byte[] encryptedData = des.encrypt(file.getBytes());
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", file.getContentType());
		return new ResponseEntity<byte[]>(encryptedData, headers, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/decrypt/file", method = RequestMethod.POST)
	public HttpEntity<byte[]> decryptFile(@RequestParam("file") MultipartFile file, @RequestParam("key") String key) throws InvalidKeyException, IOException {
		des.setKey(key.getBytes(defaultCharset));
		byte[] decryptedData = des.decrypt(file.getBytes());
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", file.getContentType());
		return new ResponseEntity<byte[]>(decryptedData, headers, HttpStatus.OK);
	}
}


