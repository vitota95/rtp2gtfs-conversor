package gtfs;

import rtp.RTPClassNames;
import rtp.entities.*;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by javig on 03/07/2017.
 */
public class Stop_times extends GTFSEntity {

    private static final List<String> header = GtfsCsvHeaders.CLASS_STOP_TIMES;
    private static final Stop_times_Values stop_times_values = new Stop_times_Values();
    private static final String timeFormat = "HH:mm:ss";
    private static final int SECONDS_IN_A_DAY = 86400;
    private static final int HOURS_IN_A_DAY = 24;

    @Override
    void getEntityParameters(String key, RTPentity value) throws IllegalAccessException {
        try {
            if (key.equalsIgnoreCase(RTPClassNames.CLASS_EXPEDICIO)) {
                Expedicio expedicio = (Expedicio) value;

                stop_times_values.trip_id = expedicio.getExpedicio_id();

            } else if (key.equalsIgnoreCase(RTPClassNames.CLASS_ITINERARI)) {
                Itinerari itinerari = (Itinerari) value;

                stop_times_values.stop_sequence = itinerari.getSequencia_id();
                stop_times_values.stop_id = itinerari.getParada_punt_id();
            } else if (key.equalsIgnoreCase(RTPClassNames.CLASS_TEMPS_ITINERARI)) {
                TempsItinerari tempsItinerari = (TempsItinerari) value;

                setArrivalTime(tempsItinerari.getTemps_viatge());
                setDeparture_time(tempsItinerari.getTemps_parat(), tempsItinerari.getTemps_viatge());
            }
            else if (key.equalsIgnoreCase(RTPClassNames.CLASS_HORES_DE_PAS)){
                HoresDePas horaDePas = (HoresDePas) value;

                setArrivalTime(horaDePas.getHora_de_pas());
                setDeparture_time(horaDePas.getTemps_parat(), horaDePas.getHora_de_pas());
            }
            else {
                throw new IOException("Routes unknown parameter: " + key);
            }
        } catch (IOException io) {
            io.printStackTrace();
        }

    }

    @Override
    Object getGtfsValues() {
        return stop_times_values;
    }

    @Override
    List<String> getHeader() {
        return header;
    }

    /**
     *
     * @param time
     */
    private void setArrivalTime(String time) {
        int secondsFromPreviousStation = Integer.parseInt(time);

        if (TimeUnit.SECONDS.toDays(secondsFromPreviousStation) > 0) {
            int remanent = secondsFromPreviousStation - SECONDS_IN_A_DAY;
            LocalTime timeOfDay = LocalTime.ofSecondOfDay(remanent);
            stop_times_values.arrival_time = String.format("%02d:%02d:%02d",
                    timeOfDay.getHour() + HOURS_IN_A_DAY,
                    timeOfDay.getMinute(),
                    timeOfDay.getSecond());
        } else {
            LocalTime timeOfDay = LocalTime.ofSecondOfDay(secondsFromPreviousStation);
            stop_times_values.arrival_time = timeOfDay.format(DateTimeFormatter.ofPattern(timeFormat));
        }
    }

    public void setDeparture_time(String timeStoped, String travel){
        LocalTime timeOfDay;
        int timeInStop = Integer.parseInt(timeStoped);
        int timeTravel = Integer.parseInt(travel);
        int times = timeInStop + timeTravel;

        if (TimeUnit.SECONDS.toDays(times) > 0) {
            int remanent = times - SECONDS_IN_A_DAY;
            timeOfDay = LocalTime.ofSecondOfDay(remanent);
            stop_times_values.departure_time = String.format("%02d:%02d:%02d",
                    timeOfDay.getHour() + HOURS_IN_A_DAY,
                    timeOfDay.getMinute(),
                    timeOfDay.getSecond());
        } else {
            timeOfDay = LocalTime.ofSecondOfDay(timeTravel);
            stop_times_values.departure_time = timeOfDay.format(DateTimeFormatter.ofPattern(timeFormat));
        }
    }
}

class Stop_times_Values {
    public String trip_id;
    public String arrival_time;
    public String departure_time;
    public String stop_id;
    public String stop_sequence;
    static final String stop_headsign = "";
    static final String pickup_type = "";
    static final String drop_off_type = "";
    static final String shape_dist_traveled = "";
}
