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
}
