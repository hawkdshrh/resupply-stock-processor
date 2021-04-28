package org.acme;

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
    @Path("products/{sku}/{name}/{amount}/{id}/{code}")
    public Boolean updateQuantity(@PathParam("sku") String sku, @PathParam("name") String name, @PathParam("amount") Integer amount, @PathParam("id") String updateId, @PathParam("code") String supplyCode) {
        LOGGER.log(Level.INFO, "Updating sku:{0} for {1} items.", new Object[]{sku, amount});
        try {
            stockSupplyService.updateAvailableStock(updateId, new Product(sku, name), amount, supplyCode);
        } catch (Throwable t) {
            System.err.println(t.getLocalizedMessage());
            return false;
        }
        return true;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("products/{sku}/{supplyCode}/{updateId}")
    public Boolean updateQuantity(@PathParam("sku") String sku, @PathParam("supplyCode") String supplyCode, @PathParam("updateId") String updateId, SupplyUpdateEntry entry) {

        /**
         * Example Payload:
         * {"product":{"productName":"bananas","productSku":"1111-2222-3333-1115"},"quantity":254}
         */
        LOGGER.log(Level.INFO, "Updating sku:{0} for {1} items.", new Object[]{entry.getProduct().getProductSku(), entry.getQuantity()});
        try {
            stockSupplyService.updateAvailableStock(updateId, entry.getProduct(), entry.getQuantity(), supplyCode);
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

        /**
         * Example Payload:
         * {"supplyCode":"restock","supplyUpdateEntries":[{"product":{"productName":"peaches","productSku":"1111-2222-3333-1226"},"quantity":312},{"product":{"productName":"cherries","productSku":"1111-2222-3333-1122"},"quantity":312}],"updateId":"26524","supplyCode":"SHRINKAGE"}
         */
        if (update == null || update.getSupplyUpdateEntries() == null || update.getSupplyUpdateEntries().length <= 0) {
            LOGGER.log(Level.INFO, "Invalid update request.");
            return false;
        }

        LOGGER.log(Level.INFO, "Updating {0} items for reason: {1}.", new Object[]{update.getSupplyUpdateEntries().length, update.getSupplyCode()});

        for (SupplyUpdateEntry entry : update.getSupplyUpdateEntries()) {
            LOGGER.log(Level.INFO, "Updating sku:{0} for {1} items.", new Object[]{entry.getProduct().getProductSku(), entry.getQuantity()});
            try {
                stockSupplyService.updateAvailableStock(update.getUpdateId(), entry.getProduct(), entry.getQuantity(), update.getSupplyCode());
            } catch (Throwable t) {
                System.err.println(t.getLocalizedMessage());
                return false;
            }
        }

        return true;
    }

}
