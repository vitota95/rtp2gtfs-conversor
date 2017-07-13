package main.java.rtp.entities;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public class Parada extends RTPentity {

    private static final Logger LOGGER = Logger.getLogger( Parada.class.getName() );
    private static String header = null;

    public Parada(String val, String header) throws IOException{
        super(val);
        setValues(header, LOGGER, this.getClass());
        validate();
    }

    @Override
    void validate() {

    }
}
