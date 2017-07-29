package rtp.entities;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public class Vehicle extends RTPentity {
    private static final Logger LOGGER = Logger.getLogger( TempsItinerari.class.getName() );

    private String tipus_vehicle_id;
    private String n_cadira_rodes;

    public Vehicle(String val, String h) throws IOException{
        super(val, h);
        setValues(LOGGER);
    }

    public String getTipus_vehicle_id() {
        return tipus_vehicle_id;
    }

    public String getN_cadira_rodes() {
        return n_cadira_rodes;
    }
}
