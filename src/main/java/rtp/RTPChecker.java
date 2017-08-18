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

    @SuppressWarnings("unchecked")
    public RTPChecker(Map entities){
        entityKeys = new ArrayList<>(entities.keySet());
        mandatoryFiles = Arrays.asList(RTPClassNames.MandatoryRTPEntities.filesMandatory);
    }

    public boolean checkRTPMap() {

        if (entityKeys.containsAll(mandatoryFiles)) {

            if (isHoresDepasPresent()) {
                LOGGER.info("All mandatory files were read, using hores de pas to calculate stop_times");
                return true;
            }
            if (isTempsitinerari_GrupHorariPresent()) {
                LOGGER.info("All mandatory files were read, using grup horari and temps itinerari " +
                        "to calculate stop_times");
                return true;
            }

        } else {
            LOGGER.log(Level.SEVERE, "Some mandatory files are missing");
        }
        return false;
    }

    public boolean isTempsitinerari_GrupHorariPresent() { return entityKeys.containsAll
            (Arrays.asList(RTPClassNames.MandatoryRTPEntities.tempsItinerari_GrupHorari));}

    public boolean isHoresDepasPresent() {
        return entityKeys.contains(RTPClassNames.MandatoryRTPEntities.horesDePas);
    }

    public boolean isRestriccioPresent(){
        return entityKeys.contains(RTPClassNames.CLASS_RESTRICCIO);
    }

    public boolean isTipusDiaPresent() {
        return entityKeys.contains(RTPClassNames.CLASS_TIPUS_DIA);
    }

    public boolean isHoresDePasPresent() {
        return entityKeys.contains(RTPClassNames.CLASS_HORES_DE_PAS);
    }
}
