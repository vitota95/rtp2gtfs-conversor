package main.java.rtp;

import main.java.rtp.entities.Expedicio;
import main.java.rtp.entities.RTPentity;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
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

    private Map entities;
    private ArrayList entitiesArray = null;
    private File directory;

    public InputReader(File dir){
        this.directory = dir;
        this.entities = new HashMap<String,ArrayList>();
    }

    /***
     *
     * @return entitiesArray
     */

    public Map getEntities() throws IOException{
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
                        this.setRTPEntity(zif.getInputStream(entry), ClassNames.CLASS_EXPEDICIO);
                        break;
                    case FileNames.FILE_RTP_ITINERARI:
                        this.setRTPEntity(zif.getInputStream(entry), ClassNames.CLASS_ITINERARI);
                        break;
                    case FileNames.FILE_RTP_LINIA:
                        this.setRTPEntity(zif.getInputStream(entry), ClassNames.CLASS_LINIA);
                        break;
                    case FileNames.FILE_RTP_OPERADOR:
                        this.setRTPEntity(zif.getInputStream(entry), ClassNames.CLASS_OPERADOR);
                        break;
                    case FileNames.FILE_RTP_PARADA:
                        this.setRTPEntity(zif.getInputStream(entry), ClassNames.CLASS_PARADA);
                        break;
                    case FileNames.FILE_RTP_PERIODE:
                        this.setRTPEntity(zif.getInputStream(entry), ClassNames.CLASS_PERIODE);
                        break;
                    case FileNames.FILE_RTP_RESTRICCIO:
                        this.setRTPEntity(zif.getInputStream(entry), ClassNames.CLASS_RESTRICCIO);
                        break;
                    case FileNames.FILE_RTP_TEMPS_ITINERARI:
                        this.setRTPEntity(zif.getInputStream(entry), ClassNames.CLASS_TEMPS_ITINERARI);
                        break;
                    case FileNames.FILE_RTP_TRAJECTE:
                        this.setRTPEntity(zif.getInputStream(entry), ClassNames.CLASS_TRAJECTE);
                        break;
                    case FileNames.FILE_RTP_VEHICLE:
                        this.setRTPEntity(zif.getInputStream(entry), ClassNames.CLASS_VEHICLE);
                        break;
                    case FileNames.FILE_RTP_VERSIO:
                        this.setRTPEntity(zif.getInputStream(entry), ClassNames.CLASS_VERSIO);
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
        entitiesArray = new ArrayList();
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        String line;

        try{
            String header = in.readLine();
            String className = ClassNames.ENTITIES_PATH + entityName;
            while((line = in.readLine()) != null) {
                Class<?> cl = Class.forName(className);
                Constructor<?> ctor = cl.getConstructor(String.class);
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
