package gtfs;

/**
 * Created by javig on 03/07/2017.
 */

public class Calendar extends GTFSEntity {

    String service_id;
    String start_date;
    String end_date;
    String monday;
    String tuesday;
    String wednesday;
    String thursday;
    String friday;
    String saturday;
    String sunday;


    @Override
    void setValues(GTFSParameters gtfsParameters) {

    }
}
