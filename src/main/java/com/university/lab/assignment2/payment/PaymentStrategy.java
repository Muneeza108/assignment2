/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.university.lab.assignment2.payment;

/**
 *
 * @author natas
 */

public interface PaymentStrategy {

    String processPayment(double payableAmount, String paymentDetail);
}
