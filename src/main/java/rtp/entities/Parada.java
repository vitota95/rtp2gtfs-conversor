package main.java.rtp.entities;

import java.io.IOException;

/**
 * Created by javig on 03/07/2017.
 */
public class Parada extends RTPentity {
    String parada_punt_id;
    String parada_punt_desc;
    String coord_x;
    String coord_y;

    @Override
    void setValues() throws IOException {

    }

    public Parada(String val, String h){
        super(val, h);
    }

    @Override
    void validate() {

    }
}
