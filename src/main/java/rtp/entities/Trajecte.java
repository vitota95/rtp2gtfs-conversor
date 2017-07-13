package main.java.rtp.entities;

import java.io.IOException;

/**
 * Created by javig on 03/07/2017.
 */
public class Trajecte extends RTPentity {
    String linia_id;
    String trajecte_id;

    @Override
    void setValues() throws IOException {

    }

    public Trajecte(String val, String h){
        super(val, h);
    }

    @Override
    void validate() {

    }
}
