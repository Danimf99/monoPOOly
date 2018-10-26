package monopooly.configuracion;

public class Nombres {
    public static final String[] CALLES = {
            "Salida",

            "Calle1",
            "Caja de la comunidad",
            "Calle1",
            "Impuestos 1",

            "Estacion 1",

            "Calle1",
            "Calle1",
            "Suerte",
            "Calle1",

            "Carcel",

            "Calle1",
            "Servicios 1",
            "Calle1",
            "Calle1",

            "Estacion 2",

            "Calle1",
            "Caja de la comunidad",
            "Calle1",
            "Calle1",

            "Parking",

            "Calle1",
            "Suerte",
            "Calle1",
            "Calle1",

            "Estacion 3",

            "Calle1",
            "Calle1",
            "Servicios 2",
            "Calle1",

            "Ve a la carcel",

            "Calle1",
            "Calle1",
            "Caja de la comunidad",
            "Calle1",

            "Estacion 4",

            "Suerte",
            "Calle1",
            "Impuestos 2",
            "Calle1",
    };

    public static int maxLen() {
        int max = 0;
        for (String nombre :
                CALLES) {
            if (nombre.length() > max) {
                max = nombre.length();
            }
        }
        return max;
    }
}
