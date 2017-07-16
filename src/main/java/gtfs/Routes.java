package gtfs;

/**
 * Created by javig on 03/07/2017.
 */
public class Routes extends Entity {

    String route_id;
    String route_short_name;
    String route_long_name;
    String route_desc;
    String agency_id;
    String route_type;
    static final String route_url = "";
    static final String route_color = "";
    static final String route_text_color = "";

    @Override
    void validate() {

    }
}
