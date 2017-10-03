package sample.config;

import java.util.ArrayList;

public class Autentificacion extends Guardian {


    ArrayList<String> login = new ArrayList<>();

    static private int cantIntentos = 0;
    static final private int intentosPermitidos = 10;

    public Boolean intento() {
        if (getCantIntentos() < intentosPermitidos) {
            if (1 == 1) {
                resetIntento();
                return true;
            } else {
                sumarIntento();
                return false;
            }


        }
        return false;

    }

    private void resetIntento() {
        setCantIntentos(0);
    }

    public static int getCantIntentos() {
        return cantIntentos;
    }

    public static void setCantIntentos(int cantIntentos) {
        Autentificacion.cantIntentos = cantIntentos;
    }

    static private int sumarIntento() {
        setCantIntentos(getCantIntentos() + 1);
        return getCantIntentos();
    }

}
