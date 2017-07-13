package main.java.rtp.entities;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by javig on 03/07/2017.
 */
public abstract class RTPentity {
    String valuesString;
    Map values;
    protected String header = null;
    static String csvSeparator = ";";

    public RTPentity(String v, String h){
        this.valuesString = v;
        if (header == null) {
            header = h;
        }
    }

    public static boolean checkEmpty(String s){
        return (s == null && s.isEmpty());
    }

    abstract void validate() throws IOException;
    abstract void setValues() throws IOException;
}
