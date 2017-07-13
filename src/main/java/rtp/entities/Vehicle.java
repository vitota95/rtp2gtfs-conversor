package main.java.rtp.entities;

import java.io.IOException;

/**
 * Created by javig on 03/07/2017.
 */
public class Vehicle extends RTPentity {

    String tipus_vehicle_id;
    String n_cadira_rodes;

    @Override
    void setValues() throws IOException {

    }

    public Vehicle(String val, String h){
        super(val, h);
    }

    @Override
    void validate() {

    }
}
