package main.java.rtp.entities;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public class Itinerari extends RTPentity {
    String linia_id;
    String trajecte_id;
    String direccio_id;
    String sequencia_id;
    String parada_punt_id;

    private static final Logger LOGGER = Logger.getLogger( Itinerari.class.getName() );
    private static String header = null;

    public Itinerari(String val, String h)  throws IOException{
        super(val, h);
        setValues(LOGGER, this.getClass());
    }

    @Override
    void validate() {

    }
}
