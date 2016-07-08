package repository;

import model.Product;

public interface ProductRepository {
    Product findProductByBarcode(String barcode);
}
