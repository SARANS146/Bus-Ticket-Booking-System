<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.wipro.bus.bean.BusBookingBean" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bus Booking Details</title>
</head>
<body>

<h2>Bus Booking Details</h2>

<%
    BusBookingBean bean = (BusBookingBean) request.getAttribute("bean");

    if (bean != null) {
%>

    Record ID: <%= bean.getRecordId() %> <br><br>
    Passenger Name: <%= bean.getPassengerName() %> <br><br>
    Bus Number: <%= bean.getBusNumber() %> <br><br>
    Seat Number: <%= bean.getSeatNo() %> <br><br>
    Travel Date: <%= bean.getTravelDate() %> <br><br>
    Remarks: <%= bean.getRemarks() %> <br><br>

<%
    } else {
%>

    <h3>No matching records found!</h3>

<%
    }
%>

<a href="menu.html">Go Back to Menu</a>

</body>
</html>
