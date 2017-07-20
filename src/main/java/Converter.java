
import gtfs.Agency;
import gtfs.GTFSClassNames;
import gtfs.GTFSParameters;
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
public class Converter {

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

    boolean convert() throws IOException {
        Agency agency;


        getAgency(RTPClassNames.CLASS_OPERADOR);
        writeGTFSFile();
        if (checkRTPEntities()) {
            return true;
        }

        return false;
    }

    void getAgency(String key) {
        ArrayList<Operador> operadors = RTPentitiesMap.get(RTPClassNames.CLASS_OPERADOR);
        ArrayList<String> agenciesCSV = new ArrayList<>();

        for (Operador operador : operadors) {
            Map<String, RTPentity> mapParameters = new HashMap<>();
            Agency agency = new Agency();
            GTFSParameters agencyParemeters = new GTFSParameters();

            mapParameters.put(key, operador);
            agencyParemeters.setRTPobjects(mapParameters);
            agency.setValues(agencyParemeters);
            agenciesCSV.add(agency.convertToCSV());
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

    void checkGTFSMap() {
        Agency agency1 = new Agency();
        agency1.agency_id = "10";
        agency1.agency_name = "agencia1";
        Agency agency2 = new Agency();
        agency2.agency_id = "10";
        agency2.agency_name = "agencia2";
        ArrayList<String> arrAgency = new ArrayList<>();
        arrAgency.add(agency1.convertToCSV());
        arrAgency.add(agency2.convertToCSV());
        addToEntitiesMap("agency.txt", arrAgency);

        try {
            writeGTFSFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addToEntitiesMap(String key, ArrayList<String> entityCSV) {
        GTFSentitiesMap.put(key, entityCSV);
    }


    private void writeGTFSFile() throws IOException {

        Writer writer = Writer.getInstance();
        GTFSentitiesMap.forEach((fileName, value) -> {
            try {
                writer.write(fileName, value, outputDirectory);
            }catch (IOException io){
                io.printStackTrace();
            }
        });

    }
}
