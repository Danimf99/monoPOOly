package monopooly.colocacion;

import monopooly.colocacion.tipoCasillas.Grupo;
import monopooly.colocacion.tipoCasillas.Impuesto;
import monopooly.colocacion.tipoCasillas.accion.CajaComunidad;
import monopooly.colocacion.tipoCasillas.accion.Suerte;
import monopooly.colocacion.tipoCasillas.accion.especiales.Especial;
import monopooly.colocacion.tipoCasillas.accion.especiales.implementacionesEspecial.Carcel;
import monopooly.colocacion.tipoCasillas.accion.especiales.implementacionesEspecial.IrCarcel;
import monopooly.colocacion.tipoCasillas.accion.especiales.implementacionesEspecial.Parking;
import monopooly.colocacion.tipoCasillas.accion.especiales.implementacionesEspecial.Salida;
import monopooly.colocacion.tipoCasillas.propiedades.TipoMonopolio;
import monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad.Estacion;
import monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad.Servicio;
import monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad.Solar;
import monopooly.configuracion.Nombres;
import monopooly.configuracion.Posiciones;
import monopooly.configuracion.Precios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Collectors;

/**
 * Esta clase se encarga de cargar la configuracion de la colocacion y nombre de las casillas y
 * devuelve un ArrayList con todas las casillas del tablero por orden, para que el tablero solo
 * tenga que llamar a esta clase para generar el tablero con todas sus casillas
 *
 * @author Danimf99
 * @author luastan
 */
public class FabricaCasillas {
    private static final Iterator nombres = Arrays.asList(Nombres.CALLES).iterator();
    private static final HashMap<TipoMonopolio, Grupo> grupos = new HashMap<>();

    /**
     * Itera y avanza sobre los nombres de las casillas para ir generandolos por orden
     * @return Nombre para la posicion actual
     */
    private String yieldNombre() {
        return (String) nombres.next();
    }

    /**
     *
     * @param tipoMonopolio Grupo al que pertenece la casilla
     * @return Casilla correspondiente al grupo
     */
    private Casilla genCasilla(TipoMonopolio tipoMonopolio) {
        switch (tipoMonopolio) {
            case impuesto:
                return new Impuesto(yieldNombre(), Precios.IMPUESTOS);
            case parking:
                return new Especial(yieldNombre(), new Parking());
            case salida:
                return new Especial(yieldNombre(), new Salida());
            case carcel:
                return new Especial(yieldNombre(), new Carcel());
            case ir_carcel:
                return new Especial(yieldNombre(), new IrCarcel());
            case caja_comunidad:
                return new CajaComunidad(yieldNombre());
            case suerte:
                return new Suerte(yieldNombre());
            case estacion:
                return new Estacion(grupos.get(tipoMonopolio), yieldNombre());
            case servicio:
                return new Servicio(grupos.get(tipoMonopolio), yieldNombre());
            default:
                return new Solar(grupos.get(tipoMonopolio), yieldNombre());
        }
    }

    /**
     *
     * @return Casillas en un ArrayList en el orden por defecto
     */
    public ArrayList<Casilla> genCasillas() {
        Arrays.stream(Posiciones.coloresValidos).forEach(monopolio -> grupos.put(monopolio, new Grupo(monopolio)));
        return Arrays.stream(Posiciones.ORDENACION_POR_DEFECTO).map(this::genCasilla).collect(Collectors.toCollection(ArrayList::new));
    }
}
