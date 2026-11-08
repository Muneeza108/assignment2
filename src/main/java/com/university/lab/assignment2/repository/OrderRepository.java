/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.university.lab.assignment2.repository;

/**
 *
 * @author natas
 */

import java.io.IOException;

public interface OrderRepository {

    void saveOrder(String email, double payableAmount,
                   String paymentStatus) throws IOException;
}
