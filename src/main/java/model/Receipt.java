package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Receipt {
    private BigDecimal totalPrice = BigDecimal.ZERO;
    private List<Product> productList = new ArrayList<>();

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void addProductAndUpdateTotalPrice(Product product) {
        productList.add(product);
        totalPrice = totalPrice.add(product.getPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.totalPrice, this.productList);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (other == this)
            return true;
        if (!(other instanceof Receipt))
            return false;
        Receipt otherReceipt = (Receipt) other;
        return (this.totalPrice.equals(otherReceipt.getTotalPrice()) && this.productList.equals(otherReceipt.getProductList()));
    }
}