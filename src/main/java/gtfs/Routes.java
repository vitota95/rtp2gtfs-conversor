package gtfs;

import rtp.RTPClassNames;
import rtp.entities.Linia;
import rtp.entities.RTPentity;

import java.io.IOException;

/**
 * Created by javig on 03/07/2017.
 */
public class Routes extends GTFSEntity {

    private String route_id;
    private String route_short_name;
    private String route_long_name;
    private String route_desc;
    private String agency_id;
    private String route_type;
    private static final String route_url = "";
    private static final String route_color = "";
    private static final String route_text_color = "";

    @Override
    void getEntityParameters(String key, RTPentity value) {
        try {
            if (key.equalsIgnoreCase(RTPClassNames.CLASS_LINIA)) {
                Linia linia = (Linia) value;

                route_id = linia.getLinia_id();
                agency_id = linia.getOperador_id();
                route_short_name = linia.getLinia_nom_curt();
                route_long_name = linia.getLinia_nom_curt();
                route_desc = linia.getLinia_desc();
            } else {
                throw new IOException("Routes unknown parameter: " + key);
            }
        } catch (IOException io) {
            io.printStackTrace();
        }

    }
}
