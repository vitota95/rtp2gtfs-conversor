package main.java.rtp.entities;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public abstract class RTPentity {
    String valuesString;
    static String csvSeparator = ";";


    public RTPentity(String v){
        this.valuesString = v;
    }

    public static boolean checkEmpty(String s){
        return (s == null && s.isEmpty());
    }

    abstract void validate() throws IOException;

    void setValues(String header, Logger LOGGER, Class c) throws IOException{
        String[] vals = this.valuesString.split(csvSeparator);
        String[] heads = header.split(csvSeparator);

        Field[] fields = c.getFields();
        int i;

        for (Field f : fields){
            i = 0;
            for (String h : heads) {
                if (f.getName().toLowerCase().equals(h.toLowerCase())) {
                    try {
                        f.set(this, vals[i]);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                i++;
            }
        }
    }
}
