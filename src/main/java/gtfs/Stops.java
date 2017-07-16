package gtfs;

import geotool.UTM2WGS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.TransformException;
import rtp.entities.Parada;
import rtp.entities.RTPentity;

import java.util.Map;

/**
 * Created by javig on 03/07/2017.
 */
public class Stops extends GTFSEntity {

    private String stop_id;
    private String stop_code;
    private String stop_name;
    static final String stop_desc = "";
    private String stop_lat;
    private String stop_lon;
    static final String zone_id = "";
    static final String stop_url = "";
    static final String location_type = "";
    static final String parent_station = "";
    static final String stop_timezone = "Europe/Madrid";
    static final String wheelchair_boarding = "";


    @Override
    void setValues(GTFSParameters gtfsParameters) {
        Map<String, RTPentity> objects = gtfsParameters.getRTPobjects();

        objects.forEach((String key, RTPentity value) -> {
            if (key.equalsIgnoreCase("Parada")) {
                final Parada parada = (Parada) value;
                stop_id = parada.getParada_punt_id();
                stop_code = parada.getParada_id();
                try {
                    double[] coordinates = UTM2WGS.transEd50Wgs84(Double.parseDouble(parada.getCoord_x()),
                            Double.parseDouble(parada.getCoord_y()));
                    stop_lat = String.valueOf(coordinates[0]);
                    stop_lon = String.valueOf(coordinates[1]);
                } catch (FactoryException | TransformException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
