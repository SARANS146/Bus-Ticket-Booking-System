package com.wipro.bus.bean;

import java.util.Date;   

public class BusBookingBean {

    private String recordId;
    private String passengerName;
    private String busNumber;
    private Date travelDate;   
    private String seatNo;
    private String ticketNo;
    private String remarks;
    
    public String getRecordId() {
        return recordId;
    }
    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }
    public String getPassengerName() {
        return passengerName;
    }
    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }
    public String getBusNumber() {
        return busNumber;
    }
    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }
    public Date getTravelDate() {
        return travelDate;
    }
    public void setTravelDate(Date travelDate) {
        this.travelDate = travelDate;
    }
    public String getSeatNo() {
        return seatNo;
    }
    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }
    public String getTicketNo() {
        return ticketNo;
    }
    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
