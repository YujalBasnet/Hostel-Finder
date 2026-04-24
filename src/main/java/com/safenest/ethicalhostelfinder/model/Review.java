package com.safenest.ethicalhostelfinder.model;

public class Review {

    private int reviewId;
    private int userId;    // FK → User
    private int hostelId;  // FK → Hostel
    private int rating;    // 1 to 5
    private String comment;
    private String createdAt;

    public Review() {}

    public Review(int reviewId, int userId, int hostelId,
                  int rating, String comment, String createdAt) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.hostelId = hostelId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public int getReviewId() { return reviewId; }
    public void setReviewId(int reviewId) { this.reviewId = reviewId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getHostelId() { return hostelId; }
    public void setHostelId(int hostelId) { this.hostelId = hostelId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}