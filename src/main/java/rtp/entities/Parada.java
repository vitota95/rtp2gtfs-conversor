package rtp.entities;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public class Parada extends RTPentity {
    private static final Logger LOGGER = Logger.getLogger( Parada.class.getName() );

    String parada_punt_id;
    String parada_punt_desc;
    String coord_x;
    String coord_y;

    public Parada(String val, String h)  throws IOException{
        super(val, h);
        setValues(LOGGER, this.getClass());
    }
}
