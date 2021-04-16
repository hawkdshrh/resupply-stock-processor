package org.acme.serde;

import io.quarkus.kafka.client.serialization.JsonbDeserializer;
import io.quarkus.runtime.annotations.RegisterForReflection;
import org.acme.beans.SupplyUpdateEntry;

@RegisterForReflection
public class SupplyUpdateEntryDeserializer extends JsonbDeserializer<SupplyUpdateEntry> {
    public SupplyUpdateEntryDeserializer() {
        super(SupplyUpdateEntry.class);
    }
}
