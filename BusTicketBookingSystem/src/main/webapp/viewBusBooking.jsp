<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View Bus Booking</title>
</head>
<body>
<h2>View Bus Ticket Booking</h2>

<form action="MainServlet" method="get">  <!-- CHANGED THIS LINE -->
    <input type="hidden" name="operation" value="viewRecord">
    Passenger Name: <input type="text" name="passengerName"><br><br>
    Travel Date: <input type="date" name="travelDate"><br><br>
    <input type="submit" value="View Booking">
</form>
</body>
</html>