package pl.zzpj.cryptography.web.restapi;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.minidev.json.JSONObject;
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
	public HttpEntity<JSONObject> encryptText(@RequestBody TextEncryptionParams params) throws InvalidKeyException {
		des.setKey(params.getKey().getBytes(defaultCharset));
		byte[] encryptedData = des.encrypt(params.getText().getBytes(defaultCharset));
		return new ResponseEntity<JSONObject>(convertToJsonObject("encryptedText", encryptedData), HttpStatus.OK);
	}
	
	@RequestMapping(path = "/decrypt/text", method = RequestMethod.POST)
	public HttpEntity<JSONObject> decryptText(@Valid @RequestBody TextEncryptionParams params) throws InvalidKeyException {
		des.setKey(params.getKey().getBytes(defaultCharset));
		byte[] encryptedData = DatatypeConverter.parseBase64Binary(params.getText());
		byte[] decryptedData = des.decrypt(encryptedData);
		String decryptedText = new String(decryptedData, defaultCharset);
		return new ResponseEntity<JSONObject>(convertToJsonObject("decryptedText", decryptedText), HttpStatus.OK);
	}
	
//	@RequestMapping(path = "/encrypt/file", method = RequestMethod.POST, consumes={"multipart/form-data"})
//	public HttpEntity<JSONObject> encryptFile(@RequestParam(value="file") MultipartFile file, @RequestParam("key") String key) throws InvalidKeyException, IOException {
//		des.setKey(key.getBytes());
//		byte[] encryptedData = des.encrypt(file.getBytes());
//		byte[] encryptedBase64 = Base64Utils.encode(encryptedData);
//		return new ResponseEntity<JSONObject>(convertToJsonObject("encryptedFile", encryptedBase64), HttpStatus.OK);
//	}
	
//	@RequestMapping(path = "/decrypt/file", method = RequestMethod.POST)
//	public HttpEntity<JSONObject> decryptFile(@RequestParam("file") MultipartFile file, @RequestParam("key") String key) throws InvalidKeyException, IOException {
//		des.setKey(key.getBytes());
//		byte[] decryptedData = des.decrypt(file.getBytes());
//		byte[] decryptedBase64 = Base64Utils.encode(decryptedData);
//		return new ResponseEntity<JSONObject>(convertToJsonObject("decryptedFile", decryptedBase64), HttpStatus.OK);
//	}
	
	private JSONObject convertToJsonObject(String key, Object value) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(key, value);
		return jsonObject;
	}
	
	@RequestMapping(path = "/encrypt/file", method = RequestMethod.POST)
	public HttpEntity<byte[]> encryptFile(@RequestParam("file") MultipartFile file, @RequestParam("key") String key) throws InvalidKeyException, IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", file.getContentType());
		headers.add("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
		return new ResponseEntity<byte[]>(file.getBytes(), headers, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/decrypt/file", method = RequestMethod.POST)
	public HttpEntity<byte[]> decryptFile(@RequestParam("file") MultipartFile file, @RequestParam("key") String key) throws InvalidKeyException, IOException {
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(file.getBytes(), HttpStatus.OK);
		return response;
	}

}