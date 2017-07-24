package gtfs;

import rtp.RTPClassNames;
import rtp.entities.Linia;
import rtp.entities.RTPentity;

import java.io.IOException;
import java.util.List;

/**
 * Created by javig on 03/07/2017.
 */
public class Routes extends GTFSEntity {
    private RoutesValues routesValues = new RoutesValues();
    private static final List<String> header = GtfsCsvHeaders.CLASS_ROUTES;

    @Override
    Object getGtfsValues() {
        return routesValues;
    }

    @Override
    void getEntityParameters(String key, RTPentity value) throws IllegalAccessException {
        try {
            if (key.equalsIgnoreCase(RTPClassNames.CLASS_LINIA)) {
                Linia linia = (Linia) value;

                routesValues.route_id = linia.getLinia_id();
                routesValues.agency_id = linia.getOperador_id();
                routesValues.route_short_name = linia.getLinia_nom_curt();
                routesValues.route_long_name = linia.getLinia_desc();
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

class RoutesValues
{
    public String route_id;
    public String route_short_name;
    public String route_long_name;
    public String agency_id;
    public static final String route_desc="";
    public static final String route_type = "3";
    public static final String route_url = "";
    public static final String route_color = "";
    public static final String route_text_color = "";
}
