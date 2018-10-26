package testClases.colocacionTest.calles;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EdificacionesTest {

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
}