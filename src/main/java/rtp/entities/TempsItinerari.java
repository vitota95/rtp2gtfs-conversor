package rtp.entities;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public class TempsItinerari extends RTPentity {
    private static final Logger LOGGER = Logger.getLogger( TempsItinerari.class.getName() );

    private String trajecte_id;
    private String linia_id;
    private String direccio_id;
    private String sequencia_id;
    private String temps_viatge;
    private String temps_parat;
    private String grup_horari_id;
    private String acumulatedTime = "0";
    private static final String timeFormat = "HH:mm:ss";
    private static final int SECONDS_IN_A_DAY = 86400;
    private static final int HOURS_IN_A_DAY = 24;


    public TempsItinerari(String val, String h) throws IOException{
        super(val, h);
        setValues(LOGGER);
    }

    public String getGrup_horari_id() {
        return grup_horari_id;
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

    public String getLinia_id() {
        return linia_id;
    }

    public String getAcummulatedTime() {
        return acumulatedTime;
    }

    public void setAccumulatedTime(String acumulatedTime) {
        this.acumulatedTime = acumulatedTime;
    }
}
