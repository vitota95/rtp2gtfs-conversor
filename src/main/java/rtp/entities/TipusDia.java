package rtp.entities;

import java.io.IOException;
import java.util.logging.Logger;

public class TipusDia extends RTPentity {

    private static final Logger LOGGER = Logger.getLogger(TipusDia.class.getName());

    private String tipus_dia_id;
    private String tipus_dia_desc;
    private String tipus_dia_codi;

    public TipusDia(String v, String h) throws IOException {
        super(v, h);
        setValues(LOGGER);
    }

    public String getTipus_dia_id() {
        return tipus_dia_id;
    }

    public String getTipus_dia_desc() {
        return tipus_dia_desc;
    }

    public String getTipus_dia_codi() {
        return tipus_dia_codi;
    }

}
