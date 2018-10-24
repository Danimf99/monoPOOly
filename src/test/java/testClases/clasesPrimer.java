package testClases;

import monopooly.player.Dados;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import testClases.colocacionTest.PosicionTest;
import testClases.colocacionTest.TableroTest;
import testClases.colocacionTest.calles.CasillaTest;
import testClases.colocacionTest.calles.EdificacionesTest;
import testClases.colocacionTest.calles.InmuebleTest;
import testClases.colocacionTest.calles.MonopolioTest;
import testClases.entradaSalida.procesarComandoTest;
import testClases.player.AvatarTest;
import testClases.player.DadosTest;
import testClases.player.JugadorTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CasillaTest.class,
        EdificacionesTest.class,
        InmuebleTest.class,
        MonopolioTest.class,
        PosicionTest.class,
        TableroTest.class,
        procesarComandoTest.class,
        AvatarTest.class,
        DadosTest.class,
        JugadorTest.class
})
public class clasesPrimer {
}
