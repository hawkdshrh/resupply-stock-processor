package org.acme.beans;

import java.util.Arrays;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class SupplyUpdate {
    private String supplyCode;
    private SupplyUpdateEntry[] supplyUpdateEntries;

    public SupplyUpdate() {

    }

    public SupplyUpdate(String supplyCode, SupplyUpdateEntry[] supplyUpdateEntries) {
        this.supplyCode = supplyCode;
        this.supplyUpdateEntries = supplyUpdateEntries;
    }

    public String getSupplyCode() {
        return supplyCode;
    }
    public void setSupplyCode(String supplyCode) {
        this.supplyCode = supplyCode;
    }
    public SupplyUpdateEntry[] getSupplyUpdateEntries() {
        return supplyUpdateEntries;
    }
    public void setSupplyUpdateEntries(SupplyUpdateEntry[] supplyUpdateEntries) {
        this.supplyUpdateEntries = supplyUpdateEntries;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((supplyCode == null) ? 0 : supplyCode.hashCode());
        result = prime * result + Arrays.hashCode(supplyUpdateEntries);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SupplyUpdate other = (SupplyUpdate) obj;
        if (supplyCode == null) {
            if (other.supplyCode != null)
                return false;
        } else if (!supplyCode.equals(other.supplyCode))
            return false;
        if (!Arrays.equals(supplyUpdateEntries, other.supplyUpdateEntries))
            return false;
        return true;
    }

    
}
