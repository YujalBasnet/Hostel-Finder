package com.hostell.hostel_finder.model;

public class Hostel {
    private int id;
    private String name;
    private String location;
    private double price;
    private String facilities;
    private String status;
    private String image;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }



    public String getFacilities() { return facilities; }
    public void setFacilities(String facilities) { this.facilities = facilities; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // Backward-compatible aliases for image path usage in servlets/JSPs.
    public String getImagePath() {
        return image;
    }

    public void setImagePath(String imagePath) {
        this.image = imagePath;
    }
}