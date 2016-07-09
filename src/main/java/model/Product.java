package model;

import java.math.BigDecimal;

public class Product {
    private String barcode;
    private String name;
    private BigDecimal price;

    public Product() {
    }

    public Product(String barcode, String name, BigDecimal price) {
        this.barcode = barcode;
        this.name = name;
        this.price = price;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
