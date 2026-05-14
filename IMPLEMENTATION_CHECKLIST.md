# Implementation Checklist - Complaints & Reviews System

## Database Setup
- [ ] Run DATABASE_SETUP.sql to create `complaints` table
- [ ] Run DATABASE_SETUP.sql to create `reviews` table
- [ ] Verify tables exist in MySQL: `SHOW TABLES;`
- [ ] Verify column structure is correct

## Java Classes Added
- [ ] Complaint.java model created in model/
- [ ] Review.java model created in model/
- [ ] ComplaintDAO.java created in dao/
- [ ] ReviewDAO.java created in dao/
- [ ] SubmitComplaintServlet.java created in controller/
- [ ] ViewComplaintsServlet.java created in controller/
- [ ] SubmitReviewServlet.java created in controller/
- [ ] ViewReviewsServlet.java created in controller/

## JSP Views Added
- [ ] my_complaints.jsp created
- [ ] admin_complaints.jsp created
- [ ] view_reviews.jsp created
- [ ] submit_review.jsp created

## Files Updated
- [ ] home.jsp - Added "📢 Complaints" navbar link
- [ ] home.jsp - Added "⭐ Reviews" button on hostel cards
- [ ] my_bookings.jsp - Added "📢 Complaints" navbar link
- [ ] admin_dashboard.jsp - Added complaints link in navbar and sidebar
- [ ] admin_bookings.jsp - Added complaints link in navbar and sidebar
- [ ] HostelDAO.java - Added getHostelById() method

## Application Restart
- [ ] Rebuild project (mvn clean package)
- [ ] Restart Tomcat/application server
- [ ] Clear browser cache
- [ ] Verify no compilation errors

## User Workflow Testing

### Test Complaint System
- [ ] Log in as regular user
- [ ] Navigate to "My Complaints"
- [ ] Click "+ Submit New Complaint"
- [ ] Fill complaint form with valid data
- [ ] Submit and verify success message
- [ ] View submitted complaints in list
- [ ] Log out and log back in as admin
- [ ] Navigate to Complaints page
- [ ] See user's complaint in list
- [ ] Change complaint status
- [ ] Verify status update works

### Test Review System
- [ ] Log in as user
- [ ] Make a booking for a hostel (if not already booked)
- [ ] Navigate to hostel's "⭐ Reviews" page
- [ ] Click "Write a Review"
- [ ] Should NOT see error about no booking
- [ ] Select a rating (1-5 stars)
- [ ] Write review text
- [ ] Submit review
- [ ] Verify review appears in the list
- [ ] Try writing another review for same hostel
- [ ] Should see error "You have already reviewed this hostel"
- [ ] Try reviewing hostel without booking
- [ ] Should see error "You must have a booking"

### Test Average Rating
- [ ] Submit multiple reviews for same hostel (from different users)
- [ ] Check that average rating calculates correctly
- [ ] Verify rating count updates

## Navigation Verification
- [ ] Home page navbar shows "📢 Complaints" link
- [ ] My Bookings page navbar shows "📢 Complaints" link
- [ ] Admin dashboard shows "Complaints" in navbar and sidebar
- [ ] All links navigate to correct pages
- [ ] Back buttons work on all new pages

## Error Handling
- [ ] User gets error when trying to access admin complaints page
- [ ] User gets error when trying to review without booking
- [ ] Admin can't submit user complaints
- [ ] Invalid hostel IDs handled gracefully
- [ ] Database connection errors handled

## Browser Compatibility
- [ ] Test in Chrome
- [ ] Test in Firefox
- [ ] Test in Safari
- [ ] Test in Edge
- [ ] Mobile responsive design works

## Documentation
- [ ] FEATURES.md is complete and accurate
- [ ] SETUP.md contains all setup instructions
- [ ] DATABASE_SETUP.sql script is complete
- [ ] All code comments are clear

## Performance
- [ ] Pages load quickly (< 2 seconds)
- [ ] No N+1 query issues in DAOs
- [ ] Indexes are properly applied to frequently queried columns

## Security
- [ ] Non-authenticated users can't submit complaints/reviews
- [ ] Non-admin users can't access admin complaints page
- [ ] Users can't modify others' complaints/reviews
- [ ] SQL injection prevention (using PreparedStatement)
- [ ] XSS prevention (using JSP escaping)

## Final Verification
- [ ] All new files are in correct directories
- [ ] No compilation errors in project
- [ ] Application starts without errors
- [ ] Database connection successful
- [ ] All URLs work as expected
- [ ] All features work as documented

---

**Last Updated:** May 2, 2026
**Checklist Version:** 1.0

## Quick Start Commands

```bash
# 1. Setup database
mysql -u root -p hostel_finder < DATABASE_SETUP.sql

# 2. Rebuild project
mvn clean package

# 3. Restart application server
# (depends on your server - Tomcat, etc.)

# 4. Test URLs
# http://localhost:8080/Hostel_Finder/views/home.jsp
# http://localhost:8080/Hostel_Finder/views/my_complaints.jsp
# http://localhost:8080/Hostel_Finder/viewComplaints (admin)
# http://localhost:8080/Hostel_Finder/viewReviews?hostelId=1
```
