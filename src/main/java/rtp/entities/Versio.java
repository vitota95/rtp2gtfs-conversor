package rtp.entities;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public class Versio extends RTPentity {

    private static final Logger LOGGER = Logger.getLogger( Versio.class.getName() );
    private String data;

    public Versio(String val, String h) throws IOException{
        super(val, h);
        setValues(LOGGER);
    }

    public String getData() {
        return data;
    }
}
