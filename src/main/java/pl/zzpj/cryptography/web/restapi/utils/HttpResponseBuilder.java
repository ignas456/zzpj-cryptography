package pl.zzpj.cryptography.web.restapi.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class HttpResponseBuilder {
	
	public HttpEntity<String> buildTextResponse(String text) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_PLAIN);
		return new ResponseEntity<String>(text, headers, HttpStatus.OK);
	}

	public HttpEntity<byte[]> buildFileResponse(byte[] fileStream, String contentType) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", contentType);
		return new ResponseEntity<byte[]>(fileStream, headers, HttpStatus.OK);
	}
}
