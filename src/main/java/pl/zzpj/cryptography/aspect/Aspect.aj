package pl.zzpj.cryptography.aspect;

import org.aspectj.lang.annotation.Before;

@org.aspectj.lang.annotation.Aspect
public aspect Aspect {

	@Before("execution(* pl.zzpj.cryptography.des.utils.BitPrinterImpl.printBits(..)) && args(source)")
	public void validateInputParams(byte[] source) {
		if (source == null)
			throw new IllegalArgumentException("source array is null");
	}
	
}
