# GBC Event Booking System

## 🌟 **Project Overview**

The **GBC Event Booking System** is a highly scalable and resilient microservices-based platform designed to streamline event booking processes at GBC. This system utilizes cutting-edge technologies and architectural patterns to ensure reliability, security, and seamless user experience.

- **Microservices Architecture**: Each key functionality (Room Booking, Event Management, Approval) is encapsulated within its own microservice, promoting modularity and scalability.
- **Secure Authentication**: Powered by **Keycloak** for secure user management and role-based access control (RBAC).
- **Event-Driven Architecture**: **Kafka** handles asynchronous communication between services to ensure real-time event processing.
- **Fault-Tolerant**: **Resilience4J**'s Circuit Breaker ensures system reliability by preventing cascading failures.
- **Swagger API Docs**: **Swagger** automatically generates comprehensive API documentation for ease of use.

---

## 🛠️ **Technologies Used**

The GBC Event Booking System leverages a suite of modern tools and frameworks to provide a robust solution:
- **Spring Cloud Gateway**: For routing requests between microservices and centralized traffic management.
- **Keycloak**: Secure user authentication and role-based access control.
- **Kafka**: A powerful message broker for asynchronous event-driven communication.
- **Schema Registry**: Ensures data consistency across services by validating message schemas in Kafka.
- **Resilience4J**: Implements Circuit Breakers to add fault tolerance and prevent cascading service failures.
- **Swagger**: Provides automated API documentation for each microservice.

---

## 🚀 **System Architecture**

The system adopts a **microservices architecture** with individual services for core functionalities such as:

- **Room Booking Service**: Manages event room availability and bookings.
- **Event Management Service**: Handles event creation, updates, and cancellations.
- **Approval Service**: Ensures event bookings go through an approval process, governed by role-based access control.

Services communicate via **Spring Cloud Gateway** for routing, while **Kafka** handles asynchronous messaging and event processing.

---

## ⚙️ **Key Features**

### 🔐 **Secure API Gateway**
- Configured **Spring Cloud Gateway** as the main entry point for user requests.
- Integrated **Keycloak** for seamless authentication and role-based access control.

### 💬 **Event Communication with Kafka**
- Implemented **Kafka** for handling asynchronous communication between microservices.
- Ensured **BookingService** publishes events, and **EventService** listens to those events in real time.

### 🔄 **Fault Tolerance with Resilience4J**
- Applied **Circuit Breaker** pattern via **Resilience4J** to ensure system reliability.
- Prevented cascading failures in key service-to-service communications, such as RoomService availability and approval processes.

### 📄 **Comprehensive API Documentation with Swagger**
- Integrated **Swagger** for detailed and easy-to-navigate API documentation.
- Aggregated all microservice APIs through **Spring Cloud Gateway** for centralized access.

---

## 🛠️ **Installation & Setup**

### **Prerequisites**
Before you begin, ensure that the following tools are installed:
- **Java 11+**
- **Docker** (for containerization)
- **Kafka** (for event-driven communication)
- **Docker Compose** (for multi-container management)
- **Keycloak** (for authentication)
- **Maven** (for building the project)

### **Cloning the Repository**
To get started, clone the repository to your local machine:
```bash
git clone https://github.com/utsav-1730/Event_Booking_App.git
cd Event_Booking_App
```

### **Running the System Locally**

1. **Start Kafka** using Docker:
   ```bash
   docker-compose up -d kafka
   ```

2. **Run Microservices** locally using Maven:
   ```bash
   ./mvnw spring-boot:run -Dspring-boot.run.profiles=local
   ```

3. **Set up Keycloak**:
   - Set up a Keycloak instance for authentication.
   - Configure roles, users, and clients.
   - Ensure all microservices are properly integrated with Keycloak for secure access.

### **Accessing the API**

Once the system is running, access the Swagger UI to explore API documentation for each service. All endpoints are centrally aggregated via the **Spring Cloud Gateway**.

## 🚧 **Challenges Faced**

### 🔑 **Role-Based Access Control (RBAC) with Keycloak**
- Configuring Keycloak for RBAC proved challenging, especially synchronizing roles across services. There were issues with token management that caused partial implementation of this feature.

### 📡 **Schema Registry Configuration**
- Running services in Docker created issues with schema validation. We encountered **“shared-schema not found”** errors, requiring us to resolve configurations across microservices.

### 🧑‍💻 **Asynchronous Messaging with Kafka**
- Managing Kafka event processing and ensuring data compatibility between services required careful planning. We focused on schema management via the **Schema Registry** to ensure message compatibility.

---

## 🌱 **Future Enhancements**

### ✅ **Complete RBAC Implementation**
- Finish the integration of Keycloak for complete role-based access control across all services.

### 📊 **Prometheus and Grafana Integration**
- Plan to integrate **Prometheus** for system monitoring and **Grafana** for enhanced observability (previously excluded).

### 🚀 **CI/CD Pipeline**
- Implement a **Continuous Integration/Continuous Deployment (CI/CD)** pipeline for automated testing, builds, and deployments using **Jenkins** or **GitHub Actions**.

