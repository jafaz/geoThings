/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.unam.unibio.geocosas.mapa;

/**
 *
 * @author jazmin
 */
public class FiltroException extends Exception{

    private int error;


    FiltroException(int error){
        this.error = error;
    }

    FiltroException(String mensaje){
         super(mensaje);
    }


    @Override
    public String toString(){
        return "Error de capa: "+this.getMessage()+"["+error+"]";
    }

}
