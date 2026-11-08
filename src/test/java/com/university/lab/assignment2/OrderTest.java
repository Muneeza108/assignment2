/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.university.lab.assignment2;

import com.university.lab.assignment2.model.Order;
import com.university.lab.assignment2.model.OrderItem;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void testOrderStoresCustomerDetails() {

        OrderItem item = new OrderItem(500.0, 2);

        Order order = new Order(
                "customer@example.com",
                "VIP",
                "PK",
                List.of(item),
                "CREDIT_CARD",
                "1234567890123456",
                "",
                "EMAIL",
                null
        );

        assertEquals("customer@example.com", order.getCustomerEmail());
        assertEquals("VIP", order.getCustomerType());
        assertEquals("PK", order.getCountry());
        assertEquals("CREDIT_CARD", order.getPaymentMethod());
    }

    @Test
    void testOrderStoresItems() {

        OrderItem item = new OrderItem(300.0, 3);

        Order order = new Order(
                "customer@example.com",
                "REGULAR",
                "PK",
                List.of(item),
                "CREDIT_CARD",
                "1234567890123456",
                "",
                "EMAIL",
                null
        );

        assertEquals(1, order.getItems().size());
        assertEquals(900.0, order.getItems().get(0).getSubtotal(), 0.01);
    }

    @Test
    void testOrderItemsCannotBeModifiedThroughOriginalList() {

        List<OrderItem> items = new java.util.ArrayList<>();
        items.add(new OrderItem(100.0, 1));

        Order order = new Order(
                "customer@example.com",
                "REGULAR",
                "PK",
                items,
                "CREDIT_CARD",
                "1234567890123456",
                "",
                "EMAIL",
                null
        );

        items.clear();

        assertEquals(1, order.getItems().size());
    }
}
