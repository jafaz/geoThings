/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.unam.unibio.geocosas.mapa;

import java.awt.Color;
import org.geotools.data.FeatureSource;
import org.geotools.map.DefaultMapContext;
import org.geotools.map.MapContext;
import java.awt.geom.AffineTransform;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.renderer.GTRenderer;
import org.geotools.renderer.lite.StreamingRenderer;

/**
 *
 * @author jazmin
 */
public class Capa {

    private String id;
    private FeatureSource shape;
    private Sld sld;
    private MapContext map;

    public Capa(String id, FeatureSource shape, Sld sld){
        this.id = id;
        this.shape = shape;
        this.sld = sld;
        map = new DefaultMapContext();
    }

    public BufferedImage toJPG(ReferencedEnvelope bbox, int ancho, int alto){
        map.addLayer(shape, sld.daStyle());

        AffineTransform transform = new AffineTransform(0.0, -1.0, -1.0, 0.0, ancho, alto);
        GTRenderer draw = (GTRenderer)new StreamingRenderer();
        draw.setContext(map);
        Rectangle paintArea = new Rectangle(alto, ancho);
        BufferedImage bimage = new BufferedImage(ancho,alto, BufferedImage.TYPE_INT_RGB) ;

        Graphics2D gr = bimage.createGraphics();
        gr.transform(transform);

        gr.setPaint(Color.WHITE);
        gr.fill(paintArea);

        draw.paint(gr, paintArea, bbox );

        return bimage;
    }


    public static BufferedImage toJPG(int ancho, int alto, String mensaje){
        BufferedImage bimage = new BufferedImage(ancho,alto, BufferedImage.TYPE_INT_RGB) ;
        Graphics2D gr = bimage.createGraphics();
        Rectangle paintArea = new Rectangle(ancho, alto);
        gr.setBackground(Color.yellow);
        gr.setPaint(Color.BLACK);
        gr.fill(paintArea);
        gr.setPaint(Color.RED);
        gr.drawString("UNIBIO 2010: "+mensaje, 10, 50);
        return bimage;
    }


    public static BufferedImage toJPG(String mensaje){
        return toJPG(800, 600, mensaje);
    }

}
