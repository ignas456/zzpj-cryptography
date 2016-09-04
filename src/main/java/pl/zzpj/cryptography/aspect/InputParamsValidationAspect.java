package pl.zzpj.cryptography.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class InputParamsValidationAspect {

	@Before("execution(* pl.zzpj.cryptography.des.utils.interfaces.BitPrinter.printBits(..)) && args(source)")
	public void validateBitPrinterMethodsParams(byte[] source) {
		if (source == null)
			throw new IllegalArgumentException("Source array is null");
	}
	
	@Before("execution(* pl.zzpj.cryptography.des.utils.interfaces.*.*(..)) && args(source, ..)")
	public void validateBitJugglerMethodsParams(byte[] source){
		if (source == null)
			throw new IllegalArgumentException("Source array is null");
	}
	
}
