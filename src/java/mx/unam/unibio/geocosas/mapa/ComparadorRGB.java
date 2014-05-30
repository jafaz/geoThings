/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.unam.unibio.geocosas.mapa;

/**
 *
 * @author Jazmin Alvarez
 */
public class ComparadorRGB {
    private int rgbInicial;
    private int rgbFinal;
    private int siguiente;
    private boolean decrese;


    private int diferencia;
    private double incremento;
    private int tonos;



    public ComparadorRGB(int rgbInicial, int rgbFinal, int tonos){
        this.siguiente = 0;
        this.rgbInicial = rgbInicial;
        this.rgbFinal = rgbFinal;
        decrese = (rgbFinal - rgbInicial) < 0;
        diferencia = Math.abs(rgbFinal - rgbInicial);
        this.tonos = tonos;
        incremento = new Double(diferencia) / new Double(tonos);
        Main.print("rgbInicial",rgbInicial);
        Main.print("rgbFinal",rgbFinal);
    }


    public void setTono(int tono){
        this.tonos = tono;
        incremento = new Double(diferencia) / new Double(tonos);
    }

    public int daNumeroTonos(){
        return diferencia < tonos ? diferencia : tonos;
    }

    public int daInicio(){
        return rgbInicial;
    }

    public int daFinal(){
        return rgbFinal;
    }


    public int daSiguiente(){
       return daColor(siguiente++);
    }


    public int daColor(int posicion){
        int siguienteColor;
        if(rgbInicial == rgbFinal){
           // Main.print(rgbInicial,);
            siguienteColor = rgbInicial;
        }
        else if(posicion < 1){
            siguienteColor = rgbInicial;
        }else if ( posicion < tonos - 1){
            siguienteColor = decrese ?
                    (int) (new Double(rgbFinal) + (incremento * (tonos - posicion ))):
                    (int) (new Double(rgbInicial) + (incremento * posicion + 1));
        }else{
            siguienteColor = rgbFinal;
        }
        /*
        Main.print("rgbInicial",rgbInicial);
        Main.print("rgbFinal",rgbInicial);
        Main.print("siguienteColor", siguienteColor);
        Main.print("incremento",incremento);
        Main.print("posicion",posicion);
        Main.print("decrese",decrese);
        Main.print("diferencia",diferencia);
        Main.print("tons",tonos);
         * */
         
        return siguienteColor;
    }

}
