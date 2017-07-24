package gtfs;

import rtp.RTPClassNames;
import rtp.entities.Operador;
import rtp.entities.RTPentity;

import java.io.IOException;
import java.util.List;

/**
 * Created by javig on 03/07/2017.
 */
public class Agency extends GTFSEntity {
    private static final List<String> header = GtfsCsvHeaders.CLASS_AGENCY;
    private static final AgencyValues agencyValues = new AgencyValues();

    @Override
    Object getGtfsValues() {
        return agencyValues;
    }

    @Override
    void getEntityParameters(String key, RTPentity value) throws IllegalAccessException {
        try {
            if (key.equalsIgnoreCase(RTPClassNames.CLASS_OPERADOR)) {
                final Operador operador = (Operador) value;
                agencyValues.agency_id = operador.getOperador_id();
                agencyValues.agency_name = operador.getOperador_nom_complet_public();
            } else {
                throw new IOException("Agency unknown parameter: " + key);
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }


    @Override
    List<String> getHeader() {
        return header;
    }
}

class AgencyValues
{
    String agency_name;
    String agency_id;
    static final String agency_url = "https://www.changeURL.notreal";
    static final String agency_timezone = "Europe/Madrid";
    static final String agency_lang = "es";
    static final String agency_phone = "";
    static final String agency_fare_url = "";
}
