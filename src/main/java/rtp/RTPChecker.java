package rtp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by javig on 07/07/2017.
 */
public class RTPChecker {


    private static final Logger LOGGER = Logger.getLogger( RTPChecker.class.getName() );
    private List<String> entityKeys;
    private List<String> mandatoryFiles;


    public RTPChecker(Map entities){
        entityKeys = new ArrayList<>(entities.keySet());
        mandatoryFiles = Arrays.asList(RTPClassNames.MandatoryRTPEntities.filesMandatory);
    }

    public ArrayList<String> checkRTPMap() {
        ArrayList<String> filesToConvert = new ArrayList<>();

        //Get files present to convert
        for (String rtpFile : mandatoryFiles) {
            if (entityKeys.contains(rtpFile)) {
                filesToConvert.add(rtpFile);
            } else {
                LOGGER.log(Level.WARNING, rtpFile + " not present, associated tables will not be generated");
            }
        }

        if (this.isRestriccioPresent()) {
            filesToConvert.add(RTPClassNames.CLASS_RESTRICCIO);
        }

        if (this.isHoresDepasPresent()) {
            filesToConvert.add(RTPClassNames.CLASS_HORES_DE_PAS);
        } else if (this.isTempsitinerari_GrupHorariPresent()) {
            filesToConvert.add(RTPClassNames.CLASS_GRUP_HORARI);
        } else {
            LOGGER.log(Level.WARNING, "neither Hores de pas, nor temps itinerari were found. Calendars are not being generated");
        }

        return filesToConvert;
    }

    public boolean isTempsitinerari_GrupHorariPresent() { return entityKeys.containsAll
            (Arrays.asList(RTPClassNames.MandatoryRTPEntities.tempsItinerari_GrupHorari));}

    private boolean isHoresDepasPresent() {
        return entityKeys.contains(RTPClassNames.CLASS_HORES_DE_PAS);
    }

    public boolean isRestriccioPresent(){
        return entityKeys.contains(RTPClassNames.CLASS_RESTRICCIO);
    }

    public boolean isPeriodePresent() {
        return entityKeys.contains(RTPClassNames.CLASS_PERIODE);
    }

    public boolean isItinerariPresent() {
        return entityKeys.contains(RTPClassNames.CLASS_ITINERARI);
    }

    public boolean isTempsItinerariPresent() {
        return entityKeys.contains(RTPClassNames.CLASS_TEMPS_ITINERARI);
    }

    public boolean isVehiclePresent() {
        return entityKeys.contains(RTPClassNames.CLASS_VEHICLE);
    }
}
