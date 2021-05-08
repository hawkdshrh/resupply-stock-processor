package org.acme.services;

import io.smallrye.reactive.messaging.kafka.KafkaRecord;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.acme.beans.Product;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class StockSupplyService {

    private static final Logger LOGGER = Logger.getLogger("StockSupplyService");
    
    @Inject
    @Channel("stock-levels-out")
    Emitter<Integer> emitter;

    public void updateAvailableStock(String updateId, Product product, Integer amount, String supplyCode) {

        LOGGER.log(Level.INFO, "Updating sku:{0} for {1} items.", new Object[]{product.getProductSku(), amount});
        KafkaRecord<Product, Integer> msg = KafkaRecord.of(product, amount);
        emitter.send(msg);
    }
}
