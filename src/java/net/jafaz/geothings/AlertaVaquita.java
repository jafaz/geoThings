/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package alerta;

import java.io.File;

import org.geotools.data.DataUtilities;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureCollections;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geometry.jts.JTSFactoryFinder;
import org.opengis.feature.simple.SimpleFeature;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.map.DefaultMapContext;
import org.geotools.map.MapContext;
import org.geotools.referencing.CRS;
import org.geotools.renderer.GTRenderer;
import org.geotools.renderer.lite.StreamingRenderer;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.vividsolutions.jts.geom.Point;

import java.awt.Color;
import org.opengis.filter.FilterFactory;
import org.geotools.styling.StyleFactory;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.renderer.lite.RendererUtilities;
import org.geotools.styling.ExternalGraphic;
import org.geotools.styling.Rule;
import org.opengis.feature.simple.SimpleFeatureType;
import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Style;

import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.Graphic;
import org.geotools.styling.NamedLayer;
import org.geotools.styling.SLD;

import org.geotools.styling.StyledLayerDescriptor;

/**
 *
 * @author jazmin
 */

public class AlertaVaquita {

    private ReferencedEnvelope bbox;
    private int ancho;
    private int alto;
    private String urlImg = "base_correo.png";
    private String urlPNGBarco = "http://es.kgbpeople.com/img/icon_android.png";
    private String archivoSalida = "prueba.png";
    private Point punto;


    

   public AlertaVaquita(int ancho, int alto, double longitud, double latitud, ReferencedEnvelope bbox) throws SchemaException{
        init(ancho, alto, longitud, latitud, bbox);
   }


   public static ReferencedEnvelope auxCrateBBOX(double a, double b, double c, double d){
        ReferencedEnvelope bbox = null;
        try {
            bbox = new ReferencedEnvelope(a, b, c, d, CRS.decode("EPSG:4326"));
        } catch (NoSuchAuthorityCodeException ex) {
            //Logger.getLogger(Manejador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FactoryException ex) {
         //   Logger.getLogger(Manejador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bbox;
    }


   public void init(int ancho, int alto, double longitud, double latitud, ReferencedEnvelope bbox) throws SchemaException{
       this.ancho = ancho;
       this.alto = alto;
       GeometryFactory geometryFactory = JTSFactoryFinder.getGeometryFactory(null);
       punto = geometryFactory.createPoint(new Coordinate(longitud, latitud));
       
       // creamos un mapa con el punto
        final SimpleFeatureType TYPE = DataUtilities.createType(
            "Location",                   // <- the name for our feature type
            "location:Point:srid=4326," + // <- the geometry attribute: Point type
            "name:String"         // <- a String attribute
        );
        FeatureCollection collection = FeatureCollections.newCollection();
        SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(TYPE);
        featureBuilder.add(punto);
        //featureBuilder.add("Barco en zona prohibida");
        SimpleFeature feature = featureBuilder.buildFeature(null);
        collection.add(feature);
        MapContext map = new DefaultMapContext();
        map.setAreaOfInterest(bbox);
        //map.get

        // Creamos un estilo
        Style style = SLD.createPointStyle("Circle", Color.RED, Color.yellow, 0, 5, null, null);

        //agregamos el punto y el estilo al mapa
       // map.addLayer(collection, createSDL(collection));
        map.addLayer(collection, style);



        ///Pintamos el mapa
          AffineTransform transform = new AffineTransform(0.0, -1.0, -1.0, 0.0, ancho, alto);
        GTRenderer draw = (GTRenderer)new StreamingRenderer();
        draw.setContext(map);
        Rectangle paintArea = new Rectangle(alto, ancho);
        BufferedImage bimage = new BufferedImage(ancho,alto, BufferedImage.TYPE_INT_RGB) ;
        Graphics2D gr = bimage.createGraphics();
        gr.setBackground(new Color(0,0,0,0)); //nuevo
        gr.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, 0.0f));   //nuevo
        //gr.transform(transform);
        gr.setPaint(Color.WHITE);
        gr.fill(paintArea);
        ImageIcon imagen = new ImageIcon(this.urlImg);
        Graphics gra = bimage.getGraphics();
        gra.drawImage(imagen.getImage(), 0,0, null);
        gr.dispose();
        draw.paint((Graphics2D) gra,paintArea, bbox);
        draw.paint(gr, paintArea, bbox );


        try {
            ImageIO.write(bimage, "png", new File(this.archivoSalida));
        } catch (Exception e) {
            e.printStackTrace();
        }

   }


   public Style createSDL(FeatureCollection collection){
        SimpleFeatureType schema = (SimpleFeatureType)collection.getSchema();
        StyleFactory fabricaEstilos = CommonFactoryFinder.getStyleFactory(null);
        FilterFactory fabricaFiltros = CommonFactoryFinder.getFilterFactory(null);
        //Class geomType = schema.getGeometryDescriptor().getType().getBinding();
        StyledLayerDescriptor sld = fabricaEstilos.createStyledLayerDescriptor();//
        NamedLayer layer = fabricaEstilos.createNamedLayer();
        layer.setName("");
        //crearegla
        Rule[] reglas = new Rule[1];
        Rule rule = fabricaEstilos.createRule();
        Graphic graphic = fabricaEstilos.createDefaultGraphic();
        graphic.graphicalSymbols().clear();
        ExternalGraphic external = fabricaEstilos.createExternalGraphic( urlPNGBarco , "image/png");
        graphic.graphicalSymbols().add( external );
        PointSymbolizer sym = fabricaEstilos.createPointSymbolizer();
        sym.setGraphic(graphic);
        rule.symbolizers().add(sym);
        reglas[0] = rule;
        FeatureTypeStyle fts = fabricaEstilos.createFeatureTypeStyle(reglas);
        Style style = fabricaEstilos.createStyle();
        style.featureTypeStyles().add(fts);
        layer.addStyle(style);
        sld.addStyledLayer(layer);
        return style;
   }


   public static void main(String[] args){
        try {
            AlertaVaquita v = new AlertaVaquita(800, 448, -110.97,31.25,
                    AlertaVaquita.auxCrateBBOX(-112.83886940944929,-106.30427853930739,29.065796487405848,32.72516737468531));
           //-115.06149 a -114.08757; Y va de 30.64819 a 31.6221; e

        } catch (SchemaException ex) {
            Logger.getLogger(AlertaVaquita.class.getName()).log(Level.SEVERE, null, ex);
        }
    
   }

}
