package gtfs;

import rtp.entities.RTPentity;

/**
 * Created by javig on 03/07/2017.
 */
public class Calendar_dates extends GTFSEntity {

    private String service_id;
    private String date;
    private String exception_type;

    @Override
    void getEntityParameters(String key, RTPentity value) {

    }
}
