package ejemploTest;

import org.junit.*;

import static junit.framework.TestCase.*;

public class clase1Test {

    @BeforeClass
    public static void preSetUp() {
        System.out.println("Se hace antes de empezar los tests de clase1Test");
    }


    @Before
    public void setUp(){
        System.out.println("Se hace antes de cada uno de los tests de clase1Test");
    }


    @Test
    public void ejemploTest1() {
        System.out.println("Caracola");
    }

    @Ignore
    public void ejemploTestIgnorado() {
        System.out.println("Neverlucky");
    }

    @Test
    public void ejemploComparacion() {
        assertEquals(0, 0);
    }

    @Test
    public void ejemploTrue() {
        assertTrue(true);
    }

    @Test
    public void ejemploNull() {
        assertNull(null);  // Tambien hay assertNotNull
    }
}
