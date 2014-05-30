/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.unam.unibio.geocosas.mapa;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import mx.unam.unibio.jsonprocessor.datatypes.JSONRecord;
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



public class Main {

public static void print(Object o){
    System.out.println(o.toString());
}

public static void print(Object a, Object b){
    print(a.toString()+" : "+b.toString());
}

    public static void main( String[] args ){



        JSONArray gamaBorde = new JSONArray();
        gamaBorde.add("ff0000");
        gamaBorde.add("0000ff");
        /*gamaBorde.add("330000");
        gamaBorde.add("ff000");
        gamaBorde.add("ff0000");
        gamaBorde.add("ff0000");*/
        //gamaBorde.add("000000");


        JSONArray gamaRelleno = new JSONArray();
        //gamaRelleno.add("000000");
         //gamaRelleno.add("00aa00");

        JSONObject gama1  =new JSONObject();
        gama1.put(ConvertidorJsonGamaColor.GAMA_BORDE,gamaBorde);
        gama1.put(ConvertidorJsonGamaColor.GAMA_RELLENO,gamaRelleno);
        gama1.put(ConvertidorJsonGamaColor.TONOS, 10);
/*
        JSONObject gama11  =new JSONObject();
        gama11.put(ConvertidorJsonAFiltro.GAMA,gamaRelleno);
        gama11.put(ConvertidorJsonGamaColor.TONOS, 10);
*/

        JSONArray gamaBorde2 = new JSONArray();
        gamaBorde2.add("ff3300");
        gamaBorde2.add("ff0022");

        JSONArray gamaRelleno2 = new JSONArray();
        gamaRelleno2.add("335599");
        gamaRelleno2.add("aaff00");

        JSONObject gama2  =new JSONObject();
        gama2.put(ConvertidorJsonGamaColor.GAMA_BORDE,gamaBorde2);
        gama2.put(ConvertidorJsonGamaColor.GAMA_RELLENO,gamaRelleno2);
        gama2.put(ConvertidorJsonGamaColor.TONOS, 10);
/*
        JSONObject gama22  =new JSONObject();
        gama22.put(ConvertidorJsonAFiltro.GAMA,gamaRelleno2);
        gama22.put(ConvertidorJsonGamaColor.TONOS, 12);
*/

        JSONObject c = new JSONObject();
        //JSONRecord c = new JSONRecord();
        c.put(Manejador.ID_CAPA,"ug1");
        c.put(Manejador.ALTO, 432);
        c.put(Manejador.ANCHO, 800);

        JSONArray bbox = new JSONArray();
        bbox.add(-128.2);
        bbox.add(-74);
        bbox.add(8.2);
        bbox.add(37.5);
        c.put(Manejador.BBOX, bbox);
        
        JSONArray fs = new JSONArray();
        c.put(ConvertidorJsonAFiltro.FILTROS,fs);


        JSONObject f = new JSONObject();

        //f.put(ConvertidorJsonAFiltro.ANCHO_BORDE, 2);
        
        f.put(ConvertidorJsonAFiltro.ATRIBUTO, "objectid");
        //f.put(ConvertidorJsonAFiltro.ATRIBUTO, "tipo");
        //f.put(ConvertidorJsonAFiltro.SELECCIONADOR, "ALL");
        f.put(ConvertidorJsonAFiltro.SELECCIONADOR, "DISTINCT");
        f.put(ConvertidorJsonAFiltro.CONDICION, "tipo='Internacional'");
        //f.put(ConvertidorJsonAFiltro.COLOR_BORDE, "330000");
        //f.put(ConvertidorJsonAFiltro.COLOR_RELLENO, "0000ff");
       // f.put(ConvertidorJsonAFiltro.COLOR_BORDE, gamaBorde);
        //f.put(ConvertidorJsonAFiltro.COLOR_RELLENO, gamaRelleno);
        f.put(ConvertidorJsonAFiltro.GAMA, gama1);
       // f.put(ConvertidorJsonGamaColor.GAMA_BORDE, gama11);
        f.put(ConvertidorJsonAFiltro.OPACIDAD_BORDE, 0.5);
        f.put(ConvertidorJsonAFiltro.OPACIDAD_RELLENO, 1);
        f.put(ConvertidorJsonAFiltro.FIGURA, "triangulo");
        f.put(ConvertidorJsonAFiltro.ANCHO_FIGURA, 30);



        JSONObject ff = new JSONObject();

        //ff.put(ConvertidorJsonAFiltro.ANCHO_BORDE, 2);
        //ff.put(ConvertidorJsonAFiltro.ANCHO_FIGURA, 15);
        //ff.put(ConvertidorJsonAFiltro.ATRIBUTO, "objectid");
        //ff.put(ConvertidorJsonAFiltro.SELECCIONADOR, "ALL");
        ff.put(ConvertidorJsonAFiltro.ATRIBUTO, "tipo");
        ff.put(ConvertidorJsonAFiltro.SELECCIONADOR, "ALL");
        ff.put(ConvertidorJsonAFiltro.CONDICION, "tipo='Nacional'");
        //ff.put(ConvertidorJsonAFiltro.COLOR_BORDE, "bbb000");
        //ff.put(ConvertidorJsonAFiltro.COLOR_RELLENO, "bb4433");
       // f.put(ConvertidorJsonAFiltro.COLOR_BORDE, gamaBorde2);
      //  f.put(ConvertidorJsonAFiltro.COLOR_RELLENO, gamaRelleno2);
        ff.put(ConvertidorJsonAFiltro.GAMA, gama2);
       // ff.put(ConvertidorJsonGamaColor.GAMA_BORDE, gama22);
        
        ff.put(ConvertidorJsonAFiltro.OPACIDAD_BORDE, 0.5);
        ff.put(ConvertidorJsonAFiltro.OPACIDAD_RELLENO, 0.5);
        ff.put(ConvertidorJsonAFiltro.FIGURA, "cruz");
        ff.put(ConvertidorJsonAFiltro.ANCHO_FIGURA, 15);

        fs.add(f);
        fs.add(ff);

        c.put("filtros", fs);


        print (c);
        Manejador m = null;

        m = Manejador.daManejador(c);

        if ( m != null ){
            print(m.sldToSTring());
            BufferedImage bimage = m.toImage();
             try {
              //  ImageIO.w
                ImageIO.write(bimage, "jpg", new File("holas.jpg"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            print("Error de");

        }

        

/*
         m = null;
        m = Manejador.daManejador();

        if (  m != null && m.daMetadatos() != null ){

            print(m.daMetadatos().toString());


        }else{
            print ( "{'error': 'Problemas de conexion con la base de datos'}");
        }
*/
        print("http://10.1.6.198:8080/WS-Mapas/resources/sld?json="+c);

/*
 * 
        String[] col = new String[3];
        col[0]="000000";
        col[1]="00ff22";
        col[2]="00ff23";


        GamaColor gama = new GamaColor("#000000,ffffff,ff3344,998877");
        print(gama);
        ArrayList<Color> g = gama.daGama();

        g.add(Color.red);
        gama = new GamaColor(g);
        print(gama);

        gama = new GamaColor(col);
        print(gama);

        //gama = new ColorGama(new Color(49,99,199), new Color(0,0,0), 200);
        gama = new GamaColor(new Color(0,0,0), new Color(0,0,0), 0);
        print(gama);


        gama = new GamaColor("333333","000000", 5);
        print("long gama", gama);
*/
        /*
        byte cc = 1;

        String binario = "101";
        byte bb = Byte.parseByte(binario, 2);
        byte dos = 2;
        //bb = (byte) (bb <<2);
        byte cero = 0;
        print("bb",bb);
        print("bb|cero",cero|bb);
      //  bb = bb<<dos;
        print(binario, bb);
        print(Integer.toBinaryString(bb), bb);


*/

        /*ReferencedEnvelope bbx = null;
        try {
            bbx = new ReferencedEnvelope(-128.2, -74, 8.2, 37.5, CRS.decode("EPSG:4326"));
        } catch (NoSuchAuthorityCodeException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FactoryException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
      

        BufferedImage bimage = m.toImage(bbx, 800, 432);

        try {
              //  ImageIO.w
                ImageIO.write(bimage, "jpg", new File("hola.jpg"));
            } catch (Exception e) {
                e.printStackTrace();
            }  

*/
        /*
        JSONArray capas = new JSONArray();

        JSONRecord capa1 = new JSONRecord();
        capa1.put("id","ug1");

        JSONArray filtros1 = new JSONArray();

        JSONArray f1 = new JSONArray();
        f1.add("entidad=Nacional");

        JSONArray c1 = new JSONArray();
        c1.add("#fff000");

        JSONArray c11 = new JSONArray();
        c11.add("#000");


        JSONRecord filtro1 = new JSONRecord();
        //filtro1.put(Filtro, capa1)
        filtro1.put("filtro", f1.toString());
        filtro1.put("indicador", "color");
        filtro1.put("color", c1);
        filtro1.put("opacidad", "1");
        filtro1.put("colorcontorno", c11);
        filtro1.put("opacidadContorno", "1");
        filtro1.put("anchocontorno", "2");
        filtro1.put("figura", "circulo");
        filtro1.put("ancho","7");

        filtros1.add(filtro1.toString());

        JSONArray f2 = new JSONArray();
        f2.add("entidad=Internacional");

        JSONArray c2 = new JSONArray();
        c2.add("#ff0000");

        JSONArray c12 = new JSONArray();
        c12.add("#000fff");


        JSONRecord filtro2 = new JSONRecord();
        filtro2.put("filtro", f2.toString());
        filtro2.put("tipo", "POINT");
        filtro2.put("indicador", "color");
        filtro2.put("color", c2.toString());
        filtro2.put("opacidad", "1");
        filtro2.put("colorcontorno", c12.toString());
        filtro2.put("opacidadContorno", "1");
        filtro2.put("anchocontorno", "2");
        filtro2.put("figura", "circulo");
        filtro2.put("triangulo","10");

        filtros1.add(filtro2.toString());



        capa1.put("filtros", filtros1.toString());

        capas.add(capa1.toString());
*/
    }

}
