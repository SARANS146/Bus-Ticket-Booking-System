<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>View All Bus Booking</title>
</head>
<body>
<h2>View All Bus Bookings</h2>

<form action="MainServlet" method="get">  <!-- CHANGED THIS LINE -->
    <input type="hidden" name="operation" value="viewAllRecords">
    <input type="submit" value="View All Bookings">
</form>
</body>
</html>