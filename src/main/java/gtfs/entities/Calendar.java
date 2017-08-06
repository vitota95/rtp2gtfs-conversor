package gtfs.entities;

import gtfs.GtfsCsvHeaders;
import rtp.RTPClassNames;
import rtp.entities.Periode;
import rtp.entities.RTPentity;
import rtp.entities.TipusDia2DiaAtribut;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */

public class Calendar extends GTFSEntity {

    private static final List<String> header = GtfsCsvHeaders.CLASS_CALENDAR;
    private final CalendarParams calendarValues = new CalendarParams();
    private static final Logger LOGGER = Logger.getLogger(Calendar.class.getName());

    @Override
    Object getGtfsValues() {
        return calendarValues;
    }

    private void getEntityParameters(String key, RTPentity[] values) {
        try {
            switch (key) {
                case RTPClassNames.CLASS_TIPUS_DIA_2_DIA_ATRIBUT:
                    for (RTPentity rtPentity : values) {
                        TipusDia2DiaAtribut td = (TipusDia2DiaAtribut) rtPentity;
                        if (calendarValues.service_id == null) {
                            calendarValues.service_id = td.getDia_atribut_id();
                        }
                        switch (td.getTipus_dia_id()) {
                            case "1":
                                calendarValues.monday = "1";
                                break;
                            case "2":
                                calendarValues.tuesday = "1";
                                break;
                            case "3":
                                calendarValues.wednesday = "1";
                                break;
                            case "4":
                                calendarValues.thursday = "1";
                                break;
                            case "5":
                                calendarValues.friday = "1";
                                break;
                            case "6":
                                calendarValues.saturday = "1";
                                break;
                            case "7":
                                calendarValues.sunday = "1";
                                break;
                            default:
                                LOGGER.log(Level.WARNING, "Unknown day index: " + td.getTipus_dia_id());
                                break;
                        }
                    }
                    break;
                case RTPClassNames.CLASS_PERIODE:
                    Periode periode = (Periode) values[0];
                    calendarValues.start_date = periode.getPeriode_dinici();
                    calendarValues.end_date = periode.getPeriode_dfi();
                    break;
                default:
                    throw new IOException("Calendar unknown parameter: " + key);
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public String getCsvString(Map<String, RTPentity[]> map) throws IllegalAccessException {
        map.forEach(this::getEntityParameters);
        setGtfsValues(this.getGtfsValues());
        return this.convertToCSV();
    }

    @Override
    void getEntityParameters(String key, RTPentity value) throws IllegalAccessException {

    }

    @Override
    List<String> getHeader() {
        return header;
    }
}

class CalendarParams {
    String service_id = null;
    String start_date;
    String end_date;
    String monday = "0";
    String tuesday = "0";
    String wednesday = "0";
    String thursday = "0";
    String friday = "0";
    String saturday = "0";
    String sunday = "0";
}

//                    calendarValues.sunday = String.valueOf(Character.getNumericValue(restriccio.getDies().charAt(0)) ^ 1);
//                            calendarValues.monday = String.valueOf(Character.getNumericValue(restriccio.getDies().charAt(1)) ^ 1);
//                            calendarValues.tuesday = String.valueOf(Character.getNumericValue(restriccio.getDies().charAt(2)) ^ 1);
//                            calendarValues.wednesday = String.valueOf(Character.getNumericValue(restriccio.getDies().charAt(3)) ^ 1);
//                            calendarValues.thursday = String.valueOf(Character.getNumericValue(restriccio.getDies().charAt(4)) ^ 1);
//                            calendarValues.friday = String.valueOf(Character.getNumericValue(restriccio.getDies().charAt(5)) ^ 1);
//                            calendarValues.saturday = String.valueOf(Character.getNumericValue(restriccio.getDies().charAt(6)) ^ 1);
