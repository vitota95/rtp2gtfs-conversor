package rtp.entities;

import java.io.IOException;
import java.util.logging.Logger;

public class DiaAtribut extends RTPentity {
    private static final Logger LOGGER = Logger.getLogger(DiaAtribut.class.getName());

    private String dia_atribut_id;
    private String dia_atribut_desc;
    private String dia_atribut_codi;

    public DiaAtribut(String v, String h) throws IOException {
        super(v, h);
        setValues(LOGGER);
    }

    public String getDia_atribut_id() {
        return dia_atribut_id;
    }

    public String getDia_atribut_desc() {
        return dia_atribut_desc;
    }

    public String getDia_atribut_codi() {
        return dia_atribut_codi;
    }

}
