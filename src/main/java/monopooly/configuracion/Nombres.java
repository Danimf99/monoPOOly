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

    public static final String AYUDA_NOMBRE = "NAME:";
    public static final String AYUDA_SYNOPSIS = "SYNOPSIS:";
    public static final String AYUDA_DESCRIPCION = "DESCRIPTION:";
    public static final String[] LISTA_COMANDOS = {"lanzar dados",
            "describir - Describe elementos",
            "jugador - Informacion del jugador actual",
            "comprar - Comprar una propiedad",
            "salir carcel - Salir de la carcel",
            "bancarrota - Declarar bancarrota",
            "listar - Lista elementos",
            "acabar turno - Termina el turno",
            "ver tablero - Muestra el tablero"
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
