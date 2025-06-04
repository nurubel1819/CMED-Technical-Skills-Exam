# 📄 Prescription Management System

A simple web-based application for managing medical prescriptions using **Spring Boot**, **JWT-based authentication**, and **H2 in-memory database**.

---

## 🚀 How to Run the Project

1. **Start the Spring Boot application** (`PrescriptionManagementSystemApplication.java`)
2. Open your browser and go to:  
   👉 [http://localhost:8080](http://localhost:8080)
3. Click the **"Login with Dummy Data First Time"** button to insert sample data.
4. Login using the default credentials:

| Role    | Phone        | Password |
|---------|--------------|----------|
| Doctor  | 01753278408  | 123      |
| Admin   | 01753278407  | 123      |

---

## ✅ Required Features

1. 🔐 **Authentication and Authorization** using **JWT**
2. 💾 Using **H2 Database** (in-memory)
3. 📝 Create new prescriptions with proper **form validation**
4. 📆 Show prescriptions for the **last 30 days** by default
5. 📊 Filter prescriptions by a **user-selected date range**
6. ✏️ **Edit prescription** entries with validation
7. 🗑️ **Delete prescription** entries
8. 📈 Generate **day-wise prescription count report** (date, count)
9. 🔄 REST API:  
   - `GET /api/v1/prescription` returns prescriptions in **JSON** via **Swagger**
   - Also displayed in **tabular format** in **Doctor Dashboard**
10. 🌐 External API Consumption:  
    - Fetch data from `https://jsonplaceholder.typicode.com/posts`  
    - Display it in **Doctor Dashboard** in **table format**

---

## 🎁 Extra Features (Bonus Points)

1. 🩺 **Patient registration** within the system
2. 👨‍⚕️ Doctor can view all **registered patients**
3. 🧾 Doctor can create prescriptions by fetching data from registered patients
4. 🛠️ **Admin Dashboard**
5. 👥 View all system users
6. 👨‍⚕️ View all system doctors
7. 👤 View all system patients
8. ➕ Add new **roles** in the system
9. 🔁 Assign **roles to specific users**
10. ❌ **Delete users** from the system

---

## 🛠️ Tech Stack

- Java 21
- Spring Boot 3.4.5
- Spring Security with JWT
- Spring Data JPA
- H2 In-Memory Database
- Thymeleaf (Frontend)
- Swagger (API Docs)
- HTML5, CSS3, JavaScript (Validation & UI)

---

## 🔗 Useful Links

- 🔍 [Swagger UI](http://localhost:8080/swagger-ui/index.html)
- 🔐 [H2 Console](http://localhost:8080/h2-console)  
  Use JDBC URL: `jdbc:h2:mem:testdb`


