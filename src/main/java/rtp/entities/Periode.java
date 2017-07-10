package main.java.rtp.entities;

import java.io.IOException;

/**
 * Created by javig on 03/07/2017.
 */
public class Periode extends RTPentity {
    String periode_id;
    String periode_dinici;
    String periode_dfi;

    @Override
    void setValues() throws IOException {

    }

    public Periode(String val, String h){
        super(val, h);
    }

    @Override
    void validate() {

    }
}
