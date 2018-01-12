package com.marketbasketanalysis.pos;

import com.google.gson.Gson;
import com.marketbasketanalysis.dao.Product;
import com.marketbasketanalysis.dto.ProductDTO;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("pos")
public class PointOfSale {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PointOfSale
     */
    public PointOfSale() {
    }

    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllProducts() {
        ProductDTO proddto=new ProductDTO ();
        List<Product> productList=proddto.getAllProducts();
        Gson gson=new Gson();
        String productsInJson=gson.toJson(productList);
        return productsInJson;
    }
    
    @GET
    @Path("deleteproduct/{id}")
    public Response deleteProduct(@PathParam("id") int id){
    Response r=null;
        ProductDTO proddto=new ProductDTO ();
        proddto.deleteProductById(id);
    return r;
    }

    /**
     * PUT method for updating or creating an instance of PointOfSale
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putXml(String content) {
        
    }
}
