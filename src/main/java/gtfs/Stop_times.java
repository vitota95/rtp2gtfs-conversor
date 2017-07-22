package gtfs;

import rtp.entities.RTPentity;

import java.util.List;

/**
 * Created by javig on 03/07/2017.
 */
public class Stop_times extends GTFSEntity {

    private static final List<String> header = GtfsCsvHeaders.CLASS_STOP_TIMES;
    private String trip_id;
    private String arrival_time;
    private String departure_time;
    private String stop_id;
    private String stop_sequence;
    static final String  stop_headsign = "";
    static final String pickup_type = "";
    static final String drop_off_type = "";
    static final String shape_dist_traveled = "";

    @Override
    void getEntityParameters(String key, RTPentity value) {

    }

    @Override
    List<String> getHeader() {
        return header;
    }
}
