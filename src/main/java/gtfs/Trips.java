package gtfs;

/**
 * Created by javig on 03/07/2017.
 */
public class Trips extends Entity {

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
    void validate() {

    }
}
