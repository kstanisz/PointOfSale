package device;

import model.Product;

import java.math.BigDecimal;

public interface Display {
    void printMessage(String message);
    void printProductNameAndPrice(Product product);
    void printTotalPrice(BigDecimal totalPrice);
}
