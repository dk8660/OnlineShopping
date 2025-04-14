# T-Shopping: Online E-commerce Platform

T-Shopping is a full-stack e-commerce platform developed using Spring Boot. The application provides core commerce features including user registration and authentication, product management, shopping cart handling, order processing, and a mock payment mechanism.

---

## 1. Project Overview

- **Project Name**: T-Shopping (Online Shopping Mall)
- **Framework**: Spring Boot
- **Deployment**: AWS EC2 with Docker
- **Note**:  
  ~~Deployed at [http://13.209.43.90:8080](http://13.209.43.90:8080)~~  
  *(The server has been shut down due to expiration of free hosting plan.)*

---

## 2. Technology Stack

### Backend
- Java 23
- Spring Boot 3.4.2
- Spring Security (session-based authentication)
- JPA (Hibernate) & MariaDB
- Lombok

### Frontend
- Thymeleaf (template engine)
- HTML/CSS, JavaScript

### Deployment Environment
- AWS EC2
- Docker

---

## 3. Key Features

### ✅ User Registration & Login
- Session-based login via Spring Security
- Role-based access control: `USER`, `ADMIN`, `SELLER`
- Access restrictions applied by role

### ✅ Product Management
- `ADMIN` and `SELLER` roles can register, update, and delete products
- `USER` role can browse products and add them to the cart

### ✅ Shopping Cart & Order
- Users can convert their cart into an order
- Order status tracking (`PENDING`, `SUCCESS`, etc.)

### ✅ Payment System
- Simulated payment logic updates payment status after order completion
- Transaction management via `OrderService`

---

## 4. Project Architecture

- Based on **MVC (Model-View-Controller)** design pattern
- Layered architecture: Entity → Repository → Service → Controller
- Spring Boot + JPA for data persistence

---

## 5. Deployment Process (Docker & AWS EC2)

### 1. Containerization using Docker
- Build Spring Boot JAR and create Docker image
- Use `Dockerfile` to include the app and static resources

### 2. Deployment to AWS EC2
- Install Docker on EC2 instance and run application container
- Launch MariaDB container and connect to the application

```sh
# Run Spring Boot container
$ docker run -p 8080:8080 --name tshopping-container -d tshopping

# Run MariaDB container
$ docker run -p 3306:3306 --name mariadb-container \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=tutorial__dev \
  -d mariadb:10.6
```

### 3. (Archived) Deployment URL
- [http://13.209.43.90:8080](http://13.209.43.90:8080)

---

## 6. Troubleshooting Experience

### ❌ Issue: IoC Container failed to create Beans
- Cause: Application failed to start, preventing bean initialization
- Resolution: Resolved underlying exception identified through log analysis

### ❌ Issue: MariaDB connection error
- Cause: Incorrect use of `localhost` instead of Docker container name `mariadb-container` in `application.yml`
- Resolution: Modified Docker network settings to allow inter-container communication

---

## 7. Future Improvements

- Integrate actual payment gateway (PG)
- Refactor frontend using React
- Optimize queries and implement caching for performance enhancement

---

## 8. API Summary

### Member APIs
- `GET /member/join`: Display signup form
- `POST /member/join`: Process signup
- `GET /member/login`: Display login form
- `POST /member/login`: Process login
- `POST /member/logout`: Process logout

### Product APIs
- `GET /product`: View all products
- `GET /product/myproducts`: View products by current seller
- `GET /product/register`: Display product registration form
- `POST /product/doProductRegister`: Register product
- `DELETE /product/delete/{id}`: Delete product
- `GET /product/update/{id}`: Display product update form

### Order APIs
- `POST /order/create`: Create new order
- `GET /order/list`: View order history
- `GET /order/{id}`: View order details

### Cart APIs
- `GET /cart`: View cart
- `POST /cart/add`: Add item to cart
- `DELETE /cart/remove/{id}`: Remove item from cart

### Payment APIs
- `POST /payment/pay/{orderId}`: Process payment

