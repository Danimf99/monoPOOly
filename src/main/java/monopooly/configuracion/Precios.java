package monopooly.configuracion;

public class Precios {
    public static final String MONEDA = "$";
    public static final int DINERO_INICIAL=1000; //Por poner algo
    public static final int SALIDA = 200;
    public static final int SERVICIOS = (int) (SALIDA * 0.75);
    public static final int IMPUESTOS = SERVICIOS;
    public static final int ESTACION = SALIDA;
    public static final int FOO = 420;
    public static final int HIPOTECA_SERVICIOS=(int)(SERVICIOS/2);

    //PRECIO CASILLAS INICIAL
    public static final int PRECIO_MARRON=60;
    public static final int PRECIO_AZUL_CLARO=(int)((1.3*PRECIO_MARRON*2)/3);
    public static final int PRECIO_VIOLETA=(int)(1.3*PRECIO_AZUL_CLARO);
    public static final int PRECIO_NARANJA=(int)(1.3*PRECIO_VIOLETA);
    public static final int PRECIO_ROJO=(int)(1.3*PRECIO_NARANJA);
    public static final int PRECIO_AMARILLO=(int)(1.3*PRECIO_ROJO);
    public static final int PRECIO_VERDE=(int)(1.3*PRECIO_AMARILLO);
    public static final int PRECIO_AZUL_MARINO=(int)((1.3*PRECIO_VERDE*3)/2);

    //ALQUILERES INICIALES
    public static final int ALQUILER_MARRON=(int)(0.1*PRECIO_MARRON);
    public static final int ALQUILER_AZUL_CLARO=(int)(0.1*PRECIO_AZUL_CLARO);
    public static final int ALQUILER_VIOLETA=(int)(0.1*PRECIO_VIOLETA);
    public static final int ALQUILER_NARANJA=(int)(0.1*PRECIO_NARANJA);
    public static final int ALQUILER_ROJO=(int)(0.1*PRECIO_ROJO);
    public static final int ALQUILER_AMARILLO=(int)(0.1*PRECIO_AMARILLO);
    public static final int ALQUILER_VERDE=(int)(0.1*PRECIO_VERDE);
    public static final int ALQUILER_AZUL_MARINO=(int)(0.1*PRECIO_AZUL_MARINO);

    //PRECIOS PARA CASILLAS MARRONES
    public static final int VALOR_CASA_MARRON=(int)(0.6*PRECIO_MARRON);
    public static final int VALOR_HOTEL_MARRON=(int)(0.6*PRECIO_MARRON);
    public static final int VALOR_PISCINA_MARRON=(int)(0.4*PRECIO_MARRON);
    public static final int VALOR_DEPORTE_MARRON=(int)(1.25*PRECIO_MARRON);
    public static final int HIPOTECA_CASA_MARRON=(int)(VALOR_CASA_MARRON/2);
    public static final int HIPOTECA_HOTEL_MARRON=(int)(VALOR_HOTEL_MARRON/2);
    public static final int HIPOTECA_PISCINA_MARRON=(int)(VALOR_PISCINA_MARRON/2);
    public static final int HIPOTECA_DEPORTE_MARRON=(int)(VALOR_DEPORTE_MARRON/2);
    public static final int HIPOTECA_MARRON=(int)(PRECIO_MARRON/2);

    //PRECIOS PARA CASILLAS AZUL CLARO
    public static final int VALOR_CASA_AZUL_CLARO=(int)(0.6*PRECIO_AZUL_CLARO);
    public static final int VALOR_HOTEL_AZUL_CLARO=(int)(0.6*PRECIO_AZUL_CLARO);
    public static final int VALOR_PISCINA_AZUL_CLARO=(int)(0.4*PRECIO_AZUL_CLARO);
    public static final int VALOR_DEPORTE_AZUL_CLARO=(int)(1.25*PRECIO_AZUL_CLARO);
    public static final int HIPOTECA_CASA_AZUL_CLARO=(int)(VALOR_CASA_AZUL_CLARO/2);
    public static final int HIPOTECA_HOTEL_AZUL_CLARO=(int)(VALOR_HOTEL_AZUL_CLARO/2);
    public static final int HIPOTECA_PISCINA_AZUL_CLARO=(int)(VALOR_PISCINA_AZUL_CLARO/2);
    public static final int HIPOTECA_DEPORTE_AZUL_CLARO=(int)(VALOR_DEPORTE_AZUL_CLARO/2);
    public static final int HIPOTECA_AZUL_CLARO=(int)(PRECIO_AZUL_CLARO/2);

    //PRECIOS PARA CASILLAS VIOLETA
    public static final int VALOR_CASA_VIOLETA=(int)(0.6*PRECIO_VIOLETA);
    public static final int VALOR_HOTEL_VIOLETA=(int)(0.6*PRECIO_VIOLETA);
    public static final int VALOR_PISCINA_VIOLETA=(int)(0.4*PRECIO_VIOLETA);
    public static final int VALOR_DEPORTE_VIOLETA=(int)(1.25*PRECIO_VIOLETA);
    public static final int HIPOTECA_CASA_VIOLETA=(int)(VALOR_CASA_VIOLETA/2);
    public static final int HIPOTECA_HOTEL_VIOLETA=(int)(VALOR_HOTEL_VIOLETA/2);
    public static final int HIPOTECA_PISCINA_VIOLETA=(int)(VALOR_PISCINA_VIOLETA/2);
    public static final int HIPOTECA_DEPORTE_VIOLETA=(int)(VALOR_DEPORTE_VIOLETA/2);
    public static final int HIPOTECA_VIOLETA=(int)(PRECIO_VIOLETA/2);

