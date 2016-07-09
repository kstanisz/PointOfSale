package service;

import device.BarcodeScanner;
import device.Display;
import device.Printer;
import model.Product;
import model.Receipt;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import repository.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;

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

    private ArrayList<Product> productsInDatabase;

    @Before
    public void setUpPointOfSaleService() {
        pointOfSaleService = new PointOfSaleServiceImpl(barcodeScanner, display, printer, productRepository, receiptService);
    }

    @Before
    public void setUpBarcodeScannerMockConfiguration() {
        Mockito.when(barcodeScanner.scanProductAndGetBarcode()).thenAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                return null;
            }
        });
    }

    @Before
    public void setUpDisplayMockConfiguration() {
        Mockito.doNothing().when(display).printMessage(Matchers.anyString());
        Mockito.doNothing().when(display).printProductNameAndPrice(Matchers.any(Product.class));
        Mockito.doNothing().when(display).printTotalPrice(Matchers.any(BigDecimal.class));
    }

    @Before
    public void setUpPrinterMockConfiguration() {
        Mockito.doNothing().when(printer).printReceipt(Matchers.any(Receipt.class));
    }

    @Before
    public void setUpProductRepositoryMockConfiguration() {
        Mockito.when(productRepository.findProductByBarcode(Matchers.anyString())).thenAnswer(new Answer<Product>() {
            @Override
            public Product answer(InvocationOnMock invocationOnMock) throws Throwable {
                return null;
            }
        });
    }

    @Test
    public void testScanProduct() {
        pointOfSaleService.scanProduct();
        Mockito.verify(barcodeScanner).scanProductAndGetBarcode();
    }

    @Test
    public void testReadInputMessage() {

    }
}
