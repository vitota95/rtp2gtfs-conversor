package main.java.rtp.entities;

import java.io.IOException;

/**
 * Created by javig on 03/07/2017.
 */
public class Itinerari extends RTPentity {
    String linia_id;
    String trajecte_id;
    String direccio_id;
    String sequencia_id;
    String parada_punt_id;

    public Itinerari(String val, String h){
        super(val, h);
    }

    @Override
    void validate() {

    }

    @Override
    void setValues() throws IOException {

    }
}
