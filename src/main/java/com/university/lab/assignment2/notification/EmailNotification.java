/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.university.lab.assignment2.notification;

/**
 *
 * @author natas
 */

public class EmailNotification implements NotificationService {

    @Override
    public void sendNotification(
            String email,
            String phone,
            double payableAmount) {

        System.out.println(
                "[EMAIL DISPATCHED] To: " + email
                + " | Your order of PKR "
                + String.format("%.2f", payableAmount)
                + " is confirmed!");
    }
}
