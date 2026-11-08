/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.university.lab.assignment2.repository;

/**
 *
 * @author natas
 */

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class FileOrderRepository implements OrderRepository {

    private final String filePath;

    public FileOrderRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void saveOrder(String email, double payableAmount,
                          String paymentStatus) throws IOException {

        try (FileWriter writer = new FileWriter(filePath, true)) {

            writer.write(
                    LocalDateTime.now()
                    + " | Order for: " + email
                    + " | Payable: PKR " + payableAmount
                    + " | Status: " + paymentStatus + "\n");
        }
    }
}
