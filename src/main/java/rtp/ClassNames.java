package main.java.rtp;

import java.util.ArrayList;

/**
 * Created by javig on 03/07/2017.
 */
public interface ClassNames {
    String ENTITIES_PATH = "main.java.rtp.entities.";
    String CLASS_VERSIO = "Versio";
    String CLASS_OPERADOR = "Operador";
    String CLASS_PARADA = "Parada";
    String CLASS_LINIA = "Linia";
    String CLASS_EXPEDICIO = "Expedicio";
    String CLASS_RESTRICCIO = "Restriccio";
    String CLASS_PERIODE = "Periode";
    String CLASS_ITINERARI = "Itinerari";
    String CLASS_VEHICLE = "Vehicle";
    String CLASS_TEMPS_ITINERARI = "TempsItinerari";
    String CLASS_TRAJECTE = "Trajecte";

    /**
     * Created by javig on 07/07/2017.
     */
    interface MandatoryRTPEntities {
        String[] fields = {CLASS_VERSIO, CLASS_OPERADOR, CLASS_PARADA, CLASS_LINIA,
        CLASS_EXPEDICIO, CLASS_PERIODE, CLASS_ITINERARI, CLASS_TEMPS_ITINERARI, CLASS_TRAJECTE};
    }
}
