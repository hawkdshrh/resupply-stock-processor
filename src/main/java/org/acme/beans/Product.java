package org.acme.beans;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class Product {
    private String productSku;
    private String productName;


    public Product() {

    }
    
    public Product(String productSku) {
        this.productSku = productSku;
    }

    public Product(String productSku, String productName) {
        this.productSku = productSku;
        this.productName = productName;
    }

    public String getProductSku() {
        return productSku;
    }

    public void setProductSku(String productSku) {
        this.productSku = productSku;
    }
    
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((productSku == null) ? 0 : productSku.hashCode());
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
        Product other = (Product) obj;
        if (productSku == null) {
            if (other.productSku != null)
                return false;
        } else if (!productSku.equals(other.productSku))
            return false;
        return true;
    }

    
}
