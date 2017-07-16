package rtp.entities;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public class Operador extends RTPentity {
    private static final Logger LOGGER = Logger.getLogger( Operador.class.getName() );
    String operador_id;
    String operador_nom_complet_public;

    public Operador(String val, String h)  throws IOException{
        super(val, h);
        setValues(LOGGER, this.getClass());
    }
}
