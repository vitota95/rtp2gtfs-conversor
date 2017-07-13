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
    private String operador_id;
    private String operador_nom_complet;
    private static String header = null;

    public Operador(String val, String header) throws IOException{
        super(val);
        setValues(header, LOGGER, this.getClass());
        validate();
    }

    @Override
    void validate() throws IOException {
    }
}
