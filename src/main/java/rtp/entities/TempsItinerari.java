package rtp.entities;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public class TempsItinerari extends RTPentity {
    private static final Logger LOGGER = Logger.getLogger( TempsItinerari.class.getName() );

    private String trajecte_id;
    private String linia_id;
    private String direccio_id;
    private String sequencia_id;
    private String temps_viatge;
    private String temps_parat;
    private String grup_horari_id;
    private String arrival_time = null;
    private String departure_time;
    private static final String timeFormat = "HH:mm:ss";
    private static final int SECONDS_IN_A_DAY = 86400;
    private static final int HOURS_IN_A_DAY = 24;


    public TempsItinerari(String val, String h) throws IOException{
        super(val, h);
        setValues(LOGGER, this.getClass());
    }

    public String getGrup_horari_id() {
        return grup_horari_id;
    }

    public String getTrajecte_id() {
        return trajecte_id;
    }

    public String getDireccio_id() {
        return direccio_id;
    }

    public String getSequencia_id() {
        return sequencia_id;
    }

    public String getTemps_viatge() {
        return temps_viatge;
    }

    public String getTemps_parat() {
        return temps_parat;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public String getLinia_id() {
        return linia_id;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setArrival_time(int timeToNextStop) throws ParseException {

        if (TimeUnit.SECONDS.toDays(timeToNextStop) > 0) {
            int remanent = timeToNextStop - SECONDS_IN_A_DAY;
            LocalTime timeOfDay = LocalTime.ofSecondOfDay(remanent);
            arrival_time = String.format("%02d:%02d:%02d",
                    timeOfDay.getHour() + HOURS_IN_A_DAY,
                    timeOfDay.getMinute(),
                    timeOfDay.getSecond());
        } else {
            LocalTime timeOfDay = LocalTime.ofSecondOfDay(timeToNextStop);
            arrival_time = timeOfDay.format(DateTimeFormatter.ofPattern(timeFormat));
        }
    }

    public void setDeparture_time(int timeInStop) throws ParseException {
        LocalTime timeOfDay;

        if (TimeUnit.SECONDS.toDays(timeInStop) > 0) {
            int remanent = timeInStop - SECONDS_IN_A_DAY;
            timeOfDay = LocalTime.ofSecondOfDay(remanent);
            departure_time = String.format("%02d:%02d:%02d",
                    timeOfDay.getHour() + HOURS_IN_A_DAY,
                    timeOfDay.getMinute(),
                    timeOfDay.getSecond());
        } else {
            timeOfDay = LocalTime.ofSecondOfDay(timeInStop);
            departure_time = timeOfDay.format(DateTimeFormatter.ofPattern(timeFormat));
        }
    }
}
