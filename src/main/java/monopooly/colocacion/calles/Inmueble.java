package monopooly.colocacion.calles;

import monopooly.entradaSalida.Mensajes;
import monopooly.player.Jugador;

public class Inmueble {
    private String nombre;
    private int precio;
    private TipoInmueble tipo;
    private Jugador propietario;
    private Monopolio grupoColor;

    /* Constructores */


    // Constructor para salida/cajacomunidad/carcel.....
    public Inmueble(String nombre, TipoInmueble tipo) {
        if (nombre == null || tipo == null) {
            Mensajes.error("No se puede Inicializar el Inmueble; uno de los valores en null.");
            return;
        }
        this.nombre = nombre;
        this.tipo = tipo;
        this.propietario = null;
        this.grupoColor = null;
        precio = 0;
    }

    // Constructor para impuestos
    public Inmueble(String nombre, int precio, TipoInmueble tipo) {
        if (nombre == null || tipo == null) {
            Mensajes.error("No se puede Inicializar el Inmueble; uno de los valores en null.");
            return;
        }
        if (precio < 0) {
            Mensajes.error("No se puede Inicializar el Inmuele; el precio no puede ser negativo.");
        }
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
        this.propietario = null;
        this.grupoColor = null;
    }

    // Constructor para el resto de calles
    public Inmueble(String nombre, int precio, TipoInmueble tipo, Monopolio grupoColor) {
        if (nombre == null || tipo == null || grupoColor == null) {
            Mensajes.error("No se puede Inicializar el Inmueble; uno de los valores en null.");
            return;
        }
        if (precio < 0) {
            Mensajes.error("No se puede Inicializar el Inmuele; el precio no puede ser negativo.");
        }

        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
        this.grupoColor = grupoColor;

        grupoColor.insertarInmueble(this);

    }

    /* Getters */

    public String getNombre() {
        return nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public TipoInmueble getTipo() {
        return tipo;
    }

    public Jugador getPropietario() {
        return propietario;
    }

    /* Setters */

    public void setPropietario(Jugador propietario) {
//        TODO Añadir this Inmueble a las propiedades de propietario
        if (propietario == null) {
            Mensajes.error("No se puede asignar un propietario Null");
            return;
        }
        this.propietario = propietario;
    }

    // Igual con setPropietario llega los demas lo mismo no hacen falta


    /* Metodos para la instancia */

    /**
     * Devuelve cuanto se debe pagar al caer en la casilla si esta pertenece a un jugadodr distinto al que cae
     * @return Cantidad de dinero que se debe abonar
     */
    public int calcularPago() {

//        TODO Implementar el calculo de lo que se debe pagar al caer en la casilla
        return 0;
    }

    /**
     * Cobra a un Jugador por caer en la calle actual.
     * @param deudor Personaje al que se le quiere cobrar por caer en esta calle
     */
    public void realizarCobro(Jugador deudor) {
//        TODO Implementar el cobro automatico al jugador que cae
    }

}
