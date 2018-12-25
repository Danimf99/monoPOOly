package monopooly.entradaSalida.parsers;

import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.propiedades.TipoMonopolio;
import monopooly.entradaSalida.Juego;
import monopooly.excepciones.ExcepcionArgumentosIncorrectos;
import monopooly.excepciones.ExcepcionMonopooly;

public class Listar implements Expresion {
    private String[] comandoIntroducido;

    public Listar(String[] comandoIntroducido) {
        if (comandoIntroducido == null) {
            // Hay que meter excepciones aqui
            return;
        }
        this.comandoIntroducido=comandoIntroducido;
    }

    @Override
    public void interpretar(Juego interprete) throws ExcepcionMonopooly {
        if (comandoIntroducido.length < 2) {
            throw new ExcepcionArgumentosIncorrectos("EL comando listar requiere de unade las siguientes opciones:\n" +
                    "  > jugadores\n" +
                    "  > avatares\n" +
                    "  > enventa\n" +
                    "  > edificios\n");
        }
        switch (comandoIntroducido[1].toLowerCase()){
            case "jugadores":
                interprete.listarJugadores();
                break;
            case "avatares":
                interprete.listarAvatares();
                break;
            case "enventa":
                interprete.listarEnVenta();
                break;
            case "edificios":
                if(comandoIntroducido.length==2){
                    interprete.listarEdificios();
                    return;
                }
                if(comandoIntroducido.length>3){
                    for(int i=3;i<comandoIntroducido.length;i++) {
                        comandoIntroducido[2] = comandoIntroducido[2].concat("_" + comandoIntroducido[i]);
                    }
                }
                for(TipoMonopolio cadena: TipoMonopolio.values()){
                    if(cadena.toString().equals(comandoIntroducido[2])){
                        interprete.listarEdificiosGrupo(TipoMonopolio.valueOf(comandoIntroducido[2]));
                        return;
                    }
                }
                //Si no existe ningun grupo con el nombre se lanza una excepcion
                throw new ExcepcionArgumentosIncorrectos("Grupo incorrecto");
            case "sucesos":
                Juego.consola.info(
                        Tablero.getPrompt().listarAccionesTurno(),
                        "Sucesos este turno: "
                );
                break;
            default:
                throw new ExcepcionArgumentosIncorrectos("EL comando listar requiere de unade las siguientes opciones:\n" +
                        "  > jugadores\n" +
                        "  > avatares\n" +
                        "  > enventa\n" +
                        "  > edificios\n");
        }
    }
}
