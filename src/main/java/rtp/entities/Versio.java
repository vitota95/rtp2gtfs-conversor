package main.java.rtp.entities;

import main.java.rtp.CSVHeaders;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public class Versio extends RTPentity {

    private static final Logger LOGGER = Logger.getLogger( Versio.class.getName() );
    private String data;

    public Versio(String val, String h) throws IOException{
        super(val, h);
        this.setValues();
        this.validate();
    }

    @Override
    void validate() throws IOException{
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date d = sdf.parse(data);
            if (!data.equals(sdf.format(d))) {
                LOGGER.log(Level.SEVERE, "Data not well formatted");
                throw new IOException("Data not well formatted");
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    void setValues() throws IOException{
        String[] vals = this.valuesString.split(csvSeparator);
        String[] heads = header.split(csvSeparator);

        if(vals.length != heads.length){
            LOGGER.log(Level.SEVERE, "Values and header don't match");
            throw new IOException();
        }

        data = vals[0];
    }
}
