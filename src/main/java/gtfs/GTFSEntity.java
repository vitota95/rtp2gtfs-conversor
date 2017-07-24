package gtfs;

import rtp.entities.RTPentity;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by javig on 03/07/2017.
 */
abstract public class GTFSEntity {
    private static final Logger LOGGER = Logger.getLogger(GTFSEntity.class.getName());
    private static final String csvSeparator = ",";
    private Map<String, String> gtfsValues;

    public String getCsvString(GTFSParameters gtfsParameters) throws IllegalAccessException {
        setValues(gtfsParameters);
        return convertToCSV();
    }

    private String convertToCSV() {
        int len = gtfsValues.size();
        String[] toCsv = new String[len];

        gtfsValues.forEach((key, value) -> {
            int index = this.getHeader().indexOf(key);
            try {
                toCsv[index] = value;
            } catch (IndexOutOfBoundsException iob) {
                LOGGER.log(Level.SEVERE, "This key was not found: " + key);
                iob.printStackTrace();
            }

        });

        return String.join(csvSeparator, toCsv);
    }

    private void setValues(GTFSParameters gtfsParameters) throws IllegalAccessException {
        Map<String, RTPentity> objects = gtfsParameters.getRTPobjects();
        objects.forEach((key, value) -> {
            try {
                getEntityParameters(key, value);
            } catch (IllegalAccessException e) {
                LOGGER.log(Level.SEVERE, e.getMessage());
                e.printStackTrace();
            }
        });
        setGtfsValues(this.getGtfsValues());
    }

    void setGtfsValues(Object params) throws IllegalAccessException {
        Field[] fields = params.getClass().getDeclaredFields();
        gtfsValues = new HashMap<>();

        for (Field field : fields) {
            field.setAccessible(true);
            this.gtfsValues.put(field.getName(), (String) field.get(params));
        }
    }

    abstract void getEntityParameters(String key, RTPentity value) throws IllegalAccessException;
    abstract List<String> getHeader();

    abstract Object getGtfsValues();
}

