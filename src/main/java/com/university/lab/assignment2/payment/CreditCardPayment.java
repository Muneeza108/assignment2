/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.university.lab.assignment2.payment;

/**
 *
 * @author natas
 */



public class CreditCardPayment implements PaymentStrategy {

    @Override
    public String processPayment(
            double payableAmount, String paymentDetail) {

        String cardNumber = paymentDetail;

        if (cardNumber == null) {
            cardNumber = "";
        }

        if (cardNumber.length() == 16
                && cardNumber.matches("\\d+")) {

            System.out.println(
                    "Processing credit card payment of PKR "
                    + payableAmount);

            return "PAID";
        }

        System.out.println("Card declined: Must be 16 digits.");

        return null;
    }
}
