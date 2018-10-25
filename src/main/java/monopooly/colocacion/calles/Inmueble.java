package monopooly.colocacion.calles;

import monopooly.player.Jugador;

public class Inmueble {
    private String nombre;
    private int precio;
    private TipoInmueble tipo;
    private Jugador propietario;
    private Monopolio grupoColor;

    /* Constructores */

    public Inmueble(String nombre, int precio, TipoInmueble tipo) {
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
        this.propietario = null;
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
//        TODO Comproobacion de errores en set propietario
//        TODO Añadir this Inmueble a las propiedades de propietario
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
