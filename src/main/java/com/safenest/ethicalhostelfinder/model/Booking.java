package com.safenest.ethicalhostelfinder.model;

public class Booking {

    private int bookingId;
    private int userId;    // FK → User
    private int hostelId;  // FK → Hostel
    private String bookingDate;
    private String checkInDate;
    private int duration;  // in months
    private String status;  // "pending", "approved", "rejected"

    public Booking() {}

    public Booking(int bookingId, int userId, int hostelId,
                   String bookingDate, String checkInDate,
                   int duration, String status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.hostelId = hostelId;
        this.bookingDate = bookingDate;
        this.checkInDate = checkInDate;
        this.duration = duration;
        this.status = status;
    }

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getHostelId() { return hostelId; }
    public void setHostelId(int hostelId) { this.hostelId = hostelId; }

    public String getBookingDate() { return bookingDate; }
    public void setBookingDate(String bookingDate) { this.bookingDate = bookingDate; }

    public String getCheckInDate() { return checkInDate; }
    public void setCheckInDate(String checkInDate) { this.checkInDate = checkInDate; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
