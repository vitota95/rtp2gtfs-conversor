package rtp.entities;

import java.io.IOException;
import java.util.logging.Logger;

public class TipusDia2DiaAtribut extends RTPentity {
    private static final Logger LOGGER = Logger.getLogger(TipusDia2DiaAtribut.class.getName());
    private String tipus_dia_id;
    private String dia_atribut_id;


    public TipusDia2DiaAtribut(String v, String h) throws IOException {
        super(v, h);
        setValues(LOGGER);
    }

    public String getTipus_dia_id() {
        return tipus_dia_id;
    }

    public String getDia_atribut_id() {
        return dia_atribut_id;
    }
}
