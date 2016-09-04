package pl.zzpj.cryptography.web.restapi.validators;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import pl.zzpj.cryptography.web.restapi.models.TextEncryptionParams;

public class TextEncryptionParamsValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return TextEncryptionParams.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		TextEncryptionParams params = (TextEncryptionParams) target;
		boolean isBase64 = Base64.isBase64(params.getText());
		if(!isBase64)
			errors.rejectValue("text","default","text is not base64");
		
	}
	
}
