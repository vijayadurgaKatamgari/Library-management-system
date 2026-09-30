# 📚 Library Management System

A complete beginner-friendly Library Management System built with:

- **Frontend:** HTML5, CSS3, JavaScript
- **Backend:** Java 17 + Jakarta Servlets
- **Database:** MySQL
- **Database Connectivity:** JDBC
- **Server:** Apache Tomcat 10+
- **Build:** Maven

## Features

### 👤 Authentication
- Admin login
- Session-based authentication
- Logout

### 📖 Books
- Add books
- View/search books
- Update books
- Delete books
- Track available copies

### 👨‍🎓 Members
- Add members
- View members
- Delete members

### 🔄 Circulation
- Issue a book
- Return a book
- Automatic due date
- Automatic fine calculation

### 💳 Payments
- Record fines/payments
- Payment methods: Cash, UPI, Card, Net Banking
- Update payment method after payment
- Payment status: Paid/Pending

### 📊 Dashboard
- Total books
- Available copies
- Members
- Active issues
- Pending fines

## Default login

Username: `admin`  
Password: `admin123`

## 1. Create database

Open MySQL Workbench and run:

`database/library.sql`

## 2. Configure database

Open:

`src/main/java/com/library/util/DBConnection.java`

Change:

```java
private static final String USER = "root";
private static final String PASSWORD = "root";
```

to your MySQL username/password.

## 3. Run

Install:
- JDK 17+
- Maven
- MySQL 8+
- Tomcat 10+

Then:

```bash
mvn clean package
```

Copy:

```text
target/library-management.war
```

to Tomcat's `webapps` folder.

Start Tomcat and open:

```text
http://localhost:8080/library-management/
```

## GitHub

Create a GitHub repository and upload the complete folder.

Do NOT upload real database passwords or production secrets.

## Project structure

```text
LibraryManagementSystem/
├── pom.xml
├── README.md
├── database/
│   └── library.sql
└── src/
    └── main/
        ├── java/com/library/
        │   ├── model/
        │   ├── dao/
        │   ├── servlet/
        │   └── util/
        └── webapp/
            ├── index.html
            ├── dashboard.html
            ├── books.html
            ├── members.html
            ├── circulation.html
            ├── payments.html
            ├── css/style.css
            └── js/app.js
```
