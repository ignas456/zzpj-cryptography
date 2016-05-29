package pl.zzpj.cryptography.des.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	ArrayUtilsTest.class, 
	BitJugglerTest.class, 
	BitPrinterTest.class 
})
public class AllTests {

}
