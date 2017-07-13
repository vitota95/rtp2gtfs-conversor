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

    public Itinerari(String val, String header) throws IOException {
        super(val);
        setValues(header, LOGGER, this.getClass());
        validate();
    }

    @Override
    void validate() {

    }
}
