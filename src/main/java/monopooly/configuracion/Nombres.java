package monopooly.configuracion;

public class Nombres {
    public static final String[] CALLES = {
            "Salida",

            "Murcia",
            "Caja",
            "A Coruña",
            "Impuestos 1",

            "Estacion 1",

            "Plasencia",
            "Suerte",
            "Ciudad Real",
            "Almería",

            "Carcel",

            "Toledo",
            "Servicios 1",
            "Teruel",
            "Getafe",

            "Estacion 2",

            "Zaragoza",
            "Caja",
            "Lugo",
            "Vigo",

            "Parking",

            "Salamanca",
            "Suerte",
            "Cádiz",
            "Sevilla",

            "Estacion 3",

            "Oviedo",
            "Valladolid",
            "Servicios 2",
            "Valencia",

            "Ir a carcel",

            "Bilbao",
            "Barcelona",
            "Caja",
            "Madrid",

            "Estacion 4",

            "Suerte",
            "Marbella",
            "Impuestos 2",
            "Ibiza",
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
