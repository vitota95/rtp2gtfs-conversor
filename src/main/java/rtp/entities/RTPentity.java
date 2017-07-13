package main.java.rtp.entities;

import javax.print.DocFlavor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public abstract class RTPentity {
    String valuesString;
    List heads = null;
    static String csvSeparator = ";";

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

    public static boolean checkEmpty(String s){
        return (s == null && s.isEmpty());
    }

    abstract void validate() throws IOException;

    void setValues(Logger LOGGER, Class c) throws IOException{
        String[] vals = this.valuesString.split(csvSeparator);

        Field[] fields = c.getDeclaredFields();
        int index;

        for (Field f : fields){
            index = heads.indexOf(f.getName().toLowerCase());
            if (index != -1) {
                try {
                    f.set(this, vals[index]);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
