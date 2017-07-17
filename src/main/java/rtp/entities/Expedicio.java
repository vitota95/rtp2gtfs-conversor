package rtp.entities;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */

public class Expedicio extends RTPentity {
    private static final Logger LOGGER = Logger.getLogger( Expedicio.class.getName() );

    private String expedicio_id;
    private String periode_id;
    private String linia_id;
    private String trajecte_id;
    private String direccio_id;
    private String tipus_vehicle_id;
    private String grup_horari_id;
    private String bicicleta_SN;

    public String getBicicleta_SN() {
        return bicicleta_SN;
    }

    public String getExpedicio_id() {
        return expedicio_id;
    }

    public String getPeriode_id() {
        return periode_id;
    }

    public String getLinia_id() {
        return linia_id;
    }

    public String getTrajecte_id() {
        return trajecte_id;
    }

    public String getDireccio_id() {
        return direccio_id;
    }

    public String getTipus_vehicle_id() {
        return tipus_vehicle_id;
    }

    public String getGrup_horari_id() {
        return grup_horari_id;
    }

    public Expedicio(String val, String h) throws IOException{
        super(val, h);
        setValues(LOGGER, this.getClass());
    }
}
