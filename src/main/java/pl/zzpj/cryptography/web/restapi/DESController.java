package pl.zzpj.cryptography.web.restapi;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import pl.zzpj.cryptography.des.algorithm.interfaces.Des;
import pl.zzpj.cryptography.des.exceptions.InvalidKeyException;
import pl.zzpj.cryptography.web.restapi.models.TextEncryptionParams;
import pl.zzpj.cryptography.web.restapi.utils.HttpResponseBuilder;
import pl.zzpj.cryptography.web.restapi.validators.TextEncryptionParamsValidator;

@RestController
@RequestMapping("/api/des")
public class DESController {
	
	private final static Charset defaultCharset = StandardCharsets.UTF_8;
	private final Des des;
	private final HttpResponseBuilder responseBuilder;
	
	@Autowired
	public DESController(Des des, HttpResponseBuilder responseBuilder) {
		this.des = des;
		this.responseBuilder = responseBuilder;
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
		
		return responseBuilder.buildTextResponse(encryptedText);
	}
	
	@RequestMapping(path = "/decrypt/text", method = RequestMethod.POST)
	public HttpEntity<String> decryptText(@Valid @RequestBody TextEncryptionParams params) throws InvalidKeyException {
		
		String key = params. getKey();
		String text = params.getText();
		
		des.setKey(key.getBytes(defaultCharset));
		byte[] encryptedData = Base64Utils.decodeFromString(text);
		byte[] decryptedData = des.decrypt(encryptedData);
		String decryptedText = new String(decryptedData, defaultCharset);
		
		return responseBuilder.buildTextResponse(decryptedText);
	}

	@RequestMapping(path = "/encrypt/file", method = RequestMethod.POST)
	public HttpEntity<byte[]> encryptFile(@RequestParam("file") MultipartFile file, @RequestParam("key") String key) throws InvalidKeyException, IOException {
		
		des.setKey(key.getBytes(defaultCharset));
		byte[] encryptedData = des.encrypt(file.getBytes());
		
		return responseBuilder.buildFileResponse(encryptedData, file.getContentType());
	}
	
	@RequestMapping(path = "/decrypt/file", method = RequestMethod.POST)
	public HttpEntity<byte[]> decryptFile(@RequestParam("file") MultipartFile file, @RequestParam("key") String key) throws InvalidKeyException, IOException {
		
		des.setKey(key.getBytes(defaultCharset));
		byte[] decryptedData = des.decrypt(file.getBytes());
		
		return responseBuilder.buildFileResponse(decryptedData, file.getContentType());
	}
	
}


