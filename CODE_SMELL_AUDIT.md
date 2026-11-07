
# Code Smell and SOLID Principle Audit

## Project Information

**Project:** Legacy Code Refactoring and Testing in Java

**Main Class:** OrderBillingEngine.java

**Package:** com.university.lab.assignment2

**Purpose:** This document identifies code smells and SOLID principle violations in the original legacy billing engine before refactoring.

---

## 1. Code Smell Audit

### 1. God Class

**Location:** OrderBillingEngine.java - OrderBillingEngine class

**Problem:**
The OrderBillingEngine class performs multiple responsibilities, including order validation, discount calculation, tax calculation, payment processing, file logging, and customer notifications.

**Why it is a problem:**
The class becomes difficult to maintain, understand, and test because many unrelated operations are combined in one place.

**Proposed Solution:**
Separate responsibilities into smaller classes such as DiscountCalculator, TaxCalculator, payment classes, notification classes, and repository classes.

### 2. Long Method

**Location:** OrderBillingEngine.java - processOrder() method

**Problem:**
The processOrder() method contains a large amount of code for validating orders, calculating totals, processing payments, saving logs, and sending notifications.

**Why it is a problem:**
A long method is difficult to understand, debug, modify, and test.

**Proposed Solution:**
Divide processOrder() into smaller methods and delegate individual responsibilities to separate classes.

### 3. Magic Numbers

**Location:** OrderBillingEngine.java - discount, tax, and payment calculations

**Problem:**
The code contains hardcoded values such as 0.15, 0.17, 0.08, 0.20, 5000, 10000, and 25000.

**Why it is a problem:**
The purpose of these numbers is not immediately clear. Changing business rules requires searching through the code.

**Proposed Solution:**
Replace hardcoded values with meaningful named constants or configuration values.

### 4. Primitive Obsession

**Location:** OrderBillingEngine.java - processOrder(Map<String, Object> orderData)

**Problem:**
Order information is stored in a Map<String, Object>, with values accessed through string keys such as customerEmail, customerType, price, and quantity.

**Why it is a problem:**
This approach is not type-safe and can lead to casting errors and incorrect key names.

**Proposed Solution:**
Create dedicated model classes such as Order and OrderItem to represent order information using appropriate data types.

### 5. Tight Coupling

**Location:** OrderBillingEngine.java - payment and notification conditional statements

**Problem:**
The billing engine directly contains the implementation details of credit card, EasyPaisa, cash-on-delivery, email, and SMS operations.

**Why it is a problem:**
Adding or modifying a payment method or notification channel requires changing the main billing class.

**Proposed Solution:**
Use the Strategy Pattern for payments and a NotificationService interface for notifications.

### 6. Direct File Handling

**Location:** OrderBillingEngine.java - FileWriter section

**Problem:**
The billing engine directly creates a FileWriter and handles file-writing operations.

**Why it is a problem:**
Business logic is mixed with data persistence, making the class harder to test and maintain.

**Proposed Solution:**
Introduce an OrderRepository interface and a FileOrderRepository implementation.

---

## 2. SOLID Principle Violations

### 1. Single Responsibility Principle (SRP)

**Location:** OrderBillingEngine.java - lines: 22 - 239

**Violation:**
The class has several reasons to change because it handles validation, calculations, payment processing, logging, and notifications.

**Proposed Solution:**
Separate each responsibility into its own class.

### 2. Open/Closed Principle (OCP)

**Location:** OrderBillingEngine.java - lines: 129 - 152


**Violation:**
Adding a new payment method requires modifying the existing payment conditional statements.

**Proposed Solution:**
Introduce a PaymentStrategy interface and separate payment implementations. New payment methods can then be added without changing the existing payment implementations.

### 3. Dependency Inversion Principle (DIP)

**Location:** OrderBillingEngine.java - lines: 198 - 236

**Violation:**
The high-level billing class directly depends on concrete implementation details instead of depending on abstractions.

**Proposed Solution:**
Use interfaces such as PaymentStrategy, NotificationService, and OrderRepository, and inject their implementations into the billing engine.

---

## 3. Refactoring Plan

The following improvements will be implemented:

1. Extract discount calculation into DiscountCalculator.
2. Extract tax calculation into TaxCalculator.
3. Implement the Strategy Pattern for payment processing.
4. Introduce NotificationService for email and SMS notifications.
5. Introduce OrderRepository for file persistence.
6. Create Order and OrderItem model classes.
7. Simplify OrderBillingEngine so it coordinates the other components.

## Conclusion

The original OrderBillingEngine contains multiple code smells and violates important SOLID principles. Refactoring it into smaller, independent, and reusable components will improve maintainability, readability, testability, and extensibility.

