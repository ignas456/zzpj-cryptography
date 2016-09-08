package pl.zzpj.cryptography.aspect;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

	private final static Logger LOGGER = Logger.getLogger(LoggingAspect.class);
	
	@Before("execution (public * pl.zzpj.cryptography.web.restapi.DESController.*(..) )")
	public void logBeforeControllerExecution(JoinPoint joinPoint) {
		StringBuilder sb = new StringBuilder();
		sb.append("RestAPI - Start execution [" + joinPoint.getSignature() + "] ");
		sb.append("with params: " + Arrays.toString(joinPoint.getArgs()));
 
		String message = sb.toString();
		LOGGER.info(message);
	}
	
	@After("execution (public * pl.zzpj.cryptography.web.restapi.DESController.*(..) )")
	public void logAfterControllerExecution(JoinPoint joinPoint) {
		StringBuilder sb = new StringBuilder();
		sb.append("RestAPI - Stop execution [" + joinPoint.getSignature() + "] ");
		sb.append("with params: " + Arrays.toString(joinPoint.getArgs()));
 
		String message = sb.toString();
		LOGGER.info(message);
	}
	
	@Before("execution (* pl.zzpj.cryptography.des.algorithm.DesImpl.*(..) )")
	public void logBeforeDesMethodsExecution(JoinPoint joinPoint) {
		StringBuilder sb = new StringBuilder();
		sb.append("DES - Start execution [" + joinPoint.getSignature() + "] ");
		sb.append("with params: " + Arrays.toString(joinPoint.getArgs()));
 
		String message = sb.toString();
		LOGGER.debug(message);
	}
	
	@After("execution (* pl.zzpj.cryptography.des.algorithm.DesImpl.*(..) )")
	public void logAfterDesMethodsExecution(JoinPoint joinPoint) {
		StringBuilder sb = new StringBuilder();
		sb.append("DES - Stop execution [" + joinPoint.getSignature() + "] ");
		sb.append("with params: " + Arrays.toString(joinPoint.getArgs()));
 
		String message = sb.toString();
		LOGGER.debug(message);
	}
	
}
