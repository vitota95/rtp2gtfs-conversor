package main.java.rtp.entities;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by javig on 03/07/2017.
 */
public abstract class RTPentity {
    String valuesString;
    String header;
    Map values;
    static String csvSeparator = ";";

    public RTPentity(String v){
        this.valuesString = v;
    }

    public static boolean checkEmpty(String s){
        return (s == null && s.isEmpty()) ? true : false;
    }

    abstract void validate() throws IOException;
    abstract void setValues() throws IOException;
}
