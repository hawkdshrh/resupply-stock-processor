package org.acme.services;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.acme.beans.Product;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.acme.beans.SupplyUpdate;
import org.acme.beans.SupplyUpdateEntry;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class StockSupplyService {

    private static final Logger LOGGER = Logger.getLogger("StockSupplyService");

    @Inject
    @Channel("updated-stock-out")
    Emitter<SupplyUpdate> emitter;

    public void updateAvailableStock(String updateId, Product product, Integer amount, String supplyCode) {

        if (updateId == null || updateId.isEmpty()) {
            updateId = UUID.randomUUID().toString();
        }
        LOGGER.log(Level.INFO, "Updating sku:{0} for {1} items.", new Object[]{product.getProductSku(), amount});
        SupplyUpdateEntry entry = new SupplyUpdateEntry(product, amount);
        SupplyUpdate supplyUpdate = new SupplyUpdate(updateId, supplyCode, new SupplyUpdateEntry[]{entry});
        emitter.send(supplyUpdate);
    }
}
