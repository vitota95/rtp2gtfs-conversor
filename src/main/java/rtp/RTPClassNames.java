package rtp;

/**
 * Created by javig on 03/07/2017.
 */
public interface RTPClassNames {
    String ENTITIES_PATH = "rtp.entities.";
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
    String CLASS_GRUP_HORARI = "GrupHorari";
    String CLASS_HORES_DE_PAS = "HoresDePas";
    String CLASS_TIPUS_DIA = "TipusDia";
    String CLASS_DIA_ATRIBUT = "DiaAtribut";
    String CLASS_TIPUS_DIA_2_DIA_ATRIBUT = "TipusDia2DiaAtribut";

    /**
     * Created by javig on 07/07/2017.
     */
    interface MandatoryRTPEntities {
        String[] filesMandatory = {CLASS_VERSIO, CLASS_OPERADOR, CLASS_PARADA, CLASS_LINIA,
                CLASS_EXPEDICIO, CLASS_PERIODE, CLASS_ITINERARI, CLASS_TRAJECTE};
        String horesDePas = CLASS_HORES_DE_PAS;
        String[] tempsItinerari_GrupHorari = {CLASS_GRUP_HORARI, CLASS_TEMPS_ITINERARI};
    }
}
