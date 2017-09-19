package conversor;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
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
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException,
            InstantiationException, ParseException, ClassNotFoundException {
        conversor.Converter converter;
        File mainDirectory;
        long start = System.nanoTime();

        if (args.length == 2){
            outputDirectory = args[1];
            File file = new File(outputDirectory);
            LOGGER.info("Correct number of arguments provided");

            if (file.isFile())
            {
                LOGGER.info("Output directory correct");
            }
            else
            {
                LOGGER.log(Level.SEVERE, "You must provide a valid file name");
                return;
            }
        } else{
            LOGGER.log(Level.SEVERE, "Wrong number of arguments, needed {input.rtp} {outputDirectory}");
            return;
        }

        mainDirectory = new File(args[0]);
        try{
            boolean conversionDone = false;
            if(mainDirectory.exists()){
                LOGGER.info("Found directory " + mainDirectory);
                converter = new conversor.Converter(mainDirectory, outputDirectory);
                conversionDone = converter.convert();
            } else {
                LOGGER.log(Level.SEVERE,"Incorrect directory path");
            }

            if (conversionDone) {
                LOGGER.fine("Conversion performed GTFS file saved to ");
            } else {
                LOGGER.warning("Some files were not saved");
            }

            long elapsedTime = System.nanoTime() - start;
            double seconds = (double) elapsedTime / NANOS_TO_SECONDS;
            System.out.println("Elapsed time (seconds): " + seconds);
        } catch (IOException io){
            io.printStackTrace();
        }
    }
}
