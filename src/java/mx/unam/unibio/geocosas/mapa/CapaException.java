/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.unam.unibio.geocosas.mapa;

/**
 *
 * @author jazmin
 */
public class CapaException extends Exception{

    private int error;
    

    CapaException(int error){
        this.error = error;
    }

    CapaException(String mensaje){
         super(mensaje);
    }


    @Override
    public String toString(){
        return "Error de capa: "+this.getMessage()+"["+error+"]";
    }

}
