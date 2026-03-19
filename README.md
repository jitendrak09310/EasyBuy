# EasyBuy
An e-commerce platform that enables individuals to create their own online store, allowing them to list, manage, and sell their products directly through a personalized website.
EasyBuy – Secure RBAC Asset Management System
Overview
A Spring Boot REST API implementing JWT-based authentication and fine-grained role-based access control for projects and assets.
Why This Project?
- Demonstrates real-world RBAC
- Object-level authorization
- Stateless JWT authentication
- Team and ownership-based data isolation
Tech Stack
- Java 17
- Spring Boot 3
- Spring Security 6
- JWT (JJWT)
- MySQL
- Maven
Architecture
Controller → Service → Repository → Database

Security Layer:
- JWT Filter
- Spring Security
- Service-level RBAC
Roles & Access Control
ADMIN → Full access
MANAGER → Team-level access
USER → Own data only
Setup Instructions
1. Clone repository
2. Create MySQL DB: easybuy
3. Configure application.properties
4. Run: mvn spring-boot:run
API Example
POST /login
{
  "userName": "admin",
  "password": "admin123"
}
→ Returns JWT token
Demo Flow
1. Create Admin
2. Create Managers & Users
3. Create assets
4. Test access via GET /assets/getAllAssets
Improvements
- Move JWT secret to env variable
- Add refresh tokens
- Add global exception handler
- Add validation annotations
Highlights
- Production-style RBAC
- Clean layered architecture
- Strong security design
