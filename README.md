# 🎉 Pro Night – Event Management System (Backend)

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-4-6DB33F?logo=springboot&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring%20Security-JWT-6DB33F?logo=springsecurity&logoColor=white"/>
  <img src="https://img.shields.io/badge/MySQL-Database-4479A1?logo=mysql&logoColor=white"/>
  <img src="https://img.shields.io/badge/Hibernate-JPA-59666C?logo=hibernate&logoColor=white"/>
  <img src="https://img.shields.io/badge/Maven-Build-C71A36?logo=apachemaven&logoColor=white"/>
  <img src="https://img.shields.io/badge/Razorpay-Payment-0C7BFF"/>
  <img src="https://img.shields.io/badge/Gemini-AI-blueviolet"/>
</p>



# 📖 About The Project

**Pro Night** is a full-stack Event Management System backend built using **Java Spring Boot**. It provides secure REST APIs for user authentication, event management, ticket booking, inventory management, online payments, QR ticket generation, email notifications, AI-powered event recommendations, and admin operations.

The backend follows a layered architecture using **Controller → Service → Repository → Database**, secured with **Spring Security and JWT Authentication**.



# ✨ Features

## 🔐 Authentication & Security

- User Registration
- User Login
- JWT Authentication
- Role-Based Authorization (ADMIN & USER)
- BCrypt Password Encryption
- Forgot Password
- OTP Verification
- Password Reset
- Protected REST APIs
- CORS Configuration

---

# 👤 User Module

- Register User
- Login User
- View Profile
- Update Profile
- Membership Levels
- Loyalty Points
- User Dashboard

---

# 🎉 Event Module

- Create Event
- Update Event
- Delete Event
- View All Events
- Event Details
- Search Events
- Filter by Category
- Filter by City
- Filter by Season
- Filter by Mood
- Featured Events
- Trending Events
- Upcoming Events
- Live Events
- Past Events

---

# 🎫 Pass Module

- Create Pass
- Update Pass
- Delete Pass
- Event-wise Passes
- Pass Availability
- Low Stock Monitoring
- Tier Pricing
- Booking Limits
- Food Benefits
- Seating Information

---

# 📑 Booking Module

- Create Booking
- Booking Validation
- Booking History
- Booking Status
- Pending Booking
- Confirm Booking
- Cancel Booking
- Cancellation Request
- Admin Approval/Rejection

---

# 📦 Inventory Management

- Real-time Pass Availability
- Reserved Inventory
- Sold Quantity Tracking
- Available Quantity Calculation
- Pessimistic Database Locking
- Prevent Overselling
- Waitlist Support
- Automatic Inventory Refresh

---

# 💳 Payment Module

- Razorpay Order Creation
- Secure Payment Integration
- Payment Verification
- Signature Validation
- Payment Success
- Payment Failure Handling

---

# 🎟 QR Ticket Module

- Automatic QR Code Generation
- Digital Ticket Generation
- Ticket PDF Generation
- QR Ticket Download
- Email Ticket Delivery

---

# ✅ QR Ticket Validation

Admin can:

- Scan QR Ticket
- Validate Ticket
- Verify Booking
- Prevent Duplicate Entry
- Mark Ticket as Checked-In

---

# ❤️ Wishlist Module

- Add Event
- Remove Event
- View Wishlist

---

# 🎁 Rewards Module

- Loyalty Points
- Claim Rewards
- Membership Rewards
- Reward Dashboard

---

# 🤖 AI Recommendation Module

Powered by **Google Gemini AI**

Features:

- Personalized Recommendations
- User Interest Analysis
- Event Recommendation Engine
- AI Generated Suggestions

---

# 📧 Email Module

- OTP Email
- Booking Confirmation Email
- Ticket Email
- Cancellation Email
- Contact Support Email

---

# 📞 Contact Module

- Contact Form API
- Store Messages
- Send Email to Admin

---

# 👨‍💼 Admin Module

- Admin Dashboard
- Manage Users
- Manage Events
- Manage Passes
- Manage Bookings
- Revenue Dashboard
- Recent Bookings
- Low Stock Passes
- Ticket Validation
- Cancellation Approval
- Dashboard Analytics

---

# 🛠 Technology Stack

## Backend

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Security
- Spring Data JPA
- Hibernate ORM
- Maven

## Database

- MySQL

## Security

