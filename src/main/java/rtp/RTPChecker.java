package main.java.rtp;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by javig on 07/07/2017.
 */
public class RTPChecker {


    private static final Logger LOGGER = Logger.getLogger( RTPChecker.class.getName() );
    private List<String> entityKeys;
    private List<String> mandatoryFiles;

    @SuppressWarnings("unchecked")
    public RTPChecker(Map entities){
        entityKeys = new ArrayList<>(entities.keySet());
        mandatoryFiles = Arrays.asList(RTPClassNames.MandatoryRTPEntities.files);
    }

    public boolean checkRTPMap(){

        if (entityKeys.containsAll(mandatoryFiles)){
            LOGGER.info("All mandatory files were read");
            return true;
        }
        else {
            LOGGER.log(Level.SEVERE, "Some mandatory files are missing");
            return false;
        }
    }

    public boolean isRestriccioPresent(){
        return entityKeys.contains(RTPClassNames.CLASS_RESTRICCIO);
    }

    public boolean isVehiclePresent() {
        return entityKeys.contains(RTPClassNames.CLASS_VEHICLE);
    }
}
