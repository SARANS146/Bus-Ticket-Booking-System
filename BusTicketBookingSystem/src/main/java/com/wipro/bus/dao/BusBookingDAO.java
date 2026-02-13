package com.wipro.bus.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.wipro.bus.bean.BusBookingBean;
import com.wipro.bus.util.DBUtil;

public class BusBookingDAO {

    public String createRecord(BusBookingBean busBean) {
        Connection con = null;
        PreparedStatement ps = null;
        
        try {
            con = DBUtil.getDBConnection();
            
            if (con == null) {
                System.out.println("Database connection is null!");
                return "FAIL";
            }
            
            String query = "INSERT INTO BUSBOOK_TB (RECORDID, PASSENGERNAME, BUSNUMBER, TRAVEL_DATE, SEATNO, TICKETNO, REMARKS) VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(query);

            ps.setString(1, busBean.getRecordId());
            ps.setString(2, busBean.getPassengerName());
            ps.setString(3, busBean.getBusNumber());
            ps.setDate(4, new java.sql.Date(busBean.getTravelDate().getTime()));
            ps.setString(5, busBean.getSeatNo());
            ps.setString(6, busBean.getTicketNo());
            ps.setString(7, busBean.getRemarks());

            System.out.println("Executing INSERT with RecordID: " + busBean.getRecordId());
            
            int rows = ps.executeUpdate();
            System.out.println("Rows inserted: " + rows);

            return rows > 0 ? busBean.getRecordId() : "FAIL";

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in createRecord: " + e.getMessage());
            return "FAIL";
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public BusBookingBean fetchRecord(String passengerName, Date travelDate) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            con = DBUtil.getDBConnection();
            String query = "SELECT * FROM BUSBOOK_TB WHERE PASSENGERNAME=? AND TRAVEL_DATE=?";
            ps = con.prepareStatement(query);

            ps.setString(1, passengerName);
            ps.setDate(2, new java.sql.Date(travelDate.getTime()));

            rs = ps.executeQuery();

            if (rs.next()) {
                BusBookingBean bean = new BusBookingBean();
                bean.setRecordId(rs.getString("RECORDID"));
                bean.setPassengerName(rs.getString("PASSENGERNAME"));
                bean.setBusNumber(rs.getString("BUSNUMBER"));
                bean.setTravelDate(rs.getDate("TRAVEL_DATE"));
                bean.setSeatNo(rs.getString("SEATNO"));
                bean.setTicketNo(rs.getString("TICKETNO"));
                bean.setRemarks(rs.getString("REMARKS"));
                return bean;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean recordExists(String passengerName, Date travelDate) {
        return fetchRecord(passengerName, travelDate) != null;
    }

    public String generateRecordID(String passengerName, Date travelDate) {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String datePart = sdf.format(travelDate);

            String namePart = passengerName.length() >= 2 ? 
                passengerName.substring(0, 2).toUpperCase() : 
                passengerName.toUpperCase() + "X";

            int seq = getSequence();
            
            String recordId = datePart + namePart + seq;
            
            if (recordId.length() > 12) {
                recordId = recordId.substring(0, 12);
            }
            
            System.out.println("Generated RecordID: " + recordId);
            return recordId;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "FAIL";
    }

    private int getSequence() {
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            con = DBUtil.getDBConnection();
            
            String checkSeqQuery = "SELECT COUNT(*) FROM user_sequences WHERE sequence_name = 'BUSBOOK_SEQ'";
            stmt = con.createStatement();
            rs = stmt.executeQuery(checkSeqQuery);
            
            if (rs.next() && rs.getInt(1) == 0) {
                System.out.println("Sequence BUSBOOK_SEQ doesn't exist. Creating it...");
                stmt.execute("CREATE SEQUENCE BUSBOOK_SEQ START WITH 10 MAXVALUE 99 INCREMENT BY 1");
            }
            
            rs.close();
            stmt.close();
            
            String query = "SELECT BUSBOOK_SEQ.NEXTVAL FROM DUAL";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            if (rs.next()) {
                int seqValue = rs.getInt(1);
                System.out.println("Sequence value: " + seqValue);
                return seqValue;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in getSequence: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return 10;
    }

    public List<BusBookingBean> fetchAllRecords() {
        List<BusBookingBean> list = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            con = DBUtil.getDBConnection();
            String query = "SELECT * FROM BUSBOOK_TB ORDER BY TRAVEL_DATE DESC";
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                BusBookingBean bean = new BusBookingBean();
                bean.setRecordId(rs.getString("RECORDID"));
                bean.setPassengerName(rs.getString("PASSENGERNAME"));
                bean.setBusNumber(rs.getString("BUSNUMBER"));
                bean.setTravelDate(rs.getDate("TRAVEL_DATE"));
                bean.setSeatNo(rs.getString("SEATNO"));
                bean.setTicketNo(rs.getString("TICKETNO"));
                bean.setRemarks(rs.getString("REMARKS"));
                list.add(bean);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}