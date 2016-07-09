package service;

import model.Product;
import model.Receipt;

public interface ReceiptService {
    void createNewReceiptIfNotExist();

    void deleteReceipt();

    boolean isReceiptAlreadyExist();

    void addProductToReceipt(Product product);

    void setReceipt(Receipt receipt);

    Receipt getReceipt();
}
