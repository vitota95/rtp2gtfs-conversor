package main.java.rtp.entities;

import java.io.IOException;

/**
 * Created by javig on 03/07/2017.
 */
public class Vehicle extends RTPentity {

    private String header;
    public void setHeader(String h){
        header = h;
    }

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
