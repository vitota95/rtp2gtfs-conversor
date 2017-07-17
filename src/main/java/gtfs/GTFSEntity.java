package gtfs;

import rtp.entities.RTPentity;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by javig on 03/07/2017.
 */
abstract public class GTFSEntity {
    private static final Logger LOGGER = Logger.getLogger(GTFSEntity.class.getName());

    private static final String csvSeparator = ";";

    public String convertToCSV(Class c) {

        Field [] fields = c.getDeclaredFields();
        String csvString = "";

        int i = 0;
        int len = fields.length - 1;
        for (Field f: fields){
            try {
                if (i == len)
                    csvString += f.get(this);
                else
                    csvString += f.get(this) + csvSeparator;
            } catch ( IllegalAccessException ex ) {
                LOGGER.log(Level.SEVERE, "Illegal access " + c.getName());
            }
            ++i;
        }
        return csvString;
    }

    void setValues(GTFSParameters gtfsParameters) {
        Map<String, RTPentity> objects = gtfsParameters.getRTPobjects();
        objects.forEach(this::getEntityParameters);
    }

    abstract void getEntityParameters(String key, RTPentity value);
}
