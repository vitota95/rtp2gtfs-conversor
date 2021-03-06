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
 * Abstract class representing an RTP entity
 *
 */
public abstract class RTPentity {
    private String valuesString;
    private List heads = null;
    private static String csvSeparator = ";";

    /**
     * @param v csv string of the RTP file
     * @param h header of the RTP file
     */

    public RTPentity(String v, String h){
        this.valuesString = v;
        if (heads == null){
            heads = Arrays.asList(h.split(csvSeparator, -1));
            ListIterator<String> iterator = heads.listIterator();
            while (iterator.hasNext()) {
                iterator.set(iterator.next().toLowerCase());
            }
        }
    }

    /**
     * Checks if there is an empty line or a mandatory value not assigned
     *
     * @param s String to check
     * @return boolean with the result
     */
    private static boolean checkEmpty(String s){
        return (s == null && s.isEmpty());
    }

    /**
     * @param LOGGER logger of the RTP entity
     * @throws IOException exception thrown at error
     */

    void setValues(Logger LOGGER) throws IOException {
        String[] vals = this.valuesString.split(csvSeparator, -1);

        Field[] fields = this.getClass().getDeclaredFields();
        int index;

        for (Field f : fields){
            index = heads.indexOf(f.getName().toLowerCase());
            if (index != -1) {
                try {
                    if (!checkEmpty(vals[index])) {
                        f.setAccessible(true);
                        f.set(this, vals[index]);
                    } else {
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
