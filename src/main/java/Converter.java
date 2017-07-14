package main.java;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
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

    private InputReader iReader;
    private RTPChecker checker;

    protected Converter(File dir) throws IOException{
        iReader = new InputReader(dir);
        Map entities =  iReader.getEntities();
        checker = new RTPChecker(entities);
    }

    protected boolean convert() throws IOException {
        try {
            if (!checker.checkRTPMap()) {
                LOGGER.log(Level.SEVERE, "Cannot do conversion, some mandatory fields not present");
                throw new Exception("Conversion not possible");
            }

            if (checker.isRestriccioPresent()) {
                LOGGER.info("We have restriccio");
            }

            if (checker.isVehiclePresent()) {
                LOGGER.info("We have vehicle");
            }

            return true;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
