package service;

import model.Product;
import model.Receipt;

public interface ReceiptService {
    void createNewReceiptIfNotExist();

    void deleteReceipt();

    Receipt getReceipt();

    boolean isReceiptAlreadyExist();

    void addProductToReceipt(Product product);
}
