package rtp;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by javig on 03/07/2017.
 * Class to read all rtp files.
 * @author javig
 */
public class InputReader {

    private static final Logger LOGGER = Logger.getLogger( InputReader.class.getName() );

    private HashMap<String, ArrayList> entities;
    private ArrayList<Object> entitiesArray = null;
    private File directory;

    public InputReader(File dir){
        this.directory = dir;
        this.entities = new HashMap<>();
    }

    /***
     *
     * @return entitiesArray
     */

    public HashMap<String, ArrayList> getEntities() throws IOException {
        if (entitiesArray == null){
            readRTPFile();
        }
        return this.entities;
    }

    /**
     * Reads rtp files from parent directory, then
     * sets the RTP entities
     */
    public void readRTPFile() throws IOException{
        ZipFile zif;
        Enumeration<? extends ZipEntry> entries;

        try {
            zif = new ZipFile(directory);
            entries = zif.entries();

            while (entries.hasMoreElements()){
                ZipEntry entry = entries.nextElement();
                String entrySimpleName = new File(entry.getName()).getName().toLowerCase();

                switch (entrySimpleName) {
                    case FileNames.FILE_RTP_EXPEDICIO:
                        this.setRTPEntity(zif.getInputStream(entry), RTPClassNames.CLASS_EXPEDICIO);
                        break;
                    case FileNames.FILE_RTP_ITINERARI:
                        this.setRTPEntity(zif.getInputStream(entry), RTPClassNames.CLASS_ITINERARI);
                        break;
                    case FileNames.FILE_RTP_LINIA:
                        this.setRTPEntity(zif.getInputStream(entry), RTPClassNames.CLASS_LINIA);
                        break;
                    case FileNames.FILE_RTP_OPERADOR:
                        this.setRTPEntity(zif.getInputStream(entry), RTPClassNames.CLASS_OPERADOR);
                        break;
                    case FileNames.FILE_RTP_PARADA:
                        this.setRTPEntity(zif.getInputStream(entry), RTPClassNames.CLASS_PARADA);
                        break;
                    case FileNames.FILE_RTP_PERIODE:
                        this.setRTPEntity(zif.getInputStream(entry), RTPClassNames.CLASS_PERIODE);
                        break;
                    case FileNames.FILE_RTP_RESTRICCIO:
                        this.setRTPEntity(zif.getInputStream(entry), RTPClassNames.CLASS_RESTRICCIO);
                        break;
                    case FileNames.FILE_RTP_TEMPS_ITINERARI:
                        this.setRTPEntity(zif.getInputStream(entry), RTPClassNames.CLASS_TEMPS_ITINERARI);
                        break;
                    case FileNames.FILE_RTP_VEHICLE:
                        this.setRTPEntity(zif.getInputStream(entry), RTPClassNames.CLASS_VEHICLE);
                        break;
                    case FileNames.FILE_RTP_VERSIO:
                        this.setRTPEntity(zif.getInputStream(entry), RTPClassNames.CLASS_VERSIO);
                        break;
                    case FileNames.FILE_RTP_GRUP_HORARI:
                        this.setRTPEntity(zif.getInputStream(entry), RTPClassNames.CLASS_GRUP_HORARI);
                        break;
                    case FileNames.FILE_RTP_HORES_DE_PAS:
                        this.setRTPEntity(zif.getInputStream(entry), RTPClassNames.CLASS_HORES_DE_PAS);
                        break;
                    case FileNames.FILE_RTP_DIA_ATRIBUT:
                        this.setRTPEntity(zif.getInputStream(entry), RTPClassNames.CLASS_DIA_ATRIBUT);
                        break;
                    case FileNames.FILE_RTP_TIPUS_DIA:
                        this.setRTPEntity(zif.getInputStream(entry), RTPClassNames.CLASS_TIPUS_DIA);
                        break;
                    case FileNames.FILE_RTP_TIPUS_DIA_2_DIA_ATRIBUT:
                        this.setRTPEntity(zif.getInputStream(entry), RTPClassNames.CLASS_TIPUS_DIA_2_DIA_ATRIBUT);
                        break;
                    case FileNames.FILE_NOM_CURT:
                        this.setRTPEntity(zif.getInputStream(entry), RTPClassNames.CLASS_NOM_CURT);
                        break;
                    default:
                        break;
                }
            }
        }
        catch (IOException e){
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

    }

    private void setRTPEntity(InputStream is, String entityName) throws IOException{
        entitiesArray = new ArrayList<>();
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        String line;

        try{
            String header = in.readLine();
            String className = RTPClassNames.ENTITIES_PATH + entityName;
            while((line = in.readLine()) != null) {
                Constructor ctor = Class.forName(className).getConstructor(String.class, String.class);
                //Strings can't contain commas because GTFS use it as file separator
                line = line.replaceAll(",", "");
                Object object = ctor.newInstance(line,header);
                entitiesArray.add(object);
            }

            entities.put(entityName, entitiesArray);
        }
        catch (ClassNotFoundException | InstantiationException | NoSuchMethodException | IllegalAccessException | IOException | InvocationTargetException e){
            LOGGER.log(Level.WARNING, e.toString(), e);
        }
    }
}
