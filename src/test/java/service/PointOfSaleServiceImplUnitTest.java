package service;

import device.BarcodeScanner;
import device.Display;
import device.Printer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import repository.ProductRepository;

@RunWith(MockitoJUnitRunner.class)
public class PointOfSaleServiceImplUnitTest {
    @Mock
    private BarcodeScanner barcodeScanner;
    @Mock
    private Display display;
    @Mock
    private Printer printer;
    @Mock
    private ProductRepository productRepository;
    private ReceiptService receiptService = new ReceiptServiceImpl();
    private PointOfSaleService pointOfSaleService;

    @Before
    public void setUpPointOfSaleService() {
        pointOfSaleService = new PointOfSaleServiceImpl(barcodeScanner, display, printer, productRepository, receiptService);
    }

    @Test
    public void testScanProduct(){
    }
}
