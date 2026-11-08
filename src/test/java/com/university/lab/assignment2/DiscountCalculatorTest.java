/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.university.lab.assignment2;

/**
 *
 * @author natas
 */

import com.university.lab.assignment2.discount.DiscountCalculator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DiscountCalculatorTest {

    private final DiscountCalculator calculator =
            new DiscountCalculator();

    @Test
    void testVipDiscountAbove5000() {
        assertEquals(
                900.0,
                calculator.calculateDiscount(6000, "VIP", 1),
                0.01
        );
    }

    @Test
    void testStudentDiscount() {
        assertEquals(
                160.0,
                calculator.calculateDiscount(2000, "STUDENT", 1),
                0.01
        );
    }

    @Test
    void testCorporateDiscountForTenItems() {
        assertEquals(
                200.0,
                calculator.calculateDiscount(1000, "CORPORATE", 10),
                0.01
        );
    }

    @Test
    void testRegularDiscountAbove10000() {
        assertEquals(
                500.0,
                calculator.calculateDiscount(12000, "REGULAR", 1),
                0.01
        );
    }

    @Test
    void testNoRegularDiscountBelow10000() {
        assertEquals(
                0.0,
                calculator.calculateDiscount(5000, "REGULAR", 1),
                0.01
        );
    }
}

