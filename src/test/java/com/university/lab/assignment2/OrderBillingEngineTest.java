/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author natas
 */

package com.university.lab.assignment2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrderBillingEngineTest {

    @TempDir
    Path tempDir;

    // Creates a new order for testing
    private Map<String, Object> createOrder(
            String customerType,
            double price,
            int quantity,
            int numberOfItems) {

        Map<String, Object> order = new HashMap<>();

        order.put("customerEmail", "customer@example.com");
        order.put("customerType", customerType);
        order.put("country", "PK");
        order.put("paymentMethod", "CREDIT_CARD");
        order.put("cardNumber", "1234567812345678");

        List<Map<String, Object>> items = new ArrayList<>();

        for (int i = 0; i < numberOfItems; i++) {

            Map<String, Object> item = new HashMap<>();

            item.put("price", price);
            item.put("quantity", quantity);

            items.add(item);
        }

        order.put("items", items);

        return order;
    }

    // Runs the order and reads the payable amount from the log
    private double getPayableAmount(
            Map<String, Object> order) throws Exception {

        OrderBillingEngine engine = new OrderBillingEngine();

        Path logFile = tempDir.resolve("test_order.log");

        engine.setLogFilePath(logFile.toString());

        assertTrue(engine.processOrder(order));

        String log = Files.readString(logFile);

        String marker = " | Payable: PKR ";
        int start = log.lastIndexOf(marker) + marker.length();

        int end = log.indexOf(" | Status", start);

        return Double.parseDouble(log.substring(start, end));
    }

    // Test 1: Regular customer
    @Test
    void testRegularCustomerDiscount() throws Exception {

        Map<String, Object> order =
                createOrder("REGULAR", 1000.0, 1, 1);

        double amount = getPayableAmount(order);

        assertEquals(1170.0, amount, 0.01);
    }

    // Test 2: VIP customer with total above 5000
    @Test
    void testVipCustomerAbove5000() throws Exception {

        Map<String, Object> order =
                createOrder("VIP", 6000.0, 1, 1);

        double amount = getPayableAmount(order);

        assertEquals(5967.0, amount, 0.01);
    }

    // Test 3: VIP customer with total below 5000
    @Test
    void testVipCustomerBelow5000() throws Exception {

        Map<String, Object> order =
                createOrder("VIP", 4000.0, 1, 1);

        double amount = getPayableAmount(order);

        assertEquals(4212.0, amount, 0.01);
    }

    // Test 4: Student customer
    @Test
    void testStudentCustomerDiscount() throws Exception {

        Map<String, Object> order =
                createOrder("STUDENT", 2000.0, 1, 1);

        double amount = getPayableAmount(order);

        assertEquals(2152.8, amount, 0.01);
    }

    // Test 5: Corporate customer with 10 items
    @Test
    void testCorporateCustomerWith10Items() throws Exception {

        Map<String, Object> order =
                createOrder("CORPORATE", 100.0, 1, 10);

        double amount = getPayableAmount(order);

        assertEquals(936.0, amount, 0.01);
    }

    // Test 6: Corporate customer with fewer than 10 items
    @Test
    void testCorporateCustomerWithLessThan10Items()
            throws Exception {

        Map<String, Object> order =
                createOrder("CORPORATE", 100.0, 1, 1);

        double amount = getPayableAmount(order);

        assertEquals(111.15, amount, 0.01);
    }

    // Test 7: Regular customer with total above 10000
    @Test
    void testRegularCustomerAbove10000() throws Exception {

        Map<String, Object> order =
                createOrder("REGULAR", 12000.0, 1, 1);

        double amount = getPayableAmount(order);

        assertEquals(13455.0, amount, 0.01);
    }
    
    
    // Helper method to test whether an order is accepted
    private boolean processForTest(Map<String, Object> order)
            throws Exception {

        OrderBillingEngine engine = new OrderBillingEngine();

        Path logFile = Files.createTempFile(
                tempDir, "order", ".log");

        engine.setLogFilePath(logFile.toString());

        return engine.processOrder(order);
    }

    // ==========================================
    // PAYMENT TESTS
    // ==========================================

    // Test 8: Valid credit card
    @Test
    void testValidCreditCardPayment() throws Exception {

        Map<String, Object> order =
                createOrder("REGULAR", 1000.0, 1, 1);

        order.put("paymentMethod", "CREDIT_CARD");
        order.put("cardNumber", "1234567812345678");

        assertTrue(processForTest(order));
    }

    // Test 9: Invalid credit card
    @Test
    void testInvalidCreditCardPayment() throws Exception {

        Map<String, Object> order =
                createOrder("REGULAR", 1000.0, 1, 1);

        order.put("paymentMethod", "CREDIT_CARD");
        order.put("cardNumber", "12345");

        assertFalse(processForTest(order));
    }

    // Test 10: Valid EasyPaisa payment
    @Test
    void testValidEasyPaisaPayment() throws Exception {

        Map<String, Object> order =
                createOrder("REGULAR", 1000.0, 1, 1);

        order.put("paymentMethod", "EASYPAISA");
        order.put("mobileNumber", "03001234567");

        assertTrue(processForTest(order));
    }

    // Test 11: Invalid EasyPaisa number
    @Test
    void testInvalidEasyPaisaPayment() throws Exception {

        Map<String, Object> order =
                createOrder("REGULAR", 1000.0, 1, 1);

        order.put("paymentMethod", "EASYPAISA");
        order.put("mobileNumber", "12345");

        assertFalse(processForTest(order));
    }

    // Test 12: Cash on delivery within the limit
    @Test
    void testCashOnDeliveryWithinLimit() throws Exception {

        Map<String, Object> order =
                createOrder("REGULAR", 1000.0, 1, 1);

        order.put("paymentMethod", "CASH_ON_DELIVERY");

        assertTrue(processForTest(order));
    }

    // Test 13: Cash on delivery above the limit
    @Test
    void testCashOnDeliveryAboveLimit() throws Exception {

        Map<String, Object> order =
                createOrder("REGULAR", 26000.0, 1, 1);

        order.put("paymentMethod", "CASH_ON_DELIVERY");

        assertFalse(processForTest(order));
    }

    // Test 14: Unsupported payment method
    @Test
    void testUnsupportedPaymentMethod() throws Exception {

        Map<String, Object> order =
                createOrder("REGULAR", 1000.0, 1, 1);

        order.put("paymentMethod", "PAYPAL");

        assertFalse(processForTest(order));
    }

    // ==========================================
    // VALIDATION AND EDGE CASE TESTS
    // ==========================================

    // Test 15: Null order
    @Test
    void testNullOrder() throws Exception {

        assertFalse(processForTest(null));
    }

    // Test 16: Empty order
    @Test
    void testEmptyOrder() throws Exception {

        Map<String, Object> order = new HashMap<>();

        assertFalse(processForTest(order));
    }

    // Test 17: Invalid email
    @Test
    void testInvalidEmail() throws Exception {

        Map<String, Object> order =
                createOrder("REGULAR", 1000.0, 1, 1);

        order.put("customerEmail", "invalid-email");

        assertFalse(processForTest(order));
    }

    // Test 18: Empty cart
    @Test
    void testEmptyCart() throws Exception {

        Map<String, Object> order =
                createOrder("REGULAR", 1000.0, 1, 1);

        order.put("items", new ArrayList<>());

        assertFalse(processForTest(order));
    }

    // Test 19: Missing price
    @Test
    void testMissingPrice() throws Exception {

        Map<String, Object> order =
                createOrder("REGULAR", 1000.0, 1, 1);

        List<Map<String, Object>> items =
                (List<Map<String, Object>>) order.get("items");

        items.get(0).remove("price");

        assertFalse(processForTest(order));
    }

    // Test 20: Missing quantity
    @Test
    void testMissingQuantity() throws Exception {

        Map<String, Object> order =
                createOrder("REGULAR", 1000.0, 1, 1);

        List<Map<String, Object>> items =
                (List<Map<String, Object>>) order.get("items");

        items.get(0).remove("quantity");

        assertFalse(processForTest(order));
    }

    // Test 21: Negative price
    @Test
    void testNegativePrice() throws Exception {

        Map<String, Object> order =
                createOrder("REGULAR", -100.0, 1, 1);

        assertFalse(processForTest(order));
    }

    // Test 22: Zero quantity
    @Test
    void testZeroQuantity() throws Exception {

        Map<String, Object> order =
                createOrder("REGULAR", 1000.0, 0, 1);

        assertFalse(processForTest(order));
    }

}
