import gtfs.GTFSFilenames;
import gtfs.GTFSParameters;
import gtfs.GtfsCsvHeaders;
import gtfs.entities.*;
import gtfs.entities.Calendar;
import rtp.InputReader;
import rtp.RTPChecker;
import rtp.RTPClassNames;
import rtp.entities.*;
import writers.Writer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    private final static String DATE_PATTERN = "yyyyMMdd";

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
        if (checker.checkRTPMap()) {
            setGtfsFilesWithSimpleConversion(Agency.class, GTFSFilenames.CLASS_AGENCY,
                    RTPClassNames.CLASS_OPERADOR, GtfsCsvHeaders.CLASS_AGENCY_CSV);
            setGtfsFilesWithSimpleConversion(Routes.class, GTFSFilenames.CLASS_ROUTES,
                    RTPClassNames.CLASS_LINIA, GtfsCsvHeaders.CLASS_ROUTES_CSV);
            setGtfsFilesWithSimpleConversion(Stops.class, GTFSFilenames.CLASS_STOPS,
                    RTPClassNames.CLASS_PARADA, GtfsCsvHeaders.CLASS_STOPS_CSV);

            if (checker.isRestriccioPresent()) {
                setCalendarWithRestriccio(GTFSFilenames.CLASS_CALENDAR, GtfsCsvHeaders.CLASS_CALENDAR_CSV);
            } else {
                setCalendarWithTipusDia(GTFSFilenames.CLASS_CALENDAR, GtfsCsvHeaders.CLASS_CALENDAR_CSV);
            }

            if (checker.isTempsitinerari_GrupHorariPresent()) {
                setStopTimesWithGrupHorari(GTFSFilenames.CLASS_STOP_TIMES, GtfsCsvHeaders.CLASS_STOP_TIMES_CSV);
            } else {
                setStopTimesWithHoresDePas(GTFSFilenames.CLASS_STOP_TIMES, GtfsCsvHeaders.CLASS_STOP_TIMES_CSV);
            }


            setGtfsFilesWithSimpleConversion(Trips.class, GTFSFilenames.CLASS_TRIPS,
                    RTPClassNames.CLASS_EXPEDICIO, GtfsCsvHeaders.CLASS_TRIPS_CSV);

            writeGTFSFile();
            return true;
        } else {
            LOGGER.log(Level.SEVERE, "Cannot perform conversion, some mandatory files not present");
            throw new IOException("Conversion not possible");
        }
    }

    /**
     * Adds csv file String and key to a map, the key is the GTFS file name
     * which will be written by the file writer at the end.
     *
     * @param key GTFS file name key to de entities map
     * @param entityCSV CSV List one GTFS entity per row
     */

    private void addToEntitiesMap(String key, List entityCSV) {
        GTFSentitiesMap.put(key, entityCSV);
    }

    /**
     * Gets the writer singleton to write the gtfs files.
     *
     * @throws IOException
     */

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
    @SuppressWarnings("unchecked")
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
                int previousSequencia_id = 0;
                int previousAccumulatedTime = 0;

                for (Itinerari itinerari : itinerariFiltered) {
                    Map<String, RTPentity> mapParameters = new HashMap<>();
                    GTFSParameters rtpValues = new GTFSParameters();

                    //Copy to avoid problems with synchronization
                    TempsItinerari tempsItinerari = tempsItinerariFiltered[index];
                    Itinerari itinerariCopy = itinerari;

                    // Prevent negative RTP values
                    if (Integer.parseInt(tempsItinerari.getTemps_viatge()) < 0) {
                        index++;
                        continue;
                    }

                    if (index > 0) {
                        int time = Integer.parseInt(tempsItinerari.getTemps_viatge()) +
                                previousAccumulatedTime;
                        tempsItinerari.setAccumulatedTime(String.valueOf(time));
                    } else {
                        int time = 0;
                        try {
                            time = Integer.parseInt(tempsItinerari.getTemps_viatge()) +
                                    Integer.parseInt(expedicio.getSortida_hora());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        tempsItinerari.setAccumulatedTime(String.valueOf(time));
                    }

                    //New accumulated time and sequencia_id
                    previousAccumulatedTime = Integer.parseInt(tempsItinerari.getAcummulatedTime());
                    previousSequencia_id++;
                    itinerariCopy.setSequencia_id(String.valueOf(previousSequencia_id));

                    mapParameters.put(RTPClassNames.CLASS_EXPEDICIO, expedicio);
                    mapParameters.put(RTPClassNames.CLASS_TEMPS_ITINERARI, tempsItinerari);
                    mapParameters.put(RTPClassNames.CLASS_ITINERARI, itinerariCopy);
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
        ArrayList<Periode> periodes = RTPentitiesMap.get(RTPClassNames.CLASS_PERIODE);
        ArrayList<TipusDia2DiaAtribut> td2da = RTPentitiesMap.get(RTPClassNames.CLASS_TIPUS_DIA_2_DIA_ATRIBUT);
        TipusDia2DiaAtribut[] tipusDia2DiaAtributs = td2da.toArray(new TipusDia2DiaAtribut[td2da.size()]);
        ArrayList<DiaAtribut> diaAtributs = RTPentitiesMap.get(RTPClassNames.CLASS_DIA_ATRIBUT);

        ArrayList<String> csv = new ArrayList<>();
        csv.add(gtfsHeader);

        Periode[] p = new Periode[1];
        p[0] = periodes.get(0);

        for (DiaAtribut diaAtribut : diaAtributs) {
            Map<String, RTPentity[]> mapParameters = new HashMap<>();

            Stream<TipusDia2DiaAtribut> tipusDia2DiaAtributStream = Arrays.stream(tipusDia2DiaAtributs)
                    .filter(x -> x.getDia_atribut_id().equalsIgnoreCase(diaAtribut.getDia_atribut_id()));

            mapParameters.put(RTPClassNames.CLASS_TIPUS_DIA_2_DIA_ATRIBUT,
                    tipusDia2DiaAtributStream.toArray(TipusDia2DiaAtribut[]::new));
            mapParameters.put(RTPClassNames.CLASS_PERIODE, p);

            Calendar calendar = new Calendar();
            csv.add(calendar.getCsvString(mapParameters));
        }

        addToEntitiesMap(gtfsFileName, csv);
    }

    /**
     * Function to generate calendar from restriccio RTP file.
     * this function only has to be called if we don't have
     * information in dia_atribut. This can happen because sometimes they use
     * only restriccio and give a generic dia_atribut.
     *
     * @param gtfsFileName
     * @param gtfsHeader
     * @throws IOException
     * @throws ParseException
     */
    @SuppressWarnings("unchecked")
    private void setCalendarWithRestriccio(String gtfsFileName, String gtfsHeader) throws IOException, ParseException, IllegalAccessException {
        ArrayList<Restriccio> restriccios = RTPentitiesMap.get(RTPClassNames.CLASS_RESTRICCIO);
        ArrayList<Periode> periodes = RTPentitiesMap.get(RTPClassNames.CLASS_PERIODE);
        ArrayList<Expedicio> exps = RTPentitiesMap.get(RTPClassNames.CLASS_EXPEDICIO);
        Expedicio[] expedicios = exps.toArray(new Expedicio[exps.size()]);
        Periode periode = periodes.get(0);
        ArrayList<String> calendarDatesCsv = new ArrayList<>();

        calendarDatesCsv.add(GtfsCsvHeaders.CLASS_CALENDAR_DATES_CSV);

        int sid = 1;

        ArrayList<String> csv = new ArrayList<>();
        csv.add(gtfsHeader);

        for (Restriccio restriccio : restriccios) {
            ArrayList<TipusDia2DiaAtribut> tipusDia2DiaAtributs =
                    generateTipusDia2DiaAtributByRestriccio(restriccio.getDies(), String.valueOf(sid), periode, calendarDatesCsv);

            Map<String, RTPentity[]> mapParameters = new HashMap<>();
            mapParameters.put(RTPClassNames.CLASS_TIPUS_DIA_2_DIA_ATRIBUT, tipusDia2DiaAtributs
                    .toArray(new TipusDia2DiaAtribut[tipusDia2DiaAtributs.size()]));
            mapParameters.put(RTPClassNames.CLASS_PERIODE,
                    periodes.toArray(new Periode[periodes.size()]));

            Calendar calendar = new Calendar();
            csv.add(calendar.getCsvString(mapParameters));


            Stream<Expedicio> expedicioStream = Arrays.stream(expedicios)
                    .filter(x -> x.getRestriccio_id().equalsIgnoreCase(restriccio.getRestriccio_id()));

            //Change expedicio restriccio_id to match with new generated service id when assigning to trips
            int serviceid = sid;
            expedicioStream.forEach(expedicio -> expedicio.setRestriccio_id(String.valueOf(serviceid)));

            sid++;
        }

        addToEntitiesMap(gtfsFileName, csv);
        addToEntitiesMap(GTFSFilenames.CLASS_CALENDAR_DATES, calendarDatesCsv);
    }


    /**
     * Function to generate the tipus_dia_2_dia_atribut entities needed to
     * set the calendar.
     *
     * @param days      String from restriccio.getDies
     * @param serviceId given service_id generated in the caller function
     * @param periode   Periode rtpEntity
     * @return ArrayList<TipusDia2DiaAtribut>
     * @throws IOException
     * @throws ParseException string can't be matched
     */

    private ArrayList<TipusDia2DiaAtribut> generateTipusDia2DiaAtributByRestriccio(String days, String serviceId
            , Periode periode, ArrayList<String> calendarDatesCsv) throws IOException, ParseException, IllegalAccessException {
        ArrayList<TipusDia2DiaAtribut> tipusDia2DiaAtributs = new ArrayList<>();
        List<String> foundPatterns = new ArrayList<>();
        List<AtomicInteger> countOfPatterns = new LinkedList<>();

        //Get init day of week to find pattern from Monday to Sunday
        int dayOfWeek = getCalendarFromStringDate(periode.getPeriode_dinici()).get(java.util.Calendar.DAY_OF_WEEK);

        //Get pattern of weeks (7 days)
        if (dayOfWeek != java.util.Calendar.MONDAY) {
            StringBuilder sb = new StringBuilder(days);
            //We put 9 cause calendar considers sunday as the day 1 and we want to start on monday
            sb.delete(0, 9 - dayOfWeek);
            days = sb.toString();
        }

        //Get pattern for every week to find the most common pattern
        Matcher m = Pattern.compile("([0-1]{7})").matcher(days);
        while (m.find()) {
            String pattern = m.group();
            if (!pattern.isEmpty()) {
                if (!foundPatterns.contains(pattern)) {
                    foundPatterns.add(pattern);
                    countOfPatterns.add(new AtomicInteger(1));
                } else {
                    countOfPatterns.get(foundPatterns.indexOf(pattern)).incrementAndGet();
                }
            }
        }

        int max = 0;
        int index = 0;
        int indexOfMax = -1;
        for (AtomicInteger count : countOfPatterns) {
            if (count.get() > max) {
                max = count.get();
                indexOfMax = index;
            }
            index++;
        }

        //Create Calendar table from the most common pattern
        String pattern = foundPatterns.get(indexOfMax);

        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) == '0') {
                TipusDia2DiaAtribut td = new TipusDia2DiaAtribut("", "");
                td.setDia_atribut_id(serviceId);
                td.setTipus_dia_id(String.valueOf(i + 1));
                tipusDia2DiaAtributs.add(td);
            }
        }

        if (tipusDia2DiaAtributs.isEmpty()) {
            TipusDia2DiaAtribut td = new TipusDia2DiaAtribut("", "");
            //Add generic tipus dia if all days are 0, this can happen in holidays restriccios
            td.setDia_atribut_id(serviceId);
            td.setTipus_dia_id("1");
            tipusDia2DiaAtributs.add(td);
        }

        setCalendarDatesByRestriccio(days, pattern, periode.getPeriode_dinici(), serviceId, calendarDatesCsv);

        return tipusDia2DiaAtributs;
    }

    /**
     * Function to set the list of calendar dates, it uses the restriccio dies and calendar pattern to
     * find strings not matching the calendar pattern to add them to the restriction.
     *
     * @param restriccioDays String of RTP restriccio days
     * @param commonPattern  Pattern in calendar for this restriction
     * @param initDate       First date of the period
     * @param serviceId      Id of the service to set
     * @param csv            List with all csv calendar dates
     * @throws ParseException
     * @throws IllegalAccessException
     */

    private void setCalendarDatesByRestriccio(String restriccioDays, String commonPattern, String initDate,
                                              String serviceId, ArrayList<String> csv) throws ParseException, IllegalAccessException {

        java.util.Calendar c = getCalendarFromStringDate(initDate);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);


        Matcher m = Pattern.compile("([0-1]{7})").matcher(restriccioDays);

        while (m.find()) {
            String pattern = m.group();
            if (!pattern.isEmpty()) {
                if (pattern.equals(commonPattern)) {
                    c.add(java.util.Calendar.DATE, java.util.Calendar.DAY_OF_WEEK);
                } else {
                    for (char ch : pattern.toCharArray()) {
                        //There is a restriction in this day
                        if (ch == '1') {
                            Calendar_dates calendarDates = new Calendar_dates();
                            calendarDates.setDate(sdf.format(c.getTime()));
                            calendarDates.setService_id(serviceId);
                            csv.add(calendarDates.toCSV());
                        }
                        c.add(java.util.Calendar.DATE, 1);
                    }
                }
            } else {
                LOGGER.log(Level.WARNING, "can not find pattern, must be a problem in restriccio.rtp");
            }
        }
    }

    /**
     * Function that returns a calendar object from a date string.
     *
     * @param d date to get calendar
     * @return java.util.calendar
     * @throws ParseException string of date can't be parsed
     */

    private java.util.Calendar getCalendarFromStringDate(String d) throws ParseException {
        DateFormat format = new SimpleDateFormat(DATE_PATTERN);
        Date date = format.parse(d);
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);

        return calendar;
    }
}
