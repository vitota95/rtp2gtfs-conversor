package main.java;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import main.java.gtfs.Agency;
import main.java.gtfs.Entity;
import main.java.gtfs.GTFSClassNames;
import main.java.rtp.InputReader;
import main.java.rtp.RTPChecker;
import main.java.writers.Writer;

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
    Map<String, ArrayList> entitiesMap = new HashMap<>();

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

    public void checkGTFSMap() {
        Agency agency1 = new Agency();
        agency1.agency_id = "10";
        agency1.agency_name = "agencia1";
        Agency agency2 = new Agency();
        agency2.agency_id = "10";
        agency2.agency_name = "agencia2";
        ArrayList<String> arrAgency = new ArrayList<>();
        arrAgency.add(agency1.convertToCSV(agency1.getClass()));
        arrAgency.add(agency2.convertToCSV(agency2.getClass()));
        addToEntitiesMap("agency.txt", arrAgency);

        try {
            writeGTFSFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addToEntitiesMap(String key, ArrayList<String> entityCSV) {
        entitiesMap.put(key, entityCSV);
    }


    private void writeGTFSFile() throws IOException {

        Writer writer = Writer.getInstance();
        entitiesMap.forEach((fileName,value) -> {
            try {
                writer.write(fileName, value);
            }catch (IOException io){
                io.printStackTrace();
            }
        });

    }
}
