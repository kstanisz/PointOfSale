package service;

import device.BarcodeScanner;
import device.Display;
import device.Printer;
import model.Product;
import model.Receipt;
import org.junit.Assert;
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
    private ReceiptService receiptService;
    private PointOfSaleService pointOfSaleService;

    private Product[] productsInDatabase = {
            new Product("C001", "Computer", new BigDecimal("1199.99")),
            new Product("T002", "Tablet", new BigDecimal("799.99")),
            new Product("P003", "Phone", new BigDecimal("599.99")),
    };

    @Before
    public void setUpPointOfSaleService() {
        this.pointOfSaleService = new PointOfSaleServiceImpl(barcodeScanner, display, printer, productRepository, receiptService);
    }

    @Before
    public void setUpReceiptService() {
        this.receiptService = new ReceiptServiceImpl();
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
                String barcode = (String) invocationOnMock.getArguments()[0];
                for (Product product : productsInDatabase) {
                    if (product.getBarcode().equals(barcode))
                        return product;
                }
                return null;
            }
        });
    }

    @Test
    public void testScanProductWhenProductExist() {
        Mockito.when(barcodeScanner.scanProductAndGetBarcode()).thenReturn(productsInDatabase[1].getBarcode());
        Receipt expectedReceipt = new Receipt();
        expectedReceipt.addProductAndUpdateTotalPrice(productsInDatabase[0]);
        receiptService.setReceipt(expectedReceipt);
        expectedReceipt.addProductAndUpdateTotalPrice(productsInDatabase[1]);
        pointOfSaleService.scanProduct();

        Mockito.verify(display).printProductNameAndPrice(productsInDatabase[1]);
        Assert.assertEquals(expectedReceipt, receiptService.getReceipt());
    }

    @Test
    public void testScanProductWhenProductNotFound() {
        Mockito.when(barcodeScanner.scanProductAndGetBarcode()).thenReturn("T001");
        Receipt expectedReceipt = new Receipt();
        expectedReceipt.addProductAndUpdateTotalPrice(productsInDatabase[0]);
        receiptService.setReceipt(expectedReceipt);
        pointOfSaleService.scanProduct();

        Mockito.verify(display).printMessage(PointOfSaleServiceImpl.PRODUCT_NOT_FOUND);
        Assert.assertEquals(expectedReceipt, receiptService.getReceipt());
    }

    @Test
    public void testScanProductWhenEmptyBarcode() {
        Mockito.when(barcodeScanner.scanProductAndGetBarcode()).thenReturn("");
        Receipt expectedReceipt = new Receipt();
        expectedReceipt.addProductAndUpdateTotalPrice(productsInDatabase[0]);
        receiptService.setReceipt(expectedReceipt);
        pointOfSaleService.scanProduct();

        Mockito.verify(display).printMessage(PointOfSaleServiceImpl.INVALID_BARCODE);
        Assert.assertEquals(expectedReceipt, receiptService.getReceipt());
    }

    @Test
    public void testReadInputMessageWhenExitAndNotNullReceipt() {
        Receipt expectedReceipt = new Receipt();
        for (Product product : productsInDatabase) {
            expectedReceipt.addProductAndUpdateTotalPrice(product);
        }

        receiptService.setReceipt(expectedReceipt);
        pointOfSaleService.readInputMessage(PointOfSaleServiceImpl.EXIT_MESSAGE);

        Mockito.verify(printer).printReceipt(expectedReceipt);
        Mockito.verify(display).printTotalPrice(expectedReceipt.getTotalPrice());
        Assert.assertNull(receiptService.getReceipt());
    }

    @Test
    public void testReadInputMessageWhenExitAndNullReceipt() {
        pointOfSaleService.readInputMessage(PointOfSaleServiceImpl.EXIT_MESSAGE);
        Mockito.verify(printer, Mockito.never()).printReceipt(Matchers.any(Receipt.class));
        Mockito.verify(display, Mockito.never()).printTotalPrice(Matchers.any(BigDecimal.class));
    }

    @Test
    public void testReadInputMessageWhenNotExit() {
        pointOfSaleService.readInputMessage("not_exit");
        Mockito.verify(printer, Mockito.never()).printReceipt(Matchers.any(Receipt.class));
        Mockito.verify(display, Mockito.never()).printTotalPrice(Matchers.any(BigDecimal.class));
    }
}
