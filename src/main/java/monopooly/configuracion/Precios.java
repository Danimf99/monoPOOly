package monopooly.configuracion;

public class Precios {
    public static final String MONEDA = "$";
    public static final int DINERO_INICIAL=939;
    public static final int SALIDA = 200;
    public static final int SERVICIOS = (int) (SALIDA * 0.75);
    public static final int IMPUESTOS = SERVICIOS;
    public static final int ESTACION = SALIDA;
    public static final int HIPOTECA_SERVICIOS=(int)(SERVICIOS/2);
    public static final int FACTOR_SERVICIOS = SALIDA / 200;
    public static final int SALIR_CARCEL=SALIDA/4;
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

    // MULTIPLICADOR VALOR_EDIFICACIONES
    public static final double VALOR_CASA = 0.6;
    public static final double VALOR_HOTEL = 0.6;
    public static final double VALOR_PISCINA = 0.4;
    public static final double VALOR_DEPORTE = 1.25;


}
