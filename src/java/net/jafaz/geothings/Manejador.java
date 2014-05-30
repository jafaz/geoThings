/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.unam.unibio.geocosas.mapa;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.unam.unibio.jsonprocessor.datatypes.JSONRecord;
import org.geotools.data.FeatureSource;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.referencing.CRS;
import org.geotools.xml.XML;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;

/**
 *
 * @author jazmin
 */
public class Manejador {

    private static DataBase base;

    public Manejador()throws Exception{
        try{
            base = new DataBase();
            init();
        }catch (IOException ex) {
            Logger.getLogger(Manejador.class.getName()).log(Level.SEVERE, null, ex);
        }

        if ( base != null ){
            base.connectDB();
        }else{
            throw new Exception("No se pudo conectar a la base de datos");
        }
    }


        
}
