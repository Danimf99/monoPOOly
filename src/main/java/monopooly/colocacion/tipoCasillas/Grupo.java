package monopooly.colocacion.tipoCasillas;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.VisitanteCasilla;
import monopooly.colocacion.tipoCasillas.propiedades.Monopolio;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.colocacion.tipoCasillas.propiedades.TipoMonopolio;
import monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad.Solar;
import monopooly.configuracion.Precios;
import monopooly.configuracion.ReprASCII;
import monopooly.player.Jugador;

import java.util.HashSet;

import static monopooly.entradaSalida.PintadoAscii.genEdificaciones;
import static monopooly.entradaSalida.PintadoAscii.genInfo;

/**
 * Grupo tiene que ser una agregacion lo cual es un poco...
 *
 *              ( * it be like that sometimes * )
 *
 * pero no pasa ni media. La idea es sacarle provecho con metodos que tenga
 * sentido usar en todas las casillas que componen un grupo a la vez.
 * Por ejemplo incrementar el precio cuando pasan X turnos
 *
 */
public class Grupo extends Casilla implements Monopolio {

    private int precio;
    private TipoMonopolio tipoMonopolio;
    private HashSet<Propiedad> propiedades;
    private String hexColor;


    public Grupo(TipoMonopolio tipoMonopolio) {
        super(genNombre(tipoMonopolio));
        this.tipoMonopolio = tipoMonopolio;
        this.propiedades = new HashSet<>();
        switch (tipoMonopolio) {
            case estacion:
                this.precio = Precios.ESTACION;
                this.hexColor = "#eaeaea";
                break;
            case marron:
                this.precio = Precios.PRECIO_MARRON;
                this.hexColor = "#987A62";
                break;
            case azul_claro:
                this.precio = Precios.PRECIO_AZUL_CLARO;
                this.hexColor = "#53CFDE";
                break;
            case violeta:
                this.precio = Precios.PRECIO_VIOLETA;
                this.hexColor = "#BC2E54";
                break;
            case naranja:
                this.precio = Precios.PRECIO_NARANJA;
                this.hexColor = "#f88229";
                break;
            case rojo:
                this.precio = Precios.PRECIO_ROJO;
                this.hexColor = "#E3170A";
                break;
            case amarillo:
                this.precio = Precios.PRECIO_AMARILLO;
                this.hexColor = "#FAA916";
                break;
            case verde:
                this.precio = Precios.PRECIO_VERDE;
                this.hexColor = "#39C80B";
                break;
            case azul_marino:
                this.precio = Precios.PRECIO_AZUL_MARINO;
                this.hexColor = "#385785";
                break;
            case servicio:
                this.precio = Precios.SERVICIOS;
                this.hexColor = "#eaeaea";
                break;
            default:
                this.precio = 0;
        }

    }


    public int sizeMonopolio(){
        return propiedades.size();
    }

    public String listaEdificaciones() {
        String nombre = "Monopolio "
                + ReprASCII.colorMonopolio(this.tipoMonopolio)
                + " " + this.tipoMonopolio.toString() + " "
                + ReprASCII.ANSI_RESET;
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("\n");
        for (Propiedad calle : this.propiedades) {
            if(calle instanceof Solar) {
                sBuilder.append(genEdificaciones((Solar)calle));
            }
        }
        return genInfo(sBuilder.toString(), nombre);
    }

    public int cantidadPropiedades(Jugador player) {
        int total = 0;
        for (Propiedad calle : propiedades) {
            if (calle.getPropietario().equals(player)) {
                total++;
            }
        }
        return total;
    }

    public boolean esCompleto() {
        HashSet<Jugador> propietariosCalles = new HashSet<>();
        for (Propiedad calle : propiedades) {
            propietariosCalles.add(calle.getPropietario());
        }
        return propietariosCalles.size() == 1;
    }
    /**
     * Genera el nombre del grupo / monopolio en funcion de su tipo
     *
     * @param tipoMonopolio Tipo de la instancia del grupo
     * @return String con el nombre
     */
    private static String genNombre(TipoMonopolio tipoMonopolio) {
        String monopolio = tipoMonopolio.toString();
        return "Monopolio " +
                "" + monopolio.substring(0, 1).toUpperCase() + monopolio;
    }

    public void addPropiedad(Propiedad propiedad) {
        this.propiedades.add(propiedad);
        propiedad.setPrecio(this.precio);
    }

    public HashSet<Propiedad> getPropiedades() {
        return new HashSet<>(propiedades);
    }

    public void setPropiedades(HashSet<Propiedad> propiedades) {
        this.propiedades = propiedades;
    }

    @Override
    public TipoMonopolio getTipoMonopolio() {
        return tipoMonopolio;
    }

    @Override
    public void setTipoMonopolio(TipoMonopolio tipoMonopolio) {
        this.tipoMonopolio = tipoMonopolio;
    }

    @Override
    public boolean compartenPropietario() {
        return propiedades.stream().map(Propiedad::getPropietario).distinct().count() == 1;
    }

    @Override
    public void incrementarPrecio() {
        propiedades.forEach(Propiedad::incrementarPrecio);
    }

    @Override
    public void setPrecio(int precio) {
        this.propiedades.forEach(propiedad -> propiedad.setPrecio(precio));
        this.precio = precio;
    }

    public String getHexColor() {
        return hexColor;
    }

    public void setHexColor(String hexColor) {
        this.hexColor = hexColor;
    }

    @Override
    public int getPrecio() {
        return this.precio;
    }

    @Override
    public void visitar(VisitanteCasilla visitante) {
        // Un grupo no se visita =P
    }

    @Override
    public TipoMonopolio getTipo() {
        return tipoMonopolio;
    }
}
