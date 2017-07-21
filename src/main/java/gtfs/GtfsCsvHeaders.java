package gtfs;

/**
 * Created by javig on 21/07/2017.
 */
public interface GtfsCsvHeaders {
    String CLASS_AGENCY = "agency_id,agency_name,agency_url,agency_timezone,agency_lang,agency_phone,agency_fare_url";
    String CLASS_CALENDAR = "service_id,monday,tuesday,wednesday,thursday,friday,saturday,sunday,start_date,end_date";
    String CLASS_CALENDAR_DATES = "service_id,date,exception_type";
    String CLASS_ROUTES = "route_id,route_short_name,route_long_name,route_desc,route_type";
    String CLASS_STOP_TIMES= "trip_id,arrival_time,departure_time,stop_id,stop_sequence,pickup_type,drop_off_type";
    String CLASS_STOPS = "stop_id,stop_name,stop_desc,stop_lat,stop_lon,stop_url,location_type,parent_station";
    String CLASS_TRIPS = "route_id,service_id,trip_id,trip_headsign,block_id";
}
