package pl.zzpj.cryptography.web.exceptionhandlers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

import org.springframework.http.HttpStatus;

import pl.zzpj.cryptography.des.exceptions.InvalidKeyException;

@ControllerAdvice
public class ApiExceptionsHandler {
	
	@ExceptionHandler(InvalidKeyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    String handleInvalidKeyException(InvalidKeyException exception) {
        return exception.getMessage();
    }
	
	@ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    String handleIOException(IOException exception) {
        return exception.getMessage();
    }
}
