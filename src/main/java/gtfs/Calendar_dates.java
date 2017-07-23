package gtfs;

import rtp.entities.RTPentity;

import java.util.List;

/**
 * Created by javig on 03/07/2017.
 */
public class Calendar_dates extends GTFSEntity {

    private static final List<String> header = GtfsCsvHeaders.CLASS_CALENDAR_DATES;
    private String service_id;
    private String date;
    private String exception_type;

    @Override
    void getEntityParameters(String key, RTPentity value) {

    }

    @Override
    Object getGtfsValues() {
        return null;
    }

    @Override
    List<String> getHeader() {
        return header;
    }
}
