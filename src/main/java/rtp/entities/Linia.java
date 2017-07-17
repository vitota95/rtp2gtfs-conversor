package rtp.entities;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public class Linia extends RTPentity {

    private static final Logger LOGGER = Logger.getLogger( Linia.class.getName() );

    private String linia_id;
    private String linia_nom_curt;
    private String operador_id;
    private String linia_desc;

    public String getLinia_desc() {
        return linia_desc;
    }

    public Linia(String val, String h)  throws IOException{
        super(val, h);
        setValues(LOGGER, this.getClass());
    }

    public String getLinia_id() {
        return linia_id;
    }

    public String getLinia_nom_curt() {
        return linia_nom_curt;
    }

    public String getOperador_id() {
        return operador_id;
    }
}
