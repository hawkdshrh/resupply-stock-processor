package org.acme;


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.beans.Product;
import org.acme.services.StockSupplyService;

@Path("/stock-update")
public class StockSupplyResource {
    
    private static final Logger LOGGER = Logger.getLogger("StockSupplyResource");

    @Inject StockSupplyService stockSupplyService;

    @GET
    @Path("products/{sku}/{amount}")
    public Boolean updateQuantity(@PathParam("sku") String sku, @PathParam("amount") Integer amount) {
        LOGGER.log(Level.INFO, "Updating sku:{0} for {1} items.", new Object[]{sku, amount});
        try {
            stockSupplyService.updateAvailableStock(new Product(sku), amount);
        } catch (Throwable t) {
            System.err.println(t.getLocalizedMessage());
            return false;
        }
        return true;
    }
    
}