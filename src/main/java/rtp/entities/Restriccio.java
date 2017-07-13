package main.java.rtp.entities;

import com.sun.org.apache.regexp.internal.RESyntaxException;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by javig on 03/07/2017.
 */
public class Restriccio extends RTPentity {
    private static final Logger LOGGER = Logger.getLogger( Restriccio.class.getName() );
    private static String header = null;

    public Restriccio(String val, String header) throws IOException{
        super(val);
        setValues(header, LOGGER, this.getClass());
        validate();
    }

    @Override
    void validate() {

    }
}
