package sample.config;

import java.util.Hashtable;

public class Guardian extends Configuracion {

    Hashtable <String,String> guardian=new Hashtable<>();

    protected String privilegio;
    protected String tabla;


    public Hashtable<String, String> getGuardian() {
        return guardian;
    }

    public void setGuardian(String llavePrimaria) {
        //guardian.put(guardian.put(llavePrimaria,guardian.put(pri,)));

        //// se pueden agregar los atributos que se deseen;
    }

}
