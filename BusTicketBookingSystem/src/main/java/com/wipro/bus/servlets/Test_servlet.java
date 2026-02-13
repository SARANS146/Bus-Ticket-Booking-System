package com.wipro.bus.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.wipro.bus.bean.BusBookingBean;
import com.wipro.bus.service.Administrator;

/**
 * Servlet implementation class Test_servlet
 */
@WebServlet("/Test_servlet")
public class Test_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	    Administrator admin = new Administrator();

	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {

	        String operation = request.getParameter("operation");

	        try {

	            if ("newRecord".equals(operation)) {

	                BusBookingBean bean = new BusBookingBean();

	                bean.setPassengerName(request.getParameter("passengerName"));
	                bean.setBusNumber(request.getParameter("busNumber"));
	                bean.setSeatNo(request.getParameter("seatNo"));
	                bean.setTicketNo(request.getParameter("ticketNo"));
	                bean.setRemarks(request.getParameter("remarks"));

	                String dateStr = request.getParameter("travelDate");

	                if (dateStr == null || dateStr.isEmpty()) {
	                    response.getWriter().println("Travel Date Missing!");
	                    return;
	                }

	                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	                Date travelDate = sdf.parse(dateStr);

	                bean.setTravelDate(travelDate);

	                String result = admin.addRecord(bean);

	                if ("FAIL".equals(result) || result.contains("INVALID"))
	                    response.sendRedirect("error.html");
	                else
	                    response.sendRedirect("success.html");

	            } 
	            else if ("viewRecord".equals(operation)) {

	                String passengerName = request.getParameter("passengerName");

	                String dateStr = request.getParameter("travelDate");

	                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	                Date travelDate = sdf.parse(dateStr);

	                BusBookingBean bean = admin.viewRecord(passengerName, travelDate);

	                request.setAttribute("bean", bean);

	                RequestDispatcher rd = request.getRequestDispatcher("displayBusBooking.jsp");
	                rd.forward(request, response);

	            } 
	            else if ("viewAllRecords".equals(operation)) {

	                List<BusBookingBean> list = admin.viewAllRecords();

	                request.setAttribute("list", list);

	                RequestDispatcher rd = request.getRequestDispatcher("displayAllBusBookings.jsp");
	                rd.forward(request, response);
	            }

	        } 
	        catch (Exception e) {

	            e.printStackTrace();

	            response.getWriter().println("ERROR: " + e.getMessage());
	        }
	    }
	}
