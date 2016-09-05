package pl.zzpj.cryptography.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class InputParamsValidationAspect {
	
	// Methods from pl.zzpj.cryptography.des.utils
	
	@Before("execution(* pl.zzpj.cryptography.des.utils.interfaces.*.*(..)) && args(source, ..)")
	public void validateSource(Object source){
		if (source == null)
			throw new IllegalArgumentException("Source array is null");
	}
	
	@Before("execution(* pl.zzpj.cryptography.des.utils.interfaces.BitJuggler.getBit(..)) && args(*,position)")
	public void validateGetBitParams(int position){
		if (position < 0)
			throw new IllegalArgumentException("position is negative number");
	}
	
	@Before("execution(* pl.zzpj.cryptography.des.utils.interfaces.BitJuggler.getBits(..)) && args(*,startPosition, bitsNumber)")
	public void validateGetBitsParams(int startPosition, int bitsNumber){
		if (startPosition < 0)
			throw new IllegalArgumentException("startPosition is negative number");
		
		if (bitsNumber < 0)
			throw new IllegalArgumentException("bitsNumber is negative number");
	}
	
	@Before("execution(* pl.zzpj.cryptography.des.utils.interfaces.BitJuggler.setBit(..)) && args(*,position, value)")
	public void validateSetBitParams(int position, int value){
		if (position < 0)
			throw new IllegalArgumentException("position is negative number");
		
		if (value != 0 && value != 1)
			throw new IllegalArgumentException("value is not 1 or 0");
	}
	
	@Before("execution(* pl.zzpj.cryptography.des.utils.interfaces.BitJuggler.setBits(..)) && args(destination, destStartPosition, source, srcStartPosition, length)")
	public void validateSetBitsParams(byte[] destination, int destStartPosition, byte[] source, int srcStartPosition, int length){
		if (destination == null || source == null)
			throw new IllegalArgumentException("Source array is null");
		
		if (destStartPosition < 0 || srcStartPosition < 0)
			throw new IllegalArgumentException("Start position is negative number");
		
		if (length < 0)
			throw new IllegalArgumentException("lenght is negative number");
	}
	
	@Before("execution(* pl.zzpj.cryptography.des.utils.interfaces.BitJuggler.rotateSelectedBitsLeft(..)) && args(*, bitsNumber, step)")
	public void validateRotateSelectedBitsLeftParams(int bitsNumber, int step){
		if (bitsNumber < 0 || step < 0) 
			throw new IllegalArgumentException("Numeric agr is negative number");
	}
	
	@Before("execution(* pl.zzpj.cryptography.des.utils.interfaces.BitJuggler.separateBits(..)) && args(source, lenght)")
	public void validateSeparateBitsParams(byte[] source, int lenght){
		if (source == null)
			throw new IllegalArgumentException("Source array is null");
		
		if (lenght < 0 || lenght > 8)
			throw new IllegalArgumentException("lenght is negative number");
		
		if (((source.length * 8) % lenght != 0))
			throw new IllegalArgumentException("wrong lenght");
	}
	
	@Before("execution(* pl.zzpj.cryptography.des.utils.interfaces.BitJuggler.concatBitSeries(..)) && args(firstSeries, firstLenght,  secondSeries,  secondLenght)")
	public void validateConcatBitSeriesParams(byte[] firstSeries, int firstLenght, byte[] secondSeries, int secondLenght){
		if (firstSeries == null || secondSeries == null)
			throw new IllegalArgumentException("Source array is null");
		
		if (firstLenght < 0 || secondLenght < 0)
			throw new IllegalArgumentException("length is negative number");
	}
}
