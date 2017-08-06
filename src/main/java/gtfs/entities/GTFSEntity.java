package gtfs.entities;

import gtfs.GTFSParameters;
import rtp.entities.RTPentity;

import java.lang.reflect.Field;
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
    static final String CSVSEPARATOR = ",";
    private Map<String, String> gtfsValues;

    /**
     * Function to get the csv of a gtfs entity, it first set the entity values
     *
     * @param gtfsParameters map of parameters needed to get the entity
     * @return csv string
     * @throws IllegalAccessException throwed in setValues
     */

    public String getCsvString(GTFSParameters gtfsParameters) throws IllegalAccessException {
        setValues(gtfsParameters);
        return convertToCSV();
    }

    /**
     * Function that converts the entity values to csv
     *
     * @return csv string
     */

    String convertToCSV() {
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

        return String.join(CSVSEPARATOR, toCsv);
    }

    /**
     * Given a map with the class name of an RTP file and a RTP entity,
     * it sets the parameters of ech entity.
     *
     * @param gtfsParameters map with rtp entities
     * @throws IllegalAccessException
     */

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

    /**
     * Method to set GTFS entity values by reflection
     *
     * @param values object of GTFS values
     * @throws IllegalAccessException
     */

    void setGtfsValues(Object values) throws IllegalAccessException {
        Field[] fields = values.getClass().getDeclaredFields();
        gtfsValues = new HashMap<>();

        for (Field field : fields) {
            field.setAccessible(true);
            this.gtfsValues.put(field.getName(), (String) field.get(values));
        }
    }

    /**
     * Method that will be overriden by each GTFS entity
     *
     * @param key   RTP class name
     * @param value RTP entity object
     * @throws IllegalAccessException
     */
    abstract void getEntityParameters(String key, RTPentity value) throws IllegalAccessException;
    abstract List<String> getHeader();

    abstract Object getGtfsValues();
}

