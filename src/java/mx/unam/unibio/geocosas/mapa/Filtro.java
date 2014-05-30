/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.unam.unibio.geocosas.mapa;


import java.awt.Color;
import java.util.ArrayList;


/**
 *
 * @author jazmin
 */
public class Filtro {

    private String atributo;
    private ArrayList<String> rasgos;
    
    private String figura;
    private int diametroFigura;
        
    private String colorBorde;
    private int anchoBorde;
    private String colorRelleno;
    private String opacidadRelleno;


    public static final String FIGURA_CIRCULO = "circulo";
    public static final String FIGURA_CRUZ = "cruz";
    public static final String FIGURA_CUADRO = "cuadro";
    public static final String FIGURA_TRIANGULO = "triangulo";
    public static final String FIGURA_X = "x";
    public static final String FIGURA_ESTRELLA = "estrella";


    public Filtro(){
        init();
    }

    public Filtro(String atributo, ArrayList<String> rasgos)throws FiltroException{

        if ( atributo == null || atributo.equals("") || rasgos == null || rasgos.size() < 1 ) {
            throw new FiltroException("El atributo o el arreglo de rasgo no puede ser vacio");
        }else{
            this.atributo = atributo;
            init();
        }
        
    }

    private void init(){
        colorRelleno = "#ffffff";
        opacidadRelleno = "0,5";
        colorBorde = "#000000";
        anchoBorde = 1;
        diametroFigura = 5;
        figura = FIGURA_CIRCULO;
    }

     protected static Color StringToColor(String sColor){
        Color color = null;
        try{
            color = Color.decode("#"+sColor);
        }catch(NumberFormatException nfe){

        }
        return color;
     }


     public void setRasgos(ArrayList<String> rasgos){
        this.rasgos = rasgos;
     }

     public  void setColorBorde(String color){
        this.colorBorde = color;
     }


     public  void setColorRelleno(String color){
        this.colorRelleno = color;
     }

     public  void setOpacidadRelleno( String opacidad ){
        this.opacidadRelleno = opacidad;
     }

     public String daOpacidadRelleno(){
         return this.opacidadRelleno;
     }

     public  void setFigura (String figura){
        figura = figura.trim();
        figura = figura.toLowerCase();
        if ( figura.equals(FIGURA_CIRCULO) ||
             figura.equals(FIGURA_CRUZ) ||
             figura.equals(FIGURA_CUADRO) ||
             figura.equals(FIGURA_TRIANGULO) ||
             figura.equals(FIGURA_X) ||
             figura.equals(FIGURA_ESTRELLA)
             )
            this.figura = figura;
     }

     public String daFigura(){
        return this.figura;
     }

     public  void setAnchoBorde( int anchoBorde ){
        this.anchoBorde = anchoBorde;
     }

     public int daAnchoBorde(){
         return this.anchoBorde;
     }

     public  void setDiametroFigura( int diametroFiguara ){
        this.diametroFigura = diametroFiguara;
     }

     public int daDiametroFigura(){
         return this.diametroFigura;
     }

     public String daAtributo(){
        return atributo;
     }

     public String daColorBorde(){
         return "#"+colorBorde;
     }


      public String daColorRelleno(){
         return "#"+colorRelleno;
     }

     public ArrayList<String> daRasgos(){
        return this.rasgos;
     }


}
