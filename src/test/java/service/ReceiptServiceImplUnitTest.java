package service;

import model.Product;
import model.Receipt;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class ReceiptServiceImplUnitTest {
    private ReceiptService receiptService;

    @Before
    public void setUpReceiptService() {
        this.receiptService = new ReceiptServiceImpl();
    }

    @Test
    public void testCreateNewReceiptIfNotExist() {
        receiptService.setReceipt(null);
        receiptService.createNewReceiptIfNotExist();
        Assert.assertNotNull(receiptService.getReceipt());

        Receipt expectedReceipt = new Receipt();
        receiptService.setReceipt(expectedReceipt);
        receiptService.createNewReceiptIfNotExist();
        Assert.assertEquals(expectedReceipt, receiptService.getReceipt());
    }

    @Test
    public void testDeleteReceipt() {
        receiptService.setReceipt(new Receipt());
        receiptService.deleteReceipt();
        Assert.assertNull(receiptService.getReceipt());
    }

    @Test
    public void testIsReceiptAlreadyExist() {
        receiptService.setReceipt(null);
        Assert.assertFalse(receiptService.isReceiptAlreadyExist());
        receiptService.setReceipt(new Receipt());
        Assert.assertTrue(receiptService.isReceiptAlreadyExist());
    }

    @Test
    public void testAddProductToReceipt() {
        Product[] products = {
                new Product("001", "First", new BigDecimal("120.00")),
                new Product("002", "Second", new BigDecimal("100.00")),
        };
        Receipt expectedReceipt = new Receipt();
        expectedReceipt.addProductAndUpdateTotalPrice(products[0]);
        receiptService.setReceipt(expectedReceipt);
        expectedReceipt.addProductAndUpdateTotalPrice(products[1]);
        receiptService.addProductToReceipt(products[1]);
        Assert.assertEquals(expectedReceipt, receiptService.getReceipt());
    }
}
