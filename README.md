# Hostel Finder

## Bookings table
Run this once in your MySQL database to enable booking:

```sql
CREATE TABLE bookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    hostel_id INT NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'pending',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (hostel_id) REFERENCES hostels(id)
);
```

## Booking flow
- Users can book approved hostels from `views/home.jsp`.
- The app will insert a row into `bookings` with `pending` status.

## Build
```powershell
./mvnw -DskipTests package
```

