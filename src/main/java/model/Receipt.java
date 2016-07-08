package model;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Receipt {
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private ArrayList<Product> products = new ArrayList<>();

    public BigDecimal getTotalPrice(){
        return totalPrice;
    }

    public void addProduct(Product product) {
        totalPrice = totalPrice.add(product.getPrice());
        products.add(product);
    }
}
