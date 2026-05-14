# Hostel Finder - New Features: Complaints & Reviews

## Overview
This document describes the new complaint and review features added to the Hostel Finder application.

## Features Added

### 1. User Complaint System
Users can now submit complaints about hostels they have issues with. The system allows users to:
- Submit complaints with a subject and detailed message
- View all their submitted complaints
- Track complaint status (pending/reviewed)

**User Interfaces:**
- `views/my_complaints.jsp` - View and manage user complaints with option to submit new ones
- Modal form for submitting complaints directly from the hostel browsing experience

**Database:**
- `complaints` table - Stores all user complaints

### 2. Admin Complaint Management
Admin users can:
- View all complaints submitted by users
- Update complaint status (pending → reviewed)
- See complaint details including user info and hostel name

**Admin Interface:**
- `views/admin_complaints.jsp` - Complaints management dashboard

### 3. Hostel Reviews System
Users can leave reviews for hostels they have booked:
- Rate hostels from 1-5 stars
- Write detailed reviews
- View average rating for each hostel
- See all reviews from other users

**Key Features:**
- Users can only review hostels they have booked
- One review per user per hostel (prevents duplicate reviews)
- Average rating calculation for each hostel

**User Interfaces:**
- `views/view_reviews.jsp` - View all reviews for a hostel with average rating
- Integrated review submission from hostel cards on home page

**Database:**
- `reviews` table - Stores all hostel reviews with ratings

## Database Setup

### Required Tables

Run the `DATABASE_SETUP.sql` script to create the necessary tables:

```sql
-- Complaints table
CREATE TABLE complaints (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    hostel_id INT NOT NULL,
    subject VARCHAR(255) NOT NULL,
    message LONGTEXT NOT NULL,
    status VARCHAR(50) DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (hostel_id) REFERENCES hostels(id) ON DELETE CASCADE
);

-- Reviews table
CREATE TABLE reviews (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    hostel_id INT NOT NULL,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    review_text LONGTEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (hostel_id) REFERENCES hostels(id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_hostel_review (user_id, hostel_id)
);
```

## New Model Classes

### Complaint.java
Location: `src/main/java/com/hostell/hostel_finder/model/Complaint.java`

Properties:
- `id` - Unique complaint ID
- `userId` - ID of user who submitted complaint
- `hostelId` - ID of hostel being complained about
- `subject` - Complaint subject line
- `message` - Detailed complaint message
- `status` - Current status (pending/reviewed)
- `createdAt` - Timestamp of submission
- `userName` - Name of user (for admin view)
- `hostelName` - Name of hostel (for display)

### Review.java
Location: `src/main/java/com/hostell/hostel_finder/model/Review.java`

Properties:
- `id` - Unique review ID
- `userId` - ID of user who wrote review
- `hostelId` - ID of hostel being reviewed
- `rating` - Star rating (1-5)
- `reviewText` - Review content
- `createdAt` - Timestamp of review
- `userName` - Name of reviewer
- `hostelName` - Name of hostel

## New DAO Classes

### ComplaintDAO.java
Location: `src/main/java/com/hostell/hostel_finder/dao/ComplaintDAO.java`

Methods:
- `submitComplaint(int userId, int hostelId, String subject, String message)` - Submit new complaint
- `getAllComplaints()` - Get all complaints (for admin)
- `getComplaintsByUser(int userId)` - Get user's complaints
- `updateComplaintStatus(int complaintId, String status)` - Update complaint status

### ReviewDAO.java
Location: `src/main/java/com/hostell/hostel_finder/dao/ReviewDAO.java`

Methods:
- `submitReview(int userId, int hostelId, int rating, String reviewText)` - Submit new review
- `getReviewsByHostel(int hostelId)` - Get all reviews for a hostel
- `getReviewsByUser(int userId)` - Get user's reviews
- `getAverageRating(int hostelId)` - Calculate average rating for hostel
- `userHasBookingForHostel(int userId, int hostelId)` - Check if user booked hostel
- `userHasAlreadyReviewed(int userId, int hostelId)` - Check for duplicate reviews

## New Servlets

### SubmitComplaintServlet
Location: `src/main/java/com/hostell/hostel_finder/controller/SubmitComplaintServlet.java`
- URL: `/submitComplaint`
- Method: POST
- Authenticates user and validates complaint data before submission

