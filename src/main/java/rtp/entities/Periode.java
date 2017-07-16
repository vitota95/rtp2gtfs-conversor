package rtp.entities;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public class Periode extends RTPentity {
    private static final Logger LOGGER = Logger.getLogger( Periode.class.getName() );
    String periode_id;
    String periode_dinici;
    String periode_dfi;

    public Periode(String val, String h)  throws IOException{
        super(val, h);
        setValues(LOGGER, this.getClass());
    }
}
