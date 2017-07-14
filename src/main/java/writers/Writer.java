package main.java.writers;

import main.java.gtfs.GTFSClassNames;

/**
 * Created by javig on 14/07/2017.
 */

public abstract class Writer  {
    protected final String fileName;

    public Writer(String f) {
        fileName = f;
    }

    abstract void write();
}
