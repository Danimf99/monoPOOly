package monopooly.configuracion;

public class Nombres {
    public static final String[] CALLES = {
            "Salida",

            "Murcia",
            "Caja",
            "A Coruña",
            "IRPF",

            "Invierno",

            "Plasencia",
            "Suerte",
            "Ciudad Real",
            "Almería",

            "Carcel",

            "Toledo",
            "Desatranques J",
            "Teruel",
            "Getafe",

            "Otoño",

            "Zaragoza",
            "Caja",
            "Lugo",
            "Vigo",

            "Parking",

            "Salamanca",
            "Suerte",
            "Cádiz",
            "Sevilla",

            "Verano",

            "Oviedo",
            "Valladolid",
            "Fontaneria",
            "Valencia",

            "Ir a carcel",

            "Bilbao",
            "Barcelona",
            "Caja",
            "Madrid",

            "Primavera",

            "Suerte",
            "Marbella",
            "IVA",
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
