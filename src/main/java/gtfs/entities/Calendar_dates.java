package gtfs.entities;

import gtfs.GtfsCsvHeaders;
import rtp.entities.RTPentity;

import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public class Calendar_dates extends GTFSEntity {
    private static final Logger LOGGER = Logger.getLogger(Calendar_dates.class.getName());
    private static final List<String> header = GtfsCsvHeaders.CLASS_CALENDAR_DATES;
    private CalendarDatesValues values = new CalendarDatesValues();

    @Override
    void getEntityParameters(String key, RTPentity value) {
    }

    public void setService_id(String service_id) {
        values.service_id = service_id;
    }

    public void setDate(String date) {
        values.date = date;
    }

    public String toCSV() throws IllegalAccessException {
        String[] toCsv = new String[3];
        Field[] fields = values.getClass().getDeclaredFields();

        for (Field f : fields) {
            f.setAccessible(true);
            int index = this.getHeader().indexOf(f.getName());
            toCsv[index] = (String) f.get(values);
        }

        return String.join(CSVSEPARATOR, toCsv);
    }

    @Override
    Object getGtfsValues() {
        return values;
    }

    @Override
    List<String> getHeader() {
        return header;
    }
}

class CalendarDatesValues {
    String service_id;
    String date;
    static final String exception_type = "2";
}
