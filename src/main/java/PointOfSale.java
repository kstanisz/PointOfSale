import device.BarcodeScanner;
import device.Display;
import device.Printer;
import repository.ProductRepository;

public class PointOfSale {
    private BarcodeScanner barcodeScanner;
    private Display display;
    private Printer printer;
    private ProductRepository productRepository;

    PointOfSale(BarcodeScanner barcodeScanner, Display display, Printer printer, ProductRepository productRepository) {
        this.barcodeScanner = barcodeScanner;
        this.display = display;
        this.printer = printer;
        this.productRepository = productRepository;
    }
}
