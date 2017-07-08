package main.java;

import main.java.rtp.InputReader;
import main.java.rtp.RTPChecker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by javig on 07/07/2017.
 */
public class Converter {

    private static final Logger LOGGER = Logger.getLogger( Converter.class.getName() );

    File directory;
    InputReader iReader;
    Map entities;
    RTPChecker checker;

    public Converter(File dir){
        this.directory = dir;
        this.iReader = new InputReader(directory);
    }

    public boolean convert() throws IOException {
        entities = iReader.getEntities();
        try {
            if (!checker.checkRTPMap((HashMap<String, ArrayList>) entities)) {
                LOGGER.log(Level.SEVERE, "Cannot do conversion, some mandatory fields not present");
                throw new Exception("Conversion not possible");
            }
            else {
                return true;
            }
        }
        catch (Exception e){
            return false;
        }
    }
}
