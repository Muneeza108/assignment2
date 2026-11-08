/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.university.lab.assignment2;

import com.university.lab.assignment2.model.OrderItem;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {

    @Test
    void testGetPrice() {
        OrderItem item = new OrderItem(500.0, 2);

        assertEquals(500.0, item.getPrice(), 0.01);
    }

    @Test
    void testGetQuantity() {
        OrderItem item = new OrderItem(500.0, 2);

        assertEquals(2, item.getQuantity());
    }

    @Test
    void testGetSubtotal() {
        OrderItem item = new OrderItem(500.0, 2);

        assertEquals(1000.0, item.getSubtotal(), 0.01);
    }
}

