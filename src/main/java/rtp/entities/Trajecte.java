package main.java.rtp.entities;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public class Trajecte extends RTPentity {
    private static final Logger LOGGER = Logger.getLogger( TempsItinerari.class.getName() );
    String linia_id;
    String trajecte_id;

    public Trajecte(String val, String h) throws IOException{
        super(val, h);
        setValues(LOGGER, this.getClass());
    }
}
