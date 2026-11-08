/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.university.lab.assignment2.model;

/**
 *
 * @author natas
 */

import java.util.List;

public class Order {

    private final String customerEmail;
    private final String customerType;
    private final String country;
    private final List<OrderItem> items;

    private final String paymentMethod;
    private final String cardNumber;
    private final String mobileNumber;

    private final String notifyChannel;
    private final String phone;

    public Order(
            String customerEmail,
            String customerType,
            String country,
            List<OrderItem> items,
            String paymentMethod,
            String cardNumber,
            String mobileNumber,
            String notifyChannel,
            String phone) {

        this.customerEmail = customerEmail;
        this.customerType = customerType;
        this.country = country;
        this.items = List.copyOf(items);
        this.paymentMethod = paymentMethod;
        this.cardNumber = cardNumber;
        this.mobileNumber = mobileNumber;
        this.notifyChannel = notifyChannel;
        this.phone = phone;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public String getCustomerType() {
        return customerType;
    }

    public String getCountry() {
        return country;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getNotifyChannel() {
        return notifyChannel;
    }

    public String getPhone() {
        return phone;
    }
}
