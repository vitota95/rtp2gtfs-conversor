package main.java.rtp.entities;

import main.java.rtp.CSVHeaders;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public class Linia extends RTPentity {

    private static final Logger LOGGER = Logger.getLogger( Linia.class.getName() );

    public String linia_id;
    public String linia_nom_curt;
    public String operador_id;
    private static String header;

    public Linia(String val, String header) throws IOException{
        super(val);
        setValues(header, LOGGER, this.getClass());
        validate();
    }

    @Override
    void validate() throws IOException {
       if (checkEmpty(operador_id) || checkEmpty(linia_id)
               || checkEmpty(linia_nom_curt)){
           //throw new IOException();
       }
    }
}
