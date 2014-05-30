/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.unam.unibio.geocosas.mapa;



import org.opengis.filter.FilterFactory;
import org.geotools.styling.StyleFactory;
import org.geotools.factory.CommonFactoryFinder;
import org.opengis.filter.Filter;
import org.opengis.filter.expression.Expression;
import org.geotools.styling.Rule;
import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Style;

import org.geotools.styling.NamedLayer;

import org.geotools.styling.SLDTransformer;
import org.geotools.styling.StyledLayerDescriptor;

import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.PolygonSymbolizer;
import org.geotools.styling.LineSymbolizer;
import org.geotools.styling.Stroke;
import org.geotools.styling.Fill;
import org.geotools.styling.Graphic;
import org.geotools.styling.Mark;
import org.geotools.styling.SLD;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.TransformerException;
import java.awt.Color;




/**
 *
 * @author jazmin
 */
public class Sld {
    public static final int PUNTOS = 1;
    public static final int LINEAS = 2;
    public static final int POLIGONOS = 3;



    private ArrayList<Filtro> filtros;
    private String nombreCapa;


    FeatureTypeStyle fts;
    Style style;
    StyledLayerDescriptor sld;

    ArrayList<Rule> reglitas;

    private StyleFactory fabricaEstilos;
    private FilterFactory fabricaFiltros;

    private String name;
    private int tipoGeometria;


    public Sld(ArrayList<Filtro> filtros, String name, int tipoGeometria){

        this.name = name;
        this.tipoGeometria = tipoGeometria;
        fabricaEstilos = CommonFactoryFinder.getStyleFactory(null);
        fabricaFiltros = CommonFactoryFinder.getFilterFactory(null);

        if ( filtros == null || filtros.size() < 1){
            
            creaEstiloSimple();

        }else{
            this.filtros = filtros;

            sld = fabricaEstilos.createStyledLayerDescriptor();
            NamedLayer layer = fabricaEstilos.createNamedLayer();
            layer.setName(name);

            reglitas = new ArrayList<Rule>();
            creaReglas();
            fts = fabricaEstilos.createFeatureTypeStyle(arrayListToArray());

            style = fabricaEstilos.createStyle();
            style.featureTypeStyles().add(fts);
            layer.addStyle(style);
            sld.addStyledLayer(layer);
            
        }
    }




    public void setName(String name){
        style.setName(name);
    }

    private Rule[] arrayListToArray(){
        Rule[] reglas = new Rule[reglitas.size()];
        for ( int i = 0; i < reglitas.size(); i++ ){
            reglas[i] = reglitas.get(i);
        }
        return reglas;
    }


    private void creaReglas() {
        switch(tipoGeometria){
            case PUNTOS:
                creaReglaPunto();
                break;
            case LINEAS:
                creaReglaLinea();
                 break;
            case POLIGONOS:
                creaReglaPoligono();
                 break;
            default:
                break;
        }
        
   
    }


    private void creaEstiloSimple() {

        switch(tipoGeometria){
            case PUNTOS:
                style = SLD.createPointStyle("Circle", Color.RED, Color.yellow, 0, 5, null, null);
                break;
            case LINEAS:
                style = SLD.createLineStyle(Color.BLACK, 0.5f);
                 break;
            case POLIGONOS:
                style = SLD.createPolygonStyle(Color.BLACK, Color.GRAY, 0.5f);
                 break;
            default:
                break;
        }


    }




       public void creaReglaPoligono(){

        for ( Filtro filtro : filtros ){

                Rule rule = fabricaEstilos.createRule();

                Stroke stroke = fabricaEstilos.createStroke(
                    fabricaFiltros.literal(filtro.daColorBorde()),
                    fabricaFiltros.literal(filtro.daAnchoBorde()));

                Fill fill = fabricaEstilos.createFill(
                        fabricaFiltros.literal(filtro.daColorRelleno()),
                        fabricaFiltros.literal(filtro.daOpacidadRelleno()));

                PolygonSymbolizer sym = fabricaEstilos.createPolygonSymbolizer(stroke, fill, null);
                rule.symbolizers().add(sym);
                Filter filtroSLD = fabricaFiltros.or(crearFiltroSLDd(filtro));
                rule.setFilter(filtroSLD);
                reglitas.add(rule);

        }
    }



