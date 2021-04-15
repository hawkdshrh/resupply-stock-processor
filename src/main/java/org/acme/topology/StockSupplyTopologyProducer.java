package org.acme.topology;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import io.quarkus.kafka.client.serialization.JsonbSerde;
import java.util.stream.Stream;
import org.acme.beans.Product;
import org.acme.beans.SupplyOrder;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Produced;

@ApplicationScoped
public class StockSupplyTopologyProducer {

    public static final String STOCK_LEVELS_TOPIC = "stock-levels";
    public static final String UPDATED_STOCK_TOPIC = "updated-stock";

    private final JsonbSerde<SupplyOrder> supplyOrderSerde = new JsonbSerde<>(SupplyOrder.class);
    private final JsonbSerde<Product> productSerde = new JsonbSerde<>(Product.class);

    @Produces
    public Topology buildTopology() {
        final StreamsBuilder builder = new StreamsBuilder();

        final KStream<String, SupplyOrder> supplyOrders = builder.stream(
                UPDATED_STOCK_TOPIC,
                Consumed.with(Serdes.String(), supplyOrderSerde));
        
        final KeyValueMapper<String,SupplyOrder,Iterable<KeyValue<Product,Integer>>> orderToProductQuantitiesMapping =
            (supplyOrderId, supplyOrder) -> 
                () -> Stream.of(supplyOrder.getSupplyOrderEntries())
                    .map(e -> new KeyValue<Product,Integer>(e.getProduct(), e.getQuantity()))
                    .iterator();

        final KStream<Product,Integer> stockOrdered = supplyOrders.flatMap(orderToProductQuantitiesMapping);
        
        stockOrdered.to(STOCK_LEVELS_TOPIC, Produced.with(productSerde, Serdes.Integer()));

        return builder.build();
    }

}
