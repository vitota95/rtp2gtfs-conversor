package rtp.entities;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public class Restriccio extends RTPentity {
    private static final Logger LOGGER = Logger.getLogger( Restriccio.class.getName() );
    private String periode_id;
    private String restriccio_id;
    private String dies;

    public void setDies(String dies) {
        this.dies = dies;
    }

    public String getPeriode_id() {
        return periode_id;
    }

    public String getRestriccio_id() {
        return restriccio_id;
    }

    public String getDies() {
        return dies;
    }

    public Restriccio(String val, String h)  throws IOException{
        super(val, h);
        setValues(LOGGER, this.getClass());
    }
}
