package gtfs;

import rtp.entities.RTPentity;

import java.util.Map;

/**
 * Created by Javipc on 16/07/2017.
 */
public class GTFSParameters {
    private Map<String, RTPentity> RTPobjects;

    public void setRTPobjects(Map<String, RTPentity> objects) {
        this.RTPobjects = objects;
    }

    public Map<String, RTPentity> getRTPobjects() {
        return RTPobjects;
    }

}
