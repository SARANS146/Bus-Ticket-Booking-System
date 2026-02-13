# Bus-Ticket-Booking-System
Project Overview  A comprehensive Bus Ticket Booking System built using Java Servlets, JSP, and Oracle Database. This web application allows users to manage bus ticket bookings with complete CRUD operations. The system follows MVC architecture with proper layered development (Bean, DAO, Service, Servlet).

Core Functionalities:

    Add New Booking - Register passengers with bus details, seat number, travel date, and ticket information

    View Booking - Retrieve specific booking using passenger name and travel date

    View All Bookings - Display all bus ticket records in a tabular format

    Automated Record ID Generation - Custom format: YYYYMMDD+First2LettersOfName+SequenceNumber

    Validation Layer - Input validation for passenger name, travel date, duplicate entries

    Error Handling - Comprehensive exception handling with user-friendly messages

Technical Highlights:

    MVC Architecture - Clean separation between presentation, business logic, and data access layers

    Database Integration - Oracle SQL with sequences for auto-increment functionality

    Servlet & JSP - Server-side processing with Jakarta EE (Tomcat 11)

    JDBC - Database connectivity with connection management

    Responsive UI - Simple, clean HTML/CSS interface with navigation menu
