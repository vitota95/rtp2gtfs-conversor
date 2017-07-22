import gtfs.*;
import rtp.InputReader;
import rtp.RTPChecker;
import rtp.RTPClassNames;
import rtp.entities.Periode;
import rtp.entities.RTPentity;
import rtp.entities.Restriccio;
import writers.Writer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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

    boolean convert() throws IOException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        setGtfs(Agency.class, GTFSClassNames.CLASS_AGENCY, RTPClassNames.CLASS_OPERADOR, GtfsCsvHeaders.CLASS_AGENCY_CSV);
        setGtfs(Trips.class, GTFSClassNames.CLASS_TRIPS, RTPClassNames.CLASS_EXPEDICIO, GtfsCsvHeaders.CLASS_TRIPS_CSV);
        setGtfs(Routes.class, GTFSClassNames.CLASS_ROUTES, RTPClassNames.CLASS_LINIA, GtfsCsvHeaders.CLASS_ROUTES_CSV);
        setGtfs(Stops.class, GTFSClassNames.CLASS_STOPS, RTPClassNames.CLASS_PARADA, GtfsCsvHeaders.CLASS_STOPS_CSV);
        writeGTFSFile();

        if (checkRTPEntities()) {
            return true;
        }

        return false;
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


    private void setGtfs(Class cl, String gtfsFileName, String rtpClass, String gtfsHeader) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        ArrayList<RTPentity> rtPentities = RTPentitiesMap.get(rtpClass);
        ArrayList<String> csv = new ArrayList<>();

        //Get GTFS object by reflection
        Constructor<?> ctor = cl.getConstructor();
        Object object = ctor.newInstance();
        GTFSEntity entity = (GTFSEntity) object;

        csv.add(gtfsHeader);
        for (RTPentity rtPentity : rtPentities) {
            Map<String, RTPentity> mapParameters = new HashMap<>();
            GTFSParameters rtpValues = new GTFSParameters();
            mapParameters.put(rtpClass, rtPentity);
            rtpValues.setRTPobjects(mapParameters);
            csv.add(entity.getCsvString(rtpValues));
        }
        addToEntitiesMap(gtfsFileName, csv);
    }

    private void setCalendars(String key) throws IllegalAccessException {
        ArrayList<Restriccio> restriccios = RTPentitiesMap.get(RTPClassNames.CLASS_RESTRICCIO);
        ArrayList<Periode> periodes = RTPentitiesMap.get(RTPClassNames.CLASS_PERIODE);
    }
}
