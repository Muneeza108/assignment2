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

import com.university.lab.assignment2.repository.OrderRepository;
import com.university.lab.assignment2.repository.FileOrderRepository;

import com.university.lab.assignment2.model.Order;
import com.university.lab.assignment2.model.OrderItem;

import java.io.IOException;
import java.util.ArrayList;
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
        List<Map<String, Object>> itemData =
                (List<Map<String, Object>>) orderData.get("items");

        if (itemData == null || itemData.isEmpty()) {
            System.out.println("Error: Cart has no items!");
            return false;
        }

        List<OrderItem> items = new ArrayList<>();
        double rawTotal = 0.0;

        for (Map<String, Object> item : itemData) {

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

            OrderItem orderItem = new OrderItem(price, quantity);

            items.add(orderItem);
            rawTotal += orderItem.getSubtotal();
        }

        String customerType =
                (String) orderData.getOrDefault(
                        "customerType", "REGULAR");

        String country =
                (String) orderData.getOrDefault("country", "PK");

        String paymentMethod =
                (String) orderData.getOrDefault(
                        "paymentMethod", "CASH");

        String cardNumber =
                (String) orderData.getOrDefault("cardNumber", "");

        String mobileNumber =
                (String) orderData.getOrDefault("mobileNumber", "");

        String notifyChannel =
                (String) orderData.getOrDefault(
                        "notifyChannel", "EMAIL");

        String phone = null;

        if (notifyChannel.equals("SMS")) {
            phone = (String) orderData.getOrDefault(
                    "phone", "0300-0000000");
        }

        Order order = new Order(
                email,
                customerType,
                country,
                items,
                paymentMethod,
                cardNumber,
                mobileNumber,
                notifyChannel,
                phone
        );

        double discount =
                discountCalculator.calculateDiscount(
                        rawTotal,
                        order.getCustomerType(),
                        order.getItems().size());

        double netAmount = rawTotal - discount;

        double tax =
                taxCalculator.calculateTax(
                        netAmount, order.getCountry());

        double payableAmount = netAmount + tax;

        PaymentStrategy paymentStrategy;
        String paymentDetail = "";

        if (order.getPaymentMethod().equals("CREDIT_CARD")) {

            paymentStrategy = new CreditCardPayment();
            paymentDetail = order.getCardNumber();

        } else if (order.getPaymentMethod().equals("EASYPAISA")) {

            paymentStrategy = new EasyPaisaPayment();
            paymentDetail = order.getMobileNumber();

        } else if (order.getPaymentMethod().equals("CASH_ON_DELIVERY")) {

            paymentStrategy = new CashOnDeliveryPayment();

        } else {

            System.out.println(
                    "Error: Unsupported payment method: "
                    + order.getPaymentMethod());

            return false;
        }

        String paymentStatus =
                paymentStrategy.processPayment(
                        payableAmount, paymentDetail);

        if (paymentStatus == null) {
            return false;
        }

        OrderRepository orderRepository =
                new FileOrderRepository(logFilePath);

        try {

            orderRepository.saveOrder(
                    order.getCustomerEmail(),
                    payableAmount,
                    paymentStatus);

        } catch (IOException e) {

            System.out.println(
                    "Failed to write order log: " + e.getMessage());
        }

        NotificationService notificationService = null;

        if (order.getNotifyChannel().equals("EMAIL")) {

            notificationService = new EmailNotification();

        } else if (order.getNotifyChannel().equals("SMS")) {

            notificationService = new SMSNotification();
        }

        if (notificationService != null) {

            notificationService.sendNotification(
                    order.getCustomerEmail(),
                    order.getPhone(),
                    payableAmount);
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