   public void creaReglaPunto(){

        for ( Filtro filtro : filtros ){

            Rule rule = fabricaEstilos.createRule();

            Graphic gr = fabricaEstilos.createDefaultGraphic();
            Mark mark = daFigura(filtro);

            mark.setStroke(fabricaEstilos.createStroke(
                                fabricaFiltros.literal(filtro.daColorBorde()),
                                fabricaFiltros.literal(filtro.daAnchoBorde())

                                ));

            mark.setFill(fabricaEstilos.createFill(
                                fabricaFiltros.literal(filtro.daColorRelleno()),
                                fabricaFiltros.literal(filtro.daOpacidadRelleno())));

            gr.graphicalSymbols().clear();
            gr.graphicalSymbols().add(mark);
            gr.setSize(fabricaFiltros.literal(filtro.daDiametroFigura()));

            PointSymbolizer sym = fabricaEstilos.createPointSymbolizer(gr, null);


            rule.symbolizers().add(sym);
            Filter filtroSLD = fabricaFiltros.or(crearFiltroSLDd(filtro));
            rule.setFilter(filtroSLD);
            reglitas.add(rule);

        }
    }


       public void creaReglaLinea(){
        ArrayList<Color> degradadoBorde;
        for ( Filtro filtro : filtros ){

                Rule rule = fabricaEstilos.createRule();
                Stroke stroke = fabricaEstilos.createStroke(
                    fabricaFiltros.literal(filtro.daColorBorde()),
                    fabricaFiltros.literal(filtro.daAnchoBorde()));

                LineSymbolizer sym = fabricaEstilos.createLineSymbolizer(stroke, null);
                rule.symbolizers().add(sym);
                Filter filtroSLD = fabricaFiltros.or(crearFiltroSLDd(filtro));
                rule.setFilter(filtroSLD);
                reglitas.add(rule);

        }
    }


    private ArrayList<Filter>  crearFiltroSLDd(Filtro filtro ){
        String atributo = filtro.daAtributo();
        ArrayList<Filter> filters = new ArrayList<Filter>();
        ArrayList<String> rasgos = filtro.daRasgos();

        for ( int i = 0; i <  rasgos.size(); i++ ){

                Expression expresion1 = fabricaFiltros.property(atributo);
                Expression expresion2 = fabricaFiltros.literal(rasgos.get(i));
                Filter f = fabricaFiltros.equals(expresion1, expresion2);
                filters.add(f);
           
        }
        return filters;
     }




    public Mark daFigura(Filtro filtro){

        String figura = filtro.daFigura();
        Mark marcador;

        if ( figura.equals(Filtro.FIGURA_CIRCULO) )
            marcador = fabricaEstilos.getCircleMark();
        else if ( figura.equals(Filtro.FIGURA_CRUZ) )
            marcador = fabricaEstilos.getCrossMark();
        else if ( figura.equals(Filtro.FIGURA_CUADRO) )
            marcador = fabricaEstilos.getSquareMark();
        else if ( figura.equals(Filtro.FIGURA_ESTRELLA) )
            marcador = fabricaEstilos.getStarMark();
        else if ( figura.equals(Filtro.FIGURA_TRIANGULO) )
            marcador = fabricaEstilos.getTriangleMark();
        else if ( figura.equals(Filtro.FIGURA_X) )
            marcador = fabricaEstilos.getXMark();
        else
            marcador = fabricaEstilos.getCircleMark();

        return marcador;
    }

    public Style daStyle(){
        return this.style;
    }

      public String toSTring(){
        String estilo = "";
        if ( filtros != null ){
        SLDTransformer aTransformer = new SLDTransformer();
            try {
                estilo = aTransformer.transform(sld);
            } catch (TransformerException ex) {
                Logger.getLogger(Sld.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            estilo = "Problema al elaborar los filtros";
        }
        return estilo;
    }


}
