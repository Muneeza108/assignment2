/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.university.lab.assignment2;

/**
 *
 * @author natas
 */

import com.university.lab.assignment2.tax.TaxCalculator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaxCalculatorTest {

    private final TaxCalculator calculator =
            new TaxCalculator();

    @Test
    void testPakistanTax() {
        assertEquals(170.0, calculator.calculateTax(1000, "PK"), 0.01);
    }

    @Test
    void testUsTax() {
        assertEquals(80.0, calculator.calculateTax(1000, "US"), 0.01);
    }

    @Test
    void testUkTax() {
        assertEquals(200.0, calculator.calculateTax(1000, "UK"), 0.01);
    }

    @Test
    void testUnknownCountryUsesDefaultTax() {
        assertEquals(150.0, calculator.calculateTax(1000, "OTHER"), 0.01);
    }

    @Test
    void testNullCountryUsesPakistanTax() {
        assertEquals(170.0, calculator.calculateTax(1000, null), 0.01);
    }
}
