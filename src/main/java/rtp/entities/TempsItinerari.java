package main.java.rtp.entities;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public class TempsItinerari extends RTPentity {
    String trajecte_id;
    String direccio_id;
    String sequencia_id;
    String temps_viatge;
    String temps_parat;

    private static final Logger LOGGER = Logger.getLogger( TempsItinerari.class.getName() );
    private static String header = null;

    public TempsItinerari(String val, String header) throws IOException {
        super(val);
        setValues(header, LOGGER, this.getClass());
        validate();
    }

    @Override
    void validate() {

    }
}
