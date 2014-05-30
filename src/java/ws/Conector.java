/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws;

import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import mx.unam.unibio.geocosas.mapa.Capa;
import mx.unam.unibio.geocosas.mapa.Manejador;
import mx.unam.unibio.jsonprocessor.datatypes.JSONRecord;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/** Singleton session bean used to store the name parameter for "/helloWorld" resource
 *
 * @author mkuchtiak
 */
@Singleton
public class Conector {

    // name field
    private String name = "World sld safsdfsd";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public BufferedImage getMapa(String peticion){
        JSONSerializer js = new JSONSerializer();
        BufferedImage mapa = null;
        JSONObject jpeticion = null;
        try {
            jpeticion = (JSONObject)JSONSerializer.toJSON(peticion);
        }catch(JSONException e){
            mapa = Capa.toJPG("error: e.getMessage()");//sld = "error: " + e.getMessage();
        }
        if (jpeticion != null) {
            Manejador  m = Manejador.daManejador(jpeticion);
            
            if ( m != null ){
                mapa = m.toImage();
            }else{
               mapa = Capa.toJPG("Los datos de la consulta son erronos");
            }
        }else{
            mapa = Capa.toJPG("El JSON está mal formado");
        }
        return  mapa;
    }


    public String getSLD(String peticion){

        JSONSerializer js = new JSONSerializer();
        String sld = "";
        JSONObject jpeticion = null;
        try {
            jpeticion = (JSONObject)JSONSerializer.toJSON(peticion);
        }catch(JSONException e){
            sld = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n"+
                        "<error>"+e.getMessage()+"</error>";
        }

        if (jpeticion != null) {
            Manejador m = Manejador.daManejador(jpeticion);
           
            if ( m != null ){
                sld = m.sldToSTring();
            }else{
                sld = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n"+
                        "<error>Los datos de la consulta son erroneos</error>";
            }
        }else{
             sld = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n"+
                        "<error>La consulta esta mal formada</error>";
        }

        return  sld;
    }

    public String daMetadatos() {
        Manejador m = Manejador.daManejador();
        if ( m != null && m.daMetadatos() != null){
           return m.daMetadatos().toString();
        }
        return "{'error': 'Problemas de conexion con la base de datos'}";
    }

}
