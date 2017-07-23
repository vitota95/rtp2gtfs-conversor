package gtfs;

import rtp.RTPClassNames;
import rtp.entities.Expedicio;
import rtp.entities.RTPentity;

import java.io.IOException;
import java.util.List;

/**
 * Created by javig on 03/07/2017.
 */
public class Trips extends GTFSEntity {

    private static final List<String> header = GtfsCsvHeaders.CLASS_TRIPS;
    private final TripValues tripValues = new TripValues();

    @Override
    Object getGtfsValues() {
        return tripValues;
    }

    @Override
    void getEntityParameters(String key, RTPentity value) throws IllegalAccessException {
        try {
            switch (key) {
                case RTPClassNames.CLASS_EXPEDICIO:
                    Expedicio expedicio = (Expedicio) value;
                    tripValues.service_id = expedicio.getPeriode_id();
                    tripValues.trip_id = expedicio.getExpedicio_id();
                    tripValues.route_id = expedicio.getLinia_id();
                    //params.bikes_allowed = Integer.toString(expedicio.getBicicleta_SN().compareToIgnoreCase("S"));
                    tripValues.direction_id = (expedicio.getDireccio_id().equals("1")) ? "0" : "1";
                    // TODO set wheelchair accesible
                    break;
                default:
                    throw new IOException("Trips unknown parameter: " + key);
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    @Override
    List<String> getHeader() {
        return header;
    }

}

class TripValues {
    String service_id;
    String trip_id;
    String route_id;
    String direction_id;
    static final String wheelchair_accessible = "";
    static final String trip_headsign = "";
    static final String trip_short_name = "";
    static final String block_id = "";
    static final String shape_id = "";
}
