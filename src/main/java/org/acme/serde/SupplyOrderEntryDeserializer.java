package org.acme.serde;

import io.quarkus.kafka.client.serialization.JsonbDeserializer;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.acme.beans.SupplyOrderEntry;

@RegisterForReflection
public class SupplyOrderEntryDeserializer extends JsonbDeserializer<SupplyOrderEntry> {
    public SupplyOrderEntryDeserializer() {
        super(SupplyOrderEntry.class);
    }
}
