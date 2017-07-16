package gtfs;

/**
 * Created by javig on 03/07/2017.
 */
public class Stops extends Entity {

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

    @Override
    void validate() {

    }
}
