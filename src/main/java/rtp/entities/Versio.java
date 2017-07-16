package rtp.entities;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public class Versio extends RTPentity {

    private static final Logger LOGGER = Logger.getLogger( Versio.class.getName() );
    String data;

    public Versio(String val, String h) throws IOException{
        super(val, h);
        setValues(LOGGER, this.getClass());
    }

//    @Override
//    void validate() throws IOException{
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//            Date d = sdf.parse(data);
//            if (!data.equals(sdf.format(d))) {
//                LOGGER.log(Level.SEVERE, "Data not well formatted");
//                throw new IOException("Data not well formatted");
//            }
//        } catch (ParseException ex) {
//            ex.printStackTrace();
//        }
//    }
}
