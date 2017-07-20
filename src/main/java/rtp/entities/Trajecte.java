package rtp.entities;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public class Trajecte extends RTPentity {
    private static final Logger LOGGER = Logger.getLogger( TempsItinerari.class.getName() );
    private String linia_id;
    private String trajecte_id;

    public Trajecte(String val, String h) throws IOException{
        super(val, h);
        setValues(LOGGER, this.getClass());
    }

    public String getLinia_id() {
        return linia_id;
    }

    public String getTrajecte_id() {
        return trajecte_id;
    }
}
