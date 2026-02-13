package com.wipro.bus.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.wipro.bus.bean.BusBookingBean;
import com.wipro.bus.service.Administrator;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/MainServlet")  
public class MainServlet extends HttpServlet {

    Administrator admin = new Administrator();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String operation = request.getParameter("operation");
        System.out.println("Operation: " + operation); // Debug log

        try {
            if ("newRecord".equals(operation)) {
                // Handle Add Booking
                addNewRecord(request, response);
            } 
            else if ("viewRecord".equals(operation)) {
                // Handle View Single Booking
                viewRecord(request, response);
            } 
            else if ("viewAllRecords".equals(operation)) {
                // Handle View All Bookings
                viewAllRecords(request, response);
            }
            else {
                // Invalid operation
                response.sendRedirect("menu.html");
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h2 style='color:red'>System Error: " + e.getMessage() + "</h2>");
            out.println("<a href='menu.html'>Go Back to Menu</a>");
        }
    }

    private void addNewRecord(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        try {
            // Get all parameters
            String passengerName = request.getParameter("passengerName");
            String busNumber = request.getParameter("busNumber");
            String seatNo = request.getParameter("seatNo");
            String ticketNo = request.getParameter("ticketNo");
            String remarks = request.getParameter("remarks");
            String dateStr = request.getParameter("travelDate");

            // Validate required fields
            if (passengerName == null || passengerName.trim().isEmpty()) {
                out.println("<h2 style='color:red'>Error: Passenger Name is required!</h2>");
                out.println("<a href='addBusBooking.jsp'>Go Back</a>");
                return;
            }
            
            if (busNumber == null || busNumber.trim().isEmpty()) {
                out.println("<h2 style='color:red'>Error: Bus Number is required!</h2>");
                out.println("<a href='addBusBooking.jsp'>Go Back</a>");
                return;
            }
            
            if (dateStr == null || dateStr.trim().isEmpty()) {
                out.println("<h2 style='color:red'>Error: Travel Date is required!</h2>");
                out.println("<a href='addBusBooking.jsp'>Go Back</a>");
                return;
            }

            // Create bean and set values
            BusBookingBean bean = new BusBookingBean();
            bean.setPassengerName(passengerName.trim());
            bean.setBusNumber(busNumber.trim());
            bean.setSeatNo(seatNo != null ? seatNo.trim() : "");
            bean.setTicketNo(ticketNo != null ? ticketNo.trim() : "");
            bean.setRemarks(remarks != null ? remarks.trim() : "");

            // Parse date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date travelDate = sdf.parse(dateStr);
            bean.setTravelDate(travelDate);

            // Call service method
            String result = admin.addRecord(bean);
            
            // Display result
            out.println("<html><head><title>Add Booking Result</title></head><body>");
            
            if (result == null || result.equals("FAIL")) {
                out.println("<h2 style='color:red'>Failed to add booking!</h2>");
                out.println("<p>Please check database connection and try again.</p>");
            } 
            else if (result.contains("INVALID")) {
                out.println("<h2 style='color:red'>" + result + "</h2>");
                if (result.equals("INVALID DATE")) {
                    out.println("<p>Travel date must be today or future date.</p>");
                } else if (result.equals("INVALID PASSENGER NAME")) {
                    out.println("<p>Passenger name must be at least 2 characters.</p>");
                } else if (result.equals("ALREADY EXISTS")) {
                    out.println("<p>This passenger already has a booking on this date.</p>");
                } else {
                    out.println("<p>Please check your input and try again.</p>");
                }
            } 
            else {
                out.println("<h2 style='color:green'>Booking Added Successfully!</h2>");
                out.println("<p>Record ID: <strong>" + result + "</strong></p>");
                out.println("<p>Passenger Name: " + bean.getPassengerName() + "</p>");
                out.println("<p>Travel Date: " + dateStr + "</p>");
                out.println("<p>Bus Number: " + bean.getBusNumber() + "</p>");
            }
            
            out.println("<br><a href='addBusBooking.jsp'>Add Another Booking</a><br>");
            out.println("<a href='menu.html'>Go Back to Menu</a>");
            out.println("</body></html>");
            
        } catch (java.text.ParseException e) {
            out.println("<h2 style='color:red'>Invalid Date Format!</h2>");
            out.println("<p>Please use YYYY-MM-DD format.</p>");
            out.println("<a href='addBusBooking.jsp'>Go Back</a>");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h2 style='color:red'>System Error: " + e.getMessage() + "</h2>");
            out.println("<a href='addBusBooking.jsp'>Go Back</a>");
        }
    }

    private void viewRecord(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            String passengerName = request.getParameter("passengerName");
            String dateStr = request.getParameter("travelDate");

            if (passengerName == null || passengerName.trim().isEmpty() ||
                dateStr == null || dateStr.trim().isEmpty()) {
                
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<h2 style='color:red'>Passenger Name and Travel Date are required!</h2>");
                out.println("<a href='viewBusBooking.jsp'>Go Back</a>");
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date travelDate = sdf.parse(dateStr);

            BusBookingBean bean = admin.viewRecord(passengerName.trim(), travelDate);
            request.setAttribute("bean", bean);
            
            RequestDispatcher rd = request.getRequestDispatcher("displayBusBooking.jsp");
            rd.forward(request, response);
            
        } catch (java.text.ParseException e) {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<h2 style='color:red'>Invalid Date Format!</h2>");
            out.println("<a href='viewBusBooking.jsp'>Go Back</a>");
        }
    }

    private void viewAllRecords(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<BusBookingBean> list = admin.viewAllRecords();
        request.setAttribute("list", list);
        
        RequestDispatcher rd = request.getRequestDispatcher("displayAllBusBookings.jsp");
        rd.forward(request, response);
    }

    // Also handle POST requests (redirect to GET)
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}