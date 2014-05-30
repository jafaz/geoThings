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
 * @author root
 */
public class GamaColor {

    private ComparadorRGB comparadorR;
    private ComparadorRGB comparadorG;
    private ComparadorRGB comparadorB;

    private int transicion;
    private ArrayList<Color> gama;

    public static final int NUMERO_TONOS = 10;
    public final int T = 0;
    public final int C_T = 1;
    public final int C_C_T = 2;
    public final int Cs_T = 3;
   /*
    public final int TONO = 0;
    public final int TONO = 0;
    public final int TONO = 0;
    public final int TONO = 0;
    public final int TONO = 0;
*/


    private int caso = -1;

  

   public GamaColor(int transicion){
       init(transicion);
       
   }

   private void init(int transicion){
       caso = T;
       this.transicion = transicion;
       init(Color.GRAY, Color.PINK, transicion);
       Main.print("gamaaaaaaaaaa",transicion);
   }

   public GamaColor(Color color, int transicion){
       caso = C_T;
        init(color,transicion);

   }

   private void init(Color color, int transicion){
       this.transicion = transicion <= 0? 1 : transicion;
       // this.transicion = transicion;

        gama = new ArrayList<Color>(transicion);
        for(int i = 0; i < transicion; i++){
            gama.add(color);
       }
       initCompadores(color, color, transicion);
       Main.print(gama, gama.size());
   }

   /*
   public GamaColor(String inicio, String fin, int numeroColores){
       Color colorTemporal = decodeColor(inicio);
       Color colorInicial = colorTemporal == null ? Color.RED : colorTemporal;
       colorTemporal = decodeColor(fin);
       Color colorFinal =  colorTemporal == null ? Color.YELLOW : colorTemporal;
       init(colorInicial,colorFinal,numeroColores);
       
   }
*/
   public GamaColor(Color colorInicial, Color colorFinal, int numeroColores){
       caso = C_C_T;
        init(colorInicial,colorFinal,numeroColores);
   }

     private void init(Color colorInicial, Color colorFinal, int numeroColores){
       this.transicion = numeroColores > 0 ? numeroColores : 10;
       gama = new ArrayList<Color>(transicion);
       //creamos un comparador entre el color ROJO, VERDE y AZUL para crear la gama
       initCompadores(colorInicial, colorFinal, numeroColores);

       //Tomamos la diferencia entre tonos ROJO VERDE Y AZUL mayor y esa la tomamos como transisicon
       transicion = Math.max(comparadorR.daNumeroTonos(),
                                        Math.max(comparadorG.daNumeroTonos(),
                                                 comparadorB.daNumeroTonos()));

       //Igualamos la transicion para cada tono
       comparadorR.setTono(transicion);
       comparadorG.setTono(transicion);
       comparadorB.setTono(transicion);

       generaGamaColores();
   }

   /*
   public GamaColor(String stringColores){
        String[] sColores = stringColores.split(",");
        ArrayList<Color> colores = new ArrayList<Color>();
        Color color;
        for( String sColor : sColores){
            color = decodeColor(sColor.trim());
            if ( color != null ) {
                colores.add(color);
            }
        }
        this.transicion = colores.size();
        if ( transicion > 0){
           gama = colores;
        }
   }
*/

   public GamaColor(ArrayList<Color>  colores, int transicion){
       if (colores.size() > 0 ){
           caso = Cs_T;
           Main.print("aqui", colores.size());
            init(colores, transicion);
       }else{
           Main.print("aquooooooooo");
           caso = T;
           init(transicion);
       }
   }

   public void init(ArrayList<Color>  colores, int numColores){
        this.transicion = colores.size();
        if ( numColores <= 0){
            this.transicion = 10;
            init(Color.RED,Color.YELLOW, numColores);
        }else{
            int contador = 0;
            int factorAgrupamineto = (int) Math.ceil(new Double(numColores) /
                                                     new Double(colores.size()));
            int j=0;
            gama = new ArrayList<Color>(numColores);
            for( int i = 0; i < numColores; i++ ){
                if ( contador == factorAgrupamineto){
                    j++;
                    contador = 0;
                }
                gama.add(colores.get(i*j));
                contador ++;
            }
/*
            for( int i = 0 ; i < numColores; i++){
                contador = contador == 3 ? 0 : contador;
                gama.add(colores.get(contador));
                contador++;
            }
 * */

            //this.gama = colores;
        }
        Main.print("entre", gama.size());
        Main.print("numeroColores", numColores);
   }

 
/*
   public GamaColor(String[] colores){
        init(colores);
   }
*/
/*
   public GamaColor(JSONObject jsonGama, String gamaKey, String tonoKey ) {
       if (jsonGama.containsKey(gamaKey)){
           transicion = jsonGama.getInt(tonoKey);
            init(jsonGama, gamaKey, transicion);
       }else{
            init(jsonGama, gamaKey, 10);
       }
    }


   public GamaColor(JSONObject jsonGama, String gamaKey, int transicion) {
        init(jsonGama, gamaKey, transicion);
    }


   private void init(JSONObject jsonGama, String gamaKey, int transicion) {
    JSONArray jArrayGama =  null;
        if (jsonGama.containsKey(gamaKey)){
            jArrayGama = jsonGama.getJSONArray(gamaKey);
        }

        if (jArrayGama != null){
           int logitudGama = jArrayGama.size();
           switch(logitudGama){
               case 1 :
                   Color color = decodeColor(jArrayGama.getString(0));
                   color = color != null ? color : Color.RED;
                  init(color,color,1);
                  break;
               case 2:
                   Color tmp = decodeColor(jArrayGama.getString(0));
                   Color uno = tmp != null ? tmp : Color.BLUE;
                   tmp = decodeColor(jArrayGama.getString(1));
                   Color dos = tmp != null ? tmp : Color.MAGENTA;
                   if ( transicion > 2 ){
                        init(uno,dos,transicion);
                   }else{
                        init(uno,dos,10);
                   }
                   break;
               default:
                  ArrayList<String> sColores = new ArrayList<String>();
                  for (Object o : jArrayGama){
                    sColores.add(o.toString());
                  }
                  init(sColores);
           }
        }
   }*/

