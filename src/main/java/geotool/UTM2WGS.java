package geotool;

import org.geotools.geometry.DirectPosition2D;
import org.geotools.referencing.CRS;
import org.opengis.geometry.DirectPosition;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

/**
 * Created by Javipc on 16/07/2017.
 */

public class UTM2WGS {

    /**
     * This function uses a mathTransform to change the lat,lon coordinates form the UTM ed 31 (ESPG:25831)
     * to the WGS84 (ESPG:4326)
     *
     * @param lat
     * @param lon
     * @return lat, lon coordinates in WGS84
     */
    public static double[] transEd50Wgs84(double lat, double lon) throws FactoryException, TransformException {

        System.setProperty("org.geotools.referencing.forceXY", "true");
        MathTransform transform = CRS.findMathTransform(CRS.decode("EPSG:25831"), CRS.decode("EPSG:4326"), false);
        DirectPosition srcPoint = new DirectPosition2D(lat, lon);
        DirectPosition dstPoint = new DirectPosition2D();
        transform.transform(srcPoint, dstPoint);

        return dstPoint.getCoordinate();
    }
}

