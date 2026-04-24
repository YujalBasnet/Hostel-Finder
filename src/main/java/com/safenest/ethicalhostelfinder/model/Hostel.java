package com.safenest.ethicalhostelfinder.model;

public class Hostel {

    private int hostelId;
    private String name;
    private String location;
    private String address;
    private double pricePerMonth;
    private String description;
    private int totalRooms;
    private int availableRooms;
    private String ownerName;
    private String contactNumber;
    private String status;  // "approved", "rejected", "pending"
    private String createdAt;

    public Hostel() {}

    public Hostel(int hostelId, String name, String location, String address,
                  double pricePerMonth, String description, int totalRooms,
                  int availableRooms, String ownerName, String contactNumber,
                  String status, String createdAt) {
        this.hostelId = hostelId;
        this.name = name;
        this.location = location;
        this.address = address;
        this.pricePerMonth = pricePerMonth;
        this.description = description;
        this.totalRooms = totalRooms;
        this.availableRooms = availableRooms;
        this.ownerName = ownerName;
        this.contactNumber = contactNumber;
        this.status = status;
        this.createdAt = createdAt;
    }

    public int getHostelId() { return hostelId; }
    public void setHostelId(int hostelId) { this.hostelId = hostelId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public double getPricePerMonth() { return pricePerMonth; }
    public void setPricePerMonth(double pricePerMonth) { this.pricePerMonth = pricePerMonth; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getTotalRooms() { return totalRooms; }
    public void setTotalRooms(int totalRooms) { this.totalRooms = totalRooms; }

    public int getAvailableRooms() { return availableRooms; }
    public void setAvailableRooms(int availableRooms) { this.availableRooms = availableRooms; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}