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
@Path("/metadatos")
public class Metadatos {

    @EJB
    private Conector conector;
    /**
     * Retrieves representation of an instance of helloworld.HelloWorldResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/json")
    public String getXml() {
      return conector.daMetadatos();
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
