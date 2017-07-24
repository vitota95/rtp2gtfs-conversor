import gtfs.*;
import gtfs.Calendar;
import rtp.InputReader;
import rtp.RTPChecker;
import rtp.RTPClassNames;
import rtp.entities.*;
import writers.Writer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

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

    boolean convert() throws IOException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException, ParseException {
        if (checkRTPEntities()) {
            setGtfsFilesWithSimpleConversion(Agency.class, GTFSClassNames.CLASS_AGENCY,
                    RTPClassNames.CLASS_OPERADOR, GtfsCsvHeaders.CLASS_AGENCY_CSV);
            setGtfsFilesWithSimpleConversion(Trips.class, GTFSClassNames.CLASS_TRIPS,
                    RTPClassNames.CLASS_EXPEDICIO, GtfsCsvHeaders.CLASS_TRIPS_CSV);
            setGtfsFilesWithSimpleConversion(Routes.class, GTFSClassNames.CLASS_ROUTES,
                    RTPClassNames.CLASS_LINIA, GtfsCsvHeaders.CLASS_ROUTES_CSV);
            setGtfsFilesWithSimpleConversion(Stops.class, GTFSClassNames.CLASS_STOPS,
                    RTPClassNames.CLASS_PARADA, GtfsCsvHeaders.CLASS_STOPS_CSV);
            if (checker.isTempsitinerari_GrupHorariPresent()){
                //setStopTimes(GTFSClassNames.CLASS_STOP_TIMES, GtfsCsvHeaders.CLASS_STOP_TIMES_CSV);
            }
            else{
                setStopTimesWithHoresDePas(GTFSClassNames.CLASS_STOP_TIMES, GtfsCsvHeaders.CLASS_STOP_TIMES_CSV);
            }
            setCalendars(GTFSClassNames.CLASS_CALENDAR, GtfsCsvHeaders.CLASS_CALENDAR_CSV);

            writeGTFSFile();
            return true;
        }
        return false;
    }

    boolean checkRTPEntities() {
        try {
            if (!checker.checkRTPMap()) {
                LOGGER.log(Level.SEVERE, "Cannot do conversion, some mandatory files not present");
                throw new IOException("Conversion not possible");
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

    private void setGtfsFilesWithSimpleConversion(Class cl, String gtfsFileName, String rtpClass, String gtfsHeader) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
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

//    private void setStopTimes(String gtfsFileName, String gtfsHeader) throws IllegalAccessException, ParseException {
//        ArrayList<Expedicio> exps = RTPentitiesMap.get(RTPClassNames.CLASS_EXPEDICIO);
//        Expedicio[] expedicios = exps.toArray(new Expedicio[exps.size()]);
//        ArrayList<Itinerari> its = RTPentitiesMap.get(RTPClassNames.CLASS_ITINERARI);
//        Itinerari[] itineraris = its.toArray(new Itinerari[its.size()]);
//        ArrayList<TempsItinerari> tpi = RTPentitiesMap.get(RTPClassNames.CLASS_TEMPS_ITINERARI);
//        TempsItinerari[] tempsItineraris = tpi.toArray(new TempsItinerari[tpi.size()]);
//        ArrayList<GrupHorari> gh = RTPentitiesMap.get(RTPClassNames.CLASS_GRUP_HORARI);
//        GrupHorari[] grupHoraris = gh.toArray(new GrupHorari[gh.size()]);
//        ArrayList<String> csv = new ArrayList<>();
//
//        csv.add(gtfsHeader);
//        for (GrupHorari grupHorari : grupHoraris) {
//
//            //Source:  https://zeroturnaround.com/rebellabs/java-8-explained-applying-lambdas-to-java-collections/
//            Optional<Expedicio> expedicioOptional = Arrays.stream(expedicios)
//                    .filter(x -> x.getGrup_horari_id().equalsIgnoreCase(grupHorari.getGrup_horari_id()))
//                    .findFirst();
//
//            Stream<TempsItinerari> tempsItinerariStream = Arrays.stream(tempsItineraris)
//                    .filter(x -> x.getGrup_horari_id().equalsIgnoreCase(grupHorari.getGrup_horari_id()));
//
//            if (expedicioOptional.isPresent()) {
//                Expedicio expedicio = expedicioOptional.get();
//
//                TempsItinerari[] tempsItinerariFiltered = tempsItinerariStream.toArray(TempsItinerari[]::new);
//                Stream<Itinerari> itinerariStream = Arrays.stream(itineraris)
//                        .filter(x -> x.getTrajecte_id().equalsIgnoreCase(expedicio.getTrajecte_id()));
//
//                int acumulatedTravelTime = Integer.parseInt(expedicio.getSortida_hora());
//                Itinerari[] itinerariFiltered = itinerariStream.toArray(Itinerari[]::new);
//
//                int index = 0;
//                for (Itinerari itinerari : itinerariFiltered) {
//                    Map<String, RTPentity> mapParameters = new HashMap<>();
//                    GTFSParameters rtpValues = new GTFSParameters();
//
//                    int travelTime = Integer.parseInt(tempsItinerariFiltered[index].getTemps_viatge());
//                    //TODO ask marc what to do with -3 value
//                    if (travelTime >= 0) {
//                        acumulatedTravelTime += travelTime;
//                        tempsItinerariFiltered[index].setArrival_time(acumulatedTravelTime);
//                        acumulatedTravelTime += Integer.parseInt(tempsItinerariFiltered[index].getTemps_parat());
//
//                        tempsItinerariFiltered[index].setDeparture_time(acumulatedTravelTime);
//                        mapParameters.put(RTPClassNames.CLASS_EXPEDICIO, expedicio);
//                        mapParameters.put(RTPClassNames.CLASS_TEMPS_ITINERARI, tempsItinerariFiltered[index]);
//                        mapParameters.put(RTPClassNames.CLASS_ITINERARI, itinerari);
//                        rtpValues.setRTPobjects(mapParameters);
//                        Stop_times stop_times = new Stop_times();
//                        csv.add(stop_times.getCsvString(rtpValues));
//
//                    }
//                    index++;
//                }
//            } else {
//                LOGGER.log(Level.SEVERE, "Couldn't find any expedicio or temps_itinerari with grup_horari "
//                        + grupHorari.getGrup_horari_id());
//            }
//        }
//        addToEntitiesMap(gtfsFileName, csv);
//    }



    @SuppressWarnings("unchecked")
    private void setStopTimesWithHoresDePas(String gtfsFileName, String gtfsHeader) throws IllegalAccessException {
        ArrayList<Expedicio> expedicios =  RTPentitiesMap.get(RTPClassNames.CLASS_EXPEDICIO);

        ArrayList<HoresDePas> hdps = RTPentitiesMap.get(RTPClassNames.CLASS_HORES_DE_PAS);
        HoresDePas[] horesDePas = hdps.toArray(new HoresDePas[hdps.size()]);

        ArrayList<Itinerari> its = RTPentitiesMap.get(RTPClassNames.CLASS_ITINERARI);
        Itinerari[] itineraris = its.toArray(new Itinerari[its.size()]);

        ArrayList<String> csv = new ArrayList<>();
        csv.add(gtfsHeader);

        for (Expedicio expedicio : expedicios) {
            Stream<HoresDePas> horesDePasStream = Arrays.stream(horesDePas)
                    .filter(x -> x.getExpedicio_id().equalsIgnoreCase(expedicio.getExpedicio_id()));

            HoresDePas[] horesDePasFiltered = horesDePasStream.toArray(HoresDePas[]::new);

            for (HoresDePas horaDepas : horesDePasFiltered) {
                //Get itinerari who match the same linia, trajecte and hora de pas
                Optional<Itinerari> itinerariOptional = Arrays.stream(itineraris)
                        .filter(x -> x.getLinia_id().equalsIgnoreCase(expedicio.getLinia_id()))
                        .filter(x -> x.getTrajecte_id().equalsIgnoreCase(expedicio.getTrajecte_id()))
                        .filter(x -> x.getSequencia_id().equalsIgnoreCase(horaDepas.getSequencia_id()))
                        .findFirst();

                if (itinerariOptional.isPresent()) {
                    Itinerari itinerari = itinerariOptional.get();
                    Map<String, RTPentity> mapParameters = new HashMap<>();
                    GTFSParameters rtpValues = new GTFSParameters();
                    mapParameters.put(RTPClassNames.CLASS_EXPEDICIO, expedicio);
                    mapParameters.put(RTPClassNames.CLASS_ITINERARI, itinerari);
                    mapParameters.put(RTPClassNames.CLASS_HORES_DE_PAS, horaDepas);
                    rtpValues.setRTPobjects(mapParameters);
                    Stop_times stop_times = new Stop_times();
                    csv.add(stop_times.getCsvString(rtpValues));
                }
            }
        }
        addToEntitiesMap(gtfsFileName, csv);
    }

    private void setCalendars(String gtfsFileName, String gtfsHeader) throws IllegalAccessException, IOException {
        ArrayList<Restriccio> restriccios;
        ArrayList<Periode> periodes = RTPentitiesMap.get(RTPClassNames.CLASS_PERIODE);
        ArrayList<String> csv = new ArrayList<>();
        Calendar calendar = new Calendar();

        csv.add(gtfsHeader);
        if (checker.isRestriccioPresent()){
            restriccios = RTPentitiesMap.get(RTPClassNames.CLASS_RESTRICCIO);
        }
        else {
            //Create dummy object
            Restriccio restriccio = new Restriccio("","");
            restriccio.setDies("000000000000000000000");
            Map<String, RTPentity> mapParameters = new HashMap<>();
            GTFSParameters rtpValues = new GTFSParameters();
            mapParameters.put(RTPClassNames.CLASS_PERIODE, periodes.get(0));
            mapParameters.put(RTPClassNames.CLASS_RESTRICCIO, restriccio);
            rtpValues.setRTPobjects(mapParameters);
            csv.add(calendar.getCsvString(rtpValues));
        }

        addToEntitiesMap(gtfsFileName, csv);
    }
}
