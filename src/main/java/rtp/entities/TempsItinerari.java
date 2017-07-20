package rtp.entities;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public class TempsItinerari extends RTPentity {
    private static final Logger LOGGER = Logger.getLogger( TempsItinerari.class.getName() );

    private String trajecte_id;
    private String direccio_id;
    private String sequencia_id;
    private String temps_viatge;
    private String temps_parat;

    public TempsItinerari(String val, String h) throws IOException{
        super(val, h);
        setValues(LOGGER, this.getClass());
    }

    public String getTrajecte_id() {
        return trajecte_id;
    }

    public String getDireccio_id() {
        return direccio_id;
    }

    public String getSequencia_id() {
        return sequencia_id;
    }

    public String getTemps_viatge() {
        return temps_viatge;
    }

    public String getTemps_parat() {
        return temps_parat;
    }
}
