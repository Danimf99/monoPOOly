package monopooly.colocacion.calles;

import monopooly.configuracion.Precios;
import monopooly.configuracion.ReprASCII;
import monopooly.entradaSalida.Mensajes;
import monopooly.player.Jugador;

public class Inmueble {
    private String nombre;
    private int precio;
    private int precio_inicial;
    private TipoInmueble tipo;
    private Jugador propietario;
    private Monopolio grupoColor;

    /* Constructores */


    // Constructor
    public Inmueble(Jugador banca, String nombre, int precio, Monopolio grupoColor) {
        if (nombre == null || grupoColor == null) {
            Mensajes.error("No se puede Inicializar el Inmueble; uno de los valores en null.");
            return;
        }
        if (precio < 0) {
            Mensajes.error("No se puede Inicializar el Inmuele; el precio no puede ser negativo.");
        }

        this.nombre = nombre;
        this.precio_inicial = precio;
        this.precio = precio;
        this.grupoColor = grupoColor;
        this.propietario = banca;

        grupoColor.insertarInmueble(this);

    }

    /* Getters */

    public String getNombre() {
        return nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public Jugador getPropietario() {
        return propietario;
    }

    public Monopolio getGrupoColor() {
        return grupoColor;
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


    public void incrementarPrecio() {
        this.precio = (int) (this.precio * 1.05);
    }

    /**
     * Devuelve cuanto se debe pagar al caer en la casilla si esta pertenece a un jugadodr distinto al que cae
     * @return Cantidad de dinero que se debe abonar
     */
    public int calcularAlquiler(Jugador deudor) {
        int dineroAlquiler = 0;
        switch (this.grupoColor.getTipo()) {
            case estacion:
                dineroAlquiler =(int) ((Precios.SALIDA/4) * this.grupoColor.cantidadPropiedades(this.propietario));
                break;
            case parking:
            case impuesto:
                dineroAlquiler = this.precio;
                break;
            case suerte:
            case caja_comunidad:
            case none:
                break;
            case servicio:
                dineroAlquiler = Precios.FACTOR_SERVICIOS * deudor.getDados().tirada();
                break;
            default:
                dineroAlquiler = (int) (this.precio_inicial * 0.1);
                // Habria que tener en cuenta las casas pero de momento no hay
                if (this.grupoColor.esCompleto()) {
                    dineroAlquiler *= 2;
                }
        }

        return dineroAlquiler;
    }

    /**
     * Permite comprar la propiedad
     * @param deudor Personaje que compra
     */
    public void compra(Jugador deudor) {
        if (deudor == null) {
            Mensajes.error("Un jugador null no puede comprar una propiedad");
            return;
        }
        this.propietario.anhadirDinero(this.precio);
        this.propietario.quitarPropiedad(this);
        deudor.quitarDinero(this.precio);
        deudor.anhadirPropiedad(this);
        this.propietario = deudor;
    }


    /**
     * Pago correspondiente a caer en una casilla
     * @param deudor Personaje que cae
     */
    public void pago(Jugador deudor) {
        if (deudor == null) {
            Mensajes.error("Un jugador null no puede pagar cuando caes");
            return;
        }
        this.propietario.anhadirDinero(this.calcularAlquiler(deudor));
        deudor.quitarDinero(this.calcularAlquiler(deudor));
        this.propietario = deudor;
    }


    @Override
    public String toString() {

        return "Inmueble{" + "\n" +
                "\tnombre='" + nombre + "'\n" +
                "\t, precio=" + precio + "\n" +
                "\t, tipo=" + tipo + "\n" +
                "\t, propietario=" + propietario + "\n" +
                "\t, grupoColor=" + grupoColor + "\n" +
                '}';
    }
}
