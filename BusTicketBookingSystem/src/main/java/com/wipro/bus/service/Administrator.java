package com.wipro.bus.service;

import java.util.Date;
import java.util.List;

import com.wipro.bus.bean.BusBookingBean;
import com.wipro.bus.dao.BusBookingDAO;
import com.wipro.bus.util.InvalidInputException;

public class Administrator {

    BusBookingDAO dao = new BusBookingDAO();

    public String addRecord(BusBookingBean busBean) {
        try {
            if (busBean == null ||
                busBean.getPassengerName() == null ||
                busBean.getTravelDate() == null) {
                System.out.println("Validation failed: Null values");
                throw new InvalidInputException();
            }

            if (busBean.getPassengerName().length() < 2) {
                System.out.println("Validation failed: Name too short");
                return "INVALID PASSENGER NAME";
            }

            Date currentDate = new Date();
            System.out.println("Travel Date: " + busBean.getTravelDate());
            System.out.println("Current Date: " + currentDate);
            
            if (busBean.getTravelDate().before(currentDate)) {
                System.out.println("Validation failed: Date is in past");
                return "INVALID DATE";
            }

            java.sql.Date sqlDate = new java.sql.Date(busBean.getTravelDate().getTime());

            if (dao.recordExists(busBean.getPassengerName(), sqlDate)) {
                System.out.println("Validation failed: Record already exists");
                return "ALREADY EXISTS";
            }

            String recordId = dao.generateRecordID(busBean.getPassengerName(), sqlDate);
            System.out.println("Generated Record ID: " + recordId);
            
            busBean.setRecordId(recordId);
            return dao.createRecord(busBean);

        } catch (InvalidInputException e) {
            System.out.println("InvalidInputException caught");
            return "INVALID INPUT";
        }
    }

    public BusBookingBean viewRecord(String passengerName, Date travelDate) {

        java.sql.Date sqlDate =
                new java.sql.Date(travelDate.getTime());

        return dao.fetchRecord(passengerName, sqlDate);
    }

    public List<BusBookingBean> viewAllRecords() {
        return dao.fetchAllRecords();
    }
}
