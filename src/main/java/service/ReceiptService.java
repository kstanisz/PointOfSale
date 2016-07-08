package service;

import model.Product;
import model.Receipt;

public class ReceiptService {
    private Receipt receipt;

    public void createNewReceiptIfNotExist(){
        receipt=new Receipt();
    }

    public void deleteReceipt(){
        receipt=null;
    }

    public Receipt getReceipt(){
        return receipt;
    }

    public boolean isReceiptAlreadyExist(){
        return receipt!=null;
    }

    public void addProductToReceipt(Product product){
        receipt.addProduct(product);
    }
}
