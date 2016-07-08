package service;

import device.BarcodeScanner;
import device.Display;
import device.Printer;
import model.Product;
import model.Receipt;
import repository.ProductRepository;

public class PointOfSale {
    private BarcodeScanner barcodeScanner;
    private Display display;
    private Printer printer;
    private ProductRepository productRepository;

    private Receipt receipt;

    private final String EXIT_MESSAGE = "exit";
    private final String INVALID_BARCODE = "Invalid bar-code";
    private final String PRODUCT_NOT_FOUNT = "Product not found";

    PointOfSale(BarcodeScanner barcodeScanner, Display display, Printer printer, ProductRepository productRepository) {
        this.barcodeScanner = barcodeScanner;
        this.display = display;
        this.printer = printer;
        this.productRepository = productRepository;
    }

    public void scanProduct() {
        if (receipt == null)
            receipt = new Receipt();

        String barcode = barcodeScanner.scanProductAndGetBarcode();
        if (barcode == null || barcode.isEmpty())
            display.printMessage(INVALID_BARCODE);
        Product product = productRepository.findProductByBarcode(barcode);
        if (product != null) {
            receipt.addProduct(product);
            display.printMessage(product.toString());
        } else
            display.printMessage(PRODUCT_NOT_FOUNT);
    }

    public void readInputMessage(String inputMessage) {
        if (inputMessage.equals(EXIT_MESSAGE))
            printReceipt();
    }

    public void printReceipt() {
        printer.printReceipt(receipt);
    }
}
