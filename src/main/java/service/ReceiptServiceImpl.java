package service;

import model.Product;
import model.Receipt;

public class ReceiptServiceImpl implements ReceiptService {
    private Receipt receipt;

    @Override
    public void createNewReceiptIfNotExist() {
        if (receipt == null)
            receipt = new Receipt();
    }

    @Override
    public void deleteReceipt() {
        receipt = null;
    }

    @Override
    public boolean isReceiptAlreadyExist() {
        return receipt != null;
    }

    @Override
    public void addProductToReceipt(Product product) {
        receipt.addProductAndUpdateTotalPrice(product);
    }

    @Override
    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }

    @Override
    public Receipt getReceipt() {
        return receipt;
    }
}
