package main.java.rtp.entities;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */

public class Expedicio extends RTPentity {
    private static final Logger LOGGER = Logger.getLogger( Expedicio.class.getName() );

    String expedicio_id;
    String periode_id;
    String linia_id;
    String trajecte_id;
    String direccio_id;
    String tipus_vehicle_id;
    String grup_horari_id;


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
