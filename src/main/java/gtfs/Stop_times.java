package main.java.gtfs;

/**
 * Created by javig on 03/07/2017.
 */
public class Stop_times extends Entity {

    String trip_id;
    String arrival_time;
    String departure_time;
    String stop_id;
    String stop_sequence;
    static final String  stop_headsign = "";
    static final String pickup_type = "";
    static final String drop_off_type = "";
    static final String shape_dist_traveled = "";

    @Override
    void write() {

    }

    @Override
    void validate() {

    }
}
