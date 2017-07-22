package gtfs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by javig on 21/07/2017.
 */
public interface GtfsCsvHeaders {
    String CLASS_AGENCY_CSV = "agency_id,agency_name,agency_url,agency_timezone,agency_lang,agency_phone,agency_fare_url";
    String CLASS_CALENDAR_CSV = "service_id,monday,tuesday,wednesday,thursday,friday,saturday,sunday,start_date,end_date";
    String CLASS_CALENDAR_DATES_CSV = "service_id,date,exception_type";
    String CLASS_ROUTES_CSV = "route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color";
    String CLASS_STOP_TIMES_CSV = "trip_id,arrival_time,departure_time,stop_id,stop_sequence,pickup_type,drop_off_type";
    String CLASS_STOPS_CSV = "stop_id,stop_name,stop_desc,stop_lat,stop_lon,stop_url,location_type,parent_station";
    String CLASS_TRIPS_CSV = "route_id,service_id,trip_id,trip_headsign,block_id,trip_short_name,direction_id,shape_id,wheelchair_accessible";

    List<String> CLASS_AGENCY = new ArrayList<>(Arrays.asList("agency_id", "agency_name", "agency_url", "agency_timezone", "agency_lang", "agency_phone", "agency_fare_url"));
    List<String> CLASS_CALENDAR = new ArrayList<>(Arrays.asList("service_id", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday", "start_date", "end_date"));
    List<String> CLASS_CALENDAR_DATES = new ArrayList<>(Arrays.asList("service_id", "date", "exception_type"));
    List<String> CLASS_ROUTES = new ArrayList<>(Arrays.asList("route_id", "agency_id", "route_short_name", "route_long_name", "route_desc", "route_type", "route_url", "route_color", "route_text_color"));
    List<String> CLASS_STOP_TIMES = new ArrayList<>(Arrays.asList("trip_id", "arrival_time", "departure_time", "stop_id", "stop_sequence", "pickup_type", "drop_off_type"));
    List<String> CLASS_STOPS = new ArrayList<>(Arrays.asList("stop_id,stop_name", "stop_desc", "stop_lat", "stop_lon", "stop_url", "location_type", "parent_station"));
    List<String> CLASS_TRIPS = new ArrayList<>(Arrays.asList("route_id", "service_id", "trip_id", "trip_headsign", "block_id", "trip_short_name", "direction_id", "shape_id", "wheelchair_accessible"));
}
