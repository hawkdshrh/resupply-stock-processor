package org.acme.topology;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import io.quarkus.kafka.client.serialization.JsonbSerde;
import java.util.stream.Stream;
import org.acme.beans.Product;
import org.acme.beans.SupplyUpdate;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Produced;

@ApplicationScoped
public class StockSupplyTopologyProducer {

    public static final String STOCK_LEVELS_TOPIC = "stock-levels";
    public static final String UPDATED_STOCK_TOPIC = "updated-stock";
    
    private final JsonbSerde<SupplyUpdate> supplyUpdateSerde = new JsonbSerde<>(SupplyUpdate.class);
    private final JsonbSerde<Product> productSerde = new JsonbSerde<>(Product.class);

    @Produces
    public Topology buildTopology() {
        final StreamsBuilder builder = new StreamsBuilder();
        
        final KStream<String, SupplyUpdate> supplyUpdates = builder.stream(
                UPDATED_STOCK_TOPIC,
                Consumed.with(Serdes.String(), supplyUpdateSerde));

        final KeyValueMapper<String,SupplyUpdate,Iterable<KeyValue<Product,Integer>>> supplyUpdateToProductQuantitiesMapping =
            (supplyUpdateId, supplyUpdate) -> 
                () -> Stream.of(supplyUpdate.getSupplyUpdateEntries())
                    .map(e -> new KeyValue<Product,Integer>(e.getProduct(), e.getQuantity()))
                        .iterator();

        final KStream<Product,Integer> stockUpdated = supplyUpdates.flatMap(supplyUpdateToProductQuantitiesMapping);
        
        stockUpdated.to(STOCK_LEVELS_TOPIC, Produced.with(productSerde, Serdes.Integer()));

        return builder.build();
    }

}
