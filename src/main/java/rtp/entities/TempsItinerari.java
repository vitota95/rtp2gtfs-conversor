package main.java.rtp.entities;

import java.io.IOException;

/**
 * Created by javig on 03/07/2017.
 */
public class TempsItinerari extends RTPentity {
    String trajecte_id;
    String direccio_id;
    String sequencia_id;
    String temps_viatge;
    String temps_parat;

    @Override
    void setValues() throws IOException {

    }

    public TempsItinerari(String val, String h){
        super(val, h);
    }

    @Override
    void validate() {

    }
}
