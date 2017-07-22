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
    private RoutesParams params = new RoutesParams();
    private static final List<String> header = GtfsCsvHeaders.CLASS_ROUTES;

    @Override
    void getEntityParameters(String key, RTPentity value) throws IllegalAccessException {
        try {
            if (key.equalsIgnoreCase(RTPClassNames.CLASS_LINIA)) {
                Linia linia = (Linia) value;

                params.route_id = linia.getLinia_id();
                params.agency_id = linia.getOperador_id();
                params.route_short_name = linia.getLinia_nom_curt();
                params.route_long_name = linia.getLinia_nom_curt();
                params.route_desc = linia.getLinia_desc();
                setGtfsValues(params);
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

class RoutesParams
{
    public String route_id;
    public String route_short_name;
    public String route_long_name;
    public String route_desc;
    public String agency_id;
    public static final String route_type = "3";
    public static final String route_url = "";
    public static final String route_color = "";
    public static final String route_text_color = "";
}
