package monopooly.configuracion;

public class Precios {
    public static final String MONEDA = "$";
    public static final int DINERO_INICIAL=1000000; //Por poner algo
    public static final int SALIDA = 200;
    public static final int SERVICIOS = (int) (SALIDA * 0.75);
    public static final int IMPUESTOS = SERVICIOS;
    public static final int ESTACION = SALIDA;
    public static final int FOO = 420;
    public static final int PRECIO_MARRON=60;
    public static final int PRECIO_AZUL_CLARO=(int)((1.3*PRECIO_MARRON*2)/3);
    public static final int PRECIO_VIOLETA=(int)(1.3*PRECIO_AZUL_CLARO);
    public static final int PRECIO_NARANJA=(int)(1.3*PRECIO_VIOLETA);
    public static final int PRECIO_ROJO=(int)(1.3*PRECIO_NARANJA);
    public static final int PRECIO_AMARILLO=(int)(1.3*PRECIO_ROJO);
    public static final int PRECIO_VERDE=(int)(1.3*PRECIO_AMARILLO);
    public static final int PRECIO_AZUL_MARINO=(int)((1.3*PRECIO_VERDE*3)/2);
}