- JWT Authentication
- BCrypt Password Encoder

## Payment

- Razorpay Java SDK

## QR & PDF

- ZXing
- iTextPDF

## AI

- Google Gemini API

## Email

- Spring Mail



# 🏗 Architecture


                React Frontend
                      │
          REST API (HTTP Requests)
                      │
                      ▼
          Spring Boot Controllers
                      │
                      ▼
              Service Layer
                      │
                      ▼
            Repository Layer
                      │
                      ▼
               MySQL Database

External Services

• Razorpay
• Gemini AI
• Spring Mail
• QR Generator
• PDF Generator




# 📂 Project Structure


src/main/java
│
├── config
├── controller
├── dto
├── entity
├── enums
├── exception
├── repository
├── security
├── service
├── util
└── ProNightApplication.java


---

# 🗄 Database Tables

| Table | Description |
|--------|-------------|
| users | User & Admin Information |
| events | Event Details |
| passes | Event Passes |
| bookings | Ticket Bookings |
| wishlist | Saved Events |
| rewards | Rewards |
| claimed_rewards | Claimed Rewards |
| waitlist | Waiting List |
| contacts | Contact Messages |
| otp_table | OTP Verification |

---

# 🔗 Entity Relationships

- One User → Many Bookings
- One Event → Many Passes
- One Pass → Many Bookings
- One User → Many Wishlist Items
- One Reward → Many Claimed Rewards
- One Pass → Many Waitlist Entries

---

# 🔐 Authentication

JWT Authentication is used.

Example Header

http
Authorization: Bearer <JWT_TOKEN>

---

# 📡 REST API Modules

Authentication API

User API

Event API

Pass API

Booking API

Wishlist API

Reward API

Recommendation API

Admin API

Contact API

---

# 💳 Booking Workflow


User Login
      │
      ▼
Browse Events
      │
      ▼
Choose Pass
      │
      ▼
Create Booking
      │
      ▼
Inventory Validation
      │
      ▼
Create Razorpay Order
      │
      ▼
Payment Success
      │
      ▼
Verify Signature
      │
      ▼
Generate QR Ticket
      │
      ▼
Generate PDF
      │
      ▼
Send Email
      │
      ▼
Booking Confirmed

---

# 🎟 Ticket Validation Workflow

QR Ticket Generated
        │
        ▼
User Arrives at Event
        │
        ▼
Admin Opens Scanner
        │
        ▼
Scan QR
        │
        ▼
Backend Validation
        │
        ▼
Checked-In
        │
        ▼
Duplicate Scan Rejected


---

# ⚙️ Installation

## Clone Repository


git clone https://github.com/sahumansi105-lgtm/Eventora-backend.git


Move to project


cd Eventora-backend


Install Dependencies


mvn clean install


Run Application
mvn spring-boot:run


Backend runs on


http://localhost:8080


---

# 🗄 Database Configuration

Create Database

sql
CREATE DATABASE eventora;


Configure **application.properties**

properties
spring.datasource.url=jdbc:mysql://localhost:3306/eventora
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true


---

# 📌 Key Highlights

- Secure JWT Authentication
- Role-Based Authorization
- RESTful API Design
- Real-Time Inventory Management
- Razorpay Payment Gateway
- QR Code Ticket Generation
- PDF Ticket Generation
- Email Notification Service
- Google Gemini AI Integration
- Loyalty Rewards System
- Wishlist Management
- Waitlist Support
- QR Ticket Validation
- Responsive API Design
- Layered Architecture
- Clean Code Structure

---

# 🚀 Future Enhancements

- Payment Webhooks
- Seat Selection
- Coupon & Promo Code System
- Event Reviews & Ratings
- Audit Logs
- Redis Caching
- Cloud Deployment
- Unit Testing
- Docker Support
- CI/CD Pipeline
- API Documentation using Swagger

---

# 👩‍💻 Developer

**Mansi Sahu**

**Java Full Stack Developer**

### Skills

- Java
- Spring Boot
- Spring Security
- Hibernate
- MySQL
- REST APIs
- React.js
- JavaScript
- HTML
- CSS

### GitHub

https://github.com/sahumansi105-lgtm

### LinkedIn
(https://www.linkedin.com/in/mansi-shahu105)



# ⭐ Support

If you found this project useful, please give this repository a **⭐ Star**.

It motivates me to build more quality projects and contribute to the developer community.
