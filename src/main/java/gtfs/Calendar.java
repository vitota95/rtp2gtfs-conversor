package gtfs;

import rtp.RTPClassNames;
import rtp.entities.Periode;
import rtp.entities.RTPentity;
import rtp.entities.Restriccio;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by javig on 03/07/2017.
 */

public class Calendar extends GTFSEntity {

    private static final List<String> header = GtfsCsvHeaders.CLASS_CALENDAR;
    private final CalendarParams params = new CalendarParams();

    @Override
    Object getGtfsValues() {
        return null;
    }

    @Override
    void getEntityParameters(String key, RTPentity value) {
        try {
            switch (key) {
                case RTPClassNames.CLASS_PERIODE:
                    Periode periode = (Periode) value;
                    params.service_id = periode.getPeriode_id();
                    params.start_date = periode.getPeriode_dinici();
                    params.end_date = periode.getPeriode_dfi();
                    break;
                case RTPClassNames.CLASS_RESTRICCIO:
                    Restriccio restriccio = (Restriccio) value;

                    //get init week date of defined calendar
                    java.util.Calendar c = java.util.Calendar.getInstance();

                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                    try {
                        Date date = format.parse(params.start_date);
                        c.setTime(date);
                        int dayOfWeek = c.get(java.util.Calendar.DAY_OF_WEEK);
                        int firstMonday = (dayOfWeek == 0) ? 0 : (7 - dayOfWeek);

                        for (int i = firstMonday; i < restriccio.getDies().length(); i++) {

                            StringBuilder pattern1 = new StringBuilder();
                            StringBuilder pattern2 = new StringBuilder();

                            for (int j = i; i < j + 7; j++) {
                                pattern1.append(restriccio.getDies().charAt(j));
                            }

                            for (int j = i + 7; j < i + 14; j++) {
                                pattern2.append(restriccio.getDies().charAt(j));
                            }

                            if (pattern1.equals(pattern2)) {
                                //Change binary logic
                                params.monday = String.valueOf(pattern1.charAt(0) ^ 1);
                                params.tuesday = String.valueOf(pattern1.charAt(1) ^ 1);
                                params.wednesday = String.valueOf(pattern1.charAt(2) ^ 1);
                                params.thursday = String.valueOf(pattern1.charAt(3) ^ 1);
                                params.friday = String.valueOf(pattern1.charAt(4) ^ 1);
                                params.saturday = String.valueOf(pattern1.charAt(5) ^ 1);
                                params.sunday = String.valueOf(pattern1.charAt(6) ^ 1);
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                default:
                    throw new IOException("Agency unknown parameter: " + key);
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    @Override
    List<String> getHeader() {
        return header;
    }
}

class CalendarParams {
    public String service_id;
    public String start_date;
    public String end_date;
    public String monday;
    public String tuesday;
    public String wednesday;
    public String thursday;
    public String friday;
    public String saturday;
    public String sunday;
}
