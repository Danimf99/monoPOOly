package monopooly.cartas;

import monopooly.configuracion.Precios;

public class ConfCarta {

    /*
    *
    * Cartas de Caja de comunidad
    *
    * */
    public class Comunidad {
        // ConfCarta #1
        public class Carta1 {
            public static final int DINERO = 150;
            public static final String MENSAJE = ""
                    + "Vas a un balneario de 5 estrellas \n"
                    + "paga " + DINERO + " " + Precios.MONEDA
                    + ""
                    + "";
        }

        // ConfCarta #2
        public class Carta2 {
            public static final String MENSAJE = ""
                    + "Ve a la carcel sin cobrar por la casilla de salida\n"
                    + ""
                    + ""
                    + "";
        }

        // ConfCarta #3
        public class Carta3 {
            public static final int DINERO = Precios.SALIDA;
            public static final String MENSAJE = ""
                    + "Colocate en la casilla de salida. \n"
                    + "cobra " + DINERO + " " + Precios.MONEDA
                    + ""
                    + "";
        }

        // ConfCarta #4
        public class Carta4 {
            public static final int DINERO = 50;
            public static final String MENSAJE = ""
                    + "Tu compañia de internet obtiene beneficios \n"
                    + "cobra " + DINERO + " " + Precios.MONEDA
                    + ""
                    + "";
        }

        // ConfCarta #5
        public class Carta5 {
            public static final int DINERO = 150;
            public static final String MENSAJE = ""
                    + "Invitas a todos tus amigos a un viaje a Leon.\n"
                    + "paga " + DINERO + " " + Precios.MONEDA
                    + ""
                    + "";
        }

        // ConfCarta #6
        public class Carta6 {
            public static final int DINERO = 0;
            public static final String MENSAJE = ""
                    + ""
                    + ""
                    + ""
                    + "";
        }

        // ConfCarta #7
        public class Carta7 {
            public static final int DINERO = 0;
            public static final String MENSAJE = ""
                    + ""
                    + ""
                    + ""
                    + "";
        }

        // ConfCarta #8
        public class Carta8 {
            public static final int DINERO = 50;
            public static final String MENSAJE = ""
                    + "Alquilas a tus compañeros una villa en Cannes durante una semana.\n"
                    + "Paga " + DINERO + " " + Precios.MONEDA
                    + ""
                    + "";
        }

        // ConfCarta #9
        public class Carta9 {
            public static final int DINERO = 0;
            public static final String MENSAJE = ""
                    + ""
                    + ""
                    + ""
                    + "";
        }


        // ConfCarta #10
        public class Carta10 {
            public static final int DINERO = 0;
            public static final String MENSAJE = ""
                    + ""
                    + ""
                    + ""
                    + "";
        }

    }
    /*
     * CARTAS DE SUERTE
     *
     * */
    public class Suerte {
        // ConfCarta #1
        public class Carta1 {
            public static final int DINERO = Precios.SALIDA;
            public static final String MENSAJE = ""
                    + "Ve a Invierno a coger un catarro. Si pasas por la casilla de salida \n"
                    + "cobra " + DINERO + " " + Precios.MONEDA
                    + ""
                    + "";
        }

        // ConfCarta #2
        public class Carta2 {
            public static final int DINERO = 0;
            public static final String MENSAJE = ""
                    + "Ve a Lugo"
                    + ""
                    + "";
        }

        // ConfCarta #3
        public class Carta3 {
            public static final int DINERO = 55;
            public static final String MENSAJE = ""
                    + "Vendes tu billete de avión para Terrasa, recibe " + DINERO + " " + Precios.MONEDA
                    + ""
                    + ""
                    + "";
        }

        // ConfCarta #4
        public class Carta4 {
            public static final int DINERO = Precios.SALIDA;
            public static final String MENSAJE = ""
                    + "Ve a Salamanca. Si pasas por la casilla de salida cobra " + DINERO + " " + Precios.MONEDA
                    + ""
                    + ""
                    + "";
        }

        // ConfCarta #5
        public class Carta5 {
            public static final int DINERO = 0;
            public static final String MENSAJE = ""
                    + ""
                    + ""
                    + ""
                    + "";
        }

        // ConfCarta #6
        public class Carta6 {
            public static final int DINERO = 0;
            public static final String MENSAJE = ""
                    + ""
                    + ""
                    + ""
                    + "";
        }

        // ConfCarta #7
        public class Carta7 {
            public static final int DINERO = 0;
            public static final String MENSAJE = ""
                    + ""
                    + ""
                    + ""
                    + "";
        }

        // ConfCarta #8
        public class Carta8 {
            public static final int DINERO = 0;
            public static final int DINERO_CASA = 20;
            public static final int DINERO_HOTEL = 20;
            public static final int DINERO_PISCINA = 20;
            public static final int DINERO_PISTA_DEPORTE = 20;
            public static final String MENSAJE = ""
                    + "EL impuesto sobre bienes inmuebles afecta a tdas tus propiedades.\n"
                    + "Paga " + DINERO_CASA + " " + Precios.MONEDA + " por casa"
                    + ""
                    + ", " + DINERO_HOTEL + " " + Precios.MONEDA + " por hotel"
                    + ""
                    + ", " + DINERO_PISCINA + " " + Precios.MONEDA + " por piscina"
                    + ""
                    + ", " + DINERO_PISTA_DEPORTE + " " + Precios.MONEDA + " por pista de deportes."
                    + ""
                    + ""
                    + "";
        }

        // ConfCarta #9
        public class Carta9 {
            public static final int DINERO = 0;
            public static final String MENSAJE = ""
                    + ""
                    + ""
                    + ""
                    + "";
        }


        // ConfCarta #10
        public class Carta10 {
            public static final int DINERO = 150;
            public static final String MENSAJE = ""
                    + "Has sido elegido presidente de la junta directiva. Paga a cada jugador "
                    + DINERO + Precios.MONEDA
                    + ""
                    + "";
        }

        // ConfCarta #11
        public class Carta11 {
            public static final int DINERO = 0;
            public static final String MENSAJE = ""
                    + ""
                    + ""
                    + ""
                    + "";
        }

        // ConfCarta #12
        public class Carta12 {
            public static final int DINERO = 0;
            public static final String MENSAJE = ""
                    + ""
                    + ""
                    + ""
                    + "";
        }

        // ConfCarta #13
        public class Carta13 {
            public static final int DINERO = 0;
            public static final String MENSAJE = ""
                    + ""
                    + ""
                    + ""
                    + "";
        }


        // ConfCarta #14
        public class Carta14 {
            public static final int DINERO = 0;
            public static final String MENSAJE = ""
                    + ""
                    + ""
                    + ""
                    + "";
        }

    }
}
