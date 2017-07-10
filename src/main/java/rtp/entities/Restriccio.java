package main.java.rtp.entities;

import java.io.IOException;

/**
 * Created by javig on 03/07/2017.
 */
public class Restriccio extends RTPentity {
    String periode_id;
    String restriccio_id;
    String dies;

    @Override
    void setValues() throws IOException {

    }

    public Restriccio(String val, String h){
        super(val, h);
    }

    @Override
    void validate() {

    }
}
