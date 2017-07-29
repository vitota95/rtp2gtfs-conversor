package rtp.entities;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public class Periode extends RTPentity {
    private static final Logger LOGGER = Logger.getLogger( Periode.class.getName() );
    private String periode_id;
    private String periode_dinici;

    public String getPeriode_id() {
        return periode_id;
    }

    public String getPeriode_dinici() {
        return periode_dinici;
    }

    public String getPeriode_dfi() {
        return periode_dfi;
    }

    private String periode_dfi;

    public Periode(String val, String h)  throws IOException{
        super(val, h);
        setValues(LOGGER);
    }
}
