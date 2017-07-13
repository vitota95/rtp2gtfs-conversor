package main.java.rtp.entities;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public class TempsItinerari extends RTPentity {
    private static final Logger LOGGER = Logger.getLogger( TempsItinerari.class.getName() );

    String trajecte_id;
    String direccio_id;
    String sequencia_id;
    String temps_viatge;
    String temps_parat;

    private static String header = null;

    public TempsItinerari(String val, String h) throws IOException{
        super(val, h);
        setValues(LOGGER, this.getClass());
    }

    @Override
    void validate() {

    }
}
