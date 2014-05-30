/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ws;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;

import javax.ws.rs.QueryParam;

/**
 *
 * @author jazmin
 */
@Stateless
@Path("/sld") 
public class SLD {

    @EJB
    private Conector conector;
    /**
     * Retrieves representation of an instance of helloworld.HelloWorldResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("text/xml")
    public String getXml(
        @QueryParam("json") String jsonPeticion) {

        if ( jsonPeticion != null && jsonPeticion.length() > 2){
            return conector.getSLD(jsonPeticion);
        }
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<error>\nSe requiere una variable ?json="+
                "{'id':'ug1',\n"+
                "'alto':432,\n"+
                "'ancho':800,\n"+
                "'bbox':[-128.2,-74,8.2,37.5],\n"+
                "'filtros':[{'anchoBorde':2,\n"+
                "           'anchiFigura':15,\n"+
                "           'atributo':'tipo',\n"+
                "           'colorBorde':'330000',\n"+
                "           'colorRelleno':'0000ff',\n"+
                "           'condicion':'tipo='Internacional'',\n"+
                "           'opacidadBorde':0.5,\n"+
                "           'opacidadRelleno':0.5,\n"+
                "           'figura':'x'},\n"+
                "           \n"+
                "           {'anchoBorde':2,\n"+
                "           'anchiFigura':15,\n"+
                "           'atributo':'tipo',\n"+
                "           'colorBorde':'bbb000',\n"+
                "           'colorRelleno':'bb4433',\n"+
                "            'condicion':'tipo='Nacional'',\n"+
                "            'opacidadBorde':0.5,\n"+
                "            'opacidadRelleno':0.5,\n"+
                "            'figura':'cruz'}\n"+
                "            ]\n"+
                "}\n</error>";
    }
    /**
     * PUT method for updating an instance of HelloWorldResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes("text/x")
    public void putXml(String content) {
        conector.setName(content);
    }

}
