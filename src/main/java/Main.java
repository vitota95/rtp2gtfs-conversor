import gtfs.GTFSClassNames;
import writers.Writer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger LOGGER = Logger.getLogger( Main.class.getName() );
    private static final double NANOS_TO_SECONDS = 1000000000.0;
    private static String outputDirectory;
    /**
     * Main function, entry point of the application, gets rtp files directory
     * and calls inputReader.
     *@param args
     */
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Converter converter;
        Map arguments = new HashMap();
        File mainDirectory;
        long start = System.nanoTime();

        if (args.length == 2){
            arguments.put("Indirectory", args[0]);
            outputDirectory = args[1];
            LOGGER.log(Level.FINE,"Correct number of arguments provided");
        }
        else{
            LOGGER.log(Level.SEVERE, "Wrong number of arguments");
            return;
        }

        mainDirectory = new File(arguments.get("Indirectory").toString());
        try{
            boolean conversionDone = false;
            if(mainDirectory.exists()){
                LOGGER.info("Found directory");
                converter = new Converter(mainDirectory, outputDirectory);
                conversionDone = converter.convert();
            }
            else {
                LOGGER.log(Level.SEVERE,"Incorrect directory path");
            }

            if (conversionDone) {
                LOGGER.fine("Conversion performed GTFS file saved to ");
            }

            long elapsedTime = System.nanoTime() - start;
            double seconds = (double) elapsedTime / NANOS_TO_SECONDS;
            System.out.println("Elapsed time (seconds): " + seconds);
        }
        catch (IOException io){
            io.printStackTrace();
        }
        //writerTest();
    }

    public static void writerTest() {
        ArrayList<String> list = new ArrayList<String>() {{
            add("A;bababa;;;");
            add("B;3fga3r3a;aaa");
            add("C");
        }};

        try {
            Writer writer = Writer.getInstance();
            writer.write(GTFSClassNames.CLASS_AGENCY, list, outputDirectory);
        } catch (IOException io){
            io.printStackTrace();
        }
    }
}
