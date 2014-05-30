/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.unam.unibio.geocosas.mapa;

import java.awt.Color;
import java.util.Vector;
import mx.unam.unibio.jsonprocessor.datatypes.JSONRecord;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author jazmin
 */
public class ConvertidorJsonAFiltro {
    private Vector<Filtro> filtros;
    private JSONArray jsonFiltros;

    public static final String ATRIBUTO = "atributo";
    public static final String CONDICION = "condicion";
    public static final String FILTROS = "filtros";
    public static final String COLOR_RELLENO = "colorRelleno";
    public static final String OPACIDAD_RELLENO = "opacidadRelleno";
    public static final String COLOR_BORDE = "colorBorde";
    public static final String OPACIDAD_BORDE = "opacidadBorde";
    public static final String ANCHO_BORDE = "anchoBorde";
    public static final String ANCHO_FIGURA = "anchiFigura";
    public static final String FIGURA = "figura";
    public static final String SELECCIONADOR = "seleccionador";
  


    public static final String GAMA = "gama";




    public ConvertidorJsonAFiltro(JSONArray jsonFiltros) {
        filtros = new Vector<Filtro>();
        this.jsonFiltros = jsonFiltros;
        creaFiltros();
        
    }


    public Vector<Filtro> daFiltros() {
       return this.filtros;
    }

    private void creaFiltros() {
        for ( Object o : jsonFiltros ){
              JSONObject jSonfiltro = (JSONObject)o;
              Filtro filtro = contruyeFitros(jSonfiltro);
              if ( filtro != null ){
                filtros.add(filtro);
            }
        }
    }


   

    private Filtro contruyeFitros(JSONObject jSonFiltro) {
        Filtro filtro = null;
        if ( jSonFiltro.containsKey( ATRIBUTO ) ){
           // String condicion = jSonFiltro.getString(CONDICION);
            String atributo = jSonFiltro.getString(ATRIBUTO);

            filtro = new Filtro(atributo);
            setSeleccionador(jSonFiltro, filtro);
            setCondicion(jSonFiltro, filtro);
            //setColorRelleno(jSonFiltro, filtro);
            //setColorBorde(jSonFiltro, filtro);
            setOpacidadRelleno(jSonFiltro, filtro);
            setOpacidadBorde(jSonFiltro, filtro);
            setAnchoBorde(jSonFiltro, filtro);
            setFigura(jSonFiltro, filtro);
            setDiametroFigura(jSonFiltro, filtro);
            ConvertidorJsonGamaColor gamas = creaGama(jSonFiltro);
            filtro.setGamaBorde(gamas.daGamaBorde());
            filtro.setGamaRelleno(gamas.daGamaRelleno());
            //filtro.setGamaRelleno(creaGama(jSonFiltro, filtro, GAMA_RELLENO));
            
        }

        return filtro;
        
    }

    private void setSeleccionador(JSONObject jSONfiltro, Filtro filtro){
         if (jSONfiltro.containsKey( SELECCIONADOR ) ){
            String seleccionador =  jSONfiltro.getString(SELECCIONADOR);
            filtro.setSeleccionador(seleccionador);
         }
    }


     private void setCondicion(JSONObject jSONfiltro, Filtro filtro){
         if (jSONfiltro.containsKey( CONDICION ) ){
            String condicion =  jSONfiltro.getString(CONDICION);
            filtro.setCondicion(condicion);
         }
    }




   private ConvertidorJsonGamaColor creaGama(JSONObject jSONfiltro){
        //ConvertidorJsonGamaColor gamas;

        return new  ConvertidorJsonGamaColor(jSONfiltro.getJSONObject(GAMA));

    }


  

   /*
    private void setColorRelleno(JSONObject jSONfiltro, Filtro filtro){
        if (jSONfiltro.containsKey( COLOR_RELLENO ) ){
            Color color = Filtro.StringToColor(jSONfiltro.getString(COLOR_RELLENO));
            if ( color != null )
                filtro.setColorRelleno(color);
        }
    }

    private void setColorBorde(JSONObject jSONfiltro, Filtro filtro){
        if (jSONfiltro.containsKey( COLOR_BORDE ) ){
            Color color = Filtro.StringToColor(jSONfiltro.getString(COLOR_BORDE));
            if ( color != null )
                filtro.setColorBorde(color);
        }
    }
*/
    private void setOpacidadRelleno(JSONObject jSONfiltro, Filtro filtro) {
        if (jSONfiltro.containsKey( OPACIDAD_RELLENO ) ){
            double opacidad = jSONfiltro.getDouble(OPACIDAD_RELLENO);
            filtro.setOpacidadRelleno(opacidad);
        }
    }

    private void setOpacidadBorde(JSONObject jSONfiltro, Filtro filtro) {
        if (jSONfiltro.containsKey( OPACIDAD_BORDE ) ){
           double opacidad = jSONfiltro.getDouble(OPACIDAD_BORDE);
            filtro.setOpacidadBorde(opacidad);
        }
    }

    private void setAnchoBorde(JSONObject jSONfiltro, Filtro filtro) {
        if (jSONfiltro.containsKey( ANCHO_BORDE ) ){
           int ancho = jSONfiltro.getInt(ANCHO_BORDE);
           filtro.setAnchoBorde(ancho);
        }
    }

    private void setFigura(JSONObject jSONfiltro, Filtro filtro) {
        if (jSONfiltro.containsKey( FIGURA ) ){
           String figura = jSONfiltro.getString(FIGURA);
           filtro.setFigura(figura);
        }
    }

    private void setDiametroFigura(JSONObject jSONfiltro, Filtro filtro) {
       if (jSONfiltro.containsKey( ANCHO_FIGURA ) ){
           int diametro = jSONfiltro.getInt(ANCHO_FIGURA);
           filtro.setDiametroFigura(diametro);
        }
    }





}
