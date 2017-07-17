package gtfs;

import rtp.RTPClassNames;
import rtp.entities.Expedicio;
import rtp.entities.RTPentity;

import java.io.IOException;

/**
 * Created by javig on 03/07/2017.
 */
public class Trips extends GTFSEntity {

    String service_id;
    String trip_id;
    String route_id;
    String direction_id;
    String bikes_allowed;
    String wheelchair_accesible;
    static final String trip_headsign = "";
    static final String trip_shortname = "";
    static final String block_id = "";
    static final String shape_id = "";


    @Override
    void getEntityParameters(String key, RTPentity value) {
        try {
            switch (key) {
                case RTPClassNames.CLASS_EXPEDICIO:
                    Expedicio expedicio = (Expedicio) value;
                    trip_id = expedicio.getExpedicio_id();
                    route_id = expedicio.getLinia_id();
                    direction_id = expedicio.getDireccio_id();
                    bikes_allowed = Integer.toString(expedicio.getBicicleta_SN().compareToIgnoreCase("S"));
                    // TODO set wheelchair accesible
                    break;
                default:
                    throw new IOException("Agency unknown parameter: " + key);
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
