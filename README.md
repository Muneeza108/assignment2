
# Legacy Code Refactoring and Testing in Java

## Project Objective

The objective of this project is to refactor a legacy Java order billing system by identifying code smells, applying SOLID principles, implementing suitable design patterns, and verifying that the refactored application preserves its original behavior through automated unit testing.

## Technologies Used

- Java 17
- Apache NetBeans
- Maven
- JUnit 5
- Git and GitHub

## Project Features

The application processes customer orders and performs:

- Customer and order validation
- Discount calculation
- Country-based tax calculation
- Credit card payment
- EasyPaisa payment
- Cash on delivery
- Email and SMS notifications
- Order audit logging

## Refactoring and Design

The original billing engine contained multiple responsibilities in one class. It was refactored into smaller components.

### Main Components

- `OrderBillingEngine`: Coordinates the order-processing workflow.
- `Order`: Represents a complete customer order.
- `OrderItem`: Represents an individual item.
- `DiscountCalculator`: Calculates customer discounts.
- `TaxCalculator`: Calculates country-based taxes.
- `PaymentStrategy`: Defines the payment strategy interface.
- `CreditCardPayment`: Handles credit card payments.
- `EasyPaisaPayment`: Handles EasyPaisa payments.
- `CashOnDeliveryPayment`: Handles cash-on-delivery payments.
- `NotificationService`: Defines the notification interface.
- `EmailNotification`: Handles email notifications.
- `SMSNotification`: Handles SMS notifications.
- `OrderRepository`: Defines the order storage contract.
- `FileOrderRepository`: Saves order records to a file.

### Design Principles and Patterns

The project applies:

- Single Responsibility Principle (SRP)
- Open/Closed Principle (OCP)
- Dependency Inversion Principle (DIP)
- Strategy Pattern for payment processing
- Interface-based design for notifications and persistence

## Running the Project

1. Open the project in Apache NetBeans.
2. Make sure JDK 17 or later is configured.
3. Open the Maven project.
4. Build the project to download dependencies and compile the code.

## Running Unit Tests

1. Right-click the project in NetBeans.
2. Select **Test**.
3. Maven Surefire runs the JUnit 5 test suite.
4. Review the test results in the Output window.

## Testing

The project includes characterization tests for the original billing behavior and additional unit tests for the extracted calculators and model classes.

The test suite covers:

- Discount calculations
- Tax calculations
- Payment validation
- Invalid order data
- Order and item models

## Repository Structure

```text
src/
├── main/java/com/university/lab/assignment2/
│   ├── OrderBillingEngine.java
│   ├── model/
│   ├── discount/
│   ├── tax/
│   ├── payment/
│   ├── notification/
│   └── repository/
└── test/java/com/university/lab/assignment2/
    ├── OrderBillingEngineTest.java
    ├── DiscountCalculatorTest.java
    ├── TaxCalculatorTest.java
    ├── OrderItemTest.java
    └── OrderTest.java
```

## Conclusion

The legacy billing system was refactored into modular components with clearer responsibilities. Design patterns and SOLID principles improved separation of concerns and maintainability. Automated tests help verify that the original order-processing behavior is preserved.
```

