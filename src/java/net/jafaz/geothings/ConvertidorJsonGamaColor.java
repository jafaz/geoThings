/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.unam.unibio.geocosas.mapa;

import java.awt.Color;
import java.util.ArrayList;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author jazmin
 */
public class ConvertidorJsonGamaColor {

    private JSONObject jsonGama;
    private int tonos;
    //private GamaColor gama;
    public static final String TONOS = "tonos";
    //public static final String GAMA = "gama";

    public static final String GAMA_BORDE = "borde";
    public static final String GAMA_RELLENO = "relleno";

    private GamaColor gamaBorde = null;
    private GamaColor gamaRelleno = null;

    public ConvertidorJsonGamaColor(JSONObject jsonGama){
        this.jsonGama = jsonGama;

        if (  jsonGama.containsKey(TONOS) )
            tonos = jsonGama.getInt(TONOS);
        else
            tonos = 10;
       /*
        JSONArray borde = leeGama(GAMA_BORDE);
        JSONArray relleno = leeGama(GAMA_RELLENO);

        if ( borde.size() < 1 && borde.size() < 1){

        }
*/
        if ( jsonGama.containsKey(GAMA_BORDE) ){
            gamaBorde = init(GAMA_BORDE);
            JSONArray gamaBordes  = jsonGama.getJSONArray(GAMA_BORDE);
        }else{
            gamaBorde = new GamaColor(Color.PINK, tonos);
        }

        if (jsonGama.containsKey(GAMA_RELLENO) ){
            gamaRelleno = init(GAMA_RELLENO);
            JSONArray gamaRelleno  = jsonGama.getJSONArray(GAMA_RELLENO);
        }else{
            gamaRelleno = new  GamaColor(Color.PINK, tonos);
        }



    }


    private JSONArray leeGama(String llave){
        JSONArray gama = null;
        if (jsonGama.containsKey(llave) ){
             gama  = jsonGama.getJSONArray(llave);
        }else{
            gama = new JSONArray();
        }
        return gama;
    }



     private GamaColor init(String key){
        JSONArray jArrayGama =  null;
        GamaColor gama;
        if (jsonGama.containsKey(key)){
            jArrayGama = jsonGama.getJSONArray(key);
        }
         if( jArrayGama != null ){
            int logitudGama = jArrayGama.size();
            Color color1;
            Color color2;
            switch(logitudGama){
               case 1 :
                    color1 = GamaColor.decodeColor(jArrayGama.getString(0));
                    color1 = color1 != null ? color1 : Color.RED;
                    gama = new GamaColor(color1, tonos);
                    break;
               case 2:
                   color1 = GamaColor.decodeColor(jArrayGama.getString(0));
                   color1 = color1 != null ? color1 : Color.BLUE;
                   color2 = GamaColor.decodeColor(jArrayGama.getString(1));
                   color2 = color2 != null ? color2 : Color.MAGENTA;
                   if ( tonos < 2){
                        gama = new GamaColor(color1, tonos);
                     } else {
                        gama = new GamaColor(color1,color2,tonos);
                   }
                   break;
               default:
                  ArrayList<Color> sColores = new ArrayList<Color>();
                  for (Object o : jArrayGama){
                    color1 =  GamaColor.decodeColor(o.toString());
                    if ( color1!= null)
                        sColores.add(color1);
                  }
                  gama = new GamaColor(sColores,tonos);
           }
        }else{
                gama = new GamaColor(tonos);
        }
        return gama;
    }

/*
         public ConvertidorJsonGamaColor(JSONObject jsonGama, Filtro filtro){
        this.jsonGama = jsonGama;
        //GamaColor gama;
        //tonos = filtro.daConsulta().size();
        if (jsonGama.containsKey(TONOS)){
           tonos = jsonGama.getInt(TONOS);
        }else{
            tonos = 10;
        }

        init();

    }



    private void init(){
        JSONArray jArrayGama =  null;
        if (jsonGama.containsKey(GAMA)){
            jArrayGama = jsonGama.getJSONArray(GAMA);
        }

        if( jArrayGama != null ){
            int logitudGama = jArrayGama.size();
            Color color1;
            Color color2;
            switch(logitudGama){
               case 1 :
                    color1 = GamaColor.decodeColor(jArrayGama.getString(0));
                    color1 = color1 != null ? color1 : Color.RED;
                    gama = new GamaColor(color1);
                    break;
               case 2:
                   color1 = GamaColor.decodeColor(jArrayGama.getString(0));
                   color1 = color1 != null ? color1 : Color.BLUE;
                   color2 = GamaColor.decodeColor(jArrayGama.getString(1));
                   color2 = color2 != null ? color2 : Color.MAGENTA;
                   if ( tonos > 2)
                        gama = new GamaColor(color1);
                   else{
                        gama = new GamaColor(color1,color2,tonos);
                   }
                   break;
               default:
                  ArrayList<Color> sColores = new ArrayList<Color>();
                  for (Object o : jArrayGama){
                    color1 =  GamaColor.decodeColor(o.toString());
                    if ( color1!= null)
                        sColores.add(color1);
                  }
                  gama = new GamaColor(sColores);
           }
        }else{
                gama = new GamaColor();
        }
    }

*/
  public GamaColor daGamaBorde(){
    return gamaBorde;
   }

   public GamaColor daGamaRelleno(){
    return gamaRelleno;
   }
}
