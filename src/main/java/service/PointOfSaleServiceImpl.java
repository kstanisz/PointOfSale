package service;

import device.BarcodeScanner;
import device.Display;
import device.Printer;
import model.Product;
import model.Receipt;
import repository.ProductRepository;

public class PointOfSaleServiceImpl implements PointOfSaleService {
    private BarcodeScanner barcodeScanner;
    private Display display;
    private Printer printer;
    private ProductRepository productRepository;
    private ReceiptService receiptService;

    public final static String EXIT_MESSAGE = "exit";
    public final static String INVALID_BARCODE = "Invalid bar-code";
    public final static String PRODUCT_NOT_FOUND = "Product not found";

    PointOfSaleServiceImpl(BarcodeScanner barcodeScanner, Display display, Printer printer, ProductRepository productRepository, ReceiptService receiptService) {
        this.barcodeScanner = barcodeScanner;
        this.display = display;
        this.printer = printer;
        this.productRepository = productRepository;
        this.receiptService = receiptService;
    }

    @Override
    public void scanProduct() {
        String barcode = barcodeScanner.scanProductAndGetBarcode();
        if (barcode == null || barcode.isEmpty())
            display.printMessage(INVALID_BARCODE);
        else
            findProductByBarcodeAndAddToReceipt(barcode);
    }

    private void findProductByBarcodeAndAddToReceipt(String barcode) {
        receiptService.createNewReceiptIfNotExist();
        Product product = productRepository.findProductByBarcode(barcode);
        if (product != null) {
            receiptService.addProductToReceipt(product);
            display.printProductNameAndPrice(product);
        } else
            display.printMessage(PRODUCT_NOT_FOUND);
    }

    @Override
    public void readInputMessage(String inputMessage) {
        if (inputMessage.equals(EXIT_MESSAGE))
            printReceiptAndDisplayTotalPrice();
    }

    private void printReceiptAndDisplayTotalPrice() {
        if (receiptService.isReceiptAlreadyExist()) {
            Receipt receipt = receiptService.getReceipt();
            printer.printReceipt(receipt);
            display.printTotalPrice(receipt.getTotalPrice());
            receiptService.deleteReceipt();
        }
    }
}
