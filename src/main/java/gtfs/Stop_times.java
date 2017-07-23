package gtfs;

import rtp.RTPClassNames;
import rtp.entities.Expedicio;
import rtp.entities.Itinerari;
import rtp.entities.RTPentity;
import rtp.entities.TempsItinerari;

import java.io.IOException;
import java.util.List;

/**
 * Created by javig on 03/07/2017.
 */
public class Stop_times extends GTFSEntity {

    private static final List<String> header = GtfsCsvHeaders.CLASS_STOP_TIMES;
    private static final Stop_times_Values stop_times_values = new Stop_times_Values();

    @Override
    void getEntityParameters(String key, RTPentity value) throws IllegalAccessException {
        try {
            if (key.equalsIgnoreCase(RTPClassNames.CLASS_EXPEDICIO)) {
                Expedicio expedicio = (Expedicio) value;

                stop_times_values.trip_id = expedicio.getExpedicio_id();

            } else if (key.equalsIgnoreCase(RTPClassNames.CLASS_ITINERARI)) {
                Itinerari itinerari = (Itinerari) value;

                stop_times_values.stop_sequence = itinerari.getSequencia_id();
                stop_times_values.stop_id = itinerari.getParada_punt_id();
            } else if (key.equalsIgnoreCase(RTPClassNames.CLASS_TEMPS_ITINERARI)) {
                TempsItinerari tempsItinerari = (TempsItinerari) value;

                stop_times_values.arrival_time = tempsItinerari.getArrival_time();
                stop_times_values.departure_time = tempsItinerari.getDeparture_time();
            } else {
                throw new IOException("Routes unknown parameter: " + key);
            }
        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    @Override
    Object getGtfsValues() {
        return stop_times_values;
    }

    @Override
    List<String> getHeader() {
        return header;
    }
}

class Stop_times_Values {
    public String trip_id;
    public String arrival_time;
    public String departure_time;
    public String stop_id;
    public String stop_sequence;
    static final String stop_headsign = "";
    static final String pickup_type = "";
    static final String drop_off_type = "";
    static final String shape_dist_traveled = "";
}
