package org.acme.beans;

import java.util.Arrays;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class SupplyOrder {
    private String supplyCode;
    private SupplyOrderEntry[] supplyOrderEntries;

    public SupplyOrder() {

    }

    public SupplyOrder(String supplyCode, SupplyOrderEntry[] supplyOrderEntries) {
        this.supplyCode = supplyCode;
        this.supplyOrderEntries = supplyOrderEntries;
    }

    public String getSupplyCode() {
        return supplyCode;
    }
    public void setOrderCode(String supplyCode) {
        this.supplyCode = supplyCode;
    }
    public SupplyOrderEntry[] getSupplyOrderEntries() {
        return supplyOrderEntries;
    }
    public void setSupplyOrderEntries(SupplyOrderEntry[] supplyOrderEntries) {
        this.supplyOrderEntries = supplyOrderEntries;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((supplyCode == null) ? 0 : supplyCode.hashCode());
        result = prime * result + Arrays.hashCode(supplyOrderEntries);
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
        SupplyOrder other = (SupplyOrder) obj;
        if (supplyCode == null) {
            if (other.supplyCode != null)
                return false;
        } else if (!supplyCode.equals(other.supplyCode))
            return false;
        if (!Arrays.equals(supplyOrderEntries, other.supplyOrderEntries))
            return false;
        return true;
    }

    
}
