package monopooly.configuracion;

public class Nombres {
    public static final String[] CALLES = {
            "Salida",

            "Calle1",
            "Caja",
            "Calle2",
            "Impuestos 1",

            "Estacion 1",

            "Calle3",
            "Suerte",
            "Calle4",
            "Calle55",

            "Carcel",

            "Calle6",
            "Servicios 1",
            "Calle7",
            "Calle8",

            "Estacion 2",

            "Calle9",
            "Caja",
            "Calle10",
            "Calle11",

            "Parking",

            "Calle12",
            "Suerte",
            "Calle13",
            "Calle14",

            "Estacion 3",

            "Calle15",
            "Calle16",
            "Servicios 2",
            "Calle17",

            "Ve a la carcel",

            "Calle18",
            "Calle19",
            "Caja",
            "Calle20",

            "Estacion 4",

            "Suerte",
            "Calle21",
            "Impuestos 2",
            "Calle22",
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
