package gtfs;

import rtp.RTPClassNames;
import rtp.entities.Periode;
import rtp.entities.RTPentity;
import rtp.entities.Restriccio;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by javig on 03/07/2017.
 */

public class Calendar extends GTFSEntity {

    private String service_id;
    private String start_date;
    private String end_date;
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;
    private String sunday;

    Calendar(String header){
        super(header);
    }


    @Override
    void getEntityParameters(String key, RTPentity value) {
        try {
            switch (key) {
                case RTPClassNames.CLASS_PERIODE:
                    Periode periode = (Periode) value;
                    service_id = periode.getPeriode_id();
                    start_date = periode.getPeriode_dinici();
                    end_date = periode.getPeriode_dfi();
                    break;
                case RTPClassNames.CLASS_RESTRICCIO:
                    Restriccio restriccio = (Restriccio) value;

                    //get init week date of defined calendar
                    java.util.Calendar c = java.util.Calendar.getInstance();

                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
                    try {
                        Date date = format.parse(start_date);
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
                                monday = String.valueOf(pattern1.charAt(0) ^ 1);
                                tuesday = String.valueOf(pattern1.charAt(1) ^ 1);
                                wednesday = String.valueOf(pattern1.charAt(2) ^ 1);
                                thursday = String.valueOf(pattern1.charAt(3) ^ 1);
                                friday = String.valueOf(pattern1.charAt(4) ^ 1);
                                saturday = String.valueOf(pattern1.charAt(5) ^ 1);
                                sunday = String.valueOf(pattern1.charAt(6) ^ 1);
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
}
