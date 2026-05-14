# Hostel Finder - Complaint & Review System Setup Guide

## Quick Start

This guide will help you set up the new complaint and review features in the Hostel Finder application.

## Prerequisites

- MySQL database (hostel_finder) already created and running
- Hostel Finder application deployed
- Admin access to database

## Step 1: Create Database Tables

Run the following SQL script in your MySQL database to create the required tables:

```bash
mysql -u root -p hostel_finder < DATABASE_SETUP.sql
```

Or manually run the SQL commands from `DATABASE_SETUP.sql`:

```sql
-- Create complaints table
CREATE TABLE IF NOT EXISTS complaints (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    hostel_id INT NOT NULL,
    subject VARCHAR(255) NOT NULL,
    message LONGTEXT NOT NULL,
    status VARCHAR(50) DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (hostel_id) REFERENCES hostels(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_hostel_id (hostel_id),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
);

-- Create reviews table
CREATE TABLE IF NOT EXISTS reviews (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    hostel_id INT NOT NULL,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    review_text LONGTEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (hostel_id) REFERENCES hostels(id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_hostel_review (user_id, hostel_id),
    INDEX idx_hostel_id (hostel_id),
    INDEX idx_user_id (user_id),
    INDEX idx_rating (rating),
    INDEX idx_created_at (created_at)
);
```

## Step 2: Verify Installation

After running the SQL script, verify the tables were created:

```sql
SHOW TABLES LIKE 'complaint%';
SHOW TABLES LIKE 'review%';
```

Both should return successfully.

## Step 3: Test the Features

### Test Complaint System

1. **As a User:**
   - Log in to your account
   - Click "📢 Complaints" in the navbar
   - Click "+ Submit New Complaint"
   - Fill in the complaint form
   - Submit the complaint

2. **As Admin:**
   - Log in with admin account
   - Click "Complaints" in navbar or admin sidebar
   - View all complaints submitted by users
   - Update complaint status (Pending → Reviewed)

### Test Review System

1. **Make a Booking First:**
   - Log in as user
   - Click on "Book Now" for a hostel
   - This creates a booking record

2. **Submit a Review:**
   - Click "⭐ Reviews" on any hostel card (from home page)
   - Click "Write a Review" button
   - Select a rating (1-5 stars)
   - Write your review text
   - Click "Submit Review"

3. **View Reviews:**
   - Navigate to "⭐ Reviews" page for any hostel
   - See all reviews with ratings and user names
   - See average rating calculation
   - Try writing another review (should show error - one per hostel per user)

## New Files Added

### Java Classes
```
src/main/java/com/hostell/hostel_finder/
├── model/
│   ├── Complaint.java
│   └── Review.java
├── dao/
│   ├── ComplaintDAO.java
│   └── ReviewDAO.java
└── controller/
    ├── SubmitComplaintServlet.java
    ├── ViewComplaintsServlet.java
    ├── SubmitReviewServlet.java
    └── ViewReviewsServlet.java
```

### JSP Views
```
src/main/webapp/views/
├── my_complaints.jsp
├── admin_complaints.jsp
├── view_reviews.jsp
└── submit_review.jsp
```

### Database
```
DATABASE_SETUP.sql - SQL script to create tables
```

### Documentation
```
FEATURES.md - Detailed feature documentation
SETUP.md - This file
```

## Architecture Overview

### Data Flow

**Complaints:**
```
User fills form → SubmitComplaintServlet 
    → ComplaintDAO.submitComplaint() 
    → complaints table
    → ViewComplaintsServlet (Admin) 
    → admin_complaints.jsp
```

**Reviews:**
```
User fills form → SubmitReviewServlet 
    → ReviewDAO.submitReview() 
    → reviews table
    → ViewReviewsServlet 
    → view_reviews.jsp
```

### Class Relationships

```
User (existing)
├── has many Complaints (new)
│   └── references Hostel
└── has many Reviews (new)
    └── references Hostel

Hostel (existing)
├── has many Complaints (new)
└── has many Reviews (new)

Booking (existing)
└── used to validate Review eligibility
```

## Security Considerations

1. **Authentication:** All complaint/review operations require login
2. **Authorization:** Only admins can view all complaints and update status
3. **Validation:** 
   - Users must have booking to review hostel
   - Only one review per user per hostel
   - Rating must be 1-5
4. **Data Protection:** Foreign keys prevent orphaned records

## Common Issues & Troubleshooting

### Issue: "Table doesn't exist" error
**Solution:** Make sure DATABASE_SETUP.sql was run successfully
```sql
SHOW TABLES;
-- Check if 'complaints' and 'reviews' tables appear
```

### Issue: Users can submit multiple reviews for same hostel
**Solution:** Check if UNIQUE constraint is applied in reviews table
```sql
SHOW CREATE TABLE reviews;
-- Look for UNIQUE KEY unique_user_hostel_review
```

### Issue: Links not working
**Solution:** Check if context path is correct in web.xml and make sure servlets are properly mapped with @WebServlet annotations

### Issue: "Must have booking to review"
**Solution:** This is intentional - verify you have an active/pending booking for the hostel before reviewing

## Performance Optimization (Optional)

For production with many complaints/reviews, consider:

1. **Add Pagination** to ViewComplaintsServlet and ViewReviewsServlet
2. **Add Caching** for average ratings
3. **Index on created_at** for sorting (already included)
4. **Archive old complaints** to separate table

## Support

For issues or questions about the complaint and review system:
1. Check FEATURES.md for detailed documentation
2. Review the servlet implementations for business logic
3. Check database integrity with provided SQL commands
4. Review browser console for JavaScript errors (rating input)

---

**Installation Date:** May 2, 2026  
**Version:** 1.0
