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
import com.university.lab.assignment2.tax.TaxCalculator;

import com.university.lab.assignment2.payment.PaymentStrategy;
import com.university.lab.assignment2.payment.CreditCardPayment;
import com.university.lab.assignment2.payment.EasyPaisaPayment;
import com.university.lab.assignment2.payment.CashOnDeliveryPayment;

import com.university.lab.assignment2.notification.NotificationService;
import com.university.lab.assignment2.notification.EmailNotification;
import com.university.lab.assignment2.notification.SMSNotification;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class OrderBillingEngine {

    private String logFilePath = "order_audit.log";

    private final DiscountCalculator discountCalculator =
            new DiscountCalculator();

    private final TaxCalculator taxCalculator =
            new TaxCalculator();

    public boolean processOrder(Map<String, Object> orderData) {

        if (orderData == null || orderData.isEmpty()) {
            System.out.println("Error: Order data is empty!");
            return false;
        }

        String email = (String) orderData.get("customerEmail");

        if (email == null || !email.contains("@")) {
            System.out.println("Error: Invalid customer email!");
            return false;
        }

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> items =
                (List<Map<String, Object>>) orderData.get("items");

        if (items == null || items.isEmpty()) {
            System.out.println("Error: Cart has no items!");
            return false;
        }

        double rawTotal = 0.0;

        for (Map<String, Object> item : items) {

            if (!item.containsKey("price")
                    || !item.containsKey("quantity")) {

                System.out.println(
                        "Error: Missing item price or quantity!");
                return false;
            }

            double price = (double) item.get("price");
            int quantity = (int) item.get("quantity");

            if (price <= 0 || quantity <= 0) {
                System.out.println(
                        "Error: Invalid price or quantity for item!");
                return false;
            }

            rawTotal += price * quantity;
        }

        String customerType =
                (String) orderData.getOrDefault(
                        "customerType", "REGULAR");

        double discount =
                discountCalculator.calculateDiscount(
                        rawTotal, customerType, items);

        double netAmount = rawTotal - discount;

        String country =
                (String) orderData.getOrDefault("country", "PK");

        double tax = taxCalculator.calculateTax(netAmount, country);

        double payableAmount = netAmount + tax;

        String paymentMethod =
                (String) orderData.getOrDefault(
                        "paymentMethod", "CASH");

        PaymentStrategy paymentStrategy;
        String paymentDetail = "";

        if (paymentMethod.equals("CREDIT_CARD")) {

            paymentStrategy = new CreditCardPayment();

            paymentDetail =
                    (String) orderData.getOrDefault("cardNumber", "");

        } else if (paymentMethod.equals("EASYPAISA")) {

            paymentStrategy = new EasyPaisaPayment();

            paymentDetail =
                    (String) orderData.getOrDefault(
                            "mobileNumber", "");

        } else if (paymentMethod.equals("CASH_ON_DELIVERY")) {

            paymentStrategy = new CashOnDeliveryPayment();

        } else {

            System.out.println(
                    "Error: Unsupported payment method: "
                    + paymentMethod);

            return false;
        }

        String paymentStatus =
                paymentStrategy.processPayment(
                        payableAmount, paymentDetail);

        if (paymentStatus == null) {
            return false;
        }

        try (FileWriter writer =
                new FileWriter(logFilePath, true)) {

            writer.write(
                    LocalDateTime.now()
                    + " | Order for: " + email
                    + " | Payable: PKR " + payableAmount
                    + " | Status: " + paymentStatus + "\n");

        } catch (IOException e) {

            System.out.println(
                    "Failed to write order log: " + e.getMessage());
        }

        String notifyChannel =
                (String) orderData.getOrDefault(
                        "notifyChannel", "EMAIL");

        String phone = null;

NotificationService notificationService = null;

        if (notifyChannel.equals("EMAIL")) {

            notificationService = new EmailNotification();

        } else if (notifyChannel.equals("SMS")) {

    phone = (String) orderData.getOrDefault(
            "phone", "0300-0000000");

    notificationService = new SMSNotification();
}

        if (notificationService != null) {

            notificationService.sendNotification(
                    email, phone, payableAmount);
        }

        return true;
    }

    public void setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    public String getLogFilePath() {
        return logFilePath;
    }
}
