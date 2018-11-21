package monopooly.colocacion.calles;

import monopooly.configuracion.Precios;
import monopooly.entradaSalida.Mensajes;
import monopooly.entradaSalida.PintadoASCII;
import monopooly.player.Jugador;

import java.util.HashSet;

public class Inmueble {
    private String nombre;
    private int precio;
    private int precio_inicial;
    private TipoInmueble tipo;
    private Jugador propietario;
    private Monopolio grupoColor;
    private Boolean hipotecado;
    private HashSet<Edificaciones> edificios;

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
        this.hipotecado = false;
        this.edificios=new HashSet<>();
        grupoColor.insertarInmueble(this);

    }

    /* Getters */
    public HashSet<Edificaciones> getEdificios(){
        return edificios;
    }
    public String getNombre() {
        return nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public int getPrecio_inicial() {
        return precio_inicial;
    }

    public Jugador getPropietario() {
        return propietario;
    }

    public Monopolio getGrupoColor() {
        return grupoColor;
    }

    public Boolean getHipotecado() {
        return hipotecado;
    }

    /* Setters */


    public void setHipotecado(Boolean hipotecado) {
        this.hipotecado = hipotecado;
    }

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

    public int precioEdificio(TipoEdificio tipo){
        switch(tipo){
            case casa:
                return (int)(this.precio_inicial*Precios.VALOR_CASA);
            case hotel:
                return (int)(this.precio_inicial*Precios.VALOR_HOTEL);
            case deporte:
                return (int)(this.precio_inicial*Precios.VALOR_DEPORTE);
            case piscina:
                return (int)(this.precio_inicial*Precios.VALOR_PISCINA);
            default:
                Mensajes.error("Ese edificio no existe");
                return 0;
        }
    }
    public int calcularHipoteca() {
        return this.getPrecio_inicial() / 2;
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
    }


    public void hipotecar() {
        this.propietario.anhadirDinero(this.calcularHipoteca());
        this.hipotecado = true;
    }

    /**
     * Añade un edificio al inmueble
     * @param edificio edificio que se quiere añadir
     */
    public void anhadirEdificio(Edificaciones edificio){
        if(edificio==null){
            Mensajes.error("Edificio nulo, no se puede insertar");
            return;
        }
        edificios.add(edificio);
    }

    /**
     * Añade un edificio al inmueble
     * @param tipo edificio que se quiere añadir
     */
    public void anhadirEdificio(TipoEdificio tipo){
        edificios.add(new Edificaciones(tipo, precioEdificio(tipo), this));
    }
    public int calcularNumCasas(){
        int cont=0;
        for(Edificaciones e: this.getEdificios()){
            if(e.getTipo()==TipoEdificio.casa){
                cont++;
            }
        }
        return cont;
    }
    public int calcularNumHoteles(){
        int cont=0;
        for(Edificaciones e: edificios){
            if(e.getTipo()==TipoEdificio.hotel){
                cont++;
            }
        }
        return cont;
    }
    public int calcularNumPiscinas(){
        int cont=0;
        for(Edificaciones e: edificios){
            if(e.getTipo()==TipoEdificio.piscina){
                cont++;
            }
        }
        return cont;
    }
    public int calcularNumDeportes(){
        int cont=0;
        for(Edificaciones e: edificios){
            if(e.getTipo()==TipoEdificio.deporte){
                cont++;
            }
        }
        return cont;
    }
    public void quitarEdificio(Edificaciones edificio){
        if(edificio==null){
            Mensajes.error("Edificio nulo, no se puede eliminar");
            return;
        }
        edificios.remove(edificio);
    }

    @Override
    public String toString() {
        return PintadoASCII.genPropiedad(this);
    }
}
