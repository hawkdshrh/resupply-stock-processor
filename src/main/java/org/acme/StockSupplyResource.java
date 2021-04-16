package org.acme;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.beans.Product;
import org.acme.beans.SupplyUpdate;
import org.acme.beans.SupplyUpdateEntry;
import org.acme.services.StockSupplyService;

@Path("/stock-update")
public class StockSupplyResource {

    private static final Logger LOGGER = Logger.getLogger("StockSupplyResource");

    @Inject
    StockSupplyService stockSupplyService;

    @GET
    @Path("products/{sku}/{name}/{amount}")
    public Boolean updateQuantity(@PathParam("sku") String sku, @PathParam("name") String name, @PathParam("amount") Integer amount) {
        LOGGER.log(Level.INFO, "Updating sku:{0} for {1} items.", new Object[]{sku, amount});
        try {
            stockSupplyService.updateAvailableStock(new Product(sku, name), amount);
        } catch (Throwable t) {
            System.err.println(t.getLocalizedMessage());
            return false;
        }
        return true;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("products/{sku}")
    public Boolean updateQuantity(@PathParam("sku") String sku, SupplyUpdateEntry entry) {

        LOGGER.log(Level.INFO, "Updating sku:{0} for {1} items.", new Object[]{entry.getProduct().getProductSku(), entry.getQuantity()});
        try {
            stockSupplyService.updateAvailableStock(entry.getProduct(), entry.getQuantity());
        } catch (Throwable t) {
            System.err.println(t.getLocalizedMessage());
            return false;
        }
        return true;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("products")
    public Boolean updateQuantities(SupplyUpdate update) {
        
        if (update == null || update.getSupplyUpdateEntries() == null) {
            LOGGER.log(Level.INFO, "Invalid update request.");
        }

        LOGGER.log(Level.INFO, "Updating {0} items for reason: {1}.", new Object[]{update.getSupplyUpdateEntries().length, update.getSupplyCode()});
        
        if (update != null && update.getSupplyUpdateEntries() != null && update.getSupplyUpdateEntries().length > 0) {
            for (SupplyUpdateEntry entry : update.getSupplyUpdateEntries()) {
                LOGGER.log(Level.INFO, "Updating sku:{0} for {1} items.", new Object[]{entry.getProduct().getProductSku(), entry.getQuantity()});
                try {
                    stockSupplyService.updateAvailableStock(entry.getProduct(), entry.getQuantity());
                } catch (Throwable t) {
                    System.err.println(t.getLocalizedMessage());
                    return false;
                }
            }
        }
        return true;
    }

}
