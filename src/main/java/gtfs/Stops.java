package gtfs;

import geotool.UTM2WGS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;
import rtp.RTPClassNames;
import rtp.entities.Parada;
import rtp.entities.RTPentity;

import java.io.IOException;
import java.util.List;

/**
 * Created by javig on 03/07/2017.
 */
public class Stops extends GTFSEntity {

    private static final List<String> header = GtfsCsvHeaders.CLASS_STOPS;
    private final StopParams params = new StopParams();

    @Override
    void getEntityParameters(String key, RTPentity value) throws IllegalAccessException {
        try {
            if (key.equalsIgnoreCase(RTPClassNames.CLASS_PARADA)) {
                final Parada parada = (Parada) value;
                params.stop_id = parada.getParada_punt_id();
                params.stop_code = parada.getParada_id();
                params.stop_name = parada.getParada_punt_desc_curta();
                try {
                    double[] coordinates = UTM2WGS.transEd50Wgs84(Double.parseDouble(parada.getCoord_x()),
                            Double.parseDouble(parada.getCoord_y()));
                    params.stop_lat = String.valueOf(coordinates[0]);
                    params.stop_lon = String.valueOf(coordinates[1]);
                    setGtfsValues(params);
                } catch (FactoryException | TransformException e) {
                    e.printStackTrace();
                }
            } else {
                throw new IOException("Routes unknown parameter: " + key);
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

class StopParams {
    String stop_id;
    String stop_code;
    String stop_name;
    static final String stop_desc = "";
    String stop_lat;
    String stop_lon;
    static final String zone_id = "";
    static final String stop_url = "";
    static final String location_type = "";
    static final String parent_station = "";
    static final String stop_timezone = "Europe/Madrid";
    static final String wheelchair_boarding = "";
}
