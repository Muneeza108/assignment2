/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.university.lab.assignment2.discount;

/**
 *
 * @author natas
 */

import java.util.List;
import java.util.Map;

public class DiscountCalculator {

    public double calculateDiscount(
            double rawTotal,
            String customerType,
            List<Map<String, Object>> items) {

        if (customerType == null) {
            customerType = "REGULAR";
        }

        double discount = 0.0;

        if (customerType.equals("VIP")) {

            if (rawTotal > 5000) {
                discount = rawTotal * 0.15;
            } else {
                discount = rawTotal * 0.10;
            }

        } else if (customerType.equals("STUDENT")) {

            discount = rawTotal * 0.08;

        } else if (customerType.equals("CORPORATE")) {

            if (items.size() >= 10) {
                discount = rawTotal * 0.20;
            } else {
                discount = rawTotal * 0.05;
            }

        } else {

            if (rawTotal > 10000) {
                discount = 500.0;
            }
        }

        return discount;
    }
}

