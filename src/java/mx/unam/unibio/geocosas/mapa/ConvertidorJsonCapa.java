/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.unam.unibio.geocosas.mapa;

import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.referencing.CRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;

/**
 *
 * @author jazmin
 */
public class ConvertidorJsonCapa {

    public final static String ANCHO = "ancho";
    public final static String ALTO = "alto";
    public final static String BBOX = "bbox";
    public final static String ID_CAPA = "id";

    private String id;
    private int ancho;
    private int alto;
    private ReferencedEnvelope bbox;
    private JSONArray jSONbbox ;

    public ConvertidorJsonCapa(JSONObject capa) {
        alto =  capa.containsKey(ALTO) ? capa.getInt(ALTO) : 432;
        ancho = capa.containsKey(ANCHO) ? capa.getInt(ANCHO) : 800;
        id = capa.containsKey(ID_CAPA) ? capa.getString(ID_CAPA) : null;
        jSONbbox = capa.containsKey(BBOX) ? capa.getJSONArray(BBOX) : null;
    }

   private ReferencedEnvelope creaBBOX() {
        ReferencedEnvelope bbox = null;
        if ( jSONbbox != null && jSONbbox.size() == 4){
            bbox = auxCrateBBOX(
                    jSONbbox.getDouble(0),
                    jSONbbox.getDouble(1),
                    jSONbbox.getDouble(2),
                    jSONbbox.getDouble(3));
       }else{
         bbox = auxCrateBBOX(-128.2, -74, 8.2, 37.5);
       }
        return bbox;
    }


    private ReferencedEnvelope auxCrateBBOX(double a, double b, double c, double d){
        ReferencedEnvelope bbox = null;
        try {
            bbox = new ReferencedEnvelope(a, b, c, d, CRS.decode("EPSG:4326"));
        } catch (NoSuchAuthorityCodeException ex) {
            Logger.getLogger(ConvertidorJsonCapa.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FactoryException ex) {
            Logger.getLogger(ConvertidorJsonCapa.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bbox;
    }


   
}
