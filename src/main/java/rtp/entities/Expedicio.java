package main.java.rtp.entities;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */

public class Expedicio extends RTPentity {
    private static final Logger LOGGER = Logger.getLogger( Expedicio.class.getName() );


    public Expedicio(String val, String h){
        super(val, h);
    }

    @Override
    void setValues() throws IOException {

    }

    @Override
    void validate() {

    }
}
