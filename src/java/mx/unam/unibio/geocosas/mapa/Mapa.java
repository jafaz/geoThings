/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.unam.unibio.geocosas.mapa;

import java.util.ArrayList;
import org.geotools.geometry.jts.ReferencedEnvelope;


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
import org.geotools.data.FeatureSource;
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
public class Mapa {

    private ArrayList<Capas> capas;
    private int ancho;
    private int alto;
    private ReferencedEnvelope bbox;
    private MapContext map;
    private FeatureSource shape;

    public Mapa(){
        capas = new ArrayList<Capas>();
        ancho = 800;
        alto = 448;
        bbox = auxCrateBBOX(-112.83886940944929,-106.30427853930739,29.065796487405848,32.72516737468531);
        map = new DefaultMapContext();
    }


    public void setBBOX(double minx, double maxx, double miny, double maxy){
        bbox = auxCrateBBOX(minx, maxx, miny, maxy);
        if ( bbox == null ){
            auxCrateBBOX(-112.83886940944929,-106.30427853930739,29.065796487405848,32.72516737468531);
        }
    }

    public void setAnchoAlto(int ancho, int alto){
        this.ancho = ancho;
        this.alto = alto;
    }

    public void setCapas(ArrayList<Capas> capas){
        this.capas = capas;
    }

    public void agregaCapa(Capas capa){
        capas.add(capa);
    }

    public static ReferencedEnvelope auxCrateBBOX(double minx, double maxx, double miny, double maxy){
        ReferencedEnvelope bbox = null;
        try {
            bbox = new ReferencedEnvelope(minx, maxx, miny, maxy, CRS.decode("EPSG:4326"));
        } catch (NoSuchAuthorityCodeException ex) {
            Logger.getLogger(Mapa.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FactoryException ex) {
            Logger.getLogger(Mapa.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bbox;
    }



    public BufferedImage toJPG(){

        BufferedImage bimage = new BufferedImage(ancho,alto, BufferedImage.TYPE_INT_RGB);
        AffineTransform transform = new AffineTransform(0.0, -1.0, -1.0, 0.0, ancho, alto);

        for ( Capas capa : capas ){

            map.addLayer(capas, capa.daSLD());
            GTRenderer draw = (GTRenderer)new StreamingRenderer();
            draw.setContext(map);
            Rectangle paintArea = new Rectangle(alto, ancho);
            Graphics2D gr = bimage.createGraphics();
            gr.transform(transform);
            gr.setPaint(Color.WHITE);
            gr.fill(paintArea);
            draw.paint(gr, paintArea, bbox );
        }

        return bimage;
    }



}
