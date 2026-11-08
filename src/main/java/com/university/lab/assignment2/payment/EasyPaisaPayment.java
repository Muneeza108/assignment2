/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.university.lab.assignment2.payment;

/**
 *
 * @author natas
 */


public class EasyPaisaPayment implements PaymentStrategy {

    @Override
    public String processPayment(
            double payableAmount, String paymentDetail) {

        String mobileNo = paymentDetail;

        if (mobileNo == null) {
            mobileNo = "";
        }

        if (mobileNo.length() == 11
                && mobileNo.startsWith("03")) {

            System.out.println(
                    "Sending EasyPaisa OTP push to "
                    + mobileNo + " for PKR " + payableAmount);

            return "PAID";
        }

        System.out.println(
                "EasyPaisa failed: Invalid mobile number.");

        return null;
    }
}