### ViewComplaintsServlet
Location: `src/main/java/com/hostell/hostel_finder/controller/ViewComplaintsServlet.java`
- URL: `/viewComplaints`
- Method: GET/POST
- GET: Loads all complaints for admin dashboard
- POST: Updates complaint status

### SubmitReviewServlet
Location: `src/main/java/com/hostell/hostel_finder/controller/SubmitReviewServlet.java`
- URL: `/submitReview`
- Method: POST
- Validates user has booking before allowing review
- Prevents duplicate reviews

### ViewReviewsServlet
Location: `src/main/java/com/hostell/hostel_finder/controller/ViewReviewsServlet.java`
- URL: `/viewReviews`
- Method: GET
- Parameters: `hostelId` (required)
- Displays all reviews for a hostel with average rating

## Updated Files

### Navigation Updates
The following files have been updated to include links to the new features:

1. **home.jsp** 
   - Added "📢 Complaints" link in navbar
   - Added "⭐ Reviews" button on each hostel card
   - Added "Report Issue" link on each card

2. **my_bookings.jsp**
   - Added "📢 Complaints" link in navbar

3. **admin_dashboard.jsp**
   - Added "📢 Complaints" link in navbar and sidebar

4. **admin_bookings.jsp**
   - Added "📢 Complaints" link in navbar and sidebar

## User Workflows

### Submitting a Complaint
1. User navigates to home page or "My Bookings"
2. Clicks "📢 Complaints" in navbar → goes to My Complaints page
3. Clicks "+ Submit New Complaint" button
4. Fills complaint form (hostel, subject, message)
5. Submits form
6. Complaint is saved to database with status "pending"
7. Admin reviews and updates status

### Writing a Review
1. User must have a booking for the hostel (validated by ReviewDAO)
2. Click "⭐ Reviews" on any hostel card
3. Click "Write a Review" button
4. Select rating (1-5 stars)
5. Write review text
6. Submit review
7. Review appears on hostel review page with user's name and date

### Admin Managing Complaints
1. Admin logs in
2. Clicks "📢 Complaints" from navbar or admin dashboard sidebar
3. Views all complaints with user info and hostel name
4. For each complaint, can:
   - Change status from dropdown (pending/reviewed)
   - Click "Update" to save status change

## Security Features

1. **Authentication Required**
   - All complaint and review operations require user to be logged in
   - Admins can only view/manage complaints
   - Users cannot access admin complaint pages

2. **Data Validation**
   - Subject and message required for complaints
   - Rating must be 1-5 for reviews
   - User ID verified from session

3. **Business Logic Validation**
   - Users can only review hostels they have booked
   - Prevents duplicate reviews for same hostel by same user
   - Only admins can update complaint status

## API Endpoints Summary

| Endpoint | Method | Purpose | Auth |
|----------|--------|---------|------|
| `/submitComplaint` | POST | Submit new complaint | User |
| `/viewComplaints` | GET | View all complaints (admin) | Admin |
| `/viewComplaints` | POST | Update complaint status | Admin |
| `/submitReview` | POST | Submit new review | User |
| `/viewReviews` | GET | View hostel reviews | None |

## Future Enhancements

Potential improvements to the complaint and review system:
1. Email notifications when complaints are reviewed
2. Reply/response system for admins to respond to complaints
3. Review moderation to flag/remove inappropriate content
4. Review images/photos support
5. Helpful/unhelpful voting on reviews
6. Complaint categories (maintenance, cleanliness, behavior, etc.)
7. Analytics dashboard showing complaint trends by hostel

## Testing

To test the new features:

1. **Setup Database:**
   - Run `DATABASE_SETUP.sql` script

2. **Test Complaints:**
   - Create user account
   - Make a booking
   - Navigate to "My Complaints"
   - Submit a test complaint
   - Login as admin, check "Complaints" page
   - Update complaint status

3. **Test Reviews:**
   - Make a booking for a hostel
   - Go to hostel review page
   - Submit a review
   - Verify review appears on the page
   - Try submitting second review (should be blocked)
   - Check average rating calculation

---

**Version:** 1.0  
**Last Updated:** May 2, 2026
