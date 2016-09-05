package pl.zzpj.cryptography.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import pl.zzpj.cryptography.des.keyGenerator.RandomKeyGenerator;

@Configuration
@EnableAspectJAutoProxy
public class AppConfig {
	
	@Bean
	public RandomKeyGenerator serverSingleton() {
	    RandomKeyGenerator instance = RandomKeyGenerator.INSTANCE;
	    return instance;
	}
}
