package rtp.entities;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public class Itinerari extends RTPentity {
    private String linia_id;
    private String trajecte_id;
    private String direccio_id;
    private String sequencia_id;
    private String parada_punt_id;

    public String getLinia_id() {
        return linia_id;
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

    public void setSequencia_id(String sequencia_id) {
        this.sequencia_id = sequencia_id;
    }

    public String getParada_punt_id() {
        return parada_punt_id;
    }

    private static final Logger LOGGER = Logger.getLogger( Itinerari.class.getName() );

    public Itinerari(String val, String h)  throws IOException{
        super(val, h);
        setValues(LOGGER);
    }
}