   /*

   private void init(ArrayList<String> colores){
          this.transicion = colores.size();
      gama = new ArrayList<Color>(transicion);
      if ( validaGama(colores) ){
          for ( int i = 0; i < transicion; i++){
            gama.add(decodeColor(colores.get(i)));
          }
       }else{
            init(Color.RED,Color.YELLOW, colores.size());
       }
   }

*//*
   private void init(Color color, int transicion){
       this.transicion = transicion <= 0? 1 : transicion;
       // this.transicion = transicion;

        gama = new ArrayList<Color>(transicion);
        for(int i = 0; i < transicion; i++){
            gama.add(color);
       }
       initCompadores(color, color, transicion);
       Main.print(gama, gama.size());
   }
*/
  /* private void init(String[] colores){
        this.transicion = colores.length;
      gama = new ArrayList<Color>(transicion);
      if ( validaGama(colores) ){
          for ( int i = 0; i < transicion; i++){
            gama.add(decodeColor(colores[i]));
          }
       }else{
            init(Color.RED,Color.YELLOW, colores.length);
       }
   }*/

   private void initCompadores(Color colorInicial, Color colorFinal, int numeroColores){
       comparadorR = new ComparadorRGB(colorInicial.getRed(), colorFinal.getRed(), transicion);
       comparadorG = new ComparadorRGB(colorInicial.getGreen(), colorFinal.getGreen(), transicion);
       comparadorB = new ComparadorRGB(colorInicial.getBlue(), colorFinal.getBlue(), transicion);
   }
 
 

   public static boolean validaGama(String[] colores){
       boolean valido = colores.length > 0? true: false;
       for ( String color : colores ){
            if ( decodeColor(color) == null )
               return false;
       }
       return valido;
   }


     public static boolean validaGama(ArrayList<String> colores){
       boolean valido = colores.size() > 0? true: false;
       for ( String color : colores ){
            if ( decodeColor(color) == null )
               return false;
       }
       return valido;
   }


    public static Color decodeColor(String sColor){
        sColor = sColor.toString();
        Color color;
        try{
            color = Color.decode(sColor);
        }catch(NumberFormatException nfe){
           color = Color.decode("#"+sColor);
        }
        return color;
    }


   private static Color decodeCodlor(String sColor){
        sColor = sColor.toString();
        Color color;
        try{
            color = Color.decode(sColor);
            if ( color == null ){
                color = Color.decode("#"+sColor);
            }
        }catch(NumberFormatException nfe){
            color = null;
        }
        return color;
    }


     private void generaGamaColores(){
        for ( int i = 0; i < this.transicion  ; i++){
            Main.print("hola",i);
            Main.print("transicion",daColor(i));
            gama.add(daColor(i));
        }
    }


    private Color daColor(int i){
        //Main.print("color",i);
        return new Color(comparadorR.daColor(i),
                               comparadorG.daColor(i),
                               comparadorB.daColor(i));
    }

    public String toString(){
        String cadena = "longitud: "+gama.size()+" arreglo: [";
        int i = 1;
        for( Color color : gama ){
            cadena += "\n"+i+" R:"+color.getRed()+" G:"+color.getGreen()+" B:"+color.getBlue();
            i++;
        }
        return cadena+=" ]";
    }

    public ArrayList<Color> daGama(){
        return gama;
    }


    

    public void reCalculaGama(int numeroTonos){
        if ( this.transicion > numeroTonos){
            Color colorInicial = gama.get(0);
            Color colorFinal = gama.get(gama.size()-1);
            switch(caso){
                case T:
                    init(numeroTonos);
                    break;
                case C_T:
                    init(colorInicial,numeroTonos);
                    break;
                case C_C_T:
                    init(colorInicial,colorFinal, numeroTonos);
                    break;
                case Cs_T:
                    init(gama,numeroTonos);
                    break;
                default:
                   init(numeroTonos);
                   break;
            }
        }
    }




    public void reCalculaGamaTMP(int numeroTonos){
        if ( this.transicion > numeroTonos){

             Color colorInicial = gama.get(0);
             Color colorFinal = gama.get(gama.size()-1);
            init(colorInicial, colorFinal, numeroTonos);
        }
    }


    public int daNumeroTonos(){
        return transicion;
    }
}
