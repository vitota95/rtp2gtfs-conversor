package main.java.writers;

import main.java.Main;
import main.java.gtfs.Entity;
import main.java.gtfs.GTFSClassNames;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by javig on 14/07/2017.
 */

public class Writer  {

    private static final String outputDirectory = Main.getOutputDirectory();
    private static final String GTFSName = "GTFS.zip";
    private static ZipOutputStream outputStream;
    private static Writer instance;

    private Writer(){}

    public static Writer getInstance() throws  FileNotFoundException{
        if(instance == null){
            instance = new Writer();
            File file = new File(outputDirectory + GTFSName);
            outputStream = new ZipOutputStream(new FileOutputStream(file));
        }
        return instance;
    }

    public void write(String fileName, ArrayList<String> entities) throws IOException{
        StringBuilder sb = new StringBuilder();
        sb.append(String.join("\n", entities));
        ZipEntry entry = new ZipEntry(fileName);
        outputStream.putNextEntry(entry);
        byte[] data = sb.toString().getBytes();
        outputStream.write(data, 0, data.length);
        outputStream.closeEntry();

        outputStream.close();
    }
}
