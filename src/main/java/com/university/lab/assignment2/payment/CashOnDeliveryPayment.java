/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.university.lab.assignment2.payment;

/**
 *
 * @author natas
 */


public class CashOnDeliveryPayment implements PaymentStrategy {

    @Override
    public String processPayment(
            double payableAmount, String paymentDetail) {

        if (payableAmount > 25000) {

            System.out.println(
                    "Cash on delivery not allowed for orders above PKR 25,000.");

            return null;
        }

        return "PENDING_CASH";
    }
}

