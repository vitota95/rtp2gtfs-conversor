package gtfs;

import rtp.entities.RTPentity;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by javig on 03/07/2017.
 */
abstract public class GTFSEntity {
    private static final Logger LOGGER = Logger.getLogger(GTFSEntity.class.getName());
    private static final String csvSeparator = ",";
    private String header;
    Map<String,String> gtfsValues;

    GTFSEntity(String h) {
        header = h;
    }

    private String convertToCSV() {
        int i = 0;
        int len = gtfsValues.size();
        final List headerArray = Arrays.asList(header.split(csvSeparator));
        String[] toCsv = new String[len];

        gtfsValues.forEach((key, value) -> {
            int index = headerArray.indexOf(key);
            toCsv[index] = value;
        });

        return String.join(csvSeparator, toCsv);
    }

    public String getCsvString(GTFSParameters gtfsParameters) throws IllegalAccessException {
        setValues(gtfsParameters);
        return convertToCSV();
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
}

