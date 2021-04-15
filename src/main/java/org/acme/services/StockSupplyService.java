package org.acme.services;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.acme.beans.Product;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.acme.beans.SupplyOrder;
import org.acme.beans.SupplyOrderEntry;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class StockSupplyService {
    
    private static final Logger LOGGER = Logger.getLogger("StockSupplyService");

    @Inject
    @Channel("updated-stock-out")
    Emitter<SupplyOrder> emitter;

    public void updateAvailableStock(Product product, Integer amount) {

        LOGGER.log(Level.INFO, "Updating sku:{0} for {1} items.", new Object[]{product.getProductSku(), amount});
            SupplyOrderEntry entry = new SupplyOrderEntry(product, amount);
            SupplyOrder supplyOrder = new SupplyOrder(UUID.randomUUID().toString(), new SupplyOrderEntry[]{entry});
            emitter.send(supplyOrder);
    }
}
