package rtp.entities;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public class Parada extends RTPentity {
    private static final Logger LOGGER = Logger.getLogger( Parada.class.getName() );

    private String parada_punt_id;
    private String parada_punt_desc_curta;
    private String coord_x;
    private String coord_y;
    private String parada_id;

    public String getParada_id() {
        return parada_id;
    }

    public String getParada_punt_id() {
        return parada_punt_id;
    }

    public String getParada_punt_desc_curta() {
        return parada_punt_desc_curta;
    }

    public String getCoord_x() {
        return coord_x;
    }

    public String getCoord_y() {

        return coord_y;
    }

    public Parada(String val, String h)  throws IOException{
        super(val, h);
        setValues(LOGGER);
    }
}
