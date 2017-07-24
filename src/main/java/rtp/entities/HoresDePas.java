package rtp.entities;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by javig on 24/07/2017.
 */
public class HoresDePas extends RTPentity {

    private static final Logger LOGGER = Logger.getLogger( HoresDePas.class.getName() );

    public HoresDePas(String v, String h) throws IOException {
        super(v,h);
        setValues(LOGGER, this.getClass());
    }

    private String periode_id;
    private String linia_id;
    private String expedicio_id;
    private String sequencia_id;
    private String hora_de_pas;
    private String temps_parat;

    public String getPeriode_id() {
        return periode_id;
    }

    public String getLinia_id() {
        return linia_id;
    }

    public String getExpedicio_id() {
        return expedicio_id;
    }

    public String getSequencia_id() {
        return sequencia_id;
    }

    public String getHora_de_pas() {
        return hora_de_pas;
    }

    public String getTemps_parat() {
        return temps_parat;
    }
}
