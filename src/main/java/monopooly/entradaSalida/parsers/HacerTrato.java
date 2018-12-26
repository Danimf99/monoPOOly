package monopooly.entradaSalida.parsers;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.entradaSalida.Juego;
import monopooly.excepciones.ExcepcionAccionInvalida;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.Jugador;

public class HacerTrato implements Expresion {
    private String[] comandoIntroducido;

    public HacerTrato(String[] comandoIntroducido) {
        if (comandoIntroducido == null) {
            return;
        }
        this.comandoIntroducido=comandoIntroducido;
    }

    private boolean esNumero(String cadena){
        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }

    @Override
    public void interpretar(Juego interprete) throws ExcepcionMonopooly {
        String comandoReplace=comandoIntroducido[1].replace(":","");//Variable para quitar : , ( ) del comando introducido
        Jugador receptor=Tablero.getTablero().getJugador(comandoReplace);
        if(receptor==null){
            throw new ExcepcionAccionInvalida("El jugador indicado para el trato no existe");
        }

        //Para comprobar que la primera propiedad no es nombre con espacios
        if(comandoIntroducido[3].contains(",") || comandoIntroducido[4].equals("y")){
            comandoReplace=comandoIntroducido[3].replace("(","");
            comandoReplace=comandoReplace.replace(",","");
            Casilla casilla1 = interprete.casillaCorrecta(comandoReplace);
            if(comandoIntroducido[4].contains(")") || comandoIntroducido[5].equals("y")){//Comprobamos que la segunda propiedad no es nombre compuesto
                comandoReplace=comandoIntroducido[4].replace(")","");
                if(esNumero(comandoReplace)){
                    interprete.Hacertrato2(Tablero.getPrompt().getJugador(),receptor,(Propiedad)casilla1,Integer.parseInt(comandoReplace));
                    return;
                }
                Casilla casilla2=interprete.casillaCorrecta(comandoReplace);
                if(casilla1 instanceof Propiedad && casilla2 instanceof Propiedad){
                    interprete.Hacertrato1(Tablero.getPrompt().getJugador(),receptor,(Propiedad)casilla1,(Propiedad)casilla2);
                }
                else{
                    throw new ExcepcionAccionInvalida("Algunas de las casillas que quieres intercambiar no es una propiedad");
                }
            }
            else{
                comandoIntroducido[4]=comandoIntroducido[4].concat(" "+comandoIntroducido[5]);
                comandoReplace=comandoIntroducido[4].replace(")","");
                Casilla casilla2=interprete.casillaCorrecta(comandoReplace);
                if(casilla1 instanceof Propiedad && casilla2 instanceof Propiedad){
                    interprete.Hacertrato1(Tablero.getPrompt().getJugador(),receptor,(Propiedad)casilla1,(Propiedad)casilla2);
                }
                else{
                    throw new ExcepcionAccionInvalida("Algunas de las casillas que quieres intercambiar no es una propiedad");
                }
            }
            return;
        }

        comandoIntroducido[3]=comandoIntroducido[3].concat(" "+comandoIntroducido[4]);
        comandoReplace=comandoIntroducido[3].replace("(","");
        comandoReplace=comandoReplace.replace(",","");

        Casilla casilla1=interprete.casillaCorrecta(comandoReplace);
        if(casilla1==null){
            throw new ExcepcionAccionInvalida("La casilla no 1 no existe");
        }

        if(comandoIntroducido[5].contains(")") || comandoIntroducido[5].equals("y")){//Comprobamos que la segunda propiedad no es nombre compuesto
            comandoReplace=comandoIntroducido[5].replace(")","");
            if(esNumero(comandoReplace)){
                interprete.Hacertrato2(Tablero.getPrompt().getJugador(),receptor,(Propiedad)casilla1,Integer.parseInt(comandoReplace));
                return;
            }
            Casilla casilla2=interprete.casillaCorrecta(comandoReplace);
            if(casilla1 instanceof Propiedad && casilla2 instanceof Propiedad){
                interprete.Hacertrato1(Tablero.getPrompt().getJugador(),receptor,(Propiedad)casilla1,(Propiedad)casilla2);
            }
            else{
                throw new ExcepcionAccionInvalida("Algunas de las casillas que quieres intercambiar no es una propiedad");
            }
        }
        else{
            comandoIntroducido[5]=comandoIntroducido[5].concat(" "+comandoIntroducido[6]);
            comandoReplace=comandoIntroducido[5].replace(")","");
            Casilla casilla2=interprete.casillaCorrecta(comandoReplace);
            if(casilla1 instanceof Propiedad && casilla2 instanceof Propiedad){
                interprete.Hacertrato1(Tablero.getPrompt().getJugador(),receptor,(Propiedad)casilla1,(Propiedad)casilla2);
            }
            else{
                throw new ExcepcionAccionInvalida("Algunas de las casillas que quieres intercambiar no es una propiedad");
            }
        }
    }
}
