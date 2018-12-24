package monopooly.entradaSalida.parsers;

import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.colocacion.tipoCasillas.propiedades.edificios.Edificio;
import monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad.Solar;
import monopooly.entradaSalida.Juego;
import monopooly.excepciones.ExcepcionAccionInvalida;
import monopooly.excepciones.ExcepcionArgumentosIncorrectos;
import monopooly.excepciones.ExcepcionMonopooly;

public class Vender implements Expresion{
    private String[] comandoIntroducido;

    public Vender(String[] comandoIntroducido) {
        if (comandoIntroducido == null) {
            return;
        }
        this.comandoIntroducido=comandoIntroducido;
    }
    @Override
    public void interpretar(Juego interprete) throws ExcepcionMonopooly {
        if(comandoIntroducido.length<4){
            throw new ExcepcionArgumentosIncorrectos("Numero de argumentos mal introducido");
        }
        if(comandoIntroducido.length>4){
            for(int i=3;i<comandoIntroducido.length-1;i++){
                comandoIntroducido[2]=comandoIntroducido[2].concat(" "+comandoIntroducido[i]);
            }
        }
        if(!(Tablero.getTablero().getCasilla(comandoIntroducido[2]) instanceof Solar)){
            throw new ExcepcionAccionInvalida("La casilla "+comandoIntroducido[2]+" no es un solar");
        }
        if(!((Propiedad)Tablero.getTablero().getCasilla(comandoIntroducido[2])).getPropietario().equals(Tablero.getPrompt().getJugador())){
            throw new ExcepcionAccionInvalida("La propiedad "+comandoIntroducido[2]+" no te pertenece");
        }
        if(((Solar)(Tablero.getTablero().getCasilla(comandoIntroducido[2]))).getEdificios().size()==0){
            throw new ExcepcionAccionInvalida("La propiedad "+comandoIntroducido[2]+" no tiene edificios");

        }
        int num=Integer.parseInt(comandoIntroducido[comandoIntroducido.length-1]);
        for(Edificio.TIPO cadena: Edificio.TIPO.values()){
            if(cadena.toString().equals(comandoIntroducido[1])){
                interprete.vender(Tablero.getTablero().getCasilla(comandoIntroducido[2]),num,Edificio.TIPO.valueOf(comandoIntroducido[1]));
                return;
            }
        }
        throw new ExcepcionArgumentosIncorrectos("Tipo de edificio incorrecto");
    }
}
