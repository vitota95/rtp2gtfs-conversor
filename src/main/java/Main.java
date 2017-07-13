package main.java;

import java.io.File;
import java.io.IOException;
import java.util.logging.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final Logger LOGGER = Logger.getLogger( Main.class.getName() );
    /**
     * Main function, entry point of the application, gets rtp files directory
     * and calls inputReader.
     *@param args
     */
    public static void main(String[] args) {
        Converter converter;
        Map arguments = new HashMap();
        File mainDirectory;
        long start = System.nanoTime();
        if (args.length == 1){
            arguments.put("directory", args[0]);
            LOGGER.log(Level.FINE,"Correct number of arguments provided");
        }
        else{
            LOGGER.log(Level.SEVERE, "Wrong number of arguments");
            return;
        }

        mainDirectory = new File(arguments.get("directory").toString());
        try{
            if(mainDirectory.exists()){
                LOGGER.log(Level.FINE,"Found directory");
                converter = new Converter(mainDirectory);
                converter.convert();
            }
            else {
                LOGGER.log(Level.SEVERE,"Incorrect directory path");
            }
            long elapsedTime = System.nanoTime() - start;
            double seconds = (double)elapsedTime / 1000000000.0;
            System.out.println("Elapsed time (seconds): " + seconds);
        }
        catch (IOException io){
            io.printStackTrace();
        }

    }
}
