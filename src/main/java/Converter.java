
import gtfs.Agency;
import gtfs.GTFSClassNames;
import gtfs.GTFSParameters;
import gtfs.GtfsCsvHeaders;
import rtp.InputReader;
import rtp.RTPChecker;
import rtp.RTPClassNames;
import rtp.entities.Operador;
import rtp.entities.RTPentity;
import writers.Writer;
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

class Converter {

    private static final Logger LOGGER = Logger.getLogger( Converter.class.getName() );

    private InputReader iReader;
    private RTPChecker checker;
    private String outputDirectory;
    private HashMap<String, ArrayList> RTPentitiesMap;
    private HashMap<String, ArrayList> GTFSentitiesMap;


    Converter(File dir, String od) throws IOException {
        iReader = new InputReader(dir);
        RTPentitiesMap = iReader.getEntities();
        checker = new RTPChecker(RTPentitiesMap);
        outputDirectory = od;
        GTFSentitiesMap = new HashMap<>();
    }

    boolean convert() throws IOException, IllegalAccessException {
        getAgency(RTPClassNames.CLASS_OPERADOR);
        writeGTFSFile();

        if (checkRTPEntities()) {
            return true;
        }

        return false;
    }

    void getAgency(String key) throws IllegalAccessException {
        ArrayList<Operador> operadors = RTPentitiesMap.get(RTPClassNames.CLASS_OPERADOR);
        ArrayList<String> agenciesCSV = new ArrayList<>();

        agenciesCSV.add(GtfsCsvHeaders.CLASS_AGENCY);
        for (Operador operador : operadors) {
            Map<String, RTPentity> mapParameters = new HashMap<>();
            Agency agency = new Agency(GtfsCsvHeaders.CLASS_AGENCY);
            GTFSParameters agencyRTPs = new GTFSParameters();

            mapParameters.put(key, operador);
            agencyRTPs.setRTPobjects(mapParameters);
            agenciesCSV.add(agency.getCsvString(agencyRTPs));
        }
        addToEntitiesMap(GTFSClassNames.CLASS_AGENCY, agenciesCSV);
    }

    boolean checkRTPEntities() {
        try {
            if (!checker.checkRTPMap()) {
                LOGGER.log(Level.SEVERE, "Cannot do conversion, some mandatory files not present");
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

    private void addToEntitiesMap(String key, ArrayList<String> entityCSV) {
        GTFSentitiesMap.put(key, entityCSV);
    }

    private void writeGTFSFile() throws IOException {
        Writer writer = Writer.getInstance();
        final String header;
        GTFSentitiesMap.forEach((fileName, value) -> {
            try {
                writer.write(fileName, value, outputDirectory);
            }catch (IOException io){
                io.printStackTrace();
            }
        });

    }
}
