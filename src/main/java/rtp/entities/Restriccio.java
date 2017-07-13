package main.java.rtp.entities;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public class Restriccio extends RTPentity {
    private static final Logger LOGGER = Logger.getLogger( Restriccio.class.getName() );
    String periode_id;
    String restriccio_id;
    String dies;

    public Restriccio(String val, String h)  throws IOException{
        super(val, h);
        setValues(LOGGER, this.getClass());
    }

    @Override
    void validate() {

    }
}
