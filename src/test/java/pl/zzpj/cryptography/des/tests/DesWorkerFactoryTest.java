package pl.zzpj.cryptography.des.tests;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pl.zzpj.cryptography.ZzpjCryptographyApplication;
import pl.zzpj.cryptography.des.algorithm.DesAlgorithm;
import pl.zzpj.cryptography.des.algorithm.DesRecursiveWorker;
import pl.zzpj.cryptography.des.algorithm.DesWorkerFactory;
import pl.zzpj.cryptography.des.algorithm.Encryptor;
import pl.zzpj.cryptography.des.algorithm.interfaces.DesWorker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ZzpjCryptographyApplication.class)
public class DesWorkerFactoryTest {
	
	@Autowired
	private DesWorkerFactory sut;
	
	@Autowired
	private Encryptor encryptor;
	
	byte[] source = {1, 2, 3, 4, 5, 6, 7, 8};
	
	@Test
	public void shouldReturnDesAlgorithm() {
		sut.setWorkerType(DesWorkerFactory.SIMPLE);
		
		DesWorker worker = sut.createWorker(source, encryptor);
		
		assertThat(worker).isExactlyInstanceOf(DesAlgorithm.class);
	}
	
	@Test
	public void shouldReturnDesRecursiveWorker() {
		sut.setWorkerType(DesWorkerFactory.RECURSIVE);
		
		DesWorker worker = sut.createWorker(source, encryptor);
		
		assertThat(worker).isExactlyInstanceOf(DesRecursiveWorker.class);
	}
	
	@Test(expected=IllegalStateException.class)
	public void shouldThrowIllegalStateException() {
		String workerType = "NotValidWorkerType";
		
		sut.setWorkerType(workerType);
		sut.createWorker(source, encryptor);
	}
	
	@After
	public void after() {
		sut.setWorkerType(DesWorkerFactory.RECURSIVE);
	}

}


