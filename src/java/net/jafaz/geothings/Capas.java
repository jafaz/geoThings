/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.unam.unibio.geocosas.mapa;

import java.util.ArrayList;

import java.awt.Color;
import org.geotools.map.MapContext;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import org.geotools.data.FeatureSource;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.renderer.GTRenderer;
import org.geotools.renderer.lite.StreamingRenderer;
import org.geotools.styling.Style;


/**
 *
 * @author jazmin
 */
public class Capas {

    private String id;
    private int tipoGeometria;
    private ArrayList<Filtro>  filtros;
    private Sld estilo;
    private MapContext map;
    private FeatureSource shape;

    
    public static final int PUNTOS = 1;
    public static final int LINEAS = 2;
    public static final int POLIGONOS = 3;


    public Capas(String id, int tipoGeometria)throws CapaException{
        this.id = id;
        if ( esGeometriaValida(tipoGeometria) ){
            this.tipoGeometria = tipoGeometria;
        }else{
            throw new CapaException("Numero de geometria invalido");
        }
    }

    public void setFiltros(ArrayList<Filtro>  filtros)throws CapaException{
        
        if ( filtros != null && filtros.size() > 0 ){
            this.filtros = filtros;
        }else{
            throw new CapaException("Se requiere un atributo y un arreglo de rasgos");
        }
    }

    public void setEstilo(Sld estilo){
        this.estilo = estilo;
    }

    public Style daSLD(){
        return estilo.daStyle();
    }

    private boolean esGeometriaValida(int geometria){
        if ( geometria == PUNTOS || geometria == LINEAS ||
             geometria == POLIGONOS ){
            return true;
        }else{
            return false;
        }

    }


    

}
