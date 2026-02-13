<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="com.wipro.bus.bean.BusBookingBean" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Display All Bus Booking</title>
</head>
<body>

<h2>All Bus Booking Records</h2>

<%
    List<BusBookingBean> bookings =
        (List<BusBookingBean>) request.getAttribute("list");

    if (bookings != null && !bookings.isEmpty()) {
%>

<table border="1">
    <tr>
        <th>Record ID</th>
        <th>Passenger Name</th>
        <th>Bus Number</th>
        <th>Seat Number</th>
        <th>Travel Date</th>
    </tr>

<%
    for (BusBookingBean booking : bookings) {
%>

<tr>
    <td><%= booking.getRecordId() %></td>
    <td><%= booking.getPassengerName() %></td>
    <td><%= booking.getBusNumber() %></td>
    <td><%= booking.getSeatNo() %></td>
    <td><%= booking.getTravelDate() %></td>
</tr>

<%
    }
%>

</table>

<%
    } else {
%>

<h3>No records available!</h3>

<%
    }
%>

<br>
<a href="menu.html">Go Back to Menu</a>

</body>
</html>
