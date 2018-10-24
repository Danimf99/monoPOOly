import ejemploTest.ejemploTestPrimer;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import testClases.clasesPrimer;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ejemploTestPrimer.class,
        clasesPrimer.class
})
public class fullTest {
}
