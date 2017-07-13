package main.java.rtp.entities;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import main.java.rtp.CSVHeaders;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public class Operador extends RTPentity {
    private static final Logger LOGGER = Logger.getLogger( Operador.class.getName() );
    String operador_id;
    String operador_nom_complet_public;

    public Operador(String val, String h)  throws IOException{
        super(val, h);
        setValues(LOGGER, this.getClass());
    }

    @Override
    void validate() throws IOException {
        if (operador_id == null && operador_id.isEmpty() && operador_nom_complet_public == null
                && operador_nom_complet_public.isEmpty()){
            LOGGER.log(Level.SEVERE, "Some values are not present");
            throw new IOException();
        }
    }
}
