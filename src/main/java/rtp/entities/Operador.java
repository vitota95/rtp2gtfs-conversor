package main.java.rtp.entities;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import main.java.rtp.CSVHeaders;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public class Operador extends RTPentity {
    private static final Logger LOGGER = Logger.getLogger( Operador.class.getName() );
    String operador_id;
    String operador_nom_complet;

    public Operador(String values, String h) throws IOException{
        super(values,h);
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

        operador_id = vals[0];
        operador_nom_complet = vals[3];
    }

    @Override
    void validate() throws IOException {
        if (operador_id == null && operador_id.isEmpty() && operador_nom_complet == null
                && operador_nom_complet.isEmpty()){
            LOGGER.log(Level.SEVERE, "Some values are not present");
            throw new IOException();
        }
    }
}
