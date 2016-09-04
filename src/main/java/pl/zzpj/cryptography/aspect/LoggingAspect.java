package pl.zzpj.cryptography.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	@Before("execution (* pl.zzpj.cryptograpchy.des..*(..) ) && !within(LoggingAspect)&& !within(InputParamsValidationAspect)")
	public void logasa() {
		System.out.println("dsd");
	}
	
}
