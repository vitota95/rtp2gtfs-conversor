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
    private HashMap<String, List> GTFSentitiesMap;

    /**
     * @param dir input RTP File
     * @param od  output directory of the GTFS file
     * @throws IOException
     */
    Converter(File dir, String od) throws IOException {
        iReader = new InputReader(dir);
        RTPentitiesMap = iReader.getEntities();
        checker = new RTPChecker(RTPentitiesMap);
        outputDirectory = od;
        GTFSentitiesMap = new HashMap<>();
    }

    /**
     * Main method of Converter, it calls the methods which perform
     * the RTP - GTFS conversion by generating the GTFS classes
     *
     * @return boolean with conversion result
     * @throws IOException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws InvocationTargetException
     * @throws ParseException
     */
    boolean convert() throws IOException, IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException, ParseException, ClassNotFoundException {
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
                setStopTimesWithGrupHorari(GTFSClassNames.CLASS_STOP_TIMES, GtfsCsvHeaders.CLASS_STOP_TIMES_CSV);
            }
            else{
                setStopTimesWithHoresDePas(GTFSClassNames.CLASS_STOP_TIMES, GtfsCsvHeaders.CLASS_STOP_TIMES_CSV);
            }
            //setCalendarWithTipusDia(GTFSClassNames.CLASS_CALENDAR, GtfsCsvHeaders.CLASS_CALENDAR_CSV);

            writeGTFSFile();
            return true;
        }
        return false;
    }

    /**
     * Checks if all mandatory RTP files are present in the RTP zip
     * if not the conversion can't be made.
     * It checks too which optional files are present.
     *
     * @return
     */

    boolean checkRTPEntities() {
        try {
            if (!checker.checkRTPMap()) {
                LOGGER.log(Level.SEVERE, "Cannot perform conversion, some mandatory files not present");
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

    /**
     * Adds csv file String and key to a map, the key is the GTFS file name
     * which will be written by the file writer at the end.
     *
     * @param key
     * @param entityCSV
     */

    private void addToEntitiesMap(String key, List entityCSV) {
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

    /**
     * Standard method to set simple GTFS files, this files
     * only need a RTP file to direct conversion.
     *
     * @param gtfsClass class created
     * @param gtfsFileName name of the GTFS file created
     * @param rtpClass name of the RTP class needed
     * @param gtfsHeader header of the GTFS class
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */

    private void setGtfsFilesWithSimpleConversion(Class gtfsClass, String gtfsFileName, String rtpClass, String gtfsHeader)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException,
            InstantiationException, ClassNotFoundException {

        ArrayList<RTPentity> rtPentities = RTPentitiesMap.get(rtpClass);
        ArrayList<String> csv = new ArrayList<>();

        //Get GTFS object by reflection
        Constructor<?> ctor = gtfsClass.getConstructor();
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

    /**
     * If the RTP files contain the grup_horari and temps_itinerari files
     * we will use this method to get the stopTimes.
     * @param gtfsFileName name of the GTFS file created
     * @param gtfsHeader header of the GTFS file created
     * @throws IllegalAccessException
     * @throws ParseException
     */
    @SuppressWarnings("unchecked")
    private void setStopTimesWithGrupHorari(String gtfsFileName, String gtfsHeader) throws IllegalAccessException, ParseException {
        ArrayList<Expedicio> exps = RTPentitiesMap.get(RTPClassNames.CLASS_EXPEDICIO);
        Expedicio[] expedicios = exps.toArray(new Expedicio[exps.size()]);
        ArrayList<Itinerari> its = RTPentitiesMap.get(RTPClassNames.CLASS_ITINERARI);
        Itinerari[] itineraris = its.toArray(new Itinerari[its.size()]);
        ArrayList<TempsItinerari> tpi = RTPentitiesMap.get(RTPClassNames.CLASS_TEMPS_ITINERARI);
        TempsItinerari[] tempsItineraris = tpi.toArray(new TempsItinerari[tpi.size()]);
        ArrayList<GrupHorari> grupHoraris = RTPentitiesMap.get(RTPClassNames.CLASS_GRUP_HORARI);

        ArrayList<String> csv = new ArrayList<>();

        csv.add(gtfsHeader);
        ArrayList<ArrayList<String>> csvParallel = new ArrayList<>(grupHoraris.size());

        grupHoraris.parallelStream().forEach((GrupHorari grupHorari) -> {
            ArrayList<String> csvTemp = new ArrayList<>();
            //Source:  https://zeroturnaround.com/rebellabs/java-8-explained-applying-lambdas-to-java-collections/
            Stream<Expedicio> expedicioStream = Arrays.stream(expedicios)
                    .filter(x -> x.getGrup_horari_id().equalsIgnoreCase(grupHorari.getGrup_horari_id()));

            Expedicio[] expedicioFiltered = expedicioStream.toArray(Expedicio[]::new);

            for (Expedicio expedicio : expedicioFiltered) {

                Stream<TempsItinerari> tempsItinerariStream = Arrays.stream(tempsItineraris)
                        .filter(x -> x.getGrup_horari_id().equalsIgnoreCase(grupHorari.getGrup_horari_id()));

                TempsItinerari[] tempsItinerariFiltered = tempsItinerariStream.toArray(TempsItinerari[]::new);
                Stream<Itinerari> itinerariStream = Arrays.stream(itineraris)
                        .filter(x -> x.getTrajecte_id().equalsIgnoreCase(expedicio.getTrajecte_id()));

                Itinerari[] itinerariFiltered = itinerariStream.toArray(Itinerari[]::new);

                int index = 0;
                int stopNumber = 0;

                for (Itinerari itinerari : itinerariFiltered) {
                    Map<String, RTPentity> mapParameters = new HashMap<>();
                    GTFSParameters rtpValues = new GTFSParameters();

                    // Prevent negative RTP values
                    if (Integer.parseInt(tempsItinerariFiltered[index].getTemps_viatge()) < 0) {
                        index++;
                        continue;
                    }
                    stopNumber++;

                    if (index > 0) {
                        int time = Integer.parseInt(tempsItinerariFiltered[index].getTemps_viatge()) +
                                Integer.parseInt(tempsItinerariFiltered[index - 1].getAcummulatedTime());
                        tempsItinerariFiltered[index].setAccumulatedTime(String.valueOf(time));
                    } else {
                        int time = 0;
                        try {
                            time = Integer.parseInt(tempsItinerariFiltered[index].getTemps_viatge()) +
                                    Integer.parseInt(expedicio.getSortida_hora());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        tempsItinerariFiltered[index].setAccumulatedTime(String.valueOf(time));
                    }

                    mapParameters.put(RTPClassNames.CLASS_EXPEDICIO, expedicio);
                    mapParameters.put(RTPClassNames.CLASS_TEMPS_ITINERARI, tempsItinerariFiltered[index]);
                    mapParameters.put(RTPClassNames.CLASS_ITINERARI, itinerari);
                    rtpValues.setRTPobjects(mapParameters);
                    Stop_times stop_times = new Stop_times();
                    try {
                        synchronized (this) {
                            csvTemp.add(stop_times.getCsvString(rtpValues));
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    index++;
                }
            }
            synchronized (this) {
                csvParallel.add(csvTemp);
            }
        });

        //prevent unordered trip_id in stop_times
        for (ArrayList arrayList : csvParallel) {
            csv.addAll(arrayList);
        }

        addToEntitiesMap(gtfsFileName, csv);
    }


    /**
     * If the RTP files contain HoresDePas we can use this method
     * to generate StopTimes, with this method is easier and has less
     * cost, so es preferable.
     *
     * @param gtfsFileName Name of the GTFS file generated
     * @param gtfsHeader   Header of the GTFS file generated
     * @throws IllegalAccessException
     */
    @SuppressWarnings("unchecked")
    private void setStopTimesWithHoresDePas(String gtfsFileName, String gtfsHeader) {
        ArrayList<Expedicio> expedicios =  RTPentitiesMap.get(RTPClassNames.CLASS_EXPEDICIO);

        ArrayList<HoresDePas> hdps = RTPentitiesMap.get(RTPClassNames.CLASS_HORES_DE_PAS);
        HoresDePas[] horesDePas = hdps.toArray(new HoresDePas[hdps.size()]);

        ArrayList<Itinerari> its = RTPentitiesMap.get(RTPClassNames.CLASS_ITINERARI);
        Itinerari[] itineraris = its.toArray(new Itinerari[its.size()]);

        ArrayList<String> csv = new ArrayList<>();
        csv.add(gtfsHeader);

        for (Expedicio expedicio : expedicios) {
            Arrays.stream(horesDePas)
                    .filter(x -> x.getExpedicio_id()
                            .equalsIgnoreCase(expedicio.getExpedicio_id()))
                    .forEach(horaDepas -> {
                        //Get itinerari which match the same linia, trajecte and hora de pas
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
                    try {
                        csv.add(stop_times.getCsvString(rtpValues));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                    });
        }
        addToEntitiesMap(gtfsFileName, csv);
    }

    /**
     * If we have several tipus_dias we can generate a calendar we that
     * if not we have to use restriccio RTP file and if they are not present,
     * we only can generate a generic calendar, with all days set to true
     * and no restrictions.
     *
     * @param gtfsFileName Name of the GTFS file
     * @param gtfsHeader   GTFS header of this file
     * @throws IllegalAccessException
     * @throws IOException
     */

    @SuppressWarnings("unchecked")
    private void setCalendarWithTipusDia(String gtfsFileName, String gtfsHeader) throws IllegalAccessException, IOException {
        ArrayList<TipusDia> td = RTPentitiesMap.get(RTPClassNames.CLASS_TIPUS_DIA);
        ArrayList<Periode> periodes = RTPentitiesMap.get(RTPClassNames.CLASS_PERIODE);
        TipusDia[] tipusDias = td.toArray(new TipusDia[td.size()]);
        ArrayList<TipusDia2DiaAtribut> td2da = RTPentitiesMap.get(RTPClassNames.CLASS_TIPUS_DIA_2_DIA_ATRIBUT);
        TipusDia2DiaAtribut[] tipusDia2DiaAtributs = td2da.toArray(new TipusDia2DiaAtribut[td2da.size()]);
        ArrayList<DiaAtribut> diaAtributs = RTPentitiesMap.get(RTPClassNames.CLASS_DIA_ATRIBUT);

        ArrayList<String> csv = new ArrayList<>();
        csv.add(gtfsHeader);

        Periode[] p = new Periode[1];
        p[0] = periodes.get(0);

        for (DiaAtribut diaAtribut : diaAtributs) {
            Map<String, RTPentity[]> mapParameters = new HashMap<>();
            GTFSParameters rtpValues = new GTFSParameters();
            DiaAtribut[] diaAtributParam = new DiaAtribut[1];
            diaAtributParam[0] = diaAtribut;

            Stream<TipusDia2DiaAtribut> tipusDia2DiaAtributStream = Arrays.stream(tipusDia2DiaAtributs)
                    .filter(x -> x.getDia_atribut_id().equalsIgnoreCase(diaAtribut.getDia_atribut_id()));

            mapParameters.put(RTPClassNames.CLASS_TIPUS_DIA_2_DIA_ATRIBUT, tipusDia2DiaAtributStream.toArray(TipusDia2DiaAtribut[]::new));
            mapParameters.put(RTPClassNames.CLASS_PERIODE, p);

            Calendar calendar = new Calendar();
            csv.add(calendar.getCsvString(mapParameters));
        }

        addToEntitiesMap(gtfsFileName, csv);
    }
}
