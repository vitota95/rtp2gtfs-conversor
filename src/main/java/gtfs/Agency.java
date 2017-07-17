package gtfs;

import rtp.RTPClassNames;
import rtp.entities.Operador;
import rtp.entities.RTPentity;

import java.io.IOException;

/**
 * Created by javig on 03/07/2017.
 */
public class Agency extends GTFSEntity {

    public String agency_name;
    public String agency_id;
    static final String agency_url = "https://www.changeURL.notreal";
    static final String agency_timezone = "Europe/Madrid";
    static final String agency_phone = "";
    static final String agency_fare_url = "";

    @Override
    void getEntityParameters(String key, RTPentity value) {
        try {
            if (key.equalsIgnoreCase(RTPClassNames.CLASS_OPERADOR)) {
                final Operador operador = (Operador) value;
                agency_id = operador.getOperador_id();
                agency_name = operador.getOperador_nom_complet_public();
            } else {
                throw new IOException("Agency unknown parameter: " + key);
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
