package rtp.entities;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by javig on 19/09/2017.
 */
public class NomCurt extends RTPentity {

    private static final Logger LOGGER = Logger.getLogger( NomCurt.class.getName() );
    public String linia_nom_curt;
    public String linia_desc;

    public String getLinia_nom_curt() {
        return linia_nom_curt;
    }

    public void setLinia_nom_curt(String linia_nom_curt) {
        this.linia_nom_curt = linia_nom_curt;
    }

    public String getLinia_desc() {
        return linia_desc;
    }

    public void setLinia_desc(String linia_desc) {
        this.linia_desc = linia_desc;
    }

    /**
     * @param v csv string of the RTP file
     * @param h header of the RTP file
     */
    public NomCurt(String v, String h) throws IOException {
        super(v, h);
        setValues(LOGGER);
    }

}
