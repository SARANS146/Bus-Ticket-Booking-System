<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ADD BUS BOOKING</title>
</head>
<body>
<h2>Add Bus Ticket Booking</h2>
<form action="MainServlet" method="get">  <!-- CHANGED THIS LINE -->
    <input type="hidden" name="operation" value="newRecord">
    Passenger Name: <input type="text" name="passengerName" required><br><br>
    Bus Number: <input type="text" name="busNumber" required><br><br>
    Seat Number: <input type="text" name="seatNo" required><br><br>
    Ticket Number: <input type="text" name="ticketNo" required><br><br>
    Remarks: <input type="text" name="remarks"><br><br>
    Travel Date: <input type="date" name="travelDate" required><br><br>
    <input type="submit" value="Add Booking">
</form>
<br>
<a href="menu.html">Back to Menu</a>
</body>
</html>