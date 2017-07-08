package main.java.rtp.entities;

import main.java.rtp.CSVHeaders;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public class Linia extends RTPentity {

    private static final Logger LOGGER = Logger.getLogger( Linia.class.getName() );
    String linia_id;
    String linia_nom_curt;
    String operador_id;

    public Linia(String val) throws IOException{
        super(val);
        this.header = CSVHeaders.CLASS_LINIA;
        this.setValues();
        this.validate();
    }

    @Override
    void setValues() throws IOException{
        String[] vals = this.valuesString.split(csvSeparator);
        String[] heads = this.header.split(csvSeparator);

        if(vals.length != heads.length){
            LOGGER.log(Level.SEVERE, "Values and header don't match");
            throw new IOException();
        }

        linia_id = vals[0];
        linia_nom_curt = vals[1];
        operador_id = vals[3];
    }

    @Override
    void validate() throws IOException {
       if (checkEmpty(operador_id) || checkEmpty(linia_id)
               || checkEmpty(linia_nom_curt)){
           throw new IOException();
       }
    }
}
