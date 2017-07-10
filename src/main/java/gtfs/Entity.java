package main.java.gtfs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by javig on 03/07/2017.
 */
abstract public class Entity {

    protected void write(String csvLine, File file) throws IOException{
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write(csvLine);
        out.close();
    }

    abstract void validate();
}
