package ejemploTest;

import org.junit.*;

import static junit.framework.TestCase.*;

public class clase2Test {

    @BeforeClass
    public static void preSetUp() {
        System.out.println("\n\nSe hace antes de empezar los tests de clase2Test");
    }


    @Before
    public void setUp(){
        System.out.println("Se hace antes de cada uno de los tests de clase2Test");
    }


    @Test
    public void ejemplo1() {
        System.out.println("amarillo");
        assertTrue(true);
    }

    @Test
    public void ejemplo2() {
        System.out.println("rojo");
    }


}
