package rtp.entities;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public abstract class RTPentity {
    private String valuesString;
    private List heads = null;
    private static String csvSeparator = ";";

    public RTPentity(String v, String h){
        this.valuesString = v;
        if (heads == null){
            heads = Arrays.asList(h.split(csvSeparator));
            ListIterator<String> iterator = heads.listIterator();
            while (iterator.hasNext())
            {
                iterator.set(iterator.next().toLowerCase());
            }
        }
    }

    private static boolean checkEmpty(String s){
        return (s == null && s.isEmpty());
    }

    void setValues(Logger LOGGER, Class c) throws IOException{
        String[] vals = this.valuesString.split(csvSeparator, -1);

        Field[] fields = c.getDeclaredFields();
        int index;

        for (Field f : fields){
            index = heads.indexOf(f.getName().toLowerCase());
            if (index != -1) {
                try {
                    if (!checkEmpty(vals[index]))
                        f.set(this, vals[index]);
                    else {
                        LOGGER.log(Level.SEVERE, "Empty value " + heads.get(index));
                        throw new IOException("Empty value detected");
                    }
                } catch (IllegalAccessException e) {
                    LOGGER.log(Level.SEVERE, "Error setValues");
                    e.printStackTrace();
                }
            }
        }
    }
}