    //PRECIOS PARA CASILLAS NARANJAS
    public static final int VALOR_CASA_NARANJA=(int)(0.6*PRECIO_NARANJA);
    public static final int VALOR_HOTEL_NARANJA=(int)(0.6*PRECIO_NARANJA);
    public static final int VALOR_PISCINA_NARANJA=(int)(0.4*PRECIO_NARANJA);
    public static final int VALOR_DEPORTE_NARANJA=(int)(1.25*PRECIO_NARANJA);
    public static final int HIPOTECA_CASA_NARANJA=(int)(VALOR_CASA_NARANJA/2);
    public static final int HIPOTECA_HOTEL_NARANJA=(int)(VALOR_HOTEL_NARANJA/2);
    public static final int HIPOTECA_PISCINA_NARANJA=(int)(VALOR_PISCINA_NARANJA/2);
    public static final int HIPOTECA_DEPORTE_NARANJA=(int)(VALOR_DEPORTE_NARANJA/2);
    public static final int HIPOTECA_NARANJA=(int)(PRECIO_NARANJA/2);

    //PRECIOS PARA CASILLAS ROJAS
    public static final int VALOR_CASA_ROJO=(int)(0.6*PRECIO_ROJO);
    public static final int VALOR_HOTEL_ROJO=(int)(0.6*PRECIO_ROJO);
    public static final int VALOR_PISCINA_ROJO=(int)(0.4*PRECIO_ROJO);
    public static final int VALOR_DEPORTE_ROJO=(int)(1.25*PRECIO_ROJO);
    public static final int HIPOTECA_CASA_ROJO=(int)(VALOR_CASA_ROJO/2);
    public static final int HIPOTECA_HOTEL_ROJO=(int)(VALOR_HOTEL_ROJO/2);
    public static final int HIPOTECA_PISCINA_ROJO=(int)(VALOR_PISCINA_ROJO/2);
    public static final int HIPOTECA_DEPORTE_ROJO=(int)(VALOR_DEPORTE_ROJO/2);
    public static final int HIPOTECA_ROJO=(int)(PRECIO_ROJO/2);

    //PRECIOS PARA CASILLAS AMARILLAS
    public static final int VALOR_CASA_AMARILLO=(int)(0.6*PRECIO_AMARILLO);
    public static final int VALOR_HOTEL_AMARILLO=(int)(0.6*PRECIO_AMARILLO);
    public static final int VALOR_PISCINA_AMRILLO=(int)(0.4*PRECIO_AMARILLO);
    public static final int VALOR_DEPORTE_AMARILLO=(int)(1.25*PRECIO_AMARILLO);
    public static final int HIPOTECA_CASA_AMARILLO=(int)(VALOR_CASA_AMARILLO/2);
    public static final int HIPOTECA_HOTEL_AMARILLO=(int)(VALOR_HOTEL_AMARILLO/2);
    public static final int HIPOTECA_PISCINA_AMARILLO=(int)(VALOR_PISCINA_AMRILLO/2);
    public static final int HIPOTECA_DEPORTE_AMARILLO=(int)(VALOR_DEPORTE_AMARILLO/2);
    public static final int HIPOTECA_AMARILLO=(int)(PRECIO_AMARILLO/2);

    //PRECIOS PARA CASILLAS VERDES
    public static final int VALOR_CASA_VERDE=(int)(0.6*PRECIO_VERDE);
    public static final int VALOR_HOTEL_VERDE=(int)(0.6*PRECIO_VERDE);
    public static final int VALOR_PISCINA_VERDE=(int)(0.4*PRECIO_VERDE);
    public static final int VALOR_DEPORTE_VERDE=(int)(1.25*PRECIO_VERDE);
    public static final int HIPOTECA_CASA_VERDE=(int)(VALOR_CASA_VERDE/2);
    public static final int HIPOTECA_HOTEL_VERDE=(int)(VALOR_HOTEL_VERDE/2);
    public static final int HIPOTECA_PISCINA_VERDE=(int)(VALOR_PISCINA_VERDE/2);
    public static final int HIPOTECA_DEPORTE_VERDE=(int)(VALOR_DEPORTE_VERDE/2);
    public static final int HIPOTECA_VERDE=(int)(PRECIO_VERDE/2);

    //PRECIOS PARA CASILLAS AZUL MARINAS
    public static final int VALOR_CASA_AZUL_MARINO=(int)(0.6*PRECIO_AZUL_MARINO);
    public static final int VALOR_HOTEL_AZUL_MARINO=(int)(0.6*PRECIO_AZUL_MARINO);
    public static final int VALOR_PISCINA_AZUL_MARINO=(int)(0.4*PRECIO_AZUL_MARINO);
    public static final int VALOR_DEPORTE_AZUL_MARINO=(int)(1.25*PRECIO_AZUL_MARINO);
    public static final int HIPOTECA_CASA_AZUL_MARINO=(int)(VALOR_CASA_AZUL_MARINO/2);
    public static final int HIPOTECA_HOTEL_AZUL_MARINO=(int)(VALOR_HOTEL_AZUL_MARINO/2);
    public static final int HIPOTECA_PISCINA_AZUL_MARINO=(int)(VALOR_PISCINA_AZUL_MARINO/2);
    public static final int HIPOTECA_DEPORTE_AZUL_MARINO=(int)(VALOR_DEPORTE_AZUL_MARINO/2);
    public static final int HIPOTECA_AZUL_MARINO=(int)(PRECIO_AZUL_MARINO/2);




}
