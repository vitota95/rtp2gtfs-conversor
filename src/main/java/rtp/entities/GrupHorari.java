package rtp.entities;

import java.io.IOException;
import java.util.logging.Logger;

public class GrupHorari extends RTPentity {
    private static final Logger LOGGER = Logger.getLogger(GrupHorari.class.getName());

    public String getGrup_horari_id() {
        return grup_horari_id;
    }

    private String grup_horari_id;


    public GrupHorari(String val, String h) throws IOException {
        super(val, h);
        setValues(LOGGER);
    }
}
